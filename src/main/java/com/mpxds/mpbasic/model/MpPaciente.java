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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpCor;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_paciente")
public class MpPaciente extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Date dataNascimento;
	private String email;
	private String telefone;
	private String observacao;
	private String informacao;
	
	private MpCor mpCor;
	private MpSexo mpSexo;
	private MpStatus mpStatus;

	private List<MpPessoaPaciente> mpPessoaPacientes = new ArrayList<MpPessoaPaciente>();
	
	private MpArquivoBD mpArquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private byte[] arquivoBD;
	
	// ----------
	
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 80)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
	
	@Column(nullable = true, length = 150)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@Column(nullable = true, length = 20)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Column(nullable = true, length = 255)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Column(nullable = true, length = 255)
	public String getInformacao() { return informacao; }
	public void setInformacao(String informacao) { this.informacao = informacao; }
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 20)
	public MpCor getMpCor() { return mpCor; }
	public void setMpCor(MpCor mpCor) { this.mpCor = mpCor; }
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	  
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }

	@OneToMany(mappedBy = "mpPaciente", cascade = CascadeType.ALL)
	public List<MpPessoaPaciente> getMpPessoaPacientes() { return mpPessoaPacientes; }
	public void setMpPessoaPacientes(List<MpPessoaPaciente> mpPessoaPacientes) {
														this.mpPessoaPacientes = mpPessoaPacientes; }

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