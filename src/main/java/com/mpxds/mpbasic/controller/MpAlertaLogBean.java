package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.model.enums.MpAlertaStatus;
import com.mpxds.mpbasic.repository.MpAlertaLogs;
import com.mpxds.mpbasic.model.MpUsuario;

@Named
@SessionScoped
public class MpAlertaLogBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlertaLogs mpAlertaLogs;
	
	@Inject
	private MpUsuario mpUsuario;

	// Tipo Alerta (AL=Alarme/AT=Atividade/CA=Calendario/ER=EstoqueReposicao ...
	@ManagedProperty(value = "#{param.idAlT}")
	private String idAlT;
	// Id Alerta ...
	@ManagedProperty(value = "#{param.idAl}")
	private String idAl;
	// Param.Alarme(Co=Confirmar/Ad-Adiar/Ca=Cancelar) ...
	@ManagedProperty(value = "#{param.idAlP}")
	private String idAlP;
	// ======================================
	
	private static final Logger logger = LogManager.getLogger(MpAlertaLogBean.class);
	
	//----------------
		
	public void preRender() {
		//		
		// --- logs debug
		if (logger.isDebugEnabled()) logger.debug("MpAlertaLogBean.preRender()");
		
		// Trata Alerta (Alarme=Confirmar/Adiar) ...
		if (null == this.idAlT) this.idAlT = "null";
		if (null == this.idAl) this.idAl = "null";
		if (null == this.idAlP) this.idAlP = "null";
		//
		if (this.idAlT.equals("AL") // ALARME...
		||  this.idAlT.equals("AT") // ATIVIDADE...
		||  this.idAlT.equals("CA") // CALENDÁRIO...
		||  this.idAlT.equals("ER")) // ESTOQUE REPOSIÇÃO... 
			if (!this.idAl.equals("null") && !this.idAlP.equals("null"))
				this.trataAlertaLog();
		//
	}

	public void trataAlertaLog() {
		//		
		MpAlertaLog mpAlertaLog = mpAlertaLogs.porId(Long.parseLong(this.idAl));
		if (null == mpAlertaLog) 
			assert(true); // Ignora !
		else {
			if (this.idAlP.equals("Co")) // Confirmar Alarme ...
				mpAlertaLog.setMpAlertaStatus(MpAlertaStatus.LIDO);
			else
			if (this.idAlP.equals("Ad05")) // Adiar (05 minutos)  Alarme ...
				mpAlertaLog.setMpAlertaStatus(MpAlertaStatus.A05M);
			else
			if (this.idAlP.equals("Ad15")) // Adiar (15 minutos)  Alarme ...
				mpAlertaLog.setMpAlertaStatus(MpAlertaStatus.A15M);
			else
			if (this.idAlP.equals("Ca")) // Cancelar Alarme ...
				mpAlertaLog.setMpAlertaStatus(MpAlertaStatus.CANCELADO);
			
			mpAlertaLogs.guardar(mpAlertaLog);		
			//
		}
	}
		
	// ---------------------------

	public String getIdAlT() { return idAlT; }
	public void setIdAlT(String newIdAlT) { this.idAlT = newIdAlT; }
	public String getIdAl() { return idAl; }
	public void setIdAl(String newIdAl) { this.idAl = newIdAl; }
	public String getIdAlP() { return idAlP; }
	public void setIdAlP(String newIdAlP) { this.idAlP = newIdAlP; }
	
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }
}