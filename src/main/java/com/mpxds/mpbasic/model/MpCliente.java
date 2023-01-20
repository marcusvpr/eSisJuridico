package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

//import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
//import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.mpxds.mpbasic.model.enums.MpEstadoCivil;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;
import com.mpxds.mpbasic.validation.MpCodigo;

@Entity
@Table(name = "mp_cliente") //, uniqueConstraints = { @UniqueConstraint(columnNames = "codigo") })
public class MpCliente extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nome;
	private String email;
	private String telefone;
	private String celular;
	private Boolean parcelamentoLiberado;
	private String documentoReceitaFederal;
	private String cpf;
	private String observacao;
	private Date dataNascimento;

	private MpTipoPessoa mpTipo;
	private MpStatus mpStatus;
	private MpSexo mpSexo;
	private MpEstadoCivil mpEstadoCivil;
	
	private List<MpEndereco> mpEnderecos = new ArrayList<MpEndereco>();
	private List<MpDependente> mpDependentes = new ArrayList<MpDependente>();

	private byte[] arquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
	// ---------------

	@NotBlank(message = "Por favor, informe o CÓDIGO")
	@MpCodigo(message = "Por favor, informe um CÓDIGO no formato 9999")
	@Column(nullable = false, length = 4, unique = true)
	public String getCodigo() {	return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@NotBlank(message = "Por favor, informe o EMAIL")
	@Column(nullable = false, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 50)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Column(nullable = true, length = 50)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }

	@Column(nullable = true)
	public Boolean getParcelamentoLiberado() { return parcelamentoLiberado; }
	public void setParcelamentoLiberado(Boolean parcelamentoLiberado) {
											this.parcelamentoLiberado = parcelamentoLiberado; }
	
	@NotBlank(message = "Por favor, informe o DOCUMENTO")
	@Column(name = "doc_receita_federal", nullable = false, length = 14)
	public String getDocumentoReceitaFederal() { return documentoReceitaFederal; }
	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
									this.documentoReceitaFederal = documentoReceitaFederal;	}
	
	@CPF
	@Column(nullable = true, length = 15)
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }

	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	//@NotNull
	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
	
	@NotNull(message = "Por favor, informe o TIPO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
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
	
	@OneToMany(mappedBy = "mpCliente", cascade = CascadeType.ALL)
	public List<MpEndereco> getMpEnderecos() { return mpEnderecos; }
	public void setMpEnderecos(List<MpEndereco> mpEnderecos) { this.mpEnderecos = mpEnderecos; }

	@OneToMany(mappedBy = "mpCliente", cascade = CascadeType.ALL)
	public List<MpDependente> getMpDependentes() { return mpDependentes; }
	public void setMpDependentes(List<MpDependente> mpDependentes) {
															this.mpDependentes = mpDependentes; }

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
