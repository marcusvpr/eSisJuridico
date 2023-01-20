package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpNotificacaoUsuarioFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private String usuarioFrom;
	private String usuario;
	private String assunto;
	private String mensagem;
	private String status;
	private String tipoPrioridade;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataDe() { return dataDe; }
	public void setDataDe(Date dataDe) { this.dataDe = dataDe; 	}

	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }
	
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	
	public String getUsuarioFrom() { return usuarioFrom; }
	public void setUsuarioFrom(String usuarioFrom) { this.usuarioFrom = usuarioFrom; }
	
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }
	
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	public String getTipoPrioridade() { return tipoPrioridade; }
	public void setTipoPrioridade(String tipoPrioridade) { this.tipoPrioridade = tipoPrioridade; }

	// ----
	
	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}