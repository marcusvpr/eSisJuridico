package com.mpxds.mpbasic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.model.enums.MpTipoPrioridade;

@Entity
@Table(name = "mp_notificacao")
public class MpNotificacao extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private String mensagem;
	
	private MpStatusNotificacao mpStatusNotificacao;
	private MpTipoPrioridade mpTipoPrioridade;
	private MpAlerta mpAlerta;
		
	// ---------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_de", nullable = false)
	public Date getDataDe() { return dataDe; }
	public void setDataDe(Date dataDe) { this.dataDe = dataDe; }

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ate", nullable = false)
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }

	@Column(nullable = false, length = 250)
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }

	@Column(name = "mp_status_notificacao", nullable = false)
	public MpStatusNotificacao getMpStatusNotificacao() { return mpStatusNotificacao; }
	public void setMpStatusNotificacao(MpStatusNotificacao mpStatusNotificacao) {
												this.mpStatusNotificacao = mpStatusNotificacao;	}

	@Column(name = "mp_tipo_prioridade", nullable = false)
	public MpTipoPrioridade getMpTipoPrioridade() { return mpTipoPrioridade; }
	public void setMpTipoPrioridade(MpTipoPrioridade mpTipoPrioridade) {
													this.mpTipoPrioridade = mpTipoPrioridade; }
	
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
	
}