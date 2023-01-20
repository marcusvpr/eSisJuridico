package com.mpxds.mpbasic.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.model.enums.MpStatusPedido;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPedidos mpPedidos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpPedido salvar(MpPedido mpPedido) throws MpNegocioException {
		if (mpPedido.isNovo()) {
			mpPedido.setDataCriacao(new Date());
			mpPedido.setMpStatus(MpStatusPedido.ORCAMENTO);
		}
		
		mpPedido.recalcularValorTotal();
		
		if (mpPedido.isNaoAlteravel()) {
			throw new MpNegocioException("Pedido não pode ser alterado no status "
					+ mpPedido.getMpStatus().getDescricao() + ".");
		}
		
		if (mpPedido.getMpItens().isEmpty()) {
			throw new MpNegocioException("O Pedido deve possuir pelo menos um item.");
		}
		
		if (mpPedido.isValorTotalNegativo()) {
			throw new MpNegocioException("Valor total do Pedido não pode ser negativo.");
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		if (null == mpPedido.getId()) { 
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpPedido.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpPedido.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		mpPedido = this.mpPedidos.guardar(mpPedido);
		return mpPedido;
	}
	
}
