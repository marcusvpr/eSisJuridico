package com.mpxds.mpbasic.controller.pt01;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.pt01.MpPessoaTitulo;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.pt01.MpPessoaTitulos;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt01.MpPessoaTituloService;
import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;

@Named
@ViewScoped
public class MpCadastroPessoaTituloBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaTitulos mpPessoaTitulos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpPessoaTituloService mpPessoaTituloService;

	// --- 
	
	private MpPessoaTitulo mpPessoaTitulo = new MpPessoaTitulo();
	private MpPessoaTitulo mpPessoaTituloAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
		
	private String txtModoTela = "";
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;
	
	// ---------------------

	public MpCadastroPessoaTituloBean() {
		//
		if (null == this.mpPessoaTitulo)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpPessoaTitulo) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPessoaTitulo.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
		//		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		//
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
	}
				
	public void salvar() {
		//
		this.mpPessoaTitulo = this.mpPessoaTituloService.salvar(this.mpPessoaTitulo);
		//
		MpFacesUtil.addInfoMessage("PessoaTitulo... salvo com sucesso!");
	}

	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(
													this.mpPessoaTitulo.getMpEnderecoLocal().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
			//
			this.mpPessoaTitulo.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpPessoaTitulo.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpPessoaTitulo.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpPessoaTitulo.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpPessoaTitulo.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
			//
		}
    }
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpPessoaTitulo = this.mpPessoaTitulos.porNavegacao("mpFirst", " "); 
		if (null == this.mpPessoaTitulo)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpPessoaTitulo.getNome()) return;
		//
		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
		//
		this.mpPessoaTitulo = this.mpPessoaTitulos.porNavegacao("mpPrev", mpPessoaTitulo.getNome());
		if (null == this.mpPessoaTitulo) {
			this.mpPessoaTitulo = this.mpPessoaTituloAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
		
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
		if (null == this.mpPessoaTitulo.getId()) return;
		//
		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPessoaTitulo.getId()) return;
		//
		try {
			this.mpPessoaTitulos.remover(mpPessoaTitulo);
			
			MpFacesUtil.addInfoMessage("Pessoa... " + this.mpPessoaTitulo.getNome()
																	+ " excluída com sucesso.");
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

		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
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
		this.mpPessoaTitulo = this.mpPessoaTituloAnt;		
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
		if (null == this.mpPessoaTitulo.getNome()) return;
		//
		this.setMpPessoaTituloAnt(this.mpPessoaTitulo);
		//
		this.mpPessoaTitulo = this.mpPessoaTitulos.porNavegacao("mpNext", mpPessoaTitulo.getNome());

		if (null == this.mpPessoaTitulo) {
			this.mpPessoaTitulo = this.mpPessoaTituloAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}
	
	public void mpEnd() {
		//
		this.mpPessoaTitulo = this.mpPessoaTitulos.porNavegacao("mpEnd", "ZZZZ"); 
		if (null == this.mpPessoaTitulo)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPessoaTitulo.getId()) return;

		try {
			this.setMpPessoaTituloAnt(this.mpPessoaTitulo);

			this.mpPessoaTitulo = (MpPessoaTitulo) this.mpPessoaTitulo.clone();
			//
			this.mpPessoaTitulo.setId(null);
			
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
		this.mpPessoaTitulo = new MpPessoaTitulo();
		
		MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal(); 
		
		this.mpPessoaTitulo.setMpEnderecoLocal(mpEnderecoLocal);
		
		this.mpPessoaTitulo.setNome("");
		//
	}
		
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public boolean isEditando() { return this.mpPessoaTitulo.getId() != null; }
	
	public MpPessoaTitulo getMpPessoaTitulo() { return mpPessoaTitulo; }
	public void setMpPessoaTitulo(MpPessoaTitulo mpPessoaTitulo) {	this.mpPessoaTitulo = mpPessoaTitulo; }
	
	public MpPessoaTitulo getMpPessoaTituloAnt() { return mpPessoaTituloAnt; }
	public void setMpPessoaTituloAnt(MpPessoaTitulo mpPessoaTituloAnt) {	
		//
		try {
			this.mpPessoaTituloAnt = (MpPessoaTitulo) this.mpPessoaTitulo.clone();
			this.mpPessoaTituloAnt.setId(this.mpPessoaTitulo.getId());
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