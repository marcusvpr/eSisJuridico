package com.mpxds.mpbasic.repository.filter.pt01;

import java.io.Serializable;
import java.util.Date;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpDataProcessoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataProtocolo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataProtocolo() { return dataProtocolo; }
	public void setDataProtocolo(Date dataProtocolo) { this.dataProtocolo = dataProtocolo; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}