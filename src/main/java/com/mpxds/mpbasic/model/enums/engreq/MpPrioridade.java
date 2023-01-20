package com.mpxds.mpbasic.model.enums.engreq;

public enum MpPrioridade {

	ESSENCIAL("Essencial"),
	IMPORTANTE("Importante"),
	DESEJAVEL("Desejável");
	
	private String descricao;
	
	// ---
	
	MpPrioridade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
