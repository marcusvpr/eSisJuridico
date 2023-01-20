package com.mpxds.mpbasic.model.dto.engreq;

import java.io.Serializable;

import com.mpxds.mpbasic.model.enums.engreq.MpCategoriaRNF;
import com.mpxds.mpbasic.model.enums.engreq.MpComplexibilidade;
import com.mpxds.mpbasic.model.enums.engreq.MpPrioridade;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;

public class MpStatusDTO implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private MpPrioridade mpPrioridade;
	private MpComplexibilidade mpComplexibilidade;
	private MpStatusRequisito mpStatusRequisito;
	private MpCategoriaRNF mpCategoriaRNF;
				
	// ---

	public MpPrioridade getMpPrioridade() { return mpPrioridade; }
	public void setMpPrioridade(MpPrioridade mpPrioridade) { this.mpPrioridade = mpPrioridade; }
	
	public MpComplexibilidade getMpComplexibilidade() { return mpComplexibilidade; }
	public void setMpComplexibilidade(MpComplexibilidade mpComplexibilidade) {
														this.mpComplexibilidade = mpComplexibilidade; }

	public MpStatusRequisito getMpStatusRequisito() { return mpStatusRequisito; }
	public void setMpStatusRequisito(MpStatusRequisito mpStatusRequisito) {
														this.mpStatusRequisito = mpStatusRequisito; }

	public MpCategoriaRNF getMpCategoriaRNF() { return mpCategoriaRNF; }
	public void setMpCategoriaRNF(MpCategoriaRNF mpCategoriaRNF) { 
																this.mpCategoriaRNF = mpCategoriaRNF; }
	
}