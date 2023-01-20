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

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpNotificacao;
import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.model.enums.MpTipoPrioridade;
import com.mpxds.mpbasic.repository.MpNotificacaos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpNotificacaoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroNotificacaoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpNotificacaos mpNotificacaos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpNotificacaoService mpNotificacaoService;

	@Inject
	private MpAlertaService mpAlertaService;
	
	// ---

	private MpNotificacao mpNotificacao = new MpNotificacao();
	private MpNotificacao mpNotificacaoAnt;
	
	private MpNotificacaoUsuario mpNotificacaoUsuario = new MpNotificacaoUsuario();

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
		
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private MpStatusNotificacao mpStatusNotificacao;
	private List<MpStatusNotificacao> mpStatusNotificacaoList;
	
	private MpTipoPrioridade mpTipoPrioridade;
	private List<MpTipoPrioridade> mpTipoPrioridadeList;
	
	// ------
	
	public MpCadastroNotificacaoBean() {
		//
		if (null == this.mpNotificacao)
			limpar();
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
		//
	}
	
	public void inicializar() throws ParseException {
		//
		if (null == this.mpNotificacao) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpNotificacao.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpNotificacaoAnt(this.mpNotificacao);
		//
		this.mpStatusNotificacaoList = Arrays.asList(MpStatusNotificacao.values());
		this.mpTipoPrioridadeList = Arrays.asList(MpTipoPrioridade.values());
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
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpNotificacao.getMpAlerta());
		this.mpNotificacao.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------
		//
		this.mpNotificacao = mpNotificacaoService.salvar(this.mpNotificacao);
		//
		MpFacesUtil.addInfoMessage("Notificação... salva com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() throws ParseException {
		//
		this.mpNotificacao = this.mpNotificacaos.porNavegacao("mpFirst", sdf.parse("01/01/1900")); 
		if (null == this.mpNotificacao) {
//			System.out.println("MpCadastroNotificacaoBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroNotificacaoBean.mpFirst() ( Entrou 0001 = " +
//															this.mpNotificacao.getParametro());
		//
		this.txtModoTela = "( Início )";
		//
	}
	
	public void mpPrev() {
		//
		if (null == this.mpNotificacao.getDataDe()) return;
		//
		this.setMpNotificacaoAnt(this.mpNotificacao);
		//
		this.mpNotificacao = this.mpNotificacaos.porNavegacao("mpPrev", mpNotificacao.getDataDe());
		if (null == this.mpNotificacao) {
			this.mpNotificacao = this.mpNotificacaoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
	}

	public void mpNew() {
		//
		this.setMpNotificacaoAnt(this.mpNotificacao);
		//
		this.limpar();		
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
		//
	}
	
	public void mpEdit() {
		//
		if (null == this.mpNotificacao.getId()) return;
		//
		this.setMpNotificacaoAnt(this.mpNotificacao);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpNotificacao.getId()) return;
		//
		try {
			this.mpNotificacaos.remover(mpNotificacao);
			
			MpFacesUtil.addInfoMessage("Notificação... "
												+ sdf.format(this.mpNotificacao.getDataDe())
												+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		if (this.mpNotificacao.getDataDe().after(this.mpNotificacao.getDataAte())) {
			//
			MpFacesUtil.addInfoMessage("Data Inicical... não pode ser maior que a data Final !");
			return;
		}
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}

		this.setMpNotificacaoAnt(this.mpNotificacao);
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
		this.mpNotificacao = this.mpNotificacaoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
		//
	}
	
	public void mpNext() {
		//
		if (null == this.mpNotificacao.getDataDe()) return;
		//
		this.setMpNotificacaoAnt(this.mpNotificacao);
		//
		this.mpNotificacao = this.mpNotificacaos.porNavegacao("mpNext", mpNotificacao.getDataDe());
		if (null == this.mpNotificacao) {
			this.mpNotificacao = this.mpNotificacaoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
	}
	
	public void mpEnd() throws ParseException {
		//
		this.mpNotificacao = this.mpNotificacaos.porNavegacao("mpEnd", sdf.parse("01/01/2900")); 
		if (null == this.mpNotificacao) {
//			System.out.println("MpCadastroNotificacaoBean.mpEnd() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroNotificacaoBean.mpEnd() ( Entrou 0001 = " +
//															this.mpNotificacao.getParametro());
		this.txtModoTela = "( Fim )";
		//
	}
	
	public void mpClone() {
		//
		if (null == this.mpNotificacao.getId()) return;

		try {
			this.setMpNotificacaoAnt(this.mpNotificacao);
			
			this.mpNotificacao = (MpNotificacao) this.mpNotificacao.clone();
			//
			this.mpNotificacao.setId(null);
			//
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
		this.mpNotificacao = new MpNotificacao();
		
		this.mpNotificacaoUsuario = new MpNotificacaoUsuario();
		//
		this.mpNotificacao.setDataDe(new Date());		
		this.mpNotificacao.setDataAte(new Date());		
		this.mpNotificacao.setMensagem("");		
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
//			System.out.println("MpCadastroNotificacaoBean.limpar() - ( MpAlerta = " +
//																mpAlertaX.getConfiguracao());
			//
		} catch (CloneNotSupportedException e) {
				e.printStackTrace();
		}
		//
		this.mpNotificacao.setMpAlerta(mpAlertaX);		
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

	public MpNotificacao getMpNotificacao() { return mpNotificacao; }
	public void setMpNotificacao(MpNotificacao mpNotificacao) { this.mpNotificacao = mpNotificacao; }

	public MpNotificacaoUsuario getMpNotificacaoUsuario() { return mpNotificacaoUsuario; }
	public void setMpNotificacaoUsuario(MpNotificacaoUsuario mpNotificacaoUsuario) { 
											this.mpNotificacaoUsuario = mpNotificacaoUsuario; }

	public MpNotificacao getMpNotificacaoAnt() { return mpNotificacaoAnt; }
	public void setMpNotificacaoAnt(MpNotificacao mpNotificacaoAnt) {
		try {
			this.mpNotificacaoAnt = (MpNotificacao) this.mpNotificacao.clone();
			this.mpNotificacaoAnt.setId(this.mpNotificacao.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public boolean isEditando() { return this.mpNotificacao.getId() != null; }

	public MpStatusNotificacao getMpStatusNotificacao() { return mpStatusNotificacao; }
	public void setMpStatusNotificacao(MpStatusNotificacao mpStatusNotificacao) {
												this.mpStatusNotificacao = mpStatusNotificacao;	}
	public List<MpStatusNotificacao> getMpStatusNotificacaoList() { 
																return mpStatusNotificacaoList;	}
	
	public MpTipoPrioridade getMpTipoPrioridade() { return mpTipoPrioridade; }
	public void setMpTipoPrioridade(MpTipoPrioridade mpTipoPrioridade) {
													this.mpTipoPrioridade = mpTipoPrioridade; }
	public List<MpTipoPrioridade> getMpTipoPrioridadeList() {	return mpTipoPrioridadeList; }

}