package com.mpxds.mpbasic.model.engreq;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.model.enums.engreq.MpCategoriaRNF;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpPrioridade;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;

@Entity
@Audited
@AuditTable(value="mp_er_requisito_nao_funcional_")
@Table(name = "mp_er_requisito_nao_funcional")
public class MpRequisitoNaoFuncional extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private MpModulo mpModulo;
	private MpFuncionalidade mpFuncionalidade;
	
	private String idRNF;
	private String descricao;
	private BigDecimal numHoraEsforco = BigDecimal.ONE;
	private String associacao; // RF0003 RF0005 -> separados por ESPAÇOS !
	
	private MpCategoriaRNF mpCategoriaRNF;
	private MpPrioridade mpPrioridade;
	private MpComplexibilidade mpComplexibilidade;
	private MpStatusRequisito mpStatusRequisito;
	
	private MpProjeto mpProjeto;
	
	// ---

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpModuloId")
	public MpModulo getMpModulo() { return mpModulo; }
	public void setMpModulo(MpModulo mpModulo) { this.mpModulo = mpModulo; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpFuncionalidadeId")
	public MpFuncionalidade getMpFuncionalidade() { return mpFuncionalidade; }
	public void setMpFuncionalidade(MpFuncionalidade mpFuncionalidade) { 
														this.mpFuncionalidade = mpFuncionalidade; }
	
	@Column(nullable = false, length = 10)
	public String getIdRNF() { return idRNF; }
	public void setIdRNF(String idRNF) { this.idRNF = idRNF; }
	
	@Lob
	@Column(nullable = false, length = 10000)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Column(name = "num_Hora_Esforco", nullable = false, precision = 5, scale = 2)
	public BigDecimal getNumHoraEsforco() { return numHoraEsforco; }
	public void setNumHoraEsforco(BigDecimal numHoraEsforco) { 
															this.numHoraEsforco = numHoraEsforco; }
	
	@Column(nullable = true, length = 250)
	public String getAssociacao() { return associacao; }
	public void setAssociacao(String associacao) { this.associacao = associacao; }

	@NotNull(message = "Por favor, informe a Categoria")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpCategoriaRNF getMpCategoriaRNF() { return mpCategoriaRNF; }
	public void setMpCategoriaRNF(MpCategoriaRNF mpCategoriaRNF) { 
															this.mpCategoriaRNF = mpCategoriaRNF; }

	@NotNull(message = "Por favor, informe a Prioridade")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpPrioridade getMpPrioridade() { return mpPrioridade; }
	public void setMpPrioridade(MpPrioridade mpPrioridade) { this.mpPrioridade = mpPrioridade; }

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