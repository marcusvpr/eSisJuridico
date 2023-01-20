package com.mpxds.mpbasic.controller.pt08;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt08.MpTipoProtocolo;
import com.mpxds.mpbasic.repository.pt08.MpTipoProtocolos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt08.MpTipoProtocoloService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTipoProtocoloBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpTipoProtocolos mpTipoProtocolos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTipoProtocoloService mpTipoProtocoloService;
	
	// ---

	private MpTipoProtocolo mpTipoProtocolo = new MpTipoProtocolo();
	private MpTipoProtocolo mpTipoProtocoloAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indVisivel = true;
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	// ------------------
	
	public MpCadastroTipoProtocoloBean() {
//		System.out.println("MpCadastroTipoProtocoloBean - Entrou 0000 ");
		//
		if (null == this.mpTipoProtocolo)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpTipoProtocolo) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpTipoProtocolo.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpTipoProtocoloAnt(this.mpTipoProtocolo);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	private void limpar() {
		this.mpTipoProtocolo = new MpTipoProtocolo();
		//
		this.mpTipoProtocolo.setCodigo("");
		//
	}
	
	public void salvar() {
		//
		this.mpTipoProtocolo = this.mpTipoProtocoloService.salvar(this.mpTipoProtocolo);
		//
		MpFacesUtil.addInfoMessage("Tipo Protocolo... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpTipoProtocolo = this.mpTipoProtocolos.porNavegacao("mpFirst", " ");
		if (null == this.mpTipoProtocolo)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
		//
		this.trataVisivel();
	}
	
	public void mpPrev() {
		//
		if (null == this.mpTipoProtocolo.getCodigo()) return;
		//
		this.mpTipoProtocoloAnt = this.mpTipoProtocolo;
		//
		this.mpTipoProtocolo = this.mpTipoProtocolos.porNavegacao("mpPrev", 
																mpTipoProtocolo.getCodigo());
		if (null == this.mpTipoProtocolo) {
			this.mpTipoProtocolo = this.mpTipoProtocoloAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataVisivel();
	}

	public void mpNew() {
		//
		this.mpTipoProtocoloAnt = this.mpTipoProtocolo;
		
		this.mpTipoProtocolo = new MpTipoProtocolo();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpTipoProtocolo.getId()) return;
		//
		this.mpTipoProtocoloAnt = this.mpTipoProtocolo;
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpTipoProtocolo.getId()) return;
		//
		try {
			this.mpTipoProtocolos.remover(mpTipoProtocolo);
			
			MpFacesUtil.addInfoMessage("Custas Composição... ( " +
													this.mpTipoProtocolo.getCodigo() + 
					    							"/" + this.mpTipoProtocolo.getDescricao() + 
					    							" )... excluído com sucesso.");
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

		this.mpTipoProtocoloAnt = this.mpTipoProtocolo;
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpTipoProtocolo = this.mpTipoProtocoloAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpTipoProtocolo.getCodigo()) return;
		//
		this.mpTipoProtocoloAnt = this.mpTipoProtocolo;
		//
		this.mpTipoProtocolo = this.mpTipoProtocolos.porNavegacao("mpNext",
																	mpTipoProtocolo.getCodigo());
		if (null == this.mpTipoProtocolo) {
			this.mpTipoProtocolo = this.mpTipoProtocoloAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataVisivel();
	}
	
	public void mpEnd() {
		//
		this.mpTipoProtocolo = this.mpTipoProtocolos.porNavegacao("mpEnd",
																			"ZZZZ");
		if (null == this.mpTipoProtocolo)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
		//
		this.trataVisivel();
	}
	
	public void mpClone() {
		//
		try {
			this.mpTipoProtocoloAnt = this.mpTipoProtocolo ;
			this.mpTipoProtocolo = (MpTipoProtocolo) this.mpTipoProtocolo.clone();
			//
			this.mpTipoProtocolo.setId(null);
			
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

	public void trataVisivel() {
		//
//		System.out.println("MpCadastroTipoProtocoloBean.trataVisivel() ( " + 
//															this.mpTipoProtocolo.getCodigo());		
		this.indVisivel = false;

		if (this.mpTipoProtocolo.getCodigo().trim().equals("9")
		|| 	this.mpTipoProtocolo.getCodigo().trim().equals("24"))
			this.indVisivel = true;
		//		
	}
	
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }

	public MpTipoProtocolo getMpTipoProtocolo() { return mpTipoProtocolo; }
	public void setMpTipoProtocolo(MpTipoProtocolo mpTipoProtocolo) {
													this.mpTipoProtocolo = mpTipoProtocolo;	}

	public MpTipoProtocolo getMpTipoProtocoloAnt() { return mpTipoProtocoloAnt; }
	public void setMpTipoProtocoloAnt(MpTipoProtocolo mpTipoProtocoloAnt) {
		//
		try {
			this.mpTipoProtocoloAnt = (MpTipoProtocolo) this.mpTipoProtocolo.clone();
			this.mpTipoProtocoloAnt.setId(this.mpTipoProtocolo.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpTipoProtocolo.getId() != null; }
	
	public boolean getIndVisivel() { return indVisivel; }
	public void setIndVisivel(Boolean indVisivel) { this.indVisivel = indVisivel; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) {	this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}