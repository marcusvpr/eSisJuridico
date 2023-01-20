package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.repository.MpClientes;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpClientes mpClientes;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpCliente salvar(MpCliente mpCliente) throws MpNegocioException {
		//
		MpCliente mpCodigoExistente = mpClientes.porCodigo(mpCliente.getCodigo());
		
		if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpCliente)) {
			throw new MpNegocioException("Já existe um cliente com o CÓDIGO informado.");
		}
		//
		MpCliente mpClienteExistente = mpClientes.porNome(mpCliente.getNome());
		
		if (mpClienteExistente != null && !mpClienteExistente.equals(mpCliente)) {
			throw new MpNegocioException("Já existe um cliente com um NOME informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpCliente.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpCliente.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpCliente.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpClientes.guardar(mpCliente);
	}
	
}
