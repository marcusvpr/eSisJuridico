package com.mpxds.mpbasic.model.engreq;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;

@Entity
@Audited
@AuditTable(value="mp_er_regra_negocio_")
@Table(name = "mp_er_regra_negocio")
public class MpRegraNegocio extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String idRN;
	private String descricao;
	private BigDecimal numHoraEsforco = BigDecimal.ONE;
	private String dependencia; // RF0003 RF0005 -> separados por ESPAÇOS !
	
	private MpComplexibilidade mpComplexibilidade;
	private MpStatusRequisito mpStatusRequisito;
	
	private MpProjeto mpProjeto;
	
	// ---
	
	@Column(nullable = false, length = 10)
	public String getIdRN() { return idRN; }
	public void setIdRN(String idRN) { this.idRN = idRN; }
	
	@Lob
	@Column(nullable = false, length = 10000)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Column(name = "num_Hora_Esforco", nullable = false, precision = 5, scale = 2)
	public BigDecimal getNumHoraEsforco() { return numHoraEsforco; }
	public void setNumHoraEsforco(BigDecimal numHoraEsforco) { this.numHoraEsforco = numHoraEsforco; }
	
	@Column(nullable = true, length = 250)
	public String getDependencia() { return dependencia; }
	public void setDependencia(String dependencia) { this.dependencia = dependencia; }

	@NotNull(message = "Por favor, informe a Complexibilidade")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpComplexibilidade getMpComplexibilidade() { return mpComplexibilidade; }
	public void setMpComplexibilidade(MpComplexibilidade mpComplexibilidade) { 
																	this.mpComplexibilidade = mpComplexibilidade; }

	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpStatusRequisito getMpStatusRequisito() { return mpStatusRequisito; }
	public void setMpStatusRequisito(MpStatusRequisito mpStatusRequisito) { 
																	this.mpStatusRequisito = mpStatusRequisito; }
	
	@ManyToOne
	@JoinColumn(name = "mpProjeto_id", nullable = false)
	public MpProjeto getMpProjeto() { return mpProjeto; }
	public void setMpProjeto(MpProjeto mpProjeto) { this.mpProjeto = mpProjeto; }
	
}