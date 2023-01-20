package com.mpxds.mpbasic.controller;

import com.mpxds.mpbasic.model.MpCompra;

public class MpCompraAlteradoEvent {
	//
	private MpCompra mpCompra;
	
	public MpCompraAlteradoEvent(MpCompra mpCompra) { this.mpCompra = mpCompra; }

	public MpCompra getMpCompra() { return mpCompra; }
	
}
