package com.mpxds.mpbasic.model.adricred;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpEstadoCivil;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;

@Entity
@Table(name = "mpac_cliente_consignado")
public class MpClienteConsignado extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome; 
	
//	private MpTabelaInterna mpIndicacao; // TAB_1000
	private String discriminacao; 
	
	private String email;
	private String telefone;
//	private MpTabelaInterna mpTipoTelefone; // TAB_1010
	private String telefone1;
//	private MpTabelaInterna mpTipoTelefone1; // TAB_1010
	private String celular;
	private String cpf;
	
	private String identidade;
//	private MpTabelaInterna mpOrgaoEmissor; // TAB_1005
	private Date dataEmissao;
//	private MpEstadoUF mpEstadoUFIdent;
	private String tempoResidenciaAnos;
	private String tempoResidenciaMeses;
	private String nomeConjuge; 
	private String nomePai; 
	private String nomeMae; 
	private String cidadeNaturalidade; 
//	private MpEstadoUF mpEstadoUFNaturalidade;
	
	private String observacao;
	private Date dataNascimento;

	private MpTipoPessoa mpTipo;
	private MpStatus mpStatus;
	private MpSexo mpSexo;
	private MpEstadoCivil mpEstadoCivil;
	
//	private MpEnderecoLocal mpEnderecoLocal;

	private MpTabelaInterna mpBanco; // TAB_1015
//	private MpDadosBancario mpDadosBancario;

	private byte[] arquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
//	private List<MpDocumentoBD> mpDocumentos = new ArrayList<MpDocumentoBD>();
	
	// ---------------

	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Column(nullable = true, length = 100)
	public String getDiscriminacao() { return discriminacao; }
	public void setDiscriminacao(String discriminacao) { this.discriminacao = discriminacao; }

//	@ManyToOne
//	@JoinColumn(name = "mpClienteConsignado_indicacao_id", nullable = true)
//	public MpTabelaInterna getMpIndicacao() { return mpIndicacao; }
//	public void setMpIndicacao(MpTabelaInterna mpIndicacao) { this.mpIndicacao = mpIndicacao; }
	
	@NotBlank(message = "Por favor, informe o EMAIL")
	@Column(nullable = false, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 50)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

//	@ManyToOne
//	@JoinColumn(name = "mpClienteConsignado_telefone_id", nullable = true)
//	public MpTabelaInterna getMpTipoTelefone() { return mpTipoTelefone; }
//	public void setMpTipoTelefone(MpTabelaInterna mpTipoTelefone) { this.mpTipoTelefone = mpTipoTelefone; }

	@Column(nullable = true, length = 50)
	public String getTelefone1() { return telefone1; }
	public void setTelefone1(String telefone1) { this.telefone1 = telefone1; }

//	@ManyToOne
//	@JoinColumn(name = "mpClienteConsignado_telefone1_id", nullable = true)
//	public MpTabelaInterna getMpTipoTelefone1() { return mpTipoTelefone1; }
//	public void setMpTipoTelefone1(MpTabelaInterna mpTipoTelefone1) { this.mpTipoTelefone1 = mpTipoTelefone1; }

	@Column(nullable = true, length = 50)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }
	
	@CPF
	@Column(nullable = true, length = 15)
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }

//	@NotBlank(message = "Por favor, informe a IDENTIDADE")
	@Column(nullable = true, length = 100)
	public String getIdentidade() { return identidade; }
	public void setIdentidade(String identidade) { this.identidade = identidade; }

//	@ManyToOne
//	@JoinColumn(name = "mpClienteConsignado_emissor_id", nullable = true)
//	public MpTabelaInterna getMpOrgaoEmissor() { return mpOrgaoEmissor; }
//	public void setMpOrgaoEmissor(MpTabelaInterna mpOrgaoEmissor) { this.mpOrgaoEmissor = mpOrgaoEmissor; }

	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_emissao", nullable = true)
	public Date getDataEmissao() { return dataEmissao; }
	public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }

