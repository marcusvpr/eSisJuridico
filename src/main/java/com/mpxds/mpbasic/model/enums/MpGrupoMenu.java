package com.mpxds.mpbasic.model.enums;

public enum MpGrupoMenu {
	//
	SENTINEL("Principal", "icon-gauge"), 
	RESPONSIVO("Responsivo", "icon-renren"), 
	CADASTRO("Cadastros", "icon-book"), 
	GRAFICO("Gráficos", "icon-chart-bar"), 
	CONTROLE("Controles", "icon-cube"),
	PEDIDO("Pedidos", "icon-th-1"),
	COMERCIO("Comércio", "icon-basket"),
	ENGENHARIA_REQUISITOS("Engenharia Requisitos", "icon-pin-outline"),
	RELATORIO("Relatórios", "icon-print"); 
	
	private String descricao;
	private String icon;
	
	// ---
	
	MpGrupoMenu(String descricao, String icon) {
		this.descricao = descricao;
		this.icon = icon;
	}

	public String getDescricao() { return descricao; }
	
	public String getIcon() { return icon; }
	
}
