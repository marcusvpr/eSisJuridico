package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpClienteFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nome;
	private String email;
	private String documentoReceitaFederal;
	private String tipoPessoa;
	private String status;
	private String sexo;
	private String estadoCivil;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---
	
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getDocumentoReceitaFederal() { return documentoReceitaFederal; }
	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
								this.documentoReceitaFederal = documentoReceitaFederal;	}

	public String getTipoPessoa() { return tipoPessoa; }
	public void setTipoPessoa(String tipoPessoa) { this.tipoPessoa = tipoPessoa; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	public String getSexo() { return sexo; }
	public void setSexo(String sexo) { this.sexo = sexo; }

	public String getEstadoCivil() { return estadoCivil; }
	public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}