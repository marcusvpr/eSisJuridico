package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTabelaInternaServiceX implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpTabelaInternas mpTabelaInternas;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpTabelaInterna salvar(MpTabelaInterna mpTabelaInterna) throws MpNegocioException {
		MpTabelaInterna mpTabelaInternaExistente =
			mpTabelaInternas.porMpNumeroCodigo(mpTabelaInterna.getMpTipoTabelaInterna(), 
																	mpTabelaInterna.getCodigo());
		
		if (mpTabelaInternaExistente != null && !mpTabelaInternaExistente.equals(
																			mpTabelaInterna)) {
			throw new MpNegocioException("Já existe um registro com o Tipo Tabela/Código informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpTabelaInterna.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpTabelaInterna.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpTabelaInterna.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpTabelaInternas.guardar(mpTabelaInterna);
	}

}
