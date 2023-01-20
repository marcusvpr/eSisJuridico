package com.mpxds.mpbasic.model;

public class MpCep {
	//
	private String cep;

	private String logradouro;
	private String bairro;
	private String cidade;
	private String estadoUF; 

	// ---

	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }
	
	public String getLogradouro() { return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

	public String getCidade() { return cidade; }
	public void setCidade(String cidade) { this.cidade = cidade; }

	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }

	public String getEstadoUF() { return estadoUF; }
	public void setEstadoUF(String estadoUF) { this.estadoUF = estadoUF; }

}