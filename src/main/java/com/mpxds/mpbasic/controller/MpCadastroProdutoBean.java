package com.mpxds.mpbasic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

import javax.validation.constraints.NotNull;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.MpMovimentoProduto;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpApresentacaoProduto;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpMarcaProduto;
import com.mpxds.mpbasic.model.enums.MpStatusProduto;
import com.mpxds.mpbasic.model.enums.MpTipoConservacao;
import com.mpxds.mpbasic.model.enums.MpTipoMedicamento;
import com.mpxds.mpbasic.model.enums.MpTipoMovimento;
import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpUnidade;
import com.mpxds.mpbasic.model.xml.multiFarma.Root;
import com.mpxds.mpbasic.model.xml.multiFarma.Posologia;
import com.mpxds.mpbasic.model.xml.multiFarma.Produto;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.repository.MpMovimentoProdutos;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.repository.filter.MpProdutoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpProdutoService;

import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroProdutoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpCategorias mpCategorias;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpProdutos mpProdutos;

	@Inject
	private MpMovimentoProdutos mpMovimentoProdutos;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpProdutoService mpProdutoService;
	
	@Inject
	private MpTabelaInternas mpTabelaInternas;
	
	// ---
	
	private MpProduto mpProduto = new MpProduto();
	private MpProduto mpProdutoAnt;
	
	private MpProdutoFilter mpFiltro = new MpProdutoFilter();

	private MpMovimentoProduto mpMovimentoProduto = new MpMovimentoProduto();
	private List<MpMovimentoProduto> mpMovimentoProdutoExcluidoList =
														new ArrayList<MpMovimentoProduto>();
	
	private MpCategoria mpCategoriaPai;	
	private List<MpCategoria> mpCategoriasRaizes;
	private List<MpCategoria> mpSubCategorias = new ArrayList<MpCategoria>();
		
	private MpStatusProduto mpStatusProduto;
	private List<MpStatusProduto> mpStatusProdutoList = new ArrayList<MpStatusProduto>();
	
	private MpTipoProduto mpTipoProduto;
	private List<MpTipoProduto> mpTipoProdutoList = new ArrayList<MpTipoProduto>();
	
	private MpMarcaProduto mpMarcaProduto;
	private List<MpMarcaProduto> mpMarcaProdutoList = new ArrayList<MpMarcaProduto>();
		
	private MpTipoMovimento mpTipoMovimento;
	private List<MpTipoMovimento> mpTipoMovimentoList = new ArrayList<MpTipoMovimento>();
	
	private MpTipoMedicamento mpTipoMedicamento;
	private List<MpTipoMedicamento> mpTipoMedicamentoList = new ArrayList<MpTipoMedicamento>();
	
	private MpApresentacaoProduto mpApresentacaoProduto;
	private List<MpApresentacaoProduto> mpApresentacaoProdutoList = 
														new ArrayList<MpApresentacaoProduto>();

	private MpTipoConservacao mpTipoConservacao;
	private List<MpTipoConservacao> mpTipoConservacaoList = new ArrayList<MpTipoConservacao>();

	private MpUnidade mpUnidade;
	private List<MpUnidade> mpUnidadeList = new ArrayList<MpUnidade>();
	 
	private MpTabelaInterna mpLocalizacao; // tab_0009 
	private List<MpTabelaInterna> mpLocalizacaoList  = new ArrayList<MpTabelaInterna>();

	private Random rand = new Random();
	
	private Boolean indApresentacao;
	private Boolean indMedicamento;
	private Boolean indConservacao;

	// ------- Mutifarmas XML - WebService (INI) --------
	private Root root = new Root();
	private Posologia posologia;
	
	private Produto produto;
	private List<Produto> produtoList = new ArrayList<Produto>();
	private List<Produto> produtoFilterList = new ArrayList<Produto>();

	private String selectLevel = "1";
	// ------- Mutifarmas XML - WebService (FIM) --------

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
		
	private String txtModoTela = "";
	
	private String usuarioGrupos = "";
	
	// ---
	
	private MpArquivoAcao mpArquivoAcao;
	private List<MpArquivoAcao> mpArquivoAcaoList = new ArrayList<MpArquivoAcao>();
	
	private MpArquivoBD mpArquivoBD;
	private List<MpArquivoBD> mpArquivoBDList;	
	//
	private String arquivoAcaoSelecao;

	private UploadedFile arquivoUpload;
	private StreamedContent arquivoContent = new DefaultStreamedContent();
	private byte[] arquivoBytes;
	private byte[] arquivoBytesC;

    // Configuração Sistema ...
    // ------------------------
	private Boolean scIndCapturaFoto;
	
	// ---------------------
	
	public MpCadastroProdutoBean() {
		//
		if (null == this.mpProduto)
			limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpProduto) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
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

		this.setMpProdutoAnt(this.mpProduto);
		//
