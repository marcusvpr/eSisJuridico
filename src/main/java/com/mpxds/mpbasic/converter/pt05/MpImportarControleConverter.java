package com.mpxds.mpbasic.converter.pt05;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.pt05.MpImportarControle;
import com.mpxds.mpbasic.repository.pt05.MpImportarControles;

@FacesConverter(forClass = MpImportarControle.class)
public class MpImportarControleConverter implements Converter, ClientConverter {

	@Inject
	private MpImportarControles mpImportarControles;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpImportarControle retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = mpImportarControles.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpImportarControle) value).getId())
				return "";
			else
				return ((MpImportarControle) value).getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpImportaControles";
	}

}