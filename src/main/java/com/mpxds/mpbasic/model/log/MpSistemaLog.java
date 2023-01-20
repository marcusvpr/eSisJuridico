package com.mpxds.mpbasic.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Table(name = "mp_sistema_log")
public class MpSistemaLog extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataCriacao;
	private String codigo;
//	private Object objeto;
//	private Object objetoAnt;
	private String mensagem;

	public MpSistemaLog() {
		super();
	}

//	public MpSistemaLog(Date dataCriacao, String codigo, Object objeto,
//															Object objetoAnt, String mensagem) {
	public MpSistemaLog(Date dataCriacao, String codigo, String mensagem) {
		super();
		//
		this.dataCriacao = dataCriacao;
		this.codigo = codigo;
//		this.objeto = objeto;
//		this.objetoAnt = objetoAnt;
		this.mensagem = mensagem;
	}

	@Column(name = "data_criacao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}
	public void setDataCriacao(Date _dataCriacao) {
		this.dataCriacao = _dataCriacao;
	}

	@Column(nullable = false, length = 200)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String newCodigo) {
		this.codigo = newCodigo;
	}

//	@Column(nullable = false)
//	public Object getObjeto() {
//		return objeto;
//	}
//	public void setObjeto(Object newObjeto) {
//		this.objeto = newObjeto;
//	}
//
//	@Column(nullable = false)
//	public Object getObjetoAnt() {
//		return objetoAnt;
//	}
//	public void setObjetoAnt(Object newObjetoAnt) {
//		this.objetoAnt = newObjetoAnt;
//	}

	@Column(nullable = false, length = 250)
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String newMensagem) {
		this.mensagem = newMensagem;
	}

}
