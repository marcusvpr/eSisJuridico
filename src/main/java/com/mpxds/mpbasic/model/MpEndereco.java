package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpEstadoUF;

@Entity
@Table(name = "mp_endereco")
public class MpEndereco extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String cep;

	private MpEstadoUF mpEstadoUF; 
	private MpCliente mpCliente;
	
	// ---
	
	@NotBlank(message = "Por favor, informe o LOGRADOURO")
	@Column(nullable = false, length = 150)
	public String getLogradouro() { return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

	@NotBlank(message = "Por favor, informe o NÚMERO")
	@Column(nullable = false, length = 20)
	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }

	@Column(nullable = true, length = 150)
	public String getComplemento() { return complemento; }
	public void setComplemento(String complemento) { this.complemento = complemento; }

	@NotBlank(message = "Por favor, informe a CIDADE")
	@Column(nullable = false, length = 60)
	public String getCidade() { return cidade; }
	public void setCidade(String cidade) { this.cidade = cidade; }

	@Column(nullable = true, length = 60)
	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 2)
	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }

	@Column(nullable = true, length = 9)
	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }

	@ManyToOne
	@JoinColumn(name = "mpCliente_id", nullable = false)
	public MpCliente getMpCliente() { return mpCliente; }
	public void setMpCliente(MpCliente mpCliente) { this.mpCliente = mpCliente; }

}