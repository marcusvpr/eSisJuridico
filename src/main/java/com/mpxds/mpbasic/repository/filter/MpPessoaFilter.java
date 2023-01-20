package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpPessoaFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String tipo;
	private String profissao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	public String getProfissao() { return profissao; }
	public void setProfissao(String profissao) { this.profissao = profissao; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}