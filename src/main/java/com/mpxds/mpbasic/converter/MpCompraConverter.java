package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpCompra;
import com.mpxds.mpbasic.repository.MpCompras;

@FacesConverter(forClass = MpCompra.class)
public class MpCompraConverter implements Converter, ClientConverter {

	@Inject
	private MpCompras mpCompras;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpCompra retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpCompras.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpCompra mpCompra = (MpCompra) value;
			return mpCompra.getId() == null ? null : mpCompra.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpCompra";
	}

}
