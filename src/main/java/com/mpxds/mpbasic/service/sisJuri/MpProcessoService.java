package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProcessoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpProcessos mpProcessos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpProcesso salvar(MpProcesso mpProcesso) throws MpNegocioException {
		//
//		MpProcesso mpProcessoExistente = mpProcessos.porDataCadastro(mpProcesso.getDataCadastro());
//		
//		if (mpProcessoExistente != null && !mpProcessoExistente.equals(mpProcesso)) {
//			throw new MpNegocioException("Já existe um PROCESSO com a DATA informada.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpProcesso.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpProcesso.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpProcesso.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpProcessos.guardar(mpProcesso);
	}
	
}
