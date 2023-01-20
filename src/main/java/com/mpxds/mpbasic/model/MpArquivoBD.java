package com.mpxds.mpbasic.model;

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

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;

@Entity
@Table(name = "mp_arquivo_bd")
public class MpArquivoBD extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String descricao;
	private Boolean indGlobal;

	private byte[] arquivo;
	
	private MpTabelaInterna mpGrupoTI; // tab_0007 
	
	private MpArquivoAcao mpArquivoAcao;
	
	// ---------------

	@Column(nullable = true)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Column(nullable = true)
	public Boolean getIndGlobal() { return indGlobal; }
	public void setIndGlobal(Boolean indGlobal) { this.indGlobal = indGlobal; }

	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
    public byte[] getArquivo() { return this.arquivo; }
    public void setArquivo(byte[] newArquivo) { this.arquivo = newArquivo; }

	@ManyToOne
	@JoinColumn(name = "mpGrupoTI_id", nullable = true)
	public MpTabelaInterna getMpGrupoTI() { return mpGrupoTI; }
	public void setMpGrupoTI(MpTabelaInterna mpGrupoTI) { this.mpGrupoTI = mpGrupoTI; }
    	
	@Enumerated(EnumType.STRING)
	@Column(name = "mparquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
    
	@Transient
	public DefaultStreamedContent getArquivoDSC() {
		if (null == this.arquivo)
			return new DefaultStreamedContent();
		else
			return new DefaultStreamedContent(new ByteArrayInputStream(this.arquivo));
	}

}
