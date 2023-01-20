package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpRegraNegocio;
import com.mpxds.mpbasic.repository.engreq.MpRegraNegocios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpRegraNegocioService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpRegraNegocios mpRegraNegocios;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpRegraNegocio salvar(MpRegraNegocio mpRegraNegocio) throws MpNegocioException {
//		MpRegraNegocio mpRegraNegocioExistente = mpRegraNegocios.porTitulo(
//																					mpRegraNegocio.getTitulo());
//		//
//		if (mpRegraNegocioExistente != null && !mpRegraNegocioExistente.equals(mpRegraNegocio)) {
//			throw new MpNegocioException("Já existe regraNegocio com NOME informado. ( " +
//																						mpRegraNegocio.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpRegraNegocio.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpRegraNegocio.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpRegraNegocio.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpRegraNegocios.guardar(mpRegraNegocio);
	}

}
