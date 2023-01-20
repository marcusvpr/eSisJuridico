package com.mpxds.mpbasic.model.enums;

public enum MpTipoMovimento {

	ENTRADA("Entrada", "C"), 
	SAIDA("Saida", "D"),
	DOACAO("Doação", "D");
	
	private String descricao;
	private String tipoCreditoDebito;
	
	// ---
	
	MpTipoMovimento(String descricao, String tipoCreditoDebito) {
		this.descricao = descricao;
		this.tipoCreditoDebito = tipoCreditoDebito;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getTipoCreditoDebito() {
		return tipoCreditoDebito;
	}
	
}
