package com.mpxds.mpbasic.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Table(name="mp_movimento_login") 
public class MpMovimentoLogin extends MpBaseEntity {
  private static final long serialVersionUID = 1L;

  private String atividade;
  private Date dtHrMovimento;
  private String numeroIP;
  private String coligada; // SistemaConfig = 00-Matriz/01-EmpresaFilial01 ...

  private String usuarioLogin;
  private String usuarioEmail;
  
  // -------------
  
  public MpMovimentoLogin() {
	  super();
  }
  
  @Column(nullable = false, length = 50)
  public String getAtividade() { return this.atividade; }
  public void setAtividade(String newAtividade) { this.atividade = newAtividade; }
  
  @Column(nullable = false, length = 100)
  public String getUsuarioLogin() { return this.usuarioLogin; }
  public void setUsuarioLogin(String newUsuarioLogin) { this.usuarioLogin = newUsuarioLogin; }
  
  @Column(nullable = false, length = 100)
  public String getUsuarioEmail() { return this.usuarioEmail; }
  public void setUsuarioEmail(String newUsuarioEmail) { this.usuarioEmail = newUsuarioEmail; }
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "dthr_movimento")
  public Date getDtHrMovimento() { return this.dtHrMovimento; }
  public void setDtHrMovimento(Date newDtHrMovimento) { this.dtHrMovimento = newDtHrMovimento; }
  
  @Column(nullable = false, length = 20, name = "numero_ip")
  public String getNumeroIP() { return this.numeroIP; }
  public void setNumeroIP(String newNumeroIP) { this.numeroIP = newNumeroIP; }
  
  @Column(nullable = false, length = 2)
  public String getColigada() { return this.coligada; }
  public void setColigada(String newColigada) { this.coligada = newColigada; }
  
}