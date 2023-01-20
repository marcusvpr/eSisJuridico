package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
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

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpProfissaoPessoa;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;

@Entity
@Table(name = "mp_pessoa")
public class MpPessoa extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private String celular;
	private String telefone;
	private Date dataNascimento;
	private Date dataInicio;
	private String observacao;
	private BigDecimal salario;
	
	private MpEnderecoLocal mpEnderecoLocal;
	private MpStatus mpStatus;
	private MpSexo mpSexo;
	private MpTipoPessoa mpTipoPessoa;
	private MpProfissaoPessoa mpProfissaoPessoa;
	private MpTabelaInterna mpProfissao; // tab_0011
	private MpTurno mpTurno;
	
	private MpArquivoBD mpArquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	
	private byte[] arquivoBD;
		
	// ----------
		
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	@NotBlank(message = "Por favor, informe o E-MAIL")
	@Column(nullable = false, unique = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@Column(nullable = true, length = 100)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }
	
	@Column(nullable = true, length = 100) 
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicio", nullable = true)
	public Date getDataInicio() { return dataInicio; }
	public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
		
	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Column(name = "salario", nullable = true, precision = 10, scale = 2)
	public BigDecimal getSalario() { return this.salario; }
	public void setSalario(BigDecimal newSalario) { this.salario = newSalario; }
  	
	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) {
													this.mpEnderecoLocal = mpEnderecoLocal;	}
	 
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 20)
	public MpTipoPessoa getMpTipoPessoa() {	return mpTipoPessoa; }
	public void setMpTipoPessoa(MpTipoPessoa mpTipoPessoa) { this.mpTipoPessoa = mpTipoPessoa; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 20)
	public MpProfissaoPessoa getMpProfissaoPessoa() { return mpProfissaoPessoa; }
	public void setMpProfissaoPessoa(MpProfissaoPessoa mpProfissaoPessoa) {
													this.mpProfissaoPessoa = mpProfissaoPessoa;	}
	
	@ManyToOne
	@JoinColumn(name = "mpProfissao_id", nullable = true)
	public MpTabelaInterna getMpProfissao() { return mpProfissao; }
	public void setMpProfissao(MpTabelaInterna mpProfissao) { this.mpProfissao = mpProfissao;}

	@ManyToOne
	@JoinColumn(name = "mpTurno_id", nullable = true)
    public MpTurno getMpTurno() { return this.mpTurno; }
    public void setMpTurno(MpTurno newMpTurno) { this.mpTurno = newMpTurno; }  	

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
