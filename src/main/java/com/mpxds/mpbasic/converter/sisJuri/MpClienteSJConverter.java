package com.mpxds.mpbasic.converter.sisJuri;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;

@FacesConverter(forClass = MpClienteSJ.class)
public class MpClienteSJConverter implements Converter, ClientConverter {

	@Inject
	private MpClienteSJs mpClienteSJs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpClienteSJ retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpClienteSJs.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpClienteSJ) value).getId())
				return "";
			else
				return ((MpClienteSJ) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpClienteSJ";
	}

}