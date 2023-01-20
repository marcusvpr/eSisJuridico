package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpTenant;
import com.mpxds.mpbasic.repository.MpTenants;

@FacesConverter(forClass = MpTenant.class)
public class MpTenantConverter implements Converter {

	@Inject
	private MpTenants mpTenants;
	
	// -----

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTenant retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpTenants.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null)
			if (null == ((MpTenant) value).getId())
				return "";
			else
				return ((MpTenant) value).getId().toString();
		//
		return "";
	}

}