package com.mpxds.mpbasic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import javax.faces.bean.ManagedBean;
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

import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpItemReceita;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.model.MpProduto;

import com.mpxds.mpbasic.model.enums.MpArquivoAcao;

import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpItemReceitas;
import com.mpxds.mpbasic.repository.MpPacientes;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpReceitas;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.security.MpSeguranca;

import com.mpxds.mpbasic.service.MpReceitaService;
import com.mpxds.mpbasic.util.MpAppUtil;
//import com.mpxds.mpbasic.util.MpFotoCameraBean;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
//@ManagedBean(name = "mpCadastroReceitaBeanX")
public class MpCadastroReceitaBeanX implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpReceitas mpReceitas;

	@Inject
	private MpPacientes mpPacientes;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpItemReceitas mpItemReceitas;

	@Inject
	private MpPessoas mpPessoas;

	@Inject
	private MpProdutos mpProdutos;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpReceitaService mpReceitaService;

	// --- 
	
	private MpReceita mpReceita = new MpReceita();
	private MpReceita mpReceitaAnt;
	
	private MpItemReceita mpItemReceita = new MpItemReceita();
	private List<MpItemReceita> mpItemReceitaExcluidoList = new ArrayList<>();

	private MpPaciente mpPaciente;
	private List<MpPaciente> mpPacienteList = new ArrayList<MpPaciente>();
	
	private MpPessoa mpPessoa;
	private List<MpPessoa> mpPessoaList = new ArrayList<MpPessoa>();

	private MpProduto mpProduto;
	private List<MpProduto> mpProdutoList = new ArrayList<MpProduto>();

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelProd = false;
	
	private String txtModoTela = "";
	private String txtModoItemReceitaDialog = "";

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

	public MpCadastroReceitaBeanX() {
		//
		if (null == this.mpReceita)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpReceita) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpReceita.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpReceitaAnt(this.mpReceita);
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
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		//		
		this.mpPacienteList = mpPacientes.mpPacienteAtivos();
		this.mpPessoaList = mpPessoas.porPessoaMedicoList();
		this.mpProdutoList = mpProdutos.porProdutoList();
		this.mpArquivoAcaoList = Arrays.asList(MpArquivoAcao.values());
		//
		if (!this.scIndCapturaFoto) {
			this.mpArquivoAcaoList.add(MpArquivoAcao.ASSOCIAR);
			this.mpArquivoAcaoList.add(MpArquivoAcao.CARREGAR);
		} else
			this.mpArquivoAcaoList = Arrays.asList(MpArquivoAcao.values());
		//
		this.mpArquivoBDList = mpArquivoBDs.porMpArquivoBDList();

		if (null == this.mpReceita)
			assert(true); // nop
		else
			this.mpItemReceita.setMpReceita(this.mpReceita);
	}

	public void salvar() {
		//
		// =========================================
		// Trata MpArquivoBD (Arquivo Banco Dados !)
		// =========================================
		if (this.isArquivoContent()) 
			this.mpReceita.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpReceita.getMpArquivoAcao())
			assert (true); // nop
		else
			if (this.mpReceita.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
				this.mpReceita.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpReceita.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		this.mpReceita = this.mpReceitaService.salvar(this.mpReceita);
		//
		if (this.mpItemReceitaExcluidoList.size() > 0) {
			for (MpItemReceita mpItemReceitaX : this.mpItemReceitaExcluidoList) {
				//
				if (null == mpItemReceitaX.getId()) continue;

				this.mpItemReceitas.remover(mpItemReceitaX);
			}
		}
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;
		//
		MpFacesUtil.addInfoMessage("Receita... salva com sucesso!");
	}

	public void alterarMpItemReceita() {
		//
		this.txtModoItemReceitaDialog = "Edição";
		
		this.indEditavelProd = true;
	}			
		
	public void adicionarMpItemReceitaX() {
		//
		this.txtModoItemReceitaDialog = "Novo";
		
		this.mpItemReceita = new MpItemReceita();

		this.mpItemReceita.setMpReceita(this.mpReceita);
		this.mpItemReceita.setTenantId(mpSeguranca.capturaTenantId());

		this.mpReceita.getMpItemReceitas().add(this.mpItemReceita);
		//
		this.indEditavelProd = true;
	}

	public void removerMpItemReceitaX() {
		//
		try {
			this.mpReceita.getMpItemReceitas().remove(this.mpItemReceita);
			
			this.mpItemReceitaExcluidoList.add(this.mpItemReceita);
			
			MpFacesUtil.addInfoMessage("ITEM RECEITA... ( " +
					this.mpItemReceita.getMpProduto().getNome()	+ " )... excluído com sucesso.");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpItemReceita() {
		//
		this.indEditavelProd = false;
		
		this.mpItemReceita = new MpItemReceita();
	}			

	public void fecharMpItemReceita() {
		//
		if (this.txtModoItemReceitaDialog.equals("Novo"))
			this.mpReceita.getMpItemReceitas().remove(this.mpItemReceita);
	}			
	
//	public void novoMpItemReceita() {
//		//
//		this.mpItemReceita = new MpItemReceita();
//	}	

	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpReceita.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpReceita.getMpArquivoAcao().getDescricao();
	}
	
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpReceita = this.mpReceitas.porNavegacao("mpFirst", " "); 
		if (null == this.mpReceita)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpReceita.getDescricao()) return;
		//
		this.setMpReceitaAnt(this.mpReceita);
		//
		this.mpReceita = this.mpReceitas.porNavegacao("mpPrev", mpReceita.getDescricao());
		if (null == this.mpReceita) {
			this.mpReceita = this.mpReceitaAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.limparArquivo();

		this.setMpReceitaAnt(this.mpReceita);
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
		//
		if (null == this.mpReceita.getId()) return;
		//
		this.setMpReceitaAnt(this.mpReceita);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		if (null == this.mpReceita.getId()) return;
		//
		try {
			this.mpReceitas.remover(mpReceita);
			
			MpFacesUtil.addInfoMessage("Receita... " + this.mpReceita.getDescricao()
																	+ " excluída com sucesso.");
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
		
		this.setMpReceitaAnt(this.mpReceita);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpReceita = this.mpReceitaAnt;
		
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
		if (null == this.mpReceita.getDescricao()) return;
		//
		this.setMpReceitaAnt(this.mpReceita);
		//
		this.mpReceita = this.mpReceitas.porNavegacao("mpNext", mpReceita.getDescricao());
		if (null == this.mpReceita) {
			this.mpReceita = this.mpReceitaAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpReceita = this.mpReceitas.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpReceita)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}

	public void mpClone() {
		//
		if (null == this.mpReceita.getId()) return;

		try {
			this.setMpReceitaAnt(this.mpReceita);

			this.mpReceita = (MpReceita) this.mpReceita.clone();
			//
			this.mpReceita.setId(null);
			
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
	}

	private void limpar() {
		//
		this.mpReceita = new MpReceita();
		//
		this.mpReceita.setDataReceita(new Date());
		this.mpReceita.setDescricao("");
		//
		this.mpItemReceita = new MpItemReceita();

		this.mpItemReceita.setQuantidade(BigDecimal.ZERO);
		this.mpItemReceita.setObservacao("");
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
			
			this.mpReceita.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroReceitaBean.aoCapturarArquivo() - NULL");
			return;
		}
		
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpReceita.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpReceita.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpReceita.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpReceita.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpReceita.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpReceita.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpReceita.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpReceita.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpReceita.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpReceita.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpReceita.getArquivoBD() != null && this.mpReceita.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpReceita.getArquivoBD()), "");
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
	
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }
	public List<MpArquivoAcao> getMpArquivoAcaoList() { return mpArquivoAcaoList; }

	public MpArquivoBD getMPArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }
	public List<MpArquivoBD> getMpArquivoBDList() { return mpArquivoBDList; }

	// ----

	public MpReceita getMpReceita() { return mpReceita; }
	public void setMpReceita(MpReceita mpReceita) { this.mpReceita = mpReceita; }

	public MpReceita getMpReceitaAnt() { return mpReceitaAnt; }
	public void setMpReceitaAnt(MpReceita mpReceitaAnt) {
		//
		try {
			this.mpReceitaAnt = (MpReceita) this.mpReceita.clone();
			this.mpReceitaAnt.setId(this.mpReceita.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpItemReceita getMpItemReceita() { return mpItemReceita; }
	public void setMpItemReceita(MpItemReceita mpItemReceita) { this.mpItemReceita = mpItemReceita; }
	
	public MpPaciente getMpPaciente() {	return mpPaciente; }
	public void setMpPaciente(MpPaciente mpPaciente) { this.mpPaciente = mpPaciente; }
	public List<MpPaciente> getMpPacienteList() { return mpPacienteList; }
	
	public MpPessoa getMpPessoa() {	return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
	public List<MpPessoa> getMpPessoaList() { return mpPessoaList; }
	
	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }
	public List<MpProduto> getMpProdutoList() {	return mpProdutoList; }

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }

	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }

	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) {	this.indNaoEditavel = indNaoEditavel; }

	public boolean getIndEditavelProd() { return indEditavelProd; }
	public void setIndEditavelProd(Boolean indEditavelProd) {
															this.indEditavelProd = indEditavelProd; }

	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public String getTxtModoItemReceitaDialog() { return txtModoItemReceitaDialog; }
	public void setTxtModoItemReceitaDialog(String txtModoItemReceitaDialog) {
										this.txtModoItemReceitaDialog = txtModoItemReceitaDialog; }
	
}