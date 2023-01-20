package com.mpxds.mpbasic.service.pt01;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt01.MpObservacao;
import com.mpxds.mpbasic.repository.pt01.MpObservacaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpObservacaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpObservacaos mpObservacaos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpObservacao salvar(MpObservacao mpObservacao) throws MpNegocioException {
		// Tratamento para chave simples !!!
		// ==================================
		MpObservacao mpObservacaoExistente = mpObservacaos.porTipoObservacao(
				                                         mpObservacao.getTipoObservacao());																	
		if (mpObservacaoExistente != null
		&& !mpObservacaoExistente.equals(mpObservacao)) {
					throw new MpNegocioException(
							"Já existe uma Observação com a Tipo Observação informado.");
		}	

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpObservacao.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpObservacao.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpObservacao.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpObservacaos.guardar(mpObservacao);
	}

}
