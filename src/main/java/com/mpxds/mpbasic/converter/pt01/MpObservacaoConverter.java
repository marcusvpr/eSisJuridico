package com.mpxds.mpbasic.converter.pt01;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt01.MpObservacao;
import com.mpxds.mpbasic.repository.pt01.MpObservacaos;

@FacesConverter(forClass = MpObservacao.class)
public class MpObservacaoConverter implements Converter, ClientConverter {

	@Inject
	private MpObservacaos mpObservacaos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpObservacao retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpObservacaos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpObservacao) value).getId())
				return "";
			else
				return ((MpObservacao) value).getId().toString();
		}
		//
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpObservacao";
	}

}