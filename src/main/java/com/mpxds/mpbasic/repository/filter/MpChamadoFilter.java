package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpChamadoFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private String descricao;
	private String tipo;
	private String areaTipo;
	private String areaOutro;
	private String severidade;
	private String status;
	private String usuario;
	private String solucao;
	private String historico;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataDe() { return dataDe; }
	public void setDataDe(Date dataDe) { this.dataDe = dataDe; }
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }

	public String getAreaTipo() { return areaTipo; }
	public void setAreaTipo(String areaTipo) { this.areaTipo = areaTipo; }

	public String getAreaOutro() { return areaOutro; }
	public void setAreaOutro(String areaOutro) { this.areaOutro = areaOutro; }

	public String getSeveridade() { return severidade; }
	public void setSeveridade(String severidade) { this.severidade = severidade; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }

	public String getSolucao() { return solucao; }
	public void setSolucao(String solucao) { this.solucao = solucao; }

	public String getHistorico() { return historico; }
	public void setHistorico(String historico) { this.historico = historico; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}