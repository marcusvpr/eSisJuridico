package com.mpxds.mpbasic.model.webVideo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Table(name="mp_wv_coligada",
uniqueConstraints = @UniqueConstraint(columnNames = "CODIGO"))
public class MpColigada extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String codigo;

	private MpEmpresa mpEmpresa;

	public MpColigada() {
	  super();
	}

	public MpColigada(String codigo
              	, MpEmpresa mpEmpresa
                 ) {
	  this.codigo = codigo;
	  this.mpEmpresa = mpEmpresa;
	}

	@Column(nullable = false, length = 5)
	public String getCodigo() {	return this.codigo; }
	public void setCodigo(String newCodigo) { this.codigo = newCodigo; }

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="mpEmpresaId")
	public MpEmpresa getMpEmpresa() { return this.mpEmpresa; }
	public void setMpEmpresa(MpEmpresa newMpEmpresa) { this.mpEmpresa = newMpEmpresa; }
	  
}
