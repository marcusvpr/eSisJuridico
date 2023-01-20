package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.model.enums.MpFormaPagamento;
import com.mpxds.mpbasic.model.MpItemCompra;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.repository.MpContatos;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpCompraService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.validation.MpSKU;

@Named
@ViewScoped
public class MpCadastroCompraBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpCompras mpCompras;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpContatos mpContatos;
	
	@Inject
	private MpProdutos mpProdutos;
	
	@Inject
	private MpCompraService mpCompraService;
	
	// ---

	private String sku;
	
	@Produces
	@MpCompraEdicao
	private MpCompra mpCompra;
	private MpCompra mpCompraAnt;
	
	private List<MpUsuarioTenant> mpVendedores;
	
	private MpProduto mpProdutoLinhaEditavel;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// ---
	
	public MpCadastroCompraBean() {
		if (null == this.mpCompra)
			limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpCompra) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpCompra.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpCompraAnt(this.mpCompra);		
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		//		
		this.mpVendedores = this.mpUsuarioTenants.mpVendedores();
		
//		this.mpCompra.adicionarItemVazio();
		
		this.recalcularMpCompra();
		//
	}
	
	public void mpContatoSelecionado(SelectEvent event) {
		mpCompra.setMpContato((MpContato) event.getObject());
	}
	
	private void limpar() {
		//
		mpCompra = new MpCompra();
	}
	
	public void mpCompraAlterado(@Observes MpCompraAlteradoEvent event) {
		//
		this.mpCompra = event.getMpCompra();
	}
	
	public void salvar() {
		//
		this.mpCompra = this.mpCompraService.salvar(this.mpCompra);
		//
		MpFacesUtil.addInfoMessage("MpCompra salvo com sucesso!");
	}
	
	public void recalcularMpCompra() {
		//
		if (this.mpCompra != null)
			this.mpCompra.recalcularValorTotal();
	}
	
	public void carregarMpProdutoPorSku() {
		//
		if (StringUtils.isNotEmpty(this.sku)) {
			this.mpProdutoLinhaEditavel = this.mpProdutos.porSku(this.sku);
			this.carregarMpProdutoLinhaEditavel();
		}
	}
	
	public void carregarMpProdutoLinhaEditavel() {
		//
		MpItemCompra mpItem = this.mpCompra.getMpItens().get(0);
		
		if (this.mpProdutoLinhaEditavel != null) {
			if (this.existeItemComMpProduto(this.mpProdutoLinhaEditavel))
				MpFacesUtil.addErrorMessage("Já existe um item no Compra com o Produto informado.");
			else {
				mpItem.setMpProduto(this.mpProdutoLinhaEditavel);
				mpItem.setValorUnitario(this.mpProdutoLinhaEditavel.getValorUnitario());
				
				this.mpCompra.adicionarItemVazio();
				this.mpProdutoLinhaEditavel = null;
				this.sku = null;
				
				this.mpCompra.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComMpProduto(MpProduto mpProduto) {
		boolean existeItem = false;
		
		for (MpItemCompra item : this.getMpCompra().getMpItens()) {
			if (mpProduto.equals(item.getMpProduto())) {
				existeItem = true;
				break;
			}
		}
		//
		return existeItem;
	}

	public List<MpProduto> completarMpProduto(String nome) {
												return this.mpProdutos.porNome(nome); }
	
	public void atualizarQuantidade(MpItemCompra item, int linha) {
		if (item.getQuantidade().compareTo(BigDecimal.ONE) < 0) {
			if (linha == 0)
				item.setQuantidade(BigDecimal.ONE);
			else
				this.getMpCompra().getMpItens().remove(linha);
		}
		//
		this.mpCompra.recalcularValorTotal();
	}

	public void atualizarQuantidadeCortesia(MpItemCompra item, int linha) {
		//
		this.mpCompra.recalcularValorTotal();
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		try {
			this.mpCompra = this.mpCompras.porNavegacao("mpFirst", sdf.parse("01/01/1900"));

			if (null == this.mpCompra) {
//				System.out.println("MpCadastroCompraBean.mpFirst() ( Entrou 000");
				this.limpar();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
//			System.out.println("MpCadastroCompraBean.mpFirst() ( Entrou 0001 = " +
//															this.mpCompra.getDataCriacao());
			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		if (null == this.mpCompra.getDataCriacao()) return;
		//
		this.setMpCompraAnt(this.mpCompra);		
		//
		this.mpCompra = this.mpCompras.porNavegacao("mpPrev", mpCompra.getDataCriacao());
		if (null == this.mpCompra) {
			this.mpCompra = this.mpCompraAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpCompraAnt(this.mpCompra);		
		
		this.mpCompra = new MpCompra();
		this.mpCompra.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpCompra.adicionarItemVazio();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpCompra.getId()) return;
		//
		this.setMpCompraAnt(this.mpCompra);		
		
		this.mpCompra.adicionarItemVazio();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpCompra.getId()) return;
		//
		try {
			this.mpCompras.remover(mpCompra);
			
			MpFacesUtil.addInfoMessage("Compra... " + this.mpCompra.getDataCriacao()
																	+ " excluído com sucesso.");
			
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			//
			this.mpCompra.removerItemVazio();

			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}
		
		this.setMpCompraAnt(this.mpCompra);		
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
		this.mpCompra.removerItemVazio();
		//
		this.mpCompra = this.mpCompraAnt;
		
		this.indEditavel = true;
		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpCompra.getDataCriacao()) return;
		//
		this.setMpCompraAnt(this.mpCompra);		
		//
		this.mpCompra = this.mpCompras.porNavegacao("mpNext", mpCompra.getDataCriacao());
		if (null == this.mpCompra) {
			this.mpCompra = this.mpCompraAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		try {
			this.mpCompra = this.mpCompras.porNavegacao("mpEnd", sdf.parse("01/01/2099"));

			if (null == this.mpCompra)
				this.limpar();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
//		else
//			System.out.println("MpCadastroCompraBean.mpEnd() ( Entrou 0001 = " +
//															this.mpCompra.getDataCriacao());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpCompra.getId()) return;

		try {
			this.setMpCompraAnt(this.mpCompra);		

			this.mpCompra = (MpCompra) this.mpCompra.clone();
			//
			this.mpCompra.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
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
	
	// ---
	
	public MpFormaPagamento[] getMpFormasPagamento() { return MpFormaPagamento.values(); }
	
	public List<MpContato> completarMpContato(String nome) { return this.mpContatos.porNomeRazaoSocialList(nome); }

	public MpCompra getMpCompra() { return mpCompra; }
	public void setMpCompra(MpCompra mpCompra) { this.mpCompra = mpCompra; }

	public MpCompra getMpCompraAnt() { return mpCompraAnt; }
	public void setMpCompraAnt(MpCompra mpCompraAnt) {
		//
		try {
			this.mpCompraAnt = (MpCompra) this.mpCompra.clone();
			this.mpCompraAnt.setId(this.mpCompra.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public List<MpUsuarioTenant> getMpVendedores() { return mpVendedores; }
	
	public boolean isEditando() { return this.mpCompra.getId() != null; }

	public MpProduto getMpProdutoLinhaEditavel() { return mpProdutoLinhaEditavel; }
	public void setMpProdutoLinhaEditavel(MpProduto mpProdutoLinhaEditavel) {
												this.mpProdutoLinhaEditavel = mpProdutoLinhaEditavel; }

	@MpSKU
	public String getSku() { return sku; }
	public void setSku(String sku) { this.sku = sku; }
	
	@NotBlank
	public String getNomeMpContato() {
		return mpCompra.getMpContato() == null ? null : mpCompra.getMpContato().getNomeRazaoSocial(); }
	public void setNomeMpContato(String nome) { }

}