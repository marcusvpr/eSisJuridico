package com.mpxds.mpbasic.controller.sisJuri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
//import javax.faces.context.FacesContext;
//import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
//import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaSJ;
import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.model.sisJuri.MpProcessoAndamento;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessos;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaSJs;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessoAndamentos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.sisJuri.MpProcessoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@ManagedBean(name = "mpCadastroProcessoBean")
@Named
@ViewScoped
public class MpCadastroProcessoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpProcessos mpProcessos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpProcessoService mpProcessoService;

	@Inject
	private MpProcessoAndamentos mpProcessoAndamentos;

	@Inject
	private MpClienteSJs mpClienteSJs;
	
	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpPessoaSJs mpPessoaSJs;

	@Inject
	private MpPessoaFisicas mpPessoaFisicas;
	
	// ---
	
	private MpProcesso mpProcesso = new MpProcesso();
	private MpProcesso mpProcessoAnt;
	private MpProcesso mpProcessoSel = new MpProcesso();

	private MpProcessoAndamento mpProcessoAndamento = new MpProcessoAndamento();
	private MpProcessoAndamento mpProcessoAndamentoSelecionado = new MpProcessoAndamento();
	
	private MpProcessoAndamento mpProcessoAndamentoAnt = new MpProcessoAndamento();

