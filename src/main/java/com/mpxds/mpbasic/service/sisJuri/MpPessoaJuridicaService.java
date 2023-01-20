package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaJuridica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaJuridicas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaJuridicaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaJuridicas mpPessoaJuridicas;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPessoaJuridica salvar(MpPessoaJuridica mpPessoaJuridica) throws MpNegocioException {
		//
		if (null == mpPessoaJuridica.getId()) {
			MpPessoaJuridica mpPessoaJuridicaExistente = mpPessoaJuridicas.porNome(mpPessoaJuridica.getNome());
		
			if (mpPessoaJuridicaExistente != null && !mpPessoaJuridicaExistente.equals(mpPessoaJuridica)) {
				throw new MpNegocioException("Já existe uma Pessoa Juridica com um NOME informado.");
			}
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpPessoaJuridica.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPessoaJuridica.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPessoaJuridica.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpPessoaJuridicas.guardar(mpPessoaJuridica);
	}
	
}
