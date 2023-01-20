package com.mpxds.mpbasic.repository.filter.pt01;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpBancoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private Integer agencia;
	private String nome;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getCodigo() {	return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	public Integer getAgencia() { return agencia; }
	public void setAgencia(Integer agencia) { this.agencia = agencia; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}