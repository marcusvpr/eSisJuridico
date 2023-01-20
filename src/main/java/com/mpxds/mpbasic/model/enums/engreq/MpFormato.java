package com.mpxds.mpbasic.model.enums.engreq;

public enum MpFormato {

	F_DATE("Data (dd/mm/aaaa)"),
	F_DATE_TIME("Data.Hora (dd/mm/aaaa hh:MM:ss)"),
	F_INTEGER("Inteiro"),
	F_BIGDECIMAL("Decimal"),
	F_BOOLEAN("Boolean"),
	F_LIST("Lista"),
	F_CLASSE("Classe"),
	F_STRING("String");
	
	private String descricao;
	
	// ---
	
	MpFormato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
