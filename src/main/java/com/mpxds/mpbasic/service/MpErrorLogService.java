package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.log.MpErrorLog;
import com.mpxds.mpbasic.repository.MpErrorLogs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpErrorLogService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpErrorLogs mpErrorLogs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpErrorLog salvar(MpErrorLog mpErrorLog) throws MpNegocioException {
//		MpErrorLog mpErrorLogExistente =
//										mpErrorLogs.porDataCr(mpErrorLog.getParametro());
//		
//		if (mpErrorLogExistente != null && !mpErrorLogExistente.equals(mpErrorLog)) {
//			throw new MpNegocioException("Já existe um registro com o Parâmetro informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpErrorLog.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpErrorLog.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpErrorLog.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpErrorLogs.guardar(mpErrorLog);
	}
		
}
