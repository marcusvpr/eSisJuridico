package com.mpxds.mpbasic.repository.filter.sisJuri;

import java.io.Serializable;
import java.util.Date;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpProcessoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCadastroDe;
	private Date dataCadastroAte;

	private String processoCodigo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public Date getDataCadastroDe() { return dataCadastroDe; }
	public void setDataCadastroDe(Date dataCadastroDe) { this.dataCadastroDe = dataCadastroDe; }

	public Date getDataCadastroAte() { return dataCadastroAte; }
	public void setDataCadastroAte(Date dataCadastroAte) { this.dataCadastroAte = dataCadastroAte; }
	
	public String getProcessoCodigo() { return processoCodigo; }
	public void setProcessoCodigo(String processoCodigo) { this.processoCodigo = processoCodigo; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}