package com.mpxds.mpbasic.model.enums;

public enum MpTipoItem {

	PRODUTO("Produto"),
	SERVICO("Serviço");
	
	private String descricao;
	
	// ---

	MpTipoItem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }
	
}