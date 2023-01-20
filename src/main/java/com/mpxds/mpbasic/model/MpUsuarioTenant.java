package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpGrupoTenant;
import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_usuario_tenant")
public class MpUsuarioTenant extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String loginGrupo;
	private String senha;
	private String senhaLog = "";
	private String observacao;
	
	// Preferências Usuário ...
	private Boolean indMenuTop = true;
	private Boolean indMenuLeft = true;
	private Boolean indBarraNavegacao = true;
	private Boolean indRodapeSistema = true;
	private Boolean indCapturaFoto = true;
	private Boolean indTenant = false;
	private Boolean indLabelCampo = false;

	private String numIpUltimoLogin;
	private Date dataUltimoLogin;
	private Date dataUltimoLoginAnt;  
	
	private MpPessoa mpPessoa;
	private MpStatus mpStatus;
	private MpGrupoTenant mpGrupoTenant;
	private MpAlerta mpAlerta;
	
	private List<MpTenantUsuario> mpTenantUsuarioList = new ArrayList<MpTenantUsuario>();
	
	// ----------
	
	@NotBlank(message = "Por favor, informe o LOGIN")
	@Column(nullable = false, length = 15, unique = true)
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	
	@Column(nullable = true, length = 50)
	public String getLoginGrupo() { return loginGrupo; }
	public void setLoginGrupo(String loginGrupo) { this.loginGrupo = loginGrupo; }
		
	@NotBlank(message = "Por favor, informe a SENHA")
	@Column(nullable = false, length = 20)
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }

	// Guarda últimas 5 (cinco) senhas !
	@Column(nullable = true, length = 200, name = "senha_log")
	public String getSenhaLog() { return senhaLog; }
	public void setSenhaLog(String senhaLog) { this.senhaLog = senhaLog; }

	@Column(nullable = true, length = 255)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }
  
	// ---
	
	@Column(name = "ind_menu_top", nullable = false)
	public Boolean getIndMenuTop() { return indMenuTop; }
	public void setIndMenuTop(Boolean indMenuTop) { this.indMenuTop = indMenuTop; }
	  
	@Column(name = "ind_menu_left", nullable = false)
	public Boolean getIndMenuLeft() { return indMenuLeft; }
	public void setIndMenuLeft(Boolean indMenuLeft) { this.indMenuLeft = indMenuLeft; }
	  
	@Column(name = "ind_barra_navegacao", nullable = false)
	public Boolean getIndBarraNavegacao() { return indBarraNavegacao; }
	public void setIndBarraNavegacao(Boolean indBarraNavegacao) { 
													this.indBarraNavegacao = indBarraNavegacao; }
	  
	@Column(name = "ind_rodape_sistema", nullable = false)
	public Boolean getIndRodapeSistema() { return indRodapeSistema; }
	public void setIndRodapeSistema(Boolean indRodapeSistema) { 
													this.indRodapeSistema = indRodapeSistema; }
	  
	@Column(name = "ind_captura_foto", nullable = false)
	public Boolean getIndCapturaFoto() { return indCapturaFoto; }
	public void setIndCapturaFoto(Boolean indCapturaFoto) { 
														this.indCapturaFoto = indCapturaFoto; }
	  
	@Column(name = "ind_tenant", nullable = false)
	public Boolean getIndTenant() { return indTenant; }
	public void setIndTenant(Boolean indTenant) { this.indTenant = indTenant; }
	  
	@Column(name = "ind_label_campo", nullable = false)
	public Boolean getIndLabelCampo() { return indLabelCampo; }
	public void setIndLabelCampo(Boolean indLabelCampo) { this.indLabelCampo = indLabelCampo; }

	// ---
	  
	@Column(nullable = true, name = "num_ip_ultimo_login")
	public String getNumIpUltimoLogin() { return this.numIpUltimoLogin; }
	public void setNumIpUltimoLogin(String newNumIpUltimoLogin) {
													this.numIpUltimoLogin = newNumIpUltimoLogin; }
	  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "data_ultimo_login")
	public Date getDataUltimoLogin() { return this.dataUltimoLogin; }
	public void setDataUltimoLogin(Date newDataUltimoLogin) {
													this.dataUltimoLogin = newDataUltimoLogin; }
	  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "data_ultimo_login_ant")
	public Date getDataUltimoLoginAnt() { return this.dataUltimoLoginAnt; }
	public void setDataUltimoLoginAnt(Date newDataUltimoLoginAnt) {
												this.dataUltimoLoginAnt = newDataUltimoLoginAnt; }

	@NotNull(message = "Por favor, informe a PESSOA")
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpPessoaId")
	public MpPessoa getMpPessoa() { return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
	
	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
		
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 30)
	public MpGrupoTenant getMpGrupoTenant() { return mpGrupoTenant; }
	public void setMpGrupoTenant(MpGrupoTenant mpGrupoTenant) { this.mpGrupoTenant = mpGrupoTenant; }
	
	@OneToMany(mappedBy = "mpUsuarioTenant", cascade = CascadeType.ALL, orphanRemoval = true,
																			fetch = FetchType.LAZY)
	public List<MpTenantUsuario> getMpTenantUsuarioList() { return mpTenantUsuarioList; }
	public void setMpTenantUsuarioList(List<MpTenantUsuario> mpTenantUsuarioList) {
												this.mpTenantUsuarioList = mpTenantUsuarioList; }
	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAlertaId")
	public MpAlerta getMpAlerta() {
		//
		if (null == this.mpAlerta)  
			this.mpAlerta = new MpAlerta(false, false, false, false, false, false, false);
		//
		return this.mpAlerta; 
	}
	public void setMpAlerta(MpAlerta mpAlerta) { this.mpAlerta = mpAlerta; }
	
}