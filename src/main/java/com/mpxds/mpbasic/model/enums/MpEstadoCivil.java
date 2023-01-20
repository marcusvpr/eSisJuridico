package com.mpxds.mpbasic.model.enums;

public enum MpEstadoCivil {

	CASADO("Casado"), 
	DIVORCIADO("Divorciado"), 
	VIÚVO("Viúvo"), 
	U_ESTÁVEL("União Estável"), 
	SOLTEIRO("Solteiro");
	
	private String descricao;
	
	// ---
	
	MpEstadoCivil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
