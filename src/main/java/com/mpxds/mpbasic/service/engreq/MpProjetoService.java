package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpProjeto;
import com.mpxds.mpbasic.repository.engreq.MpProjetos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProjetoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpProjetos mpProjetos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpProjeto salvar(MpProjeto mpProjeto) throws MpNegocioException {
		MpProjeto mpProjetoExistente = mpProjetos.porNome(mpProjeto.getNome());
		//
		if (mpProjetoExistente != null && !mpProjetoExistente.equals(mpProjeto)) {
			throw new MpNegocioException("Já existe projeto com NOME informado. ( " +	mpProjeto.getNome());
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpProjeto.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpProjeto.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpProjeto.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpProjetos.guardar(mpProjeto);
	}
	
}
