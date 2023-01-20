package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpNotificacaoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private String mensagem;
	private String tipoNotificacao;
	private String tipoPrioridade;
	private String status;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataDe() { return dataDe; }
	public void setDataDe(Date dataDe) { this.dataDe = dataDe; }
	
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }
	
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }

	public String getTipoNotificacao() { return tipoNotificacao; }
	public void setTipoNotificacao(String tipoNotificacao) { 
														this.tipoNotificacao = tipoNotificacao; }

	public String getTipoPrioridade() { return tipoPrioridade; }
	public void setTipoPrioridade(String tipoPrioridade) { this.tipoPrioridade = tipoPrioridade; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}