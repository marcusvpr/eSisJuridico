package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.service.MpEmissaoCompraService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpEmissaoCompraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpEmissaoCompraService mpEmissaoCompraService;
	
	@Inject
	@MpCompraEdicao
	private MpCompra mpCompra;
	
	@Inject
	private Event<MpCompraAlteradoEvent> mpCompraAlteradoEvent;
	
	public void emitirMpCompra() {
		//
		this.mpCompra.removerItemVazio();
		
		try {
			this.mpCompra = this.mpEmissaoCompraService.emitir(this.mpCompra);
			
			this.mpCompraAlteradoEvent.fire(new MpCompraAlteradoEvent(this.mpCompra));
			
			MpFacesUtil.addInfoMessage("Compra emitida com sucesso!");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.mpCompra.adicionarItemVazio();
		}
	}
	
}
