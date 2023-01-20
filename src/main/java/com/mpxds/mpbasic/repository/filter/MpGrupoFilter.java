package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpGrupoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
											this.mpFilterOrdenacao = mpFilterOrdenacao; }

}