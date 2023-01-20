package com.mpxds.mpbasic.model.xml;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="negociacao")
public class MpNegociacaoXML {
	//
	private Double preco;
	private Integer quantidade;
	private Calendar data;
	//
	@XmlElement(name="preco")
	public Double getPreco() { return preco; }
	public void setPreco(Double newPreco) { this.preco = newPreco; }
	
	@XmlElement(name="quantidade")
	public Integer getQuantidade() { return quantidade; }
	public void setQuantidade(Integer newQuantidade) { this.quantidade = newQuantidade; }
	
	@XmlElement(name="data")
	public Calendar getData() { return data; }
	public void setData(Calendar newData) { this.data = newData; }
}