//		System.out.println("MpCadastroProdutoBean.inicializar() - 000 (SKU= " +
//																	this.mpProdutoAnt.getSku() ); 
		//
		if (null == this.mpProduto.getMpTipoProduto()) 
			this.mpCategoriasRaizes = mpCategorias.mpCategoriaList(mpSeguranca.capturaTenantId().trim());
		else {
			this.mpCategoriasRaizes = mpCategorias.raizes(this.mpProduto.getMpTipoProduto());
			//
			if (this.mpCategoriaPai != null) this.carregarMpSubCategorias();
		}
		//
		this.trataExibicaoArquivo();
		//
		// Trata Configuração Sistema ...
		// ==============================
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("indCapturaFoto");
		if (null == mpSistemaConfig)
			this.scIndCapturaFoto = true;
		else {
			this.scIndCapturaFoto = mpSistemaConfig.getIndValor();
			if (this.scIndCapturaFoto)
				if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
					this.scIndCapturaFoto = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																						getIndCapturaFoto();
				else
					this.scIndCapturaFoto = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																						getIndCapturaFoto();
		}		
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();

		// Verifica filtro para controle de Estoque ...
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLoginGrupo();
		else
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getGruposNome();
		//
		for (MpTipoProduto mpTipoProdutoX : Arrays.asList(MpTipoProduto.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpTipoProdutoX.getIndControleEstoque())
					this.mpTipoProdutoList.add(mpTipoProdutoX);
			} else
				if (!mpTipoProdutoX.getIndControleEstoque())
					this.mpTipoProdutoList.add(mpTipoProdutoX);
		}
		//
		for (MpMarcaProduto mpMarcaProdutoX : Arrays.asList(MpMarcaProduto.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpMarcaProdutoX.getIndControleEstoque())
					this.mpMarcaProdutoList.add(mpMarcaProdutoX);
			} else
				if (!mpMarcaProdutoX.getIndControleEstoque())
					this.mpMarcaProdutoList.add(mpMarcaProdutoX);
		}
		//
		for (MpApresentacaoProduto mpApresentacaoProdutoX : Arrays.asList(MpApresentacaoProduto.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpApresentacaoProdutoX.getIndControleEstoque())
					this.mpApresentacaoProdutoList.add(mpApresentacaoProdutoX);
			} else
				if (!mpApresentacaoProdutoX.getIndControleEstoque())
					this.mpApresentacaoProdutoList.add(mpApresentacaoProdutoX);
		}
		//
		for (MpUnidade mpUnidadeX : Arrays.asList(MpUnidade.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpUnidadeX.getIndControleEstoque())
					this.mpUnidadeList.add(mpUnidadeX);
			} else
				if (!mpUnidadeX.getIndControleEstoque())
					this.mpUnidadeList.add(mpUnidadeX);
		}
		//
		this.mpStatusProdutoList = Arrays.asList(MpStatusProduto.values());
		this.mpTipoMedicamentoList = Arrays.asList(MpTipoMedicamento.values());
		this.mpTipoConservacaoList = Arrays.asList(MpTipoConservacao.values());
		this.mpTipoMovimentoList = Arrays.asList(MpTipoMovimento.values());
		this.mpUnidadeList = Arrays.asList(MpUnidade.values());
		this.mpLocalizacaoList = this.mpTabelaInternas.mpNumeroList(MpTipoTabelaInterna.TAB_0009);
		//
		if (!this.scIndCapturaFoto) {
			this.mpArquivoAcaoList.add(MpArquivoAcao.ASSOCIAR);
			this.mpArquivoAcaoList.add(MpArquivoAcao.CARREGAR);
		} else
			this.mpArquivoAcaoList = Arrays.asList(MpArquivoAcao.values());
		//
		this.mpArquivoBDList = mpArquivoBDs.porMpArquivoBDList();	
	}
	
	public void carregarMpCategorias() {
		//
//		System.out.println("MpCadastroProdutoBeanX.carregarMpCategorias() - 000"); 

		if (null == this.mpProduto.getMpTipoProduto()) return;
		
		this.mpCategoriasRaizes = mpCategorias.raizes(this.mpProduto.getMpTipoProduto());
		//
		this.indApresentacao = this.mpProduto.getMpTipoProduto().getIndApresentacao();
		this.indConservacao = this.mpProduto.getMpTipoProduto().getIndConservacao();
		this.indMedicamento = this.mpProduto.getMpTipoProduto().getIndMedicamento();
		//		
//		System.out.println("MpCadastroProdutoBeanX.carregarMpCategorias() ( " + 
//							this.mpCategoriasRaizes.size() + this.mpProduto.getMpTipoProduto());	
	}
	
	public void carregarMpSubCategorias() {
		//
		this.mpSubCategorias = this.mpCategorias.mpSubCategoriasDe(mpCategoriaPai);
		//
//		System.out.println("MpCadastroProdutoBeanX.carregarMpSubCategorias() ( " + 
//																this.mpSubCategorias.size());	
	}
			
	public void adicionarMpMovimentoProduto() {
		//
		this.mpMovimentoProduto = new MpMovimentoProduto();		
			
		this.mpMovimentoProduto.setMpProduto(mpProduto);
		this.mpMovimentoProduto.setTenantId(mpSeguranca.capturaTenantId());
		this.mpMovimentoProduto.setDtHrMovimento(new Date());
		this.mpMovimentoProduto.setQuantidade(BigDecimal.ZERO);
		this.mpMovimentoProduto.setMpTipoMovimento(MpTipoMovimento.ENTRADA);
		this.mpMovimentoProduto.setObservacao("");

		this.mpProduto.getMpMovimentoProdutos().add(this.mpMovimentoProduto);
	}

	public void removerMpMovimentoProduto() {
		//
		try {
			this.mpProduto.getMpMovimentoProdutos().remove(this.mpMovimentoProduto);
			
			this.mpMovimentoProdutoExcluidoList.add(this.mpMovimentoProduto);

			this.mpProduto.setQuantidadeEstoque(this.mpProduto.getQuantidadeEstoque().
												subtract(this.mpMovimentoProduto.getQuantidade()));
			
			MpFacesUtil.addInfoMessage("Movimento Produto ( " +
					this.mpMovimentoProduto.getDtHrMovimentoSDF() + " )... excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			
	
	public void salvarMpMovimentoProduto() {
		//
		// Trata atualização da quantidade do ESTOQUE ...
		if (null == this.mpMovimentoProduto.getMpTipoMovimento())
			assert(true); // nop
		else
		if (this.mpMovimentoProduto.getMpTipoMovimento().getTipoCreditoDebito().equals("C"))
			this.mpProduto.setQuantidadeEstoque(this.mpProduto.getQuantidadeEstoque().
												add(this.mpMovimentoProduto.getQuantidade()));
		else
			this.mpProduto.setQuantidadeEstoque(this.mpProduto.getQuantidadeEstoque().
											subtract(this.mpMovimentoProduto.getQuantidade()));
		//
		this.mpMovimentoProduto = new MpMovimentoProduto();		
	}			

	public void fecharMpItemObjeto() {
		//
		if (null == this.mpMovimentoProduto.getId())
			this.mpProduto.getMpMovimentoProdutos().remove(this.mpMovimentoProduto);
	}			
	
	public void onEventSelect2D() {
		//
		if (!this.mpMovimentoProduto.getCodigoBarra2D().isEmpty()) {
			//
			if (this.mpMovimentoProduto.getCodigoBarra2D().length() == 13) {
				String lote = this.mpMovimentoProduto.getCodigoBarra2D().substring(0, 3);
				String dtFab = this.mpMovimentoProduto.getCodigoBarra2D().substring(3, 6);
				String dtVal = this.mpMovimentoProduto.getCodigoBarra2D().substring(6, 9);
				//
				this.mpMovimentoProduto.setLote(lote);
				//
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
	
	public void onMultiFarmaWebService() {
    	//
		if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
			//
			MpFacesUtil.addErrorMessage("WebService... sem configuração !");
			//
			return;
		}
		//
		this.root = MpClienteWebService.executaMultiFarmaPost(this.getMpProduto().getNome(), "preco");
		if (null == this.root) {
			MpFacesUtil.addErrorMessage("MULTIFARMAS WebService... sem retorno !");
			//
			return;
		}
		if (null == this.root.getPosologia()) {
			MpFacesUtil.addErrorMessage("MULTIFARMAS WebService Produtos... sem retorno !");
			//
			return;
		}
		//
		this.produtoList.clear();
		
		for (Posologia posologia: this.root.getPosologia()) {
			//
			for (Produto produto: posologia.getProduto()) {
				//
				this.produtoList.add(produto);
			}			
		}	
		//
//		System.out.println("MpCadastroPodutoBeanX.onMultiFarmaWebService() - ( " + 
//																	this.produtoList.size());
    }
	
	public void filtrar() {
		//
		this.produtoFilterList.clear();
		this.produtoFilterList.addAll(this.produtoList);
		
		this.produtoList.clear();
		//
		String nomeF = "";
		String posologiaF = "";
		String principioAtivoF = "";
		//
		for (Produto produtoX : this.produtoFilterList) {
			//
			if (null == this.getMpFiltro().getNome())
				nomeF = "null";
			else
				nomeF = this.getMpFiltro().getNome().toUpperCase();
			if (null == this.getMpFiltro().getPosologia())
				posologiaF = "null";
			else
				posologiaF = this.getMpFiltro().getPosologia().toUpperCase();
			if (null == this.getMpFiltro().getPrincipioAtivo())
				principioAtivoF = "null";
			else
				principioAtivoF = this.getMpFiltro().getPrincipioAtivo().toUpperCase();
			//
			if (produtoX.getFarmacia_nome().toUpperCase().contains(nomeF)
			||  produtoX.getProduto_posologia().toUpperCase().contains(posologiaF)
			||  produtoX.getProduto_principio_ativo().toUpperCase().contains(principioAtivoF))
				this.produtoList.add(produtoX);
			//
		}
		//
//		System.out.println("MpCadastroPodutoBeanX.filtrar() - ( " +	this.produtoList.size());
    }
	
	public void onSelPosologia(Posologia posologia) {
    	//
		this.selectLevel = "2";
		
		this.produtoList = posologia.getProduto();
		
//		System.out.println("MpCadastroProdutoBean.onSelPosologia() - 000 ( " + 
//																		this.produtoList.size());
    }
	
	public void produtoSelecionado(Produto produto) {
		//
//		Produto produto = (Produto) event.getObject();
		
		this.mpProduto.setNome(produto.getProduto_nome());
		
		if (null == produto.getProduto_preco() || produto.getProduto_preco().isEmpty())
			assert(true); // nop
		else
			this.mpProduto.setValorUnitario(new BigDecimal(produto.getProduto_preco()));
		//
		this.carregarMpCategorias();
		this.carregarMpSubCategorias();
		//
//		System.out.println("MpCadastroProdutoBean.produtoSelecionado() - 000 ( " + 
//									produto.getProduto_nome() + " / " + produto.getProduto_preco() +
//									" / " + this.mpProduto.getValorUnitario());
	}
		
	public void salvar() {
		//
		// =========================================
		// Trata MpArquivoBD (Arquivo Banco Dados !)
		// =========================================
		if (this.isArquivoContent())
			this.mpProduto.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpProduto.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpProduto.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpProduto.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpProduto.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		// Default's ...
		if (null == this.mpProduto.getSku()
		||  this.mpProduto.getSku().isEmpty())
			this.mpProduto.setSku("PR" + rand.nextInt((10000 - 1) + 1) + 1);
		
		if (null == this.mpProduto.getMpTipoConservacao())
			this.mpProduto.setMpTipoConservacao(MpTipoConservacao.AMBIENTE);
		if (null == this.mpProduto.getMpTipoMedicamento())
			this.mpProduto.setMpTipoMedicamento(MpTipoMedicamento.OUTRO);

		//
		this.mpProduto = mpProdutoService.salvar(this.mpProduto);
		//
		if (this.mpMovimentoProdutoExcluidoList.size() > 0) {
			for (MpMovimentoProduto mpMovimentoProdutoX : 
												this.mpMovimentoProdutoExcluidoList) {
				//
				if (null == mpMovimentoProdutoX.getId()) continue;
					
				this.mpMovimentoProdutos.remover(mpMovimentoProdutoX);
			}
		}
		// this.limpar();
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;
		//
		MpFacesUtil.addInfoMessage("Produto... salvo com sucesso!");
		//
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
    	//
        return mpMarcaProduto.toString().charAt(0);
    }

	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpProduto.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpProduto.getMpArquivoAcao().getDescricao();
	}
    
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpProduto = this.mpProdutos.porNavegacao("mpFirst", " "); 
		if (null == this.mpProduto) {
//			System.out.println("MpCadastroProdutoBean.mpFirst() - Entrou 000");
			this.limpar();
		}
		else {
			this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();

			this.carregarMpCategorias();
			this.carregarMpSubCategorias();
			//
//			System.out.println("MpCadastroProdutoBean.mpFirst() - Entrou 0001 = " +
//															this.mpProduto.getParametro());
			this.trataExibicaoArquivo();
		}
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpProduto.getSku()) return;
		//
		this.setMpProdutoAnt(this.mpProduto);
		//		
		this.mpProduto = this.mpProdutos.porNavegacao("mpPrev", mpProduto.getSku());
		if (null == this.mpProduto) {
			this.mpProduto = this.mpProdutoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();

		this.carregarMpCategorias();
		this.carregarMpSubCategorias();
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpProdutoAnt(this.mpProduto);
		
		this.limpar();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpProduto.getId()) return;
		//
		this.setMpProdutoAnt(this.mpProduto);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpProduto.getId()) return;
		//
		try {
			this.mpProdutos.remover(mpProduto);
			
			MpFacesUtil.addInfoMessage("Sistema Configuração... " + this.mpProduto.getSku()
																	+ " excluído com sucesso.");			
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
//			System.out.println("MpCadastroProdutoBean.mpGrava() - 000 (SKU.Ant/Atu= " +
//								this.mpProdutoAnt.getSku() + " / " + this.mpProduto.getSku() ); 
			//
			MpFacesUtil.addInfoMessage("ERRO Gravação! ( " + e.toString());
			return;
		}

		this.setMpProdutoAnt(this.mpProduto);
		//
//		System.out.println("MpCadastroProdutoBean.mpGrava() - 001 (SKU.Ant/Atu= " +
//									this.mpProdutoAnt.getSku() + " / " + this.mpProduto.getSku() ); 
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpProduto = this.mpProdutoAnt;
		//
		this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();

		this.carregarMpCategorias();
		this.carregarMpSubCategorias();
		//
		this.trataExibicaoArquivo();
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpProduto.getSku()) return;
		//
		this.setMpProdutoAnt(this.mpProduto);
		//		
		this.mpProduto = this.mpProdutos.porNavegacao("mpNext", mpProduto.getSku());
		if (null == this.mpProduto) {
			this.mpProduto = this.mpProdutoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();

		this.carregarMpCategorias();
		this.carregarMpSubCategorias();
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpProduto = this.mpProdutos.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpProduto)
			this.limpar();
		else {
			this.trataExibicaoArquivo();

			this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();

			this.carregarMpCategorias();
			this.carregarMpSubCategorias();
			//
		}
//		else
//			System.out.println("MpCadastroProdutoBean.mpEnd() ( Entrou 0001 = " +
//															this.mpProduto.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpProduto.getId()) return;

		try {
			this.setMpProdutoAnt(this.mpProduto);
			
			this.mpProduto = (MpProduto) this.mpProduto.clone();
			//
			this.mpProduto.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
//		System.out.println("MpCadastroProdutoBean.mpHelp() (Obj = " + 
//																this.mpObjetoHelp.getTransacao());
	}
	
	private void limpar() {
		//
		this.mpProduto = new MpProduto();
		
		this.mpProduto.setTenantId(mpSeguranca.capturaTenantId());		
		//
		this.mpProduto.setSku("");		
		this.mpProduto.setNome("");	
		//
		this.mpMovimentoProduto = new MpMovimentoProduto();
		
		this.mpMovimentoProduto.setMpProduto(mpProduto);
		this.mpMovimentoProduto.setDtHrMovimento(new Date());
		this.mpMovimentoProduto.setQuantidade(BigDecimal.ZERO);
		this.mpMovimentoProduto.setMpTipoMovimento(MpTipoMovimento.ENTRADA);
		this.mpMovimentoProduto.setObservacao("");
		
		this.mpMovimentoProduto.setTenantId(mpSeguranca.capturaTenantId());		
		
		this.mpCategoriaPai = null;
		
		this.mpSubCategorias.clear();
		this.mpMovimentoProdutoExcluidoList.clear();
		//
		this.limparArquivo();
	}
		
	private void limparArquivo() {
		//
		this.arquivoBytes = null;
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;
	}

	// -------- Trata FOTO ...
	
	public void handleFileUpload(FileUploadEvent event) {
		//
		try {
			this.arquivoContent = new DefaultStreamedContent(event.getFile().getInputstream(),
													"image/jpeg", event.getFile().getFileName());
			
			this.arquivoBytes = MpAppUtil.getFileContents(event.getFile().getInputstream());
			
			this.mpProduto.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
			MpFacesUtil.addInfoMessage("Arquivo ( " + event.getFile().getFileName() +
																" )... carregado com sucesso.");
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
	}

	public void aoCapturarArquivo(CaptureEvent event) {
		//
		if (null == event || null == event.getData()) {
			//
			System.out.println("MpCadastroProdutoBean.aoCapturarArquivo() - NULL");
			return;
		}
		
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpProduto.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
															" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpProduto.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpProduto.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpProduto.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpProduto.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpProduto.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpProduto.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpProduto.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpProduto.getArquivoBD());
	}
	
	// ---
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpProduto.getArquivoBD() != null && this.mpProduto.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
															this.mpProduto.getArquivoBD()), "");
    	return imagemDsc;
    }

    public UploadedFile getArquivoUpload() { return arquivoUpload; }
    public void setArquivoUpload(UploadedFile arquivoUpload) { 
    														this.arquivoUpload = arquivoUpload; }
    
	public StreamedContent getArquivoContent() { return arquivoContent; }
    public void setArquivoContent(StreamedContent arquivoContent) { 
    													this.arquivoContent = arquivoContent; }

	public byte[] getArquivoBytes() { return arquivoBytes; }
    public void setArquivoBytes(byte[] arquivoBytes) { this.arquivoBytes = arquivoBytes; }
	public byte[] getArquivoBytesC() { return arquivoBytesC; }
    public void setArquivoBytesC(byte[] arquivoBytesC) { this.arquivoBytesC = arquivoBytesC; }
	
	public boolean isArquivoContent() { return getArquivoContent() != null; }

	// --- 
	
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { 
														this.mpArquivoAcao = mpArquivoAcao; }
	public List<MpArquivoAcao> getMpArquivoAcaoList() { return mpArquivoAcaoList; }

	public MpArquivoBD getMPArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }
	public List<MpArquivoBD> getMpArquivoBDList() { return mpArquivoBDList; }
	
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { 
														this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { 
														this.indNaoEditavel = indNaoEditavel; }

	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
		
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) {
		this.mpProduto = mpProduto;		
		//
		if (this.mpProduto != null)
			this.mpCategoriaPai = this.mpProduto.getMpCategoria().getMpCategoriaPai();
	}

	public MpProduto getMpProdutoAnt() { return mpProdutoAnt; }
	public void setMpProdutoAnt(MpProduto mpProduto) {
		//
		try {
			this.mpProdutoAnt = (MpProduto) this.mpProduto.clone();
			this.mpProdutoAnt.setId(this.mpProduto.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }
	
	public MpMovimentoProduto getMpMovimentoProduto() { return mpMovimentoProduto; }
	public void setMpMovimentoProduto(MpMovimentoProduto mpMovimentoProduto) {
												this.mpMovimentoProduto = mpMovimentoProduto; }

	public List<MpCategoria> getMpCategoriasRaizes() { return mpCategoriasRaizes; }
	@NotNull
	public MpCategoria getMpCategoriaPai() { return mpCategoriaPai; }
	public void setMpCategoriaPai(MpCategoria mpCategoriaPai) {
														this.mpCategoriaPai = mpCategoriaPai; }
	public List<MpCategoria> getMpSubCategorias() { return mpSubCategorias; }
	
	public MpStatusProduto getMpStatusProduto() { return mpStatusProduto; }
	public void setMpStatusProduto(MpStatusProduto mpStatusProduto) {
														this.mpStatusProduto = mpStatusProduto;	}
	public List<MpStatusProduto> getMpStatusProdutoList() {	return mpStatusProdutoList; }
	
	public MpApresentacaoProduto getMpApresentacaoProduto() { return mpApresentacaoProduto; }
	public void setMpApresentacaoProduto(MpApresentacaoProduto mpApresentacaoProduto) {
											this.mpApresentacaoProduto = mpApresentacaoProduto; }
	public List<MpApresentacaoProduto> getMpApresentacaoProdutoList() {
															return mpApresentacaoProdutoList; }
	
	public MpTipoProduto getMpTipoProduto() { return mpTipoProduto; }
	public void setMpTipoProduto(MpTipoProduto mpTipoProduto) {
		this.mpTipoProduto = mpTipoProduto;		

		if (this.mpTipoProduto != null) {
			this.mpCategoriasRaizes = mpCategorias.raizes(mpTipoProduto);
		
			if (this.mpCategoriaPai != null)
				this.carregarMpSubCategorias();
			//
		}
	}
	public List<MpTipoProduto> getMpTipoProdutoList() { return mpTipoProdutoList; }
	
	// --------
	
	public Root getRoot() { return root; }
	public void setRoot(Root root) { this.root = root; }

	public Posologia getPosologia() { return posologia; }
	public void setPosologia(Posologia posologia) { this.posologia = posologia; }
	
	public Produto getProduto() { return produto; }
	public void setProduto(Produto produto) { this.produto = produto; }
	public List<Produto> getProdutoList() { return produtoList; }

	public MpProdutoFilter getMpFiltro() { return mpFiltro; }
	public void setMpFiltro(MpProdutoFilter mpFiltro) { this.mpFiltro = mpFiltro; }

	public String getSelectLevel() { return selectLevel; }
	public void setSelectLevel(String selectLevel) { this.selectLevel = selectLevel; }
	
	// --------
	
	public MpMarcaProduto getMpMarcaProduto() { return mpMarcaProduto; }
	public void setMpMarcaProduto(MpMarcaProduto mpMarcaProduto) {
														this.mpMarcaProduto = mpMarcaProduto; }
	public List<MpMarcaProduto> getMpMarcaProdutoList() { return mpMarcaProdutoList; }
	
	public MpTipoMovimento getMpTipoMovimento() { return mpTipoMovimento; }
	public void setMpTipoMovimento(MpTipoMovimento mpTipoMovimento) {
														this.mpTipoMovimento = mpTipoMovimento;	}
	public List<MpTipoMovimento> getMpTipoMovimentoList() { return mpTipoMovimentoList; }	
	
	public MpTipoMedicamento getMpTipoMedicamento() { return mpTipoMedicamento; }
	public void setMpTipoMedicamento(MpTipoMedicamento mpTipoMedicamento) {
													this.mpTipoMedicamento = mpTipoMedicamento;	}
	public List<MpTipoMedicamento> getMpTipoMedicamentoList() { return mpTipoMedicamentoList; }	
	
	public MpTipoConservacao getMpTipoConservacao() { return mpTipoConservacao; }
	public void setMpTipoConservacao(MpTipoConservacao mpTipoConservacao) {
													this.mpTipoConservacao = mpTipoConservacao;	}
	public List<MpTipoConservacao> getMpTipoConservacaoList() { return mpTipoConservacaoList; }	
	
	public MpUnidade getMpUnidade() { return mpUnidade; }
	public void setMpUnidade(MpUnidade mpUnidade) {	this.mpUnidade = mpUnidade;	}
	public List<MpUnidade> getMpUnidadeList() { return mpUnidadeList; }	

	public void setIndApresentacao(Boolean indApresentacao) { 
														this.indApresentacao = indApresentacao; }
	public Boolean getIndApresentacao() { return indApresentacao; }
	
	public void setIndMedicamento(Boolean indMedicamento) { this.indMedicamento = indMedicamento; }
	public Boolean getIndMedicamento() { return indMedicamento; }
	
	public void setIndConservacao(Boolean indConservacao) { this.indConservacao = indConservacao; }
	public Boolean getIndConservacao() { return indConservacao; }

	public MpTabelaInterna getMpLocalizacao() { return mpLocalizacao; }
	public void setMpLocalizacao(MpTabelaInterna mpLocalizacao) { this.mpLocalizacao = mpLocalizacao; }
	public List<MpTabelaInterna> getMpLocalizacaoList() { return mpLocalizacaoList; }
	
	public boolean isEditando() { return this.mpProduto.getId() != null; }

}