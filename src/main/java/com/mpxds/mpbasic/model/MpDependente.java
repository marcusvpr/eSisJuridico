package com.mpxds.mpbasic.model;

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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_dependente")
public class MpDependente extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String numero; // Tam. = 1 Rever para Tam. = 2 (06/10) ;
	private String nome;
	private String email;
	private String observacao;
	private Date dataNascimento;

	private MpStatus mpStatus;
	private MpSexo mpSexo;
	private MpCliente mpCliente;

	// ---
	
	@NotBlank(message = "Por favor, informe o NÚMERO")
	@Column(nullable = false, length = 1)
	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }

	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 150) 
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Column(nullable = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	//@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
	
	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	
	@NotNull(message = "Por favor, informe o SEXO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }

	@ManyToOne
	@JoinColumn(name = "mpCliente_id", nullable = false)
	public MpCliente getMpCliente() { return mpCliente; }
	public void setMpCliente(MpCliente mpCliente) { this.mpCliente = mpCliente; }

}