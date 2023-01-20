package com.mpxds.mpbasic.model.engreq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_er_sequencia_")
@Table(name = "mp_er_sequencia")
public class MpSequencia extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private Integer sequencia;
	
	private MpProjeto mpProjeto;
	
	// ---
			
	@Column(nullable = false, length = 50)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	@Column(nullable = false)
	public Integer getSequencia() { return sequencia; }
	public void setSequencia(Integer sequencia) { this.sequencia = sequencia; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpProjetoId")
	public MpProjeto getMpProjeto() { return mpProjeto; }
	public void setMpProjeto(MpProjeto mpProjeto) { this.mpProjeto = mpProjeto; }
	
}