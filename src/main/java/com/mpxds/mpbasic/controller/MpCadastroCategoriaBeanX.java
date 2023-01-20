package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpCategoriaService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroCategoriaBeanX implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCategorias mpCategorias;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpCategoriaService mpCategoriaService;

	private MpCategoria mpCategoria = new MpCategoria();
	private MpCategoria mpCategoriaAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelSubcat = false;
	
	private String txtModoTela = "";
	private String txtModoSubcategoriaDialog = "";

	private MpCategoria mpCategoriaPai = new MpCategoria();
	private MpCategoria mpSubcategoria = new MpCategoria();
	
	private List<MpCategoria> mpCategoriasRaizes = new ArrayList<MpCategoria>();
	
	private List<MpCategoria> mpSubcategorias;
	private List<MpCategoria> mpSubcategoriaExcluidaList = new ArrayList<MpCategoria>();	
	
	private MpTipoProduto mpTipoProduto;
	private MpTipoProduto mpTipoProdutoAnt;
	
	private List<MpTipoProduto> mpTipoProdutoList = new ArrayList<MpTipoProduto>();
	
	private String usuarioGrupos = "";
	
	// -----------------------
	
	public MpCadastroCategoriaBeanX() {
		if (null == this.mpCategoria)
			this.limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpCategoria) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpCategoria.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		//
		this.setMpCategoriaAnt(this.mpCategoria);
		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
				
