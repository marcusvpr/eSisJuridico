package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpUsuarioTenantFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String login;
	private String nome;
	private String email;
	private String status;
	private String tenant;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getTenant() { return tenant; }
	public void setTenant(String tenant) { this.tenant = tenant; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}