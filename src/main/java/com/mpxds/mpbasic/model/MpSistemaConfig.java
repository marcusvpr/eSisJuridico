package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpTipoCampo;

@Entity
@Table(name="mp_sistema_config")
public class MpSistemaConfig extends MpBaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String parametro; 
	private String descricao;
	
	private MpTipoCampo mpTipoCampo = MpTipoCampo.TEXTO ;
	
	private String valorT = "";
	private Integer valorN = 0;
	private Boolean indValor = false;

	private String objeto;

	// ---
	
	public MpSistemaConfig() {
	  super();
	}

	public MpSistemaConfig(String parametro
						, String descricao
						, MpTipoCampo mpTipoCampo
						, String valorT
						, Integer valorN
						, Boolean indValor
						, String objeto) {
		this.parametro = parametro;
		this.descricao = descricao;
		this.mpTipoCampo = mpTipoCampo;
		this.valorT = valorT;
		this.valorN = valorN;
		this.indValor = indValor;
		this.objeto = objeto;
	}

	@NotBlank(message = "Por favor, informe o PARAMÊTRO")
	@Column(nullable = false, length = 100)
	public String getParametro() { return this.parametro; }
	public void setParametro(String newParametro) { this.parametro = newParametro; }

	@NotBlank(message = "Por favor, informe o DESCRIÇÃO")
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	@NotNull(message = "Por favor, informe o TIPO CAMPO")
	@Column(nullable = false)
	public MpTipoCampo getMpTipoCampo() { return this.mpTipoCampo; }
	public void setMpTipoCampo(MpTipoCampo newMpTipoCampo) { this.mpTipoCampo = newMpTipoCampo; }

	@NotNull(message = "Por favor, informe o VALOR t")
	@Column(nullable = false, length = 1000)
	public String getValorT() { return this.valorT; }
	public void setValorT(String newValorT) { this.valorT = newValorT; }

	@NotNull(message = "Por favor, informe o VALOR n")
	@Column(nullable = false)
	public Integer getValorN() { return this.valorN; }
	public void setValorN(Integer newValorN) { this.valorN = newValorN; }

	@NotNull(message = "Por favor, informe o VALOR b")
	@Column(nullable = false)
	public Boolean getIndValor() { return this.indValor; }
	public void setIndValor(Boolean newIndValor) { this.indValor = newIndValor; }

	@Column(nullable = true, length = 30)
	public String getObjeto() { return this.objeto; }
	public void setObjeto(String newObjeto) { this.objeto = newObjeto; }

	@Transient
	public String getValor() {
		if (this.mpTipoCampo.equals(MpTipoCampo.TEXTO))
			return this.valorT.trim();

		if (this.mpTipoCampo.equals(MpTipoCampo.NUMERO))
			return "" + this.valorN;

		return "" + this.indValor;
	}

	@Transient
	public String getParametroValor() {
		if (this.mpTipoCampo.equals(MpTipoCampo.TEXTO))
			return this.parametro.trim() + " / " + this.valorT.trim();

		if (this.mpTipoCampo.equals(MpTipoCampo.NUMERO))
			return this.parametro.trim() + " / " + this.valorN;

		return this.parametro.trim() + " / " + this.indValor;
	}

}
