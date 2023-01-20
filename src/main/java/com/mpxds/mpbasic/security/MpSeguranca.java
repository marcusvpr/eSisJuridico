package com.mpxds.mpbasic.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import org.apache.commons.io.IOUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.MpChamado;
import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.model.enums.MpChamadoAreaTipo;
import com.mpxds.mpbasic.model.enums.MpChamadoSeveridade;
import com.mpxds.mpbasic.model.enums.MpChamadoStatus;
import com.mpxds.mpbasic.model.enums.MpChamadoTipo;
import com.mpxds.mpbasic.model.enums.MpStatusNotificacao;
import com.mpxds.mpbasic.model.enums.MpTipoPrioridade;

import com.mpxds.mpbasic.repository.MpChamados;
import com.mpxds.mpbasic.repository.MpNotificacaoUsuarios;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.MpMovimentoLogins;

import com.mpxds.mpbasic.repository.filter.MpChamadoFilter;

import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpChamadoService;
import com.mpxds.mpbasic.service.MpNotificacaoUsuarioService;
import com.mpxds.mpbasic.service.MpUsuarioService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped //  @RequestScoped
public class MpSeguranca implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private ExternalContext externalContext;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;

	@Inject
	private MpChamados mpChamados;

	@Inject
	private MpNotificacaoUsuarios mpNotificacaoUsuarios;

	@Inject
	private MpMovimentoLogins mpMovimentoLogins;

	@Inject
	private MpChamadoService mpChamadoService;

	@Inject
	private MpNotificacaoUsuarioService mpNotificacaoUsuarioService;
	
	@Inject
	private MpUsuarioService mpUsuarioService;
	
	@Inject
	private MpAlertaService mpAlertaService;

	// ---
	
	private MpChamado mpChamado = new MpChamado();
	
	private MpAlerta mpAlerta = new MpAlerta(false, false, false, false, false, false, false);
	
	// ---
	
