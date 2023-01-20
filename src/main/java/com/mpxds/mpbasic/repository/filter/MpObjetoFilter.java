package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpObjetoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String transacao;
	private String codigoId;
	private String codigo;
	private String nome;
	private String descricao;
	private String classeAssociada;
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getTransacao() { return transacao; }
	public void setTransacao(String transacao) { this.transacao = transacao; }
	
	public String getCodigoId() { return codigoId; }
	public void setCodigoId(String codigoId) { this.codigoId = codigoId; }
	
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getClasseAssociada() { return classeAssociada; }
	public void setClasseAssociada(String classeAssociada) { 
														this.classeAssociada = classeAssociada; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}