package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpMacroRequisito;
import com.mpxds.mpbasic.repository.engreq.MpMacroRequisitos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpMacroRequisitoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpMacroRequisitos mpMacroRequisitos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpMacroRequisito salvar(MpMacroRequisito mpMacroRequisito) throws MpNegocioException {
//		MpMacroRequisito mpMacroRequisitoExistente = mpMacroRequisitos.porTitulo(mpMacroRequisito.getTitulo());
//		//
//		if (mpMacroRequisitoExistente != null && !mpMacroRequisitoExistente.equals(mpMacroRequisito)) {
//			throw new MpNegocioException("Já existe macroRequisito com NOME informado. ( " +	mpMacroRequisito.getTitulo());
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpMacroRequisito.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpMacroRequisito.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpMacroRequisito.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpMacroRequisitos.guardar(mpMacroRequisito);
	}

}
