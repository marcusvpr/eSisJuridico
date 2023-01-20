package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpStatusObjeto;

@Entity
@Table(name="mp_tenant")
public class MpTenant extends MpBaseEntity {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String descricao;
	
	private MpStatusObjeto mpStatusObjeto;
	
	// ---
	
	public MpTenant() {
		super();
	}

	public MpTenant(String codigo, String descricao, MpStatusObjeto mpStatusObjeto) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.mpStatusObjeto = mpStatusObjeto;
	}

	@NotBlank(message = "Por favor, informe o CÓDIGO")
	@Column(nullable = false, length = 50)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	@NotBlank(message = "Por favor, informe a DESCRIÇÃO")
	@Column(nullable = false, length = 150)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10, name = "mpStatus_objeto")
	public MpStatusObjeto getMpStatusObjeto() { return this.mpStatusObjeto; }
	public void setMpStatusObjeto(MpStatusObjeto newMpStatusObjeto) {
													this.mpStatusObjeto = newMpStatusObjeto; }  

}
