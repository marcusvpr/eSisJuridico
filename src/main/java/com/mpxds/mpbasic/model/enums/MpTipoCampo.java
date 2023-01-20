package com.mpxds.mpbasic.model.enums;

public enum MpTipoCampo {

	BOOLEAN("Boolean"), 
	NUMERO("Número"), 
	TEXTO("Texto"); 
	
	private String descricao;
	
	// ---
	
	MpTipoCampo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
