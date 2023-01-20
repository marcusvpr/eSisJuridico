package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpTenant;
import com.mpxds.mpbasic.model.enums.MpStatusObjeto;
import com.mpxds.mpbasic.repository.MpTenants;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpTenantService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTenantBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpTenants mpTenants;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTenantService mpTenantService;
	
	// ---

	private MpTenant mpTenant;
	private MpTenant mpTenantAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";

	private MpStatusObjeto mpStatusObjeto;
	private List<MpStatusObjeto> mpStatusObjetoList = new ArrayList<MpStatusObjeto>();

	//---
		
	public MpCadastroTenantBean() {
		if (null == this.mpTenant)
			this.limpar();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpTenant) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpTenant.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpTenantAnt(this.mpTenant);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		//
		this.mpStatusObjetoList = Arrays.asList(MpStatusObjeto.values());
	}
	
	private void limpar() {
		this.mpTenant = new MpTenant();
		//
		this.mpTenant.setCodigo("");		
		this.mpTenant.setDescricao("");		
	}
	
	public void salvar() {
		//
		this.mpTenant = mpTenantService.salvar(this.mpTenant);
		//
		MpFacesUtil.addInfoMessage("Tenant... salva com sucesso!");
	}

	// -------- Trata Navegação ...

	public void mpFirst() {
		this.mpTenant = this.mpTenants.porNavegacao("mpFirst", " "); 
		if (null == this.mpTenant)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		if (null == this.mpTenant.getCodigo()) return;
		//
		this.setMpTenantAnt(this.mpTenant);
		//
		this.mpTenant = this.mpTenants.porNavegacao("mpPrev", mpTenant.getCodigo());
		if (null == this.mpTenant) {
			this.mpTenant = this.mpTenantAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		this.setMpTenantAnt(this.mpTenant);
		
		this.mpTenant = new MpTenant();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		if (null == this.mpTenant.getId())
			return;
		//
		this.setMpTenantAnt(this.mpTenant);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		if (null == this.mpTenant.getId()) return;
		//
		try {
			this.mpTenants.remover(mpTenant);
			
			MpFacesUtil.addInfoMessage("Tenant... " + this.mpTenant.getCodigo()
																	+ " excluído com sucesso.");
			//
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
		
		this.setMpTenantAnt(this.mpTenant);
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
		this.mpTenant = this.mpTenantAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		if (null == this.mpTenant.getCodigo()) return;
		//
		this.setMpTenantAnt(this.mpTenant);
		//
		this.mpTenant = this.mpTenants.porNavegacao("mpNext", mpTenant.getCodigo());
		if (null == this.mpTenant) {
			this.mpTenant = this.mpTenantAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		this.mpTenant = this.mpTenants.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpTenant)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpTenant.getId()) return;

		try {
			this.setMpTenantAnt(this.mpTenant);

			this.mpTenant = (MpTenant) this.mpTenant.clone();
			//
			this.mpTenant.setId(null);
			
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

	public MpTenant getMpTenant() { return mpTenant; }
	public void setMpTenant(MpTenant mpTenant) { this.mpTenant = mpTenant; }

	public MpTenant getMpTenantAnt() { return mpTenantAnt; }
	public void setMpTenantAnt(MpTenant mpTenantAnt) {
		//
		try {
			this.mpTenantAnt = (MpTenant) this.mpTenant.clone();
			this.mpTenantAnt.setId(this.mpTenant.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpStatusObjeto getMpStatusObjeto() { return mpStatusObjeto; }
	public void setMpStatusObjeto(MpStatusObjeto mpStatusObjeto) {
																this.mpStatusObjeto = mpStatusObjeto; }
	public List<MpStatusObjeto> getMpStatusObjetoList() { return mpStatusObjetoList; }

	public boolean isEditando() { return this.mpTenant.getId() != null; }
	
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}