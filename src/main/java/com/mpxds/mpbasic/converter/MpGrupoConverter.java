package com.mpxds.mpbasic.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.repository.MpGrupos;

@FacesConverter(forClass = MpGrupo.class)
public class MpGrupoConverter implements Converter {

	@Inject
	private MpGrupos mpGrupos;
	
	// -----

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpGrupo retorno = null;
		//
		if (StringUtils.isNotEmpty(value))
			retorno = this.mpGrupos.porId(new Long(value));
		//
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null)
			if (null == ((MpGrupo) value).getId())
				return "";
			else
				return ((MpGrupo) value).getId().toString();
		//
		return "";
	}

}