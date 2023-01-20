package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.model.enums.MpGrupoTenant;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpUsuarioTenantService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
@ManagedBean(name = "mpCadastroUsuarioTenantBean")
public class MpCadastroUsuarioTenantBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpPessoas mpPessoas;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpUsuarioTenantService mpUsuarioTenantService;

	@Inject
	private MpAlertaService mpAlertaService;

	// ---
	
	private MpPessoa mpPessoa;
	private List<MpPessoa> mpPessoaList = new ArrayList<MpPessoa>();
	
	private MpUsuarioTenant mpUsuarioTenant = new MpUsuarioTenant();
	private MpUsuarioTenant mpUsuarioTenantAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private String usuarioGrupos = "";
		
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList = new ArrayList<MpStatus>();
		
	private MpGrupoTenant mpGrupoTenant;
	private List<MpGrupoTenant> mpGrupoTenantList = new ArrayList<MpGrupoTenant>();
	
	// ---------------------

	public MpCadastroUsuarioTenantBean() {
		//
		if (null == this.mpUsuarioTenant)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpUsuarioTenant) {
			this.limpar();
			// Posiciona no usuário logado !!!
			this.mpNext();; 
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpUsuarioTenant.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		//
		this.indEditavelNav = this.mpUsuarioTenant.getIndBarraNavegacao();
		
		this.mpPessoaList = mpPessoas.porPessoaAtivoList();
		
		this.mpStatusList = Arrays.asList(MpStatus.values());
		//
//		this.mpGrupoTenantList = Arrays.asList(MpGrupoTenant.values());
		// Verifica filtro para controle de Estoque ...
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getLoginGrupo();
		else
			this.usuarioGrupos = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getGruposNome();
		//
		for (MpGrupoTenant mpGrupoTenantX : Arrays.asList(MpGrupoTenant.values())) {
			//
			if (this.usuarioGrupos.indexOf("SK_ADMIN") >= 0) {
				if (mpGrupoTenantX.getIndControleEstoque())
					this.mpGrupoTenantList.add(mpGrupoTenantX);
			} else
				if (!mpGrupoTenantX.getIndControleEstoque())
					this.mpGrupoTenantList.add(mpGrupoTenantX);
		}
	}

	public void salvar() {
		//
		// Trata Alerta ---------------------------
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpUsuarioTenant.getMpAlerta());
		this.mpUsuarioTenant.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------
		
		this.mpUsuarioTenant = this.mpUsuarioTenantService.salvar(this.mpUsuarioTenant);
		//
		MpFacesUtil.addInfoMessage("Usuário Ambiente... salvo com sucesso!");
	}

	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpUsuarioTenant = this.mpUsuarioTenants.porNavegacao("mpFirst", " "); 
		if (null == this.mpUsuarioTenant)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpUsuarioTenant.getLogin()) return;
		//
		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		//
		this.mpUsuarioTenant = this.mpUsuarioTenants.porNavegacao("mpPrev",
																		mpUsuarioTenant.getLogin());
		if (null == this.mpUsuarioTenant) {
			this.mpUsuarioTenant = this.mpUsuarioTenantAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		
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
		if (null == this.mpUsuarioTenant.getId()) return;
		//
		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpUsuarioTenant.getId())	return;
		//
		try {
			this.mpUsuarioTenants.remover(mpUsuarioTenant);
			
			MpFacesUtil.addInfoMessage("Usuário... " + this.mpUsuarioTenant.getLogin()
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

		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioTenant().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpUsuarioTenant = this.mpUsuarioTenantAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpUsuarioTenant.getLogin()) return;
		//
		this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);
		//
		this.mpUsuarioTenant = this.mpUsuarioTenants.porNavegacao("mpNext", mpUsuarioTenant.getLogin());
		if (null == this.mpUsuarioTenant) {
			this.mpUsuarioTenant = this.mpUsuarioTenantAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}
	
	public void mpEnd() {
		//
		this.mpUsuarioTenant = this.mpUsuarioTenants.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpUsuarioTenant)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpUsuarioTenant.getId()) return;

		try {
			this.setMpUsuarioTenantAnt(this.mpUsuarioTenant);

			this.mpUsuarioTenant = (MpUsuarioTenant) this.mpUsuarioTenant.clone();
			//
			this.mpUsuarioTenant.setId(null);
			
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
		this.mpUsuarioTenant = new MpUsuarioTenant();

		this.mpUsuarioTenant.setTenantId(mpSeguranca.capturaTenantId());
		this.mpUsuarioTenant.setLogin("");
		//
	}
		
	// ---
	
	public boolean isEditando() { return this.mpUsuarioTenant.getId() != null; }
	
	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) { 
															this.mpUsuarioTenant = mpUsuarioTenant; }

	public MpUsuarioTenant getMpUsuarioTenantAnt() { return mpUsuarioTenantAnt; }
	public void setMpUsuarioTenantAnt(MpUsuarioTenant mpUsuarioTenantAnt) { 
		//
		try {
			this.mpUsuarioTenantAnt = (MpUsuarioTenant) this.mpUsuarioTenant.clone();
			this.mpUsuarioTenantAnt.setId(this.mpUsuarioTenant.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpPessoa getMpPessoa() { return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
	public List<MpPessoa> getMpPessoaList() { return mpPessoaList; }

	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpGrupoTenant getMpGrupoTenant() { return mpGrupoTenant; }
	public void setMpGrupoTenant(MpGrupoTenant mpGrupoTenant) { this.mpGrupoTenant = mpGrupoTenant; }
	public List<MpGrupoTenant> getMpGrupoTenantList() { return mpGrupoTenantList; }

	// --- 
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}