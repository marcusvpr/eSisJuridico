package com.mpxds.mpbasic.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mp_item_receita")
public class MpItemReceita extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private BigDecimal quantidade = BigDecimal.ONE;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private String dosagem;
	private String observacao;

	private MpProduto mpProduto;
	private MpReceita mpReceita;

	// ---
	
	@NotNull(message = "Por favor, informe a Quantidade")
	@Column(nullable = false, precision = 7, scale = 3)
	public BigDecimal getQuantidade() {	return quantidade; }
	public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() { return valorUnitario; }
	public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

	@NotNull(message = "Por favor, informe Dosagem")
	@Column(nullable = false, length = 255)
	public String getDosagem() { return dosagem; }
	public void setDosagem(String dosagem) { this.dosagem = dosagem; }

	@Column(nullable = true, length = 255)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@ManyToOne
	@JoinColumn(name = "mpProduto_id", nullable = false)
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }

	@ManyToOne
	@JoinColumn(name = "mpReceita_id", nullable = false)
	public MpReceita getMpReceita() { return mpReceita; }
	public void setMpReceita(MpReceita mpReceita) { this.mpReceita = mpReceita; }

	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(this.getQuantidade()); 	}
	
	@Transient
	public boolean isProdutoAssociado() {
		return this.getMpProduto() != null && this.getMpProduto().getId() != null; }

}
