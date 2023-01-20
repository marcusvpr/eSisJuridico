package com.mpxds.mpbasic.converter.pt01;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt01.MpBanco;
import com.mpxds.mpbasic.repository.pt01.MpBancos;

@FacesConverter(forClass = MpBanco.class)
public class MpBancoConverter implements Converter, ClientConverter {

	@Inject
	private MpBancos mpBancoss;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpBanco retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpBancoss.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpBanco) value).getId())
				return "";
			else
				return ((MpBanco) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpBancos";
	}

}