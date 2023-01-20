package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.model.enums.MpStatusPedido;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPedidos mpPedidos;
	
	@Inject
	private MpEstoqueService estoqueService;
	
	@MpTransactional
	public MpPedido cancelar(MpPedido mpPedido) throws MpNegocioException {
		mpPedido = this.mpPedidos.porId(mpPedido.getId());
		
		if (mpPedido.isNaoCancelavel()) {
			throw new MpNegocioException("Pedido não pode ser cancelado no status "
					+ mpPedido.getMpStatus().getDescricao() + ".");
		}
		
		if (mpPedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(mpPedido);
		}
		
		mpPedido.setMpStatus(MpStatusPedido.CANCELADO);
		
		mpPedido = this.mpPedidos.guardar(mpPedido);
		
		return mpPedido;
	}

}
