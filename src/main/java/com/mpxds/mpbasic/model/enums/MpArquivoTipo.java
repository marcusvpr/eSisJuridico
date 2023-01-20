package com.mpxds.mpbasic.model.enums;

public enum MpArquivoTipo {

	JPG("Imagem/FotoJPG"),
	IMG("Imagem/Foto"),
	DOC("MS Word"),
	XLS("MS Excel"),
	PDF("PDF Acrobat"),
	TXT("Arquivo TXT");
	
	private String descricao;
	
	// ---

	MpArquivoTipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }

}