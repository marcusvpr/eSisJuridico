package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.repository.MpReceitas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpReceitaService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpReceitas mpReceitas;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpReceita salvar(MpReceita mpReceita) throws MpNegocioException {

		// INICIO - Trata dados auditoria ! -----------------------------------
		mpReceita.setMpAuditoriaObjeto(mpSeguranca.trataMpAuditoriaObjeto("MpReceita",
																				mpReceita));
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpReceitas.guardar(mpReceita);
	}
	
}
