package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.repository.MpUsuarios;

@FacesConverter(forClass = MpUsuario.class)
public class MpUsuarioConverter implements Converter {

	@Inject
	private MpUsuarios mpUsuarios;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpUsuario retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpUsuarios.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpUsuario) value).getId())
				return "";
			else
				return ((MpUsuario) value).getId().toString();
		}
		//
		return "";
	}

}