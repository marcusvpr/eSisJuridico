package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.repository.MpContatos;

@FacesConverter(forClass = MpContato.class)
public class MpContatoConverter implements Converter, ClientConverter {

	@Inject
	private MpContatos mpContatos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpContato retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpContatos.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpContato) value).getId())
				return "";
			else
				return ((MpContato) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpContato";
	}

}