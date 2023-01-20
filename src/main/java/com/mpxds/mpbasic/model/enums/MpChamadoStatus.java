package com.mpxds.mpbasic.model.enums;

public enum MpChamadoStatus {

	NOVO("Novo"), 
	ANDAMENTO("Andamento"), 
	CANCELADO("Cancelado"), 
	ENCERRADO("Encerrado"), 
	REDIRECIONADO("Redirecionado"), 
	SUSPENSO("Suspenso"); 
	
	private String descricao;
	
	// ---
	
	MpChamadoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
