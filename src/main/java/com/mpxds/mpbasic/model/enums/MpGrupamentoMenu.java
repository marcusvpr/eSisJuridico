package com.mpxds.mpbasic.model.enums;

public enum MpGrupamentoMenu {

	NULO("_"), 
	ALERTA("Alerta..."), 
	CLIENTE("Cliente..."), 
	PESSOA("Pessoa..."), 
	PRODUTO("Produto..."), 
	USUARIO("Usuário..."), 
	SISTEMA("Sistema..."),
	LOGS("Logs..."), 
	GRAFICO("Gráfico..."), 
	PESQUISA("Pesquisa..."), 
	PROCEDIMENTO("Procedimento..."), 
	CARTORIO("Cartório Ofício..."), 
	CADASTRO("Cadastro..."); 
	
	private String descricao;
	
	// ---
	
	MpGrupamentoMenu(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
