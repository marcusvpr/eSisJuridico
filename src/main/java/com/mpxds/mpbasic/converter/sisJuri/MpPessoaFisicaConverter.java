package com.mpxds.mpbasic.converter.sisJuri;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;

@FacesConverter(forClass = MpPessoaFisica.class)
public class MpPessoaFisicaConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoaFisicas mpPessoaFisicas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoaFisica retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpPessoaFisicas.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoaFisica) value).getId())
				return "";
			else
				return ((MpPessoaFisica) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoaFisica";
	}

}