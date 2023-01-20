package com.mpxds.mpbasic.controller.engreq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.repository.engreq.MpPessoaERs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.engreq.MpPessoaERService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroPessoaERBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaERs mpPessoaERs;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpPessoaERService mpPessoaERService;
	
	// ---

	private MpPessoaER mpPessoaER = new MpPessoaER();
	private MpPessoaER mpPessoaERAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";	
	//	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList = new ArrayList<MpSexo>();

	// ---------------------

	public MpCadastroPessoaERBean() {
		//
		if (null == this.mpPessoaER)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpPessoaER) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPessoaER.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPessoaERAnt(this.mpPessoaER);
		// ---
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		//		
		this.mpSexoList = Arrays.asList(MpSexo.values());
	}
	
	public void salvar() {
		//
		String msg = "";
		if (this.mpPessoaER.getEmail().isEmpty())
			msg = msg + "\n(E-mail)";

		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		// Trata duplicidade do codigo !
		// if (this.checkDuplicidade()) {
		// MpFacesUtil.addInfoMessage("Código já existe... favor verificar! (" +
		// this.mpPessoaER.getCodigo());
		// return;
		// }
		//
		this.mpPessoaER = this.mpPessoaERService.salvar(this.mpPessoaER);
		//
		MpFacesUtil.addInfoMessage("Pessoa ER... salvo com sucesso!");
	}
			
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		this.mpPessoaER = this.mpPessoaERs.porNavegacao("mpFirst", " "); 
		if (null == this.mpPessoaER)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}

	public void mpPrev() {
		//
		if (null == this.mpPessoaER.getNome()) return;
		//
		this.setMpPessoaERAnt(this.mpPessoaER);
		//
		this.mpPessoaER = this.mpPessoaERs.porNavegacao("mpPrev", mpPessoaER.getNome());
		if (null == this.mpPessoaER) {
			this.mpPessoaER = this.mpPessoaERAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpPessoaERAnt(this.mpPessoaER);
		
		this.limpar();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpPessoaER.getId()) return;
		//
		this.setMpPessoaERAnt(this.mpPessoaER);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPessoaER.getId()) return;
		//
		try {
			this.mpPessoaERs.remover(mpPessoaER);
			
			MpFacesUtil.addInfoMessage("PessoaER... " + this.mpPessoaER.getNome()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}
		
		this.setMpPessoaERAnt(this.mpPessoaER);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpPessoaER = this.mpPessoaERAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpPessoaER.getNome()) return;
		//
		this.setMpPessoaERAnt(this.mpPessoaER);
		//
		this.mpPessoaER = this.mpPessoaERs.porNavegacao("mpNext", mpPessoaER.getNome());
		if (null == this.mpPessoaER) {
			this.mpPessoaER = this.mpPessoaERAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		this.mpPessoaER = this.mpPessoaERs.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpPessoaER)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPessoaER.getId()) return;

		try {
			this.setMpPessoaERAnt(this.mpPessoaER);
			
			this.mpPessoaER = (MpPessoaER) this.mpPessoaER.clone();
			//
			this.mpPessoaER.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	private void limpar() {
		//
		this.mpPessoaER = new MpPessoaER();
		
		this.mpPessoaER.setTenantId(mpSeguranca.capturaTenantId());
		this.mpPessoaER.setNome("");
		this.mpPessoaER.setEmail("");
	}
		
	public boolean isEditando() { return this.mpPessoaER.getId() != null; }
	
	// ---
	
	public MpPessoaER getMpPessoaER() { return mpPessoaER; }
	public void setMpPessoaER(MpPessoaER mpPessoaER) { this.mpPessoaER = mpPessoaER; }

	public MpPessoaER getMpPessoaERAnt() { return mpPessoaERAnt; }
	public void setMpPessoaERAnt(MpPessoaER mpPessoaERAnt) { 
		//
		try {
			this.mpPessoaERAnt = (MpPessoaER) this.mpPessoaER.clone();
			this.mpPessoaERAnt.setId(this.mpPessoaER.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	// --- 
		
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }
	
}