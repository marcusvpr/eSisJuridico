package com.mpxds.mpbasic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.enums.MpTipoJornada;

import java.util.Date;

import javax.persistence.Column;

@Entity
@Table(name="mp_turno", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class MpTurno extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpTipoJornada mpTipoJornada;
	private String descricao;
	private Date horaEntrada; 
	
	private Integer horaSegunda;
	private Integer horaTerca;
	private Integer horaQuarta;
	private Integer horaQuinta;
	private Integer horaSexta;
	private Integer horaSabado;
	private Integer horaDomingo;

	// ----------
	
	public MpTurno() {
		super();
	}

  	@NotNull(message = "Por favor, informe o TIPO JORNADA")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpTipo_jornada", nullable = false, length = 20)
  	public MpTipoJornada getMpTipoJornada() { return this.mpTipoJornada; }
  	public void setMpTipoJornada(MpTipoJornada newMpTipoJornada) {
  													this.mpTipoJornada = newMpTipoJornada; }
	  	
  	@NotNull(message = "Por favor, informe a DESCRIÇÂO")
  	@Column(nullable = false, length = 150)
  	public String getDescricao() { return this.descricao; }
  	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }
 
	@Temporal(TemporalType.TIME)
    @Column(name = "hora_entrada", nullable = false)
  	public Date getHoraEntrada() { return this.horaEntrada; }
  	public void setHoraEntrada(Date newHoraEntrada) { this.horaEntrada = newHoraEntrada; }
  	 
  	@Column(name = "hora_segunda", nullable = true)
  	public Integer getHoraSegunda() { return this.horaSegunda; }
  	public void setHoraSegunda(Integer newHoraSegunda) { this.horaSegunda = newHoraSegunda; }
  	
  	@Column(name = "hora_terca", nullable = true)
  	public Integer getHoraTerca() { return this.horaTerca; }
  	public void setHoraTerca(Integer newHoraTerca) { this.horaTerca = newHoraTerca; }
  	
  	@Column(name = "hora_quarta", nullable = true)
  	public Integer getHoraQuarta() { return this.horaQuarta; }
  	public void setHoraQuarta(Integer newHoraQuarta) { this.horaQuarta = newHoraQuarta; }
  	
  	@Column(name = "hora_quinta", nullable = true)
  	public Integer getHoraQuinta() { return this.horaQuinta; }
  	public void setHoraQuinta(Integer newHoraQuinta) { this.horaQuinta = newHoraQuinta; }
  	
  	@Column(name = "hora_sexta", nullable = true)
  	public Integer getHoraSexta() { return this.horaSexta; }
  	public void setHoraSexta(Integer newHoraSexta) { this.horaSexta = newHoraSexta; }
  	
  	@Column(name = "hora_sabado", nullable = true)
  	public Integer getHoraSabado() { return this.horaSabado; }
  	public void setHoraSabado(Integer newHoraSabado) { this.horaSabado = newHoraSabado; }
  	
  	@Column(name = "hora_domingo", nullable = true)
  	public Integer getHoraDomingo() { return this.horaDomingo; }
  	public void setHoraDomingo(Integer newHoraDomingo) { this.horaDomingo = newHoraDomingo; }
  	
  	// ---
  	
	@Transient
	public Integer calcularValorTotal() {
		//
		Integer valorTotal = 0;
		
		if (null == this.horaSegunda) assert(true);
		else valorTotal = valorTotal + this.horaSegunda;
		if (null == this.horaTerca) assert(true);
		else valorTotal = valorTotal + this.horaTerca;
		if (null == this.horaQuarta) assert(true);
		else valorTotal = valorTotal + this.horaQuarta;
		if (null == this.horaQuinta) assert(true);
		else valorTotal = valorTotal + this.horaQuinta;
		if (null == this.horaSexta) assert(true);
		else valorTotal = valorTotal + this.horaSexta;
		if (null == this.horaSabado) assert(true);
		else valorTotal = valorTotal + this.horaSabado;
		if (null == this.horaDomingo) assert(true);
		else valorTotal = valorTotal + this.horaDomingo;
		//
		return valorTotal;
	}
	
}
