package com.mpxds.mpbasic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpCalendarioRepetir;

@Entity
@Table(name="mp_calendario", uniqueConstraints = @UniqueConstraint(columnNames = "data_Movimento"))
public class MpCalendario extends MpBaseEntity {
	private static final long serialVersionUID = 1L;

	private Date dataMovimento;  
	private Date dataFimMovimento;  
	private Boolean indDiaTodo;
	private Boolean indFeriado;
	private String descricao;
	private String observacao;

	private MpCalendarioRepetir mpCalendarioRepetir;
	private MpAlerta mpAlerta;
	
	// ----------
	
	public MpCalendario() {
		super();
	}

  	public MpCalendario(Date dataMovimento
  			 , Date dataFimMovimento
             , Boolean indDiaTodo
             , Boolean indFeriado
             , String descricao
             , String observacao
             , Boolean indAtivo
             , MpAlerta mpAlerta
             ) {
  		this.dataMovimento = dataMovimento;
  		this.dataFimMovimento = dataFimMovimento;
  		this.indDiaTodo = indDiaTodo;
  		this.indFeriado = indFeriado;
  		this.descricao = descricao;
  		this.observacao = observacao;
  		this.mpAlerta = mpAlerta;
  	}
 
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_movimento", unique = true, nullable = false)
  	public Date getDataMovimento() { return this.dataMovimento; }
  	public void setDataMovimento(Date newDataMovimento) { 
  														this.dataMovimento = newDataMovimento; }
  	 
  	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_fim_movimento", nullable = true)
  	public Date getDataFimMovimento() { return this.dataFimMovimento; }
  	public void setDataFimMovimento(Date newDataFimMovimento) {
  													this.dataFimMovimento = newDataFimMovimento; }
  	
	@Column(name = "ind_dia_todo", nullable = true)
	public Boolean getIndDiaTodo() { return this.indDiaTodo; }
	public void setIndDiaTodo(Boolean newIndDiaTodo) { this.indDiaTodo = newIndDiaTodo; }
  	
	@Column(name = "ind_feriado", nullable = true)
	public Boolean getIndFeriado() { return this.indFeriado; }
	public void setIndFeriado(Boolean newIndFeriado) { this.indFeriado = newIndFeriado; }
  	
	@NotBlank(message = "Por favor, informe a DESCRIÇÃO")
	@Column(nullable = false, length = 150)
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }
    
	@Column(nullable = true, length = 255)
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String newObservacao) { this.observacao = newObservacao; }
	
	@Enumerated(EnumType.STRING)
	@Column(name = "calendario_repetir", nullable = false, length = 20)
	public MpCalendarioRepetir getMpCalendarioRepetir() { return mpCalendarioRepetir; }
	public void setMpCalendarioRepetir(MpCalendarioRepetir mpCalendarioRepetir) {
											this.mpCalendarioRepetir = mpCalendarioRepetir;	}

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAlertaId")
	public MpAlerta getMpAlerta() {
		//
		if (null == this.mpAlerta)  
			this.mpAlerta = new MpAlerta(false, false, false, false, false, false, false);
		//
		return this.mpAlerta; 
	}
	public void setMpAlerta(MpAlerta newMpAlerta) { this.mpAlerta = newMpAlerta; }
	
}
