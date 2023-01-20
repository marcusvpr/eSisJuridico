package com.mpxds.mpbasic.model.sisJuri;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;

@Entity
@Table(name = "MP_SJ_PESSOA")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MpPessoaSJ extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean indResponsavel;	
	private Boolean indParteContraria;	
	private String oab;

	private MpEstadoUF mpOabUF;	
	private MpTabelaInternaSJ mpBanco; // TAB_0015

	private String bancoAgencia;	
	private String bancoConta;
	private String email;
	private String telefone;
	private String webPage;
	private String observacao;
		  
	private MpEnderecoLocal mpEnderecoLocal;

	private byte[] arquivoBD;

	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
    private Long idCarga;

	// ---
	
	public MpPessoaSJ() {
		super();
	}

	// ---
	
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100) // , unique = true)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Column(name = "ind_Responsavel", nullable = true)
	public Boolean getIndResponsavel() { return indResponsavel; }
	public void setIndResponsavel(Boolean indResponsavel) { this.indResponsavel = indResponsavel; }

	@Column(name = "ind_Parte_Contraria", nullable = true)
	public Boolean getIndParteContraria() {	return indParteContraria; }
	public void setIndParteContraria(Boolean indParteContraria) { this.indParteContraria = indParteContraria; }

	@Column(nullable = false, length = 50)
	public String getOab() { return oab; }
	public void setOab(String oab) { this.oab = oab; }

	@Enumerated(EnumType.STRING)
	@Column(name = "mpOab_UF", nullable = true, length = 2)
	public MpEstadoUF getMpOabUF() { return mpOabUF; }
	public void setMpOabUF(MpEstadoUF mpOabUF) { this.mpOabUF = mpOabUF; }

	@ManyToOne
	@JoinColumn(name = "mpBanco_id", nullable = true)
	public MpTabelaInternaSJ getMpBanco() { return mpBanco; }
	public void setMpBanco(MpTabelaInternaSJ mpBanco) { this.mpBanco = mpBanco; }

	@Column(name = "banco_Agencia", nullable = true, length = 10)
	public String getBancoAgencia() { return bancoAgencia; }
	public void setBancoAgencia(String bancoAgencia) { this.bancoAgencia = bancoAgencia; }

	@Column(name = "banco_Conta", nullable = true, length = 20)
	public String getBancoConta() { return bancoConta; }
	public void setBancoConta(String bancoConta) { this.bancoConta = bancoConta; }

	@NotBlank(message = "Por favor, informe o EMAIL")
	@Column(nullable = false, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 50)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Column(name = "web_Page", nullable = true, length = 255)
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
		
	@Column(nullable = true, length = 255)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) { 
															this.mpEnderecoLocal = mpEnderecoLocal; }

	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
	public byte[] getArquivoBD() { return arquivoBD; }
	public void setArquivoBD(byte[] arquivoBD) { this.arquivoBD = arquivoBD; }

	@Enumerated(EnumType.STRING)
	@Column(name = "mpArquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }

	@ManyToOne
	@JoinColumn(name = "mpArquivoBD_id", nullable = true)
	public MpArquivoBD getMpArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }

	@Column(name = "id_carga", nullable = true)
	public Long getIdCarga() { return this.idCarga; }
	public void setIdCarga(Long idCarga) { this.idCarga = idCarga; }
	
}
