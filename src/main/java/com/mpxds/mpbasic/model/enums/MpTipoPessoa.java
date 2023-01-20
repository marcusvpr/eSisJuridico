package com.mpxds.mpbasic.model.enums;

public enum MpTipoPessoa {

	FISICA("Fisica"),
	JURIDICA("Jurídica");
	
	private String descricao;
	
	// ---

	MpTipoPessoa(String descricao) { 
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }

}