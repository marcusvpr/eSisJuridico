package com.mpxds.mpbasic.repository.filter.pt01;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpObservacaoFilter implements Serializable { 
	//
	private static final long serialVersionUID = 1L;

	private String tipoObservacao;
	private String descricaoObservacao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getTipoObservacao() {	return tipoObservacao; }
	public void setTipoObservacao(String tipoObservacao) { 
														this.tipoObservacao = tipoObservacao; }
	
	public String getDescricaoObservacao() {return descricaoObservacao; }
	public void setDescricaoObservacao(String descricaoObservacao) { 
												this.descricaoObservacao = descricaoObservacao; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}