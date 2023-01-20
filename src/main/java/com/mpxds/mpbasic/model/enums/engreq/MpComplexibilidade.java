package com.mpxds.mpbasic.model.enums.engreq;

public enum MpComplexibilidade {

	BAIXA("Baixa"),
	MEDIA("Média"),
	ALTA("Alta");
	
	private String descricao;
	
	// ---
	
	MpComplexibilidade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
