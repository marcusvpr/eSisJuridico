package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.mail.MpMailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class MpEnvioPedidoEmailBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpMailer mpMailer;
	
	@Inject
	@MpPedidoEdicao
	private MpPedido mpPedido;
	
	public void enviarMpPedido() {
		MailMessage message = mpMailer.novaMensagem();
		
		message.to(this.mpPedido.getMpContato().getEmail())
			.subject("Pedido " + this.mpPedido.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/mpPedido.template")))
			.put("mpPedido", this.mpPedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		MpFacesUtil.addInfoMessage("Pedido enviado... por e-mail com sucesso!");
	}
	
}
