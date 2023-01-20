package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpFormaPagamento;
import com.mpxds.mpbasic.model.MpItemPedido;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.repository.MpContatos;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpPedidoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.validation.MpSKU;

@Named
@ViewScoped
public class MpCadastroPedidoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpPedidos mpPedidos;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpContatos mpContatos;
	
	@Inject
	private MpProdutos mpProdutos;
	
	@Inject
	private MpPedidoService mpPedidoService;
	
	// ---

	private String sku;
	
	@Produces
	@MpPedidoEdicao
	private MpPedido mpPedido;
	private MpPedido mpPedidoAnt;
	
	private List<MpUsuarioTenant> mpVendedores;
	
	private MpProduto mpProdutoLinhaEditavel;
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// ---
	
	public MpCadastroPedidoBean() {
		if (null == this.mpPedido)
			limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpPedido) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPedido.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPedidoAnt(this.mpPedido);		
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		//		
		this.mpVendedores = this.mpUsuarioTenants.mpVendedores();
		
//		this.mpPedido.adicionarItemVazio();
		
		this.recalcularMpPedido();
		//
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
	}
	
	public void mpContatoSelecionado(SelectEvent event) {
		mpPedido.setMpContato((MpContato) event.getObject());
	}
	
	private void limpar() {
		mpPedido = new MpPedido();
		
		mpPedido.setMpEnderecoLocal(new MpEnderecoLocal());
	}
	
	public void mpPedidoAlterado(@Observes MpPedidoAlteradoEvent event) {
		this.mpPedido = event.getMpPedido();
	}
	
	public void salvar() {
		//
		this.mpPedido = this.mpPedidoService.salvar(this.mpPedido);
		//
		MpFacesUtil.addInfoMessage("MpPedido salvo com sucesso!");
	}
	
	public void recalcularMpPedido() {
		//
		if (this.mpPedido != null)
			this.mpPedido.recalcularValorTotal();
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
		MpItemPedido mpItem = this.mpPedido.getMpItens().get(0);
		
		if (this.mpProdutoLinhaEditavel != null) {
			if (this.existeItemComMpProduto(this.mpProdutoLinhaEditavel))
				MpFacesUtil.addErrorMessage("Já existe um item no Pedido com o Produto informado.");
			else {
				mpItem.setMpProduto(this.mpProdutoLinhaEditavel);
				mpItem.setValorUnitario(this.mpProdutoLinhaEditavel.getValorUnitario());
				
				this.mpPedido.adicionarItemVazio();
				this.mpProdutoLinhaEditavel = null;
				this.sku = null;
				
				this.mpPedido.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComMpProduto(MpProduto mpProduto) {
		boolean existeItem = false;
		
		for (MpItemPedido item : this.getMpPedido().getMpItens()) {
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
	
	public void atualizarQuantidade(MpItemPedido item, int linha) {
		if (item.getQuantidade().compareTo(BigDecimal.ONE) < 0) {
			if (linha == 0)
				item.setQuantidade(BigDecimal.ONE);
			else
				this.getMpPedido().getMpItens().remove(linha);
		}
		//
		this.mpPedido.recalcularValorTotal();
	}

	public void atualizarQuantidadeCortesia(MpItemPedido item, int linha) {
		//
		this.mpPedido.recalcularValorTotal();
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		try {
			this.mpPedido = this.mpPedidos.porNavegacao("mpFirst", sdf.parse("01/01/1900"));

			if (null == this.mpPedido) {
//				System.out.println("MpCadastroPedidoBean.mpFirst() ( Entrou 000");
				this.limpar();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
//			System.out.println("MpCadastroPedidoBean.mpFirst() ( Entrou 0001 = " +
//															this.mpPedido.getDataCriacao());
			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		if (null == this.mpPedido.getDataCriacao()) return;
		//
		this.setMpPedidoAnt(this.mpPedido);		
		//
		this.mpPedido = this.mpPedidos.porNavegacao("mpPrev", mpPedido.getDataCriacao());
		if (null == this.mpPedido) {
			this.mpPedido = this.mpPedidoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpPedidoAnt(this.mpPedido);		
		
		this.mpPedido = new MpPedido();
		this.mpPedido.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpPedido.adicionarItemVazio();
		//
		MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal(); 
		mpEnderecoLocal.setCep("");
		
		this.mpPedido.setMpEnderecoLocal(mpEnderecoLocal);
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpPedido.getId()) return;
		//
		this.setMpPedidoAnt(this.mpPedido);		
		
		this.mpPedido.adicionarItemVazio();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPedido.getId()) return;
		//
		try {
			this.mpPedidos.remover(mpPedido);
			
			MpFacesUtil.addInfoMessage("Pedido... " + this.mpPedido.getDataCriacao()
																	+ " excluído com sucesso.");
			
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			//
			this.mpPedido.removerItemVazio();

			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}
		
		this.setMpPedidoAnt(this.mpPedido);		
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
		this.mpPedido.removerItemVazio();
		//
		this.mpPedido = this.mpPedidoAnt;
		
		this.indEditavel = true;
		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpPedido.getDataCriacao()) return;
		//
		this.setMpPedidoAnt(this.mpPedido);		
		//
		this.mpPedido = this.mpPedidos.porNavegacao("mpNext", mpPedido.getDataCriacao());
		if (null == this.mpPedido) {
			this.mpPedido = this.mpPedidoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		try {
			this.mpPedido = this.mpPedidos.porNavegacao("mpEnd", sdf.parse("01/01/2099"));

			if (null == this.mpPedido)
				this.limpar();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
//		else
//			System.out.println("MpCadastroPedidoBean.mpEnd() ( Entrou 0001 = " +
//															this.mpPedido.getDataCriacao());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPedido.getId()) return;

		try {
			this.setMpPedidoAnt(this.mpPedido);		

			this.mpPedido = (MpPedido) this.mpPedido.clone();
			//
			this.mpPedido.setId(null);
			
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

	public MpPedido getMpPedido() { return mpPedido; }
	public void setMpPedido(MpPedido mpPedido) { this.mpPedido = mpPedido; }

	public MpPedido getMpPedidoAnt() { return mpPedidoAnt; }
	public void setMpPedidoAnt(MpPedido mpPedidoAnt) {
		//
		try {
			this.mpPedidoAnt = (MpPedido) this.mpPedido.clone();
			this.mpPedidoAnt.setId(this.mpPedido.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public List<MpUsuarioTenant> getMpVendedores() { return mpVendedores; }
	
	public boolean isEditando() { return this.mpPedido.getId() != null; }

	public MpProduto getMpProdutoLinhaEditavel() { return mpProdutoLinhaEditavel; }
	public void setMpProdutoLinhaEditavel(MpProduto mpProdutoLinhaEditavel) {
												this.mpProdutoLinhaEditavel = mpProdutoLinhaEditavel; }

	@MpSKU
	public String getSku() { return sku; }
	public void setSku(String sku) { this.sku = sku; }
	
	@NotBlank
	public String getNomeMpContato() {
		return mpPedido.getMpContato() == null ? null : mpPedido.getMpContato().getNomeRazaoSocial(); }
	public void setNomeMpContato(String nome) { }

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpUf(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }

}