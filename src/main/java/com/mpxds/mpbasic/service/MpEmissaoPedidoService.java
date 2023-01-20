package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.model.enums.MpStatusPedido;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpEmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPedidoService mpPedidoService;
	
	@Inject
	private MpEstoqueService mpEstoqueService;
	
	@Inject
	private MpPedidos mpPedidos;
	
	@MpTransactional
	public MpPedido emitir(MpPedido mpPedido) throws MpNegocioException {
		mpPedido = this.mpPedidoService.salvar(mpPedido);
		
		if (mpPedido.isNaoEmissivel()) {
			throw new MpNegocioException("Pedido não pode ser emitido com status "
					+ mpPedido.getMpStatus().getDescricao() + ".");
		}
		
		this.mpEstoqueService.baixarItensEstoque(mpPedido);
		
		mpPedido.setMpStatus(MpStatusPedido.EMITIDO);
		
		mpPedido = this.mpPedidos.guardar(mpPedido);
		
		return mpPedido;
	}
	
}
