package com.mpxds.mpbasic.converter.adricred;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.adricred.MpClienteConsignado;
import com.mpxds.mpbasic.repository.adricred.MpClienteConsignados;

@FacesConverter(forClass = MpClienteConsignado.class)
public class MpClienteConsignadoConverter implements Converter, ClientConverter {

	@Inject
	private MpClienteConsignados mpClienteConsignados;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpClienteConsignado retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpClienteConsignados.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpClienteConsignado) value).getId())
				return "";
			else
				return ((MpClienteConsignado) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpClienteConsignado";
	}

}