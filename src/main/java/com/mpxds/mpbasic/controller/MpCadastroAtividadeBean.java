package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.enums.MpAtividadeTipo;
import com.mpxds.mpbasic.model.enums.MpPeriodicidade;
import com.mpxds.mpbasic.repository.MpAtividades;
import com.mpxds.mpbasic.repository.MpPacientes;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.filter.MpPacienteFilter;
import com.mpxds.mpbasic.repository.filter.MpProdutoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpAtividadeService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroAtividadeBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAtividades mpAtividades;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpAtividadeService mpAtividadeService;

	@Inject
	private MpProdutos mpProdutos;

	@Inject
	private MpPacientes mpPacientes;

	@Inject
	private MpAlertaService mpAlertaService;
	
	// ---

	private MpAtividade mpAtividade = new MpAtividade();
	private MpAtividade mpAtividadeAnt;

	private MpObjeto mpObjetoHelp;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
		
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
    private UploadedFile file;
	
	private MpPeriodicidade mpPeriodicidade;
	private List<MpPeriodicidade> mpPeriodicidadeList;
	
	private MpAtividadeTipo mpAtividadeTipo;
	private List<MpAtividadeTipo> mpAtividadeTipoList;
	
	private MpProduto mpProduto;
	private List<MpProduto> mpProdutoList;
	private MpProdutoFilter mpProdutoFilter = new MpProdutoFilter();

	private MpPaciente mpPaciente;
	private List<MpPaciente> mpPacienteList;
	private MpPacienteFilter mpPacienteFilter = new MpPacienteFilter();
	
	// ------
	
	public MpCadastroAtividadeBean() {
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
		//
		if (null == this.mpAtividade)
			limpar();
		//	
//		if (null == mpSeguranca)
//			System.out.println("MpCadastroAtividadeBean() - mpSeguranca ( null");
//		else
//			if (null == mpSeguranca.capturaTenantId())
//				System.out.println("MpCadastroAtividadeBean.capturaTenantId() ( null");
//			else
//				System.out.println("MpCadastroAtividadeBean.capturaTenantId() ( tenantId = " +
//																mpSeguranca.capturaTenantId());
	}
	
	public void inicializar() {
		//
		if (null == this.mpAtividade) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpAtividade.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpAtividadeAnt(this.mpAtividade);
		//
		this.mpPeriodicidadeList = Arrays.asList(MpPeriodicidade.values());
		this.mpAtividadeTipoList = Arrays.asList(MpAtividadeTipo.values());
		this.mpProdutoList = mpProdutos.filtrados(mpProdutoFilter);
		this.mpPacienteList = mpPacientes.filtrados(mpPacienteFilter);
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																		getIndBarraNavegacao();
		else
			this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	public void salvar() {
		//
		// Trata Alerta ---------------------------
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpAtividade.getMpAlerta());
		this.mpAtividade.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------
		//
		this.mpAtividade = mpAtividadeService.salvar(this.mpAtividade);

		MpFacesUtil.addInfoMessage("ATIVIDADE... salva com sucesso!");
		//
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		Date dateX = new Date();
		try {
			dateX = sdf.parse("01/01/1900");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//
		this.mpAtividade = this.mpAtividades.porNavegacao("mpFirst", dateX); 
		if (null == this.mpAtividade)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpAtividade.getDtHrAtividade()) return;
		//
		this.setMpAtividadeAnt(this.mpAtividade);
		//
		this.mpAtividade = this.mpAtividades.porNavegacao("mpPrev", 
				  												 mpAtividade.getDtHrAtividade());
		if (null == this.mpAtividade) {
			this.mpAtividade = this.mpAtividadeAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else {
			this.txtModoTela = "( Anterior )";
		}
	}

	public void mpNew() {
		//
		this.setMpAtividadeAnt(this.mpAtividade);
		
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
		if (null == this.mpAtividade.getId()) return;
		//
		this.setMpAtividadeAnt(this.mpAtividade);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpAtividade.getId()) return;
		//
		try {
			this.mpAtividades.remover(mpAtividade);
			
			MpFacesUtil.addInfoMessage("Atividade... "
												+ sdf.format(this.mpAtividade.getDtHrAtividade())
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

		this.setMpAtividadeAnt(this.mpAtividade);
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
		this.mpAtividade = this.mpAtividadeAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpAtividade.getDtHrAtividade()) return;
		//
		this.setMpAtividadeAnt(this.mpAtividade);
		//
		this.mpAtividade = this.mpAtividades.porNavegacao("mpNext", mpAtividade.getDtHrAtividade());
		if (null == this.mpAtividade) {
			this.mpAtividade = this.mpAtividadeAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else {
			this.txtModoTela = "( Próximo )";
		}
	}

	public void mpEnd() {
		//
		Date dateX = new Date();
		try {
			dateX = sdf.parse("31/12/2100");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//
		this.mpAtividade = this.mpAtividades.porNavegacao("mpEnd",	dateX); 
		if (null == this.mpAtividade)
			this.limpar();
		//
//		else
//			System.out.println("MpCadastroAtividadeBean.mpEnd() ( Entrou 0001 = " +
//															this.mpAtividade.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpAtividade.getId()) return;

		try {
			this.setMpAtividadeAnt(this.mpAtividade);
			
			this.mpAtividade = (MpAtividade) this.mpAtividade.clone();
			//
			this.mpAtividade.setId(null);
			
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
	}
	
	private void limpar() {
		//
		this.mpAtividade = new MpAtividade();
		
		this.mpAtividade.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpAtividade.setIndAtivo(true);
		this.mpAtividade.setIndInicioFim(true);
		//
		this.mpAtividade.setDtHrAtividade(new Date());		
		//
		MpAlerta mpAlertaX = new MpAlerta(false, false, false, false, false, false, false);
		//
		try {
			if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
															getMpUsuarioTenant().getMpAlerta().clone();
			else
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
																getMpUsuario().getMpAlerta().clone();
			//
//			System.out.println("MpCadastroAtividadeBean.limpar() - ( MpAlerta = " +
//																mpAlertaX.getConfiguracao());
			//
		} catch (CloneNotSupportedException e) {
				e.printStackTrace();
		}
		//
		this.mpAtividade.setMpAlerta(mpAlertaX);		
	}
		
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public MpAtividade getMpAtividade() { return mpAtividade; }
	public void setMpAtividade(MpAtividade mpAtividade) { this.mpAtividade = mpAtividade; }

	public MpAtividade getMpAtividadeAnt() { return mpAtividadeAnt; }
	public void setMpAtividadeAnt(MpAtividade mpAtividadeAnt) { 
		try {
			this.mpAtividadeAnt = (MpAtividade) this.mpAtividade.clone();
			this.mpAtividadeAnt.setId(this.mpAtividade.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	public boolean isEditando() { return this.mpAtividade.getId() != null; }

	public MpPeriodicidade getMpPeriodicidade() { return mpPeriodicidade; }
	public void setMpPeriodicidade(MpPeriodicidade mpPeriodicidade) {
															this.mpPeriodicidade = mpPeriodicidade;	}
	public List<MpPeriodicidade> getMpPeriodicidadeList() {	return mpPeriodicidadeList; }

	public MpAtividadeTipo getMpAtividadeTipo() { return mpAtividadeTipo; }
	public void setMpAtividadeTipo(MpAtividadeTipo mpAtividadeTipo) {
															this.mpAtividadeTipo = mpAtividadeTipo;	}
	public List<MpAtividadeTipo> getMpAtividadeTipoList() {	return mpAtividadeTipoList;	}

	public MpProduto getMpProduto() { return mpProduto; }
	public void setMpProduto(MpProduto mpProduto) { this.mpProduto = mpProduto; }
	public List<MpProduto> getMpProdutoList() { return mpProdutoList; }
	
	public MpProdutoFilter getMpProdutoFilter() { return mpProdutoFilter; }
	public void setMpProdutoFilter(MpProdutoFilter mpProdutoFilter) { 
															this.mpProdutoFilter = mpProdutoFilter; }

	public MpPaciente getMpPaciente() { return mpPaciente; }
	public void setMpPaciente(MpPaciente mpPaciente) { this.mpPaciente = mpPaciente; }
	public List<MpPaciente> getMpPacienteList() { return mpPacienteList; }
	
	public MpPacienteFilter getMpPacienteFilter() { return mpPacienteFilter; }
	public void setMpPacienteFilter(MpPacienteFilter mpPacienteFilter) { 
														this.mpPacienteFilter = mpPacienteFilter; }

	public UploadedFile getFile() { return this.file; }	
    public void setFile(UploadedFile file) { this.file = file; }
    
}