package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.repository.MpClientes;

@FacesConverter(forClass = MpCliente.class)
public class MpClienteConverter implements Converter, ClientConverter {

	@Inject
	private MpClientes mpClientes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpCliente retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpClientes.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpCliente) value).getId())
				return "";
			else
				return ((MpCliente) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpCliente";
	}

}