package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.MpMovimentoProduto;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpApresentacaoProduto;
import com.mpxds.mpbasic.model.enums.MpMarcaProduto;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.model.enums.MpStatusProduto;
import com.mpxds.mpbasic.model.enums.MpTipoConservacao;
import com.mpxds.mpbasic.model.enums.MpTipoMedicamento;
import com.mpxds.mpbasic.model.enums.MpTipoMovimento;
import com.mpxds.mpbasic.service.MpProdutoService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroProdutoBeanY implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpCategorias mpCategorias;

	@Inject
	private MpProdutoService mpProdutoService;
	
	@Inject
	private MpTabelaInternas mpTabelaInternas;
	
	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	
	private MpProduto mpProduto = new MpProduto();
	private MpProduto mpProdutoAnt;
	
	private MpMovimentoProduto mpMovimentoProduto = new MpMovimentoProduto();
	
	private MpTipoProduto mpTipoProduto;
	private List<MpTipoProduto> mpTipoProdutoList = new ArrayList<MpTipoProduto>();

	private MpCategoria mpCategoriaPai;	
	private List<MpCategoria> mpCategoriasRaizes;
	private List<MpCategoria> mpSubCategorias;
	
	private MpStatusProduto mpStatusProduto;
	private List<MpStatusProduto> mpStatusProdutoList = new ArrayList<MpStatusProduto>();
	
	private MpApresentacaoProduto mpApresentacaoProduto;
	private List<MpApresentacaoProduto> mpApresentacaoProdutoList = 
																new ArrayList<MpApresentacaoProduto>();
	private MpMarcaProduto mpMarcaProduto;
	private List<MpMarcaProduto> mpMarcaProdutoList = new ArrayList<MpMarcaProduto>();
	
	private MpTipoMovimento mpTipoMovimento;
	private List<MpTipoMovimento> mpTipoMovimentoList = new ArrayList<MpTipoMovimento>();
	
	private MpTipoMedicamento mpTipoMedicamento;
	private List<MpTipoMedicamento> mpTipoMedicamentoList = new ArrayList<MpTipoMedicamento>();
	
	private MpTipoConservacao mpTipoConservacao;
	private List<MpTipoConservacao> mpTipoConservacaoList = new ArrayList<MpTipoConservacao>();
	 
	private MpTabelaInterna mpLocalizacao; // tab_0009 
	private List<MpTabelaInterna> mpLocalizacaoList  = new ArrayList<MpTabelaInterna>();

	private Random rand = new Random();
	
	private Boolean indApresentacao;
	private Boolean indMedicamento;
	private Boolean indConservacao;
	
	// ---
	
	public MpCadastroProdutoBeanY() {
		if (null == this.mpProduto)
			this.limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpProduto)
			this.limpar();
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpProduto.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		//
		this.mpTipoProdutoList = Arrays.asList(MpTipoProduto.values());
		this.mpStatusProdutoList = Arrays.asList(MpStatusProduto.values());
		this.mpApresentacaoProdutoList = Arrays.asList(MpApresentacaoProduto.values());
		this.mpMarcaProdutoList = Arrays.asList(MpMarcaProduto.values());
		this.mpTipoMovimentoList = Arrays.asList(MpTipoMovimento.values());
		this.mpTipoMedicamentoList = Arrays.asList(MpTipoMedicamento.values());
		this.mpTipoConservacaoList = Arrays.asList(MpTipoConservacao.values());
		this.mpLocalizacaoList = this.mpTabelaInternas.mpNumeroList(MpTipoTabelaInterna.TAB_0009);
	}
	
	public void carregarMpCategorias() {
		//
		this.mpCategoriasRaizes = this.mpCategorias.raizes(this.mpProduto.getMpTipoProduto());
		//
		this.indApresentacao = this.mpProduto.getMpTipoProduto().getIndApresentacao();
		this.indConservacao = this.mpProduto.getMpTipoProduto().getIndConservacao();
		this.indMedicamento = this.mpProduto.getMpTipoProduto().getIndMedicamento();
		//
		System.out.println("MpCadastroProdutoBeanY.carregarMpCategorias() ( " + this.indApresentacao +
										" / " + this.indConservacao + " / " + this.indMedicamento);
	}
	
	public void carregarMpSubCategorias() {
		//
		this.mpSubCategorias = this.mpCategorias.mpSubCategoriasDe(mpCategoriaPai);
	}
	
	public void onEventSelect2D() {
		if (!this.mpMovimentoProduto.getCodigoBarra2D().isEmpty()) {
			//
			if (this.mpMovimentoProduto.getCodigoBarra2D().length() == 13) {
				String lote = this.mpMovimentoProduto.getCodigoBarra2D().substring(0, 3);
				String dtFab = this.mpMovimentoProduto.getCodigoBarra2D().substring(3, 6);
				String dtVal = this.mpMovimentoProduto.getCodigoBarra2D().substring(6, 9);
				//
				this.mpMovimentoProduto.setLote(lote);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				//
				try {
					this.mpMovimentoProduto.setDataFabricacao(sdf.parse("01/" + dtFab));
					this.mpMovimentoProduto.setDataValidade(sdf.parse("01/" + dtVal));
					//
				} catch (ParseException e) {
//					e.printStackTrace();
					MpFacesUtil.addErrorMessage("MULTIFARMAS WebService... erro Dat.Fab/Val ! (" + 
																		dtFab + "/" + dtVal);
				}
			} 
		}
	}
	
	private void limpar() {
		this.mpProduto = new MpProduto();
		//
		this.mpProduto.setSku("");		
		this.mpProduto.setNome("");	
		//
		this.mpMovimentoProduto = new MpMovimentoProduto();
		
		this.mpMovimentoProduto.setDtHrMovimento(new Date());
		this.mpMovimentoProduto.setQuantidade(BigDecimal.ZERO);
		this.mpMovimentoProduto.setMpTipoMovimento(MpTipoMovimento.ENTRADA);
		this.mpMovimentoProduto.setObservacao("");
		//
		this.mpCategoriaPai = null;
		this.mpSubCategorias = new ArrayList<>();
	}
	
	public void editaMpMovimentoProduto() {
		//
	}
	
	public void novoMpMovimentoProduto() {
		//
		this.mpMovimentoProduto = new MpMovimentoProduto();
	}
	
	public void adicionarMpMovimentoProduto() {
		// Trata atualização da quantidade do ESTOQUE ...
		if (this.mpMovimentoProduto.getMpTipoMovimento().getTipoCreditoDebito().equals("C"))
			this.mpProduto.setQuantidadeEstoque(this.mpProduto.getQuantidadeEstoque().
													add(this.mpMovimentoProduto.getQuantidade()));
		else
			this.mpProduto.setQuantidadeEstoque(this.mpProduto.getQuantidadeEstoque().
												subtract(this.mpMovimentoProduto.getQuantidade()));
		//
		this.mpProduto.getMpMovimentoProdutos().add(this.mpMovimentoProduto);
		
		this.mpMovimentoProduto.setMpProduto(this.mpProduto);
	}
	
	public void salvar() {
		//
		this.mpProduto.setSku("PR" + rand.nextInt((10000 - 1) + 1) + 1);
		
		this.mpProdutoService.salvar(this.mpProduto);
		
		this.mpProduto = new MpProduto();
		this.mpProdutoAnt = this.mpProduto;
		
		FacesMessage msg = new FacesMessage("Produto... salvo com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

    public List<MpMarcaProduto> completaMpMarcaProduto(String query) {
    	//
    	List<MpMarcaProduto> filteredMpMarcaProdutos = new ArrayList<MpMarcaProduto>();
         
        for (int i = 0; i < this.mpMarcaProdutoList.size(); i++) {
        	MpMarcaProduto mpMarcaProduto = this.mpMarcaProdutoList.get(i);
        	
            if (mpMarcaProduto.toString().startsWith(query.toUpperCase()))
            	filteredMpMarcaProdutos.add(mpMarcaProduto);
        }
        //
        return filteredMpMarcaProdutos;
    }

    public char getMpMarcaProdutoGrupo(MpMarcaProduto mpMarcaProduto) {
        return mpMarcaProduto.toString().charAt(0);
    }
    
	// ---
	
	public MpProduto getMpProduto() {
		return mpProduto;
	}
	public void setMpProduto(MpProduto mpProduto) {
		this.mpProduto = mpProduto;		
		
		if (this.mpProduto != null)
			this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();
	}

	public MpProduto getMpProdutoAnt() { return mpProdutoAnt; }
	public void setMpProdutoAnt(MpProduto mpProdutoAnt) { this.mpProdutoAnt = mpProdutoAnt; }
	
	public MpMovimentoProduto getMpMovimentoProduto() { return mpMovimentoProduto; }
	public void setMpMovimentoProduto(MpMovimentoProduto mpMovimentoProduto) {
														this.mpMovimentoProduto = mpMovimentoProduto; }

	public List<MpCategoria> getMpCategoriasRaizes() { return mpCategoriasRaizes; }
	@NotNull
	public MpCategoria getMpCategoriaPai() { return mpCategoriaPai; }
	public void setMpCategoriaPai(MpCategoria mpCategoriaPai) {
														this.mpCategoriaPai = mpCategoriaPai; }
	public List<MpCategoria> getMpSubCategorias() { return mpSubCategorias; }
	
	public MpTipoProduto getMpTipoProduto() { return mpTipoProduto;
	}
	public void setMpTipoProduto(MpTipoProduto mpTipoProduto) {
		this.mpTipoProduto = mpTipoProduto;		

		if (this.mpTipoProduto != null) {
			this.mpCategoriasRaizes = mpCategorias.raizes(mpTipoProduto);
		
			if (this.mpCategoriaPai != null) {
				this.carregarMpSubCategorias();
			}
		}
	}
	public List<MpTipoProduto> getMpTipoProdutoList() { return mpTipoProdutoList; }
	
	public MpStatusProduto getMpStatusProduto() { return mpStatusProduto; }
	public void setMpStatusProduto(MpStatusProduto mpStatusProduto) {
		this.mpStatusProduto = mpStatusProduto;		
	}
	public List<MpStatusProduto> getMpStatusProdutoList() {
		return mpStatusProdutoList;
	}
	
	public MpApresentacaoProduto getMpApresentacaoProduto() {
		return mpApresentacaoProduto;
	}
	public void setApresentacaoProduto(MpApresentacaoProduto mpApresentacaoProduto) {
		this.mpApresentacaoProduto = mpApresentacaoProduto;		
	}
	public List<MpApresentacaoProduto> getMpApresentacaoProdutoList() {
		return mpApresentacaoProdutoList;
	}
	
	public MpMarcaProduto getMpMarcaProduto() {
		return mpMarcaProduto;
	}
	public void setMpMarcaProduto(MpMarcaProduto mpMarcaProduto) {
		this.mpMarcaProduto = mpMarcaProduto;		
	}
	public List<MpMarcaProduto> getMpMarcaProdutoList() {
		return mpMarcaProdutoList;
	}
	
	public MpTipoMovimento getMpTipoMovimento() {
		return mpTipoMovimento;
	}
	public void setMpTipoMovimento(MpTipoMovimento mpTipoMovimento) {
		this.mpTipoMovimento = mpTipoMovimento;		
	}
	public List<MpTipoMovimento> getMpTipoMovimentoList() {
		return mpTipoMovimentoList;
	}	
	
	public MpTipoMedicamento getMpTipoMedicamento() {
		return mpTipoMedicamento;
	}
	public void setMpTipoMedicamento(MpTipoMedicamento mpTipoMedicamento) {
		this.mpTipoMedicamento = mpTipoMedicamento;		
	}
	public List<MpTipoMedicamento> getMpTipoMedicamentoList() {
		return mpTipoMedicamentoList;
	}	
	
	public MpTipoConservacao getMpTipoConservacao() {
		return mpTipoConservacao;
	}
	public void setMpTipoConservacao(MpTipoConservacao mpTipoConservacao) {
		this.mpTipoConservacao = mpTipoConservacao;		
	}
	public List<MpTipoConservacao> getMpTipoConservacaoList() {
		return mpTipoConservacaoList;
	}	

	public void setIndApresentacao(Boolean indApresentacao) { this.indApresentacao = indApresentacao; }
	public Boolean getIndApresentacao() { return indApresentacao; }
	
	public void setIndMedicamento(Boolean indMedicamento) { this.indMedicamento = indMedicamento; }
	public Boolean getIndMedicamento() { return indMedicamento; }
	
	public void setIndConservacao(Boolean indConservacao) { this.indConservacao = indConservacao; }
	public Boolean getIndConservacao() { return indConservacao; }

	public MpTabelaInterna getMpLocalizacao() { return mpLocalizacao; }
	public void setMpLocalizacao(MpTabelaInterna mpLocalizacao) { this.mpLocalizacao = mpLocalizacao; }
	public List<MpTabelaInterna> getMpLocalizacaoList() { return mpLocalizacaoList; }
	
	// ---
	
	public boolean isEditando() { return this.mpProduto.getId() != null; }
	
}