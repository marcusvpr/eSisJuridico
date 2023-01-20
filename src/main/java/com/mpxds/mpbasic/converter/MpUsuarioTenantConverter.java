package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;

@FacesConverter(forClass = MpUsuarioTenant.class)
public class MpUsuarioTenantConverter implements Converter {

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpUsuarioTenant retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpUsuarioTenants.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpUsuarioTenant) value).getId())
				return "";
			else
				return ((MpUsuarioTenant) value).getId().toString();
		}
		//
		return "";
	}

}