package com.mpxds.mpbasic.model.enums;

public enum MpTipoTabelaInterna {

	// Cidade	 			<0010>
	// Tipo	 				<0015>
	// Grupo	 			<0025>
	// Forma Tributária		<0030>
	// Grupo Menu 			<0040>
	// Ramo Atividade		<0045>
	// Unidade				<0055>
	
	TAB_0001("Tabela Cidade", "c", 50),
	TAB_0003("Tabela Cor", "c", 30),
	TAB_0005("Tabela Idioma", "c", 20),
	TAB_0007("Tabela Grupo ImagemBD", "c", 30),
	TAB_0009("Tabela Localização", "c", 50),
	TAB_0011("Tabela Profissão", "c", 50);

	private String descricao;
	private String formato; // c=character n=numeric
	private Integer tamanho;
	
	// ---

	MpTipoTabelaInterna(String descricao, String formato, Integer tamanho) {
		this.descricao = descricao;
		this.formato = formato;
		this.tamanho = tamanho;
	}
	
	public String getDescricao() { return descricao; }
	
	public String getFormato() { return formato; }
	
	public Integer getTamanho() { return tamanho; }
	
}