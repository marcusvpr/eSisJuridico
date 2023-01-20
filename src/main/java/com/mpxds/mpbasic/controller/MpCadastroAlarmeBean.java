package com.mpxds.mpbasic.controller;

import java.io.Serializable;
//import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlarmeService;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroAlarmeBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlarmes mpAlarmes;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpAlarmeService mpAlarmeService;

	@Inject
	private MpAlertaService mpAlertaService;
	
	// ---

	private MpAlarme mpAlarme = new MpAlarme();
	private MpAlarme mpAlarmeAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// ------------------
	
	public MpCadastroAlarmeBean() {
//		System.out.println("MpCadastroAlarmeBean - Entrou 0000 ");
		//
		if (null == this.mpAlarme)
			this.limpar();
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
	}
	
	public void inicializar() {
		//
		if (null == this.mpAlarme) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpAlarme.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}

		this.setMpAlarmeAnt(this.mpAlarme);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
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
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpAlarme.getMpAlerta());
		this.mpAlarme.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------
		//
		this.mpAlarme = this.mpAlarmeService.salvar(this.mpAlarme);
		// this.limpar();
		MpFacesUtil.addInfoMessage("Alarme... salvo com sucesso!");
		//
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		try {
			this.mpAlarme = this.mpAlarmes.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpAlarme)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpAlarme.getHoraMovimento()) return;
		//
		this.setMpAlarmeAnt(this.mpAlarme);
		//		
		this.mpAlarme = this.mpAlarmes.porNavegacao("mpPrev", mpAlarme.getHoraMovimento());
		if (null == this.mpAlarme) {
			this.mpAlarme = this.mpAlarmeAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpAlarmeAnt(this.mpAlarme);
		
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
		if (null == this.mpAlarme.getId()) return;
		//
		this.setMpAlarmeAnt(this.mpAlarme);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpAlarme.getId()) return;
		//
		try {
			this.mpAlarmes.remover(mpAlarme);
			//
			MpFacesUtil.addInfoMessage("Alarme... " + this.sdf.format(
								this.mpAlarme.getHoraMovimento()) + " excluído com sucesso.");
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

		this.setMpAlarmeAnt(this.mpAlarme);
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
		this.mpAlarme = this.mpAlarmeAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpAlarme.getHoraMovimento()) return;
		//
		this.setMpAlarmeAnt(this.mpAlarme);
		//		
		this.mpAlarme = this.mpAlarmes.porNavegacao("mpNext", mpAlarme.getHoraMovimento());
		if (null == this.mpAlarme) {
			this.mpAlarme = this.mpAlarmeAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		try {
			this.mpAlarme = this.mpAlarmes.porNavegacao("mpEnd", sdf.parse("01/01/2099"));
			if (null == this.mpAlarme)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpAlarme.getId()) return;

		try {
			this.setMpAlarmeAnt(this.mpAlarme);
			this.mpAlarme = (MpAlarme) this.mpAlarme.clone();
			//
			this.mpAlarme.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	private void limpar() {
		//
		this.mpAlarme = new MpAlarme();
		this.mpAlarme.setIndAtivo(true);
		//
//		this.mpAlarme.setHoraMovimento(new Time());
		//
		//
		MpAlerta mpAlertaX = new MpAlerta(false, false, false, false, false, false, false);
		
		try {
			if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
													getMpUsuarioTenant().getMpAlerta().clone();
			else
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
														getMpUsuario().getMpAlerta().clone();
			//
//			System.out.println("MpCadastroAlarmeBean.limpar() - ( MpAlerta = " +
//																mpAlertaX.getConfiguracao());
			//
		} catch (CloneNotSupportedException e) {
				e.printStackTrace();
		}
		//
		this.mpAlarme.setMpAlerta(mpAlertaX);		
	}
		
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) {
														this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	// ---

	public MpAlarme getMpAlarme() { return mpAlarme; }
	public void setMpAlarme(MpAlarme mpAlarme) { this.mpAlarme = mpAlarme; }

	public MpAlarme getMpAlarmeAnt() { return mpAlarmeAnt; }
	public void setMpAlarmeAnt(MpAlarme mpAlarme) {
		//
		try {
			this.mpAlarmeAnt = (MpAlarme) this.mpAlarme.clone();
			this.mpAlarmeAnt.setId(this.mpAlarme.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpAlarme.getId() != null; }

}