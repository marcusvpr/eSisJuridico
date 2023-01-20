package com.mpxds.mpbasic.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.enums.engreq.MpCardinalidade;
import com.mpxds.mpbasic.model.enums.engreq.MpFormato;

@Entity
@Audited
@AuditTable(value="mp_item_objeto_")
@Table(name = "mp_item_objeto")
public class MpItemObjeto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	private BigDecimal tamanho = BigDecimal.ZERO;
	private Boolean indKey = false;
	
	private MpFormato mpFormato;
	private MpCardinalidade mpCardinalidade;

	private MpObjeto mpObjeto;

	// ---
	
	@Column(nullable = false)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	@Column(nullable = false)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Column(nullable = true, precision = 5, scale = 2)
	public BigDecimal getTamanho() { return this.tamanho; }
	public void setTamanho(BigDecimal newTamanho) { this.tamanho = newTamanho; }

	@Column(nullable = true)
	public Boolean getIndKey() { return this.indKey; }
	public void setIndKey(Boolean newIndKey) { this.indKey = newIndKey; }

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 50)
	public MpFormato getMpFormato() { return mpFormato; }
	public void setMpFormato(MpFormato mpFormato) {	this.mpFormato = mpFormato; }	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 50)
	public MpCardinalidade getMpCardinalidade() { return mpCardinalidade; }
	public void setMpCardinalidade(MpCardinalidade mpCardinalidade) { 
														this.mpCardinalidade = mpCardinalidade; }	
	
	@ManyToOne
	@JoinColumn(name = "mpObjeto_ID", nullable = false)
	public MpObjeto getMpObjeto() {	return mpObjeto; }
	public void setMpObjeto(MpObjeto mpObjeto) { this.mpObjeto = mpObjeto; }

}