//	private String sistemaURL = "localhost:8080/mpProtesto/";
	private String sistemaURL = "191.252.101.154:8080/mpProtesto/";

	private String freshdeskURL = "mpcom.freshdesk.com"; // "mpxdsrj.freshdesk.com";
	
	private String sistemaVersao = "1.0.11-09";
	
	private String nomeAplicativo = "MpSisJuri";// "MpConsig"; // "Justantum" "MpProtesto"
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private MpUsuario mpUsuario = new MpUsuario();
	private List<MpUsuario> mpUsuarioList = new ArrayList<MpUsuario>();
	
	private List<MpUsuario> mpUsuarioAtivoList = new ArrayList<MpUsuario>();
	private List<MpUsuarioTenant> mpUsuarioTenantAtivoList = new ArrayList<MpUsuarioTenant>();
	
	private String nomeMpUsuarioNotificacao;
	private List<String> nomeMpUsuarioNotificacaoList = new ArrayList<String>();

	private MpUsuarioTenant mpUsuarioTenant = new MpUsuarioTenant();
	
	private MpNotificacaoUsuario mpNotificacaoUsuario;
	private List<MpNotificacaoUsuario> mpNotificacaoUsuarioList = 
														new ArrayList<MpNotificacaoUsuario>();
	
	private MpTipoPrioridade mpTipoPrioridade;
	private List<MpTipoPrioridade> mpTipoPrioridadeList = 
														Arrays.asList(MpTipoPrioridade.values());
	
	private MpChamadoTipo mpChamadoTipo;
	private List<MpChamadoTipo> mpChamadoTipoList = Arrays.asList(MpChamadoTipo.values());
	
	private MpChamadoAreaTipo mpChamadoAreaTipo;
	private List<MpChamadoAreaTipo> mpChamadoAreaTipoList = 
													Arrays.asList(MpChamadoAreaTipo.values());	
	private String chamadoAreaOutro;
	private String chamadoDescricao;

	private MpChamadoSeveridade mpChamadoSeveridade;
	private List<MpChamadoSeveridade> mpChamadoSeveridadeList = 
													Arrays.asList(MpChamadoSeveridade.values());

	private MpChamadoStatus mpChamadoStatus;
	private List<MpChamadoStatus> mpChamadoStatusList = Arrays.asList(MpChamadoStatus.values());
	
	private String assunto;
	private String mensagem;
	
	private String trocaSenhaAtual;
	private String trocaSenhaNova;
	private String trocaSenhaNovaConfirma;

	private BigDecimal usuarioSaldo = new BigDecimal("0.0");
	private Integer usuarioChamados = 0;
	//
	private static final Logger logger = LogManager.getLogger(MpSeguranca.class);

    private UploadedFile file;
    private byte[] fileByte;

	private String usuarioAmbiente = "0";
	private Boolean isUsuarioAmbiente = false;
    
	// ----	
	
	public MpObjeto mpHelp(String objetoHelp) {
		//
		MpObjeto mpObjetoHelp = mpObjetos.porClasseAssociada(objetoHelp, this.capturaTenantId().trim());
		if (null == mpObjetoHelp)
			mpObjetoHelp = new MpObjeto();
		//
		return mpObjetoHelp;
	}
	
	public String getLoginUsuario() {
		//
		String login = null;
		//
		MpUsuarioSistema mpUsuarioLogado = this.getMpUsuarioLogado();
		//
		if (null == mpUsuarioLogado)
			assert(true); // nop
		else {
			if (null == mpUsuarioLogado.getMpUsuario())
				login = mpUsuarioLogado.getMpUsuarioTenant().getLogin();
			else
				login = mpUsuarioLogado.getMpUsuario().getLogin();
		}
		//
		return login;
	}

	public String getUltimoLoginUsuario() {
		//
//		System.out.println("MpSeguranca.getUltimoLoginUsuario() - Entrou!");
		//
    	@SuppressWarnings("unused")
		byte[] imageByte = null;
    	//
		try {
			imageByte = Utils.toByteArray(Faces.getResourceAsStream("/resources/images/blank.gif"));
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		if (this.mpUsuarioList.size() == 0) 
			this.mpUsuarioList = this.mpUsuarios.mpUsuarioAtivos();
		//
		if (this.usuarioChamados == 0) {
			MpChamadoFilter mpChamadoFilter = new MpChamadoFilter();
			
			if (this.isAdministradores())
				mpChamadoFilter.setUsuario("");
			else
				mpChamadoFilter.setUsuario(this.getMpUsuarioLogado().getMpUsuario().getLogin());
			
			this.usuarioChamados = this.mpChamados.quantidadeFiltrados(mpChamadoFilter);
		}
					
		String ultimoLogin = "";
		//
		MpUsuarioSistema mpUsuarioLogado = this.getMpUsuarioLogado();
		//
		if (mpUsuarioLogado != null)
			ultimoLogin = sdf.format(mpUsuarioLogado.getMpUsuario().getDataUltimoLogin());
		//
//		System.out.println("MpSeguranca.getUltimoLoginUsuario() - Entrou.001 ( " + 
//														this.mpNotificacaoUsuarioList.size());		
		return ultimoLogin;
	}

	public Integer getNumNotificacaoUsuario() {
		//
//		System.out.println("MpSeguranca.getNumNotificacaoUsuario() - Entrou.000 ( " + 
//								this.mpNotificacaoUsuarios.mpNotificacaoUsuarioList().size());
		//
		this.mpNotificacaoUsuarioList = this.mpNotificacaoUsuarios.
														mpNotificacaoUsuarioList("LEITURA");
		//
		return this.mpNotificacaoUsuarioList.size();
	}

	public String getUltimoLoginUsuarioAnt() {
		//
		String ultimoLoginAnt = "";
		//
		MpUsuarioSistema mpUsuarioLogado = this.getMpUsuarioLogado();
		//
		if (null == mpUsuarioLogado)
			ultimoLoginAnt = "";
		else {
			if (this.usuarioAmbiente.trim().equals("0")) {
				if (null == mpUsuarioLogado.getMpUsuario()) {
					if (null == mpUsuarioLogado.getMpUsuarioTenant())
						ultimoLoginAnt = "";
					else
						if (null == mpUsuarioLogado.getMpUsuarioTenant().getDataUltimoLoginAnt())
							ultimoLoginAnt = "";
						else
							ultimoLoginAnt = sdf.format(mpUsuarioLogado.getMpUsuarioTenant().
																		getDataUltimoLoginAnt());
					//
					return ultimoLoginAnt;
				}
				//
				if (null == mpUsuarioLogado.getMpUsuario().getDataUltimoLoginAnt())
					ultimoLoginAnt = "";
				else
					ultimoLoginAnt = sdf.format(mpUsuarioLogado.getMpUsuario().
																		getDataUltimoLoginAnt());
			} else {
				if (null == mpUsuarioLogado.getMpUsuarioTenant().getDataUltimoLoginAnt())
					ultimoLoginAnt = "";
				else
					ultimoLoginAnt = sdf.format(mpUsuarioLogado.getMpUsuarioTenant().
																		getDataUltimoLoginAnt());
			}
		}
		//
		return ultimoLoginAnt;
	}

	public String getNumVisitaUsuarioTotal() {
		//
		Long total = mpMovimentoLogins.totalMovimento("total");
		
		return total + "";
	}
	
	public String getNumVisitaUsuarioDia() {
		//
		Long total = mpMovimentoLogins.totalMovimento("dia");
		
		return total + "";
	}
	
	public String getNumVisitaUsuarioSemPercentual() {
		//
		Long total = mpMovimentoLogins.totalMovimento("semanaPercentual");
		
		return total + "";
	}
	
	public List<String> getNomeMpUsuarioNotificacaoList() {
		// --- Carrega Lista Usuarios Ativos para envio de notificação !!!
		this.nomeMpUsuarioNotificacaoList.clear();
		//
		if (this.mpUsuarioAtivoList.size() == 0)
			this.mpUsuarioAtivoList = this.mpUsuarios.mpUsuarioAtivos();
		//
		if (this.mpUsuarioTenantAtivoList.size() == 0)
			this.mpUsuarioTenantAtivoList = this.mpUsuarioTenants.mpUsuarioTenantAtivos();		
		//
		if (this.nomeMpUsuarioNotificacaoList.size() == 0) {
			// Trata Usuário ...
			for (MpUsuario mpUsuarioAtivo : mpUsuarioAtivoList) {
				//
				if (null == this.getMpUsuarioLogado().getMpUsuario())
					assert(true); // nop
				else {
					if (this.getMpUsuarioLogado().getMpUsuario().getId().equals(
																		mpUsuarioAtivo.getId()))
						continue;
					//
					if (this.getMpUsuarioLogado().getMpUsuario().getTenantId().equals("0"))
						assert(true); // nop
					else
					if (this.getMpUsuarioLogado().getMpUsuario().getTenantId().equals(
																mpUsuarioAtivo.getTenantId()))
						assert(true); // nop
					else
						continue;
				}
				//
				if (null == this.getMpUsuarioLogado().getMpUsuarioTenant())
					assert(true); // nop
				else {
					if (this.getMpUsuarioLogado().getMpUsuarioTenant().getTenantId().equals("0"))
						assert(true); // nop
					else
					if (this.getMpUsuarioLogado().getMpUsuarioTenant().getTenantId().equals( 
																	mpUsuarioAtivo.getTenantId()))
						assert(true); // nop
					else
						continue;
				}
				//
				this.nomeMpUsuarioNotificacaoList.add(mpUsuarioAtivo.getNome() + " (Id=" + 
																mpUsuarioAtivo.getId() + ")");
			}
			// Trata UsuárioTenant (Ambiente) ...
			for (MpUsuarioTenant mpUsuarioTenantAtivo : mpUsuarioTenantAtivoList) {
				//
				if (null == this.getMpUsuarioLogado().getMpUsuario())
					assert(true); // nop
				else {
					//
					if (this.getMpUsuarioLogado().getMpUsuario().getTenantId().equals("0"))
						assert(true); // nop
					else
						if (this.getMpUsuarioLogado().getMpUsuario().getTenantId().equals(
																mpUsuarioTenantAtivo.getTenantId()))
							assert(true); // nop
						else
							continue;
				}
				//
				if (null == this.getMpUsuarioLogado().getMpUsuarioTenant())
					assert(true); // nop
				else {
					if (this.getMpUsuarioLogado().getMpUsuarioTenant().getId().equals( 
																mpUsuarioTenantAtivo.getId()))
						continue;
					//
					if (this.getMpUsuarioLogado().getMpUsuarioTenant().getTenantId().equals("0"))
						assert(true); // nop
					else
					if (this.getMpUsuarioLogado().getMpUsuarioTenant().getTenantId().equals( 
															mpUsuarioTenantAtivo.getTenantId()))
						assert(true); // nop
					else
						continue;
				}
				//
				this.nomeMpUsuarioNotificacaoList.add(mpUsuarioTenantAtivo.getMpPessoa().getNome()
												+ " (IdT=" + mpUsuarioTenantAtivo.getId() + ")");
			}
		}
		//
		return this.nomeMpUsuarioNotificacaoList;
	}

	public void exibeMpNotificacao(MpNotificacaoUsuario mpNotificacaoUsuarioX) {
		//
		this.mpNotificacaoUsuario = mpNotificacaoUsuarioX;
		
		this.mpNotificacaoUsuario.setIndVisualiza(true);
		this.mpNotificacaoUsuario.setMpStatusNotificacao(MpStatusNotificacao.VISUALIZADA);
		
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.guardar(this.mpNotificacaoUsuario);		
		//
//		System.out.println("MpSeguranca.exibeMpNotificacao() - (Assunto = " + 
//														this.mpNotificacaoUsuario.getAssunto());
		//
	}
	
	public void leituraMpNotificacao() {
		//
		this.mpNotificacaoUsuario.setIndLeitura(true);
		this.mpNotificacaoUsuario.setMpStatusNotificacao(MpStatusNotificacao.LIDA);
		
		this.mpNotificacaoUsuario = this.mpNotificacaoUsuarios.guardar(this.mpNotificacaoUsuario);		
		//
//		System.out.println("MpSeguranca.leituraMpNotificacao() - (Assunto = " + 
//														this.mpNotificacaoUsuario.getAssunto());
		//
	}
		
	public void enviaMpNotificacao() {
		//
		String msg = "";
		if (null == this.nomeMpUsuarioNotificacao) msg = msg + "\nFavor selecionar um usuário!";
		if (this.assunto.isEmpty()) msg = msg + "\nFavor informar assunto!";
		if (this.mensagem.isEmpty()) msg = msg + "\nFavor informar mensagem!";
		if (null == this.mpTipoPrioridade) msg = msg + "\nFavor selecionar a prioridade!";
		//
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg);
			return;
		}
		// --------------
		this.mpNotificacaoUsuario = new MpNotificacaoUsuario();

		// Trata Alerta ---------------------------
		this.mpAlerta = this.mpAlertaService.salvar(this.mpAlerta);
		// Trata Alerta ---------------------------

		this.mpNotificacaoUsuario.setDataEnvio(new Date());
		this.mpNotificacaoUsuario.setAssunto(this.assunto);
		this.mpNotificacaoUsuario.setMensagem(this.mensagem);
		this.mpNotificacaoUsuario.setMpTipoPrioridade(mpTipoPrioridade);
		//
		if (null == this.getMpUsuarioLogado().getMpUsuario()) {
			this.mpNotificacaoUsuario.setNomeUsuarioFrom(this.getMpUsuarioLogado().
												getMpUsuarioTenant().getMpPessoa().getNome());
			this.mpNotificacaoUsuario.setTenantId(this.getMpUsuarioLogado().
												getMpUsuarioTenant().getTenantId());
		} else {
			this.mpNotificacaoUsuario.setNomeUsuarioFrom(this.getMpUsuarioLogado().
																	getMpUsuario().getNome());
			this.mpNotificacaoUsuario.setTenantId(this.getMpUsuarioLogado().
																	getMpUsuario().getTenantId());
		}
		//
		String nome = "";
		
		if (this.nomeMpUsuarioNotificacao.indexOf("(Id=") > 0) {
			nome = this.nomeMpUsuarioNotificacao.substring(0,
												this.nomeMpUsuarioNotificacao.indexOf("(Id="));
			MpUsuario mpUsuario = mpUsuarios.porNome(nome);
			this.mpNotificacaoUsuario.setMpUsuario(mpUsuario);
			this.mpNotificacaoUsuario.setNomeUsuario(mpUsuario.getNome());
		}
		//
		if (this.nomeMpUsuarioNotificacao.indexOf("(IdT=") > 0) {
			nome = this.nomeMpUsuarioNotificacao.substring(0,
												this.nomeMpUsuarioNotificacao.indexOf("(IdT="));
			MpUsuarioTenant mpUsuarioTenant = mpUsuarioTenants.porNome(nome);
			this.mpNotificacaoUsuario.setMpUsuarioTenant(mpUsuarioTenant);
			this.mpNotificacaoUsuario.setNomeUsuario(mpUsuarioTenant.getMpPessoa().getNome());
		}
		//
