package com.mpxds.mpbasic.repository.filter.engreq;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpRegraNegocioFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String idRN;
	private String descricao;
	private String dependencia; // RF0003 RF0005 -> separados por ESPAÇOS !
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getIdRN() { return idRN; }
	public void setIdRN(String idRN) { this.idRN = idRN; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getDependencia() { return dependencia; }
	public void setDependencia(String dependencia) { this.dependencia = dependencia; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
																	this.mpFilterOrdenacao = mpFilterOrdenacao; }

}