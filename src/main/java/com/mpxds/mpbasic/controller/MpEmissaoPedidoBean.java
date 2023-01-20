package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.service.MpEmissaoPedidoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpEmissaoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpEmissaoPedidoService mpEmissaoPedidoService;
	
	@Inject
	@MpPedidoEdicao
	private MpPedido mpPedido;
	
	@Inject
	private Event<MpPedidoAlteradoEvent> mpPedidoAlteradoEvent;
	
	public void emitirMpPedido() {
		//
		this.mpPedido.removerItemVazio();
		
		try {
			this.mpPedido = this.mpEmissaoPedidoService.emitir(this.mpPedido);
			this.mpPedidoAlteradoEvent.fire(new MpPedidoAlteradoEvent(this.mpPedido));
			
			MpFacesUtil.addInfoMessage("Pedido emitido com sucesso!");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.mpPedido.adicionarItemVazio();
		}
	}
	
}
