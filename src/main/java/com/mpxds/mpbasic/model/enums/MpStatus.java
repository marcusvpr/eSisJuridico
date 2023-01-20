package com.mpxds.mpbasic.model.enums;

public enum MpStatus {

	ATIVO("Ativo"),
	INATIVO("Inativo"),
	BLOQUEADO("Bloqueado"),
	CANCELADO("Cancelado"),
	REGISTRO("Registro"),
	PENDENTE("Pendente");
	
	private String descricao;
	
	// ---

	MpStatus(String descricao) { 
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }

}