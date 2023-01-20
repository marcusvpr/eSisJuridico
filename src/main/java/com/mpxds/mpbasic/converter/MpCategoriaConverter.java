package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.repository.MpCategorias;

@FacesConverter(forClass = MpCategoria.class)
public class MpCategoriaConverter implements Converter, ClientConverter {

	@Inject
	private MpCategorias mpCategorias;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpCategoria retorno = null;
		
		if (StringUtils.isNotEmpty(value))
			retorno = mpCategorias.porId(new Long(value));
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpCategoria) value).getId())
				return "";
			else
				return ((MpCategoria) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpCategoria";
	}

}