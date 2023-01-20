package com.mpxds.mpbasic.model.enums;

public enum MpIndicadorIE {

	CONTRIBUINTE("Contribuinte"), 
	ISENTO("Isento de IE"),
	NAO_CONTRIBUINTE("Não Contribuinte"); 
	
	private String descricao;
	
	// ---
	
	MpIndicadorIE(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
