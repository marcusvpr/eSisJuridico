package com.mpxds.mpbasic.controller.pt01;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt01.MpDataProcesso;

import com.mpxds.mpbasic.repository.pt01.MpDataProcessos;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt01.MpDataProcessoService;
import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroDataProcessoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpDataProcessos mpDataProcessos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpDataProcessoService mpDataProcessoService;

	// --- 
	
	private MpDataProcesso mpDataProcesso = new MpDataProcesso();
	private MpDataProcesso mpDataProcessoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// ---------------------

	public MpCadastroDataProcessoBean() {
		//
		if (null == this.mpDataProcesso)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpDataProcesso) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpDataProcesso.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpDataProcessoAnt(this.mpDataProcesso);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
	}
				
	public void salvar() {
		//
		this.mpDataProcesso = this.mpDataProcessoService.salvar(this.mpDataProcesso);
		//
		MpFacesUtil.addInfoMessage("Data Processo... salvo com sucesso!");
	}
			
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		try {
			this.mpDataProcesso = this.mpDataProcessos.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpDataProcesso)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpDataProcesso.getDataProtocolo()) return;
		//
		this.setMpDataProcessoAnt(this.mpDataProcesso);
		//
		this.mpDataProcesso = this.mpDataProcessos.porNavegacao("mpPrev",
															mpDataProcesso.getDataProtocolo());
		if (null == this.mpDataProcesso) {
			this.mpDataProcesso = this.mpDataProcessoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
	}

	public void mpNew() {
		//
		this.setMpDataProcessoAnt(this.mpDataProcesso);
		
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
		if (null == this.mpDataProcesso.getId()) return;
		//
		this.setMpDataProcessoAnt(this.mpDataProcesso);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpDataProcesso.getId()) return;
		//
		try {
			this.mpDataProcessos.remover(mpDataProcesso);
			
			MpFacesUtil.addInfoMessage("Data Processo... " + this.sdf.format(
						this.mpDataProcesso.getDataProtocolo()) + " ... excluído com sucesso.");
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

		this.setMpDataProcessoAnt(this.mpDataProcesso);
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
		this.mpDataProcesso = this.mpDataProcessoAnt;		
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
		if (null == this.mpDataProcesso.getDataProtocolo()) return;
		//
		this.setMpDataProcessoAnt(this.mpDataProcesso);
		//
		this.mpDataProcesso = this.mpDataProcessos.porNavegacao("mpNext",
															mpDataProcesso.getDataProtocolo());
		if (null == this.mpDataProcesso) {
			this.mpDataProcesso = this.mpDataProcessoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}

	public void mpEnd() {
		//
		try {
			this.mpDataProcesso = this.mpDataProcessos.porNavegacao("mpEnd", 
																		sdf.parse("01/01/2099"));
			if (null == this.mpDataProcesso)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpDataProcesso.getId()) return;

		try {
			this.setMpDataProcessoAnt(this.mpDataProcesso);

			this.mpDataProcesso = (MpDataProcesso) this.mpDataProcesso.clone();
			//
			this.mpDataProcesso.setId(null);
			
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
		this.mpDataProcesso = new MpDataProcesso();
		this.mpDataProcesso.setDataProtocolo(new Date());
		//
	}
		
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public boolean isEditando() { return this.mpDataProcesso.getId() != null; }
	
	public MpDataProcesso getMpDataProcesso() { return mpDataProcesso; }
	public void setMpDataProcesso(MpDataProcesso mpDataProcesso) {
															this.mpDataProcesso = mpDataProcesso; }

	public MpDataProcesso getMpDataProcessoAnt() { return mpDataProcessoAnt; }
	public void setMpDataProcessoAnt(MpDataProcesso mpDataProcessoAnt) { 
		//
		try {
			this.mpDataProcessoAnt = (MpDataProcesso) this.mpDataProcesso.clone();
			this.mpDataProcessoAnt.setId(this.mpDataProcesso.getId());
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

}