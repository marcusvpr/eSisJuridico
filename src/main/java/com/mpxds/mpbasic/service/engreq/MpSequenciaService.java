package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpSequencia;
import com.mpxds.mpbasic.repository.engreq.MpSequencias;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpSequenciaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpSequencias mpSequencias;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpSequencia salvar(MpSequencia mpSequencia) throws MpNegocioException {
//		MpSequencia mpSequenciaExistente = mpSequencias.porTitulo(mpSequencia.getTitulo());
//		//
//		if (mpSequenciaExistente != null && !mpSequenciaExistente.equals(mpSequencia)) {
//			throw new MpNegocioException("Já existe sequencia com NOME informado. ( " +	mpSequencia.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpSequencia.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpSequencia.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpSequencia.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpSequencias.guardar(mpSequencia);
	}

}
