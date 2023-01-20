package com.mpxds.mpbasic.model.enums;

public enum MpAlertaStatus {
	
	LIDO("Lido"),
	CANCELADO("Cancelado"),
	A05M("Adiar 05(cinco) minutos"),
	A15M("Adiar 15(quinze) minutos");
	
	private String descricao;
	
	// ---
	
	MpAlertaStatus(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { return this.descricao; }
	
}