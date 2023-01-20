package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.mail.MpMailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class MpEnvioCompraEmailBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpMailer mpMailer;
	
	@Inject
	@MpCompraEdicao
	private MpCompra mpCompra;
	
	public void enviarMpCompra() {
		MailMessage message = mpMailer.novaMensagem();
		
		message.to(this.mpCompra.getMpContato().getEmail())
			.subject("Compra " + this.mpCompra.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/mpCompra.template")))
			.put("mpCompra", this.mpCompra)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		MpFacesUtil.addInfoMessage("Compra enviada... por e-mail com sucesso!");
	}
	
}
