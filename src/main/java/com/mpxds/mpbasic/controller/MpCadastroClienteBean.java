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

import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.model.MpDependente;
import com.mpxds.mpbasic.model.MpEndereco;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.enums.MpEstadoCivil;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.MpClientes;
import com.mpxds.mpbasic.repository.MpDependentes;
import com.mpxds.mpbasic.repository.MpEnderecos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpArquivoBDs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpClienteService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;
import com.mpxds.mpbasic.util.MpAppUtil;
//import com.mpxds.mpbasic.util.MpFotoCameraBean;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroClienteBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpClientes mpClientes;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpDependentes mpDependentes;

	@Inject
	private MpEnderecos mpEnderecos;

	@Inject
	private MpArquivoBDs mpArquivoBDs;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpClienteService mpClienteService;

	// --- 
	
	private MpCliente mpCliente = new MpCliente();
	private MpCliente mpClienteAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelDep = false;
	private Boolean indEditavelEnd = false;
	
	private String txtModoTela = "";
	private String txtModoDependenteDialog = "";
	private String txtModoEnderecoDialog = "";
	
	private MpDependente mpDependente = new MpDependente();
	private List<MpDependente> mpDependenteExcluidoList = new ArrayList<MpDependente>();	

	private MpEndereco mpEndereco = new MpEndereco();
	private List<MpEndereco> mpEnderecoExcluidoList = new ArrayList<MpEndereco>();
	
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

	public MpCadastroClienteBean() {
		//
		if (null == this.mpCliente)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpCliente) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpCliente.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpClienteAnt(this.mpCliente);
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
			this.mpCliente.setArquivoBD(this.getArquivoBytes());

		// Limpa campos de Arquivo !
		if (null == this.mpCliente.getMpArquivoAcao())
			assert (true); // nop
		else if (this.mpCliente.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR))
			this.mpCliente.setArquivoBD(null);
		//
		if (null == this.mpArquivoAcao)
			assert (true); // nop
		else
			this.mpCliente.setMpArquivoAcao(mpArquivoAcao);
		// ============================================

		String msg = "";
		// if (this.mpCliente.getCodigo().isEmpty()) msg = msg + "\n(Código)";
		// if (this.mpCliente.getEmail().isEmpty()) msg = msg + "\n(E-mail)";
		if (!this.mpCliente.getCpf().isEmpty()) {
			String isCPF = MpAppUtil.verificaCpf(this.mpCliente.getCpf());
			if (isCPF.equals("false"))
				msg = msg + "\n(Doc.Rec.Federal)";
		}
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválido(s)");
			return;
		}
		//
		if (null == this.mpCliente.getDocumentoReceitaFederal())
			this.mpCliente.setDocumentoReceitaFederal(this.mpCliente.getCpf());
		
		// Trata duplicidade do codigo !
		// if (this.checkDuplicidade()) {
		// MpFacesUtil.addInfoMessage("Código já existe... favor verificar! (" +
		// this.mpCliente.getCodigo());
		// return;
		// }
		//
		this.mpCliente = this.mpClienteService.salvar(this.mpCliente);
		//
		if (this.mpDependenteExcluidoList.size() > 0) {
			for (MpDependente mpDependenteX : this.mpDependenteExcluidoList) {
				//
				if (null == mpDependenteX.getId()) continue;

				this.mpDependentes.remover(mpDependenteX);
			}
		}
		//
		if (this.mpEnderecoExcluidoList.size() > 0) {
			for (MpEndereco mpEnderecoX : this.mpEnderecoExcluidoList) {
				//
				if (null == mpEnderecoX.getId()) continue;

				this.mpEnderecos.remover(mpEnderecoX);
			}
		}
		//
		this.arquivoBytesC = null;
		this.arquivoContent = null;
		this.mpArquivoAcao = null;
		this.arquivoAcaoSelecao = null;

		MpFacesUtil.addInfoMessage("Cliente... salvo com sucesso!");
	}
	
	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.getMpEndereco().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroClienteBean.onCepWebService() - ( " +
//				this.mpCliente.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.getMpEndereco().setLogradouro(mpCepXML.getLogradouro());
			this.getMpEndereco().setComplemento(mpCepXML.getComplemento());
			this.getMpEndereco().setBairro(mpCepXML.getBairro());
			this.getMpEndereco().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.getMpEndereco().setMpEstadoUF(mpEstadoUF);
			//
		}
    }

