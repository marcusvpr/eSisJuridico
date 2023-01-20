package com.mpxds.mpbasic.model.pt01;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt01_observacao_")
@Table(name = "mp_pt01_observacao")
public class MpObservacao extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String tipoObservacao;	
	private String descricaoObservacao;

	// ---
	
	public MpObservacao(){
		super();
    }
	
	// ---

	@NotBlank(message = "Por favor, informe o Tipo Observação")	
	@Column(name = "tipo_Observacao", nullable = false, length = 15)
	public String getTipoObservacao() { return tipoObservacao; }
	public void setTipoObservacao(String tipoObservacao) { this.tipoObservacao = tipoObservacao; }

	@NotBlank(message = "Por favor, informe a Descrição Observação")	
	@Column(name = "descricao_Observacao", nullable = false, length = 15)
	public String getDescricaoObservacao() { return descricaoObservacao; }
	public void setDescricaoObservacao(String descricaoObservacao) { 
															this.descricaoObservacao = descricaoObservacao; }
	
}
