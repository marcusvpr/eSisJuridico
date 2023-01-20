package com.mpxds.mpbasic.repository.filter.engreq;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpRequisitoNaoFuncionalFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String idRNF;
	private String descricao;
	private String associacao; // RF0003 RF0005 -> separados por ESPAÇOS !
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getIdRNF() { return idRNF; }
	public void setIdRNF(String idRNF) { this.idRNF = idRNF; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getAssociacao() { return associacao; }
	public void setAssociacao(String associacao) { this.associacao = associacao; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
																	this.mpFilterOrdenacao = mpFilterOrdenacao; }

}