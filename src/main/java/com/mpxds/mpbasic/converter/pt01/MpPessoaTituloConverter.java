package com.mpxds.mpbasic.converter.pt01;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt01.MpPessoaTitulo;
import com.mpxds.mpbasic.repository.pt01.MpPessoaTitulos;

@FacesConverter(forClass = MpPessoaTitulo.class)
public class MpPessoaTituloConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoaTitulos mpPessoaTituloss;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoaTitulo retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpPessoaTituloss.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoaTitulo) value).getId())
				return "";
			else
				return ((MpPessoaTitulo) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoaTitulos";
	}

}