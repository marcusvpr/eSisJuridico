package com.mpxds.mpbasic.model.pt05;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt05_custas_composicao_")
@Table(name = "mp_pt05_custas_composicao")
public class MpCustasComposicao extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String tabela;
	private String item;
	private String subItem; 
	private String descricaoTabela;
//	private String complemento;
//	private String excessao;
	private String codigoAto = "";
	private String codigoAtoConvenio = "";

	private Integer quantidade; 
	private Integer quantidadeBalcao;
	
	private BigDecimal valorCusta = BigDecimal.ZERO; 
	private BigDecimal valorDe = BigDecimal.ZERO;
	private BigDecimal valorAte = BigDecimal.ZERO;
	
	// ---
	
	public MpCustasComposicao(){
		super();
	}

	@NotBlank(message = "Por favor, informe a Tabela")	
	@Column(nullable = false, length = 4)
	public String getTabela() { return tabela; }
	public void setTabela(String tabela) { this.tabela = tabela.toUpperCase(); }

	@Column(nullable = false, length = 4)
	public String getItem() { return item; }
	public void setItem(String item) { this.item = item.toUpperCase(); }

	@Column(nullable = false, length = 4)
	public String getSubItem() { return subItem; }
	public void setSubItem(String subItem) { this.subItem = subItem.toUpperCase(); }

	@Column(name = "descricao_tabela", nullable = false, length = 35)
	public String getDescricaoTabela() { return descricaoTabela; }
	public void setDescricaoTabela(String descricaoTabela) { 
									this.descricaoTabela = descricaoTabela.toUpperCase(); }

//	@Column(nullable = true, length = 20)
//	public String getComplemento() {
//		return complemento;
//	}
//	public void setComplemento(String complemento) {
//		this.complemento = complemento;
//	}
//
//	@Column(nullable = true, length = 50)
//	public String getExcessao() {
//		return excessao;
//	}
//	public void setExcessao(String excessao) {
//		this.excessao = excessao;
//	}

	@Column(name = "codigo_ato", nullable = true, length = 4)
	public String getCodigoAto() { return codigoAto; }
	public void setCodigoAto(String codigoAto) { this.codigoAto = codigoAto.toUpperCase(); }

	@Column(name = "codigo_ato_convenio", nullable = true, length = 4)
	public String getCodigoAtoConvenio() { return codigoAtoConvenio; }
	public void setCodigoAtoConvenio(String codigoAtoConvenio) {
								this.codigoAtoConvenio = codigoAtoConvenio.toUpperCase(); }

	@Column(nullable = true, length = 5)
	public Integer getQuantidade() { return quantidade; }
	public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

	@Column(name = "quantidade_balcao", nullable = true, length = 5)
	public Integer getQuantidadeBalcao() {return quantidadeBalcao; }
	public void setQuantidadeBalcao(Integer quantidadeBalcao) {
													this.quantidadeBalcao = quantidadeBalcao; }

	@Column(name = "valor_custa", nullable = true, precision = 10, scale = 2)
	public BigDecimal getValorCusta() { return valorCusta; }
	public void setValorCusta(BigDecimal valorCusta) { this.valorCusta = valorCusta; }
	
	/*
	 *TODO: normalizar e separar esse campo
	 *
	 */
	@Column(name = "valor_de", nullable = true, precision = 10, scale = 2)
	public BigDecimal getValorDe() { return valorDe; }
	public void setValorDe(BigDecimal valorDe) { this.valorDe = valorDe; }

	/*
	 *TODO: normalizar e separar esse campo
	 *
	 */
	@Column(name = "valor_ate", nullable = true, precision = 10, scale = 2)
	public BigDecimal getValorAte() { return valorAte; }
	public void setValorAte(BigDecimal valorAte) { this.valorAte = valorAte; }

	@Transient
	public String getTabelaItemSub() {
		if (null==this.tabela) this.tabela = "";
		if (null==this.item) this.item = "";
		if (null==this.subItem) this.subItem = "";
		//
	    return this.tabela.trim() + "." + this.item.trim() + "." + subItem.trim() ;
	}

}