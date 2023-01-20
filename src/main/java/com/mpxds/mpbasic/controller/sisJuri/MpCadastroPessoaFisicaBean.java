package com.mpxds.mpbasic.controller.sisJuri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.sisJuri.MpPessoaFisicaService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroPessoaFisicaBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpPessoaFisicas mpPessoaFisicas;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpPessoaFisicaService mpPessoaFisicaService;

	// --- 
	
	private MpPessoaFisica mpPessoaFisica = new MpPessoaFisica();
	private MpPessoaFisica mpPessoaFisicaAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;

	private String nomeFind = "";
		
	private String txtModoTela = "";
		
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;

	private MpEstadoUF mpOabUF;
	private List<MpEstadoUF> mpOabUFList;
	
	private MpTabelaInternaSJ mpBanco; // tab_0015
	private List<MpTabelaInternaSJ> mpBancoList  = new ArrayList<MpTabelaInternaSJ>();

	private MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();

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

	public MpCadastroPessoaFisicaBean() {
		//
		if (null == this.mpPessoaFisica)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpPessoaFisica) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPessoaFisica.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
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
				this.scIndCapturaFoto = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndCapturaFoto();
		}
		// ---
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();

		this.mpOabUFList = Arrays.asList(MpEstadoUF.values());
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
		this.mpBancoList = this.mpTabelaInternaSJs.mpNumeroList(MpTipoTabelaInternaSJ.TAB_0015);
		//
		if (!this.scIndCapturaFoto) {
			this.mpArquivoAcaoList.add(MpArquivoAcao.ASSOCIAR);
			this.mpArquivoAcaoList.add(MpArquivoAcao.CARREGAR);
		} else
			this.mpArquivoAcaoList = Arrays.asList(MpArquivoAcao.values());
		//
		this.mpArquivoBDList = mpArquivoBDs.porMpArquivoBDList();
	}

	public void salvar() {
		//
		// =========================================
		// Trata MpArquivoBD (Arquivo Banco Dados !)
		// =========================================
		if (this.isArquivoContent())
			this.mpPessoaFisica.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpPessoaFisica.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpPessoaFisica.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpPessoaFisica.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpPessoaFisica.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";

		if (!this.mpPessoaFisica.getCpf().isEmpty()) {
			String isCPF = MpAppUtil.verificaCpf(this.mpPessoaFisica.getCpf());
			if (isCPF.equals("false"))
				msg = msg + "\n(CPF)";
		}
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		//
		this.mpPessoaFisica = this.mpPessoaFisicaService.salvar(this.mpPessoaFisica);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Pessoa Fisica... salva com sucesso!");
	}
	
	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.mpPessoaFisica.getMpEnderecoLocal().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroPessoaFisicaBean.onCepWebService() - ( " +
//				this.mpPessoaFisica.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.mpPessoaFisica.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpPessoaFisica.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpPessoaFisica.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpPessoaFisica.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpPessoaFisica.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
			//
		}
    }

	// ====================
	// --- Find by Nome ...
	// ====================

	public void findByNome() {
		//
    	this.nomeFind = "";
    	
//    	MpAppUtil.PrintarLn("MpCadastroCliente.findByNome() = TRUE");
	}

    public List<String> completaFindByNome(String query) {
    	//
        List<String> results = new ArrayList<String>();
        
        List<MpPessoaFisica> mpPessoaFisicaListX = this.mpPessoaFisicas.byNomeList();
        
        for (MpPessoaFisica mpPessoaFisicaX : mpPessoaFisicaListX) {
        	if (mpPessoaFisicaX.getNome().toLowerCase().startsWith(query)) {
                results.add(mpPessoaFisicaX.getNome());
            }
        }
                // 
        return results;
    }
    
    public void onNomeSelecionado(SelectEvent event) {
    	//
    	MpFacesUtil.addInfoMessage("Nome Selecionado ( " + event.getObject().toString());
    	
    	MpPessoaFisica mpPessoaFisicaX = this.mpPessoaFisicas.porNome(event.getObject().toString());
    	if (null != mpPessoaFisicaX)
    		this.mpPessoaFisica = mpPessoaFisicaX;
    	//
    	this.nomeFind = "";
    }	

	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpPessoaFisica = this.mpPessoaFisicas.porNavegacao("mpFirst", " "); 
		if (null == this.mpPessoaFisica)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpPessoaFisica.getNome()) return;
		//
		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
		//
		this.mpPessoaFisica = this.mpPessoaFisicas.porNavegacao("mpPrev", mpPessoaFisica.getNome());
		if (null == this.mpPessoaFisica) {
			this.mpPessoaFisica = this.mpPessoaFisicaAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
		
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
		if (null == this.mpPessoaFisica.getId()) return;
		//
		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPessoaFisica.getId()) return;
		//
		try {
			this.mpPessoaFisicas.remover(mpPessoaFisica);
			
			MpFacesUtil.addInfoMessage("PessoaFisica... " + this.mpPessoaFisica.getNome()
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
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}

		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
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
		this.limparArquivo();
		
		this.mpPessoaFisica = this.mpPessoaFisicaAnt;		
		
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
		if (null == this.mpPessoaFisica.getNome()) return;
		//
		this.setMpPessoaFisicaAnt(this.mpPessoaFisica);
		//
		this.mpPessoaFisica = this.mpPessoaFisicas.porNavegacao("mpNext", mpPessoaFisica.getNome());
		if (null == this.mpPessoaFisica) {
			this.mpPessoaFisica = this.mpPessoaFisicaAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpPessoaFisica = this.mpPessoaFisicas.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpPessoaFisica)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPessoaFisica.getId()) return;

		try {
			this.setMpPessoaFisicaAnt(this.mpPessoaFisica);

			this.mpPessoaFisica = (MpPessoaFisica) this.mpPessoaFisica.clone();
			//
			this.mpPessoaFisica.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}

	public void mpHelp() {
		//
		String objetoHelp = this.getClass().getSimpleName();
		//
		this.mpObjetoHelp = mpObjetos.porCodigo(objetoHelp, mpSeguranca.capturaTenantId().trim());
		if (null == mpObjetoHelp) {
			this.mpObjetoHelp = new MpObjeto();
			objetoHelp = "null";
			
			MpFacesUtil.addInfoMessage("Receita Help... Error ( " + objetoHelp);
		}
	}
	
	// ---
	
	private void limpar() {
		//
		this.mpPessoaFisica = new MpPessoaFisica();
		
		this.mpPessoaFisica.setTenantId(mpSeguranca.capturaTenantId());
		this.mpPessoaFisica.setNome("");
		this.mpPessoaFisica.setEmail("");		
		//
		this.mpEnderecoLocal = new MpEnderecoLocal();

		this.mpEnderecoLocal.setCep("");
		this.mpEnderecoLocal.setLogradouro("");
		this.mpEnderecoLocal.setBairro("");
		this.mpEnderecoLocal.setComplemento("");
		this.mpEnderecoLocal.setNumero("");
		
		this.mpPessoaFisica.setMpEnderecoLocal(mpEnderecoLocal);
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
			
			this.mpPessoaFisica.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroPessoaFisicaBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpPessoaFisica.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpPessoaFisica.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpPessoaFisica.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoaFisica.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpPessoaFisica.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpPessoaFisica.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoaFisica.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpPessoaFisica.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoaFisica.getArquivoBD());
	}
	
	// ---
	
	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpPessoaFisica.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpPessoaFisica.getMpArquivoAcao().getDescricao();
	}
		
	// ---
	
	public boolean isEditando() { return this.mpPessoaFisica.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpPessoaFisica.getArquivoBD() != null && this.mpPessoaFisica.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpPessoaFisica.getArquivoBD()), "");
    	return imagemDsc;
    }

    public UploadedFile getArquivoUpload() { return arquivoUpload; }
    public void setArquivoUpload(UploadedFile arquivoUpload) { this.arquivoUpload = arquivoUpload; }
    
	public StreamedContent getArquivoContent() { return arquivoContent; }
    public void setArquivoContent(StreamedContent arquivoContent) { 
    														this.arquivoContent = arquivoContent; }

	public byte[] getArquivoBytes() { return arquivoBytes; }
    public void setArquivoBytes(byte[] arquivoBytes) { this.arquivoBytes = arquivoBytes; }
	public byte[] getArquivoBytesC() { return arquivoBytesC; }
    public void setArquivoBytesC(byte[] arquivoBytesC) { this.arquivoBytesC = arquivoBytesC; }
	
	public boolean isArquivoContent() { return getArquivoContent() != null; }

	// ---
	
	public MpPessoaFisica getMpPessoaFisica() { return mpPessoaFisica; }
	public void setMpPessoaFisica(MpPessoaFisica mpPessoaFisica) {	this.mpPessoaFisica = mpPessoaFisica; }
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public MpPessoaFisica getMpPessoaFisicaAnt() { return mpPessoaFisicaAnt; }
	public void setMpPessoaFisicaAnt(MpPessoaFisica mpPessoaFisicaAnt) {
		try {
			this.mpPessoaFisicaAnt = (MpPessoaFisica) this.mpPessoaFisica.clone();
			this.mpPessoaFisicaAnt.setId(this.mpPessoaFisica.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }

	public MpEstadoUF getMpOabUF() { return mpOabUF; }
	public void setMpOabUF(MpEstadoUF mpOabUF) { this.mpOabUF = mpOabUF; }
	public List<MpEstadoUF> getMpOabUFList() { return mpOabUFList; }

	public MpTabelaInternaSJ getMpBanco() { return mpBanco; }
	public void setMpBanco(MpTabelaInternaSJ mpBanco) { this.mpBanco = mpBanco; }
	public List<MpTabelaInternaSJ> getMpBancoList() { return mpBancoList; }
	
	// --- 
	
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	public List<MpArquivoAcao> getMpArquivoAcaoList() { return mpArquivoAcaoList; }

	public MpArquivoBD getMPArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }
	public List<MpArquivoBD> getMpArquivoBDList() { return mpArquivoBDList; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public Boolean getScIndCapturaFoto() { return scIndCapturaFoto; }
	public void setScIndCapturaFoto(Boolean scIndCapturaFoto) { this.scIndCapturaFoto = scIndCapturaFoto; }
	
	public String getNomeFind() { return nomeFind; }
	public void setNomeFind(String nomeFind) { this.nomeFind = nomeFind; }

}