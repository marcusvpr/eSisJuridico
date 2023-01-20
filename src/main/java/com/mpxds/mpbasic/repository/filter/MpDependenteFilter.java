package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpDependenteFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String status;
	private String sexo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getSexo() { return sexo; }
	public void setSexo(String sexo) { this.sexo = sexo; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}