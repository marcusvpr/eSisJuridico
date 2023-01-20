package com.mpxds.mpbasic.model.enums;

public enum MpArquivoAcao {

	ASSOCIAR("Associar", "Associada"),
	CAPTURAR("Capturar", "Capturada"),
	CARREGAR("Carregar", "Carregada");
	
	private String descricao;
	private String resultado;
	
	// ---

	MpArquivoAcao(String descricao, String resultado) {
		this.descricao = descricao;
		this.resultado = resultado;
	}
	
	public String getDescricao() { return descricao; }

	public String getResultado() { return resultado; }

}