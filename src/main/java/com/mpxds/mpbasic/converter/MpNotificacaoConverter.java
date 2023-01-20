package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpNotificacao;
import com.mpxds.mpbasic.repository.MpNotificacaos;

@FacesConverter(forClass = MpNotificacao.class)
public class MpNotificacaoConverter implements Converter, ClientConverter {

	@Inject
	private MpNotificacaos notificacaos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpNotificacao retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = notificacaos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpNotificacao notificacao = (MpNotificacao) value;
			return notificacao.getId() == null ? null : notificacao.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpNotificacao";
	}

}
