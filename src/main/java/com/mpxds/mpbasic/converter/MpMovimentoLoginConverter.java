package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.repository.MpMovimentoLogins;

@FacesConverter(forClass = MpMovimentoLogin.class)
public class MpMovimentoLoginConverter implements Converter, ClientConverter {

	@Inject
	private MpMovimentoLogins mpMovimentoLogins;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpMovimentoLogin retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.mpMovimentoLogins.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpMovimentoLogin) value).getId())
				return "";
			else
				return ((MpMovimentoLogin) value).getId().toString();
		}
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpMovimentoLogin";
	}

}