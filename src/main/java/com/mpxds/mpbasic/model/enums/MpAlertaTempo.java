package com.mpxds.mpbasic.model.enums;

public enum MpAlertaTempo {
	
	A03M(3, "3(três) minutos"),
	A05M(5, "5(cinco) minutos"),
	A10M(10, "10(dez) minutos"),
	A15M(15, "15(quinze) minutos"),
	A30M(30, "30(trinta) minutos"),
	A01H(60, "1(hora)"),
	A02H(120, "2(horas)"),
	A01D(1440, "1(dia)");
	
	private Integer tempo;
	private String descricao;
	
	// ---
	
	MpAlertaTempo(Integer tempo, String descricao) {
		this.tempo = tempo;
		this.descricao = descricao;
	}

	public Integer getTempo() { return this.tempo; }
	
	public String getDescricao() { return this.descricao; }
	
	public String getDescricaoTempo() {	return this.descricao + " - [ " + this.tempo + " ]"; }
}