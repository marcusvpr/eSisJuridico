package com.mpxds.mpbasic.model.xml;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="result")
public class MpCepXML {
	//
	private String complemento;
	private String bairro;
	private String cidade;
	private String logradouro;
	private MpEstado_info estado_info;
	private String cep;
	private MpCidade_info cidade_info;
	private String estado;
	//
	@XmlElement(name="complemento")
	public String getComplemento() { return complemento; }
	public void setComplemento(String newComplemento) { this.complemento = newComplemento; }
	
	@XmlElement(name="bairro")
	public String getBairro() { return bairro; }
	public void setBairro(String newBairro) { this.bairro = newBairro; }
	
	@XmlElement(name="cidade")
	public String getCidade() { return cidade; }
	public void setCidade(String newCidade) { this.cidade = newCidade; }
	
	@XmlElement(name="logradouro")
	public String getLogradouro() { return logradouro; }
	public void setLogradouro(String newLogradouro) { this.logradouro = newLogradouro; }
	
	@XmlElement(name="estado_info")
	public MpEstado_info getEstado_info() { return estado_info; }
	public void setEstado_info(MpEstado_info newEstadoInfo) { this.estado_info = newEstadoInfo; }
	
	@XmlElement(name="cep")
	public String getCep() { return cep; }
	public void setCep(String newCep) { this.cep = newCep; }
	
	@XmlElement(name="cidade_info")
	public MpCidade_info getCidade_info() { return cidade_info; }
	public void setCidade_info(MpCidade_info newCidadeInfo) { this.cidade_info = newCidadeInfo; }
	
	@XmlElement(name="estado")
	public String getEstado() { return estado; }
	public void setEstado(String newEstado) { this.estado = newEstado; }

}