//	private MpProcessoAndamento mpProcessoAndamentoUpload = new MpProcessoAndamento();

	private List<MpProcessoAndamento> mpProcessoAndamentoDelList = new ArrayList<MpProcessoAndamento>();
	
	// ---

	private Boolean indVisivelDet = true;
	private Boolean indAlteracaoDet = false;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;

	private String processoFind = "";
	
	private String txtModoTela = "";
	private String dialogPagina = "";
	private String dialogMensagem = "";
	
	private SimpleDateFormat sdfX = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private List<MpClienteSJ> mpClienteSJList = new ArrayList<MpClienteSJ>();
	private List<MpPessoaSJ> mpParteContrariaList = new ArrayList<MpPessoaSJ>();		
	private List<MpTabelaInternaSJ> mpComarcaList = new ArrayList<MpTabelaInternaSJ>(); // TAB_1008 (Pai)	
	private List<MpTabelaInternaSJ> mpComarcaCartorioList = new ArrayList<MpTabelaInternaSJ>(); // TAB_1042 (filha)		
	private List<MpPessoaFisica> mpResponsavelList = new ArrayList<MpPessoaFisica>();
	
	private List<MpTabelaInternaSJ> mpAtoPraticadoList = new ArrayList<MpTabelaInternaSJ>(); // TAB_1040	
	private List<MpTabelaInternaSJ> mpAndamentoTipoList = new ArrayList<MpTabelaInternaSJ>(); // TAB_1022	
	private List<MpTabelaInternaSJ> mpDetalhamentoList = new ArrayList<MpTabelaInternaSJ>(); // TAB_1070

	// --- 

	private UploadedFile arquivoUpload;
	
	private StreamedContent arquivoContent = new DefaultStreamedContent();
	private byte[] arquivoBytes;
	private byte[] arquivoBytesC;
	
	private StreamedContent fileSC = new DefaultStreamedContent();
	
	// ---
	
	public MpCadastroProcessoBean() {
//		System.out.println("MpCadastroProcessoBean - Entrou 0000 ");
		//
		if (null == this.mpProcesso)
			this.limpar();
	}
	
	public void inicializar() {
//		System.out.println("MpCadastroProcessoBean.inicializar() - Entrou 0000 ");
		//
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (null == facesContext.getExternalContext().getSessionMap().get("selectedRecordX"))
			assert(true); // nop
		else {
			this.mpProcesso = (MpProcesso) facesContext.getExternalContext().getSessionMap().
																					get("selectedRecordX");
			
			facesContext.getExternalContext().getSessionMap().put("selectedRecordX", null);
		}
		//
		if (null == this.mpProcesso) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		//
//		System.out.println("MpCadastroProcessoBean.inicializar() ( " + this.mpProcesso.getProcessoCodigo());
		
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpProcesso.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
//		System.out.println("MpCadadastroBean.inicializar() - ( " + this.mpProcesso.getProcessoCodigo()); 
		
		this.setMpProcessoAnt(this.mpProcesso);
		
		this.mpProcessoAndamento.setMpProcesso(this.mpProcesso);
		this.mpProcessoAndamento.setTenantId(this.mpProcesso.getTenantId());
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		//
		this.mpClienteSJList = mpClienteSJs.byNomeList();
		this.mpComarcaList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1008);
		if (this.mpProcesso.getMpComarca() != null) this.carregarMpComarcaCartorio();
		//
		this.mpParteContrariaList = mpPessoaSJs.byNomeParteContrariaList();
		this.mpResponsavelList = mpPessoaFisicas.byNomeList();
		//
		this.mpAtoPraticadoList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1040);
		this.mpAndamentoTipoList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1022);
		this.mpDetalhamentoList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1070);
	}
	
	public void carregarMpComarcaCartorio() {
		//
		this.mpComarcaCartorioList = this.mpTabelaInternaSJs.mpFilhaList(this.mpProcesso.getMpComarca());
		//		
//		System.out.println("MpCadastroProcessoBean.carregarMpComarcaCartorio() ( " + this.mpProcesso.getMpComarca()
//																+ " / " + this.mpComarcaCartorioList.size());	
	}
	
	public void salvar() {
		//
		this.mpProcesso.setProcessoCodigo(this.mpProcesso.getProcessoCodigo().trim());
		
		this.mpProcesso = this.mpProcessoService.salvar(this.mpProcesso);
		//
		if (this.mpProcessoAndamentoDelList.size() > 0) {
			for (MpProcessoAndamento mpProcessoAndamentoX : this.mpProcessoAndamentoDelList) {
				//
				if (null == mpProcessoAndamentoX.getId()) continue;

				this.mpProcessoAndamentos.remover(mpProcessoAndamentoX);
			}
		}
		//
		MpFacesUtil.addInfoMessage("Processo... salvo com sucesso !");
	}

	public void onChangeNumProcesso() {
		//
//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.onChangeNumProcesso() - 000 ( " + 
//																	this.mpProcesso.getProcessoCodigo());
		String msg = "";
		Boolean indExisteProceso = false;
		
		this.mpProcessoSel = new MpProcesso();
		//		
		if (this.mpProcesso.getProcessoCodigo().isEmpty()) {
			msg = msg + "(Informar No.Processo)";

			MpFacesUtil.addInfoMessage("Alerta ! " + msg);
			return;
		} else {
			List<MpProcesso> mpProcessoListXX = this.mpProcessos.findAllByProcessoCodigo(
																	this.mpProcesso.getProcessoCodigo().trim());
//			MpAppUtil.PrintarLn("MpCadastroProcessoBean.onChangeNumProcesso() - 001 ( Size = " + 
//																					mpProcessoListXX.size()); 
			if (mpProcessoListXX.size()==0) 
				assert(true);
			else {
				for (MpProcesso mpProcessoXX : mpProcessoListXX) {
//					//
//					MpAppUtil.PrintarLn("MpCadastroProcessoBean.onChangeNumProcesso() - 000 ( " + 
//					this.mpProcesso.getProcessoCodigo() + " / " + mpProcessoXX.getProcessoCodigo() + " / " +
//					mpProcessoXX.getDataCadastro() + " / " + this.mpProcesso.getDataCadastro());
					
					if (mpProcessoXX.getDataCadastro().equals(this.mpProcesso.getDataCadastro()))
						continue;
					
					if (msg.isEmpty()) {
						msg = "(Processo já cadastrado!... ( Datas = ";

						this.mpProcessoSel = mpProcessoXX;
						//
						indExisteProceso = true;
					}
					//
					msg = msg +	mpProcessoXX.getDataCadastro() + " ";
				}
			}
		}
		//
		if (!indExisteProceso) {
			if (!msg.isEmpty()) {
//				MpAppUtil.PrintarLn("Alerta ! " + msg);
				//
				MpFacesUtil.addInfoMessage("Alerta ! " + msg);
				return;
			}
		} else {
//			MpAppUtil.PrintarLn(msg + " Deseja Continuar ?");
			//
			this.dialogMensagem = msg + " Deseja Continuar ?";
			
		    RequestContext context = RequestContext.getCurrentInstance();
		    
		    context.execute("PF('mpNumProcessoDialog').show();");		    
		}
	}	
	
	public void trataChamaProcesso() {
		//
//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.trataChamaProcesso() - 000");

		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.getExternalContext().getSessionMap().put("selectedRecordX", this.mpProcessoSel);
		//
		try {
			facesContext.getExternalContext().redirect("processoRS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void trataChangeDet() {
		//
		if (null == this.mpProcessoAndamento
		||	null == this.mpProcessoAndamento.getMpAtoPraticado()
		||	null == this.mpProcessoAndamento.getMpAtoPraticado().getCodigo())
			return;
		//
//		if (this.mpProcessoAndamento.getMpAtoPraticado().getCodigo().equals("3"))
//			System.out.println("MpCadastroProcessoBean.trataChangeDet() ( " + 
//													this.mpProcessoAndamento.getDataCadastroSDF());
//		else
//			if (null != this.mpProcessoAndamento.getMpAndamentoTipo())
//				System.out.println("MpCadastroProcessoBean.trataChangeDet() ( " + 
//													this.mpProcessoAndamento.getDataCadastro() + " / " +
//													this.mpProcessoAndamento.getMpAndamentoTipo().getDescricao());
	}
	
	public void trataGravacaoDet() {
		//
		if (!this.indAlteracaoDet)
			this.mpProcesso.getMpAndamentos().add(this.mpProcessoAndamento);
		else {
			this.mpProcesso.getMpAndamentos().remove(this.mpProcessoAndamentoAnt);
			this.mpProcesso.getMpAndamentos().add(this.mpProcessoAndamento);
		}
		//
		this.trataLimpezaDet();
	}
			
//	private UIComponent findComponent(String id, UIComponent where) {
//		//
//		if (where == null) {
//			return null;
//		} else if (where.getId().equals(id)) {
//			return where;
//		} else {
//			List<UIComponent> childrenList = where.getChildren();
//			
//			if (childrenList == null || childrenList.isEmpty()) {
//				return null;
//			}
//			//
//			for (UIComponent child : childrenList) {
//				UIComponent result = null;
//				
//				result = findComponent(id, child);
//
//				System.out.println("MpCadastroProcessoBean.findComponent() ( " + child.getId() + 
//								" / " + child.getClientId() + " / " + 
//								child.getContainerClientId(FacesContext.getCurrentInstance()));
//				
//				if (result != null) {
//					return result;
//				}
//				return null;
//			}
//		}
//		return null;
//	}
	
	public void trataAlteracaoDet() {
		//
		this.indAlteracaoDet = true;
		
//		System.out.println("MpCadastroProcessoBean.trataAlteracaoDet() ( " +
//										this.mpProcessoAndamentoSelecionado.getDataCadastro());
		
		this.mpProcessoAndamento = this.mpProcessoAndamentoSelecionado;				
		this.mpProcessoAndamentoAnt = this.mpProcessoAndamentoSelecionado;
		//
		this.trataVisivelDet();
	}

	public void trataExclusaoDet() {
		//
//		System.out.println("MpLiberaUsuariosBean.liberar() (" + mpUsuarioSelecionado.getNome());
//		//
		try {
			this.mpProcessoAndamentoDelList.add(this.mpProcessoAndamentoSelecionado);
			
			this.mpProcesso.getMpAndamentos().remove(this.mpProcessoAndamentoSelecionado);
			
			MpFacesUtil.addInfoMessage("Processo Andamento : ( " + this.mpProcessoAndamentoSelecionado.getDataCadastro() 
																+ " )... Excluido com sucesso!");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void trataVisivelDet() {
		//
		if (null == this.mpProcessoAndamento
		||	null == this.mpProcessoAndamento.getMpAtoPraticado()
		||	null == this.mpProcessoAndamento.getMpAtoPraticado().getCodigo())
			return;

//		System.out.println("MpCadastroProcessoBean.trataVisivelDet() ( " + 
//													this.mpProcessoAndamento.getMpAtoPraticado().getCodigo());

		// 1 = Audiência / 3 = Diligência... Vide Tab_1040 !
		if (this.mpProcessoAndamento.getMpAtoPraticado().getCodigo().equals("3"))
			this.indVisivelDet = false;
		else
			this.indVisivelDet = true;
		//		
	}
	
	// ----
	
//	public void trataFileUpload(FileUploadEvent event) {
		//
//	    FacesContext context = FacesContext.getCurrentInstance();
//
//	    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//	       // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//	       assert(true); // return new DefaultStreamedContent();
//	       System.out.println("MpCadastroProcessoBean.trataFileUpload() ( PhaseId.RENDER_RESPONSE");
//	    } else {
//	       // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
//	    	String mpAndamentoId = context.getExternalContext().getRequestParameterMap().get("mpAndamentoId");
//	    	if (null == mpAndamentoId) {
//	    	   assert(true);
//		       System.out.println("MpCadastroProcessoBean.trataFileUpload() - 000 ( mpAndamentoId = null");
//	    	} else {
//		       MpProcessoAndamento mpProcessoAndamentoX = this.mpProcessoAndamentos.porId(Long.valueOf(mpAndamentoId));
////		       return new DefaultStreamedContent(new ByteArrayInputStream(student.getImage()));
//		       System.out.println("MpCadastroProcessoBean.trataFileUpload() - 000 ( " + mpAndamentoId);
//	    	}	        
//	    }
	    //
//		System.out.println("MpCadastroProcessoBean.trataFileUpload() - 000 ( " + event.getFile().getFileName() + 
//								" / " + event.getFile().getContentType());
				
//		try {
//			this.arquivoBytes = MpAppUtil.getFileContents(event.getFile().getInputstream());
////			this.arquivoBytes = MpAppUtil.getFileContents(this.fileSC.getStream());
//			
//			this.mpProcessoAndamentoAnt = this.mpProcessoAndamentoSelecionado;
//			this.mpProcessoAndamentoUpload = this.mpProcessoAndamentoSelecionado;
//			
//			this.mpProcessoAndamentoUpload.setFotoBD(this.arquivoBytes);
//			
//			if (event.getFile().getContentType().toUpperCase().contains("PDF"))
//				this.mpProcessoAndamentoUpload.setTipoFotoBD("PDF");
//			else
//			if (event.getFile().getContentType().toUpperCase().contains("DOC"))
//				this.mpProcessoAndamentoUpload.setTipoFotoBD("DOC");
//			else
//			if (event.getFile().getContentType().toUpperCase().contains("XLS"))
//				this.mpProcessoAndamentoUpload.setTipoFotoBD("XLS");
////			
////			MpFacesUtil.addInfoMessage("Arquivo ( " + event.getFile().getFileName() +
////																	" )... carregado com sucesso.");
////			//
//		} catch (IOException e) {
//			MpFacesUtil.addErrorMessage(e.getMessage());
//		}
//	}
	
	public void gravaFileUpload() {
		//
//		System.out.println("MpCadastroProcessoBean.gravaFileUpload() - 000 ( " + this.arquivoUpload);

		if (null == this.arquivoUpload) {
			//
			MpFacesUtil.addInfoMessage("Selecionar Arquivo !");
			return;
		}
		//
		try {
			this.arquivoBytes = MpAppUtil.getFileContents(this.arquivoUpload.getInputstream());
//			this.arquivoBytes = MpAppUtil.getFileContents(this.fileSC.getStream());
			
			this.mpProcessoAndamentoAnt = this.mpProcessoAndamentoSelecionado;
			
			this.mpProcessoAndamentoSelecionado.setFotoBD(this.arquivoBytes);
			
			if (this.arquivoUpload.getContentType().toUpperCase().contains("PDF"))
				this.mpProcessoAndamentoSelecionado.setTipoFotoBD("PDF");
			else
			if (this.arquivoUpload.getContentType().toUpperCase().contains("DOC"))
				this.mpProcessoAndamentoSelecionado.setTipoFotoBD("DOC");
			else
			if (this.arquivoUpload.getContentType().toUpperCase().contains("XLS"))
				this.mpProcessoAndamentoSelecionado.setTipoFotoBD("XLS");
			//
			if (null == this.mpProcessoAndamentoSelecionado.getId())
				this.mpProcesso.getMpAndamentos().add(this.mpProcessoAndamentoSelecionado);
			else {
				this.mpProcesso.getMpAndamentos().remove(this.mpProcessoAndamentoAnt);
				this.mpProcesso.getMpAndamentos().add(this.mpProcessoAndamentoSelecionado);
			}
			
			MpFacesUtil.addInfoMessage("Arquivo ( " + this.arquivoUpload.getFileName() +
																	" )... carregado com sucesso.");
//			//
		} catch (IOException e) {
			MpFacesUtil.addErrorMessage(e.getMessage());
		}
	}

    public StreamedContent trataFileDownload() { 
    	//
//	    FacesContext context = FacesContext.getCurrentInstance();
//
//	    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//	       // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//	       assert(true); // return new DefaultStreamedContent();
//	       System.out.println("MpCadastroProcessoBean.trataFileDownloadView() ( PhaseId.RENDER_RESPONSE");
//	    } else {
//	       // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
//	       String mpAndamentoId = context.getExternalContext().getRequestParameterMap().get("mpAndamentoId");
//	        
//	       this.mpProcessoAndamentoSelecionado = this.mpProcessoAndamentos.porId(Long.valueOf(mpAndamentoId));
////	       return new DefaultStreamedContent(new ByteArrayInputStream(student.getImage()));
//	       System.out.println("MpCadastroProcessoBean.trataFileDownloadView() - 000 ( " + mpAndamentoId);
//	    }

//    	System.out.println("MpCadastroProcessoBean.trataFileDownloadView() - 000 ");
    	
    	this.fileSC = new DefaultStreamedContent();
    	//
    	if (null == this.mpProcessoAndamentoSelecionado
    	||	null == this.mpProcessoAndamentoSelecionado.getFotoBD())
 	       return null;
    	else {
    		InputStream stream = new ByteArrayInputStream(this.mpProcessoAndamentoSelecionado.getFotoBD());
    		
    		String contentTypeX = "application/pdf";
    		if (this.mpProcessoAndamentoSelecionado.getTipoFotoBD().toUpperCase().equals("PDF"))
    			contentTypeX = "application/pdf";
    		else
    		if (this.mpProcessoAndamentoSelecionado.getTipoFotoBD().toUpperCase().equals("XLS"))
    			contentTypeX = "application/vnd.ms-excel";
    		else
    		if (this.mpProcessoAndamentoSelecionado.getTipoFotoBD().toUpperCase().equals("DOC"))
    			contentTypeX = "application/vnd.ms-word";
    		else
    		if (this.mpProcessoAndamentoSelecionado.getTipoFotoBD().toUpperCase().equals("JPG"))
    			contentTypeX = "image/jpg";
    		//
    		this.fileSC = new DefaultStreamedContent(stream, contentTypeX, "Arquivo_Andamento_" +  
    										this.mpProcessoAndamentoSelecionado.getId() + "." + 
    										this.mpProcessoAndamentoSelecionado.getTipoFotoBD().toUpperCase());
    	}
    	//
    	return this.fileSC;
    }	
    
    // --- Trata DIALOG x Cadastros ...
    
    public void viewParteContrariaJ() {
    	//
        Map<String,Object> options = new HashMap<String, Object>();
        
        options.put("id", "dlgIdParteContrariaJ");
        options.put("widgetVar", "dlgParteContrariaJ");
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 650);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        //
        RequestContext.getCurrentInstance().openDialog(
        		"/sentinel/sisJuri/pessoaJuridicas/mpCadastroPessoaJuridica", options, null);
		//
		this.mpParteContrariaList = mpPessoaSJs.byNomeParteContrariaList();        
    }    
    public void viewParteContrariaF() {
    	//
        Map<String,Object> options = new HashMap<String, Object>();
        
        options.put("id", "dlgIdParteContrariaF");
        options.put("widgetVar", "dlgParteContrariaF");
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 650);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        //
        RequestContext.getCurrentInstance().openDialog(
        		"/sentinel/sisJuri/pessoaFisicas/mpCadastroPessoaFisica", options, null);
		//
		this.mpParteContrariaList = mpPessoaSJs.byNomeParteContrariaList();        
    }
    public void returnParteContraria() {
    	//
//        Object rating = event.getObject();
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "You rated the book with " + rating, null);
//        FacesContext.getCurrentInstance().addMessage(null, message);
		//
		this.mpParteContrariaList = mpPessoaSJs.byNomeParteContrariaList();        

//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.returnParteContrariaJ() - 000");
    }
    
    public void viewClienteSJ() {
    	//
        Map<String,Object> options = new HashMap<String, Object>();
        
        options.put("id", "dlgIdClienteSJ");
        options.put("widgetVar", "dlgClienteSJ");
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 650);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        //
        RequestContext.getCurrentInstance().openDialog(
        		"/sentinel/sisJuri/clientes/mpCadastroCliente", options, null);
		//
		this.mpClienteSJList = mpClienteSJs.byNomeList();
    }    
    public void returnClienteSJ() {
		//
		this.mpClienteSJList = mpClienteSJs.byNomeList();

//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.returnClienteSJ() - 000");
    }
    
    public void viewComarca() {
    	//
        Map<String,Object> options = new HashMap<String, Object>();
        
        options.put("id", "dlgIdComarca");
        options.put("widgetVar", "dlgComarca");
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 650);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        
        List<String> values = new ArrayList<String>();
        
        values.add(this.mpProcesso.getMpComarca().getId().toString());
        
        params.put("mpTabelaInternaSJ", values);
        //
        RequestContext.getCurrentInstance().openDialog(
        		"/sentinel/sisJuri/tabelaInternas/mpCadastroTabelaInternaX", options, params);
		//
		this.mpComarcaList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1008);
    }
    public void returnComarca() {
    	//
		this.mpComarcaList = this.mpTabelaInternaSJs.mpNumeroDescricaoList(MpTipoTabelaInternaSJ.TAB_1008);

//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.returnComarca() - 000");
    }
    
    public void viewResponsavel() {
    	//
        Map<String,Object> options = new HashMap<String, Object>();
        
        options.put("id", "dlgIdResponsavel");
        options.put("widgetVar", "dlgResponsavel");
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 650);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        //
        RequestContext.getCurrentInstance().openDialog(
        		"/sentinel/sisJuri/pessoaFisicas/mpCadastroPessoaFisica", options, null);
		//
		this.mpResponsavelList = mpPessoaFisicas.byNomeList();
    }
    public void returnResponsavel() {
    	//
		this.mpResponsavelList = mpPessoaFisicas.byNomeList();

//		MpAppUtil.PrintarLn("MpCadastroProcessoBean.returnResponsavel() - 000");
    }
        
	// ====================
	// --- Find by Nome ...
	// ====================

	public void findByProcesso() {
		//
    	this.processoFind = "";
    	
//    	MpAppUtil.PrintarLn("MpCadastroCliente.findByNome() = TRUE");
	}

    public List<String> completaFindByProcesso(String query) {
    	//
        List<String> results = new ArrayList<String>();
        
        List<MpProcesso> mpProcessoListX = this.mpProcessos.byProcessoCodigoList();
        
        for (MpProcesso mpProcessoX : mpProcessoListX) {
        	if (mpProcessoX.getProcessoCodigo().toLowerCase().startsWith(query)) {
                results.add(mpProcessoX.getProcessoCodigo() + " ( " + mpProcessoX.getId());
            }
        }
        // 
        return results;
    }
    
    public void onProcessoSelecionado(SelectEvent event) {
    	//
    	String numProcessoIdX = event.getObject().toString();
    	
    	Long idProcessoX = Long.parseLong(numProcessoIdX.substring(numProcessoIdX.indexOf("( ") + 2));
    	
    	MpFacesUtil.addInfoMessage("Processo Selecionado ( " + numProcessoIdX);
    	
    	MpProcesso mpProcessoX = this.mpProcessos.porId(idProcessoX);
    	if (null != mpProcessoX)
    		this.mpProcesso = mpProcessoX;
    	//
    	this.processoFind = "";
    }	

	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		try {
			this.mpProcesso = this.mpProcessos.porNavegacao("mpFirst", sdfX.parse("01/01/1900 00:00:00"));
			if (null == this.mpProcesso)
				this.limpar();
			else
				this.carregarMpComarcaCartorio();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpProcesso.getDataCadastro()) return;
		//
		this.setMpProcessoAnt(this.mpProcesso);
		//
		this.mpProcesso = this.mpProcessos.porNavegacao("mpPrev", mpProcesso.getDataCadastro());
		if (null == this.mpProcesso) {
			this.mpProcesso = this.mpProcessoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.carregarMpComarcaCartorio();
	}

	public void mpNew() {
		//
		this.setMpProcessoAnt(this.mpProcesso);
		
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
		if (null == this.mpProcesso.getId()) return;
		//
		this.setMpProcessoAnt(this.mpProcesso);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpProcesso.getId()) return;
		//
		try {
			this.mpProcessos.remover(mpProcesso);
			
			MpFacesUtil.addInfoMessage("Processo... " + this.sdfX.format(
							this.mpProcesso.getDataCadastro()) + " ... excluído com sucesso.");
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
		//
		this.setMpProcessoAnt(this.mpProcesso);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpProcesso = this.mpProcessoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
		//
		this.carregarMpComarcaCartorio();
	}
	
	public void mpNext() {
		//
		if (null == this.mpProcesso.getDataCadastro()) return;
		//
		this.setMpProcessoAnt(this.mpProcesso);
		//
		this.mpProcesso = this.mpProcessos.porNavegacao("mpNext", mpProcesso.getDataCadastro());
		if (null == this.mpProcesso) {
			this.mpProcesso = this.mpProcessoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.carregarMpComarcaCartorio();
	}
	
	public void mpEnd() {
		//
		try {
			this.mpProcesso = this.mpProcessos.porNavegacao("mpEnd", sdfX.parse("01/01/2099 23:59:59"));
			if (null == this.mpProcesso)
				this.limpar();
			else
				this.carregarMpComarcaCartorio();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpProcesso.getId()) return;
		
		try {
			this.setMpProcessoAnt(this.mpProcesso);
			
			this.mpProcesso = (MpProcesso) this.mpProcesso.clone();
			//
			this.mpProcesso.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	// ---

	private void limpar() {
		//
		this.mpProcesso = new MpProcesso();
		
		this.mpProcesso.setTenantId(mpSeguranca.capturaTenantId());
		this.mpProcesso.setDataCadastro(new Date());
		
		this.mpComarcaCartorioList.clear();
		this.mpProcessoAndamentoDelList.clear();
		//
		this.trataLimpezaDet();
	}
	
	public void trataLimpezaDet() {
		//
//		FacesContext context = FacesContext.getCurrentInstance();
//
//		String idX = "pGridAndamento";
//		UIComponent iamLookingFor = findComponent(idX, context.getViewRoot());
//				
//		if (null == iamLookingFor)
//			System.out.println("MpCadastroProcessoBean.trataLimpezaDet() ( NULL = " + idX);
//		else
//			System.out.println("MpCadastroProcessoBean.trataLimpezaDet() ( idX = " + iamLookingFor.getId() + 
//				" / " + iamLookingFor.getClientId() + " / " + iamLookingFor.getContainerClientId(context));		
		//		
		this.mpProcessoAndamento = new MpProcessoAndamento();

		this.mpProcessoAndamento.setMpProcesso(this.mpProcesso);
		this.mpProcessoAndamento.setTenantId(this.mpProcesso.getTenantId());
		this.mpProcessoAndamento.setDataCadastro(new Date());
		this.mpProcessoAndamento.setTenantId(this.mpProcesso.getTenantId());
		//
//		this.mpProcesso.getMpAndamentos().add(this.mpProcessoAndamento);
		
		this.mpProcessoAndamentoAnt = new MpProcessoAndamento();
		this.mpProcessoAndamentoAnt.setTenantId(this.mpProcesso.getTenantId());
		
		this.indAlteracaoDet = false;
	}
          	
	// --- 

	public MpProcesso getMpProcesso() { return mpProcesso; }
	public void setMpProcesso(MpProcesso mpProcesso) { this.mpProcesso = mpProcesso; }

	public MpProcesso getMpProcessoSel() { return mpProcessoSel; }
	public void setMpProcessoSel(MpProcesso mpProcessoSel) { this.mpProcessoSel = mpProcessoSel; }

	public MpProcesso getMpProcessoAnt() { return mpProcessoAnt; }
	public void setMpProcessoAnt(MpProcesso mpProcessoAnt) {
		try {
			this.mpProcessoAnt = (MpProcesso) this.mpProcesso.clone();
			this.mpProcessoAnt.setId(this.mpProcesso.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpProcessoAndamento getMpProcessoAndamento() { return mpProcessoAndamento; }
	public void setMpProcessoAndamento(MpProcessoAndamento mpProcessoAndamento) {
													this.mpProcessoAndamento = mpProcessoAndamento; }

	public MpProcessoAndamento getMpProcessoAndamentoSelecionado() { return mpProcessoAndamentoSelecionado; }
	public void setMpProcessoAndamentoSelecionado(MpProcessoAndamento mpProcessoAndamentoSelecionado) {
										this.mpProcessoAndamentoSelecionado = mpProcessoAndamentoSelecionado; }
	
	public List<MpPessoaSJ> getMpParteContrariaList() { return mpParteContrariaList; }
	public List<MpClienteSJ> getMpClienteSJList() { return mpClienteSJList; }
	public List<MpTabelaInternaSJ> getMpComarcaList() { return mpComarcaList; }
	public List<MpTabelaInternaSJ> getMpComarcaCartorioList() { return mpComarcaCartorioList; }
	public List<MpPessoaFisica> getMpResponsavelList() { return mpResponsavelList; }

	public List<MpTabelaInternaSJ> getMpAtoPraticadoList() { return mpAtoPraticadoList; }
	public List<MpTabelaInternaSJ> getMpDetalhamentoList() { return mpDetalhamentoList; }
	public List<MpTabelaInternaSJ> getMpAndamentoTipoList() { return mpAndamentoTipoList; }

	// ---
	
	public boolean isEditando() { return this.mpProcesso.getId() != null; }
	
	public boolean getIndVisivelDet() { return indVisivelDet; }
	public void setIndVisivelDet(Boolean indVisivelDet) { this.indVisivelDet = indVisivelDet; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public String getDialogPagina() { return dialogPagina; }
	public void setDialogPagina(String dialogPagina) { this.dialogPagina = dialogPagina; }
	
	public String getDialogMensagem() { return dialogMensagem; }
	public void setDialogMensagem(String dialogMensagem) { this.dialogMensagem = dialogMensagem; }
	
	public String getProcessoFind() { return processoFind; }
	public void setProcessoFind(String processoFind) { this.processoFind = processoFind; }
		
	// ---

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
	
    public StreamedContent getFileSC() { return fileSC; }
	
}