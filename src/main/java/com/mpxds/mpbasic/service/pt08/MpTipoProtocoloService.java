package com.mpxds.mpbasic.service.pt08;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt08.MpTipoProtocolo;
import com.mpxds.mpbasic.repository.pt08.MpTipoProtocolos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTipoProtocoloService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpTipoProtocolos mpTipoProtocolos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpTipoProtocolo salvar(MpTipoProtocolo mpTipoProtocolo) throws MpNegocioException {
		// Tratamento para chave simples !!!
		// ==================================
		MpTipoProtocolo mpTipoProtocoloExistente = mpTipoProtocolos.porCodigo(
																	mpTipoProtocolo.getCodigo());
		
		if (mpTipoProtocoloExistente != null
		&& !mpTipoProtocoloExistente.equals(mpTipoProtocolo)) {
			throw new MpNegocioException(
							"Já existe Tipo Protocolo com o Código informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpTipoProtocolo.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpTipoProtocolo.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpTipoProtocolo.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpTipoProtocolos.guardar(mpTipoProtocolo);
	}
	
}
