package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.repository.MpTurnos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTurnoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpTurnos mpTurnos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpTurno salvar(MpTurno mpTurno) throws MpNegocioException {
		MpTurno mpTurnoExistente = mpTurnos.porDescricao(mpTurno.getDescricao());
		
		if (mpTurnoExistente != null && !mpTurnoExistente.equals(mpTurno)) {
			throw new MpNegocioException("Já existe um TURNO com a DESCRIÇÃO informada.");
		}
		
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpTurno.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());

			// Trata Multitenancy ...
			if (!mpSeguranca.getMpUsuarioLogado().getMpUsuario().getTenantId().trim().
																				equals("0"))
				mpTurno.setTenantId(mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																				getTenantId());
			//
		} else {
			mpAuditoriaObjeto = mpTurno.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpTurno.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpTurnos.guardar(mpTurno);
	}
	
}
