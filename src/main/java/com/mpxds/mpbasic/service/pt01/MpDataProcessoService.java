package com.mpxds.mpbasic.service.pt01;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt01.MpDataProcesso;
import com.mpxds.mpbasic.repository.pt01.MpDataProcessos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpDataProcessoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpDataProcessos mpDataProcessos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpDataProcesso salvar(MpDataProcesso mpDataProcesso) throws MpNegocioException {
		//
		MpDataProcesso mpDataExistente = mpDataProcessos.porDataProtocolo(
															mpDataProcesso.getDataProtocolo());

		if (mpDataExistente != null && !mpDataExistente.equals(mpDataProcesso)) {
			throw new MpNegocioException("Já existe uma DATA PROTOCOLO... com a DATA informada.");
		}
		
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpDataProcesso.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpDataProcesso.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpDataProcesso.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpDataProcessos.guardar(mpDataProcesso);
	}
	
}
