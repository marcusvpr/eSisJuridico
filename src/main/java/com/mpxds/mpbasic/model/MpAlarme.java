package com.mpxds.mpbasic.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="mp_alarme", uniqueConstraints = @UniqueConstraint(columnNames = "hora_movimento"))
public class MpAlarme extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date horaMovimento;  
	private Boolean indDomingo;
	private Boolean indSegunda;
	private Boolean indTerca;
	private Boolean indQuarta;
	private Boolean indQuinta;
	private Boolean indSexta;
	private Boolean indSabado;
	private Boolean indSemanalmente;
	private String nome;
	
	private MpAlerta mpAlerta;
	
	// ----------
	
	public MpAlarme() {
		super();
	}

  	public MpAlarme(Date horaMovimento
             , Boolean indDomingo
             , Boolean indSegunda
             , Boolean indTerca
             , Boolean indQuarta
             , Boolean indQuinta
             , Boolean indSexta
             , Boolean indSabado
             , Boolean indSemanalmente
             , String nome
             , Boolean indAtivo
             , MpAlerta mpAlerta
             ) {
  		this.horaMovimento = horaMovimento;
  		this.indDomingo = indDomingo;
  		this.indSegunda = indSegunda;
  		this.indTerca = indTerca;
  		this.indQuarta = indQuarta;
  		this.indQuinta = indQuinta;
  		this.indSexta = indSexta;
  		this.indSabado = indSabado;
  		this.indSemanalmente = indSemanalmente;
  		this.nome = nome;
  		this.mpAlerta = mpAlerta;
  	}
 
	@Temporal(TemporalType.TIME)
    @Column(name = "hora_movimento", unique = true, nullable = false)
  	public Date getHoraMovimento() { return this.horaMovimento; }
  	public void setHoraMovimento(Date newHoraMovimento) { this.horaMovimento = newHoraMovimento; }
	   	
  	@Column(name = "ind_domingo", nullable = true)
  	public Boolean getIndDomingo() { return this.indDomingo; }
  	public void setIndDomingo(Boolean newIndDomingo) { this.indDomingo = newIndDomingo; }
  	   	
	@Column(name = "ind_segunda", nullable = true)
	public Boolean getIndSegunda() { return this.indSegunda; }
	public void setIndSegunda(Boolean newIndSegunda) { this.indSegunda = newIndSegunda; }
	   	
	@Column(name = "ind_terca", nullable = true)
	public Boolean getIndTerca() { return this.indTerca; }
	public void setIndTerca(Boolean newIndTerca) { this.indTerca = newIndTerca; }
   	
	@Column(name = "ind_quarta", nullable = true)
	public Boolean getIndQuarta() { return this.indQuarta; }
	public void setIndQuarta(Boolean newIndQuarta) { this.indQuarta = newIndQuarta; }
	   	
	@Column(name = "ind_quinta", nullable = true)
	public Boolean getIndQuinta() { return this.indQuinta; }
	public void setIndQuinta(Boolean newIndQuinta) { this.indQuinta = newIndQuinta; }
   	
	@Column(name = "ind_sexta", nullable = true)
	public Boolean getIndSexta() { return this.indSexta; }
	public void setIndSexta(Boolean newIndSexta) { this.indSexta = newIndSexta; }
	
	@Column(name = "ind_sabado", nullable = true)
	public Boolean getIndSabado() { return this.indSabado; }
	public void setIndSabado(Boolean newIndSabado) { this.indSabado = newIndSabado; }
  	
	@Column(name = "ind_semanalmente", nullable = true)
	public Boolean getIndSemanalmente() { return this.indSemanalmente; }
	public void setIndSemanalmente(Boolean newIndSemanalmente) {
		 												this.indSemanalmente = newIndSemanalmente; }
  	
	@Column(nullable = true, length = 150)
	public String getNome() { return this.nome; }
	public void setNome(String newNome) { this.nome = newNome; }
	
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
