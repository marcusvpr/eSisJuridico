package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.repository.MpPacientes;

@FacesConverter(forClass = MpPaciente.class)
public class MpPacienteConverter implements Converter {

	@Inject
	private MpPacientes mpPacientes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPaciente retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpPacientes.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPaciente) value).getId())
				return "";
			else
				return ((MpPaciente) value).getId().toString();
		}
		//
		return "";
	}

}