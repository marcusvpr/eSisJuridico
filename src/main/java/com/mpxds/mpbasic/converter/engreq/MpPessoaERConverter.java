package com.mpxds.mpbasic.converter.engreq;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.repository.engreq.MpPessoaERs;

@FacesConverter(forClass = MpPessoaER.class)
public class MpPessoaERConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoaERs mpPessoaERs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoaER retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpPessoaERs.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoaER) value).getId())
				return "";
			else
				return ((MpPessoaER) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoaER";
	}

}