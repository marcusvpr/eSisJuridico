package com.mpxds.mpbasic.model.enums.engreq;

public enum MpPessoaFuncao {

	PI_MASTER("Parte Interessada", "Product Owner"),
	PI_KeyUser("Parte Interessada", "Key User"),
	EQ_GP("Equipe", "Gerente Projetos/Scrum Master"),
	EQ_ER("Equipe", "Engenharia Requisitos/BPM"),
	EQ_DSV("Equipe", "Desenvolvedor"),
	EQ_DBA("Equipe", "DBA"),
	EQ_TEST("Equipe", "Analista Testes"),
	EQ_SIST("Equipe", "Analista Sistemas"),
	EQ_WEB("Equipe", "Web Designer");
	
	private String grupo;
	private String funcao;
	
	// ---
	
	MpPessoaFuncao(String grupo, String funcao) {
		this.grupo = grupo;
		this.funcao = funcao;
	}

	public String getGrupo() { return grupo; }

	public String getFuncao() { return funcao; }

	public String getGrupoFuncao() { return grupo + " / " + funcao; }

}
