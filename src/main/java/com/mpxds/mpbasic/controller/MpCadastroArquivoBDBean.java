package com.mpxds.mpbasic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
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

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpArquivoBDService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.MpAppUtil;
//import com.mpxds.mpbasic.util.MpFotoCameraBean;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
@ManagedBean(name = "mpCadastroArquivoBDBean")
public class MpCadastroArquivoBDBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpArquivoBDs mpArquivoBDs;

	@Inject
	private MpTabelaInternas mpTabelaInternas;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpArquivoBDService mpArquivoBDService;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	 
	private MpTabelaInterna mpGrupo; // tab_0007 
	private List<MpTabelaInterna> mpGrupoList  = new ArrayList<MpTabelaInterna>();
	
	private MpArquivoAcao mpArquivoAcao;
	private List<MpArquivoAcao> mpArquivoAcaoList  = new ArrayList<MpArquivoAcao>();
	
	private MpArquivoBD mpArquivoBD = new MpArquivoBD();
	private MpArquivoBD mpArquivoBDAnt;
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

	public MpCadastroArquivoBDBean() {
		//
		if (null == this.mpArquivoBD)
			this.limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpArquivoBD) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		} 
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpArquivoBD.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}

		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//
		this.mpGrupoList = this.mpTabelaInternas.mpNumeroList(MpTipoTabelaInterna.TAB_0007);
		
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
		this.mpArquivoAcaoList.add(MpArquivoAcao.CARREGAR);
		if (this.scIndCapturaFoto)
			this.mpArquivoAcaoList.add(MpArquivoAcao.CAPTURAR);
	}
	
	public void salvar() {
		//
		// =========================================
		// Trata MpArquivoBD (Arquivo Banco Dados !)
		// =========================================
		if (this.isArquivoContent()) 
			this.mpArquivoBD.setArquivo(this.getArquivoBytes());
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpArquivoBD.setMpArquivoAcao(mpArquivoAcao);
		// ============================================
			
		this.mpArquivoBD = this.mpArquivoBDService.salvar(this.mpArquivoBD);			
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;
				
		MpFacesUtil.addInfoMessage("ArquivoBD... salvo com sucesso!");
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpArquivoBD.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpArquivoBD.getMpArquivoAcao().getDescricao();		
	}

	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpArquivoBD = this.mpArquivoBDs.porNavegacao("mpFirst", " "); 
		if (null == this.mpArquivoBD)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpArquivoBD.getDescricao()) return;

		this.limparArquivo();
		//
		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//
		this.mpArquivoBD = this.mpArquivoBDs.porNavegacao("mpPrev", mpArquivoBD.getDescricao());
		if (null == this.mpArquivoBD) {
			this.mpArquivoBD = this.mpArquivoBDAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//		
		this.limpar();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		if (null == this.mpArquivoBD.getId()) return;
		//
		this.limparArquivo();
		
		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpDelete() {
		//
		if (null == this.mpArquivoBD.getId()) return;
		//
		try {
			this.mpArquivoBDs.remover(mpArquivoBD);
			
			MpFacesUtil.addInfoMessage("ArquivoBD... " + this.mpArquivoBD.getDescricao()
																	+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}
		
		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpArquivoBD = this.mpArquivoBDAnt;

		this.trataExibicaoArquivo();
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpArquivoBD.getDescricao()) return;
		//
		this.setMpArquivoBDAnt(this.mpArquivoBD);
		//
		this.mpArquivoBD = this.mpArquivoBDs.porNavegacao("mpNext", mpArquivoBD.getDescricao());
		if (null == this.mpArquivoBD) {
			this.mpArquivoBD = this.mpArquivoBDAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpArquivoBD = this.mpArquivoBDs.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpArquivoBD) 
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}

	public void mpClone() {
		//
		if (null == this.mpArquivoBD.getId()) return;
		
		try {
			this.mpArquivoBDAnt = this.mpArquivoBD;
			this.mpArquivoBD = (MpArquivoBD) this.mpArquivoBD.clone();
			//
			this.mpArquivoBD.setId(null);
			
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
	
	// ---

	private void limpar() {
		//
		this.mpArquivoBD = new MpArquivoBD();
		
		this.mpArquivoBD.setDescricao("");
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
			
			this.mpArquivoBD.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroArquivoBDBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpArquivoBD.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpArquivoBD.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpArquivoBD.getArquivo())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpArquivoBD.getArquivo());
			//
			return;
		}
		//
		if 	(null == this.mpArquivoBD.getArquivo())
			assert(true); // nop
		else
			this.setArquivoBytes(this.mpArquivoBD.getArquivo());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpArquivoBD.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpArquivoBD.getArquivo() != null && this.mpArquivoBD.getArquivo().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
															this.mpArquivoBD.getArquivo()), "");
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
		
	public MpArquivoBD getMpArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }

	public MpArquivoBD getMpArquivoBDAnt() { return mpArquivoBDAnt; }
	public void setMpArquivoBDAnt(MpArquivoBD mpArquivoBDAnt) {
		try {
			this.mpArquivoBDAnt = (MpArquivoBD) this.mpArquivoBD.clone();
			this.mpArquivoBDAnt.setId(this.mpArquivoBD.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpTabelaInterna getMpGrupo() { return mpGrupo; }
	public void setMpGrupo(MpTabelaInterna mpGrupo) { this.mpGrupo = mpGrupo; }
	public List<MpTabelaInterna> getMpGrupoList() { return mpGrupoList; }

	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	public List<MpArquivoAcao> getMpArquivoAcaoList() { return mpArquivoAcaoList; }

	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) {	this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
}