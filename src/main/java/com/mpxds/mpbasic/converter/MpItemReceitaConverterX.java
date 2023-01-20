package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpItemReceita;
import com.mpxds.mpbasic.repository.MpItemReceitas;

@FacesConverter(value = "mpItemReceitaConverterX")
public class MpItemReceitaConverterX implements Converter {

	@Inject
	private MpItemReceitas mpItemReceitas;
	
	// -----

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpItemReceita retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpItemReceitas.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null)
			return ((MpItemReceita) value).getId().toString();
		//
		return "";
	}

}