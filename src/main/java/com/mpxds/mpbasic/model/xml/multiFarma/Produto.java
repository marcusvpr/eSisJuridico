package com.mpxds.mpbasic.model.xml.multiFarma;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="produto")
public class Produto {
	//
    private String produto_nome;
    private String produto_id;
    private String farmacia_id;
    private String produto_link;
    private String produto_preco;
    private String produto_cod_barra;
    private String produto_thumb;
    private String produto_cod_farmacia;
    private String produto_principio_ativo;
    private String produto_ultima_atualizacao;
    private String produto_laboratorio;
    private String farmacia_thumb;
    private String farmacia_nome;
    private String produto_posologia;
    private String codigo_ggrem;
    private String bula_id;
    
    // ---
    
	public String getProduto_nome() { return produto_nome; }
	public void setProduto_nome(String produto_nome) { this.produto_nome = produto_nome; }

	public String getProduto_id() { return produto_id; }
	public void setProduto_id(String produto_id) { this.produto_id = produto_id; }

	public String getFarmacia_id() { return farmacia_id; }
	public void setFarmacia_id(String farmacia_id) { this.farmacia_id = farmacia_id; }

	public String getProduto_link() { return produto_link; }
	public void setProduto_link(String produto_link) { this.produto_link = produto_link; }

	public String getProduto_preco() { return produto_preco; }
	public void setProduto_preco(String produto_preco) { this.produto_preco = produto_preco; }

	public String getProduto_cod_barra() { return produto_cod_barra; }
	public void setProduto_cod_barra(String produto_cod_barra) { 
													this.produto_cod_barra = produto_cod_barra; }

	public String getProduto_thumb() { return produto_thumb; }
	public void setProduto_thumb(String produto_thumb) { this.produto_thumb = produto_thumb; }

	public String getProduto_cod_farmacia() { return produto_cod_farmacia; }
	public void setProduto_cod_farmacia(String produto_cod_farmacia) {
												this.produto_cod_farmacia = produto_cod_farmacia; }

	public String getProduto_principio_ativo() { return produto_principio_ativo; }
	public void setProduto_principio_ativo(String produto_principio_ativo) {
											this.produto_principio_ativo = produto_principio_ativo;	}

	public String getProduto_ultima_atualizacao() { return produto_ultima_atualizacao; }
	public void setProduto_ultima_atualizacao(String produto_ultima_atualizacao) {
									this.produto_ultima_atualizacao = produto_ultima_atualizacao; }

	public String getProduto_laboratorio() { return produto_laboratorio; }
	public void setProduto_laboratorio(String produto_laboratorio) {
												this.produto_laboratorio = produto_laboratorio;	}

	public String getFarmacia_thumb() { return farmacia_thumb; 	}
	public void setFarmacia_thumb(String farmacia_thumb) { this.farmacia_thumb = farmacia_thumb; }

	public String getFarmacia_nome() { return farmacia_nome; }
	public void setFarmacia_nome(String farmacia_nome) { this.farmacia_nome = farmacia_nome; }
	
	public String getProduto_posologia() { return produto_posologia; }
	public void setProduto_posologia(String produto_posologia) {
													this.produto_posologia = produto_posologia; }
	
	public String getCodigo_ggrem() { return codigo_ggrem; }
	public void setCodigo_ggrem(String codigo_ggrem) { this.codigo_ggrem = codigo_ggrem; }

	public String getBula_id() { return bula_id; }
	public void setBula_id(String bula_id) { this.bula_id = bula_id; }
    
}
