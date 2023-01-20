package com.mpxds.mpbasic.model.enums;

public enum MpStatusCompra {

	ORCAMENTO("Orçamento"), 
	EMITIDA("Emitida"), 
	CANCELADA("Cancelada");
	
	private String descricao;
	
	// ---
	
	MpStatusCompra(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
