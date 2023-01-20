package com.mpxds.mpbasic.model.vo;

import java.io.Serializable;

public class MpDadoQuantidade implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String dado;
	private Integer quantidade;

	// ---
	 
	 public MpDadoQuantidade() {
		 super();
	 }
	 
	 public MpDadoQuantidade(String dado,
			 				 int quantidade) {
		 this.dado = dado;
		 this.quantidade = quantidade;
	 }	
	public String getDado() { return dado; }
	public void setDado(String dado) { this.dado = dado; }

	public Integer getQuantidade() { return quantidade; }
	public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

}
