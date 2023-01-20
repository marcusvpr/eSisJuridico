package com.mpxds.mpbasic.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;

// ---

public class MpUsuarioSistema extends User {

	private static final long serialVersionUID = 1L;
	
	private MpUsuario mpUsuario;
	private MpUsuarioTenant mpUsuarioTenant;
	
	// ---
	
	public MpUsuarioSistema(MpUsuario mpUsuario, Collection<? extends GrantedAuthority> authorities) {
		super(mpUsuario.getEmail(), mpUsuario.getSenha(), authorities);
		this.mpUsuario = mpUsuario;
		this.mpUsuarioTenant = null;
	}
	
	public MpUsuarioSistema(MpUsuarioTenant mpUsuarioTenant, Collection<? extends GrantedAuthority> 
																						authorities) {
		super(mpUsuarioTenant.getMpPessoa().getEmail(), mpUsuarioTenant.getSenha(), authorities);
		this.mpUsuario = null;
		this.mpUsuarioTenant = mpUsuarioTenant;
	}

	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }

	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) {
															this.mpUsuarioTenant = mpUsuarioTenant; }

}
