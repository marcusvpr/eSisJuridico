package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.repository.MpAtividades;

@FacesConverter(forClass = MpAtividade.class)
public class MpAtividadeConverter implements Converter, ClientConverter {

	@Inject
	private MpAtividades atividades;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpAtividade retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = atividades.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpAtividade atividade = (MpAtividade) value;
			return atividade.getId() == null ? null : atividade.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpAtividade";
	}

}
