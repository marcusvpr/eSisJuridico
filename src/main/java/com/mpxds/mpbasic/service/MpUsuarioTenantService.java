package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpUsuarioTenantService implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpUsuarioTenant salvar(MpUsuarioTenant mpUsuarioTenant) throws MpNegocioException {
		//
		MpUsuarioTenant mpUsuarioTenantExistente = mpUsuarioTenants.porLogin(mpUsuarioTenant.
																					getLogin());	
		if (mpUsuarioTenantExistente != null && !mpUsuarioTenantExistente.equals(mpUsuarioTenant))
			throw new MpNegocioException("Já existe uma usuario com o LOGIN informado.");
		//
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		//
		if (null == mpUsuarioTenant.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpUsuarioTenant.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpUsuarioTenant.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
		//
		return mpUsuarioTenants.guardar(mpUsuarioTenant);
	}
	
}
