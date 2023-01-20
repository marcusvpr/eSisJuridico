package com.mpxds.mpbasic.model.enums;

public enum MpAtividadeTipo {

	RECEITA("Receita"),
	ROTINA("Rotina"),
	FISICA("Fisica");
	
	private String descricao;
	
	// ---

	MpAtividadeTipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }

}