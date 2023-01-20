package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class MpAtividadeFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private String paciente;
	private String descricao;
	private String atividadeTipo;
	private String produto;
	private Boolean indAtivo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Date getDataDe() { return dataDe; }
	public void setDataDe(Date dataDe) { this.dataDe = dataDe; }
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }
	
	public String getPaciente() { return paciente; }
	public void setPaciente(String paciente) { this.paciente = paciente; }
	
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public String getAtividadeTipo() { return atividadeTipo; }
	public void setAtividadeTipo(String atividadeTipo) { this.atividadeTipo = atividadeTipo; }

	public String getProduto() { return produto; }
	public void setProduto(String produto) { this.produto = produto; }

	public Boolean getIndAtivo() { return indAtivo; }
	public void setIndAtivo(Boolean indAtivo) { this.indAtivo = indAtivo; }

	// ----
	
	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}