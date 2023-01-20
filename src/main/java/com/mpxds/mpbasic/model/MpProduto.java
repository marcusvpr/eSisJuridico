package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.enums.MpApresentacaoProduto;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpMarcaProduto;
import com.mpxds.mpbasic.model.enums.MpStatusProduto;
import com.mpxds.mpbasic.model.enums.MpTipoConservacao;
import com.mpxds.mpbasic.model.enums.MpTipoMedicamento;
import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.model.enums.MpUnidade;
import com.mpxds.mpbasic.validation.MpSKU;

@Entity
@Table(name="mp_produto")
public class MpProduto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String sku;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private BigDecimal quantidadeEstoque = BigDecimal.ZERO;

	private Boolean indControleEstoque = true;
	
	private BigDecimal precoVenda = BigDecimal.ZERO;
	private BigDecimal precoVendaEspecial = BigDecimal.ZERO;
	private BigDecimal margemLucro = BigDecimal.ZERO;
		
	private Integer unidadesEmbalagem = 1;	
	private Integer quantidadeMinima = 1;
	private String observacao;
	private String informacao;
	
	private MpCategoria mpCategoria;
	private MpStatusProduto mpStatusProduto;
	private MpTipoProduto mpTipoProduto;
	private MpMarcaProduto mpMarcaProduto;
	private MpApresentacaoProduto mpApresentacaoProduto;
	private MpTipoMedicamento mpTipoMedicamento;
	private MpTipoConservacao mpTipoConservacao;
	private MpUnidade mpUnidade;
	private MpTabelaInterna mpLocalizacao; // tab_0009

	private List<MpMovimentoProduto> mpMovimentoProdutos = new ArrayList<MpMovimentoProduto>();
		
	private byte[] arquivoBD;
	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
	// ---
	
	@NotBlank(message = "Por favor, informe o NOME")
	@Size(max = 200)
	@Column(nullable = false, length = 200)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@NotBlank(message = "Por favor, informe o SKU")
	@MpSKU(message = "Por favor, informe um SKU no formato XX9999")
	@Column(nullable = false, length = 20, unique = false) // unique = true -> Tenant ???
	public String getSku() { return sku; }
	public void setSku(String sku) { this.sku = sku == null ? null : sku.toUpperCase(); }

	@NotNull(message = "Valor unitário é obrigatório")
	@Column(name="valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() { return valorUnitario; }
	public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

	@NotNull
	@Column(name="quantidade_estoque", nullable = false, precision = 10, scale = 2)
	public BigDecimal getQuantidadeEstoque() { return quantidadeEstoque; }
	public void setQuantidadeEstoque(BigDecimal quantidadeEstoque) {
													this.quantidadeEstoque = quantidadeEstoque; }
	
	@Column(name = "ind_controle_estoque", nullable = true)
	public Boolean getIndControleEstoque() { return indControleEstoque; }
	public void setIndControleEstoque(Boolean indControleEstoque) { this.indControleEstoque = indControleEstoque; }

	@Column(name="preco_venda", nullable = true, precision = 10, scale = 2)
	public BigDecimal getPrecoVenda() { return precoVenda; }
	public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

	@Column(name="preco_venda_especial", nullable = true, precision = 10, scale = 2)
	public BigDecimal getPrecoVendaEspecial() { return precoVendaEspecial; }
	public void setPrecoVendaEspecial(BigDecimal precoVendaEspecial) { this.precoVendaEspecial = precoVendaEspecial; }

	@Column(name="margem_lucro", nullable = true, precision = 10, scale = 2)
	public BigDecimal getMargemLucro() { return margemLucro; }
	public void setMargemLucro(BigDecimal margemLucro) { this.margemLucro = margemLucro; }

	@NotNull @Min(1) @Max(value = 9999, message = "tem um valor muito alto")
	@Column(name="unidades_embalagem", nullable = false, length = 5)
	public Integer getUnidadesEmbalagem() { return unidadesEmbalagem; }
	public void setUnidadesEmbalagem(Integer unidadesEmbalagem) {
													this.unidadesEmbalagem = unidadesEmbalagem; }

	@NotNull @Min(1) @Max(value = 9999, message = "tem um valor muito alto")
	@Column(name="quantidade_minima", nullable = false, length = 5)
	public Integer getQuantidadeMinima() { return quantidadeMinima;	}
	public void setQuantidadeMinima(Integer quantidadeMinima) {
														this.quantidadeMinima = quantidadeMinima; }
	
	@Size(max = 200)
	@Column(nullable = true, length = 200)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Lob
	@Column(nullable = true, length = 10000)
	public String getInformacao() { return informacao; }
	public void setInformacao(String informacao) { this.informacao = informacao; }
		
	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpCategoria_id", nullable = false)
	public MpCategoria getMpCategoria() { return mpCategoria; }
	public void setMpCategoria(MpCategoria mpCategoria) { this.mpCategoria = mpCategoria; }

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="mpStatus_produto", nullable = true, length = 20)
	public MpStatusProduto getMpStatusProduto() { return mpStatusProduto; }
	public void setMpStatusProduto(MpStatusProduto mpStatusProduto) {
														this.mpStatusProduto = mpStatusProduto;	}
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="mpTipo_produto", nullable = true, length = 20)
	public MpTipoProduto getMpTipoProduto() { return mpTipoProduto; }
	public void setMpTipoProduto(MpTipoProduto mpTipoProduto) { this.mpTipoProduto = mpTipoProduto;	}
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="mpMarca_produto", nullable = true, length = 30)
	public MpMarcaProduto getMpMarcaProduto() { return mpMarcaProduto; 	}
	public void setMpMarcaProduto(MpMarcaProduto mpMarcaProduto) { this.mpMarcaProduto = mpMarcaProduto; }

	@Enumerated(EnumType.STRING)
	@Column(name="mpApresentacao_Produto", nullable = false, length = 20)
	public MpApresentacaoProduto getMpApresentacaoProduto() { return mpApresentacaoProduto; }
	public void setMpApresentacaoProduto(MpApresentacaoProduto mpApresentacaoProduto) {
													this.mpApresentacaoProduto = mpApresentacaoProduto; }

	@Enumerated(EnumType.STRING)
	@Column(name="mpTipo_Medicamento", nullable = false, length = 20)
	public MpTipoMedicamento getMpTipoMedicamento() { return mpTipoMedicamento; }
	public void setMpTipoMedicamento(MpTipoMedicamento mpTipoMedicamento) {
		 											this.mpTipoMedicamento = mpTipoMedicamento; }

	@Enumerated(EnumType.STRING)
	@Column(name="mpTipo_Conservacao", nullable = false, length = 20)
	public MpTipoConservacao getMpTipoConservacao() { return mpTipoConservacao; }
	public void setMpTipoConservacao(MpTipoConservacao mpTipoConservacao) {
													this.mpTipoConservacao = mpTipoConservacao; }

	@Enumerated(EnumType.STRING)
	@Column(name="mpUnidade", nullable = true, length = 20)
	public MpUnidade getMpUnidade() { return mpUnidade; }
	public void setMpUnidade(MpUnidade mpUnidade) {	this.mpUnidade = mpUnidade; }
	
	@ManyToOne
	@JoinColumn(name = "mpLocalizacao_id", nullable = true)
	public MpTabelaInterna getMpLocalizacao() { return mpLocalizacao; }
	public void setMpLocalizacao(MpTabelaInterna mpLocalizacao) { this.mpLocalizacao = mpLocalizacao; }
	
	@OneToMany(mappedBy = "mpProduto", cascade = CascadeType.ALL)
