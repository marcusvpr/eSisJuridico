package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.repository.MpAlertaLogs;

@FacesConverter(forClass = MpAlertaLog.class)
public class MpAlertaLogConverter implements Converter, ClientConverter {

	@Inject
	private MpAlertaLogs mpAlertaLogs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpAlertaLog retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpAlertaLogs.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpAlertaLog) value).getId())
				return "";
			else
				return ((MpAlertaLog) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpAlertaLog";
	}

}