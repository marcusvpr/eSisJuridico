package com.mpxds.mpbasic.repository.filter.engreq;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpMacroRequisitoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String descricao;
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
																	this.mpFilterOrdenacao = mpFilterOrdenacao; }

}