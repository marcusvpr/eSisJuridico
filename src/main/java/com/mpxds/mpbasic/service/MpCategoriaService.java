package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
//import javax.transaction.Transactional;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCategoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCategorias mpCategorias;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpCategoria salvar(MpCategoria mpCategoria) throws MpNegocioException {
		MpCategoria mpCategoriaExistente = mpCategorias.porTipoDescricao(
									mpCategoria.getMpTipoProduto(), mpCategoria.getDescricao());
		//
		if (mpCategoriaExistente != null && !mpCategoriaExistente.equals(mpCategoria)) {
			throw new MpNegocioException("Já existe categoria com TIPO/DESCRIÇÃO informada. ( " +
			mpCategoria.getMpTipoProduto().getDescricao() + " / " + mpCategoria.getDescricao());
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpCategoria.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpCategoria.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpCategoria.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpCategorias.guardar(mpCategoria);
	}
	
}
