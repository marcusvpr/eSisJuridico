package com.mpxds.mpbasic.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.repository.MpProdutos;

@FacesConverter(forClass = MpProduto.class)
public class MpErrorLogConverter implements Converter, ClientConverter {

	@Inject
	private MpProdutos produtos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MpProduto retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = produtos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MpProduto produto = (MpProduto) value;
			return produto.getId() == null ? null : produto.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.mpxds.MpProduto";
	}

}
