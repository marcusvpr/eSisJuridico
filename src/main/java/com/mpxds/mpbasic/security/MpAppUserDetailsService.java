package com.mpxds.mpbasic.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.model.enums.MpGrupoTenant;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;

public class MpAppUserDetailsService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String loginEmail) throws UsernameNotFoundException {
		//
		MpSeguranca mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);

		MpUsuarioSistema mpUser = null;
		//
		if (!mpSeguranca.getUsuarioAmbiente().trim().equals("0")) {
			MpUsuarioTenants mpUsuarioTenants = MpCDIServiceLocator.getBean(MpUsuarioTenants.class);
			MpUsuarioTenant mpUsuarioTenant = mpUsuarioTenants.porLoginEmail(loginEmail);
			//
			if (mpUsuarioTenant != null)
				mpUser = new MpUsuarioSistema(mpUsuarioTenant, getGrupoTenant(mpUsuarioTenant));
			//
		} else {
			MpUsuarios mpUsuarios = MpCDIServiceLocator.getBean(MpUsuarios.class);
			MpUsuario mpUsuario = mpUsuarios.porLoginEmail(loginEmail);
			//
			if (mpUsuario != null)
				mpUser = new MpUsuarioSistema(mpUsuario, getMpGrupos(mpUsuario));
			//
		}
		//
		return mpUser;
	}

	private Collection<? extends GrantedAuthority> getMpGrupos(MpUsuario mpUsuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (MpGrupo mpGrupo : mpUsuario.getMpGrupos()) {
			authorities.add(new SimpleGrantedAuthority(mpGrupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}

	private Collection<? extends GrantedAuthority> getGrupoTenant(MpUsuarioTenant 
																			mpUsuarioTenant) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		if (mpUsuarioTenant.getMpGrupoTenant().equals(MpGrupoTenant.CUIDADOR))
			authorities.add(new SimpleGrantedAuthority("PROTESTOS"));
		else
			if (mpUsuarioTenant.getMpGrupoTenant().equals(MpGrupoTenant.EMPREGADO))
				authorities.add(new SimpleGrantedAuthority("PROTESTOS"));
			else
				if (mpUsuarioTenant.getMpGrupoTenant().equals(MpGrupoTenant.FAMILIA))
					authorities.add(new SimpleGrantedAuthority("PROTESTOS"));
		
		return authorities;
	}

}
