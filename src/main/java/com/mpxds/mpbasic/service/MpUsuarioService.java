package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpUsuario salvar(MpUsuario mpUsuario) throws MpNegocioException {
		//
		MpUsuario mpUsuarioExistente = mpUsuarios.porLogin(mpUsuario.getLogin());	
		if (mpUsuarioExistente != null && !mpUsuarioExistente.equals(mpUsuario))
			throw new MpNegocioException("Já existe uma usuario com o LOGIN informado.");
		mpUsuarioExistente = mpUsuarios.porEmail(mpUsuario.getEmail());	
		if (mpUsuarioExistente != null && !mpUsuarioExistente.equals(mpUsuario))
			throw new MpNegocioException("Já existe uma usuario com o EMAIL informado.");
		mpUsuarioExistente = mpUsuarios.porNome(mpUsuario.getNome());	
		if (mpUsuarioExistente != null && !mpUsuarioExistente.equals(mpUsuario))
			throw new MpNegocioException("Já existe uma usuario com o NOME informado.");
		//
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpUsuario.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpUsuario.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpUsuario.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
		//
		return mpUsuarios.guardar(mpUsuario);
	}
	
}
