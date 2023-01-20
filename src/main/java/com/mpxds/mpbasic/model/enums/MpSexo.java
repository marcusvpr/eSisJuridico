package com.mpxds.mpbasic.model.enums;

public enum MpSexo {

	MASCULINO("Masculino"), 
	FEMININO("Feminino");
	
	private String descricao;
	
	// ---
	
	MpSexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
