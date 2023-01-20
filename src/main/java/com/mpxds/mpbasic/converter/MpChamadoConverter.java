package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpChamado;
import com.mpxds.mpbasic.repository.MpChamados;

@FacesConverter(forClass = MpChamado.class)
public class MpChamadoConverter implements Converter, ClientConverter {

	@Inject
	private MpChamados chamados;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpChamado retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = chamados.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpChamado chamado = (MpChamado) value;
			return chamado.getId() == null ? null : chamado.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpChamado";
	}

}
