package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpDolarFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacaoDe;
	private Date dataCriacaoAte;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public Date getDataCriacaoDe() { return dataCriacaoDe; }
	public void setDataCriacaoDe(Date dataCriacaoDe) { this.dataCriacaoDe = dataCriacaoDe; }

	public Date getDataCriacaoAte() { return dataCriacaoAte; }
	public void setDataCriacaoAte(Date dataCriacaoAte) { this.dataCriacaoAte = dataCriacaoAte; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}