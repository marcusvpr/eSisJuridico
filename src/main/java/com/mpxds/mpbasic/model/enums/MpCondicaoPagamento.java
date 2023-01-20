package com.mpxds.mpbasic.model.enums;

public enum MpCondicaoPagamento {

	AVISTA("À vista"), 
	DIAS30("30 Dias"),
	DIAS15_30_60("15 + 30 + 60 dias"),
	DIAS30_60("30 + 60 dias"),
	DIAS30_60_90("30 + 60 + 90 dias"),
	DIAS30_60_90_120("30 + 60 + 90 + 120 dias"),
	E_DIAS30("Entrada + 30 Dias"),
	E_DIAS15_30_60("Entrada + 15 + 30 + 60 dias"),
	E_DIAS30_60("Entrada + 30 + 60 dias"),
	E_DIAS30_60_90("Entrada + 30 + 60 + 90 dias"),
	E_DIAS30_60_90_120("Entrada + 30 + 60 + 90 + 120 dias");
	
	private String descricao;
	
	// ---
	
	MpCondicaoPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
