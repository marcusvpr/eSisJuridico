package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@AuditTable(value="mp_dolar_")
@Table(name="mp_dolar",
uniqueConstraints = @UniqueConstraint(columnNames = "data_movimento"))
public class MpDolar extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataMovimento;  
	private BigDecimal valor;
	private String observacao;

	// ---
	
	public MpDolar() {
		super();
	}

  	public MpDolar(Date dataMovimento
             , BigDecimal valor
             , String observacao
             ) {
  		this.dataMovimento = dataMovimento;
  		this.valor = valor;
  		this.observacao = observacao;
  	}
 
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_movimento", unique = true, nullable = false, length = 10)
  	public Date getDataMovimento() { return this.dataMovimento; }
  	public void setDataMovimento(Date newDataMovimento) { this.dataMovimento = newDataMovimento; }
  	
	@NotNull(message = "Valor é obrigatório")
	@Column(nullable = false, precision = 10, scale = 3)
	public BigDecimal getValor() { return this.valor; }
	public void setValor(BigDecimal newValor) { this.valor = newValor; }
  	
	@Column(nullable = true, length = 255)
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String newObservacao) { this.observacao = newObservacao; }
    
}
