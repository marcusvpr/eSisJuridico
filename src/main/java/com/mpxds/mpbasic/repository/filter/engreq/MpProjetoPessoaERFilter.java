package com.mpxds.mpbasic.repository.filter.engreq;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpProjetoPessoaERFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String observacao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
													this.mpFilterOrdenacao = mpFilterOrdenacao; }

}