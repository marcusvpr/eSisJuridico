package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.enums.MpFormaPagamento;
import com.mpxds.mpbasic.model.enums.MpStatusPedido;

@Entity
@Table(name = "mp_pedido")
public class MpPedido extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacao;
	private String observacao;
	private Date dataEntrega;
	
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private BigDecimal valorTroco = BigDecimal.ZERO;
	
	private MpStatusPedido mpStatus = MpStatusPedido.ORCAMENTO;
	private MpFormaPagamento mpFormaPagamento;
	private MpUsuarioTenant mpVendedor;
	private MpContato mpContato;
	private MpEnderecoLocal mpEnderecoLocal;
	
	private List<MpItemPedido> mpItens = new ArrayList<MpItemPedido>();
	
	// ------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

	//Column(columnDefinition = "text")
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false)
	public Date getDataEntrega() { return dataEntrega; }
	public void setDataEntrega(Date dataEntrega) { this.dataEntrega = dataEntrega; }

	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorFrete() { return valorFrete; }
	public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() { return valorDesconto; }
	public void setValorDesconto(BigDecimal valorDesconto) { this.valorDesconto = valorDesconto; }

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {	return valorTotal; }
	public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

	@NotNull
	@Column(name = "valor_troco", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTroco() {	return valorTroco; }
	public void setValorTroco(BigDecimal valorTroco) { this.valorTroco = valorTroco; }

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public MpStatusPedido getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatusPedido mpStatus) { this.mpStatus = mpStatus; }

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "mpForma_pagamento", nullable = false, length = 20)
	public MpFormaPagamento getMpFormaPagamento() {	return mpFormaPagamento; }
	public void setMpFormaPagamento(MpFormaPagamento mpFormaPagamento) {
														this.mpFormaPagamento = mpFormaPagamento; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpVendedor_id", nullable = false)
	public MpUsuarioTenant getMpVendedor() { return mpVendedor; }
	public void setMpVendedor(MpUsuarioTenant mpVendedor) { this.mpVendedor = mpVendedor; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpContato_id", nullable = false)
	public MpContato getMpContato() { return mpContato; }
	public void setMpContato(MpContato mpContato) { this.mpContato = mpContato; }

	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) {
													this.mpEnderecoLocal = mpEnderecoLocal; }

	@OneToMany(mappedBy = "mpPedido", cascade = CascadeType.ALL, orphanRemoval = true,
																			fetch = FetchType.LAZY)
	public List<MpItemPedido> getMpItens() { return mpItens; }
	public void setMpItens(List<MpItemPedido> mpItens) { this.mpItens = mpItens; }

	@Transient
	public boolean isNovo() { return getId() == null; }
	
	@Transient
	public boolean isExistente() { return !isNovo(); }
	
	@Transient
	public BigDecimal getValorSubtotal() {
		return this.getValorTotal().subtract(this.getValorFrete()).add(this.getValorDesconto()); }
	
	public void recalcularValorTotal() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
		
		for (MpItemPedido item : this.getMpItens()) {
			if (item.getMpProduto() != null && item.getMpProduto().getId() != null) {
				if (null == item.getIndCortesia()) item.setIndCortesia(false);
				if (item.getIndCortesia() == false)
					total = total.add(item.getValorTotal());
			}
		}
		//
		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		//
		if (this.isOrcamento()) {
			MpProduto mpProduto = new MpProduto();
			
			mpProduto.setTenantId(this.getTenantId());
			//
			MpItemPedido item = new MpItemPedido();
			
			item.setTenantId(this.getTenantId());
			item.setMpProduto(mpProduto);
			item.setMpPedido(this);
			//
			this.getMpItens().add(0, item);
		}
	}

	@Transient
	public boolean isOrcamento() { return MpStatusPedido.ORCAMENTO.equals(this.getMpStatus()); }

	public void removerItemVazio() {
		//
		if (this.getMpItens().size() > 0) {
			//
			MpItemPedido mpPrimeiroItem = this.getMpItens().get(0);
		
			if (mpPrimeiroItem != null && mpPrimeiroItem.getMpProduto().getId() == null)
				this.getMpItens().remove(0);
		}
	}

	@Transient
	public boolean isValorTotalNegativo() {
								return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0; }

	@Transient
	public boolean isEmitido() { return MpStatusPedido.EMITIDO.equals(this.getMpStatus()); }

	@Transient
	public boolean isNaoEmissivel() { return !this.isEmissivel(); }

	@Transient
	public boolean isEmissivel() { return this.isExistente() && this.isOrcamento(); }

	@Transient
	public boolean isCancelavel() { return this.isExistente() && !this.isCancelado(); }

	@Transient
	private boolean isCancelado() { return MpStatusPedido.CANCELADO.equals(this.getMpStatus()); }

	@Transient
	public boolean isNaoCancelavel() { return !this.isCancelavel(); }

	@Transient
	public boolean isAlteravel() { return this.isOrcamento(); }
	
	@Transient
	public boolean isNaoAlteravel() { return !this.isAlteravel(); }
	
	@Transient
	public boolean isNaoEnviavelPorEmail() { return this.isNovo() || this.isCancelado(); }

}