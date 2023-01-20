package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.mpxds.mpbasic.model.enums.MpStatusPedido;

public class MpPedidoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeVendedor;
	private String nomeCliente;
	
	private MpStatusPedido[] mpStatuses;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public Long getNumeroDe() { return numeroDe; }
	public void setNumeroDe(Long numeroDe) { this.numeroDe = numeroDe; }

	public Long getNumeroAte() { return numeroAte; }
	public void setNumeroAte(Long numeroAte) { this.numeroAte = numeroAte; }

	public Date getDataCriacaoDe() { return dataCriacaoDe; }
	public void setDataCriacaoDe(Date dataCriacaoDe) { this.dataCriacaoDe = dataCriacaoDe; }
	
	public Date getDataCriacaoAte() { return dataCriacaoAte; }
	public void setDataCriacaoAte(Date dataCriacaoAte) { this.dataCriacaoAte = dataCriacaoAte; }

	public String getNomeVendedor() { return nomeVendedor; }
	public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }

	public String getNomeCliente() { return nomeCliente; }
	public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

	public MpStatusPedido[] getMpStatuses() { return mpStatuses; }
	public void setMpStatuses(MpStatusPedido[] mpStatuses) { this.mpStatuses = mpStatuses; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}