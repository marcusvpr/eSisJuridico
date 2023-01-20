package com.mpxds.mpbasic.service.engreq;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.repository.engreq.MpPessoaERs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaERService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaERs mpPessoaERs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPessoaER salvar(MpPessoaER mpPessoaER) throws MpNegocioException {
		//
//		MpPessoaER mpCodigoExistente = mpPessoaERs.porCodigo(mpPessoaER.getCodigo());
//		
//		if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpPessoaER)) {
//			throw new MpNegocioException("Já existe um pessoa com o CÓDIGO informado.");
//		}
		//
		MpPessoaER mpPessoaERExistente = mpPessoaERs.porNome(mpPessoaER.getNome());
		
		if (mpPessoaERExistente != null && !mpPessoaERExistente.equals(mpPessoaER)) {
			throw new MpNegocioException("Já existe um pessoa ER com um NOME informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpPessoaER.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPessoaER.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPessoaER.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpPessoaERs.guardar(mpPessoaER);
	}

}
