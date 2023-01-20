package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.model.enums.MpStatusCompra;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpEmissaoCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCompraService mpCompraService;
	
	@Inject
	private MpEstoqueService mpEstoqueService;
	
	@Inject
	private MpCompras mpCompras;
	
	@MpTransactional
	public MpCompra emitir(MpCompra mpCompra) throws MpNegocioException {
		mpCompra = this.mpCompraService.salvar(mpCompra);
		
		if (mpCompra.isNaoEmissivel()) {
			throw new MpNegocioException("Compra não pode ser emitida com status "
														+ mpCompra.getMpStatus().getDescricao() + ".");
		}
		
		this.mpEstoqueService.adicionarItensEstoque(mpCompra);
		
		mpCompra.setMpStatus(MpStatusCompra.EMITIDA);
		
		mpCompra = this.mpCompras.guardar(mpCompra);
		
		return mpCompra;
	}
	
}