//	public Boolean checkDuplicidade() {
//		//
//        if (this.mpCliente.getId() == null) {
//        	if (this.mpClientes.countByCodigo(this.mpCliente.getCodigo()) > 0)
//        		return true;
//        } else { 
//			if (!mpClienteAnt.getCodigo().equals(this.mpCliente.getCodigo()))
//	        	if (mpClientes.countByCodigo(this.mpCliente.getCodigo()) > 0)
//					return true;
//        }
//		//
//		return false;		
//	}

	// ---
	
	public void alterarMpDependente() {
		//
		this.txtModoDependenteDialog = "Edição";
		
		this.indEditavelDep = true;
	}			
	
	public void adicionarMpDependenteX() {
		//
		this.txtModoDependenteDialog = "Novo";
		
		this.mpDependente = new MpDependente();

		this.mpDependente.setMpCliente(this.mpCliente);
		this.mpDependente.setTenantId(mpSeguranca.capturaTenantId());
			
		this.mpCliente.getMpDependentes().add(this.mpDependente);
		//
		this.indEditavelDep = true;
	}

	public void removerMpDependenteX() {
		//
		try {
			this.mpCliente.getMpDependentes().remove(this.mpDependente);
			
			this.mpDependenteExcluidoList.add(this.mpDependente);
			
			MpFacesUtil.addInfoMessage("Dependente... " + this.mpDependente.getNome()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpDependente() {
		//
		this.indEditavelDep = false;
		
		this.mpDependente = new MpDependente();
	}			

	public void fecharMpDependente() {
		//
		if (this.txtModoDependenteDialog.equals("Novo"))
			this.mpCliente.getMpDependentes().remove(this.mpDependente);
	}			
	
	// ---
	
	public void alterarMpEndereco() {
		//
		this.txtModoEnderecoDialog = "Edição";

		this.indEditavelEnd = true;
	}			
	
	public void adicionarMpEndereco() {
		//
		this.txtModoEnderecoDialog = "Novo";

		this.mpEndereco = new MpEndereco();

		this.mpEndereco.setTenantId(mpSeguranca.capturaTenantId());
		this.mpEndereco.setMpCliente(this.mpCliente);
			
		this.mpCliente.getMpEnderecos().add(this.mpEndereco);
		//
		this.indEditavelEnd = true;
	}

	public void removerMpEndereco() {
		//
		try {
			this.mpCliente.getMpEnderecos().remove(this.mpEndereco);
			
			this.mpEnderecoExcluidoList.add(this.mpEndereco);
			
			MpFacesUtil.addInfoMessage("Endereço... " + this.mpEndereco.getLogradouro()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpEndereco() {
		//
		this.indEditavelEnd = false;
		
		this.mpEndereco = new MpEndereco();
	}			

	public void fecharMpEndereco() {
		//
		if (this.txtModoEnderecoDialog.equals("Novo"))
			this.mpCliente.getMpEnderecos().remove(this.mpEndereco);
	}			

	// ---
	
	public void associaMpArquivoBD() {
		//
		this.trataExibicaoArquivo();
	}
	
	public void arquivoAcaoSelecionado(ValueChangeEvent event) {
		//
		this.mpCliente.setMpArquivoAcao((MpArquivoAcao) event.getNewValue());
		//
		this.arquivoAcaoSelecao = this.mpCliente.getMpArquivoAcao().getDescricao();
	}
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpCliente = this.mpClientes.porNavegacao("mpFirst", " "); 
		if (null == this.mpCliente)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpCliente.getNome()) return;
		//
		this.setMpClienteAnt(this.mpCliente);
		//
		this.mpCliente = this.mpClientes.porNavegacao("mpPrev", mpCliente.getNome());
		if (null == this.mpCliente) {
			this.mpCliente = this.mpClienteAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.trataExibicaoArquivo();
	}

	public void mpNew() {
		//
		this.setMpClienteAnt(this.mpCliente);
		
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
		if (null == this.mpCliente.getId()) return;
		//
		this.setMpClienteAnt(this.mpCliente);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpCliente.getId()) return;
		//
		try {
			this.mpClientes.remover(mpCliente);
			
			MpFacesUtil.addInfoMessage("Cliente... " + this.mpCliente.getNome() + " excluído com sucesso.");
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

		this.setMpClienteAnt(this.mpCliente);
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
		
		this.mpCliente = this.mpClienteAnt;		
		
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
		if (null == this.mpCliente.getNome()) return;
		//
		this.setMpClienteAnt(this.mpCliente);
		//
		this.mpCliente = this.mpClientes.porNavegacao("mpNext", mpCliente.getNome());
		if (null == this.mpCliente) {
			this.mpCliente = this.mpClienteAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.trataExibicaoArquivo();
	}
	
	public void mpEnd() {
		//
		this.mpCliente = this.mpClientes.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpCliente)
			this.limpar();
		else
			this.trataExibicaoArquivo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpCliente.getId()) return;

		try {
			this.setMpClienteAnt(this.mpCliente);

			this.mpCliente = (MpCliente) this.mpCliente.clone();
			//
			this.mpCliente.setId(null);
			
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
		this.mpCliente = new MpCliente();
		
		this.mpCliente.setTenantId(mpSeguranca.capturaTenantId());
		this.mpCliente.setNome("");
		this.mpCliente.setEmail("");
		
		this.mpCliente.setMpDependentes(new ArrayList<MpDependente>());
		this.mpCliente.setMpEnderecos(new ArrayList<MpEndereco>());
		//
		this.mpDependente = new MpDependente();

		this.mpDependente.setTenantId(mpSeguranca.capturaTenantId());
		this.mpDependente.setNumero("");
		//
		this.mpEndereco = new MpEndereco();

		this.mpEndereco.setTenantId(mpSeguranca.capturaTenantId());
		this.mpEndereco.setLogradouro("");
		//
		this.indEditavelDep = false;
		this.indEditavelEnd = false;
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
			
			this.mpCliente.setMpArquivoAcao(MpArquivoAcao.CARREGAR);
			
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
			System.out.println("MpCadastroClienteBean.aoCapturarArquivo() - NULL");
			return;
		}
		//
		this.arquivoBytesC = event.getData();
		this.arquivoBytes = event.getData();

		this.arquivoContent = new ByteArrayContent(this.arquivoBytesC, "image/jpeg");
	
		this.mpCliente.setMpArquivoAcao(MpArquivoAcao.CAPTURAR);
		
		MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoBytesC.length +
																" )... capturado com sucesso.");
	}
	
	public void trataExibicaoArquivo() {
		//
		if (null == this.mpCliente.getMpArquivoAcao()) {
			this.limparArquivo();
			//
			if 	(null == this.mpCliente.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpCliente.getArquivoBD());
			//
			return;
		}
		//
		if (this.mpCliente.getMpArquivoAcao().equals(MpArquivoAcao.ASSOCIAR)) {
			if (null == this.mpCliente.getMpArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpCliente.getMpArquivoBD().getArquivo());
		} else // CAPTURAR e CARREGAR ...
			if 	(null == this.mpCliente.getArquivoBD())
				assert(true); // nop
			else
				this.setArquivoBytes(this.mpCliente.getArquivoBD());
	}
	
	// ---
	
	public boolean isEditando() { return this.mpCliente.getId() != null; }
	
	public String getArquivoAcaoSelecao() { return arquivoAcaoSelecao; }
	public void setArquivoAcaoSelecao(String arquivoAcaoSelecao) {
													this.arquivoAcaoSelecao = arquivoAcaoSelecao; }
	
    public StreamedContent getImagem() {
		DefaultStreamedContent imagemDsc = new DefaultStreamedContent();
		
		if (this.mpCliente.getArquivoBD() != null && this.mpCliente.getArquivoBD().length != 0)
			imagemDsc = new DefaultStreamedContent(new ByteArrayInputStream(
																this.mpCliente.getArquivoBD()), "");
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
	
	public MpCliente getMpCliente() { return mpCliente; }
	public void setMpCliente(MpCliente mpCliente) {	this.mpCliente = mpCliente; }
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public MpCliente getMpClienteAnt() { return mpClienteAnt; }
	public void setMpClienteAnt(MpCliente mpClienteAnt) {
		try {
			this.mpClienteAnt = (MpCliente) this.mpCliente.clone();
			this.mpClienteAnt.setId(this.mpCliente.getId());
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
	
	public MpDependente getMpDependente() { return mpDependente; }
	public void setMpDependente(MpDependente mpDependente) { this.mpDependente = mpDependente; }

	public MpEndereco getMpEndereco() {	return mpEndereco; }
	public void setMpEndereco(MpEndereco mpEndereco) { this.mpEndereco = mpEndereco; }

	public boolean getIndEditavelDep() { return indEditavelDep; }
	public void setIndEditavelDep(Boolean indEditavelDep) { this.indEditavelDep = indEditavelDep; }

	public boolean getIndEditavelEnd() { return indEditavelEnd; }
	public void setIndEditavelEnd(Boolean indEditavelEnd) { this.indEditavelEnd = indEditavelEnd; }
	
	public String getTxtModoDependenteDialog() { return txtModoDependenteDialog; }
	public void setTxtModoDependenteDialog(String txtModoDependenteDialog) {
											this.txtModoDependenteDialog = txtModoDependenteDialog; }
	
	public String getTxtModoEnderecoDialog() { return txtModoEnderecoDialog; }
	public void setTxtModoEnderecoDialog(String txtModoEnderecoDialog) {
												this.txtModoEnderecoDialog = txtModoEnderecoDialog; }
	
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