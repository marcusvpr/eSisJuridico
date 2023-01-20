package com.mpxds.mpbasic.model.enums.engreq;

public enum MpRelatorioER {

	REL_ER_DE("ER", "Declaração de Escopo", true, true, false),
	REL_ER_DCMR("ER", "Documento de Coleta de Macro Requisitos", true, true, false),
	REL_ER_DRN("ER", "Documento de Regras de Negócio", true, true, false),
	REL_ER_DRF("ER", "Documento de Requisitos Funcionais", true, true, false),
	REL_ER_DRNF("ER", "Documento de Requisitos Não Funcionais", true, true, false),
	REL_ER_EN("ER", "Escopo Negativo", true, true, false),
	REL_ER_F("ER", "Fronteiras", true, true, false),
	REL_ER_LPI("ER", "Lista das Partes Interessadas", true, true, false),
	REL_ER_MRRACI("ER", "Matriz de Responsabilidades RACI", true, true, false),

	REL_CAD_Projeto("CAD", "Cadastro Projeto", true, true, false),
	REL_CAD_PessoaER("CAD", "Cadastro Pessoa", true, true, false),
	REL_CAD_Objeto("CAD", "Cadastro Objeto", true, true, false);
	
	private String grupo;
	private String relatorio;
	private Boolean indDoc;
	private Boolean indPdf;
	private Boolean indXls;
	
	// ---
	
	MpRelatorioER(String grupo, String relatorio, Boolean indDoc, Boolean indPdf, Boolean indXls) {
		this.grupo = grupo;
		this.relatorio = relatorio;
		this.indDoc = indDoc;
		this.indPdf = indPdf;
		this.indXls = indXls;
	}

	public String getGrupo() { return grupo; }

	public String getRelatorio() { return relatorio; }

	public String getGrupoRelatorio() { return grupo + " / " + relatorio; }

	public Boolean getIndDoc() { return indDoc; }
	public Boolean getIndPdf() { return indPdf; }
	public Boolean getIndXls() { return indXls; }

}
