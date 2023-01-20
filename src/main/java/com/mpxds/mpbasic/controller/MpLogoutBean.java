package com.mpxds.mpbasic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.security.MpUsuarioLogados;
import com.mpxds.mpbasic.service.MpMovimentoLoginService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpLogoutBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpMovimentoLoginService mpMovimentoLoginService;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpLoginBean mpLoginBean;

	@SuppressWarnings("unused")
	@Inject
	private MpUsuarioLogados mpUsuarioLogados;

	// --------------
	
	public MpLogoutBean() {
//		System.out.println("MpLogoutBean() - 000");
	}
	
	public void inicializar() {
		//
		mpLoginBean.setIndMenu(false); // Remontar o Menu no LOGIN !!!!
		mpLoginBean.setIndMenuDoseCerta(false); // Remontar o Menu no LOGIN !!!!
		//
//		System.out.println("MpLogoutBean.inicializar() - 000");
		// Grava MovimentoLogin ...
		MpMovimentoLogin mpMovimentoLogin = new MpMovimentoLogin();
		//
		mpMovimentoLogin.setAtividade("Logout");
		if (null == mpSeguranca.getMpUsuarioLogado()) {
			mpMovimentoLogin.setUsuarioLogin("null-MpLogoutBean");
			mpMovimentoLogin.setUsuarioEmail("null");
		} else
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario()) {
			mpMovimentoLogin.setUsuarioLogin(mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																						getLogin());
			mpMovimentoLogin.setUsuarioEmail(mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																			getMpPessoa().getEmail());
		} else {
			mpMovimentoLogin.setUsuarioLogin(mpSeguranca.getMpUsuarioLogado().getMpUsuario().getLogin());
			mpMovimentoLogin.setUsuarioEmail(mpSeguranca.getMpUsuarioLogado().getMpUsuario().getEmail());
		}
		mpMovimentoLogin.setDtHrMovimento(new Date());
		mpMovimentoLogin.setNumeroIP(mpSeguranca.getNumeroIP());
		mpMovimentoLogin.setColigada("00");
		//
		mpMovimentoLogin = this.mpMovimentoLoginService.salvar(mpMovimentoLogin);
		// 
		if (null == mpSeguranca.getMpUsuarioLogado())
			assert(true); // nop
		else
		if (null == mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			MpUsuarioLogados.getMpUsuarioLogadoList().remove(mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant());
		else
			MpUsuarioLogados.getMpUsuarioLogadoList().remove(mpSeguranca.getMpUsuarioLogado().getMpUsuario());
		//
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		String url = extContext.encodeActionURL(extContext.getRequestContextPath() +
																			"/sentinel/mpLogin.xhtml");
//																	"/j_spring_security_logout");
		try {
			extContext.redirect(url);
			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void inicializarErro() {
		//
		mpLoginBean.setIndMenu(false); // Remontar o Menu no LOGIN !!!!
		mpLoginBean.setIndMenuDoseCerta(false); // Remontar o Menu no LOGIN !!!!
	}
		
}