package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.repository.MpNotificacaoUsuarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpNotificacaoUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpNotificacaoUsuarios mpNotificacaoUsuarios;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpNotificacaoUsuario salvar(MpNotificacaoUsuario mpNotificacaoUsuario) 
																throws MpNegocioException {
//		MpNotificacao mpNotificacaoExistente =
//										mpNotificacaos.porDataCr(mpNotificacao.getParametro());
//		
//		if (mpNotificacaoExistente != null && !mpNotificacaoExistente.equals(mpNotificacao)) {
//			throw new MpNegocioException("Já existe um registro com o Parâmetro informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpNotificacaoUsuario.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpNotificacaoUsuario.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpNotificacaoUsuario.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpNotificacaoUsuarios.guardar(mpNotificacaoUsuario);
	}
		
}
