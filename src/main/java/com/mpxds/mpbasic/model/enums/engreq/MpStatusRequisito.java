package com.mpxds.mpbasic.model.enums.engreq;

public enum MpStatusRequisito {

	PROPOSTO("Proposto"),
	VALIDANDO("Validando"),
	VALIDADO("Validado"),
	IMPLEMENTANDO("Implementando"),
	IMPLEMENTADO("Implementado"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	// ---
	
	MpStatusRequisito(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
