package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.service.MpCancelamentoPedidoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpCancelamentoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpCancelamentoPedidoService mpCancelamentoPedidoService;
	
	@Inject
	private Event<MpPedidoAlteradoEvent> mpPedidoAlteradoEvent;
	
	@Inject
	@MpPedidoEdicao
	private MpPedido mpPedido;
	
	public void cancelarMpPedido() {
		try {
			this.mpPedido = this.mpCancelamentoPedidoService.cancelar(this.mpPedido);
			this.mpPedidoAlteradoEvent.fire(new MpPedidoAlteradoEvent(this.mpPedido));
			
			MpFacesUtil.addInfoMessage("Pedido cancelado com sucesso!");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
}
