package com.mpxds.mpbasic.model.enums;

public enum MpPeriodicidade {

	UNICA("Única"),
	DIARIA("Diária"),
	SEMANAL("Semanal"),
	QUINZENAL("Quinzenal"),
	MENSAL("Mensal"),
	BIMENSAL("Bimensal"),
	TRIMENSAL("Trimensal"),
	SEMESTRAL("Semestral"),
	ANUAL("Anual");
	
	private String descricao;
	
	// ---

	MpPeriodicidade(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }
	
}