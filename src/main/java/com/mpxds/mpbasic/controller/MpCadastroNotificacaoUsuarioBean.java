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
import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.model.enums.MpTipoPrioridade;
import com.mpxds.mpbasic.repository.MpNotificacaoUsuarios;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpNotificacaoUsuarioService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroNotificacaoUsuarioBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpNotificacaoUsuarios mpNotificacaoUsuarios;

	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpNotificacaoUsuarioService mpNotificacaoUsuarioService;

	@Inject
	private MpAlertaService mpAlertaService;
	
	// ---

	private MpNotificacaoUsuario mpNotificacaoUsuario = new MpNotificacaoUsuario();
	private MpNotificacaoUsuario mpNotificacaoUsuarioAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelPerfil = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
		
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private MpStatusNotificacao mpStatusNotificacao;
	private List<MpStatusNotificacao> mpStatusNotificacaoList;
		
	private MpTipoPrioridade mpTipoPrioridade;
	private List<MpTipoPrioridade> mpTipoPrioridadeList;
	
//	private String nomeMpUsuarioNotificacao;
		
	// ------
	
	public MpCadastroNotificacaoUsuarioBean() {
		//
		if (null == this.mpNotificacaoUsuario)
			limpar();
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
		//
	}
	
	public void inicializar() throws ParseException {
		//
		if (null == this.mpNotificacaoUsuario) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpNotificacaoUsuario.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
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
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.mpNotificacaoUsuario.setNomeUsuarioFrom(
					this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().getMpPessoa().getNome());
		else
			this.mpNotificacaoUsuario
					.setNomeUsuarioFrom(this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getNome());
		//
		String nome = "";

		if (this.mpSeguranca.getNomeMpUsuarioNotificacao().indexOf("(Id=") > 0) {
			nome = this.mpSeguranca.getNomeMpUsuarioNotificacao().substring(0,
					this.mpSeguranca.getNomeMpUsuarioNotificacao().indexOf("(Id="));
			MpUsuario mpUsuario = mpUsuarios.porNome(nome);
			this.mpNotificacaoUsuario.setMpUsuario(mpUsuario);
			this.mpNotificacaoUsuario.setNomeUsuario(mpUsuario.getNome());
		}
		//
		if (this.mpSeguranca.getNomeMpUsuarioNotificacao().indexOf("(IdT=") > 0) {
			nome = this.mpSeguranca.getNomeMpUsuarioNotificacao().substring(0,
					this.mpSeguranca.getNomeMpUsuarioNotificacao().indexOf("(IdT="));
			MpUsuarioTenant mpUsuarioTenant = mpUsuarioTenants.porNome(nome);
			this.mpNotificacaoUsuario.setMpUsuarioTenant(mpUsuarioTenant);
			this.mpNotificacaoUsuario.setNomeUsuario(mpUsuarioTenant.getMpPessoa().getNome());
		}

		// Trata Alerta ---------------------------
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpNotificacaoUsuario.getMpAlerta());
		this.mpNotificacaoUsuario.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------
		//
		this.mpNotificacaoUsuario = mpNotificacaoUsuarioService.salvar(this.mpNotificacaoUsuario);
		//
		MpFacesUtil.addInfoMessage("Notificação Usuário... salva com sucesso!");
	}

	// -------- Trata Navegação ...

	public void mpFirst() throws ParseException {
		//
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.porNavegacao("mpFirst",
																		sdf.parse("01/01/1900")); 
		if (null == this.mpNotificacaoUsuario) {
//			System.out.println("MpCadastroNotificacaoBean.mpFirst() ( Entrou 000");
			this.limpar();
		} else			
			this.mpSeguranca.setNomeMpUsuarioNotificacao(this.mpNotificacaoUsuario.
																		getNomeUsuario());
//		else
//			System.out.println("MpCadastroNotificacaoBean.mpFirst() ( Entrou 0001 = " +
//													this.mpNotificacaoUsuario.getParametro());
			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpNotificacaoUsuario.getDataEnvio()) return;
		//
		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
		//
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.porNavegacao("mpPrev", 
				  											mpNotificacaoUsuario.getDataEnvio());
		if (null == this.mpNotificacaoUsuario) {
			this.mpNotificacaoUsuario = this.mpNotificacaoUsuarioAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";		
		//
		this.mpSeguranca.setNomeMpUsuarioNotificacao(this.mpNotificacaoUsuario.
																			getNomeUsuario());
	}

	public void mpNew() {
		//
		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
		//
		this.limpar();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indEditavelPerfil = false;
		this.indNaoEditavel = true;		
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpNotificacaoUsuario.getId()) return;
		//
		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;

		if (this.mpSeguranca.capturaTenantId().equals("0"))
			this.indEditavelPerfil = false;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpNotificacaoUsuario.getId()) return;
		//
		try {
			this.mpNotificacaoUsuarios.remover(mpNotificacaoUsuario);
			
			MpFacesUtil.addInfoMessage("Notificação Usuário.. "
						+ sdf.format(this.mpNotificacaoUsuario.getDataEnvio())
						+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		String msg = "";
		if (null == this.mpNotificacaoUsuario.getNomeUsuarioFrom())
			msg = msg + "\nFavor selecionar um usuário!";
		if (this.mpNotificacaoUsuario.getAssunto().isEmpty()) 
			msg = msg + "\nFavor informar assunto!";
		if (this.mpNotificacaoUsuario.getMensagem().isEmpty())
			msg = msg + "\nFavor informar mensagem!";
		if (null == this.mpNotificacaoUsuario.getMpTipoPrioridade()) 
			msg = msg + "\nFavor selecionar a prioridade!";
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg);
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

		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
		//		
		this.indEditavel = true;
		this.indEditavelPerfil = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarioAnt;
		
		this.indEditavel = true;
		this.indEditavelPerfil = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
		//
		this.mpSeguranca.setNomeMpUsuarioNotificacao(this.mpNotificacaoUsuario.
																			getNomeUsuario());
	}
	
	public void mpNext() {
		//
		if (null == this.mpNotificacaoUsuario.getDataEnvio()) return;
		//
		this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
		//
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.porNavegacao("mpNext", 
				  											mpNotificacaoUsuario.getDataEnvio());
		if (null == this.mpNotificacaoUsuario) {
			this.mpNotificacaoUsuario = this.mpNotificacaoUsuarioAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.mpSeguranca.setNomeMpUsuarioNotificacao(this.mpNotificacaoUsuario.
																			getNomeUsuario());
	}
	
	public void mpEnd() throws ParseException {
		//
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.porNavegacao("mpEnd", 
																	sdf.parse("01/01/2900")); 
		if (null == this.mpNotificacaoUsuario) {
//			System.out.println("MpCadastroNotificacaoBean.mpEnd() ( Entrou 000");
			this.limpar();
		} else
			this.mpSeguranca.setNomeMpUsuarioNotificacao(this.mpNotificacaoUsuario.
																				getNomeUsuario());
//		else
//			System.out.println("MpCadastroNotificacaoBean.mpEnd() ( Entrou 0001 = " +
//													this.mpNotificacaoUsuario.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpNotificacaoUsuario.getId()) return;

		try {
			this.setMpNotificacaoUsuarioAnt(this.mpNotificacaoUsuario);
	
			this.mpNotificacaoUsuario = (MpNotificacaoUsuario) this.mpNotificacaoUsuario.
																						clone();
			this.mpNotificacaoUsuario.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelPerfil = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	private void limpar() {
		//
		this.mpNotificacaoUsuario = new MpNotificacaoUsuario();

		this.mpNotificacaoUsuario.setDataEnvio(new Date());		
		this.mpNotificacaoUsuario.setAssunto("");
		this.mpNotificacaoUsuario.setMensagem("");		
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
//			System.out.println("MpCadastroNotificacaoUsuarioBean.limpar() - ( MpAlerta = " +
//																mpAlertaX.getConfiguracao());
			//
		} catch (CloneNotSupportedException e) {
				e.printStackTrace();
		}
		//
		this.mpNotificacaoUsuario.setMpAlerta(mpAlertaX);		
		//
		this.mpSeguranca.setNomeMpUsuarioNotificacao("");
	}

	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelPerfil() { return indEditavelPerfil; }
	public void setIndEditavelPerfil(Boolean indEditavelPerfil) { 
												this.indEditavelPerfil = indEditavelPerfil; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) {
													this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { 
													this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public MpNotificacaoUsuario getMpNotificacaoUsuario() { return mpNotificacaoUsuario; }
	public void setMpNotificacaoUsuario(MpNotificacaoUsuario mpNotificacaoUsuario) {
										this.mpNotificacaoUsuario = mpNotificacaoUsuario; }

	public MpNotificacaoUsuario getMpNotificacaoUsuarioAnt() { return mpNotificacaoUsuarioAnt; }
	public void setMpNotificacaoUsuarioAnt(MpNotificacaoUsuario mpNotificacaoUsuarioAnt) {
		//
		try {
			this.mpNotificacaoUsuarioAnt = (MpNotificacaoUsuario) this.mpNotificacaoUsuario.clone();
			this.mpNotificacaoUsuarioAnt.setId(this.mpNotificacaoUsuario.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}


	public boolean isEditando() { return this.mpNotificacaoUsuario.getId() != null;	}
	
	public MpStatusNotificacao getMpStatusNotificacao() { return mpStatusNotificacao; }
	public void setMpStatusNotificacao(MpStatusNotificacao mpStatusNotificacao) {
												this.mpStatusNotificacao = mpStatusNotificacao;	}
	public List<MpStatusNotificacao> getMpStatusNotificacaoList() {
																return mpStatusNotificacaoList; }
	
	public MpTipoPrioridade getMpTipoPrioridade() { return mpTipoPrioridade; }
	public void setMpTipoPrioridade(MpTipoPrioridade mpTipoPrioridade) {
													this.mpTipoPrioridade = mpTipoPrioridade; }
	public List<MpTipoPrioridade> getMpTipoPrioridadeList() {	return mpTipoPrioridadeList; }
	
//	public String getNomeMpUsuarioNotificacao() { return nomeMpUsuarioNotificacao; }
//	public void setNomeMpUsuarioNotificacao(String nomeMpUsuarioNotificacao) { 
//										this.nomeMpUsuarioNotificacao = nomeMpUsuarioNotificacao; }
	
}