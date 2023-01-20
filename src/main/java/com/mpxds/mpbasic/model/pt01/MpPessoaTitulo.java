package com.mpxds.mpbasic.model.pt01;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpEnderecoLocal;

@Audited
@AuditTable(value="mp_pt01_pessoa_titulo_")
@Entity
@Table(name="mp_pt01_pessoa_titulo")
public class MpPessoaTitulo extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;	
	private String tipoDocumento;
	private String numeroDocumento;
	private MpEnderecoLocal mpEnderecoLocal; 

	// ----------
	
	public MpPessoaTitulo() {
		super();
	}

  	@Column(name = "nome", nullable = false, length = 150)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

  	@Column(name = "tipo_documento", nullable = false, length = 10)
	public String getTipoDocumento() { return tipoDocumento; }
	public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

	@Column(name = "numero_documento", nullable = false, length = 50)
	public String getNumeroDocumento() { return numeroDocumento; }
	public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) { this.mpEnderecoLocal = mpEnderecoLocal; }
	
}
