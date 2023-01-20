package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mpxds.mpbasic.model.enums.MpTipoMovimento;

@Entity
@Table(name = "mp_movimento_produto")
public class MpMovimentoProduto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dtHrMovimento;
	private BigDecimal quantidade;
	private String codigoBarra;
	private String codigoBarra2D;
	private String lote;
	private Date dataFabricacao;
	private Date dataValidade;
	private String observacao;
	
	private MpTipoMovimento mpTipoMovimento;
	private MpProduto mpProduto;

	// ---
	
	@NotNull(message = "Por favor, informe a Data/Hora")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dthr_movimento", nullable = false)
	public Date getDtHrMovimento() { return this.dtHrMovimento; }
	public void setDtHrMovimento(Date newDtHrMovimento) {
													this.dtHrMovimento = newDtHrMovimento; }
	
	@NotNull(message = "Por favor, informe o Tipo Movimento")
	@Enumerated(EnumType.STRING)
	@Column(name="mptipo_movimento", nullable = false, length = 20)
	public MpTipoMovimento getMpTipoMovimento() { return mpTipoMovimento; }
	public void setMpTipoMovimento(MpTipoMovimento mpTipoMovimento) {
													this.mpTipoMovimento = mpTipoMovimento;	}

	@NotNull(message = "Por favor, informe a Quantidade")
	public BigDecimal getQuantidade() { return quantidade; }
	public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
	
	@Size(max = 200)
	@Column(nullable = true, length = 200)
	public String getCodigoBarra() { return codigoBarra; }
	public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }
	
	@Size(max = 200)
	@Column(nullable = true, length = 200)
	public String getCodigoBarra2D() { return codigoBarra2D; }
	public void setCodigoBarra2D(String codigoBarra2D) { this.codigoBarra2D = codigoBarra2D; }

	@Size(max = 50)
	@Column(nullable = true, length = 50)
	public String getLote() { return lote; }
	public void setLote(String lote) { this.lote = lote; }

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataFabricacao() { return dataFabricacao; }
	public void setDataFabricacao(Date dataFabricacao) { this.dataFabricacao = dataFabricacao; }

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataValidade() { return dataValidade; }
	public void setDataValidade(Date dataValidade) { this.dataValidade = dataValidade; }

	@Column(nullable = true, length = 200) 
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@ManyToOne
	@JoinColumn(name = "mpProduto_id", nullable = false)
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }
	
	// ---
	
	@Transient
	public String getDtHrMovimentoSDF() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		//
		if (null == this.dtHrMovimento)
			return "null";
		else
			return sdf.format(this.dtHrMovimento);
	}

}
