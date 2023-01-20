package com.mpxds.mpbasic.controller;

//import java.io.FileInputStream;
//import java.io.InputStream;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
//import javax.imageio.stream.FileImageOutputStream;
//import javax.servlet.ServletContext;
//import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
//import javax.faces.FacesException;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpProfissaoPessoa;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;
//import com.mpxds.mpbasic.model.enums.MpTipoProduto;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.repository.MpTurnos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpPessoaService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroPessoaBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpPessoas mpPessoas;

	@Inject
	private MpTurnos mpTurnos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	@Inject
	private MpTabelaInternas mpTabelaInternas;

	@Inject
	private MpPessoaService mpPessoaService;
	
	// ---

	private MpPessoa mpPessoa = new MpPessoa();
	private MpPessoa mpPessoaAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private String usuarioGrupos = "";
	//
	private MpTipoPessoa mpTipoPessoa;
	private List<MpTipoPessoa> mpTipoPessoaList  = new ArrayList<MpTipoPessoa>();
	
	private MpProfissaoPessoa mpProfissaoPessoa;
	private List<MpProfissaoPessoa> mpProfissaoPessoaList = new ArrayList<MpProfissaoPessoa>();
	
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList = new ArrayList<MpStatus>();
	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList = new ArrayList<MpSexo>();
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList = new ArrayList<MpEstadoUF>();
	
	private MpTurno mpTurno;
	private List<MpTurno> mpTurnoList;
	 
	private MpTabelaInterna mpProfissao; // tab_0011
	private List<MpTabelaInterna> mpProfissaoList  = new ArrayList<MpTabelaInterna>();

	private MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();

	// ---
	
	private MpArquivoAcao mpArquivoAcao;
	private List<MpArquivoAcao> mpArquivoAcaoList = new ArrayList<MpArquivoAcao>();
	
	private MpArquivoBD mpArquivoBD;
	private List<MpArquivoBD> mpArquivoBDList = new ArrayList<MpArquivoBD>();	
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

	public MpCadastroPessoaBean() {
		//
		if (null == this.mpPessoa)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpPessoa) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPessoa.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPessoaAnt(this.mpPessoa);
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
		//
		// Verifica filtro para controle de Estoque ...
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLoginGrupo();
		else
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getGruposNome();
		//
		for (MpProfissaoPessoa mpProfissaoPessoaX : Arrays.asList(MpProfissaoPessoa.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpProfissaoPessoaX.getIndControleEstoque())
					this.mpProfissaoPessoaList.add(mpProfissaoPessoaX);
			} else
				if (!mpProfissaoPessoaX.getIndControleEstoque())
					this.mpProfissaoPessoaList.add(mpProfissaoPessoaX);
		}
		
		this.mpTipoPessoaList = Arrays.asList(MpTipoPessoa.values());		
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpSexoList = Arrays.asList(MpSexo.values());
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
		this.mpTurnoList = mpTurnos.mpTurnoList();
		this.mpProfissaoList = this.mpTabelaInternas.mpNumeroList(MpTipoTabelaInterna.TAB_0011);
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
			this.mpPessoa.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpPessoa.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpPessoa.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpPessoa.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpPessoa.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";
		if (this.mpPessoa.getEmail().isEmpty())
			msg = msg + "\n(E-mail)";

		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		// Trata duplicidade do codigo !
		// if (this.checkDuplicidade()) {
		// MpFacesUtil.addInfoMessage("Código já existe... favor verificar! (" +
		// this.mpPessoa.getCodigo());
		// return;
		// }
		//
		this.mpPessoa = this.mpPessoaService.salvar(this.mpPessoa);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Pessoa... salvo com sucesso!");
	}
	
	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.mpPessoa.getMpEnderecoLocal().
																						getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroPessoaBean.onCepWebService() - ( " +
//				this.mpPessoa.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.mpPessoa.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpPessoa.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpPessoa.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpPessoa.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpPessoa.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
			//
		}
    }
	
//	public Boolean checkDuplicidade() {
//		//
//        if (this.mpPessoa.getId() == null) {
//        	if (this.mpPessoas.countByCodigo(this.mpPessoa.getCodigo()) > 0)
//        		return true;
//        } else { 
//			if (!mpPessoaAnt.getCodigo().equals(this.mpPessoa.getCodigo()))
//	        	if (mpPessoas.countByCodigo(this.mpPessoa.getCodigo()) > 0)
//					return true;
//        }
//		//
//		return false;		
//	}	
	
	// -------- Trata FOTO ...
	
	
