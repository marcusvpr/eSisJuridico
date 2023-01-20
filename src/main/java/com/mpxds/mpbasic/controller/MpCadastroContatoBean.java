package com.mpxds.mpbasic.controller;

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
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpIndicadorIE;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.MpContatos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpContatoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
//import com.mpxds.mpbasic.util.MpFotoCameraBean;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroContatoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpContatos mpContatos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpContatoService mpContatoService;

	// --- 
	
	private MpContato mpContato = new MpContato();
	private MpContato mpContatoAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
		
	private String txtModoTela = "";
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;

	private MpEnderecoLocal mpEnderecoLocal = new MpEnderecoLocal();
	
	private MpIndicadorIE mpIndicadorIE;
	private List<MpIndicadorIE> mpIndicadorIEList;

	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList;

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

	public MpCadastroContatoBean() {
		//
		if (null == this.mpContato)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpContato) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpContato.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpContatoAnt(this.mpContato);
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
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
		this.mpIndicadorIEList = Arrays.asList(MpIndicadorIE.values());
		this.mpSexoList = Arrays.asList(MpSexo.values());
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
			this.mpContato.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpContato.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpContato.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpContato.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpContato.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";
		// if (this.mpContato.getEmail().isEmpty()) msg = msg + "\n(E-mail)";
		
		if (!this.mpContato.getCpfCnpj().isEmpty()) { // Vrf. pelo tamanho???
			String isCPF = MpAppUtil.verificaCpf(this.mpContato.getCpfCnpj());
			if (isCPF.equals("false"))
				msg = msg + "\n(Doc.Rec.Federal)";
		}
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		//
		// Trata duplicidade do codigo !
		// if (this.checkDuplicidade()) {
		// MpFacesUtil.addInfoMessage("Código já existe... favor verificar! (" +
		// this.mpContato.getCodigo());
		// return;
		// }
		//
		this.mpContato = this.mpContatoService.salvar(this.mpContato);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Contato... salvo com sucesso!");
	}
	
	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.mpContato.getMpEnderecoLocal().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroContatoBean.onCepWebService() - ( " +
//				this.mpContato.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.mpContato.getMpEnderecoLocal().setLogradouro(mpCepXML.getLogradouro());
			this.mpContato.getMpEnderecoLocal().setComplemento(mpCepXML.getComplemento());
			this.mpContato.getMpEnderecoLocal().setBairro(mpCepXML.getBairro());
			this.mpContato.getMpEnderecoLocal().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.mpContato.getMpEnderecoLocal().setMpEstadoUF(mpEstadoUF);
			//
		}
    }

//	public Boolean checkDuplicidade() {
//		//
//        if (this.mpContato.getId() == null) {
//        	if (this.mpContatos.countByCodigo(this.mpContato.getCodigo()) > 0)
//        		return true;
//        } else { 
//			if (!mpContatoAnt.getCodigo().equals(this.mpContato.getCodigo()))
//	        	if (mpContatos.countByCodigo(this.mpContato.getCodigo()) > 0)
//					return true;
//        }
//		//
//		return false;		
//	}

	// ---
	
	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpContato.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpContato.getMpArquivoAcao().getDescricao();
	}
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpContato = this.mpContatos.porNavegacao("mpFirst", " "); 
		if (null == this.mpContato)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpContato.getNomeRazaoSocial()) return;
		//
		this.setMpContatoAnt(this.mpContato);
		//
		this.mpContato = this.mpContatos.porNavegacao("mpPrev", mpContato.getNomeRazaoSocial());
		if (null == this.mpContato) {
			this.mpContato = this.mpContatoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpContatoAnt(this.mpContato);
		
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
		if (null == this.mpContato.getId()) return;
		//
		this.setMpContatoAnt(this.mpContato);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpContato.getId()) return;
		//
		try {
			this.mpContatos.remover(mpContato);
			
			MpFacesUtil.addInfoMessage("Contato... " + this.mpContato.getNomeRazaoSocial()
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

		this.setMpContatoAnt(this.mpContato);
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
		
		this.mpContato = this.mpContatoAnt;		
		
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
		if (null == this.mpContato.getNomeRazaoSocial()) return;
		//
		this.setMpContatoAnt(this.mpContato);
		//
		this.mpContato = this.mpContatos.porNavegacao("mpNext", mpContato.getNomeRazaoSocial());
		if (null == this.mpContato) {
			this.mpContato = this.mpContatoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpContato = this.mpContatos.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpContato)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpContato.getId()) return;

		try {
			this.setMpContatoAnt(this.mpContato);

			this.mpContato = (MpContato) this.mpContato.clone();
			//
			this.mpContato.setId(null);
			
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
		this.mpContato = new MpContato();
		
		this.mpContato.setTenantId(mpSeguranca.capturaTenantId());
		this.mpContato.setNomeRazaoSocial("");
		this.mpContato.setEmail("");		
		//
		this.mpEnderecoLocal = new MpEnderecoLocal();

		this.mpEnderecoLocal.setCep("");
		this.mpEnderecoLocal.setLogradouro("");
		this.mpEnderecoLocal.setBairro("");
		this.mpEnderecoLocal.setComplemento("");
		this.mpEnderecoLocal.setNumero("");
		
		this.mpContato.setMpEnderecoLocal(mpEnderecoLocal);
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
			
			this.mpContato.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroContatoBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpContato.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpContato.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpContato.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpContato.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpContato.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpContato.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpContato.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpContato.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpContato.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpContato.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpContato.getArquivoBD() != null && this.mpContato.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpContato.getArquivoBD()), "");
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
	
	public MpContato getMpContato() { return mpContato; }
	public void setMpContato(MpContato mpContato) {	this.mpContato = mpContato; }
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public MpContato getMpContatoAnt() { return mpContatoAnt; }
	public void setMpContatoAnt(MpContato mpContatoAnt) {
		try {
			this.mpContatoAnt = (MpContato) this.mpContato.clone();
			this.mpContatoAnt.setId(this.mpContato.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }

	public MpIndicadorIE getMpIndicadorIE() {	return mpIndicadorIE; }
	public void setMpIndicadorIE(MpIndicadorIE mpIndicadorIE) { this.mpIndicadorIE = mpIndicadorIE; }
	public List<MpIndicadorIE> getMpIndicadorIEList() { return mpIndicadorIEList; }

	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }
	
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