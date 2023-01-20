package com.mpxds.mpbasic.model;

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
import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpChamadoTipo;
import com.mpxds.mpbasic.model.enums.MpChamadoAreaTipo;
import com.mpxds.mpbasic.model.enums.MpChamadoSeveridade;
import com.mpxds.mpbasic.model.enums.MpChamadoStatus;

@Entity
@Table(name="mp_chamado")
public class MpChamado extends MpBaseEntity {
	private static final long serialVersionUID = 1L;

	private Date dtHrChamado;  
	private String areaOutro;
	private String descricao;
	private String obs;
	private String solucao;
	private String codigoPsw;
	private byte[] arquivoBD;
	private String arquivoTipoBD;
	private String historico;

	private MpChamadoTipo mpChamadoTipo;
	private MpChamadoAreaTipo mpChamadoAreaTipo;
	private MpChamadoSeveridade mpChamadoSeveridade;
	private MpChamadoStatus mpChamadoStatus;
	private MpUsuario mpUsuario;	

	// ---
	
	public MpChamado() {
		super();
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "mpChamado_tipo", nullable = true, length = 30)
  	public MpChamadoTipo getMpChamadoTipo() {
  		return this.mpChamadoTipo;
  	}
  	public void setMpChamadoTipo(MpChamadoTipo newMpChamadoTipo) {
  		this.mpChamadoTipo = newMpChamadoTipo;
  	}

	@Enumerated(EnumType.STRING)
	@Column(name = "mpChamado_area_tipo", nullable = true, length = 30)
  	public MpChamadoAreaTipo getMpChamadoAreaTipo() {
  		return this.mpChamadoAreaTipo;
  	}
  	public void setMpChamadoAreaTipo(MpChamadoAreaTipo newMpChamadoAreaTipo) {
  		this.mpChamadoAreaTipo = newMpChamadoAreaTipo;
  	}

	@Enumerated(EnumType.STRING)
	@Column(name = "mpChamado_severidade", nullable = true, length = 30)
  	public MpChamadoSeveridade getMpChamadoSeveridade() {
  		return this.mpChamadoSeveridade;
  	}
  	public void setMpChamadoSeveridade(MpChamadoSeveridade newMpChamadoSeveridade) {
  		this.mpChamadoSeveridade = newMpChamadoSeveridade;
  	}

	@Enumerated(EnumType.STRING)
	@Column(name = "mpChamado_status", nullable = true, length = 30)
  	public MpChamadoStatus getMpChamadoStatus() {
  		return this.mpChamadoStatus;
  	}
  	public void setMpChamadoStatus(MpChamadoStatus newMpChamadoStatus) {
  		this.mpChamadoStatus = newMpChamadoStatus;
  	}
  	
	@NotBlank
	@Column(nullable = false, length = 10000)
	public String getDescricao() {
	    return this.descricao;
	}
	public void setDescricao(String newDescricao) {
		this.descricao = newDescricao;
	}
	   	
	@Column(name = "area_outro", nullable = true, length = 150)
	public String getAreaOutro() {
	    return this.areaOutro;
	}
	public void setAreaOutro(String newAreaOutro) {
		this.areaOutro = newAreaOutro;
	}
  	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpUsuarioId")
	public MpUsuario getMpUsuario() {
	    return this.mpUsuario;
	}
	public void setMpUsuario(MpUsuario newMpUsuario) {
		this.mpUsuario = newMpUsuario;
	}
	   	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dthr_chamado", nullable = false)
	public Date getDtHrChamado() {
	    return this.dtHrChamado;
	}
	public void setDtHrChamado(Date newDtHrChamado) {
		this.dtHrChamado = newDtHrChamado;
	}
  	
	@Column(nullable = true, length = 255)
	public String getObs() {
	    return this.obs;
	}
	public void setObs(String newObs) {
		this.obs = newObs;
	}
    
	@Column(nullable = true, length = 10000)
	public String getSolucao() {
	    return this.solucao;
	}
	public void setSolucao(String newSolucao) {
		this.solucao = newSolucao;
	}
    
	@Column(name = "codigo_psw", nullable = true, length = 30)
	public String getCodigoPsw() {
	    return this.codigoPsw;
	}
	public void setCodigoPsw(String newCodigoPsw) {
		this.codigoPsw = newCodigoPsw;
	}
    
	@Lob
	@Column(name = "arquivo_bd")
	public byte[] getArquivoBD() {
		return this.arquivoBD;
	}
	public void setArquivoBD(byte[] arquivoBD) {
	    this.arquivoBD = arquivoBD;
	}  	
	
	@Column(name = "arquivo_tipo_bd", nullable = true, length = 30)
	public String getArquivoTipoBD() {
		return this.arquivoTipoBD;
	}
	public void setArquivoTipoBD(String arquivoTipoBD) {
	    this.arquivoTipoBD = arquivoTipoBD;
	}  	
	
	@Column(nullable = true, length = 10000)
	public String getHistorico() {
	    return this.historico;
	}
	public void setHistorico(String newHistorico) {
		this.historico = newHistorico;
	}
  
}
