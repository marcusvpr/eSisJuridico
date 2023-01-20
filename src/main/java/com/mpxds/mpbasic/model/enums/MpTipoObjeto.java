package com.mpxds.mpbasic.model.enums;

public enum MpTipoObjeto {

	DOC("MS Word"), 
	DOCX("MS Word X"), 
	JAVA("Java"), 
	JAVA_CONTROLLER("Java Controller"), 
	JAVA_MODEL("Java Model"), 
	JAVA_ENUM("Java Enum"), 
	JSP("Java JSP"), 
	JSF("Java Server Page"), 
	PDF("Acrobat PDF"), 
	TXT("Arquivo Texto"), 
	XHTML("JSF XHTML"), 
	XLS("MS Excel"), 
	XLSX("MS Excel X"), 
	XML("Arquivo XML"),
	ZUL("ZK ZUL");
	
	private String descricao;
	
	// ---
	
	MpTipoObjeto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
