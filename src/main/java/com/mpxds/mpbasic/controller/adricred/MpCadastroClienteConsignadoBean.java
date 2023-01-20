package com.mpxds.mpbasic.controller.adricred;

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

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.adricred.MpClienteConsignado;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.enums.MpEstadoCivil;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.adricred.MpClienteConsignados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.adricred.MpClienteConsignadoService;
import com.mpxds.mpbasic.util.MpAppUtil;
//import com.mpxds.mpbasic.util.MpFotoCameraBean;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroClienteConsignadoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpClienteConsignados mpClienteConsignados;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpClienteConsignadoService mpClienteConsignadoService;

	// --- 
	
	private MpClienteConsignado mpClienteConsignado = new MpClienteConsignado();
	private MpClienteConsignado mpClienteConsignadoAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	private MpTipoPessoa mpTipoPessoa;
	private List<MpTipoPessoa> mpTipoPessoaList;
	
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList;
	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList;
	
	private MpEstadoCivil mpEstadoCivil;
	private List<MpEstadoCivil> mpEstadoCivilList;
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;

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

	public MpCadastroClienteConsignadoBean() {
		//
		if (null == this.mpClienteConsignado)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpClienteConsignado) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpClienteConsignado.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
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
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();

		this.mpTipoPessoaList = Arrays.asList(MpTipoPessoa.values());
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpSexoList = Arrays.asList(MpSexo.values());
		this.mpEstadoCivilList = Arrays.asList(MpEstadoCivil.values());
		//
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
			this.mpClienteConsignado.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpClienteConsignado.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpClienteConsignado.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpClienteConsignado.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpClienteConsignado.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";
		// if (this.mpClienteConsignado.getCodigo().isEmpty()) msg = msg + "\n(Código)";
		// if (this.mpClienteConsignado.getEmail().isEmpty()) msg = msg + "\n(E-mail)";
		if (!this.mpClienteConsignado.getCpf().isEmpty()) {
			String isCPF = MpAppUtil.verificaCpf(this.mpClienteConsignado.getCpf());
			if (isCPF.equals("false"))
				msg = msg + "\n(Doc.Rec.Federal)";
		}
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		
		// Trata duplicidade do codigo !
		// if (this.checkDuplicidade()) {
		// MpFacesUtil.addInfoMessage("Código já existe... favor verificar! (" +
		// this.mpClienteConsignado.getCodigo());
		// return;
		// }
		//
		this.mpClienteConsignado = this.mpClienteConsignadoService.salvar(this.mpClienteConsignado);
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Cliente Consignado... salvo com sucesso!");
	}
	
	public void onCepWebService() {
    	//
//		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.mpClientConsignado.getMpEnderecoLocal().getCep());
//		if (null == mpCepXML)
//			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
//		else {
////			System.out.println("MpCadastroClienteConsignadoBean.onCepWebService() - ( " +
////				this.mpClienteConsignado.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
//			//
//			this.getMpEndereco().setLogradouro(mpCepXML.getLogradouro());
//			this.getMpEndereco().setComplemento(mpCepXML.getComplemento());
//			this.getMpEndereco().setBairro(mpCepXML.getBairro());
//			this.getMpEndereco().setCidade(mpCepXML.getCidade());
//			// Trata UF! ...
//			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
//			if (null == mpCepXML.getEstado())
//				mpEstadoUF = MpEstadoUF.XX;
//			else {
//				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
//				if (null == mpEstadoUF)
//					mpEstadoUF = MpEstadoUF.XX;
//			}
//			this.getMpEndereco().setMpEstadoUF(mpEstadoUF);
//			//
//		}
    }

