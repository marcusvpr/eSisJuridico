package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpContatoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nomeRazaoSocial;
	private String email;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	
	public String getNomeRazaoSocial() { return nomeRazaoSocial; }
	public void setNomeRazaoSocial(String nomeRazaoSocial) { this.nomeRazaoSocial = nomeRazaoSocial; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}