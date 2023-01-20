package com.mpxds.mpbasic.model.vo;

import java.io.Serializable;
import java.util.Date;

public class MpDataQuantidade implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date data;
	private Integer dia;
	private Integer mes;
	private Integer ano;
	private Long valor;

	// ---
	
	public Date getData() { return data; }
	public void setData(Date data) { this.data = data; }

	public Integer getDia() { return dia;}
	public void setDia(Integer dia) { this.dia = dia; }
	public Integer getMes() { return mes;}
	public void setMes(Integer mes) { this.mes = mes; }
	public Integer getAno() { return ano;}
	public void setAno(Integer ano) { this.ano = ano; }
	
	public Long getValor() { return valor; }
	public void setValor(Long valor) { this.valor = valor; }

}
