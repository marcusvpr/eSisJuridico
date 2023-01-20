package com.mpxds.mpbasic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpEstadoUF;

@Embeddable
public class MpEnderecoLocal implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String logradouro;
	private String numero;
	private String complemento;
	private String cidade;
	private String bairro;
	private String cep;

	private MpEstadoUF mpEstadoUF;

	// ---
	
	@NotBlank @Size(max = 150)
	@Column(name = "local_logradouro", nullable = false, length = 150)
	public String getLogradouro() {	return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

	@NotBlank @Size(max = 20)
	@Column(name = "local_numero", nullable = false, length = 20)
	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }

	@Size(max = 150)
	@Column(name = "local_complemento", length = 150)
	public String getComplemento() { return complemento; }
	public void setComplemento(String complemento) { this.complemento = complemento; }

	@NotBlank @Size(max = 60)
	@Column(name = "local_cidade", nullable = false, length = 60)
	public String getCidade() { return cidade; }
	public void setCidade(String cidade) { this.cidade = cidade; }

	@NotBlank @Size(max = 60)
	@Column(name = "local_bairro", nullable = false, length = 60)
	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 2)
	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }

	@NotBlank @Size(max = 9)
	@Column(name = "local_cep", nullable = false, length = 9)
	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }

}
