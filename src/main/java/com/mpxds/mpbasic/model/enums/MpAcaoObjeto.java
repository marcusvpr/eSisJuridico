package com.mpxds.mpbasic.model.enums;

public enum MpAcaoObjeto {

	NOVO("Novo"), 
	EDITA("Edita"), 
	EXCLUIR("Excluir"), 
	NAVEGA("Navegação"), 
	CLONA("Clona"), 
	PESQUISA("Pesquisa"); 
	
	private String descricao;
		
	// ---
	
	MpAcaoObjeto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }

}