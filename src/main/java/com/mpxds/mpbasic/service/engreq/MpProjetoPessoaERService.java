package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpProjetoPessoaER;
import com.mpxds.mpbasic.repository.engreq.MpProjetoPessoaERs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpProjetoPessoaERService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpProjetoPessoaERs mpProjetoPessoaERs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpProjetoPessoaER salvar(MpProjetoPessoaER mpProjetoPessoaER) throws MpNegocioException {
		//
//		MpProjetoPessoaER mpCodigoExistente = mpProjetoPessoaERs.porCodigo(mpProjetoPessoaER.getCodigo());
//		
//		if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpProjetoPessoaER)) {
//			throw new MpNegocioException("Já existe um pessoa com o CÓDIGO informado.");
//		}
		//
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpProjetoPessoaER.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpProjetoPessoaER.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpProjetoPessoaER.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpProjetoPessoaERs.guardar(mpProjetoPessoaER);
	}

}
