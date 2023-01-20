package com.mpxds.mpbasic.model.enums;

public enum MpTipoObjetoSistema {

	FORMULARIO("Formulário"), 
	FORMULARIO_CADASTRO("Formulário Cadastro"), 
	FORMULARIO_PESQUISA("Formulário Pesquisa"), 
	FORMULARIO_FILTRO("Formulário Filtro"), 
	FORMULARIO_SELECAO("Formulário Seleção"), 
	FORMULARIO_ACAO("Formulário Ação"), 
	RELATORIO("Relatório"), 
	GRAFICO("Gráfico"), 
	DEFINIR("A definir!"), 
	ENTIDADE("Entidade");
	
	private String descricao;
	
	// ---
	
	MpTipoObjetoSistema(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
