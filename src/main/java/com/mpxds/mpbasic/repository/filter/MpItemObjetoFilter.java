package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpItemObjetoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String nome;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}