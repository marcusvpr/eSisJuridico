package com.mpxds.mpbasic.model.enums;

public enum MpEstadoUF {

	RJ("Rio de Janeiro"),
	SP("São Paulo"),
	AC("Acre"),
	AL("Alagoas"),
	AP("Amapá"),
	AM("Amazonas"),
	BA("Bahia"),
	CE("Ceará"),
	XX("Desconhecido"),
	DF("Distrito Federal"),
	ES("Espírito Santo"),
	FN("Fernando de Noronha"),
	GO("Goiás"),
	MA("Maranhão"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Pará"),
	PB("Paraíba"),
	PR("Paraná"),
	PE("Pernambuco"),
	PI("Piauí"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	RO("Rondônia"),
	RR("Roraima"),
	SC("Santa Catarina"),
	SE("Sergipe"),
	TO("Tocantins");

	// ---
	
	private String descricao;
	
	// ---
	
	MpEstadoUF(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }

}