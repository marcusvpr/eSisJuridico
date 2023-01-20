package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.repository.MpDolars;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpDolarService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroDolarBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpDolars mpDolars;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpDolarService mpDolarService;
	
	// ---

	private MpDolar mpDolar = new MpDolar();
	private MpDolar mpDolarAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// ------------------
	
	public MpCadastroDolarBean() {
//		System.out.println("MpCadastroDolarBean - Entrou 0000 ");
		//
		if (null == this.mpDolar)
			this.limpar();
	}
	
	public void inicializar() {
		//
		HttpServletRequest request = (HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext().getRequest();
		
		String param = request.getParameter("param");
		if (null == param) param = "null";
//		System.out.println("MpCadastroDolarBean.inicializar() - ( param = " + param);
		if (param.equals("login"))
			this.mpNew(); // Posiciona no novo registro !!!
		else
			if (null == this.mpDolar) {
				this.limpar();
				//
				this.mpEnd(); // Posiciona no último registro !!!
			}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpDolar.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpDolarAnt(this.mpDolar);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
	}
	
	private void limpar() {
		//
		this.mpDolar = new MpDolar();
		
		this.mpDolar.setDataMovimento(new Date());
	}
	
	public void salvar() {
		//
		this.mpDolar = this.mpDolarService.salvar(this.mpDolar);

		MpFacesUtil.addInfoMessage("Dolar... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		try {
			this.mpDolar = this.mpDolars.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpDolar)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		if (null == this.mpDolar.getDataMovimento()) return;
		//
		this.setMpDolarAnt(this.mpDolar);
		//
		this.mpDolar = this.mpDolars.porNavegacao("mpPrev", mpDolar.getDataMovimento());
		if (null == this.mpDolar) {
			this.mpDolar = this.mpDolarAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpDolarAnt(this.mpDolar);
		
		this.mpDolar = new MpDolar();
		
		this.mpDolar.setTenantId(mpSeguranca.capturaTenantId());
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpDolar.getId()) return;
		//
		this.setMpDolarAnt(this.mpDolar);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpDolar.getId()) return;
		//
		try {
			this.mpDolars.remover(mpDolar);
			
			MpFacesUtil.addInfoMessage("Dolar... " + this.sdf.format(
							this.mpDolar.getDataMovimento()) + " ... excluído com sucesso.");
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

		this.setMpDolarAnt(this.mpDolar);
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
		this.mpDolar = this.mpDolarAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		if (null == this.mpDolar.getDataMovimento()) return;
		//
		this.setMpDolarAnt(this.mpDolar);
		//
		this.mpDolar = this.mpDolars.porNavegacao("mpNext", mpDolar.getDataMovimento());
		if (null == this.mpDolar) {
			this.mpDolar = this.mpDolarAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		try {
			this.mpDolar = this.mpDolars.porNavegacao("mpEnd", sdf.parse("01/01/2099"));
			if (null == this.mpDolar)
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
		if (null == this.mpDolar.getId()) return;
		
		try {
			this.setMpDolarAnt(this.mpDolar);
			
			this.mpDolar = (MpDolar) this.mpDolar.clone();
			//
			this.mpDolar.setId(null);
			
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

	public MpDolar getMpDolar() { return mpDolar; }
	public void setMpDolar(MpDolar mpDolar) { this.mpDolar = mpDolar; }

	public MpDolar getMpDolarAnt() { return mpDolarAnt; }
	public void setMpDolarAnt(MpDolar mpDolarAnt) {
		try {
			this.mpDolarAnt = (MpDolar) this.mpDolar.clone();
			this.mpDolarAnt.setId(this.mpDolar.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpDolar.getId() != null; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}