package com.mpxds.mpbasic.converter.engreq;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.engreq.MpProjetoPessoaER;
import com.mpxds.mpbasic.repository.engreq.MpProjetoPessoaERs;

@FacesConverter(forClass = MpProjetoPessoaER.class)
public class MpProjetoPessoaERConverter implements Converter, ClientConverter {

	@Inject
	private MpProjetoPessoaERs mpProjetoPessoaERs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpProjetoPessoaER retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpProjetoPessoaERs.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpProjetoPessoaER) value).getId())
				return "";
			else
				return ((MpProjetoPessoaER) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpProjetoPessoaER";
	}

}