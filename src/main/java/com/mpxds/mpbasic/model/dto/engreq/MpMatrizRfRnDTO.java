package com.mpxds.mpbasic.model.dto.engreq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MpMatrizRfRnDTO implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String requisitoFuncional;
	private List<String> regraNegocioList = new ArrayList<String>(50);;
				
	// ---

	public String getRequisitoFuncional() { return requisitoFuncional; }
	public void setRequisitoFuncional(String requisitoFuncional) { 
													this.requisitoFuncional = requisitoFuncional; }
	
	public List<String> getRegraNegocioList() { return regraNegocioList; }
	public void setRegraNegocioList(List<String> regraNegocioList) { 
														this.regraNegocioList = regraNegocioList; }
	
}