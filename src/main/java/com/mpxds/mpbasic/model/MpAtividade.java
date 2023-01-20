package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoTipo;
import com.mpxds.mpbasic.model.enums.MpAtividadeTipo;
import com.mpxds.mpbasic.model.enums.MpPeriodicidade;

@Entity
@Table(name="mp_atividade")
public class MpAtividade extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dtHrAtividade;  
	private Date dtHrFimAtividade;  
	private Integer duracao; // Em minutos!  
	private String descricao;
	private BigDecimal quantidade;
	private String observacao;

	private Boolean indPaciente;
	private Boolean indInicioFim;

	private byte[] arquivoBD;
	private MpArquivoTipo mpArquivoTipo;
	
	private MpPaciente mpPaciente;
	private MpPeriodicidade mpPeriodicidade;
	private MpAtividadeTipo mpAtividadeTipo;
	private MpProduto mpProduto;
	private MpAlerta mpAlerta;

	// ---
	
	public MpAtividade() {
		super();
	}
   	
	@NotNull(message = "Por favor, informe a DATA/HORA")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dthr_atividade", nullable = false)
	public Date getDtHrAtividade() { return this.dtHrAtividade; }
	public void setDtHrAtividade(Date newDtHrAtividade) { 
													this.dtHrAtividade = newDtHrAtividade; }

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dthr_fim_atividade", nullable = true)
	public Date getDtHrFimAtividade() { return this.dtHrFimAtividade; }
	public void setDtHrFimAtividade(Date newDtHrFimAtividade) {
												this.dtHrFimAtividade = newDtHrFimAtividade; }

	@NotNull(message = "Por favor, informe a DURAÇÃO")
	@Column(nullable = false)
	public Integer getDuracao() { return duracao; }
	public void setDuracao(Integer duracao) { this.duracao = duracao; }

	@NotBlank(message = "Por favor, informe a DESCRIÇÃO")
	@Column(nullable = false, length = 255)
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	@NotNull(message = "Por favor, informe a QUANTIDADE")
	@Column(nullable = false, precision = 6, scale = 2)
	public BigDecimal getQuantidade() { return quantidade; }
	public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
	   	  	
	@Column(nullable = true, length = 200)
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String newObservacao) { this.observacao = newObservacao; }
	
	@Column(name = "ind_paciente", nullable = true)
	public Boolean getIndPaciente() { return this.indPaciente; }
	public void setIndPaciente(Boolean newIndPaciente) { this.indPaciente = newIndPaciente; }
	
	@Column(name = "ind_inicio_fim", nullable = true)
	public Boolean getIndInicioFim() { return this.indInicioFim; }
	public void setIndInicioFim(Boolean newIndInicioFim) { this.indInicioFim = newIndInicioFim; }
	
	@Lob
	@Column(name = "arquivo_bd")
	public byte[] getArquivoBD() { return this.arquivoBD; }
	public void setArquivoBD(byte[] arquivoBD) { this.arquivoBD = arquivoBD; }  	
	
	@Column(name = "mpArquivo_tipo", nullable = true, length = 20)
	public MpArquivoTipo getMpArquivoTipo() { return this.mpArquivoTipo; }
	public void setMpArquivoTipo(MpArquivoTipo mpArquivoTipo) {
	    													this.mpArquivoTipo = mpArquivoTipo; }  	

	@NotNull(message = "Por favor, informe a PERIODICIDADE")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpPeriodicidade", nullable = false, length = 20)
  	public MpPeriodicidade getMpPeriodicidade() { return this.mpPeriodicidade; }
  	public void setMpPeriodicidade(MpPeriodicidade newMpPeriodicidade) {
  													this.mpPeriodicidade = newMpPeriodicidade; }
	  	
	@NotNull(message = "Por favor, informe o Tipo Atividade")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpAtividade_tipo", nullable = false, length = 20)
  	public MpAtividadeTipo getMpAtividadeTipo() { return this.mpAtividadeTipo; }
  	public void setMpAtividadeTipo(MpAtividadeTipo newMpAtividadeTipo) {
  												this.mpAtividadeTipo = newMpAtividadeTipo; }
	  	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpProdutoId")
	public MpProduto getMpProduto() { return this.mpProduto; }
	public void setMpProduto(MpProduto newMpProduto) { this.mpProduto = newMpProduto; }
  	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpPacienteId")
	public MpPaciente getMpPaciente() { return this.mpPaciente; }
	public void setMpPaciente(MpPaciente newMpPaciente) { this.mpPaciente = newMpPaciente; }
	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAlertaId")
	public MpAlerta getMpAlerta() {
		//
		if (null == this.mpAlerta)  
			this.mpAlerta = new MpAlerta(false, false, false, false, false, false, false);
		//
		return this.mpAlerta; 
	}
	public void setMpAlerta(MpAlerta newMpAlerta) { this.mpAlerta = newMpAlerta; }
	
	// ---
	
	@Transient
	public String getDescricaoCompleta() {
		//
		if (null==this.duracao) this.duracao = 0;
		if (null==this.quantidade) this.quantidade = BigDecimal.ZERO;
		if (null==this.observacao) this.observacao = "";
		//
		String descricaoCompleta = this.descricao.trim();
		
		if (this.duracao > 0)
			descricaoCompleta = descricaoCompleta + " (Duração = " + this.duracao + " Min.)";
		if (this.indInicioFim)
			descricaoCompleta = descricaoCompleta + " (Ind.Ini-Fim = S)";
		if (null == this.getMpPeriodicidade())
			assert(true); // nop
		else
			descricaoCompleta = descricaoCompleta + " (Periodic. = " + 
												this.getMpPeriodicidade().getDescricao() + ")";
		if (null == this.getMpPeriodicidade())
			assert(true); // nop
		else
			descricaoCompleta = descricaoCompleta + " (Ativ. = " + 
											this.getMpAtividadeTipo().getDescricao().trim() + ")";
		if (null == this.mpProduto)
			assert(true); // nop
		else
			descricaoCompleta = descricaoCompleta + " (Produto = " + 
														this.mpProduto.getNome().trim()	+ ")";
		if (this.quantidade.doubleValue() > 0.0)
			descricaoCompleta = descricaoCompleta + " (Qtd. = " + this.quantidade + ")";
		if (null == this.mpPaciente)
			assert(true); // nop
		else
			descricaoCompleta = descricaoCompleta + " (Paciente = " + 
														this.mpPaciente.getNome().trim() + ")";
		if (!this.observacao.isEmpty())
			descricaoCompleta = descricaoCompleta + " (Obs. = " + this.observacao.trim() + ")";
		//
		if (null == this.mpAlerta)
			assert(true); // nop
		else
			if (!this.mpAlerta.getConfiguracao().isEmpty())
				descricaoCompleta = descricaoCompleta + " (Alerta = " + 
														this.mpAlerta.getConfiguracao() + ")";
		//
	    return descricaoCompleta;
	}
      
}
