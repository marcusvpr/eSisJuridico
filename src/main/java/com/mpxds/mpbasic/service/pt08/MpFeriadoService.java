package com.mpxds.mpbasic.service.pt08;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt08.MpFeriado;
import com.mpxds.mpbasic.repository.pt08.MpFeriados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpFeriadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpFeriados mpFeriados;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpFeriado salvar(MpFeriado mpFeriado) throws MpNegocioException {
		//
		MpFeriado mpFeriadoExistente = mpFeriados.porDataFeriado(mpFeriado.getDataFeriado());
		
		if (mpFeriadoExistente != null && !mpFeriadoExistente.equals(mpFeriado)) {
			throw new MpNegocioException("Já existe um FERIADO com a Data informada.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpFeriado.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpFeriado.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpFeriado.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpFeriados.guardar(mpFeriado);
	}
	
}
