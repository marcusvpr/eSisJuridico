package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaFisicaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaFisicas mpPessoaFisicas;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPessoaFisica salvar(MpPessoaFisica mpPessoaFisica) throws MpNegocioException {
		//
		if (null == mpPessoaFisica.getId()) {
			MpPessoaFisica mpPessoaFisicaExistente = mpPessoaFisicas.porNome(mpPessoaFisica.getNome());
		
			if (mpPessoaFisicaExistente != null && !mpPessoaFisicaExistente.equals(mpPessoaFisica)) {
				throw new MpNegocioException("Já existe uma Pessoa Fisica com um NOME informado.");
			}
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpPessoaFisica.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPessoaFisica.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPessoaFisica.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpPessoaFisicas.guardar(mpPessoaFisica);
	}
	
}
