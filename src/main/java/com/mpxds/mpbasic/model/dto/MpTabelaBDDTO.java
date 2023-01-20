package com.mpxds.mpbasic.model.dto;

public class MpTabelaBDDTO {

	private Integer numTabela;
	private String tabela;
	private String entidade;
	private String descricao;
	private Long numeroRegistros;

	// ---
	
	public Integer getNumTabela() { return numTabela; }
	public void setNumTabela(Integer numTabela) { this.numTabela = numTabela; }
	
	public String getTabela() {	return tabela; }
	public void setTabela(String tabela) { this.tabela = tabela; }

	public String getEntidade() { return entidade; 	}
	public void setEntidade(String entidade) { this.entidade = entidade; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
	
	public Long getNumeroRegistros() { return numeroRegistros; }
	public void setNumeroRegistros(Long numeroRegistros) {this.numeroRegistros = numeroRegistros; }

}