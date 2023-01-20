package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAlarmeService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlarmes mpAlarmes;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpAlarme salvar(MpAlarme mpAlarme) throws MpNegocioException {
		//
		MpAlarme mpAlarmeExistente = mpAlarmes.porHoraMovimento(mpAlarme.getHoraMovimento());
		
		if (mpAlarmeExistente != null && !mpAlarmeExistente.equals(mpAlarme)) {
			throw new MpNegocioException("Já existe um ALARME com a HORA informada.");
		}
		
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpAlarme.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());

			// Trata Multitenancy ...
			if (!mpSeguranca.getMpUsuarioLogado().getMpUsuario().getTenantId().trim().equals("0"))
				mpAlarme.setTenantId(mpSeguranca.getMpUsuarioLogado().getMpUsuario().getTenantId());
			//
		} else {
			mpAuditoriaObjeto = mpAlarme.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpAlarme.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpAlarmes.guardar(mpAlarme);
	}

}
