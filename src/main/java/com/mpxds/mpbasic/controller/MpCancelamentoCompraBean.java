package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.service.MpCancelamentoCompraService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpCancelamentoCompraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCancelamentoCompraService mpCancelamentoCompraService;
	
	@Inject
	private Event<MpCompraAlteradoEvent> mpCompraAlteradoEvent;
	
	@Inject
	@MpCompraEdicao
	private MpCompra mpCompra;
	
	public void cancelarMpCompra() {
		try {
			this.mpCompra = this.mpCancelamentoCompraService.cancelar(this.mpCompra);
			this.mpCompraAlteradoEvent.fire(new MpCompraAlteradoEvent(this.mpCompra));
			
			MpFacesUtil.addInfoMessage("Compra cancelada com sucesso!");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
}
