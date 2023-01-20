package com.mpxds.mpbasic.util.jpa;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.mpxds.mpbasic.model.MpTenant;
import com.mpxds.mpbasic.model.enums.MpStatusObjeto;

@RequestScoped
public class MpTenantProducer {

	@Produces
	@RequestScoped
	@MpTenantInject
	public MpTenant create() {
		String requestURL = getRequest().getRequestURL().toString();
		String tenantId = requestURL.substring(7).replaceAll("\\..+", "");
		return new MpTenant(tenantId, tenantId, MpStatusObjeto.ATIVO);
	}
	
	private HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (HttpServletRequest) context.getExternalContext().getRequest();
	}
	
}