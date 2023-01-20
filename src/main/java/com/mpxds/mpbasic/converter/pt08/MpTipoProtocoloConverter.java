package com.mpxds.mpbasic.converter.pt08;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt08.MpTipoProtocolo;
import com.mpxds.mpbasic.repository.pt08.MpTipoProtocolos;

@FacesConverter(forClass = MpTipoProtocolo.class)
public class MpTipoProtocoloConverter implements Converter, ClientConverter {

	@Inject
	private MpTipoProtocolos mpTipoProtocolos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTipoProtocolo retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpTipoProtocolos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpTipoProtocolo) value).getId())
				return "";
			else
				return ((MpTipoProtocolo) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpTipoProtocolo";
	}

}