package com.mpxds.mpbasic.model.enums;

public enum MpGrupoTenant {
	//
	AFILIADO("Afiliado", false),
	AFILIADO_ENGREQ("Afiliado Eng.Requisito", false),
	CUIDADOR("Cuidador", false),
	EMPREGADO("Empregado", false),
	FAMILIA("Familia", false),
	// SK ...
	CLIENTE("Cliente", true),
	ENTREGADOR("Entregador", true),
	VENDEDOR("Vendedor", true);
	
	private String descricao;
	private Boolean indControleEstoque;
	
	// ---
	
	MpGrupoTenant(String descricao,
			   	  Boolean indControleEstoque) {
		this.descricao = descricao;
		this.indControleEstoque = indControleEstoque;
	}

	public String getDescricao() { return this.descricao; }

	public Boolean getIndControleEstoque() { return indControleEstoque; }
	
}