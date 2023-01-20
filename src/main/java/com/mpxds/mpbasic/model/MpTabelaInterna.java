package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;

@Entity
@Table(name="mp_tabela_interna")
public class MpTabelaInterna extends MpBaseEntity {
	private static final long serialVersionUID = 1L;
	
	private MpTipoTabelaInterna mpTipoTabelaInterna;	  
	private String codigo;
	private String descricao;
	
	private Boolean indPai = false;;
	private Boolean indFilha = false;
	//	
	private MpTabelaInterna mpPai;
	
	private List<MpTabelaInterna> mpPais = new ArrayList<>();

	// ---
	
	public MpTabelaInterna() {
	  super();
	}

	public MpTabelaInterna(MpTipoTabelaInterna mpTipoTabelaInterna
                   , String codigo
                   , String descricao
                 ) {
      this.mpTipoTabelaInterna = mpTipoTabelaInterna;
	  this.codigo = codigo;
	  this.descricao = descricao;
	}

	@NotNull(message = "Por favor, informe o Tipo Tabela")
	@Column(name="mpTipo_tabela_interna", nullable = false)
	@Enumerated(EnumType.STRING)
	public MpTipoTabelaInterna getMpTipoTabelaInterna() { return this.mpTipoTabelaInterna; }
	public void setMpTipoTabelaInterna(MpTipoTabelaInterna newMpTipoTabelaInterna) { 
																this.mpTipoTabelaInterna = newMpTipoTabelaInterna; }

	@NotBlank(message = "Por favor, informe o Código")
	@Column(nullable=false)
	public String getCodigo() { return this.codigo; }
	public void setCodigo(String newCodigo) { this.codigo = newCodigo; }

	@NotBlank(message = "Por favor, informe a Descrição")
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	public Boolean getIndPai() { return this.indPai; }
	public void setIndPai(Boolean newIndPai) { this.indPai = newIndPai; }
	public Boolean getIndFilha() { return this.indFilha; }
	public void setIndFilha(Boolean newIndFilha) { this.indFilha = newIndFilha; }

	@OneToOne
	@JoinColumn(name="mpPai_Id")
	public MpTabelaInterna getMpPai() { return this.mpPai; }
	public void setMpPai(MpTabelaInterna newMpPai) { this.mpPai = newMpPai; }
	
	@OneToMany(mappedBy = "mpPai", cascade = CascadeType.ALL, orphanRemoval = true,
																		fetch = FetchType.LAZY)
	public List<MpTabelaInterna> getMpPais() { return mpPais; }
	public void setMpPais(List<MpTabelaInterna> mpPais) { this.mpPais = mpPais; }

	@Transient
	public String getDescricaoNumero() { 
		if (null==this.descricao) this.descricao = "";
		//
	    return this.descricao.trim() + " ( " + this.mpTipoTabelaInterna.getDescricao() + " )";
	}
	
}
