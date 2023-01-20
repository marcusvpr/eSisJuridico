package com.mpxds.mpbasic.converter.sisJuri;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.sisJuri.MpPessoaJuridica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaJuridicas;

@FacesConverter(forClass = MpPessoaJuridica.class)
public class MpPessoaJuridicaConverter implements Converter, ClientConverter {

	@Inject
	private MpPessoaJuridicas mpPessoaJuridicas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpPessoaJuridica retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.mpPessoaJuridicas.porId(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpPessoaJuridica) value).getId())
				return "";
			else
				return ((MpPessoaJuridica) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpPessoaJuridica";
	}

}