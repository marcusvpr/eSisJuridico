package com.mpxds.mpbasic.model.enums;

public enum MpStatusNotificacao {

	NOVA("Nova"), 
	LIDA("Lida"), 
	VISUALIZADA("Visualizada"), 
	CANCELADA("Cancelada");
	
	private String descricao;
	
	// ---
	
	MpStatusNotificacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
