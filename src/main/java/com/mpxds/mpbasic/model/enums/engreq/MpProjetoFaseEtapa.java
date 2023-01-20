package com.mpxds.mpbasic.model.enums.engreq;

public enum MpProjetoFaseEtapa {

	ENG_REQ("Engenharia Requisitos", "", 20, false),
	ENG_REQ_ELICITACAO("Engenharia Requisitos", "Elicitação Requisitos", 20, true),
	ENG_REQ_ANALISE("Engenharia Requisitos", "Análise Requisitos", 20, true),
	ENG_REQ_ESPECIFICACAO("Engenharia Requisitos", "Especificação Requisitos", 40, true),
	ENG_REQ_VALIDACAO("Engenharia Requisitos", "Validação Requisitos", 15, true),
	ENG_REQ_GERENCIAMENTO("Engenharia Requisitos", "Gerenciamento Requisitos durante Engenharia", 5, true),
	ANALISE("Análise", "", 15, false),
	ANALISE_AVALIACAO("Análise", "Avaliação Modelo Requisitos", 5, true),
	ANALISE_DEFINICAO("Análise", "Definição Cenários e Identificação Casos de Uso", 20, true),
	ANALISE_ESPECIFICACAO("Análise", "Especificação Casos de Uso", 50, true),
	ANALISE_VALIDACAO("Análise", "Validação Casos de Uso", 20, true),
	ANALISE_REVISAO("Análise", "Revisão Modelo Requisitos", 5, true),
	PROJETO("Projeto", "", 10, false),
	PROJETO_AVALIACAO("Projeto", "Avaliação Modelo Casos de Uso", 5, true),
	PROJETO_DEFINICAO("Projeto", "Definição Arquitetura", 10, true),
	PROJETO_DEFINICAO_MODELO("Projeto", "Definição Modelo Dados", 15, true),
	PROJETO_DIAGRAMAS("Projeto", "Diagramas Classe e Sequência", 35, true),
	PROJETO_ESPECIFICACAO("Projeto", "Especificação Técnica Interface Gráfica", 20, true),
	PROJETO_VALIDACAO("Projeto", "Validação Projeto", 10, true),
	PROJETO_REVISAO("Projeto", "Revisão Modelo Casos de Uso", 5, true),
	CONSTRUCAO("Construção", "", 30, false),
	CONSTRUCAO_AVALIACAO("Construção", "Avaliação Modelo Estrutural/Arquitetural", 5, true),
	CONSTRUCAO_COMPONENTES("Construção", "Construção Componentes", 15, true),
	CONSTRUCAO_FUNCIONALIDADES("Construção", "Construção Funcionalidades", 45, true),
	CONSTRUCAO_TESTES("Construção", "Testes Unitários", 20, true),
	CONSTRUCAO_VALIDACAO("Construção", "Validação Construção", 10, true),
	CONSTRUCAO_REVISAO("Construção", "Revisão Modelo Estrutural/Arquitetural", 5, true),
	TESTES("Testes", "", 20, false),
	TESTES_ESTUDOS_REQUISITO("Testes", "Estudo Modelo Requisitos", 10, true),
	TESTES_ESTUDOS_CASOS_USO("Testes", "Estudo Modelo Casos de Uso", 10, true),
	TESTES_ESPECIFICACAO("Testes", "Especificação Casos de Teste", 30, true),
	TESTES_EXECUCAO("Testes", "Execução Casos de Teste", 50, true),
	GESTAO("Gestão", "", 5, false),
	GESTAO_PROJETO("Gestão", "Gestão Projeto", 100, true),
	TOTAL("Total", "", 100, false);
	
	private String faseEtapa;
	private String disciplina;
	private Integer percentualConsumo;
	private Boolean isDisciplina;
	
	// ---
	
	MpProjetoFaseEtapa(String faseEtapa,
					   String disciplina,
					   Integer percentualConsumo,
					   Boolean isDisciplina) {
		this.faseEtapa = faseEtapa;
		this.disciplina = disciplina;
		this.percentualConsumo = percentualConsumo;
		this.isDisciplina = isDisciplina;
	}

	public String getFaseEtapa() { return faseEtapa; }

	public String getDisciplina() { return disciplina; }

	public Integer getPercentualConsumo() { return percentualConsumo; }
	
	public Boolean getIsDisciplina() { return this.isDisciplina; }
	
}
