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

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpPessoaPaciente;
import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpCor;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpPessoaPacientes;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.MpPacientes;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpPacienteService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroPacienteBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpPacientes mpPacientes;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpPessoaPacientes mpPessoaPacientes;

	@Inject
	private MpPessoas mpPessoas;

	@Inject
	private MpGrupos mpGrupos;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpPacienteService mpPacienteService;

	// ---
	
	private MpPaciente mpPaciente = new MpPaciente();
	private MpPaciente mpPacienteAnt;
	
	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelPessoa = false;

	private String txtModoTela = "";
	private String txtModoPessoaPacienteDialog = "";

	private MpPessoaPaciente mpPessoaPaciente = new MpPessoaPaciente();
	private List<MpPessoaPaciente> mpPessoaPacienteExcluidoList = 
													new ArrayList<MpPessoaPaciente>();	
	
	private MpPessoa mpPessoa;
	private List<MpPessoa> mpPessoaList = new ArrayList<MpPessoa>();
	
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList = new ArrayList<MpStatus>();
		
	private MpGrupo mpGrupo;
	private List<MpGrupo> mpGrupoList = new ArrayList<MpGrupo>();
	
	private MpCor mpCor;
	private List<MpCor> mpCorList = new ArrayList<MpCor>();
	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList = new ArrayList<MpSexo>();

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

	public MpCadastroPacienteBean() {
		//
		if (null == this.mpPaciente)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpPaciente) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpPaciente.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpPacienteAnt(this.mpPaciente);
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
			//
			if (this.scIndCapturaFoto) 
				this.scIndCapturaFoto = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndCapturaFoto();
		}
		// ---
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.mpPessoaList = mpPessoas.porPessoaAtivoList();
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpGrupoList = mpGrupos.mpGrupoList();
		this.mpCorList = Arrays.asList(MpCor.values());
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
			this.mpPaciente.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpPaciente.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpPaciente.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpPaciente.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpPaciente.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		this.mpPaciente = this.mpPacienteService.salvar(this.mpPaciente);
		//
		if (this.mpPessoaPacienteExcluidoList.size() > 0) {
			for (MpPessoaPaciente mpPessoaPaciente : this.mpPessoaPacienteExcluidoList) {
				//
				if (null == mpPessoaPaciente.getId())
					continue;

				this.mpPessoaPacientes.remover(mpPessoaPaciente);
			}
		}
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Paciente... salvo com sucesso!");
	}

	public void alterarMpPessoaPaciente() {
		//
		this.txtModoPessoaPacienteDialog = "Edição";
		
		this.indEditavelPessoa = true;
	}			
	
