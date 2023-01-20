package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpCompraFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeContato;
	
	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Long getNumeroDe() { return numeroDe; }
	public void setNumeroDe(Long numeroDe) { this.numeroDe = numeroDe; }

	public Long getNumeroAte() { return numeroAte; }
	public void setNumeroAte(Long numeroAte) { this.numeroAte = numeroAte; }

	public Date getDataCriacaoDe() { return dataCriacaoDe; }
	public void setDataCriacaoDe(Date dataCriacaoDe) { this.dataCriacaoDe = dataCriacaoDe; }
	
	public Date getDataCriacaoAte() { return dataCriacaoAte; }
	public void setDataCriacaoAte(Date dataCriacaoAte) { this.dataCriacaoAte = dataCriacaoAte; }

	public String getNomeContato() { return nomeContato; }
	public void setNomeContato(String nomeContato) { this.nomeContato = nomeContato; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}