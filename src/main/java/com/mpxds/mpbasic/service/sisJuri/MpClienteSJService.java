package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpClienteSJService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpClienteSJs mpClienteSJs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpClienteSJ salvar(MpClienteSJ mpClienteSJ) throws MpNegocioException {
		//
		if (null == mpClienteSJ.getId()) {
			MpClienteSJ mpClienteSJExistente = mpClienteSJs.porNome(mpClienteSJ.getNome());
		
			if (mpClienteSJExistente != null && !mpClienteSJExistente.equals(mpClienteSJ)) {
				throw new MpNegocioException("Já existe um CLIENTE com um NOME informado.");
			}
		}
		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpClienteSJ.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpClienteSJ.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpClienteSJ.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpClienteSJs.guardar(mpClienteSJ);
	}
	
}
