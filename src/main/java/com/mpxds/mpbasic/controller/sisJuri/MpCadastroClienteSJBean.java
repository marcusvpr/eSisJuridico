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

import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.sisJuri.MpClienteSJService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroClienteSJBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpClienteSJs mpClienteSJs;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	@Inject
	private MpClienteSJService mpClienteSJService;

	// --- 
	
	private MpClienteSJ mpClienteSJ = new MpClienteSJ();
	private MpClienteSJ mpClienteSJAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;

	private String nomeFind = "";
		
	private String txtModoTela = "";
		
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;

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

	public MpCadastroClienteSJBean() {
		//
		if (null == this.mpClienteSJ)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpClienteSJ) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpClienteSJ.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpClienteSJAnt(this.mpClienteSJ);
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

		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
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
			this.mpClienteSJ.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpClienteSJ.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpClienteSJ.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpClienteSJ.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpClienteSJ.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";

		if (!this.mpClienteSJ.getCpfCnpj().isEmpty()) {
			String isCPFCNPJ = MpAppUtil.verificaCpf(this.mpClienteSJ.getCpfCnpj());
			if (isCPFCNPJ.equals("false"))
				msg = msg + "\n(CPF / CNPJ)";
		}
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		//
		this.mpClienteSJ = this.mpClienteSJService.salvar(this.mpClienteSJ);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Cliente... salvo com sucesso!");
	}
	
	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.mpClienteSJ.getMpEnderecoLocal().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroClienteSJBean.onCepWebService() - ( " +
//				this.mpClienteSJ.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.mpClienteSJ.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpClienteSJ.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpClienteSJ.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpClienteSJ.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpClienteSJ.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
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
        
        List<MpClienteSJ> mpCLienteSJListX = this.mpClienteSJs.byNomeList();
        
        for (MpClienteSJ mpClienteSJX : mpCLienteSJListX) {
        	if (mpClienteSJX.getNome().toLowerCase().startsWith(query)) {
                results.add(mpClienteSJX.getNome());
            }
        }
        // 
        return results;
    }
    
    public void onNomeSelecionado(SelectEvent event) {
    	//
    	MpFacesUtil.addInfoMessage("Nome Selecionado ( " + event.getObject().toString());
    	
    	MpClienteSJ mpClienteSJX = this.mpClienteSJs.porNome(event.getObject().toString());
    	if (null != mpClienteSJX)
    		this.mpClienteSJ = mpClienteSJX;
    	//
    	this.nomeFind = "";
    }	

	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpClienteSJ = this.mpClienteSJs.porNavegacao("mpFirst", " "); 
		if (null == this.mpClienteSJ)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpClienteSJ.getNome()) return;
		//
		this.setMpClienteSJAnt(this.mpClienteSJ);
		//
		this.mpClienteSJ = this.mpClienteSJs.porNavegacao("mpPrev", mpClienteSJ.getNome());
		if (null == this.mpClienteSJ) {
			this.mpClienteSJ = this.mpClienteSJAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpClienteSJAnt(this.mpClienteSJ);
		
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
		if (null == this.mpClienteSJ.getId()) return;
		//
		this.setMpClienteSJAnt(this.mpClienteSJ);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpClienteSJ.getId()) return;
		//
		try {
			this.mpClienteSJs.remover(mpClienteSJ);
			
			MpFacesUtil.addInfoMessage("Cliente... " + this.mpClienteSJ.getNome()
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

		this.setMpClienteSJAnt(this.mpClienteSJ);
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
		
		this.mpClienteSJ = this.mpClienteSJAnt;		
		
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
		if (null == this.mpClienteSJ.getNome()) return;
		//
		this.setMpClienteSJAnt(this.mpClienteSJ);
		//
		this.mpClienteSJ = this.mpClienteSJs.porNavegacao("mpNext", mpClienteSJ.getNome());
		if (null == this.mpClienteSJ) {
			this.mpClienteSJ = this.mpClienteSJAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpClienteSJ = this.mpClienteSJs.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpClienteSJ)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpClienteSJ.getId()) return;

		try {
			this.setMpClienteSJAnt(this.mpClienteSJ);

			this.mpClienteSJ = (MpClienteSJ) this.mpClienteSJ.clone();
			//
			this.mpClienteSJ.setId(null);
			
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
		this.mpClienteSJ = new MpClienteSJ();
		
		this.mpClienteSJ.setTenantId(mpSeguranca.capturaTenantId());
		this.mpClienteSJ.setNome("");
		this.mpClienteSJ.setEmail("");		
		//
		this.mpEnderecoLocal = new MpEnderecoLocal();

		this.mpEnderecoLocal.setCep("");
		this.mpEnderecoLocal.setLogradouro("");
		this.mpEnderecoLocal.setBairro("");
		this.mpEnderecoLocal.setComplemento("");
		this.mpEnderecoLocal.setNumero("");
		
		this.mpClienteSJ.setMpEnderecoLocal(mpEnderecoLocal);
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
			
			this.mpClienteSJ.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroClienteSJBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpClienteSJ.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpClienteSJ.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpClienteSJ.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteSJ.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpClienteSJ.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpClienteSJ.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteSJ.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpClienteSJ.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteSJ.getArquivoBD());
	}
	
	// ---
	
	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpClienteSJ.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpClienteSJ.getMpArquivoAcao().getDescricao();
	}
		
	// ---
	
	public boolean isEditando() { return this.mpClienteSJ.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpClienteSJ.getArquivoBD() != null && this.mpClienteSJ.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpClienteSJ.getArquivoBD()), "");
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
	
	public MpClienteSJ getMpClienteSJ() { return mpClienteSJ; }
	public void setMpClienteSJ(MpClienteSJ mpClienteSJ) {	this.mpClienteSJ = mpClienteSJ; }
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public MpClienteSJ getMpClienteSJAnt() { return mpClienteSJAnt; }
	public void setMpClienteSJAnt(MpClienteSJ mpClienteSJAnt) {
		try {
			this.mpClienteSJAnt = (MpClienteSJ) this.mpClienteSJ.clone();
			this.mpClienteSJAnt.setId(this.mpClienteSJ.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }
	
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