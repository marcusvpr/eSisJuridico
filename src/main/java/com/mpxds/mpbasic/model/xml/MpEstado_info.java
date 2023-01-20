package com.mpxds.mpbasic.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="estado_info")
public class MpEstado_info {
	
	private String area_km2;
	private String codigo_ibge;
	private String nome;

	@XmlElement(name="area_km2")
	public String getArea_km2() { return area_km2; 	}
	public void setArea_km2(String newArea_km2) { this.area_km2 = newArea_km2; }
	
	@XmlElement(name="codigo_ibge")
	public String getCodigo_ibge() { return codigo_ibge; 	}
	public void setCodigo_ibge(String newCodigo_ibge) { this.codigo_ibge = newCodigo_ibge; }
	
	@XmlElement(name="nome")
	public String getNome() { return nome; 	}
	public void setNome(String newNome) { this.nome = newNome; }
	
}
