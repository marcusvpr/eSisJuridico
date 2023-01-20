package com.mpxds.mpbasic.model.webVideo ;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class MpEnderecoComercial implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String logradouroCom;
	private String complementoCom;
	private String bairroCom;
	private String cepCom;
	private String cidadeCom;  // Tab.0010
	private String ufCom;  // Tab.0005
	
	public MpEnderecoComercial() {
		super();
	}
	
	public static MpEnderecoComercial novoEnderecoCom() {
		return new MpEnderecoComercial();
	}

	public String getLogradouroCom() {
		return this.logradouroCom;
	}
	public MpEnderecoComercial setLogradouroCom(String logradouroCom) {
		this.logradouroCom = logradouroCom;
		return this;
	}

	public String getComplementoCom() {
		return this.complementoCom;
	}
	public MpEnderecoComercial setComplementoCom(String complementoCom) {
		this.complementoCom = complementoCom;
		return this;
	}

	public String getBairroCom() {
		return this.bairroCom;
	}
	public MpEnderecoComercial setBairroCom(String bairroCom) {
		this.bairroCom = bairroCom;
		return this;
	}

	public String getCepCom() {
		return this.cepCom;
	}
	public MpEnderecoComercial setCepCom(String cepCom) {
		this.cepCom = cepCom;
		return this;
	}

	public String getCidadeCom() {
		return this.cidadeCom;
	}
	public MpEnderecoComercial setCidadeCom(String cidadeCom) {
		this.cidadeCom = cidadeCom;
		return this;
	}

	public String getUfCom() {
		return this.ufCom;
	}
	public MpEnderecoComercial setUfCom(String ufCom) {
		this.ufCom = ufCom;
		return this;
	}
	  
}