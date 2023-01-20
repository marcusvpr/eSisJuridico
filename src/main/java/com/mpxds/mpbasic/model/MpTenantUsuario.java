package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mpxds.mpbasic.model.enums.MpStatusObjeto;

@Entity
@Table(name = "mp_tenant_usuario")
public class MpTenantUsuario extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String ambiente;
	private String observacao;
	
	private MpStatusObjeto mpStatusObjeto;
	private MpUsuarioTenant mpUsuarioTenant;

	// ---
	
	@Column(nullable = true, length = 30)
	public String getAmbiente() { return ambiente; }
	public void setAmbiente(String ambiente) { this.ambiente = ambiente; }

	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpStatusObjeto getMpStatusObjeto() { return mpStatusObjeto; }
	public void setMpStatusObjeto(MpStatusObjeto mpStatusObjeto) {
													this.mpStatusObjeto = mpStatusObjeto; }

	@ManyToOne
	@JoinColumn(name = "mpUsuario_id", nullable = false)
	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) { 
															this.mpUsuarioTenant = mpUsuarioTenant; }

	@Transient
	public boolean isUsuarioAssociado() {
		return this.getMpUsuarioTenant() != null && this.getMpUsuarioTenant().getId() != null; }	

}