package com.mpxds.mpbasic.model.enums;

public enum MpChamadoTipo {

	ELOGIO("Elogio"), 
	MANUTENCAO("Manutenção"), 
	IMPLEMENTACAO("Nova Implementação"), 
	PROBLEMA("Problema"), 
	RECLAMACAO("Reclamação"), 
	SUGESTAO("Sugestão");
	
	private String descricao;
	
	// ---
	
	MpChamadoTipo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
