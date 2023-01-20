package com.mpxds.mpbasic.model.enums;

import java.math.BigDecimal;

public enum MpUnidade {

	CAIXASK("Caixa", BigDecimal.ONE, true),
	MALA_PACK_PCT("Mala/Pack/Pct", BigDecimal.ONE, true),
	SACO("Saco", BigDecimal.ONE, true),
	LITRO("Litro", BigDecimal.ONE, true),
	MILILITRO("Mililitro", BigDecimal.ONE, true),
	GARRAFA("Garrafa", BigDecimal.ONE, true),
	//
	PILULA("Pilula", BigDecimal.ONE, false),
	PILULA_MEIA("1/2 Pilula", BigDecimal.ONE.divide(new BigDecimal("2")), false),
	COMPRIMIDO("Comprimido", BigDecimal.ONE, false),
	COMPRIMIDO_MEIO("1/2 Comprimido", BigDecimal.ONE.divide(new BigDecimal("2")), false),
	CAPSULA("Capsula", BigDecimal.ONE, false),
	CAPSULA_MEIA("1/2 Capsula", BigDecimal.ONE.divide(new BigDecimal("2")), false),
	UNIDADE("Unidade", BigDecimal.ONE, false),
	UNIDADE_MEIA("1/2 Unidade", BigDecimal.ONE.divide(new BigDecimal("2")), false),
	KG("Kilo (KG)", BigDecimal.ONE, false),
	LT("Litro (L)", BigDecimal.ONE, false),
	ML("Mililitro (ML)", BigDecimal.ONE, false),
	CAIXA("Caixa", BigDecimal.ONE, false);
	
	private String descricao;
	private BigDecimal valor;
	private Boolean indControleEstoque;
	
	// ---
	
	MpUnidade(String descricao, 
			  BigDecimal valor,
			  Boolean indControleEstoque) {
		this.descricao = descricao;
		this.valor = valor;
		this.indControleEstoque = indControleEstoque;
	}

	public String getDescricao() { return descricao; }
	public BigDecimal getValor() { return valor; }
	public Boolean getIndControleEstoque() { return indControleEstoque; }

}