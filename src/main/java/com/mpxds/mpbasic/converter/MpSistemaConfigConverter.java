package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;

@FacesConverter(forClass = MpSistemaConfig.class)
public class MpSistemaConfigConverter implements Converter, ClientConverter {

	@Inject
	private MpSistemaConfigs sistemaConfigs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpSistemaConfig retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = sistemaConfigs.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpSistemaConfig sistemaConfig = (MpSistemaConfig) value;
			return sistemaConfig.getId() == null ? null : sistemaConfig.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpSistemaConfig";
	}

}
