package com.mpxds.mpbasic.service.pt05;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.repository.pt05.MpCustasComposicaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCustasComposicaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCustasComposicaos mpCustasComposicaos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpCustasComposicao salvar(MpCustasComposicao mpCustasComposicao) throws MpNegocioException {
		// Tratamento para chave composta !!!
		// ==================================
		MpCustasComposicao mpCustasComposicaoExistente = mpCustasComposicaos.porTabelaItemSubItem(
																	mpCustasComposicao.getTabela(),
																	mpCustasComposicao.getItem(),
																	mpCustasComposicao.getSubItem());
		
		if (mpCustasComposicaoExistente != null
		&& !mpCustasComposicaoExistente.equals(mpCustasComposicao)) {
			throw new MpNegocioException(
							"Já existe um Custas Composicao com a Tabela/Item/SubItem informados.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpCustasComposicao.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpCustasComposicao.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpCustasComposicao.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpCustasComposicaos.guardar(mpCustasComposicao);
	}
	
}
