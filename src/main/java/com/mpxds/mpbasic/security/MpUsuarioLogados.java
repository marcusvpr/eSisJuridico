package com.mpxds.mpbasic.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;

@ManagedBean(eager=true)
@ApplicationScoped
public class MpUsuarioLogados implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private static Set<MpUsuario> mpUsuarioLogadoList = new HashSet<MpUsuario>();
	private static Set<MpUsuarioTenant> mpUsuarioTenantLogadoList = new HashSet<MpUsuarioTenant>();

    public static Set<MpUsuario> getMpUsuarioLogadoList() { return mpUsuarioLogadoList; }
    public static Set<MpUsuarioTenant> getMpUsuarioTenantLogadoList() {
    														return mpUsuarioTenantLogadoList; }

}