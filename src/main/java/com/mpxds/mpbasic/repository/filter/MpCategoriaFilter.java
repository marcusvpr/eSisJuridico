package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpCategoriaFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String tipo;
	private String descricao;
	private String pai;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
	
	public String getPai() { return pai; }
	public void setPai(String pai) { this.pai = pai; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}