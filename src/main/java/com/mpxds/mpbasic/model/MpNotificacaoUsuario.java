package com.mpxds.mpbasic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.model.enums.MpTipoPrioridade;

@Entity
@Table(name = "mp_notificacao_usuario")
public class MpNotificacaoUsuario extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataEnvio;
	private String assunto;
	private String mensagem;

	private Boolean indLeitura = false;
	private Boolean indVisualiza = false;
	
	private String nomeUsuarioFrom;
	private String nomeUsuario;
	
	private MpUsuario mpUsuario;
	private MpUsuarioTenant mpUsuarioTenant;
	private MpStatusNotificacao mpStatusNotificacao;
	private MpTipoPrioridade mpTipoPrioridade;
	private MpAlerta mpAlerta;

	// ---

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_envio", nullable = false)
	public Date getDataEnvio() { return dataEnvio; }
	public void setDataEnvio(Date dataEnvio) { this.dataEnvio = dataEnvio; }

	@Column(nullable = false, length = 50)
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }

	@Column(nullable = false, length = 250)
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }
	
	@Column(name = "ind_leitura", nullable = true)
	public Boolean getIndLeitura() { return this.indLeitura; }
	public void setIndLeitura(Boolean newIndLeitura) { this.indLeitura = newIndLeitura; }
	
	@Column(name = "ind_visualiza", nullable = true)
	public Boolean getIndVisualiza() { return this.indVisualiza; }
	public void setIndVisualiza(Boolean newIndVisualiza) { 
														this.indVisualiza = newIndVisualiza; }

	@Column(name = "nome_usuario_from", nullable = false, length = 100)
	public String getNomeUsuarioFrom() { return nomeUsuarioFrom; }
	public void setNomeUsuarioFrom(String nomeUsuarioFrom) { 
													this.nomeUsuarioFrom = nomeUsuarioFrom; }

	@Column(name = "nome_usuario", nullable = false, length = 100)
	public String getNomeUsuario() { return nomeUsuario; }
	public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
	
	@ManyToOne
	@JoinColumn(name = "mpUsuario_id", nullable = true)
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }
	
	@ManyToOne
	@JoinColumn(name = "mpUsuario_tenant_id", nullable = true)
	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) { 
													this.mpUsuarioTenant = mpUsuarioTenant; }

	@Column(name = "mpStatus_notificacao", nullable=false)
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
