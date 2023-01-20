package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpDependente;
import com.mpxds.mpbasic.repository.MpDependentes;

@FacesConverter(forClass = MpDependente.class)
public class MpDependenteConverter implements Converter, ClientConverter {

	@Inject
	private MpDependentes mpDependentes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpDependente retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.mpDependentes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpDependente) value).getId())
				return "";
			else
				return ((MpDependente) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpDependente";
	}

}