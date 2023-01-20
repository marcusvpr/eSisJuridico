package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpSistemaConfigFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String parametro;
	private String descricao;
	private String valor;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getParametro() { return parametro; }
	public void setParametro(String parametro) { this.parametro = parametro; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getValor() { return valor; }
	public void setValor(String valor) { this.valor = valor; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}