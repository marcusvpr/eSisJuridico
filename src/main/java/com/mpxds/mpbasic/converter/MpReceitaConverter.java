package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.repository.MpReceitas;

@FacesConverter(forClass = MpReceita.class)
public class MpReceitaConverter implements Converter, ClientConverter {

	@Inject
	private MpReceitas receitas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpReceita retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = receitas.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpReceita receita = (MpReceita) value;
			return receita.getId() == null ? null : receita.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpReceita";
	}

}
