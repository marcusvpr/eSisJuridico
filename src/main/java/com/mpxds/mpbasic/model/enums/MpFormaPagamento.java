package com.mpxds.mpbasic.model.enums;

public enum MpFormaPagamento {

	DINHEIRO("Dinheiro"), 
	CHEQUE("Cheque"), 
	BOLETO_BANCARIO("Boleto bancário"), 
	DEPOSITO_BANCARIO("Depósito bancário"),
	CARTAO_CREDITO("Cartão de crédito"), 
	CARTAO_DEBITO("Cartão de débito"), 
	DUPLICATA("Duplicata"), 
	CARNE("Carnê"), 
	TRANSF_ELETRONICA("Transferência Eletrônica"), 
	CREDITO_LOJA("Crédito Loja"), 
	VALE_ALIMENTACAO("Vale Refeição"),
	VALE_REFEICAO("Vale Refeição"),
	VALE_PRESENTE("Vale Presente"),
	VALE_COMBUSTIVEL("Vale Combsutível"),
	SEM_DOCUMENTO("Sem Documento");
	
	private String descricao;
	
	// ---
	
	MpFormaPagamento(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() { return descricao; }
	
}