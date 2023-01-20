package com.mpxds.mpbasic.model.enums;

public enum MpProfissaoPessoa {

	CABELO("Cabeleleira(o)/Barbeiro", false),
	COMERCIO("Comércio", false),
	CUIDADOR("Cuidador(a)", false),
	DIARISTA("Diarista", false),
	DENTISTA("Dentista", false),
	EMPREGADA("Empregada(o)", false),
	ENFERMEIRA("Enfermeira(o)", false),
	FAMILIA("Familia", false),
	FISIOTERAPEUTA("Fisioterapeuta", false),
	FONOAUDIOLOGO("Fonoaudiólogo", false),
	MANICURE("Manicure", false),
	MASSAGISTA("Massagista", false),
	MEDICO("Médica(o)", false),
	TEC_ENFERMAGEM("Técnica(o) Enfermagem", false),
	OUTRO("Outra(o)", false),
	
	VENDEDOR("Vendedor(a)", true),
	ENTREGADOR("Entregador(a)", true),
	GARÇON("Garçon", true),
	OUTROSK("Outra(o).", true);
	
	private String descricao;
	private Boolean indControleEstoque;
	
	// ---

	MpProfissaoPessoa(String descricao,
			   		  Boolean indControleEstoque) {
		this.descricao = descricao;
		this.indControleEstoque = indControleEstoque;
	}
	
	public String getDescricao() { return descricao; }

	public Boolean getIndControleEstoque() { return indControleEstoque; }
	
}