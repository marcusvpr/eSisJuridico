package com.mpxds.mpbasic.model.adricred;

import java.io.ByteArrayInputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.validation.constraints.NotNull;

import org.primefaces.model.DefaultStreamedContent;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;

@Entity
@Table(name = "mp_arquivo_bd")
public class MpDocumentoBD extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String descricao;

	private byte[] arquivo;
	
	private MpArquivoAcao mpArquivoAcao;
	
	private MpClienteConsignado mpClienteConsignado;

	
	// ---------------

	@Column(nullable = true)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
    public byte[] getArquivo() { return this.arquivo; }
    public void setArquivo(byte[] newArquivo) { this.arquivo = newArquivo; }
    	
	@Enumerated(EnumType.STRING)
	@Column(name = "mparquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	
	@ManyToOne
	@JoinColumn(name = "mpClienteConsignado_id", nullable = false)
	public MpClienteConsignado getMpClienteConsignado() { return mpClienteConsignado; }
	public void setMpClienteConsignado(MpClienteConsignado mpClienteConsignado) { 
																	this.mpClienteConsignado = mpClienteConsignado; }
	
	// ---
    
	@Transient
	public DefaultStreamedContent getArquivoDSC() {
		if (null == this.arquivo)
			return new DefaultStreamedContent();
		else
			return new DefaultStreamedContent(new ByteArrayInputStream(this.arquivo));
	}

}
