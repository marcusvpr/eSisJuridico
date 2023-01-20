package com.mpxds.mpbasic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpStatusMensagem;
import com.mpxds.mpbasic.model.enums.MpTipoContato;

@Entity
@Table(name = "mp_mensagem_movimento")
public class MpMensagemMovimento extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpUsuario mpUsuario;
	private MpUsuarioTenant mpUsuarioTenant;
	
	private Date dataMovimento;
	private String usuarioNome;
	private String contatoNome;
	private String contatoDestino;
	private String assunto;
	private String mensagem;
	private MpTipoContato mpTipoContato;
	private MpStatusMensagem mpStatusMensagem = MpStatusMensagem.NOVA;

	private Date dataProgramada;
	private Boolean indRespostaUsuario;
	private String respostaUsuario;
	private String codigoRetorno;
	private MpStatusMensagem mpStatusMensagemUsuario = MpStatusMensagem.NOVA;;
	
	private String tipoAlerta; // Alarme/Calendário/Atividade/...
	private Long idTipoAlerta;
	
	// ---

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpUsuarioId")
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpUsuarioTenantId")
	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) { 
														this.mpUsuarioTenant = mpUsuarioTenant; }

	@NotNull(message = "Por favor, informe a DATA")
	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_movimento", nullable = false)
	public Date getDataMovimento() { return dataMovimento; }
	public void setDataMovimento(Date dataMovimento) { this.dataMovimento = dataMovimento; }

	@Column(nullable = false, length = 150)
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }

	@Column(nullable = false)
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }

	@Column(name = "usuario_nome", nullable = false, length = 150)
	public String getUsuarioNome() { return usuarioNome; }
	public void setUsuarioNome(String usuarioNome) {this.usuarioNome = usuarioNome; }

	@Column(name = "contato_nome", nullable = false, length = 150)
	public String getContatoNome() { return contatoNome; }
	public void setContatoNome(String contatoNome) {this.contatoNome = contatoNome; }

	@Column(name = "contato_destino", nullable = false, length = 150)
	public String getContatoDestino() { return contatoDestino; }
	public void setContatoDestino(String contatoDestino) {this.contatoDestino = contatoDestino; }
	
	@Enumerated(EnumType.STRING)
	@Column(name = "mpTipo_contato", nullable = false, length = 100)
	public MpTipoContato getMpTipoContato() { return mpTipoContato; }
	public void setMpTipoContato(MpTipoContato mpTipoContato) {	
															this.mpTipoContato = mpTipoContato; }
	
	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpStatus_mensagem", nullable = false, length = 30)
	public MpStatusMensagem getMpStatusMensagem() { return mpStatusMensagem; }
	public void setMpStatusMensagem(MpStatusMensagem mpStatusMensagem) {	
													this.mpStatusMensagem = mpStatusMensagem; }

	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_programada", nullable = true)
	public Date getDataProgramada() { return dataProgramada; }
	public void setDataProgramada(Date dataProgramada) { this.dataProgramada = dataProgramada; }

  	@Column(name = "ind_resposta_usuario", nullable = true)
	public Boolean getIndRespostaUsuario() { return indRespostaUsuario; }
	public void setIndRespostaUsuario(Boolean indRespostaUsuario) {
												this.indRespostaUsuario = indRespostaUsuario; }

  	@Column(name = "resposta_usuario", nullable = true, length = 30)
	public String getRespostaUsuario() { return respostaUsuario; }
	public void setRespostaUsuario(String respostaUsuario) { 
													this.respostaUsuario = respostaUsuario; }

  	@Column(name = "codigo_retorno", nullable = true, length = 50)
	public String getCodigoRetorno() { return codigoRetorno; }
	public void setCodigoRetorno(String codigoRetorno) { this.codigoRetorno = codigoRetorno; }

	@NotNull(message = "Por favor, informe o STATUS USUÁRIO")
	@Enumerated(EnumType.STRING)
	@Column(name = "mpStatus_mensagem_usuario", nullable = false, length = 30)
	public MpStatusMensagem getMpStatusMensagemUsuario() { return mpStatusMensagemUsuario; }
	public void setMpStatusMensagemUsuario(MpStatusMensagem mpStatusMensagemUsuario) {	
										this.mpStatusMensagemUsuario = mpStatusMensagemUsuario; }
	
	@Column(name = "tipo_alerta",nullable = false, length = 30)
	public String getTipoAlerta() { return tipoAlerta; }
	public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }
	
	@Column(name = "id_tipo_alerta", nullable = false)
	public Long getIdTipoAlerta() { return idTipoAlerta; }
	public void setIdTipoAlerta(Long idTipoAlerta) { this.idTipoAlerta = idTipoAlerta; }
	
}