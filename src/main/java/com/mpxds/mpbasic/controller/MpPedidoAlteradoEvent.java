package com.mpxds.mpbasic.controller;

import com.mpxds.mpbasic.model.MpPedido;

public class MpPedidoAlteradoEvent {
	//
	private MpPedido mpPedido;
	
	public MpPedidoAlteradoEvent(MpPedido mpPedido) { this.mpPedido = mpPedido; }

	public MpPedido getMpPedido() { return mpPedido; }
	
}
