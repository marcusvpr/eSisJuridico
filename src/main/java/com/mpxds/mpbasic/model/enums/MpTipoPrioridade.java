package com.mpxds.mpbasic.model.enums;

public enum MpTipoPrioridade {

	ALTA("Alta", "red"), 
	MEDIA("Média", "yellow"), 
	BAIXA("Baixa", "green"); 
	
	private String descricao;
	private String cor;
	
	// ---
	
	MpTipoPrioridade(String descricao, String cor) {
		this.descricao = descricao;
		this.cor = cor;
	}

	public String getDescricao() { return descricao; }
	
	public String getCor() { return cor; }
	
}
