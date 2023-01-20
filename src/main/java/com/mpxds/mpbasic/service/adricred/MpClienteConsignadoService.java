package com.mpxds.mpbasic.service.adricred;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.adricred.MpClienteConsignado;
import com.mpxds.mpbasic.repository.adricred.MpClienteConsignados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpClienteConsignadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpClienteConsignados mpClienteConsignados;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpClienteConsignado salvar(MpClienteConsignado mpClienteConsignado) throws MpNegocioException {
		//
		MpClienteConsignado mpClienteConsignadoExistente = mpClienteConsignados.porNome(mpClienteConsignado.getNome());
		
		if (mpClienteConsignadoExistente != null && !mpClienteConsignadoExistente.equals(mpClienteConsignado)) {
			throw new MpNegocioException("Já existe um ClienteConsignado com um NOME informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpClienteConsignado.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpClienteConsignado.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpClienteConsignado.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpClienteConsignados.guardar(mpClienteConsignado);
	}
	
}
