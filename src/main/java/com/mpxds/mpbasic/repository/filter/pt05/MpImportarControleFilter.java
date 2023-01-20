package com.mpxds.mpbasic.repository.filter.pt05;

import java.io.Serializable;
import java.util.Date;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpImportarControleFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataImportar;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataImportar() { return dataImportar; }
	public void setDataImportar(Date dataImportar) { this.dataImportar = dataImportar; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}