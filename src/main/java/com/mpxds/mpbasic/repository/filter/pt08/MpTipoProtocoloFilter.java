package com.mpxds.mpbasic.repository.filter.pt08;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpTipoProtocoloFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String descricao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getCodigo() {	return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	
	public String getDescricao() {	return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}