package com.mpxds.mpbasic.model.engreq;

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
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;

@Entity
@Audited
@AuditTable(value="mp_er_funcionalidade_")
@Table(name = "mp_er_funcionalidade")
public class MpFuncionalidade extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private MpModulo mpModulo;

	private String descricao;
	
	private MpStatusRequisito mpStatusRequisito;
	
	private MpProjeto mpProjeto;
	
	// ---

	@NotNull(message = "Por favor, informe o MÓDULO")
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpModuloId")
	public MpModulo getMpModulo() { return mpModulo; }
	public void setMpModulo(MpModulo mpModulo) { this.mpModulo = mpModulo; }
	
	@Lob
	@Column(nullable = false, length = 10000)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

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