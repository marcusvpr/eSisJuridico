package com.mpxds.mpbasic.repository.filter.pt05;

import java.io.Serializable;

import com.mpxds.mpbasic.repository.filter.MpFilterOrdenacao;

public class MpCustasComposicaoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String tabela;
	private String item;
	private String subItem;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getTabela() {	return tabela; }
	public void setTabela(String tabela) { this.tabela = tabela; }
	
	public String getItem() { return item; }
	public void setItem(String item) { this.item = item; }
	
	public String getSubItem() { return subItem; }
	public void setSubItem(String subItem) { this.subItem = subItem; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}