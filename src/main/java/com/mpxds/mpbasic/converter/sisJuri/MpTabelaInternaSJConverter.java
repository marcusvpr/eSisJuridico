package com.mpxds.mpbasic.converter.sisJuri;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;

@FacesConverter(forClass = MpTabelaInternaSJ.class)
public class MpTabelaInternaSJConverter implements Converter, ClientConverter {

	@Inject
	private MpTabelaInternaSJs tabelaInternaSJs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTabelaInternaSJ retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = tabelaInternaSJs.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpTabelaInternaSJ tabelaInternaSJ = (MpTabelaInternaSJ) value;
			return tabelaInternaSJ.getId() == null ? null : tabelaInternaSJ.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpTabelaInterna";
	}

}
