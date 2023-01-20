package com.mpxds.mpbasic.model.enums;

public enum MpChamadoAreaTipo {

	EMPRESA("Empresa"), 
	SISTEMA("Sistema"), 
	SITE("Site"), 
	FUNCIONARIO("Funcionário"), 
	OUTRO("Outro(a)"); 
	
	private String descricao;
	
	// ---
	
	MpChamadoAreaTipo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
