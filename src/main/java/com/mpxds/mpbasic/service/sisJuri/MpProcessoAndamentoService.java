package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpProcessoAndamento;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessoAndamentos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProcessoAndamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpProcessoAndamentos mpProcessoAndamentos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpProcessoAndamento salvar(MpProcessoAndamento mpProcessoAndamento) throws MpNegocioException {
		//
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpProcessoAndamento.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpProcessoAndamento.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpProcessoAndamento.getMpAuditoriaObjeto();
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpProcessoAndamentos.guardar(mpProcessoAndamento);
	}
	
}
