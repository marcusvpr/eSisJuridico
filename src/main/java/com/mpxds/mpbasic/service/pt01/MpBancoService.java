package com.mpxds.mpbasic.service.pt01;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt01.MpBanco;
import com.mpxds.mpbasic.repository.pt01.MpBancos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpBancoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpBancos mpBancos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpBanco salvar(MpBanco mpBanco) throws MpNegocioException {
		//
		MpBanco mpCodigoExistente = mpBancos.porCodigo(mpBanco.getCodigo());
		
		if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpBanco)) {
			throw new MpNegocioException("Já existe um BANCO... com o CÓDIGO informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpBanco.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpBanco.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpBanco.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpBancos.guardar(mpBanco);
	}
	
}
