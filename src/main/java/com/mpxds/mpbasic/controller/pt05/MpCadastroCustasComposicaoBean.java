package com.mpxds.mpbasic.controller.pt05;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.repository.pt05.MpCustasComposicaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt05.MpCustasComposicaoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroCustasComposicaoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpCustasComposicaos mpCustasComposicaos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpCustasComposicaoService mpCustasComposicaoService;
	
	// ---

	private MpCustasComposicao mpCustasComposicao = new MpCustasComposicao();
	private MpCustasComposicao mpCustasComposicaoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indVisivel = true;
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	// ------------------
	
	public MpCadastroCustasComposicaoBean() {
//		System.out.println("MpCadastroCustasComposicaoBean - Entrou 0000 ");
		//
		if (null == this.mpCustasComposicao)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpCustasComposicao) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpCustasComposicao.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpCustasComposicaoAnt(this.mpCustasComposicao);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	private void limpar() {
		this.mpCustasComposicao = new MpCustasComposicao();
		//
		this.mpCustasComposicao.setTabela("");
		//
}
	
	public void salvar() {
		//
		this.mpCustasComposicao = this.mpCustasComposicaoService.salvar(this.mpCustasComposicao);
		//
		MpFacesUtil.addInfoMessage("Custas Composição... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		this.mpCustasComposicao = this.mpCustasComposicaos.porNavegacao("mpFirst", " ", " ", " ");
		if (null == this.mpCustasComposicao)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
		//
		this.trataVisivel();
	}
	
	public void mpPrev() {
		//
		if (null == this.mpCustasComposicao.getTabela()) return;
		//
		this.mpCustasComposicaoAnt = this.mpCustasComposicao;
		//
		this.mpCustasComposicao = this.mpCustasComposicaos.porNavegacao("mpPrev", 
																mpCustasComposicao.getTabela(),
																mpCustasComposicao.getItem(),
																mpCustasComposicao.getSubItem());
		if (null == this.mpCustasComposicao) {
			this.mpCustasComposicao = this.mpCustasComposicaoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataVisivel();
	}

	public void mpNew() {
		//
		this.mpCustasComposicaoAnt = this.mpCustasComposicao;
		
		this.mpCustasComposicao = new MpCustasComposicao();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpCustasComposicao.getId()) return;
		//
		this.mpCustasComposicaoAnt = this.mpCustasComposicao;
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpCustasComposicao.getId()) return;
		//
		try {
			this.mpCustasComposicaos.remover(mpCustasComposicao);
			
			MpFacesUtil.addInfoMessage("Custas Composição... ( " +
													this.mpCustasComposicao.getTabela() + 
					    							"/" + this.mpCustasComposicao.getItem() + 
					    							"/" + this.mpCustasComposicao.getSubItem() + 
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

		this.mpCustasComposicaoAnt = this.mpCustasComposicao;
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpCustasComposicao = this.mpCustasComposicaoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpCustasComposicao.getTabela()) return;
		//
		this.mpCustasComposicaoAnt = this.mpCustasComposicao;
		//
		this.mpCustasComposicao = this.mpCustasComposicaos.porNavegacao("mpNext",
																mpCustasComposicao.getTabela(),
																mpCustasComposicao.getItem(),
																mpCustasComposicao.getSubItem());
		if (null == this.mpCustasComposicao) {
			this.mpCustasComposicao = this.mpCustasComposicaoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataVisivel();
	}
	
	public void mpEnd() {
		this.mpCustasComposicao = this.mpCustasComposicaos.porNavegacao("mpEnd", "ZZZZ", "ZZZZ", "ZZZZ");
		if (null == this.mpCustasComposicao)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
		//
		this.trataVisivel();
	}
	
	public void mpClone() {
		try {
			this.mpCustasComposicaoAnt = this.mpCustasComposicao ;
			this.mpCustasComposicao = (MpCustasComposicao) this.mpCustasComposicao.clone();
			//
			this.mpCustasComposicao.setId(null);
			
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
//		System.out.println("MpCadastroCustasComposicaoBean.trataVisivel() ( " + 
//															this.mpCustasComposicao.getTabela());
		
		this.indVisivel = false;

		if (this.mpCustasComposicao.getTabela().trim().equals("9")
		|| 	this.mpCustasComposicao.getTabela().trim().equals("24"))
			this.indVisivel = true;
		//		
	}
	
	// ----

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }

	public MpCustasComposicao getMpCustasComposicao() {	return mpCustasComposicao; }
	public void setMpCustasComposicao(MpCustasComposicao mpCustasComposicao) {
											this.mpCustasComposicao = mpCustasComposicao; }

	public MpCustasComposicao getMpCustasComposicaoAnt() { return mpCustasComposicaoAnt; }
	public void setMpCustasComposicaoAnt(MpCustasComposicao mpCustasComposicaoAnt) {
		//
		try {
			this.mpCustasComposicaoAnt = (MpCustasComposicao) this.mpCustasComposicao.clone();
			this.mpCustasComposicaoAnt.setId(this.mpCustasComposicao.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpCustasComposicao.getId() != null; }
	
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