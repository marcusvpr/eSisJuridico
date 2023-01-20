package com.mpxds.mpbasic.model.enums;

public enum MpStatusProduto {

	ATIVO("Ativo"), 
	SUSPENSO("Suspenso"); 
	
	private String descricao;
	
	// ---
	
	MpStatusProduto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}