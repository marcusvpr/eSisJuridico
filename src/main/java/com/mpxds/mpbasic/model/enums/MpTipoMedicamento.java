package com.mpxds.mpbasic.model.enums;

public enum MpTipoMedicamento {

	GENERICO("Genérico"),
	REFERENCIA("Referência"),
	SIMILAR("Similar"),
	OUTRO("Outro");
	
	private String descricao;
	
	// ---

	MpTipoMedicamento(String descricao) { this.descricao = descricao; }
	
	public String getDescricao() { return descricao; }
	
}