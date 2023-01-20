package com.mpxds.mpbasic.model.enums.adricred;

public enum MpTipoConta {
	
	INDIVIDUAL("Individual"),
	CONJUNTA("Conjunta");
	
	private String descricao;
	
	// ---
	
	MpTipoConta(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return this.descricao; }

}