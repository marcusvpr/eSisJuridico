package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.repository.MpAlertaLogs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAlertaLogService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlertaLogs mpAlertaLogs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpAlertaLog salvar(MpAlertaLog mpAlertaLog) throws MpNegocioException {
		MpAlertaLog mpAlertaLogExistente = mpAlertaLogs.porDataMovimento(
																mpAlertaLog.getDataMovimento());
		
		if (mpAlertaLogExistente != null && !mpAlertaLogExistente.equals(mpAlertaLog)) {
			throw new MpNegocioException("Já existe um alertaLog com a Data informada.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();

		if (null == mpAlertaLog.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpAlertaLog.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpAlertaLog.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpAlertaLogs.guardar(mpAlertaLog);
	}
	
}
