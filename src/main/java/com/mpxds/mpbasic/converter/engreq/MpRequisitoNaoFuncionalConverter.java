package com.mpxds.mpbasic.converter.engreq;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.engreq.MpRequisitoNaoFuncional;
import com.mpxds.mpbasic.repository.engreq.MpRequisitoNaoFuncionals;

@FacesConverter(forClass = MpRequisitoNaoFuncional.class)
public class MpRequisitoNaoFuncionalConverter implements Converter, ClientConverter {

	@Inject
	private MpRequisitoNaoFuncionals mpRequisitoNaoFuncionals;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpRequisitoNaoFuncional retorno = null;
		
		if (StringUtils.isNotEmpty(value))
			retorno = mpRequisitoNaoFuncionals.porId(new Long(value));
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpRequisitoNaoFuncional) value).getId())
				return "";
			else
				return ((MpRequisitoNaoFuncional) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.mpbasic.MpRequisitoNaoFuncional";
	}

}