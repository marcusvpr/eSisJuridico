package com.mpxds.mpbasic.model.enums;

public enum MpTipoJornada {
	
	T6_24 ("Turno 06 por 24", 6, 24),
	T8_24 ("Turno 08 por 24", 8, 24),
	T11_72 ("Turno 11 por 72", 11, 72),
	T12_36 ("Turno 12 por 36", 12, 36),
	T12_48 ("Turno 12 por 48", 12, 48),
	T12_144("Turno 12 por 144", 12, 144),
	T24_72 ("Turno 24 por 72", 24, 72);
	
	private String descricao;
	private Integer numeroHorasTrabalha;
	private Integer numeroHorasFolga;
	
	// ---
	
	MpTipoJornada(String descricao, Integer numeroHorasTrabalha, Integer numeroHorasFolga) {
		this.descricao = descricao;
		this.numeroHorasTrabalha = numeroHorasTrabalha;
		this.numeroHorasFolga = numeroHorasFolga;
	}

	public String getDescricao() { return this.descricao; }
	
	public Integer getNumeroHorasTrabalha() { return this.numeroHorasTrabalha; }
	
	public Integer getNumeroHorasFolga() { return this.numeroHorasFolga; }

}