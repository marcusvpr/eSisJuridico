package com.mpxds.mpbasic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;

@Entity
@Table(name="mp_alerta")
public class MpAlerta extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Boolean indEmail;
	private Boolean indSMS;
	private Boolean indPush;
	private Boolean indTelegram;
	private Boolean indWhatsapp;
	private Boolean indMpComunicator;

	private Boolean indRespostaUsuario;

	// ----------
	
	public MpAlerta() {
		super();
	}

  	public MpAlerta(Boolean indEmail
             , Boolean indSMS
             , Boolean indPush
             , Boolean indTelegram
             , Boolean indWhatsapp
             , Boolean indMpComunicator
             , Boolean indRespostaUsuario
             ) {
  		this.indEmail = indEmail;
  		this.indSMS = indSMS;
  		this.indPush = indPush;
  		this.indTelegram = indTelegram;
  		this.indWhatsapp = indWhatsapp;
  		this.indMpComunicator = indMpComunicator;
  		this.indRespostaUsuario = indRespostaUsuario;
  	}
 	   	
  	@Column(name = "ind_email", nullable = true)
  	public Boolean getIndEmail() { return this.indEmail; }
  	public void setIndEmail(Boolean newIndEmail) { this.indEmail = newIndEmail; }
  	   	
	@Column(name = "ind_sms", nullable = true)
	public Boolean getIndSMS() { return this.indSMS; }
	public void setIndSMS(Boolean newIndSMS) { this.indSMS = newIndSMS; }
	   	
	@Column(name = "ind_push", nullable = true)
	public Boolean getIndPush() { return this.indPush; }
	public void setIndPush(Boolean newIndPush) { this.indPush = newIndPush; }
   	
	@Column(name = "ind_telegram", nullable = true)
	public Boolean getIndTelegram() { return this.indTelegram; }
	public void setIndTelegram(Boolean newIndTelegram) { this.indTelegram = newIndTelegram; }
	   	
	@Column(name = "ind_whatsapp", nullable = true)
	public Boolean getIndWhatsapp() { return this.indWhatsapp; }
	public void setIndWhatsapp(Boolean newIndWhatsapp) { this.indWhatsapp = newIndWhatsapp; }
   	
	@Column(name = "ind_mp_comunicator", nullable = true)
	public Boolean getIndMpComunicator() { return this.indMpComunicator; }
	public void setIndMpComunicator(Boolean newIndMpComunicator) { 
												this.indMpComunicator = newIndMpComunicator; }
   	
	@Column(name = "ind_resposta_usuario", nullable = true)
	public Boolean getIndRespostaUsuario() { return this.indRespostaUsuario; }
	public void setIndRespostaUsuario(Boolean newIndRespostaUsuario) { 
											this.indRespostaUsuario = newIndRespostaUsuario; }

	// ---
	
	@Transient
	public String getConfiguracao() {
		//
		String result = "";
		
		if (this.indEmail) result = result + "(Email)";
		if (this.indSMS) result = result + "(SMS)";
		if (this.indPush) result = result + "(Push)";
		if (this.indTelegram) result = result + "(Telegram)";
		if (this.indWhatsapp) result = result + "(Whatsapp)";
		if (this.indMpComunicator) result = result + "(MpCom)";
		if (this.indRespostaUsuario) result = result + "(RespUsu)";
		//
		return result;
	}
}
