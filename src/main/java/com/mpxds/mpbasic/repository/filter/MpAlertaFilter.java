package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpAlertaFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Boolean indEmail;
	private Boolean indSMS;
	private Boolean indPush;
	private Boolean indTelegram;
	private Boolean indWhatsapp;
	private Boolean indMpComunicator;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

  	public Boolean getIndEmail() { return this.indEmail; }
  	public void setIndEmail(Boolean newIndEmail) { this.indEmail = newIndEmail; }
  	   	
	public Boolean getIndSMS() { return this.indSMS; }
	public void setIndSMS(Boolean newIndSMS) { this.indSMS = newIndSMS; }
	   	
	public Boolean getIndPush() { return this.indPush; }
	public void setIndPush(Boolean newIndPush) { this.indPush = newIndPush; }
   	
	public Boolean getIndTelegram() { return this.indTelegram; }
	public void setIndTelegram(Boolean newIndTelegram) { this.indTelegram = newIndTelegram; }
	   	
	public Boolean getIndWhatsapp() { return this.indWhatsapp; }
	public void setIndWhatsapp(Boolean newIndWhatsapp) { this.indWhatsapp = newIndWhatsapp; }
   	
	public Boolean getIndMpComunicator() { return this.indMpComunicator; }
	public void setIndMpComunicator(Boolean newIndMpComunicator) { 
												this.indMpComunicator = newIndMpComunicator; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}