package com.mpxds.mpbasic.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.service.MpMovimentoLoginService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class MpUsuariosOnline implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpMovimentoLoginService mpMovimentoLoginService;

	@Inject
	private MpSeguranca mpSeguranca;
	
	private Set<MpUsuario> mpUsuarioLogadoList = new HashSet<MpUsuario>();
		
	private MpUsuario mpUsuarioSelecionado = new MpUsuario();

	// ---
	
	public MpUsuariosOnline() {
		//
	}
	
	public void inicializar() {
		//
//    	System.out.println("MpUsuariosOnline.getMpUsuarioLogadoList (Size = " +
//										MpUsuarioLogados.getMpUsuarioLogadoList().size() );

    	this.mpUsuarioLogadoList = MpUsuarioLogados.getMpUsuarioLogadoList();
	}

	public void liberar() {
		//
		if (mpUsuarioSelecionado.getLogin().equals(mpSeguranca.getMpUsuarioLogado().
																	getMpUsuario().getLogin())) {
			//
			MpFacesUtil.addInfoMessage("Não é permitida encerrar à própria sessão!");
			return;
		}
		//
		MpMovimentoLogin mpMovimentoLogin = new MpMovimentoLogin();
		//
		mpMovimentoLogin.setAtividade("Encerrada por: " + mpSeguranca.getMpUsuarioLogado().
																		getMpUsuario().getLogin());
		mpMovimentoLogin.setUsuarioLogin(mpUsuarioSelecionado.getLogin());
		mpMovimentoLogin.setDtHrMovimento(new Date());
		mpMovimentoLogin.setNumeroIP(mpSeguranca.getNumeroIP());
		mpMovimentoLogin.setColigada("00");
		//
		mpMovimentoLogin = this.mpMovimentoLoginService.salvar(mpMovimentoLogin);
		// 
        MpUsuarioLogados.getMpUsuarioLogadoList().remove(
        											mpSeguranca.getMpUsuarioLogado().getMpUsuario());		
	}

	// ---
	
	public MpUsuario getMpUsuarioSelecionado() { return mpUsuarioSelecionado; }
	public void setMpUsuarioSelecionado(MpUsuario mpUsuarioSelecionado) {
												this.mpUsuarioSelecionado = mpUsuarioSelecionado; }
		
    public Set<MpUsuario> getMpUsuarioLogadoList() { return mpUsuarioLogadoList; }
	public void setMpUsuarioLogadoList(Set<MpUsuario> mpUsuarioLogadoList) {
												this.mpUsuarioLogadoList = mpUsuarioLogadoList; }

}