//	@Enumerated(EnumType.STRING)
//	@Column(nullable = true, length = 2)
//	public MpEstadoUF getMpEstadoUFIdent() { return mpEstadoUFIdent; }
//	public void setMpEstadoUFIdent(MpEstadoUF mpEstadoUFIdent) { this.mpEstadoUFIdent = mpEstadoUFIdent; }

//	@NotBlank(message = "Por favor, informe o TEMPO RESIDÊNCIA ANOS")
	@Column(nullable = true, length = 3)
	public String getTempoResidenciaAnos() { return tempoResidenciaAnos; }
	public void setTempoResidenciaAnos(String tempoResidenciaAnos) { this.tempoResidenciaAnos = tempoResidenciaAnos; }

//	@NotBlank(message = "Por favor, informe o TEMPO RESIDÊNCIA MESES")
	@Column(nullable = true, length = 3)
	public String getTempoResidenciaMeses() { return tempoResidenciaMeses; }
	public void setTempoResidenciaMeses(String tempoResidenciaMeses) { 
																	this.tempoResidenciaMeses = tempoResidenciaMeses; }

	@Column(nullable = true, length = 100)
	public String getNomeConjuge() { return nomeConjuge; }
	public void setNomeConjuge(String nomeConjuge) { this.nomeConjuge = nomeConjuge; }

	@Column(nullable = true, length = 100)
	public String getNomePai() { return nomePai; }
	public void setNomePai(String nomePai) { this.nomePai = nomePai; }

	@Column(nullable = true, length = 100)
	public String getNomeMae() { return nomeMae; }
	public void setNomeMae(String nomeMae) { this.nomeMae = nomeMae; }

	@Column(nullable = true, length = 100)
	public String getCidadeNaturalidade() { return cidadeNaturalidade; }
	public void setCidadeNaturalidade(String cidadeNaturalidade) { this.cidadeNaturalidade = cidadeNaturalidade; }

//	@Enumerated(EnumType.STRING)
//	@Column(nullable = true, length = 2)
//	public MpEstadoUF getMpEstadoUFNaturalidade() { return mpEstadoUFNaturalidade; }
//	public void setMpEstadoUFNaturalidade(MpEstadoUF mpEstadoUFNaturalidade) { 
//															this.mpEstadoUFNaturalidade = mpEstadoUFNaturalidade; }

	@Column(nullable = true, length = 1000)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
	
	@NotNull(message = "Por favor, informe o TIPO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 15)
	public MpTipoPessoa getMpTipo() { return mpTipo; }
	public void setMpTipo(MpTipoPessoa mpTipo) { this.mpTipo = mpTipo; }
	
	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	
	@NotNull(message = "Por favor, informe o SEXO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public MpSexo getMpSexo() { return mpSexo; 	}
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	
	@NotNull(message = "Por favor, informe o ESTADO CIVIL")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpestado_civil", nullable = false, length = 15)
	public MpEstadoCivil getMpEstadoCivil() { return mpEstadoCivil; }
	public void setMpEstadoCivil(MpEstadoCivil mpEstadoCivil) {	this.mpEstadoCivil = mpEstadoCivil; }
	
	@ManyToOne
	@JoinColumn(name = "mpClienteConsignado_banco_id", nullable = true)
	public MpTabelaInterna getMpBanco() { return mpBanco; }
	public void setMpBanco(MpTabelaInterna mpBanco) { this.mpBanco = mpBanco; }
	
//	@Embedded
//	public MpDadosBancario getMpDadosBancario() { return mpDadosBancario; }
//	public void setMpDadosBancario(MpDadosBancario mpDadosBancario) { this.mpDadosBancario = mpDadosBancario; }
//	
//	@Embedded
//	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
//	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) { this.mpEnderecoLocal = mpEnderecoLocal; }
	
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

//	@OneToMany(mappedBy = "mpClienteConsignado", cascade = CascadeType.ALL, orphanRemoval = true,
//																			fetch = FetchType.LAZY)
//	public List<MpDocumentoBD> getMpDocumentos() { return mpDocumentos; }
//	public void setMpDocumentos(List<MpDocumentoBD> mpDocumentos) { this.mpDocumentos = mpDocumentos; }

}
