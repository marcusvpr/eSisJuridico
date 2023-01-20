package com.mpxds.mpbasic.model.enums;

public enum MpTipoProduto {

	BEBIDAS("Bebidas", true, false, true, true),
	BEBIDAS_ALCOOL("Bebidas Alcoólicas", true, false, true, true),
	PRODUTOS("Produtos", true, false, true, true),
	
	MEDICAMENTOS("Medicamentos", true, true, true, false),
	GENERICOS("Genéricos", true, true, true, false),
	DERMOCOSMETICOS("Dermocosméticos", false, false, false, false),
	HIGIENE("Higiene", false, false, false, false),
	BEBE_MAMAE("Bebê e Mamãe", false, false, false, false),
	ORTOPEDIA_ACESSORIOS("Ortopedia e Acessórios", false, false, false, false),
	ESTETETICA("Estética", true, true, true, false),
	NUTRICAO_ALIMENTOS("Nutrição e Alimentos", false, false, false, false),
	PERFUMARIA("Perfumaria", false, false, false, false),
	MAQUIAGEM("Maquiagem", false, false, false, false),
	TESTES_APARELHOS("Testes e Aparelhos", false, false, false, false),
	OUTROS("Outros", true, true, true, true);
	
	private String descricao;
	private Boolean indApresentacao;
	private Boolean indMedicamento;
	private Boolean indConservacao;
	private Boolean indControleEstoque;
	
	// ---

	MpTipoProduto(String descricao
				, Boolean indApresentacao
				, Boolean indMedicamento
				, Boolean indConservacao
				, Boolean indControleEstoque) {
		this.descricao = descricao;
		this.indApresentacao = indApresentacao;
		this.indMedicamento = indMedicamento;
		this.indConservacao = indConservacao;
		this.indControleEstoque = indControleEstoque;
	}
	
	public String getDescricao() { return descricao; }
	
	public Boolean getIndApresentacao() { return indApresentacao; }	
	public Boolean getIndMedicamento() { return indMedicamento; }
	public Boolean getIndConservacao() { return indConservacao; }
	public Boolean getIndControleEstoque() { return indControleEstoque; }

}