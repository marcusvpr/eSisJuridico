package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpObjetoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpObjeto salvar(MpObjeto mpObjeto) throws MpNegocioException {
		//
		MpObjeto mpObjetoExistente = mpObjetos.porCodigo(mpObjeto.getCodigo(),
															mpSeguranca.capturaTenantId().trim());
		if (mpObjetoExistente != null && !mpObjetoExistente.equals(mpObjeto))
			throw new MpNegocioException("Já existe um registro com o Código... informado. (" +
																		mpObjeto.getCodigo());
		//
		mpObjetoExistente = mpObjetos.porTransacao(mpObjeto.getTransacao(),
															mpSeguranca.capturaTenantId().trim());
		if (mpObjetoExistente != null && !mpObjetoExistente.equals(mpObjeto))
			throw new MpNegocioException("Já existe um registro com a Transação... informada. (" +
																		mpObjeto.getTransacao());
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpObjeto.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpObjeto.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpObjeto.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpObjetos.guardar(mpObjeto);
	}

}
