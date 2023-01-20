package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.log.MpSistemaLog;
import com.mpxds.mpbasic.repository.MpSistemaLogs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpSistemaLogService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpSistemaLogs mpSistemaLogs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpSistemaLog salvar(MpSistemaLog mpSistemaLog) throws MpNegocioException {
//		MpSistemaLog mpSistemaLogExistente =
//										mpSistemaLogs.porDataCr(mpSistemaLog.getParametro());
//		
//		if (mpSistemaLogExistente != null && !mpSistemaLogExistente.equals(mpSistemaLog)) {
//			throw new MpNegocioException("Já existe um registro com o Parâmetro informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpSistemaLog.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpSistemaLog.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpSistemaLog.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpSistemaLogs.guardar(mpSistemaLog);
	}
		
}
