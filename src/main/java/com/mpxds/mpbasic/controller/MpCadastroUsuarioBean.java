package com.mpxds.mpbasic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.CaptureEvent;

import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpUsuarioService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
@ManagedBean(name = "mpCadastroUsuarioBean")
public class MpCadastroUsuarioBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpGrupos mpGrupos;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpUsuarioService mpUsuarioService;

	@Inject
	private MpAlertaService mpAlertaService;

	// ---
	
	private MpUsuario mpUsuario = new MpUsuario();
	private MpUsuario mpUsuarioAnt;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;

	private String nomeFind = "";
	
	private String txtModoTela = "";
		
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList = new ArrayList<MpStatus>();
		
	private MpGrupo mpGrupo;
	private List<MpGrupo> mpGrupoList = new ArrayList<MpGrupo>();
	
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

	public MpCadastroUsuarioBean() {
		//
		if (null == this.mpUsuario)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpUsuario) {
			this.limpar();
			// Posiciona no usuário logado !!!
			this.mpUsuario = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario();
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpUsuario.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpUsuarioAnt(this.mpUsuario);
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
		this.indEditavelNav = this.mpUsuario.getIndBarraNavegacao();
		
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpGrupoList = mpGrupos.mpGrupoList();
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
			this.mpUsuario.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpUsuario.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpUsuario.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpUsuario.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpUsuario.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		// Trata Alerta ---------------------------
		MpAlerta mpAlertaX = this.mpAlertaService.salvar(this.mpUsuario.getMpAlerta());
		
//		System.out.println("MpCadastroUsuario.grava() - (MpAlertaX = " +
//																mpAlertaX.getConfiguracao());
		//
		this.mpUsuario.setMpAlerta(mpAlertaX);
		// Trata Alerta ---------------------------
		
		this.mpUsuario = this.mpUsuarioService.salvar(this.mpUsuario);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;
		//
		if (this.mpUsuario.equals(this.mpSeguranca.getMpUsuarioLogado().getMpUsuario()))
			this.mpSeguranca.getMpUsuarioLogado().setMpUsuario(this.mpUsuario);
		//
		MpFacesUtil.addInfoMessage("Usuário... salvo com sucesso!");
	}

	// ====================
	// --- Find by Nome ...
	// ====================

	public void findByNome() {
		//
    	this.nomeFind = "";
	}

    public List<String> completaFindByNome(String query) {
    	//
        List<String> results = new ArrayList<String>();
        
        List<MpUsuario> mpUsuarioListX = this.mpUsuarios.mpUsuarioAtivos();
        
        for (MpUsuario mpUsuarioX : mpUsuarioListX) {
        	if (mpUsuarioX.getLogin().toLowerCase().startsWith(query)) {
                results.add(mpUsuarioX.getLogin());
            }
        }
                // 
        return results;
    }
    
    public void onNomeSelecionado(SelectEvent event) {
    	//
    	MpFacesUtil.addInfoMessage("Login Selecionado ( " + event.getObject().toString());
    	
    	MpUsuario mpUsuarioX = this.mpUsuarios.porLogin(event.getObject().toString());
    	if (null != mpUsuarioX)
    		this.mpUsuario = mpUsuarioX;
    	//
    	this.nomeFind = "";
    }	
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpUsuario = this.mpUsuarios.porNavegacao("mpFirst", " "); 
		if (null == this.mpUsuario)
			this.limpar();
		else
			this.trataExibicaoArquivo();			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpUsuario.getNome()) return;
		//
		this.setMpUsuarioAnt(this.mpUsuario);
		//
		this.mpUsuario = this.mpUsuarios.porNavegacao("mpPrev", mpUsuario.getNome());
		if (null == this.mpUsuario) {
			this.mpUsuario = this.mpUsuarioAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();			
	}

	public void mpNew() {
		//
		this.setMpUsuarioAnt(this.mpUsuario);
		
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
		if (null == this.mpUsuario.getId()) return;
		//
		this.setMpUsuarioAnt(this.mpUsuario);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpUsuario.getId())	return;
		//
		try {
			this.mpUsuarios.remover(mpUsuario);
			
			MpFacesUtil.addInfoMessage("Usuário... " + this.mpUsuario.getNome()
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

		this.setMpUsuarioAnt(this.mpUsuario);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpUsuario = this.mpUsuarioAnt;
		
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
		if (null == this.mpUsuario.getNome()) return;
		//
		this.setMpUsuarioAnt(this.mpUsuario);
		//
		this.mpUsuario = this.mpUsuarios.porNavegacao("mpNext", mpUsuario.getNome());
		if (null == this.mpUsuario) {
			this.mpUsuario = this.mpUsuarioAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpUsuario = this.mpUsuarios.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpUsuario)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpUsuario.getId()) return;

		try {
			this.setMpUsuarioAnt(this.mpUsuario);

			this.mpUsuario = (MpUsuario) this.mpUsuario.clone();
			//
			this.mpUsuario.setId(null);
			
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
		this.mpUsuario = new MpUsuario();

		this.mpUsuario.setNome("");
		this.mpUsuario.setMpGrupos(new ArrayList<MpGrupo>());
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
			
			this.mpUsuario.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroUsuarioBean.aoCapturarArquivo() - NULL");
			return;
		}
		
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpUsuario.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpUsuario.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpUsuario.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpUsuario.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpUsuario.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpUsuario.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpUsuario.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpUsuario.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpUsuario.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpUsuario.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpUsuario.getArquivoBD() != null && this.mpUsuario.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpUsuario.getArquivoBD()), "");
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

	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpUsuario.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpUsuario.getMpArquivoAcao().getDescricao();
	}
	
	// ---
	
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }

	public MpUsuario getMpUsuarioAnt() { return mpUsuarioAnt; }
	public void setMpUsuarioAnt(MpUsuario mpUsuarioAnt) {
		//
		try {
			this.mpUsuarioAnt = (MpUsuario) this.mpUsuario.clone();
			this.mpUsuarioAnt.setId(this.mpUsuario.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpGrupo getMpGrupo() { return mpGrupo; }
	public void setMpGrupo(MpGrupo mpGrupo) { this.mpGrupo = mpGrupo; }
	public List<MpGrupo> getMpGrupoList() { return mpGrupoList; }

	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }

	// --- 
	
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	public List<MpArquivoAcao> getMpArquivoAcaoList() { return mpArquivoAcaoList; }

	public MpArquivoBD getMpArquivoBD() { return mpArquivoBD; }
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