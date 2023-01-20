package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpDolarFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String filtroXXX; ???

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getFiltroxxx() { return filtroXXX; }
	public void setFiltroxxx(String filtroXXX) { this.filtroXXX = filtroXXX; }

	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}