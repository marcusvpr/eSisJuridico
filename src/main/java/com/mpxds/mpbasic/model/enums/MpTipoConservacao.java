package com.mpxds.mpbasic.model.enums;

public enum MpTipoConservacao {

	CONGELADO("Congelador"),
	GELADEIRA("Geladeira"),
	AMBIENTE("Ambiente");
	
	private String descricao;
	
	// ---
	
	MpTipoConservacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }

}