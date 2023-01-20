package com.mpxds.mpbasic.model.enums;

public enum MpPlanoPagamento {
	M("M", "Mensal", 0.0, 30.0),
	T("T", "Trimestral", 10.0, 81.0),  // 10%
	S("S", "Semestral", 20.0, 144.0),  // 20%
	A("A", "Anual", 30.0, 252.0);      // 30%     
	
	private String plano;
	private String descricao;
	private Double desconto;
	private Double valor;
	
	// ----
	
	private MpPlanoPagamento(String plano, String descricao, Double desconto, Double valor) {
			this.plano = plano;
			this.descricao = descricao;
			this.desconto = desconto;
			this.valor = valor;
	}
	
	public String getPlano() {
		return plano;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public Double getDesconto() {
		return desconto;
	}
	
	public String getDescricaoValor() {
		return descricao + " = R$ " + valor.toString().replace(".0", ",00");
	}

}