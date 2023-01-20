package com.mpxds.mpbasic.model.enums;

public enum MpCalendarioRepetir {

	UNICO("Evento único"), 
	DIARIO("Diariamente"), 
	SEMANAL("Semanalmente"), 
	QUINZENAL("Quinzenalmente"), 
	MENSAL("Mensalmente"), 
	ANUAL("Anualmente");
	
	private String descricao;
	
	// ---
	
	MpCalendarioRepetir(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