//											orphanRemoval = true, fetch = FetchType.LAZY)
	public List<MpMovimentoProduto> getMpMovimentoProdutos() {return mpMovimentoProdutos; }
	public void setMpMovimentoProdutos(List<MpMovimentoProduto> mpMovimentoProdutos) {
												this.mpMovimentoProdutos = mpMovimentoProdutos; }

	@Enumerated(EnumType.STRING)
	@Column(name = "mpArquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { 
															this.mpArquivoAcao = mpArquivoAcao; }

	@ManyToOne
	@JoinColumn(name = "mpArquivoBD_id", nullable = true)
    public MpArquivoBD getMpArquivoBD() { return this.mpArquivoBD; }
    public void setMpArquivoBD(MpArquivoBD newMpArquivoBD) { this.mpArquivoBD = newMpArquivoBD; }  	
    
	@Lob
	@Column(columnDefinition = "blob", nullable = true, length = 10000)
    public byte[] getArquivoBD() { return this.arquivoBD; }
    public void setArquivoBD(byte[] newArquivoBD) { this.arquivoBD = newArquivoBD; }
	
	// ----

	public void baixarEstoque(BigDecimal quantidade) throws MpNegocioException {
		BigDecimal novaQuantidade = this.getQuantidadeEstoque().subtract(quantidade);
		
		if (novaQuantidade.compareTo(BigDecimal.ZERO) < 1) //  > 0)
			throw new MpNegocioException("Não há disponibilidade no estoque de "
									+ quantidade + " itens do produto " + this.getSku() + ".");
		//
		this.setQuantidadeEstoque(novaQuantidade);
	}

	public void adicionarEstoque(BigDecimal quantidade) {
								this.setQuantidadeEstoque(getQuantidadeEstoque().add(quantidade)); }

	public void subtrairEstoque(BigDecimal quantidade) {
								this.setQuantidadeEstoque(getQuantidadeEstoque().subtract(quantidade)); }
	
	// ---
	
	@Transient
	public String getTipoProduto() { return mpTipoProduto.getDescricao(); }

}