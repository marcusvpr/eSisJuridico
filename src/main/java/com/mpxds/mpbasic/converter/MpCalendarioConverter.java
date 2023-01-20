package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.repository.MpCalendarios;

@FacesConverter(forClass = MpCalendario.class)
public class MpCalendarioConverter implements Converter, ClientConverter {

	@Inject
	private MpCalendarios mpCalendarios;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpCalendario retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpCalendarios.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpCalendario) value).getId())
				return "";
			else
				return ((MpCalendario) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpCalendario";
	}

}