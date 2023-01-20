package com.mpxds.mpbasic.model.enums;

public enum MpFeriadoOficial {

	ANONOVO("01/01", "Ano Novo"),
	TIRADENTES("21/04", "Tiradentes"),
	TRABALHO("01/05", "Dia do Trabalho"),
	INDEPENDENCIA("07/09", "Independência do Brasil"),
	NSAPARECIDA("12/10", "Nossa Sr.a Aparecida"),
	FINADOS("02/11", "Finados"),
	PROCLAMACAO("15/11", "Proclamação da República"),
	NATAL("25/12", "Natal");		
	
	private String dataEvento;
	private String descricao;
	
	// ---

	MpFeriadoOficial(String dataEvento, String descricao) {
		this.dataEvento = dataEvento;
		this.descricao = descricao;
	}
	
	public String getDataEvento() { return dataEvento; }
	
	public String getDescricao() { return descricao; }

}