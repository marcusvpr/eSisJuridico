package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.dto.MpTabelaBDDTO;
import com.mpxds.mpbasic.repository.MpTabelaBDs;

@FacesConverter(forClass = MpTabelaBDDTO.class)
public class MpTabelaBDDTOConverter implements Converter, ClientConverter {

	@Inject
	private MpTabelaBDs mpTabelaBDs;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpTabelaBDDTO retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			String tabela = new String(value);
			retorno = mpTabelaBDs.porTabela(tabela);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (null == ((MpTabelaBDDTO) value).getTabela())
				return "";
			else
				return ((MpTabelaBDDTO) value).getTabela();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpTabelaBDDTO";
	}

}