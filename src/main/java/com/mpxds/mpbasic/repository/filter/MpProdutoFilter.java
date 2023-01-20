package com.mpxds.mpbasic.repository.filter;

import java.io.Serializable;

//import com.mpxds.mpbasic.validation.MpSKU;

public class MpProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sku;
	private String nome;
	private String tipoProduto;
	private String posologia;
	private String principioAtivo;
	private String localizacao;

	private MpFilterOrdenacao mpFilterOrdenacao = new MpFilterOrdenacao();
	
	// ---

	// @MpSKU
	public String getSku() { return sku; }
	public void setSku(String sku) { this.sku = sku == null ? null : sku.toUpperCase();	}

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getTipoProduto() { return tipoProduto; }
	public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }

	public String getPosologia() { return posologia; }
	public void setPosologia(String posologia) { this.posologia = posologia; }

	public String getPrincipioAtivo() { return principioAtivo; }
	public void setPrincipioAtivo(String principioAtivo) { this.principioAtivo = principioAtivo; }

	public String getLocalizacao() { return localizacao; }
	public void setLocalizacao(String  localizacao) { this.localizacao = localizacao; }
	
	// ---

	public MpFilterOrdenacao getMpFilterOrdenacao() { return mpFilterOrdenacao; }
	public void setMpFilterOrdenacao(MpFilterOrdenacao mpFilterOrdenacao) {
												this.mpFilterOrdenacao = mpFilterOrdenacao; }

}