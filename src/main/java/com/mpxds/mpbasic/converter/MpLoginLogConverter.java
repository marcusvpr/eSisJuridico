package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.log.MpSistemaLog;
import com.mpxds.mpbasic.repository.MpSistemaLogs;

@FacesConverter(forClass = MpSistemaLog.class)
public class MpLoginLogConverter implements Converter, ClientConverter {

	@Inject
	private MpSistemaLogs sistemaLogs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpSistemaLog retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = sistemaLogs.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpSistemaLog sistemaLog = (MpSistemaLog) value;
			return sistemaLog.getId() == null ? null : sistemaLog.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpSistemaLog";
	}

}
