package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.repository.MpPessoas;

@FacesConverter(forClass = MpPessoa.class)
public class MpPessoaConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoas mpPessoas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoa retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpPessoas.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoa) value).getId())
				return "";
			else
				return ((MpPessoa) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoa";
	}

}