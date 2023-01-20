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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpGrupoTenant;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_usuario")
public class MpUsuario extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String loginGrupo;
	private String nome;
	private String email;
	private String senha;
	private String senhaLog = "";
	private Date dataNascimento;
	private String celular;
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
	
	private MpStatus mpStatus;
	private MpSexo mpSexo;
	private MpGrupoTenant mpGrupoTenant;
	private MpAlerta mpAlerta;
	
	private List<MpGrupo> mpGrupos = new ArrayList<MpGrupo>();
	
	private MpArquivoBD mpArquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private byte[] arquivoBD;
	
	// ----------
	
	@NotBlank(message = "Por favor, informe o LOGIN")
	@Column(nullable = false, length = 15, unique = true)
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	
	@Column(nullable = true, length = 50)
	public String getLoginGrupo() { return loginGrupo; }
	public void setLoginGrupo(String loginGrupo) { this.loginGrupo = loginGrupo; }
	
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	@NotBlank(message = "Por favor, informe o E-MAIL")
	@Column(nullable = false, unique = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@NotBlank(message = "Por favor, informe a SENHA")
	@Column(nullable = false, length = 20)
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }

	// Guarda últimas 5 (cinco) senhas !
	@Column(nullable = true, length = 200, name = "senha_log")
	public String getSenhaLog() { return senhaLog; }
	public void setSenhaLog(String senhaLog) { this.senhaLog = senhaLog; }

	@Past(message="Data futuro inválida!")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

	@Column(nullable = true, length = 50)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }

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

	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 30)
	public MpGrupoTenant getMpGrupoTenant() { return mpGrupoTenant; }
	public void setMpGrupoTenant(MpGrupoTenant mpGrupoTenant) { 
															this.mpGrupoTenant = mpGrupoTenant; }

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
		
	@ManyToMany(cascade = CascadeType.ALL) // , fetch = FetchType.EAGER)
	@JoinTable(name = "mp_usuario_grupo", joinColumns = @JoinColumn(name="mpUsuario_id"),
									inverseJoinColumns = @JoinColumn(name = "mpGrupo_id"))
	public List<MpGrupo> getMpGrupos() { return mpGrupos; }
	public void setMpGrupos(List<MpGrupo> mpGrupos) { this.mpGrupos = mpGrupos; }

	@Enumerated(EnumType.STRING)
	@Column(name = "mpArquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { 
															this.mpArquivoAcao = mpArquivoAcao; }

	@ManyToOne
	@JoinColumn(name = "mpArquivoBD_id", nullable = true)
    public MpArquivoBD getMpArquivoBD() { return this.mpArquivoBD; }
    public void setMpArquivoBD(MpArquivoBD newMpArquivoBD) { this.mpArquivoBD = newMpArquivoBD; }  	
    
	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
    public byte[] getArquivoBD() { return this.arquivoBD; }
    public void setArquivoBD(byte[] newArquivoBD) { this.arquivoBD = newArquivoBD; }
    
    //
    
	@Transient
	public String getGruposNome() {
		String gruposNome = "";
		
		for (MpGrupo mpGrupoX : this.mpGrupos) {
			gruposNome = gruposNome + " " + mpGrupoX.getNome(); 
		}
		//
		return gruposNome;
	}
	
}