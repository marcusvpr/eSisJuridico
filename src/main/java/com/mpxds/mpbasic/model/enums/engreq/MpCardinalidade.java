package com.mpxds.mpbasic.model.enums.engreq;

public enum MpCardinalidade {

	NAO_APLICA("N/A"),
	ONE_TO_ONE("Um para Um (1:1)"),
	ONE_TO_MANY("Um para Muitos (1:N)"),
	MANY_TO_ONE("Muitos para Um (N:1)"),
	MANY_TO_MANY("Muitos para Muitos (N:N)");
	
	private String descricao;
	
	// ---
	
	MpCardinalidade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
