package com.mpxds.mpbasic.model.enums;

public enum MpApresentacaoProduto {
	//
	ADESIVO("Adesivo", false),
	AMPOLA("Ampola", false),
	CAPSULA("Cápsula", false),
	COMPRIMIDO("Comprimido", false),
	CAIXA("Caixa", true), // SK
	COPO("Copo", true), // SK
	CREME("Creme", false),
	ENEMA("Enema", false),
	ENGRADADO("Engradado", true), // SK
	FRASCO("Frasco", false),
	GARRAFA("Garrafa", true), // SK
	GEL("Gél", false),
	KIT("Kit Completo", false),
	LIQUIDO("Líquido", false),
	LOCAO("Loção", false),
	MALAPACKPCT("Mala/Pack/Pct", true),
	PO("Pó", false),
	POMADA("Pomada", false),
	SACHE("Sache", false),
	SERINGA("Seringa", false),
	SUPOSITORIO("Supositório", false),
	SPRAY("Spray", false),
	UNIDADESK("Unidade.", true), // SK
	UNIDADE("Unidade", false),
	OUTROSK("Outro.", true), // SK
	OUTRO("Outro", false);
	
	private String descricao;
	private Boolean indControleEstoque;
	
	// ---
	
	MpApresentacaoProduto(String descricao
						, Boolean indControleEstoque) {
		this.descricao = descricao;
		this.indControleEstoque = indControleEstoque;
	}

	public String getDescricao() { return descricao; }
	public Boolean getIndControleEstoque() { return indControleEstoque; }

}