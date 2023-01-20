package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.model.MpItemCompra;
import com.mpxds.mpbasic.model.MpItemPedido;
import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpEstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpPedidos mpPedidos;

	@Inject
	private MpCompras mpCompras;
	
	@MpTransactional
	public void baixarItensEstoque(MpPedido mpPedido) throws MpNegocioException {
		mpPedido = this.mpPedidos.porId(mpPedido.getId());
		
		for (MpItemPedido mpItem : mpPedido.getMpItens()) {
			mpItem.getMpProduto().baixarEstoque(mpItem.getQuantidade());
		}
	}

	public void retornarItensEstoque(MpPedido mpPedido) {
		mpPedido = this.mpPedidos.porId(mpPedido.getId());
		
		for (MpItemPedido mpItem : mpPedido.getMpItens()) {
			mpItem.getMpProduto().adicionarEstoque(mpItem.getQuantidade());
		}
	}
	
	@MpTransactional
	public void adicionarItensEstoque(MpCompra mpCompra) throws MpNegocioException {
		mpCompra = this.mpCompras.porId(mpCompra.getId());
		
		for (MpItemCompra mpItem : mpCompra.getMpItens()) {
			mpItem.getMpProduto().adicionarEstoque(mpItem.getQuantidade());
		}
	}

	@MpTransactional
	public void baixarItensEstoque(MpCompra mpCompra) throws MpNegocioException {
		mpCompra = this.mpCompras.porId(mpCompra.getId());
		
		for (MpItemCompra mpItem : mpCompra.getMpItens()) {
			mpItem.getMpProduto().subtrairEstoque(mpItem.getQuantidade());
		}
	}
	
}
