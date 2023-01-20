package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpMovimentoLoginFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String atividade;
	private String numeroIP;
	private String usuario;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataCriacaoDe() { return dataCriacaoDe; }
	public void setDataCriacaoDe(Date dataCriacaoDe) { this.dataCriacaoDe = dataCriacaoDe; }

	public Date getDataCriacaoAte() { return dataCriacaoAte; }
	public void setDataCriacaoAte(Date dataCriacaoAte) { this.dataCriacaoAte = dataCriacaoAte; }
	
	public String getAtividade() { return atividade; }
	public void setAtividade(String atividade) { this.atividade = atividade; }
	
	public String getNumeroIP() { return numeroIP; }
	public void setNumeroIP(String numeroIP) { this.numeroIP = numeroIP; }
	
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}