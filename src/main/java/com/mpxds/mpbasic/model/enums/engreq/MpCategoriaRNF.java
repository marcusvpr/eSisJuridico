package com.mpxds.mpbasic.model.enums.engreq;

public enum MpCategoriaRNF { // Tam.20

	DESEMPENHO("Desempenho"),
	DISPONIBILIDADE("Disponibilidade"),
	SEGURANCA("Segurança"),
	INTEROPERABILIDADE("Interoperabilidade"), 
	USABILIDADE("Usabilidade"),
	COMPATIBILIDADE("Compatibilidade"),
	CONFIABILIDADE("Confiabilidade"),
	PADROES("Padrões"),
	LEGAIAS("Legais");
	
	private String descricao;
	
	// ---
	
	MpCategoriaRNF(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
