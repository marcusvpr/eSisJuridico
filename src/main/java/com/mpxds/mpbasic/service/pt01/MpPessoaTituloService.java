package com.mpxds.mpbasic.service.pt01;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt01.MpPessoaTitulo;
import com.mpxds.mpbasic.repository.pt01.MpPessoaTitulos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPessoaTituloService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoaTitulos mpPessoaTitulos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPessoaTitulo salvar(MpPessoaTitulo mpPessoaTitulo) throws MpNegocioException {
		//
//		MpPessoaTitulo mpNomeExistente = mpPessoaTitulos.porNome(mpPessoaTitulo.getNome());
//		
//		if (mpNomeExistente != null && !mpNomeExistente.equals(mpPessoaTitulo)) {
//			throw new MpNegocioException("Já existe uma PESSOA... com o NOME informado.");
//		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpPessoaTitulo.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPessoaTitulo.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPessoaTitulo.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpPessoaTitulos.guardar(mpPessoaTitulo);
	}
	
}
