package com.mpxds.mpbasic.model.dto.engreq;

import java.io.Serializable;

public class MpRfRnDTO implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String requisitoFuncional;
	private String regraNegocio;
				
	// ---

	public String getRequisitoFuncional() { return requisitoFuncional; }
	public void setRequisitoFuncional(String requisitoFuncional) { 
													this.requisitoFuncional = requisitoFuncional; }
	
	public String getRegraNegocio() { return regraNegocio; }
	public void setRegraNegocio(String regraNegocio) { this.regraNegocio = regraNegocio; }
	
}