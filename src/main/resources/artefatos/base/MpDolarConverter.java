package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.repository.MpDolars;

@FacesConverter(forClass = MpDolar.class)
public class MpDolarConverter implements Converter, ClientConverter {

	@Inject
	private MpDolars mpDolars;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpDolar retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpDolars.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpDolar) value).getId())
				return "";
			else
				return ((MpDolar) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpDolar";
	}

}