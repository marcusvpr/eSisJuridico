package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCalendarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCalendarios mpCalendarios;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpCalendario salvar(MpCalendario mpCalendario) throws MpNegocioException {
		MpCalendario mpCalendarioExistente = mpCalendarios.porDtHrMovimento(
																mpCalendario.getDataMovimento());
		
		if (mpCalendarioExistente != null && !mpCalendarioExistente.equals(mpCalendario)) {
			throw new MpNegocioException("Já existe um calendario com a Data informada.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpCalendario.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpCalendario.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpCalendario.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpCalendarios.guardar(mpCalendario);
	}

}
