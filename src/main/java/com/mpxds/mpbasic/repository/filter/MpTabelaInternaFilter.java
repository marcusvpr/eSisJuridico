package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

//import com.mpxds.mpbasic.validation.MpCodigo;

public class MpTabelaInternaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoTabelaInterna;
	private String codigo;
	private String descricao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getTipoTabelaInterna() { return tipoTabelaInterna; }
	public void setTipoTabelaInterna(String tipoTabelaInterna) { 
													this.tipoTabelaInterna = tipoTabelaInterna; }

	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}