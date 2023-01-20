package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.repository.MpPedidos;

@FacesConverter(forClass = MpPedido.class)
public class MpPedidoConverter implements Converter, ClientConverter {

	@Inject
	private MpPedidos mpPedidos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPedido retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpPedidos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpPedido mpPedido = (MpPedido) value;
			return mpPedido.getId() == null ? null : mpPedido.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPedido";
	}

}
