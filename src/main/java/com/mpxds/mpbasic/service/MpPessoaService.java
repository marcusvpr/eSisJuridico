package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoas mpPessoas;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPessoa salvar(MpPessoa mpPessoa) throws MpNegocioException {
		//
//		MpPessoa mpCodigoExistente = mpPessoas.porCodigo(mpPessoa.getCodigo());
//		
//		if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpPessoa)) {
//			throw new MpNegocioException("Já existe um pessoa com o CÓDIGO informado.");
//		}
		//
		MpPessoa mpPessoaExistente = mpPessoas.porNome(mpPessoa.getNome());
		
		if (mpPessoaExistente != null && !mpPessoaExistente.equals(mpPessoa)) {
			throw new MpNegocioException("Já existe um pessoa com um NOME informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpPessoa.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPessoa.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPessoa.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpPessoas.guardar(mpPessoa);
	}

}
