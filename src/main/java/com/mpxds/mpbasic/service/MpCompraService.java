package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCompras mpCompras;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpCompra salvar(MpCompra mpCompra) throws MpNegocioException {
		if (mpCompra.isNovo()) {
			mpCompra.setDataCriacao(new Date());
		}
		
		mpCompra.recalcularValorTotal();
		//		
		if (mpCompra.getMpItens().isEmpty()) {
			throw new MpNegocioException("A Compra deve possuir pelo menos um item.");
		}
		
		if (mpCompra.isValorTotalNegativo()) {
			throw new MpNegocioException("Valor total da Compra não pode ser negativo.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpCompra.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpCompra.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpCompra.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		mpCompra = this.mpCompras.guardar(mpCompra);
		return mpCompra;
	}
	
}
