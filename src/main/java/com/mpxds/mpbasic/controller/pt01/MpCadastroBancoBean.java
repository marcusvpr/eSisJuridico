package com.mpxds.mpbasic.controller.pt01;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.pt01.MpBanco;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.pt01.MpBancos;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt01.MpBancoService;
import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;

@Named
@ViewScoped
public class MpCadastroBancoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpBancos mpBancos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpBancoService mpBancoService;

	// --- 
	
	private MpBanco mpBanco = new MpBanco();
	private MpBanco mpBancoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
		
	private String txtModoTela = "";
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;
	
	// ---------------------

	public MpCadastroBancoBean() {
		//
		if (null == this.mpBanco)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpBanco) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpBanco.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpBancoAnt(this.mpBanco);
		//		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		//
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
	}
				
	public void salvar() {
		//
		this.mpBanco = this.mpBancoService.salvar(this.mpBanco);
		//
		MpFacesUtil.addInfoMessage("Banco... salvo com sucesso!");
	}

	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(
													this.mpBanco.getMpEnderecoLocal().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
			//
			this.mpBanco.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpBanco.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpBanco.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpBanco.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpBanco.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
			//
		}
    }
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpBanco = this.mpBancos.porNavegacao("mpFirst", " "); 
		if (null == this.mpBanco)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpBanco.getCodigo()) return;
		//
		this.setMpBancoAnt(this.mpBanco);
		//
		this.mpBanco = this.mpBancos.porNavegacao("mpPrev", mpBanco.getCodigo());
		if (null == this.mpBanco) {
			this.mpBanco = this.mpBancoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpBancoAnt(this.mpBanco);
		
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
		if (null == this.mpBanco.getId()) return;
		//
		this.setMpBancoAnt(this.mpBanco);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpBanco.getId()) return;
		//
		try {
			this.mpBancos.remover(mpBanco);
			
			MpFacesUtil.addInfoMessage("Banco... " + this.mpBanco.getCodigo()
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

		this.setMpBancoAnt(this.mpBanco);
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
		this.mpBanco = this.mpBancoAnt;		
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
		//
	}
	
	public void mpNext() {
		//
		if (null == this.mpBanco.getCodigo()) return;
		//
		this.setMpBancoAnt(this.mpBanco);
		//
		this.mpBanco = this.mpBancos.porNavegacao("mpNext", mpBanco.getCodigo());

		if (null == this.mpBanco) {
			this.mpBanco = this.mpBancoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}
	
	public void mpEnd() {
		//
		this.mpBanco = this.mpBancos.porNavegacao("mpEnd", "ZZZZ"); 
		if (null == this.mpBanco)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpBanco.getId()) return;

		try {
			this.setMpBancoAnt(this.mpBanco);

			this.mpBanco = (MpBanco) this.mpBanco.clone();
			//
			this.mpBanco.setId(null);
			
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

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	

	// ---
	
	private void limpar() {
		//
		this.mpBanco = new MpBanco();
		
		MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal(); 
		
		this.mpBanco.setMpEnderecoLocal(mpEnderecoLocal);
		
		this.mpBanco.setCodigo("");
		//
	}
		
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public boolean isEditando() { return this.mpBanco.getId() != null; }
	
	public MpBanco getMpBanco() { return mpBanco; }
	public void setMpBanco(MpBanco mpBanco) {	this.mpBanco = mpBanco; }
	
	public MpBanco getMpBancoAnt() { return mpBancoAnt; }
	public void setMpBancoAnt(MpBanco mpBancoAnt) {	
		//
		try {
			this.mpBancoAnt = (MpBanco) this.mpBanco.clone();
			this.mpBancoAnt.setId(this.mpBanco.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpEstadoUF getMPEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }
		
	// --- 
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}