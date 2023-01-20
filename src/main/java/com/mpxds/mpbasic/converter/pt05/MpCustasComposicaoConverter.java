package com.mpxds.mpbasic.converter.pt05;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.repository.pt05.MpCustasComposicaos;

@FacesConverter(forClass = MpCustasComposicao.class)
public class MpCustasComposicaoConverter implements Converter, ClientConverter {

	@Inject
	private MpCustasComposicaos mpCustasComposicaos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpCustasComposicao retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpCustasComposicaos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpCustasComposicao) value).getId())
				return "";
			else
				return ((MpCustasComposicao) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpCustasComposicao";
	}

}