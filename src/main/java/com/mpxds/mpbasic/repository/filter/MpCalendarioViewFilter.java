package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpCalendarioViewFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String descricao;
	private Boolean indFeriado;
	private Boolean indAtivo;

	private Boolean indShowAlarme = true;
	private Boolean indShowCalendario = true;
	private Boolean indShowFeriado = true;
	private Boolean indShowAtividade = true;
	private Boolean indShowTurno = true;

	private Integer numDiaExpande;
	
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

	public Boolean getIndShowAlarme() { return indShowAlarme; }
	public void setIndShowAlarme(Boolean indShowAlarme) { this.indShowAlarme = indShowAlarme; }

	public Boolean getIndShowCalendario() { return indShowCalendario; }
	public void setIndShowCalendario(Boolean indShowCalendario) { 
														this.indShowCalendario = indShowCalendario; }

	public Boolean getIndShowFeriado() { return indShowFeriado; }
	public void setIndShowFeriado(Boolean indShowFeriado) { this.indShowFeriado = indShowFeriado; }

	public Boolean getIndShowAtividade() { return indShowAtividade; }
	public void setIndShowAtividade(Boolean indShowAtividade) 
														{ this.indShowAtividade = indShowAtividade; }

	public Boolean getIndShowTurno() { return indShowTurno; }
	public void setIndShowTurno(Boolean indShowTurno) { this.indShowTurno = indShowTurno; }
	
	public Integer getNumDiaExpande() { return numDiaExpande; }
	public void setNumDiaExpande(Integer numDiaExpande) { this.numDiaExpande = numDiaExpande; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}