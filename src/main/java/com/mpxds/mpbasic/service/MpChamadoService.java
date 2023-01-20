package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpChamado;
import com.mpxds.mpbasic.repository.MpChamados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpChamadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpChamados mpChamados;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpChamado salvar(MpChamado mpChamado) throws MpNegocioException {
//		MpChamado mpChamadoExistente =
//										mpChamados.porDataCr(mpChamado.getParametro());
//		
//		if (mpChamadoExistente != null && !mpChamadoExistente.equals(mpChamado)) {
//			throw new MpNegocioException("Já existe um registro com o Parâmetro informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto  = new MpAuditoriaObjeto();
		
		if (null == mpChamado.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpChamado.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpChamado.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpChamados.guardar(mpChamado);
	}
		
}
