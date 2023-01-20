package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.repository.MpTabelaInternas;

@FacesConverter(forClass = MpTabelaInterna.class)
public class MpTabelaInternaConverter implements Converter, ClientConverter {

	@Inject
	private MpTabelaInternas tabelaInternas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTabelaInterna retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = tabelaInternas.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpTabelaInterna tabelaInterna = (MpTabelaInterna) value;
			return tabelaInterna.getId() == null ? null : tabelaInterna.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpTabelaInterna";
	}

}
