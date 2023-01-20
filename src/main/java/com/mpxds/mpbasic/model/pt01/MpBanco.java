package com.mpxds.mpbasic.model.pt01;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Embedded;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpEnderecoLocal;

@Entity
@Audited
@AuditTable(value="mp_pt01_banco_")
@Table(name="mp_pt01_banco")
public class MpBanco extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;  
	private Integer agencia;  
	private String nome;  
	private String tipoDocumento;  
	private String numeroDocumento;  
	
	private MpEnderecoLocal mpEnderecoLocal;

	// ---
	
	public MpBanco() {
		super();
	}

  	public MpBanco(String codigo
             , Integer agencia
             , String nome
             , String tipoDocumento
             , String numeroDocumento
             , MpEnderecoLocal mpEnderecoLocal
             ) {
  		this.codigo = codigo;
  		this.agencia = agencia;
  		this.nome = nome;
  		this.tipoDocumento = tipoDocumento;
  		this.numeroDocumento = numeroDocumento;
  		this.mpEnderecoLocal = mpEnderecoLocal;
  	}

  	// ---
  	
    @Column(name = "codigo", nullable = false, length = 7)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

    @Column(name = "agencia", nullable = false, length = 5)
	public Integer getAgencia() { return agencia; }
	public void setAgencia(Integer agencia) { this.agencia = agencia; }

  	@Column(name = "nome", nullable = false, length = 135)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

  	@Column(name = "tipo_documento", nullable = false, length = 3)
	public String getTipoDocumento() { return tipoDocumento; }
	public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

	@Column(name = "numero_documento", nullable = false, length = 14)
	public String getNumeroDocumento() { return numeroDocumento; }
	public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) {
															this.mpEnderecoLocal = mpEnderecoLocal; }
  	
}
