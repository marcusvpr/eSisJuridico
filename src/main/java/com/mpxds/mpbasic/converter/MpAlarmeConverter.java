package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.repository.MpAlarmes;

@FacesConverter(forClass = MpAlarme.class)
public class MpAlarmeConverter implements Converter, ClientConverter {

	@Inject
	private MpAlarmes mpAlarmes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpAlarme retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpAlarmes.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpAlarme) value).getId())
				return "";
			else
				return ((MpAlarme) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpAlarme";
	}

}