//		System.out.println("MpSeguranca.enviaMpNotificacao() - (nome = " + nome);
		//
		this.mpNotificacaoUsuario.setMpStatusNotificacao(MpStatusNotificacao.NOVA);
		this.mpNotificacaoUsuario.setMpAlerta(this.mpAlerta);
		
		this.mpNotificacaoUsuarioService.salvar(mpNotificacaoUsuario);
		//
		MpFacesUtil.addInfoMessage("Notificação Usuário... enviada!");
	}

	public String getNumeroIP() {
		//
		String ipAddress = null;

		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().
															getExternalContext().getRequest();
		ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (null == ipAddress)
			ipAddress = request.getRemoteAddr();
		//
		return ipAddress;
	}
	
	@Produces
	@MpUsuarioLogado
	public MpUsuarioSistema getMpUsuarioLogado() {
		//
		MpUsuarioSistema mpUsuarioSistema = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) 
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if (auth != null && auth.getPrincipal() != null)
			mpUsuarioSistema = (MpUsuarioSistema) auth.getPrincipal();
		//
		return mpUsuarioSistema;
	}
	
	public String capturaTenantId() {
		//
		this.isUsuarioAmbiente = false;

		String tenantId = "0";
		//
		MpUsuarioSistema mpUsuarioSistema = this.getMpUsuarioLogado();
		if (null == mpUsuarioSistema)
			assert(true); // nop
		else {
			this.mpUsuario = mpUsuarioSistema.getMpUsuario();
			if (null == this.mpUsuario) {
				this.mpUsuarioTenant = mpUsuarioSistema.getMpUsuarioTenant();
				if (null == this.mpUsuarioTenant)
					assert(true); // nop
				else {
					this.isUsuarioAmbiente = true;

					tenantId = mpUsuarioTenant.getTenantId();
				}
			} else
				tenantId = mpUsuario.getTenantId();
		}
		//
//		System.out.println("MpSeguranca.capturaTenantId() - ( " + tenantId + " / " +
//																		this.isUsuarioAmbiente);
		return tenantId;
	}
		
	public Date getDataSistema() {
		//
		return new Date();
	}

	public String getNumUsuarioOnline() {
		//
		Integer total = this.mpNotificacaoUsuarios.mpNotificacaoUsuarioList("").size();
		
		return total + "";
	}
	
	// ---

	public MpAuditoriaObjeto trataMpAuditoriaObjeto(String nomeObjeto, Object objeto) {
		//
		MpAuditoriaObjeto mpAuditoriaObjeto = new MpAuditoriaObjeto();
		
		Long id = null;
		
		if (nomeObjeto.equals("MpDolar")) {
			MpDolar mpDolar = (MpDolar) objeto;
			id = mpDolar.getId();
			mpAuditoriaObjeto = mpDolar.getMpAuditoriaObjeto();
		}
		if (nomeObjeto.equals("MpReceita")) {
			MpReceita mpReceita = (MpReceita) objeto;
			id = mpReceita.getId();
			mpAuditoriaObjeto = mpReceita.getMpAuditoriaObjeto();
		}
			
		if (null == id) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(this.getLoginUsuario());
		} else {
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(this.getLoginUsuario());				
		}
		//
		return mpAuditoriaObjeto;
	}	
	
	public void enviaSenhaTroca() {
		//
//		System.out.println("MpSeguranca.enviaMpNotificacao() - Entrou!");
		//
		String mensagem = "";
		if (this.trocaSenhaAtual.isEmpty()) mensagem = mensagem + "(Informar Senha Atual)";
		if (this.trocaSenhaNova.isEmpty()) mensagem = mensagem + "(Informar Senha Nova)";
		if (this.trocaSenhaNovaConfirma.isEmpty()) mensagem = mensagem + 
															"(Informar Senha Nova Confirma)";
		if (!mensagem.isEmpty()) {
			//		
			MpFacesUtil.addInfoMessage(mensagem);
			return;
		}
		//
		if (!this.trocaSenhaNova.equals(this.trocaSenhaNovaConfirma)) {
			MpFacesUtil.addInfoMessage("Senha Inválida!");			
			return;
		}
		//
		MpUsuarioSistema mpUsuarioSistema = this.getMpUsuarioLogado();

		this.mpUsuario = mpUsuarioSistema.getMpUsuario();
		
		if (this.trocaSenhaAtual.equals(this.mpUsuario.getSenha())) {
			//
			this.mpUsuario.setSenha(this.trocaSenhaNova);
			//
			// Trata armazanamento das últimas 5 (cinco) senhas !
			//
			String senhaLog = this.mpUsuario.getSenhaLog();
			if (null == senhaLog) senhaLog = "";
		
			if (senhaLog.split(" ").length > 4)
				senhaLog = this.mpUsuario.getSenha()  + " " + senhaLog.substring(
																		senhaLog.lastIndexOf(" "));
			else
				senhaLog = this.mpUsuario.getSenha() + " " + senhaLog;

			this.mpUsuario.setSenhaLog(senhaLog);
			//	
			this.mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
			//
			MpFacesUtil.addInfoMessage("Troca de senha... efetuada!");
			// logs info
			logger.info("Usuário... Troca de senha! (Login = " + mpUsuario.getLogin());
		} else
			MpFacesUtil.addInfoMessage("Senha Inválida!");			
	}

	public void enviaSaldo() {
		//
//		System.out.println("MpSeguranca.enviaMpNotificacao() - Entrou!");
		//
		String mensagem = "";
		if (this.usuarioSaldo == new BigDecimal("0.0"))
			mensagem = mensagem + "(Informar Saldo)";
		//
		if (!mensagem.isEmpty()) {
			//		
			MpFacesUtil.addInfoMessage(mensagem);
			//
			return;
		}
		//
		MpFacesUtil.addInfoMessage("Saldo... carregado!");
		// logs info
		logger.info("Usuário... Carga Saldo! (Login = " + mpUsuario.getLogin());
	}
	
	public void enviaChamado() {
		//
		String mensagem = "";
		
		if (null == this.mpChamadoTipo)
			mensagem = mensagem + "(Informar Tipo)";
		if (null == this.mpChamadoAreaTipo)
			mensagem = mensagem + "(Informar Area Tipo)";
		if (null == this.mpChamadoSeveridade)
			mensagem = mensagem + "(Informar Severidade)";
		if (null == this.chamadoDescricao || this.chamadoDescricao.isEmpty())
			mensagem = mensagem + "(Informar Descrição)";

		if (!mensagem.isEmpty()) {
			//		
			MpFacesUtil.addInfoMessage(mensagem);		
			return;
		}
		//
		MpUsuarioSistema mpUsuarioSistema = this.getMpUsuarioLogado();

		this.mpUsuario = mpUsuarioSistema.getMpUsuario();
		//
		this.mpChamado = new MpChamado();
		
		this.mpChamado.setDtHrChamado(new Date());
		this.mpChamado.setMpUsuario(this.mpUsuario);
		this.mpChamado.setDescricao(this.chamadoDescricao);
		this.mpChamado.setMpChamadoTipo(this.mpChamadoTipo);
		this.mpChamado.setMpChamadoAreaTipo(this.mpChamadoAreaTipo);
		this.mpChamado.setAreaOutro(chamadoAreaOutro);
		this.mpChamado.setMpChamadoSeveridade(this.mpChamadoSeveridade);
		this.mpChamado.setMpChamadoStatus(MpChamadoStatus.NOVO);
		
		// Trata File....
		mensagem = "";
		//
        if (null == this.file)
        	this.file = null;
        else {
			this.mpChamado.setArquivoBD(this.fileByte);
			this.mpChamado.setArquivoTipoBD(this.file.getContentType());
            //
//        	mensagem = mensagem + "Sucesso(" + this.file.getSize() + "/" + this.file.getFileName();
        }
		//
		this.mpChamado = this.mpChamadoService.salvar(mpChamado);		
		//
		MpFacesUtil.addInfoMessage("Chamado.Número=" + this.mpChamado.getId() + " / " + mensagem);
	}

	public void handleFileUpload(FileUploadEvent event) {
		//
	    this.file = event.getFile();
	    
	    String fileName = this.file.getFileName();
	    String contentType = this.file.getContentType();
	    
	    try {
			this.fileByte = IOUtils.toByteArray(file.getInputstream()); // event.getFile().getContents();
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //
		if (null == this.file)
			System.out.println("MpSeguranca.handleFileUpload - NULL" + 
									"/filename=" + fileName + "/contentType=" + contentType);
		else
			System.out.println("MpSeguranca.handleFileUpload - " + this.file.getSize() +
									"/filename=" + fileName + "/contentType=" + contentType);
	}
	
	public StreamedContent byteToImage(byte[] imgBytes) throws IOException {
		//
		if (null == imgBytes) return null;
		//
		InputStream is = new ByteArrayInputStream(imgBytes);
		//
//		ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
		//
		return new DefaultStreamedContent(is); // img, "image/jpg");
	}
	
	// ==============
	
	public boolean isAdministradores() {
		return externalContext.isUserInRole("ADMINISTRADORES");
	}
	public boolean isEmitirPedidoPermitido() {
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("VENDEDORES");
	}
	public boolean isCancelarPedidoPermitido() {
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("VENDEDORES");
	}
	public boolean isGestores() {
		return externalContext.isUserInRole("PROTESTOS-ADMIN");
	}

	// ---
	
	public String getSistemaURL() { return sistemaURL; }
	public void setSistemaURL(String sistemaURL) { this.sistemaURL = sistemaURL; }
	
	public String getFreshdeskURL() { return freshdeskURL; }
	public void setFreshdeskURL(String freshdeskURL) { this.sistemaURL = freshdeskURL; }
	
	public String getSistemaVersao() { return sistemaVersao; }
	public void setSistemaVersao(String sistemaVersao) { this.sistemaVersao = sistemaVersao; }
	
	public String getNomeAplicativo() { return nomeAplicativo; }
	public void setNomeAplicativo(String nomeAplicativo) { this.nomeAplicativo = nomeAplicativo; }
	
	// ---
	
	public MpAlerta getMpAlerta() { return mpAlerta; }
	public void setMpAlerta(MpAlerta mpAlerta) { this.mpAlerta = mpAlerta;	}
	
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) {	this.mpUsuario = mpUsuario; }
	public List<MpUsuario> getMpUsuarioList() {	return mpUsuarioList; }

	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) {	
														this.mpUsuarioTenant = mpUsuarioTenant; }
		
	public List<MpUsuario> getMpUsuarioAtivoList() { return mpUsuarioAtivoList; }	
	public List<MpUsuarioTenant> getMpUsuarioTenantAtivoList() {
															return mpUsuarioTenantAtivoList; }	
	
	public String getNomeMpUsuarioNotificacao() { return nomeMpUsuarioNotificacao; }
	public void setNomeMpUsuarioNotificacao(String nomeMpUsuarioNotificacao) {	
									this.nomeMpUsuarioNotificacao = nomeMpUsuarioNotificacao; }
	
	public MpNotificacaoUsuario getMpNotificacaoUsuario() { return mpNotificacaoUsuario; }
	public void setMpNotificacaoUsuario(MpNotificacaoUsuario mpNotificacaoUsuario) {
										this.mpNotificacaoUsuario = mpNotificacaoUsuario; }
	public List<MpNotificacaoUsuario> getMpNotificacaoUsuarioList() {
														return mpNotificacaoUsuarioList; }

	public MpTipoPrioridade getMpTipoPrioridade() { return mpTipoPrioridade; }
	public void setMpTipoPrioridade(MpTipoPrioridade mpTipoPrioridade) {
													this.mpTipoPrioridade = mpTipoPrioridade; }
	public List<MpTipoPrioridade> getMpTipoPrioridadeList() { return mpTipoPrioridadeList; }
	
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	
	public String getMensagem() { return mensagem; }
	public void setMensagem(String mensagem) { this.mensagem = mensagem; }

	public String getTrocaSenhaAtual() { return trocaSenhaAtual; }
	public void setTrocaSenhaAtual(String trocaSenhaAtual) { 
													this.trocaSenhaAtual = trocaSenhaAtual; }

	public String getTrocaSenhaNova() { return trocaSenhaNova; }
	public void setTrocaSenhaNova(String trocaSenhaNova) { 
													this.trocaSenhaNova = trocaSenhaNova; }
	public String getTrocaSenhaNovaConfirma() { return trocaSenhaNovaConfirma; }
	public void setTrocaSenhaNovaConfirma(String trocaSenhaNovaConfirma) { 
										this.trocaSenhaNovaConfirma = trocaSenhaNovaConfirma; }

	public BigDecimal getUsuarioSaldo() { return usuarioSaldo; }
	public void setUsuarioSaldo(BigDecimal usuarioSaldo) { this.usuarioSaldo = usuarioSaldo; }

	public Integer getUsuarioChamados() { return usuarioChamados; }
	public void setUsuarioChamados(Integer usuarioChamados) { 
													this.usuarioChamados = usuarioChamados; }

	public MpChamadoSeveridade getMpChamadoSeveridade() { return mpChamadoSeveridade; }
	public void setMpChamadoSeveridade(MpChamadoSeveridade mpChamadoSeveridade) {
													this.mpChamadoSeveridade = mpChamadoSeveridade;	}
	
	public List<MpChamadoSeveridade> getMpChamadoSeveridadeList() {
															return mpChamadoSeveridadeList; }

	public MpChamadoAreaTipo getMpChamadoAreaTipo() { return mpChamadoAreaTipo; }
	public void setMpChamadoAreaTipo(MpChamadoAreaTipo mpChamadoAreaTipo) {
												this.mpChamadoAreaTipo = mpChamadoAreaTipo;	}
	public List<MpChamadoAreaTipo> getMpChamadoAreaTipoList() {	return mpChamadoAreaTipoList; }

	public String getChamadoAreaOutro() { return chamadoAreaOutro; }
	public void setChamadoAreaOutro(String chamadoAreaOutro) { 
													this.chamadoAreaOutro = chamadoAreaOutro; }

	public String getChamadoDescricao() { return chamadoDescricao; }
	public void setChamadoDescricao(String chamadoDescricao) { 
													this.chamadoDescricao = chamadoDescricao; }
	
	public MpChamadoTipo getMpChamadoTipo() { return mpChamadoTipo; }
	public void setMpChamadoTipo(MpChamadoTipo mpChamadoTipo) {
														this.mpChamadoTipo = mpChamadoTipo;	}
	public List<MpChamadoTipo> getMpChamadoTipoList() {	return mpChamadoTipoList; }

	public MpChamadoStatus getMpChamadoStatus() { return mpChamadoStatus; }
	public void setMpChamadoStatus(MpChamadoStatus mpChamadoStatus) {
														this.mpChamadoStatus = mpChamadoStatus;	}
	public List<MpChamadoStatus> getMpChamadoStatusList() {	return mpChamadoStatusList; }
	
    public UploadedFile getFile() { return file; }
    public void setFile(UploadedFile file) { this.file = file; }
	
    public byte[] getFileByte() { return fileByte; }
    public void setFileByte(byte[] fileByte) { this.fileByte = fileByte; }

	public String getUsuarioAmbiente() { return usuarioAmbiente; }
	public void setUsuarioAmbiente(String usuarioAmbiente) { 
														this.usuarioAmbiente = usuarioAmbiente; }

	public Boolean getIsUsuarioAmbiente() { return isUsuarioAmbiente; }
	public void setIsUsuarioAmbiente(Boolean isUsuarioAmbiente) { 
													this.isUsuarioAmbiente = isUsuarioAmbiente; }

}
