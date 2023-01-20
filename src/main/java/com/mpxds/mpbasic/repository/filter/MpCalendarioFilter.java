package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpCalendarioFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String descricao;
	private Boolean indFeriado;
	private Boolean indAtivo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public Date getDataCriacaoDe() { return dataCriacaoDe; }
	public void setDataCriacaoDe(Date dataCriacaoDe) { this.dataCriacaoDe = dataCriacaoDe; }

	public Date getDataCriacaoAte() { return dataCriacaoAte; }
	public void setDataCriacaoAte(Date dataCriacaoAte) { this.dataCriacaoAte = dataCriacaoAte; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public Boolean getIndFeriado() { return indFeriado; }
	public void setIndFeriado(Boolean indFeriado) {	this.indFeriado = indFeriado; }

	public Boolean getIndAtivo() { return indAtivo; }
	public void setIndAtivo(Boolean indAtivo) { this.indAtivo = indAtivo; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}