package com.mpxds.mpbasic.service.sisJuri;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpTabelaInternaSJServiceX implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpTabelaInternaSJ salvar(MpTabelaInternaSJ mpTabelaInternaSJ) throws MpNegocioException {
		MpTabelaInternaSJ mpTabelaInternaSJExistente =
			mpTabelaInternaSJs.porMpNumeroCodigo(mpTabelaInternaSJ.getMpTipoTabelaInternaSJ(), 
																	mpTabelaInternaSJ.getCodigo());
		
		if (mpTabelaInternaSJExistente != null && !mpTabelaInternaSJExistente.equals(
																			mpTabelaInternaSJ)) {
			throw new MpNegocioException("Já existe um registro com o Tipo Tabela/Código informado.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpTabelaInternaSJ.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpTabelaInternaSJ.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpTabelaInternaSJ.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpTabelaInternaSJs.guardar(mpTabelaInternaSJ);
	}

}
