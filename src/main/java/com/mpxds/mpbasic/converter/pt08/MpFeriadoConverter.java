package com.mpxds.mpbasic.converter.pt08;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt08.MpFeriado;
import com.mpxds.mpbasic.repository.pt08.MpFeriados;

@FacesConverter(forClass = MpFeriado.class)
public class MpFeriadoConverter implements Converter, ClientConverter {

	@Inject
	private MpFeriados mpFeriados;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpFeriado retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpFeriados.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpFeriado) value).getId())
				return "";
			else
				return ((MpFeriado) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpFeriado";
	}

}