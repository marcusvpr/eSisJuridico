package com.mpxds.mpbasic.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
 
@ManagedBean(name="mpCountry")
@SessionScoped
public class MpCountryBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static Map<String,String> countries;
	
	private String localeCode = "pt-br"; //default value 
	
	static{
		countries = new LinkedHashMap<String,String>();

		countries.put("Brasil", "pt-br"); //label, value
		countries.put("United Kingdom", "en");
		countries.put("French", "fr");
		countries.put("German", "de");
		countries.put("China", "zh_CN");
	}

	public void countryLocaleCodeChanged(ValueChangeEvent e){
		//assign new value to localeCode
		localeCode = e.getNewValue().toString();
		
	}

	public Map<String,String> getCountryInMap() {
		return this.countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

}