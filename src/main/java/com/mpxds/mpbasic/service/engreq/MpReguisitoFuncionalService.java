package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpRequisitoFuncional;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoFuncionals;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpReguisitoFuncionalService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpRequisitoFuncionals mpRequisitoFuncionals;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpRequisitoFuncional salvar(MpRequisitoFuncional mpRequisitoFuncional) throws MpNegocioException {
//		MpRequisitoFuncional mpRequisitoFuncionalExistente = mpRequisitoFuncionals.porTitulo(
//																					mpRequisitoFuncional.getTitulo());
//		//
//		if (mpRequisitoFuncionalExistente != null && !mpRequisitoFuncionalExistente.equals(mpRequisitoFuncional)) {
//			throw new MpNegocioException("Já existe requisitoFuncional com NOME informado. ( " +
//																						mpRequisitoFuncional.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpRequisitoFuncional.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpRequisitoFuncional.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpRequisitoFuncional.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpRequisitoFuncionals.guardar(mpRequisitoFuncional);
	}

}
