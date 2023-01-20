package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.mpxds.mpbasic.model.enums.MpStatusCompra;

@Entity
@Table(name = "mp_compra")
public class MpCompra extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacao;
	private String observacao;
	private Boolean indOrcamento = false;
	private Boolean indFinalizada = false;
	
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private MpStatusCompra mpStatus = MpStatusCompra.ORCAMENTO;

	private MpContato mpContato;
	
	private List<MpItemCompra> mpItens = new ArrayList<MpItemCompra>();
	
	// ------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

	//Column(columnDefinition = "text")
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Column(name="ind_orcamento", nullable=true)
	public Boolean getIndOrcamento() { return indOrcamento; }
	public void setIndOrcamento(Boolean indOrcamento) { this.indOrcamento = indOrcamento; }

	@Column(name="ind_finalizada", nullable=true)
	public Boolean getIndFinalizada() { return indFinalizada; }
	public void setIndFinalizada(Boolean indFinalizada) { this.indFinalizada = indFinalizada; }

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {	return valorTotal; }
	public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public MpStatusCompra getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatusCompra mpStatus) { this.mpStatus = mpStatus; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpContato_id", nullable = false)
	public MpContato getMpContato() { return mpContato; }
	public void setMpContato(MpContato mpContato) { this.mpContato = mpContato; }

	@OneToMany(mappedBy = "mpCompra", cascade = CascadeType.ALL, orphanRemoval = true,
																			fetch = FetchType.LAZY)
	public List<MpItemCompra> getMpItens() { return mpItens; }
	public void setMpItens(List<MpItemCompra> mpItens) { this.mpItens = mpItens; }

	@Transient
	public boolean isNovo() { return getId() == null; }
	
	@Transient
	public boolean isExistente() { return !isNovo(); }

	@Transient
	public BigDecimal getValorSubtotal() { return this.getValorTotal(); }
		
	public void recalcularValorTotal() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
		for (MpItemCompra item : this.getMpItens()) {
			if (item.getMpProduto() != null && item.getMpProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}
		}
		//
		this.setValorTotal(total);
	}

	public void adicionarItemVazio() {
		//
		MpProduto mpProduto = new MpProduto();

		mpProduto.setTenantId(this.getTenantId());
		//
		MpItemCompra item = new MpItemCompra();

		item.setTenantId(this.getTenantId());
		item.setMpProduto(mpProduto);
		item.setMpCompra(this);
		//
		this.getMpItens().add(0, item);
	}

	@Transient
	public boolean isOrcamento() { return MpStatusCompra.ORCAMENTO.equals(this.getMpStatus()); }

	public void removerItemVazio() {
		//
		if (this.getMpItens().size() > 0) {
			//
			MpItemCompra mpPrimeiroItem = this.getMpItens().get(0);
		
			if (mpPrimeiroItem != null && mpPrimeiroItem.getMpProduto().getId() == null)
				this.getMpItens().remove(0);
		}
	}

	@Transient
	public boolean isFinalizada() { return this.indFinalizada; }
	
	@Transient
	public boolean isValorTotalNegativo() {
								return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0; }

	@Transient
	public boolean isEmitido() { return MpStatusCompra.EMITIDA.equals(this.getMpStatus()); }

	@Transient
	public boolean isNaoEmissivel() { return !this.isEmissivel(); }

	@Transient
	public boolean isEmissivel() { return this.isExistente() && this.isOrcamento(); }

	@Transient
	public boolean isCancelavel() { return this.isExistente() && !this.isCancelado(); }

	@Transient
	private boolean isCancelado() { return MpStatusCompra.CANCELADA.equals(this.getMpStatus()); }

	@Transient
	public boolean isNaoCancelavel() { return !this.isCancelavel(); }

	@Transient
	public boolean isAlteravel() { return this.isOrcamento(); }
	
	@Transient
	public boolean isNaoAlteravel() { return !this.isAlteravel(); }
	
	@Transient
	public boolean isNaoEnviavelPorEmail() { return this.isNovo() || this.isCancelado(); }
	
	
}