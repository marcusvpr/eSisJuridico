package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpArquivoTipo;

@Entity
@Table(name="mp_receita")
public class MpReceita extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataReceita;  
	private String descricao;
	private String obs;

	private MpPaciente mpPaciente;
	private MpPessoa mpPessoa;
	
	private byte[] arquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoTipo mpArquivoTipo;
	private MpArquivoBD mpArquivoBD;
	
	private List<MpItemReceita> mpItemReceitas = new ArrayList<MpItemReceita>();
	
	// ---
	
	public MpReceita() {
		super();
	}
  	   	
	@NotNull(message = "Por favor, informe a Data")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_receita", nullable = false, length = 10)
	public Date getDataReceita() { return this.dataReceita; }
	public void setDataReceita(Date newDataReceita) { this.dataReceita = newDataReceita; }

	@NotBlank(message = "Por favor, informe a Descrição")
	@Column(nullable = false, length = 255)
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }
	   	  	
	@Column(nullable = true, length = 200)
	public String getObs() { return this.obs; }
	public void setObs(String newObs) { this.obs = newObs; }

	@NotNull(message = "Por favor, informe o Paciente")
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpPacienteId", nullable = false)
	public MpPaciente getMpPaciente() { return this.mpPaciente; }
	public void setMpPaciente(MpPaciente newMpPaciente) { this.mpPaciente = newMpPaciente; }

	@NotNull(message = "Por favor, informe o Responsável")
	@ManyToOne
	@JoinColumn(name = "mpPessoa_id", nullable = false)
	public MpPessoa getMpPessoa() {	return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
	
	@Lob
	@Column(name = "arquivo_bd")
	public byte[] getArquivoBD() { return this.arquivoBD; }
	public void setArquivoBD(byte[] arquivoBD) { this.arquivoBD = arquivoBD; }  	
	
	@Column(name = "mpArquivo_acao", nullable = true, length = 20)
	public MpArquivoAcao getMpArquivoAcao() { return this.mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	
	@Column(name = "mpArquivo_tipo", nullable = true, length = 20)
	public MpArquivoTipo getMpArquivoTipo() { return this.mpArquivoTipo; }
	public void setMpArquivoTipo(MpArquivoTipo mpArquivoTipo) { this.mpArquivoTipo = mpArquivoTipo; }
	
	@ManyToOne
	@JoinColumn(name = "mpArquivoBD_id", nullable = true)
    public MpArquivoBD getMpArquivoBD() { return this.mpArquivoBD; }
    public void setMpArquivoBD(MpArquivoBD newMpArquivoBD) { this.mpArquivoBD = newMpArquivoBD; }  	
    
	@OneToMany(mappedBy = "mpReceita", cascade = CascadeType.ALL, orphanRemoval = true,
																		fetch = FetchType.LAZY)
	public List<MpItemReceita> getMpItemReceitas() { return mpItemReceitas; }
	public void setMpItemReceitas(List<MpItemReceita> mpItemReceitas) { 
															this.mpItemReceitas = mpItemReceitas; }
	
}
