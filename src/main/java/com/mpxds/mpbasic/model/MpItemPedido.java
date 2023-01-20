package com.mpxds.mpbasic.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mp_item_pedido")
public class MpItemPedido extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private BigDecimal quantidade = BigDecimal.ONE;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Boolean indCortesia = false;
	private Boolean indPrecoEspecial = false;

	private MpProduto mpProduto;
	private MpPedido mpPedido;

	// ---
	
	@Column(nullable = false)
	public BigDecimal getQuantidade() { return quantidade; }
	public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() { return valorUnitario; }
	public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

	@Column(name="ind_cortesia", nullable=true)
    public Boolean getIndCortesia() { return this.indCortesia; }
    public void setIndCortesia(Boolean newIndCortesia) { this.indCortesia = newIndCortesia; }

	@Column(name="ind_preco_especial", nullable=true)
    public Boolean getIndPrecoEspecial() { return this.indPrecoEspecial; }
    public void setIndPrecoEspecial(Boolean newIndPrecoEspecial) { this.indPrecoEspecial = newIndPrecoEspecial; }

	@ManyToOne
	@JoinColumn(name = "mpProduto_id", nullable = false)
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }

	@ManyToOne
	@JoinColumn(name = "mpPedido_id", nullable = false)
	public MpPedido getMpPedido() {	return mpPedido; }
	public void setMpPedido(MpPedido mpPedido) { this.mpPedido = mpPedido; }

	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(this.getQuantidade()); }
	
	@Transient
	public boolean isProdutoAssociado() {
		return this.getMpProduto() != null && this.getMpProduto().getId() != null; }
	
	@Transient
	public boolean isEstoqueSuficiente() {
		return this.getMpPedido().isEmitido() || this.getMpProduto().getId() == null  
			|| this.getMpProduto().getQuantidadeEstoque().compareTo(this.getQuantidade()) >= 0 ; 
	}
	
	@Transient
	public boolean isEstoqueInsuficiente() { return !this.isEstoqueSuficiente(); }

}
