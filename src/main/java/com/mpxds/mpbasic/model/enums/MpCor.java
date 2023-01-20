package com.mpxds.mpbasic.model.enums;

public enum MpCor {

	AZUL("Azul", "blue"),
	AMARELO("Amarelo", "yellow"),
	VERDE("Verde", "green"),
	VERMELHO("Vermelho", "red"),
	PRETO("Preto", "black");		
	
	private String descricao;
	private String codigo;
	
	// ---

	MpCor(String descricao, String codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	
	public String getDescricao() { return descricao; }

	public String getCodigo() { return codigo; }
	
}