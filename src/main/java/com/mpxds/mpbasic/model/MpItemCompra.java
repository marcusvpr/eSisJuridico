package com.mpxds.mpbasic.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mp_item_compra")
public class MpItemCompra extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private BigDecimal quantidade = BigDecimal.ONE;
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	private MpProduto mpProduto;
	private MpCompra mpCompra;

	// ---
	
	@Column(nullable = false)
	public BigDecimal getQuantidade() { return quantidade; }
	public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() { return valorUnitario; }
	public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

	@ManyToOne
	@JoinColumn(name = "mpProduto_id", nullable = false)
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }

	@ManyToOne
	@JoinColumn(name = "mpCompra_id", nullable = false)
	public MpCompra getMpCompra() {	return mpCompra; }
	public void setMpCompra(MpCompra mpCompra) { this.mpCompra = mpCompra; }

	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(this.getQuantidade()); }
	
	@Transient
	public boolean isProdutoAssociado() {
		return this.getMpProduto() != null && this.getMpProduto().getId() != null; }

}
