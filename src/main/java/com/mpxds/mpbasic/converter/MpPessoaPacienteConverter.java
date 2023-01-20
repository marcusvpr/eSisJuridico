package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpPessoaPaciente;
import com.mpxds.mpbasic.repository.MpPessoaPacientes;

@FacesConverter(forClass = MpPessoaPaciente.class)
public class MpPessoaPacienteConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoaPacientes mpPessoaPacientes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoaPaciente retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.mpPessoaPacientes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoaPaciente) value).getId())
				return "";
			else
				return ((MpPessoaPaciente) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoaPaciente";
	}

}