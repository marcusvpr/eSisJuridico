package com.mpxds.mpbasic.controller.pt01;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt01.MpObservacao;
import com.mpxds.mpbasic.repository.pt01.MpObservacaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt01.MpObservacaoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroObservacaoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObservacaos mpObservacaos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpObservacaoService mpObservacaoService;
	
	// ---

	private MpObservacao mpObservacao = new MpObservacao();
	private MpObservacao mpObservacaoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indVisivel = true;
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	// ------------------
	
	public MpCadastroObservacaoBean() {
//		System.out.println("MpCadastroObservacaoBean - Entrou 0000 ");
		//
		if (null == this.mpObservacao)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpObservacao) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpObservacao.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpObservacaoAnt(this.mpObservacao);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	private void limpar() {
		this.mpObservacao = new MpObservacao();
		//
		this.mpObservacao.setTipoObservacao("");
		//
}
	
	public void salvar() {
		//
		this.mpObservacao = this.mpObservacaoService.salvar(this.mpObservacao);
		//
		MpFacesUtil.addInfoMessage("Observação... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		this.mpObservacao = this.mpObservacaos.porNavegacao("mpFirst", " ");
		if (null == this.mpObservacao)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
		//
	}
	
	public void mpPrev() {
		if (null == this.mpObservacao.getTipoObservacao()) return;
		//
		this.setMpObservacaoAnt(this.mpObservacao);
		//
		this.mpObservacao = this.mpObservacaos.porNavegacao("mpPrev", 
															mpObservacao.getTipoObservacao());
		if (null == this.mpObservacao) {
			this.mpObservacao = this.mpObservacaoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
	}

	public void mpNew() {
		//
		this.setMpObservacaoAnt(this.mpObservacao);
		
		this.mpObservacao = new MpObservacao();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		if (null == this.mpObservacao.getId()) return;
		//
		this.setMpObservacaoAnt(this.mpObservacao);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		if (null == this.mpObservacao.getId()) return;
		//
		try {
			this.mpObservacaos.remover(mpObservacao);
			
			MpFacesUtil.addInfoMessage("Observacao... ( " + this.mpObservacao.getTipoObservacao() + 
					    					"/" + this.mpObservacao.getDescricaoObservacao() + 
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

		this.setMpObservacaoAnt(this.mpObservacao);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpObservacao = this.mpObservacaoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		if (null == this.mpObservacao.getTipoObservacao()) return;
		//
		this.setMpObservacaoAnt(this.mpObservacao);
		//
 		this.mpObservacao = this.mpObservacaos.porNavegacao("mpNext",
															mpObservacao.getTipoObservacao());
		if (null == this.mpObservacao) {
			this.mpObservacao = this.mpObservacaoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}
	
	public void mpEnd() {
		this.mpObservacao = this.mpObservacaos.porNavegacao("mpEnd", "ZZZZZZ");
		if (null == this.mpObservacao)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
		//
	}
	
	public void mpClone() {
		try {
			this.setMpObservacaoAnt(this.mpObservacao);

			this.mpObservacao = (MpObservacao) this.mpObservacao.clone();
			//
			this.mpObservacao.setId(null);
			
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

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }

	public MpObservacao getMpObservacao() {	return mpObservacao; }
	public void setMpObservacao(MpObservacao mpObservacao) { this.mpObservacao = mpObservacao; }

	public MpObservacao getMpObservacaoAnt() { return mpObservacaoAnt; }
	public void setMpObservacaoAnt(MpObservacao mpObservacaoAnt) {
		//
		try {
			this.mpObservacaoAnt = (MpObservacao) this.mpObservacao.clone();
			this.mpObservacaoAnt.setId(this.mpObservacao.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpObservacao.getId() != null; }
	
	public boolean getIndVisivel() { return indVisivel; }
	public void setIndVisivel(Boolean indVisivel) { this.indVisivel = indVisivel; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}