//	public Boolean checkDuplicidade() {
//		//
//        if (this.mpClienteConsignado.getId() == null) {
//        	if (this.mpClienteConsignados.countByCodigo(this.mpClienteConsignado.getCodigo()) > 0)
//        		return true;
//        } else { 
//			if (!mpClienteConsignadoAnt.getCodigo().equals(this.mpClienteConsignado.getCodigo()))
//	        	if (mpClienteConsignados.countByCodigo(this.mpClienteConsignado.getCodigo()) > 0)
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
		this.mpClienteConsignado.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpClienteConsignado.getMpArquivoAcao().getDescricao();
	}
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpClienteConsignado = this.mpClienteConsignados.porNavegacao("mpFirst", " "); 
		if (null == this.mpClienteConsignado)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpClienteConsignado.getNome()) return;
		//
		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
		//
		this.mpClienteConsignado = this.mpClienteConsignados.porNavegacao("mpPrev", mpClienteConsignado.getNome());
		if (null == this.mpClienteConsignado) {
			this.mpClienteConsignado = this.mpClienteConsignadoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
		
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
		if (null == this.mpClienteConsignado.getId()) return;
		//
		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpClienteConsignado.getId()) return;
		//
		try {
			this.mpClienteConsignados.remover(mpClienteConsignado);
			
			MpFacesUtil.addInfoMessage("ClienteConsignado... " + this.mpClienteConsignado.getNome() + " excluído com sucesso.");
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

		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.limparArquivo();
		
		this.mpClienteConsignado = this.mpClienteConsignadoAnt;		
		
		this.trataExibicaoArquivo();
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpClienteConsignado.getNome()) return;
		//
		this.setMpClienteConsignadoAnt(this.mpClienteConsignado);
		//
		this.mpClienteConsignado = this.mpClienteConsignados.porNavegacao("mpNext", mpClienteConsignado.getNome());
		if (null == this.mpClienteConsignado) {
			this.mpClienteConsignado = this.mpClienteConsignadoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpClienteConsignado = this.mpClienteConsignados.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpClienteConsignado)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpClienteConsignado.getId()) return;

		try {
			this.setMpClienteConsignadoAnt(this.mpClienteConsignado);

			this.mpClienteConsignado = (MpClienteConsignado) this.mpClienteConsignado.clone();
			//
			this.mpClienteConsignado.setId(null);
			
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
		this.mpClienteConsignado = new MpClienteConsignado();
		
		this.mpClienteConsignado.setTenantId(mpSeguranca.capturaTenantId());
		this.mpClienteConsignado.setNome("");
		this.mpClienteConsignado.setEmail("");		
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
			
			this.mpClienteConsignado.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroClienteConsignadoBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpClienteConsignado.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpClienteConsignado.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpClienteConsignado.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteConsignado.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpClienteConsignado.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpClienteConsignado.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteConsignado.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpClienteConsignado.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpClienteConsignado.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpClienteConsignado.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpClienteConsignado.getArquivoBD() != null && this.mpClienteConsignado.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpClienteConsignado.getArquivoBD()), "");
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
	
	public MpClienteConsignado getMpClienteConsignado() { return mpClienteConsignado; }
	public void setMpClienteConsignado(MpClienteConsignado mpClienteConsignado) {	this.mpClienteConsignado = mpClienteConsignado; }
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public MpClienteConsignado getMpClienteConsignadoAnt() { return mpClienteConsignadoAnt; }
	public void setMpClienteConsignadoAnt(MpClienteConsignado mpClienteConsignadoAnt) {
		try {
			this.mpClienteConsignadoAnt = (MpClienteConsignado) this.mpClienteConsignado.clone();
			this.mpClienteConsignadoAnt.setId(this.mpClienteConsignado.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpTipoPessoa getMpTipoPessoa() {	return mpTipoPessoa; }
	public void setMpTipoPessoa(MpTipoPessoa mpTipoPessoa) { this.mpTipoPessoa = mpTipoPessoa; }
	public List<MpTipoPessoa> getMpTipoPessoaList() { return mpTipoPessoaList; }

	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }

	public MpEstadoCivil getMpEstadoCivil() { return mpEstadoCivil;	}
	public void setMpEstadoCivil(MpEstadoCivil mpEstadoCivil) {	this.mpEstadoCivil = mpEstadoCivil; }
	public List<MpEstadoCivil> getMpEstadoCivilList() {	return mpEstadoCivilList; }

	public MpEstadoUF getMPEstadoUF() { return mpEstadoUF; }
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

}