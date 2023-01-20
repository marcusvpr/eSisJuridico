package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpFuncionalidade;
import com.mpxds.mpbasic.repository.engreq.MpFuncionalidades;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpFuncionalidadeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpFuncionalidades mpFuncionalidades;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpFuncionalidade salvar(MpFuncionalidade mpFuncionalidade) throws MpNegocioException {
//		MpFuncionalidade mpFuncionalidadeExistente = mpFuncionalidades.porTitulo(mpFuncionalidade.getTitulo());
//		//
//		if (mpFuncionalidadeExistente != null && !mpFuncionalidadeExistente.equals(mpFuncionalidade)) {
//			throw new MpNegocioException("Já existe funcionalidade com NOME informado. ( " + mpFuncionalidade.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpFuncionalidade.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpFuncionalidade.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpFuncionalidade.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpFuncionalidades.guardar(mpFuncionalidade);
	}

}
