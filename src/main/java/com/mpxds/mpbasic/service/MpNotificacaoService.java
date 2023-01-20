package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpNotificacao;
import com.mpxds.mpbasic.repository.MpNotificacaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpNotificacaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpNotificacaos mpNotificacaos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpNotificacao salvar(MpNotificacao mpNotificacao) throws MpNegocioException {
//		MpNotificacao mpNotificacaoExistente =
//										mpNotificacaos.porDataCr(mpNotificacao.getParametro());
//		
//		if (mpNotificacaoExistente != null && !mpNotificacaoExistente.equals(mpNotificacao)) {
//			throw new MpNegocioException("Já existe um registro com o Parâmetro informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpNotificacao.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpNotificacao.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpNotificacao.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
		//
		return mpNotificacaos.guardar(mpNotificacao);
	}

}