//	public void adicionarMpPessoaPaciente() {
//		//
//		if (this.mpPessoaPaciente != null) {
//			this.mpPaciente.getMpPessoaPacientes().add(this.mpPessoaPaciente);
//			
//			this.mpPessoaPaciente.setMpPaciente(this.mpPaciente);
//		}
//	}
//
//	public void removerMpPessoaPaciente() {
//		//
//		if (this.mpPessoaPaciente != null)
//			this.mpPaciente.getMpPessoaPacientes().remove(this.mpPessoaPaciente);
//	}			
	
	public void adicionarMpPessoaPacienteX() {
		//
		this.txtModoPessoaPacienteDialog = "Novo";

		if (this.mpPessoaPaciente != null) {
			this.mpPaciente.getMpPessoaPacientes().add(this.mpPessoaPaciente);
		
			this.mpPessoaPaciente.setTenantId(mpSeguranca.capturaTenantId());
			this.mpPessoaPaciente.setMpPaciente(this.mpPaciente);
		}
		//
		this.indEditavelPessoa = true;
	}

	public void removerMpPessoaPacienteX() {
		//
		try {
			this.mpPaciente.getMpPessoaPacientes().remove(this.mpPessoaPaciente);
			
			this.mpPessoaPacienteExcluidoList.add(this.mpPessoaPaciente);
			
			MpFacesUtil.addInfoMessage("PessoaPaciente " +
											this.mpPessoaPaciente.getMpPessoa().getNome()
																+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpPessoaPaciente() {
		//
		this.indEditavelPessoa = false;
		
		this.mpPessoaPaciente = new MpPessoaPaciente();
	}			
		
	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpPaciente.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpPaciente.getMpArquivoAcao().getDescricao();
	}
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpPaciente = this.mpPacientes.porNavegacao("mpFirst", " "); 
		if (null == this.mpPaciente)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}

	public void mpPrev() {
		//
		if (null == this.mpPaciente.getNome()) return;
		//
		this.setMpPacienteAnt(this.mpPaciente);
		//
		this.mpPaciente = this.mpPacientes.porNavegacao("mpPrev", mpPaciente.getNome());
		if (null == this.mpPaciente) {
			this.mpPaciente = this.mpPacienteAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpPacienteAnt(this.mpPaciente);
		
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
		if (null == this.mpPaciente.getId()) return;
		//
		this.setMpPacienteAnt(this.mpPaciente);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpPaciente.getId())	return;
		//
		try {
			this.mpPacientes.remover(mpPaciente);
			
			MpFacesUtil.addInfoMessage("Paciente... " + this.mpPaciente.getNome()
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

		this.setMpPacienteAnt(this.mpPaciente);
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
		this.mpPaciente = this.mpPacienteAnt;
		
		this.trataExibicaoArquivo();
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpPaciente.getNome()) return;
		//
		this.setMpPacienteAnt(this.mpPaciente);
		//
		this.mpPaciente = this.mpPacientes.porNavegacao("mpNext", mpPaciente.getNome());
		if (null == this.mpPaciente) {
			this.mpPaciente = this.mpPacienteAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpPaciente = this.mpPacientes.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpPaciente)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpPaciente.getId()) return;

		try {
			this.setMpPacienteAnt(this.mpPaciente);

			this.mpPaciente = (MpPaciente) this.mpPaciente.clone();
			//
			this.mpPaciente.setId(null);
			
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
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
//		System.out.println("MpCadastroPacienteBean.mpHelp() (Obj = " + 
//																this.mpObjetoHelp.getTransacao());
	}
	
	private void limpar() {
		//
		this.mpPaciente = new MpPaciente();
		
		this.mpPaciente.setTenantId(mpSeguranca.capturaTenantId());
		//
		this.mpPaciente.setNome("");
		
		this.mpPaciente.setMpPessoaPacientes(new ArrayList<MpPessoaPaciente>());
		//
		this.mpPessoaPaciente = new MpPessoaPaciente();
		//
		this.indEditavelPessoa = false;
		
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
			
			this.mpPaciente.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroPacienteBean.aoCapturarArquivo() - NULL");
			return;
		}
		
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpPaciente.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpPaciente.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpPaciente.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPaciente.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpPaciente.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpPaciente.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPaciente.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpPaciente.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpPaciente.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpPaciente.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
												this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpPaciente.getArquivoBD() != null && this.mpPaciente.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
															this.mpPaciente.getArquivoBD()), "");
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
	
	public MpPaciente getMpPaciente() {	return mpPaciente; }
	public void setMpPaciente(MpPaciente mpPaciente) { this.mpPaciente = mpPaciente; }

	public MpPaciente getMpPacienteAnt() { return mpPacienteAnt; }
	public void setMpPacienteAnt(MpPaciente mpPacienteAnt) { 
		//
		try {
			this.mpPacienteAnt = (MpPaciente) this.mpPaciente.clone();
			this.mpPacienteAnt.setId(this.mpPaciente.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }
	
	public MpPessoaPaciente getMpPessoaPaciente() { return mpPessoaPaciente; }
	public void setMpPessoaPaciente(MpPessoaPaciente mpPessoaPaciente) { 
													this.mpPessoaPaciente = mpPessoaPaciente; }
	
	public MpPessoa getMpPessoa() { return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
	public List<MpPessoa> getMpPessoaList() { return mpPessoaList; }
	
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpGrupo getMpGrupo() { return mpGrupo; }
	public void setMpGrupo(MpGrupo mpGrupo) { this.mpGrupo = mpGrupo; }
	public List<MpGrupo> getMpGrupoList() { return mpGrupoList; }

	public MpCor getMpCor() { return mpCor; }
	public void setMpCor(MpCor mpCor) {	this.mpCor = mpCor; }
	public List<MpCor> getMpCorList() {	return mpCorList; }

	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }
	
	public boolean getIndEditavelPessoa() { return indEditavelPessoa; }
	public void setIndEditavelPessoa(Boolean indEditavelPessoa) {
													this.indEditavelPessoa = indEditavelPessoa; }
	
	public String getTxtModoPessoaPacienteDialog() { return txtModoPessoaPacienteDialog; }
	public void setTxtModoPessoaPacienteDialog(String txtModoPessoaPacienteDialog) {
								this.txtModoPessoaPacienteDialog = txtModoPessoaPacienteDialog; }

	// --- 
	
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { 
															this.mpArquivoAcao = mpArquivoAcao; }
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