//		System.out.println("MpCadastroCategoriaBean.inicializar() ( Entrou 000");
		//
		// Verifica filtro para controle de Estoque ...
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLoginGrupo();
		else
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getGruposNome();
		//
		for (MpTipoProduto mpTipoProdutoX : Arrays.asList(MpTipoProduto.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpTipoProdutoX.getIndControleEstoque())
					this.mpTipoProdutoList.add(mpTipoProdutoX);
			} else
				if (!mpTipoProdutoX.getIndControleEstoque())
					this.mpTipoProdutoList.add(mpTipoProdutoX);
		}
		
		this.carregarMpCategorias();
	}

	public void carregarMpCategorias() {
		//
		if (this.mpCategoria.getMpTipoProduto() != null) {
			if (null == mpTipoProdutoAnt || 
					(!mpTipoProdutoAnt.equals(this.mpCategoria.getMpTipoProduto()))) {
				this.mpCategoriasRaizes = mpCategorias.raizes(this.mpCategoria.getMpTipoProduto());
				
				mpTipoProdutoAnt = this.mpCategoria.getMpTipoProduto();
			}
			//
			if (this.mpCategoriaPai != null) {
				this.carregarMpSubcategorias();
			}
		}
	}
	
	public void carregarMpSubcategorias() {
		//
		this.mpSubcategorias = this.mpCategorias.mpSubCategoriasDe(this.mpCategoriaPai);
	}
	
	public void salvar() {
		//
		this.mpCategoria = this.mpCategoriaService.salvar(this.mpCategoria);
		//
		if (this.mpSubcategoriaExcluidaList.size() > 0) {
			for (MpCategoria mpSubcategoria : this.mpSubcategoriaExcluidaList) {
				//
				if (null == mpSubcategoria.getId()) continue;

				this.mpCategorias.remover(mpSubcategoria);
			}
		}
		//
		MpFacesUtil.addInfoMessage("Categoria... salva com sucesso!");
	}

	// ---
	
	public void alterarMpSubcategoria() {
		//
		this.txtModoSubcategoriaDialog = "Edição";
		
		this.indEditavelSubcat = true;
	}			
	
	public void adicionarMpSubcategoriaX() {
		//
		this.txtModoSubcategoriaDialog = "Novo";
		
		if (this.mpSubcategoria != null) {
			this.mpSubcategoria.setMpTipoProduto(this.mpCategoria.getMpTipoProduto());
			this.mpSubcategoria.setMpCategoriaPai(this.mpCategoria);
			this.mpSubcategoria.setTenantId(mpSeguranca.capturaTenantId());
			
			this.mpCategoria.getMpSubcategorias().add(this.mpSubcategoria);
		}
		//
		this.indEditavelSubcat = true;
	}

	public void removerMpSubcategoriaX() {
		//
		try {
			this.mpCategoria.getMpSubcategorias().remove(this.mpSubcategoria);
			
			this.mpSubcategoriaExcluidaList.add(this.mpSubcategoria);
			
			MpFacesUtil.addInfoMessage("Subcategoria... " + this.mpSubcategoria.getDescricao()
																	+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpSubcategoria() {
		//
		this.indEditavelSubcat = false;
		
		this.mpSubcategoria = new MpCategoria();
	}			

	public void fecharMpSubcategoria() {
		//
		if (this.txtModoSubcategoriaDialog.equals("Novo"))
			this.mpCategoria.getMpSubcategorias().remove(this.mpSubcategoria);
	}			
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpCategoria = this.mpCategorias.porNavegacao("mpFirst", " ", " "); 
		if (null == this.mpCategoria) {
//			System.out.println("MpCadastroCategoriaBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
		else {
			this.mpCategoriaPai = this.mpCategoria.getMpCategoriaPai();
			
			this.carregarMpCategorias();
		}
//			System.out.println("MpCadastroCategoriaBean.mpFirst() ( Entrou 0001 = " +
//															this.mpCategoria.getDescricao());
		//
		this.txtModoTela = "( Início )";
		//
		this.mpComplementaNavegacao();
	}
	
	public void mpPrev() {
		//
		if (null == this.mpCategoria.getDescricao()) return;
		//
		this.setMpCategoriaAnt(this.mpCategoria);
		//
		this.mpCategoria = this.mpCategorias.porNavegacao("mpPrev",
													mpCategoria.getMpTipoProduto().getDescricao(),
													mpCategoria.getDescricao());
		if (null == this.mpCategoria) {
			this.mpCategoria = this.mpCategoriaAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		
		this.carregarMpCategorias();
		//
		this.mpComplementaNavegacao();
	}

	public void mpNew() {
		//
		this.setMpCategoriaAnt(this.mpCategoria);
		
		this.mpCategoria = new MpCategoria();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpCategoria.getId()) return;
		//
		this.setMpCategoriaAnt(this.mpCategoria);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpCategoria.getId()) return;
		//
		try {
			this.mpCategorias.remover(mpCategoria);
			
			MpFacesUtil.addInfoMessage("Sistema Configuração... " + 
									this.mpCategoria.getDescricao()	+ " excluído com sucesso.");
			//
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

		this.mpCategoriaAnt = this.mpCategoria;
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
		this.mpCategoria = this.mpCategoriaAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpCategoria.getDescricao()) return;
		//
		this.setMpCategoriaAnt(this.mpCategoria);
		//
		this.mpCategoria = this.mpCategorias.porNavegacao("mpNext",
														mpCategoria.getMpTipoProduto().getDescricao(),
														mpCategoria.getDescricao());
		if (null == this.mpCategoria) {
			this.mpCategoria = this.mpCategoriaAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		
		this.carregarMpCategorias();
		//
		this.mpComplementaNavegacao();
	}
	
	public void mpEnd() {
		//
		this.mpCategoria = this.mpCategorias.porNavegacao("mpEnd", "ZZZZZ", "ZZZZZ"); 
		if (null == this.mpCategoria)
			this.limpar();
		else {
			this.mpCategoriaPai = this.mpCategoria.getMpCategoriaPai();
				
			this.carregarMpCategorias();
		}
//		else
//			System.out.println("MpCadastroCategoriaBean.mpEnd() ( Entrou 0001 = " +
//															this.mpCategoria.getDescricao());
		//
		this.txtModoTela = "( Fim )";
		//
		this.mpComplementaNavegacao();
	}
	
	public void mpClone() {
		//
		if (null == this.mpCategoria.getId()) return;

		try {
			this.setMpCategoriaAnt(this.mpCategoria);

			this.mpCategoria = (MpCategoria) this.mpCategoria.clone();
			//
			this.mpCategoria.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	public void mpComplementaNavegacao() {
		//
		if (this.mpCategoria.getMpSubcategorias().isEmpty())
			this.mpCategoria.setMpSubcategorias(this.mpCategorias.mpSubCategoriasDe(this.mpCategoria));
	}

	private void limpar() {
		//
		this.mpCategoria = new MpCategoria();
	
		this.mpCategoria.setTenantId(mpSeguranca.capturaTenantId());		
		//
		this.mpCategoria.setDescricao("");
		this.mpCategoria.setMpSubcategorias(new ArrayList<MpCategoria>());
		//
		this.mpTipoProduto = null;
		this.mpCategoriaPai = null;
		//
		this.mpSubcategoria = new MpCategoria();
		this.mpSubcategorias = new ArrayList<>();
	}
	
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) {
														this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { 
														this.indNaoEditavel = indNaoEditavel; }
	public boolean getIndEditavelSubcat() { return indEditavelSubcat; }
	public void setIndEditavelSubcat(Boolean indEditavelSubcat) { 
														this.indEditavelSubcat = indEditavelSubcat; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public String getTxtModoSubcategoriaDialog() { return txtModoSubcategoriaDialog; }
	public void setTxtModoSubcategoriaDialog(String txtModoSubcategoriaDialog) {
										this.txtModoSubcategoriaDialog = txtModoSubcategoriaDialog; }
	
	public MpCategoria getMpCategoria() { return mpCategoria; }
	public void setMpCategoria(MpCategoria mpCategoria) {
		this.mpCategoria = mpCategoria;		
		
		if (this.mpCategoria != null) {
			this.mpCategoriaPai = this.mpCategoria.getMpCategoriaPai();
		}
	}

	public MpCategoria getMpCategoriaAnt() { return mpCategoriaAnt; }
	public void setMpCategoriaAnt(MpCategoria mpCategoriaAnt) {
		try {
			this.mpCategoriaAnt = (MpCategoria) this.mpCategoria.clone();
			this.mpCategoriaAnt.setId(this.mpCategoria.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public List<MpCategoria> getMpCategoriasRaizes() { return mpCategoriasRaizes; }

	@NotNull
	public MpCategoria getMpCategoriaPai() { return mpCategoriaPai; }
	public void setMpCategoriaPai(MpCategoria mpCategoriaPai) {	this.mpCategoriaPai = mpCategoriaPai; }
	public List<MpCategoria> getMpSubcategorias() {	return mpSubcategorias; }
	
	public MpCategoria getMpSubcategoria() { return mpSubcategoria; }
	public void setMpSubcategoria(MpCategoria mpSubcategoria) { this.mpSubcategoria = mpSubcategoria; }
	
	public MpTipoProduto getMpTipoProduto() { return mpTipoProduto; }
	public void setMpTipoProduto(MpTipoProduto mpTipoProduto) {
		this.mpTipoProduto = mpTipoProduto;		

		if (this.mpTipoProduto != null) {
			this.mpCategoriasRaizes = mpCategorias.raizes(mpTipoProduto);
		
			if (this.mpCategoriaPai != null)
				this.carregarMpSubcategorias();
			//
		}
	}
	public List<MpTipoProduto> getMpTipoProdutoList() { return mpTipoProdutoList; }
	
	public boolean isEditando() { return this.mpCategoria.getId() != null; }

}