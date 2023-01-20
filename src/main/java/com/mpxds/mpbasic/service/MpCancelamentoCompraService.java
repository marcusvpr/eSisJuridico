package com.mpxds.mpbasic.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.model.enums.MpStatusCompra;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpCancelamentoCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCompras mpCompras;
	
	@Inject
	private MpEstoqueService estoqueService;
	
	@MpTransactional
	public MpCompra cancelar(MpCompra mpCompra) throws MpNegocioException {
		mpCompra = this.mpCompras.porId(mpCompra.getId());
		
		if (mpCompra.isNaoCancelavel()) {
			throw new MpNegocioException("Compra não pode ser cancelada no status "
					+ mpCompra.getMpStatus().getDescricao() + ".");
		}
		
		if (mpCompra.isEmitido()) {
			this.estoqueService.baixarItensEstoque(mpCompra);
		}
		
		mpCompra.setMpStatus(MpStatusCompra.CANCELADA);
		
		mpCompra = this.mpCompras.guardar(mpCompra);
		
		return mpCompra;
	}

}
