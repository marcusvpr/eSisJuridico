package com.mpxds.mpbasic.model.enums;

public enum MpChamadoSeveridade {

	ALTA("Alta (04 horas)"), 
	MEDIA("Média (08 horas)"), 
	BAIXA("Baixa (24 horas)"), 
	NAOAPLICA("Não se aplica"); 
	
	private String descricao;
	
	// ---
	
	MpChamadoSeveridade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