//    private String getRandomImageName() {
//        int i = (int) (Math.random() * 10000000);
//         
//        return String.valueOf(i);
//    }
// 	
//    public void oncapture(CaptureEvent captureEvent) {
//    	//
////    	System.out.println("MpCadastroPessoaBean.oncapture() - Entrou 000");
//    	
//        this.filename = getRandomImageName();
//        
//        byte[] data = captureEvent.getData();
//         
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().
//        															getExternalContext().getContext();
//        
//        String newFileName = servletContext.getRealPath("") + File.separator + "resources" +
//        					File.separator + "fotosPessoas" + File.separator + this.filename + ".jpeg";
//        //
//        FileImageOutputStream imageOutput;
//        try {
//            imageOutput = new FileImageOutputStream(new File(newFileName));
//            imageOutput.write(data, 0, data.length);
//            //
//            imageOutput.close();
//        }
//        catch(IOException e) {
//            throw new FacesException("Erro na gravação da imagem capturada!", e);
//        }
//        //
//    }	
//
//    public byte[] mpRead(File file) throws IOException {
//        // Get the size of the file
//        long length = file.length();
//
//        // You cannot create an array using a long type.
//        // It needs to be an int type.
//        // Before converting to an int type, check
//        // to ensure that file is not larger than Integer.MAX_VALUE.
//        if (length > Integer.MAX_VALUE) {
//            // File is too large
//            throw new FacesException("Erro(FileLarge) na gravação da imagem capturada!");
//        }
//
//        // Create the byte array to hold the data
//        byte[] bytes = new byte[(int)length];
//
//        // Read in the bytes
//        int offset = 0;
//        int numRead = 0;
//
//        InputStream is = new FileInputStream(file);
//        try {
//            while (offset < bytes.length
//               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
//                offset += numRead;
//            }
//        } finally {
//            is.close();
//        }
//
//        // Ensure all the bytes have been read in
//        if (offset < bytes.length) {
//            throw new IOException("Could not completely read file "+file.getName());
//        }
//        return bytes;
//    }    
    

	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpPessoa.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpPessoa.getMpArquivoAcao().getDescricao();
	}
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		this.mpPessoa = this.mpPessoas.porNavegacao("mpFirst", " "); 
		if (null == this.mpPessoa)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}

	public void mpPrev() {
		//
		if (null == this.mpPessoa.getNome()) return;
		//
		this.setMpPessoaAnt(this.mpPessoa);
		//
		this.mpPessoa = this.mpPessoas.porNavegacao("mpPrev", mpPessoa.getNome());
		if (null == this.mpPessoa) {
			this.mpPessoa = this.mpPessoaAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpPessoaAnt(this.mpPessoa);
		
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
		if (null == this.mpPessoa.getId()) return;
		//
		this.setMpPessoaAnt(this.mpPessoa);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPessoa.getId()) return;
		//
		try {
			this.mpPessoas.remover(mpPessoa);
			
			MpFacesUtil.addInfoMessage("Pessoa... " + this.mpPessoa.getNome()
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
		
		this.setMpPessoaAnt(this.mpPessoa);
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
		this.mpPessoa = this.mpPessoaAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpPessoa.getNome()) return;
		//
		this.setMpPessoaAnt(this.mpPessoa);
		//
		this.mpPessoa = this.mpPessoas.porNavegacao("mpNext", 
				  													  mpPessoa.getNome());
		if (null == this.mpPessoa) {
			this.mpPessoa = this.mpPessoaAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpPessoa = this.mpPessoas.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpPessoa)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPessoa.getId()) return;

		try {
			this.setMpPessoaAnt(this.mpPessoa);
			
			this.mpPessoa = (MpPessoa) this.mpPessoa.clone();
			//
			this.mpPessoa.setId(null);
			
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
	
	private void limpar() {
		//
		this.mpPessoa = new MpPessoa();
		
		this.mpPessoa.setTenantId(mpSeguranca.capturaTenantId());
		this.mpPessoa.setNome("");
		this.mpPessoa.setEmail("");
		//
		this.mpEnderecoLocal = new MpEnderecoLocal();
		this.mpEnderecoLocal.setCep("");
		this.mpEnderecoLocal.setLogradouro("");
		this.mpEnderecoLocal.setBairro("");
		this.mpEnderecoLocal.setComplemento("");
		this.mpEnderecoLocal.setNumero("");
		
		this.mpPessoa.setMpEnderecoLocal(mpEnderecoLocal);
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
			
			this.mpPessoa.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroPessoaBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpPessoa.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpPessoa.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpPessoa.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoa.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpPessoa.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpPessoa.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoa.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpPessoa.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPessoa.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpPessoa.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpPessoa.getArquivoBD() != null && this.mpPessoa.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpPessoa.getArquivoBD()), "");
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
	
	public MpPessoa getMpPessoa() { return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }

	public MpPessoa getMpPessoaAnt() { return mpPessoaAnt; }
	public void setMpPessoaAnt(MpPessoa mpPessoaAnt) { 
		//
		try {
			this.mpPessoaAnt = (MpPessoa) this.mpPessoa.clone();
			this.mpPessoaAnt.setId(this.mpPessoa.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpTipoPessoa getMpTipoPessoa() { return mpTipoPessoa; }
	public void setMpTipoPessoa(MpTipoPessoa mpTipoPessoa) { this.mpTipoPessoa = mpTipoPessoa; }
	public List<MpTipoPessoa> getMpTipoPessoaList() { return mpTipoPessoaList; 	}

	public MpProfissaoPessoa getMpProfissaoPessoa() { return mpProfissaoPessoa; }
	public void setMpProfissaoPessoa(MpProfissaoPessoa mpProfissaoPessoa) {
												this.mpProfissaoPessoa = mpProfissaoPessoa;	}
	public List<MpProfissaoPessoa> getMpProfissaoPessoaList() {	return mpProfissaoPessoaList; }

	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF;  }
	public void setMpUf(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }

	public MpTurno getMpTurno() { return mpTurno; }
	public void setMpTurno(MpTurno mpTurno) { this.mpTurno = mpTurno; }
	public List<MpTurno> getMpTurnoList() { return mpTurnoList; }

	public MpTabelaInterna getMpProfissao() { return mpProfissao; }
	public void setMpProfissao(MpTabelaInterna mpProfissao) { this.mpProfissao = mpProfissao; }
	public List<MpTabelaInterna> getMpProfissaoList() { return mpProfissaoList; }
	
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
	public void setScIndCapturaFoto(Boolean scIndCapturaFoto) {
															this.scIndCapturaFoto = scIndCapturaFoto; }
	
}