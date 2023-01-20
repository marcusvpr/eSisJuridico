package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.repository.MpDolars;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpDolarService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpDolars mpDolars;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpDolar salvar(MpDolar mpDolar) throws MpNegocioException {
		//
		MpDolar mpDolarExistente = mpDolars.porDtHrMovimento(mpDolar.getDataMovimento());
		
		if (mpDolarExistente != null && !mpDolarExistente.equals(mpDolar)) {
			throw new MpNegocioException("Já existe um DÓLAR com a Data informada.");
		}
		// INICIO - Trata dados auditoria ! -----------------------------------
		mpDolar.setMpAuditoriaObjeto(mpSeguranca.trataMpAuditoriaObjeto("MpDolar", mpDolar));
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpDolars.guardar(mpDolar);
	}
	
}
