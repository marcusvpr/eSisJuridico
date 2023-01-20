package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.repository.MpNotificacaoUsuarios;

@FacesConverter(forClass = MpNotificacaoUsuario.class)
public class MpNotificacaoUsuarioConverter implements Converter, ClientConverter {

	@Inject
	private MpNotificacaoUsuarios notificacaoUsuarios;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpNotificacaoUsuario retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = notificacaoUsuarios.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpNotificacaoUsuario notificacaoUsuario = (MpNotificacaoUsuario) value;
			return notificacaoUsuario.getId() == null ? null : notificacaoUsuario.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpNotificacaoUsuario";
	}

}
