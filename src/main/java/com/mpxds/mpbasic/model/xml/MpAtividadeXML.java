package com.mpxds.mpbasic.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="mpAtividade")
public class MpAtividadeXML {
	//
	private int id;
	private String dataHora;  
	private int duracao; // Em minutos!  
	private String descricao;
	private int quantidade;
	private String observacao;

	// ---

	@XmlAttribute
    public int getId() { return id; }
	public void setId(int newId) { this.id = newId; }
	
	@XmlElement(name="dataHora")
	public String getDataHora() { return this.dataHora; }
	public void setDataHora(String newDataHora) { this.dataHora = newDataHora; }

	@XmlElement(name="duracao")
	public int getDuracao() { return duracao; }
	public void setDuracao(int duracao) { this.duracao = duracao; }

	@XmlElement(name="descricao")
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	@XmlElement(name="quantidade")
	public int getQuantidade() { return quantidade; }
	public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
	   	  	
	@XmlElement(name="observacao")
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String newObservacao) { this.observacao = newObservacao; }

}
