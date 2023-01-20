package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpRequisitoNaoFuncional;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoNaoFuncionals;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpRequisitoNaoFuncionalService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpRequisitoNaoFuncionals mpRequisitoNaoFuncionals;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpRequisitoNaoFuncional salvar(MpRequisitoNaoFuncional mpRequisitoNaoFuncional) throws MpNegocioException {
//		MpRequisitoNaoFuncional mpRequisitoNaoFuncionalExistente = mpRequisitoNaoFuncionals.porTitulo(
//																					mpRequisitoNaoFuncional.getTitulo());
//		//
//		if (mpRequisitoNaoFuncionalExistente != null && !mpRequisitoNaoFuncionalExistente.equals(
//																					mpRequisitoNaoFuncional)) {
//			throw new MpNegocioException("Já existe requisitoFuncional com NOME informado. ( " +
//																				mpRequisitoNaoFuncional.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpRequisitoNaoFuncional.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpRequisitoNaoFuncional.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpRequisitoNaoFuncional.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpRequisitoNaoFuncionals.guardar(mpRequisitoNaoFuncional);
	}

}
