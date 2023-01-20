package com.mpxds.mpbasic.model.enums;

public enum MpAlertaTipo {
	
	NOTIFICACAO("Notificação"),
	EMAIL("E-mail"),
	SMS("SMS");
	
	private String descricao;
	
	// ---
	
	MpAlertaTipo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return this.descricao; }

}