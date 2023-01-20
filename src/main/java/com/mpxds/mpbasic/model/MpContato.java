package com.mpxds.mpbasic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpIndicadorIE;
import com.mpxds.mpbasic.model.enums.MpSexo;

@Entity
@Table(name = "mp_contato")
public class MpContato extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nomeRazaoSocial;
	private String cpfCnpj;
	private String nomeFantasia;
	private String nomeContato;
	private String email;
	private String telefone;
	private String celular;

	private String inscricaoEstadual;
	private String inscricaoEstadualSubstTrib;
	private String inscricaoMunicipal;
	private String suframa;
	private Date dataNascimento;
	private String observacao;
	
	private MpIndicadorIE mpIndicadorIE;
	private MpSexo mpSexo;

	private Boolean indCliente;
	private Boolean indFornecedor;
	private Boolean indTransportador;
	private Boolean indClienteFinal;

	private MpEnderecoLocal mpEnderecoLocal;

	private byte[] arquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
	// ---------------

	@NotBlank(message = "Por favor, informe o NOME ou RAZÃO SOCIAL")
	@Column(nullable = false, length = 100)
	public String getNomeRazaoSocial() { return nomeRazaoSocial; }
	public void setNomeRazaoSocial(String nomeRazaoSocial) { this.nomeRazaoSocial = nomeRazaoSocial; }

	@Column(nullable = true, length = 20)
	public String getCpfCnpj() { return cpfCnpj; }
	public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

	@Column(nullable = true, length = 100)
	public String getNomeFantasia() { return nomeFantasia; }
	public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

	@Column(nullable = true, length = 100)
	public String getNomeContato() { return nomeContato; }
	public void setNomeContato(String nomeContato) { this.nomeContato = nomeContato; }

	@Column(nullable = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 50)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Column(nullable = true, length = 50)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }

	@Column(nullable = true, length = 50)
	public String getInscricaoEstadual() { return inscricaoEstadual; }
	public void setInscricaoEstadual(String inscricaoEstadual) { this.inscricaoEstadual = inscricaoEstadual; }

	@Column(nullable = true, length = 50)
	public String getInscricaoEstadualSubstTrib() { return inscricaoEstadualSubstTrib; }
	public void setInscricaoEstadualSubstTrib(String inscricaoEstadualSubstTrib) { 
													this.inscricaoEstadualSubstTrib = inscricaoEstadualSubstTrib; }

	@Column(nullable = true, length = 50)
	public String getInscricaoMunicipal() { return inscricaoMunicipal; }
	public void setInscricaoMunicipal(String inscricaoMunicipal) { this.inscricaoMunicipal = inscricaoMunicipal; }

	@Column(nullable = true, length = 50)
	public String getSuframa() { return suframa; }
	public void setSuframa(String suframa) { this.suframa = suframa; }

	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Column(nullable = true)
	public Boolean getIndCliente() { return indCliente; }
	public void setIndCliente(Boolean indCliente) { this.indCliente = indCliente; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 15)
	public MpSexo getMpSexo() { return mpSexo; 	}
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 20)
	public MpIndicadorIE getMpIndicadorIE() { return mpIndicadorIE; 	}
	public void setMpIndicadorIE(MpIndicadorIE mpIndicadorIE) { this.mpIndicadorIE = mpIndicadorIE; }

	@Column(nullable = true)
	public Boolean getIndFornecedor() { return indFornecedor; }
	public void setIndFornecedor(Boolean indFornecedor) { this.indFornecedor = indFornecedor; }

	@Column(nullable = true)
	public Boolean getIndTransportador() { return indTransportador; }
	public void setIndTransportador(Boolean indTransportador) { this.indTransportador = indTransportador; }
  	
	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) { this.mpEnderecoLocal = mpEnderecoLocal; }

	@Column(nullable = true)
	public Boolean getIndClienteFinal() { return indClienteFinal; }
	public void setIndClienteFinal(Boolean indClienteFinal) { this.indClienteFinal = indClienteFinal; }
		
	// ---
	
	@Enumerated(EnumType.STRING)
	@Column(name = "mpArquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }

	@ManyToOne
	@JoinColumn(name = "mpArquivoBD_id", nullable = true)
    public MpArquivoBD getMpArquivoBD() { return this.mpArquivoBD; }
    public void setMpArquivoBD(MpArquivoBD newMpArquivoBD) { this.mpArquivoBD = newMpArquivoBD; }  	
    
	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
    public byte[] getArquivoBD() { return this.arquivoBD; }
    public void setArquivoBD(byte[] newArquivoBD) { this.arquivoBD = newArquivoBD; }

}
