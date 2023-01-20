package com.mpxds.mpbasic.converter.pt05;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.repository.pt05.MpAtos;

@FacesConverter(forClass = MpAto.class)
public class MpAtoConverter implements Converter, ClientConverter {

	@Inject
	private MpAtos mpAtoss;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpAto retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpAtoss.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpAto) value).getId())
				return "";
			else
				return ((MpAto) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpAtos";
	}

}