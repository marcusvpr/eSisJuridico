package com.mpxds.mpbasic.model.pt08;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt08_tipo_protocolo_")
@Table(name = "mp_pt08_tipo_protocolo")
public class MpTipoProtocolo extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;	
	private String descricao;	
	private String observacao;	
	private String codigoAto;	
	private String atoSequencia;
	private Integer prazoEntrega; 
	
	// ---
	
	public MpTipoProtocolo(){
		super();
	}
	
	// ---

	@NotBlank(message = "Por favor, informe o Codigo")	
	@Column(nullable = false, length = 1)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	@NotBlank(message = "Por favor, informe a Descrição")	
	@Column(nullable = false, length = 15)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@NotBlank(message = "Por favor, informe a Observação")	
	@Column(nullable = false, length = 300)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@NotBlank(message = "Por favor, informe o Codigo Ato")	
	@Column(name = "codigo_ato", nullable = false, length = 4)
	public String getCodigoAto() { return codigoAto; }
	public void setCodigoAto(String codigoAto) { this.codigoAto = codigoAto; }

	@NotBlank(message = "Por favor, informe o AtoSequencia")	
	@Column(name = "ato_sequencia", nullable = false, length = 1)
	public String getAtoSequencia() { return atoSequencia; }
	public void setAtoSequencia(String atoSequencia) { this.atoSequencia = atoSequencia; }

	@Column(name = "prazo_entrega", nullable = true, length = 4)
	public Integer getPrazoEntrega() { return prazoEntrega; }
	public void setPrazoEntrega(Integer prazoEntrega) { this.prazoEntrega = prazoEntrega; }
	
}