package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

public class MpAlarmeFilter implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean indDomingo;
	private Boolean indSegunda;
	private Boolean indTerca;
	private Boolean indQuarta;
	private Boolean indQuinta;
	private Boolean indSexta;
	private Boolean indSabado;
	private Boolean indSemanalmente;
	private Boolean indAtivo;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public Boolean getIndDomingo() { return indDomingo; }
	public void setIndDomingo(Boolean indDomingo) { this.indDomingo = indDomingo; }

	public Boolean getIndSegunda() { return indSegunda; }
	public void setIndSegunda(Boolean indSegunda) { this.indSegunda = indSegunda; }

	public Boolean getIndTerca() { return indTerca; }
	public void setIndTerca(Boolean indTerca) { this.indTerca = indTerca; }

	public Boolean getIndQuarta() { return indQuarta; }
	public void setIndQuarta(Boolean indQuarta) { this.indQuarta = indQuarta; }

	public Boolean getIndQuinta() { return indQuinta; }
	public void setIndQuinta(Boolean indQuinta) { this.indQuinta = indQuinta; }

	public Boolean getIndSexta() { return indSexta; }
	public void setIndSexta(Boolean indSexta) { this.indSexta = indSexta; }

	public Boolean getIndSabado() { return indSabado; }
	public void setIndSabado(Boolean indSabado) { this.indSabado = indSabado; }

	public Boolean getIndSemanalmente() { return indSemanalmente; }
	public void setIndSemanalmente(Boolean indSemanalmente) {
														this.indSemanalmente = indSemanalmente;	}

	public Boolean getIndAtivo() { return indAtivo; }
	public void setIndAtivo(Boolean indAtivo) { this.indAtivo = indAtivo; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }
	
}