package com.mpxds.mpbasic.model.pt01;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt01_especie_")
@Table(name="mp_pt01_especie")
public class MpEspecie extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String especieCodigo;  	
	private String descricao;  
	private Integer numeroCodigo;  

	// ---
	
	public MpEspecie() {
		super();
	}

  	public MpEspecie(String especieCodigo
             , String descricao
             , Integer numeroCodigo
             ) {
  		this.especieCodigo = especieCodigo;
  		this.descricao = descricao;
  		this.numeroCodigo = numeroCodigo;
  	}
  	
  	// ---

    @Column(name = "especie_codigo", nullable = false, length = 3)
	public String getEspecieCodigo() { return especieCodigo; }
	public void setEspecieCodigo(String especieCodigo) { this.especieCodigo = especieCodigo; }

  	@Column(nullable = false, length = 25)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

  	@Column(name = "numero_codigo", nullable = false, length = 2)
	public Integer getNumeroCodigo() { return numeroCodigo; }
	public void setNumeroCodigo(Integer numeroCodigo) { this.numeroCodigo = numeroCodigo; }
 
}
