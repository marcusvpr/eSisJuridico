package com.mpxds.mpbasic.converter.engreq;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.engreq.MpRequisitoFuncional;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoFuncionals;

@FacesConverter(forClass = MpRequisitoFuncional.class)
public class MpRequisitoFuncionalConverter implements Converter, ClientConverter {

	@Inject
	private MpRequisitoFuncionals mpRequisitoFuncionals;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpRequisitoFuncional retorno = null;
		
		if (StringUtils.isNotEmpty(value))
			retorno = mpRequisitoFuncionals.porId(new Long(value));
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpRequisitoFuncional) value).getId())
				return "";
			else
				return ((MpRequisitoFuncional) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.mpbasic.MpRequisitoFuncional";
	}

}