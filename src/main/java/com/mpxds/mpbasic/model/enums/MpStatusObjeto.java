package com.mpxds.mpbasic.model.enums;

public enum MpStatusObjeto {

	ATIVO("Ativo"),
	INATIVO("Inativo"),
	BLOQUEADO("Bloqueado");
	
	private String descricao;
	
	// ---

	MpStatusObjeto(String descricao) { 
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }

}