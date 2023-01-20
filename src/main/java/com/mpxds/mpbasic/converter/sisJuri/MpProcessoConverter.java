package com.mpxds.mpbasic.converter.sisJuri;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessos;

@FacesConverter(forClass = MpProcesso.class)
public class MpProcessoConverter implements Converter, ClientConverter {

	@Inject
	private MpProcessos mpProcessos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpProcesso retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpProcessos.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpProcesso) value).getId())
				return "";
			else
				return ((MpProcesso) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpProcesso";
	}

}