package com.mpxds.mpbasic.converter.pt05;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt05.MpAtoComposicao;
import com.mpxds.mpbasic.repository.pt05.MpAtoComposicaos;

@FacesConverter(forClass = MpAtoComposicao.class)
public class MpAtoComposicaoConverter implements Converter, ClientConverter {

	@Inject
	private MpAtoComposicaos mpAtosComposicaos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpAtoComposicao retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpAtosComposicaos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpAtoComposicao) value).getId())
				return "";
			else
				return ((MpAtoComposicao) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpAtosComposicao";
	}

}