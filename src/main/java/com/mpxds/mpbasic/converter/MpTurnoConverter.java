package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.repository.MpTurnos;

@FacesConverter(forClass = MpTurno.class)
public class MpTurnoConverter implements Converter, ClientConverter {

	@Inject
	private MpTurnos mpTurnos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTurno retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpTurnos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpTurno) value).getId())
				return "";
			else
				return ((MpTurno) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpTurno";
	}

}