package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.repository.MpObjetos;

@FacesConverter(forClass = MpObjeto.class)
public class MpObjetoConverter implements Converter {

	@Inject
	private MpObjetos mpObjetos;
	
	// -----

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpObjeto retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpObjetos.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null)
			if (null == ((MpObjeto) value).getId())
				return "";
			else
				return ((MpObjeto) value).getId().toString();
		//
		return "";
	}

}