package com.mpxds.mpbasic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpItemObjeto;
import com.mpxds.mpbasic.model.log.MpLoginLog;
import com.mpxds.mpbasic.model.enums.MpGrupamentoMenu;
import com.mpxds.mpbasic.model.enums.MpGrupoMenu;
import com.mpxds.mpbasic.model.enums.MpMenuGlobalObjeto;
import com.mpxds.mpbasic.model.enums.MpMenuGlobalSistemaConfig;
import com.mpxds.mpbasic.model.enums.MpMenuGlobalGrupo;
import com.mpxds.mpbasic.model.enums.MpMenuGlobalUsuario;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpStatusObjeto;
import com.mpxds.mpbasic.model.enums.MpTipoObjeto;
import com.mpxds.mpbasic.model.enums.MpTipoProdutoCategoria;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.MpNotificacaos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpUsuarioTenants;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpTenant;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.MpUsuarioTenant;
import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.security.MpUsuarioLogados;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpCategoriaService;
import com.mpxds.mpbasic.service.MpGrupoService;
import com.mpxds.mpbasic.service.MpLoginLogService;
import com.mpxds.mpbasic.service.MpMovimentoLoginService;
import com.mpxds.mpbasic.service.MpObjetoService;
import com.mpxds.mpbasic.service.MpSistemaConfigService;
import com.mpxds.mpbasic.service.MpTenantService;
import com.mpxds.mpbasic.service.MpUsuarioService;
import com.mpxds.mpbasic.service.MpUsuarioTenantService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.mail.MpMailer;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@SessionScoped
public class MpLoginBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private MpNotificacaos mpNotificacaos;
		
	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpUsuarioTenants mpUsuarioTenants;
	
	@Inject
	private MpUsuarioService mpUsuarioService;

	@Inject
	private MpUsuarioTenantService mpUsuarioTenantService;
	
	@Inject
	private MpGrupos mpGrupos;

	@Inject
	private MpGrupoService mpGrupoService;
	
	@Inject
	private MpObjetoService mpObjetoService;
	
	@Inject
	private MpMovimentoLoginService mpMovimentoLoginService;
	
	@Inject
	private MpCategoriaService mpCategoriaService;
	
	@Inject
	private MpLoginLogService mpLoginLogService;
	
	@Inject
	private MpSistemaConfigService mpSistemaConfigService;
	
	@Inject
	private MpTenantService mpTenantService;
	
	@Inject
	private MpAlertaService mpAlertaService;
	
	@Inject
	private MpCategorias mpCategorias;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	@Inject
	private MpObjetos mpObjetos;
	
	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpUsuarioLogados mpUsuarioLogados;

	@Inject
	private MpMailer mpMailer;

//	@Inject ????
//	@MpUsuarioEdicao
	private MpUsuario mpUsuario;
	private MpUsuarioTenant mpUsuarioTenant;
	
	// Trata parametros recebidos via URL ...
	// ======================================
	@ManagedProperty(value = "#{param.idI}")
	private String idI;
	@ManagedProperty(value = "#{param.idU}")
	private String idU;

	// ======================================
	
    private Boolean indLoginAmbiente = false;
    private Boolean indAdministrador = false;
	private String loginAmbiente = "";
	
	private String loginEmail = "";
	private String loginEmailAnt = ".";
	
	private Integer contloginErro = 0;
	private Integer contloginErroRegistro = 0;
	
	private String mensagemSistema = "";

	private String transacaoSistema;

	private String emailSenha = "";

	private String usuarioRegistro = "";
	private String nomeRegistro = "";
	private String emailRegistro = "";
	private String senhaRegistro = "";
	private String senhaConfirmaRegistro = "";
	//    
    private MenuModel modelMenu;
    private MenuModel modelMenuLeft;
    
    private Boolean indMenu = false;
    private Boolean indMenuDoseCerta = false;

    private Boolean indAceiteTermo = false;
    private Boolean indEsqueciSenha = false;
    private Boolean indVisivelRegistro = true;
    
    // Configuração Sistema ...
    // ------------------------
    private Integer scNumLoginError;
	private Boolean scIndMultiTenancy; // Tenant(MultiUsuário) no ambiente...
    @SuppressWarnings("unused")
	private Integer scNumDiasTrocaSenha;
	private String scSistemaURL = "";
	private Boolean scIndMenuTop;
	private Boolean scIndMenuLeft;
	private Boolean scIndBarraNavegacao;
	private Boolean scIndRodapeSistema;
	private Boolean scIndLabelCampo;
	// Trata Ativação forma de ALERTAS ...
	private Boolean scIndAtivaEmail;
	private Boolean scIndAtivaSMS;
	private Boolean scIndAtivaPush;
	private Boolean scIndAtivaTelegram;
	private Boolean scIndAtivaWhatsapp;
	private Boolean scIndAtivaMpComunicator;
	//
	private static final Logger logger = LogManager.getLogger(MpSeguranca.class);
	
    // Configuração Menu Responsivo ...
    // --------------------------------
	private Boolean indMenuDashboard = false;
    // --------------------------------
	
	//----------------
		
	public void inicializar() {
		//
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			context.getExternalContext().redirect("/mpProtesto/sentinel/mpLogin.xhtml");
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void preRender() {
		//
		// --- logs debug ---
		if (logger.isDebugEnabled()) logger.debug("MpLoginBean.preRender()");

		// Trata Configuração Sistema ...
		// ======================================
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("numeroLoginError");
		if (null == mpSistemaConfig)
			this.scNumLoginError = 3;
		else
			this.scNumLoginError = mpSistemaConfig.getValorN();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("numeroDiasTrocaSenha");
		if (null == mpSistemaConfig)
			this.scNumDiasTrocaSenha = 180;
		else
			this.scNumDiasTrocaSenha = mpSistemaConfig.getValorN();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indMultiTenancy");
		if (null == mpSistemaConfig)
			this.scIndMultiTenancy = false;
		else
			this.scIndMultiTenancy = mpSistemaConfig.getIndValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("sistemaURL");
		if (null == mpSistemaConfig)
			this.scSistemaURL = mpSeguranca.getSistemaURL(); // Default = localhost ...
		else
			this.scSistemaURL = mpSistemaConfig.getValor();
		
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indMenuTop");
		if (null == mpSistemaConfig)
			this.scIndMenuTop = true;
		else
			this.scIndMenuTop = mpSistemaConfig.getIndValor();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indMenuLeft");
		if (null == mpSistemaConfig)
			this.scIndMenuLeft = true;
		else
			this.scIndMenuLeft = mpSistemaConfig.getIndValor();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indBarraNavegacao");
		if (null == mpSistemaConfig)
			this.scIndBarraNavegacao = true;
		else
			this.scIndBarraNavegacao = mpSistemaConfig.getIndValor();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indRodapeSistema");
		if (null == mpSistemaConfig)
			this.scIndRodapeSistema = true;
		else
			this.scIndRodapeSistema = mpSistemaConfig.getIndValor();

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indLabelCampo");
		if (null == mpSistemaConfig)
			this.scIndLabelCampo = true;
		else
			this.scIndLabelCampo = mpSistemaConfig.getIndValor();		
		// ==========
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaEmail");
		if (null == mpSistemaConfig)
			this.scIndAtivaEmail = false;
		else
			this.scIndAtivaEmail = mpSistemaConfig.getIndValor();		

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaSMS");
		if (null == mpSistemaConfig)
			this.scIndAtivaSMS = false;
		else
			this.scIndAtivaSMS = mpSistemaConfig.getIndValor();		

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaPush");
		if (null == mpSistemaConfig)
			this.scIndAtivaPush = false;
		else
			this.scIndAtivaPush = mpSistemaConfig.getIndValor();		

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaTelegram");
		if (null == mpSistemaConfig)
			this.scIndAtivaTelegram = false;
		else
			this.scIndAtivaTelegram = mpSistemaConfig.getIndValor();		

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaWhatsapp");
		if (null == mpSistemaConfig)
			this.scIndAtivaWhatsapp = false;
		else
			this.scIndAtivaWhatsapp = mpSistemaConfig.getIndValor();		

		mpSistemaConfig = this.mpSistemaConfigs.porParametro("indAtivaMpComunicator");
		if (null == mpSistemaConfig)
			this.scIndAtivaMpComunicator = false;
		else
			this.scIndAtivaMpComunicator = mpSistemaConfig.getIndValor();		
		//
		// Trata Confirmação Registro Usuário ...
		if (null == this.idI) this.idI = "null";
		if (null == this.idU) this.idU = "null";
		//
		if (this.idI.equals("null") || this.idU.equals("null"))
			this.contloginErroRegistro = 0;
		//
//		System.out.println("MpCadastroDolarBean.preRender - ( idI/idU = " +	idI + "/"+ idU );
		
		if (!this.idI.equals("null") && !this.idU.equals("null")) {
			//
			MpUsuario mpUsuario = this.mpUsuarios.porLogin(this.idU);
			if (null == mpUsuario)
				mpUsuario = new MpUsuario();
			else {
				if (mpUsuario.getMpStatus().equals(MpStatus.REGISTRO)) {
					//
					if (mpUsuario.getId().equals(Long.parseLong(this.idI))) {
						//
						mpUsuario.setMpStatus(MpStatus.ATIVO);
						//
						mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
						//
						MpFacesUtil.addErrorMessage("Registro Efetuado! ( " + 
						mpUsuario.getLogin() +
						")... Use suas credenciais para efetuar o login em nossa plataforma. !");
						//
						this.contloginErroRegistro = 0;
						//
						return;
					}
				} else {
					this.contloginErroRegistro++;
					if (this.contloginErroRegistro > this.scNumLoginError) {
						mpUsuario.setMpStatus(MpStatus.BLOQUEADO);
						//
						mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
						//
						MpFacesUtil.addErrorMessage(
									"Tentativas Registro... inválida! Contactar o Suporte!");
						// logs exception
						logger.error("Tentativas Registro... inválida! (Login=" +
											mpUsuario.getLogin(), new Exception("Violação"));
						//
						return;
					}
				}
			}
		}
		//
		if (this.mensagemSistema.isEmpty())
			this.mensagemSistema = mpNotificacaos.trataMensagemSistema();
		//
		if ("true".equals(request.getParameter("invalid"))) {			
			//
			if (this.loginEmail.isEmpty()) {
				MpFacesUtil.addErrorMessage("Informar Usuário (Login/E-mail)!");
				return;
			}
			//
			this.contloginErro++;
			if (this.contloginErro > this.scNumLoginError) {
				//
				if (indLoginAmbiente) {
					//
					MpUsuarioTenant mpUsuarioTenant = mpUsuarioTenants.porLoginEmailAmbiente(
																this.loginEmail, this.loginAmbiente);
					if (null == mpUsuarioTenant) {
						MpFacesUtil.addErrorMessage("Usuário Ambiente inválido! (rc=0010)");
						//
						return;
					} else {
						mpUsuarioTenant.setMpStatus(MpStatus.BLOQUEADO);
						//
						mpUsuarioTenant = this.mpUsuarioTenantService.salvar(mpUsuarioTenant);
					}
					//
				} else {
					MpUsuario mpUsuario = mpUsuarios.porLoginEmailAmbiente(
														this.loginEmail, this.loginAmbiente);
					if (null == mpUsuario) {
						MpFacesUtil.addErrorMessage("Usuário inválido! (rc=0010)");
						//
						return;
					} else {
						mpUsuario.setMpStatus(MpStatus.BLOQUEADO);
						//
						mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
					}
				}
				//
				this.contloginErro = 0;
				//
				MpFacesUtil.addErrorMessage(
							"Excedido o número de tentativas no LOGIN... Usuário Bloqueado!");
				// logs exception
				logger.error("Usuário BLOQUEADO! (Login=" + mpUsuario.getLogin(),
																	new Exception("Violação"));
				//
				return;
			}
			//
			MpLoginLog mpLoginLog = new MpLoginLog();
			//
			mpLoginLog.setFailedLoginName(this.loginEmail);
			mpLoginLog.setCreatedDate(new Date());
			mpLoginLog.setClientIp(mpSeguranca.getNumeroIP());
			if (indLoginAmbiente)
				mpLoginLog.setFailedPassword(this.getMpUsuarioTenant().getSenha());
			else
				mpLoginLog.setFailedPassword(this.getMpUsuario().getSenha());
			mpLoginLog.setResult(2); // 1=success; 2=failed.
			
			mpLoginLogService.salvar(mpLoginLog);
			//
			MpFacesUtil.addErrorMessage("Usuário ou senha inválido! (" + this.contloginErro);
		}
	}

	public void trataVisivelTenant() {
		//
		System.out.println("MpLoginBean.trataVisivelTenant() ( " + this.indLoginAmbiente);
		
		if (this.indLoginAmbiente) {
			this.scIndMultiTenancy = true;			
		} else {
			//
			MpUsuario mpUsuario = mpUsuarios.porLoginEmail(this.loginEmail);
			if (null == mpUsuario)
				assert(true); // nop
			else 
				this.scIndMultiTenancy = mpUsuario.getIndTenant();
		}
//		
//		System.out.println("MpLoginBean.trataVisivelTenant() - ( " + this.loginEmail +
//															" / " + this.scIndMultiTenancy);
	}
		
	public void login() throws ServletException, IOException {
		//
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");

		if (null == dispatcher)
			MpFacesUtil.addErrorMessage("Usuário ou senha inválido! Null Dispatcher");
		else {
			//
			String usuario = request.getParameter("j_username");
			if (null == usuario || usuario.isEmpty()) {
				MpFacesUtil.addErrorMessage("Usuário inválido! Erro(001) " + usuario);
				//
				return;
			}
			//
			if (indLoginAmbiente) {
				//
				if (null == this.loginAmbiente || this.loginAmbiente.isEmpty()) {
					MpFacesUtil.addErrorMessage("Usuário Ambiente inválido! Erro(003) " + 
																					usuario);
					return;
				}
				//
				this.mpSeguranca.setIsUsuarioAmbiente(true);
				
				this.mpSeguranca.setUsuarioAmbiente(loginAmbiente);
				
				this.mpUsuarioTenant = mpUsuarioTenants.porLoginEmailAmbiente(
																		usuario, loginAmbiente);
				if (null == this.mpUsuarioTenant) {
					//
					MpFacesUtil.addErrorMessage("Usuário Ambiente inválido! Erro(002)");
					return;
				} else {
					if (!this.mpUsuarioTenant.getMpStatus().toString().equals("ATIVO")) {
						MpFacesUtil.addErrorMessage("Usuário Ambiente erro! - Status! ( "
							+ this.mpUsuarioTenant.getMpStatus() + " ) - Contate o SUPORTE !");
						return;
					}
					// Grava MovimentoLogin ...
					MpMovimentoLogin mpMovimentoLogin = new MpMovimentoLogin();

					mpMovimentoLogin.setAtividade("Login");
					mpMovimentoLogin.setUsuarioLogin(this.mpUsuarioTenant.getLogin());
					mpMovimentoLogin.setUsuarioEmail(this.mpUsuarioTenant.getMpPessoa().getEmail());
					mpMovimentoLogin.setDtHrMovimento(new Date());
					mpMovimentoLogin.setNumeroIP(mpSeguranca.getNumeroIP());
					mpMovimentoLogin.setColigada("00");
					//
					mpMovimentoLogin = this.mpMovimentoLoginService.salvar(mpMovimentoLogin);
					//
					this.mpUsuarioTenant.setDataUltimoLoginAnt(this.mpUsuarioTenant.getDataUltimoLogin());
					this.mpUsuarioTenant.setDataUltimoLogin(mpMovimentoLogin.getDtHrMovimento());
					this.mpUsuarioTenant.setNumIpUltimoLogin(mpSeguranca.getNumeroIP());

					this.mpUsuarioTenant = this.mpUsuarioTenantService.salvar(this.mpUsuarioTenant);
					//
					// Trata Preferências USUÁRIO AMBIENTE ...
					if (this.scIndMenuTop)
						this.scIndMenuTop = this.mpUsuarioTenant.getIndMenuTop();
					if (this.scIndMenuLeft)
						this.scIndMenuLeft = this.mpUsuarioTenant.getIndMenuLeft();
					if (this.scIndBarraNavegacao)
						this.scIndBarraNavegacao = this.mpUsuarioTenant.getIndBarraNavegacao();
					if (this.scIndRodapeSistema)
						this.scIndRodapeSistema = this.mpUsuarioTenant.getIndRodapeSistema();
					if (this.scIndLabelCampo)
						this.scIndLabelCampo = this.mpUsuarioTenant.getIndLabelCampo();

					this.montaMenuTenant();
					//
					MpUsuarioLogados.getMpUsuarioTenantLogadoList().add(this.mpUsuarioTenant);
				}
			} else {
				//
				this.mpSeguranca.setIsUsuarioAmbiente(false);

				this.mpSeguranca.setUsuarioAmbiente("0");
				//
				this.mpUsuario = mpUsuarios.porLoginEmailAmbiente(usuario, loginAmbiente);
				if (null == this.mpUsuario) {
					// Gera Usuário+Dados DEFAULTS...
					if (usuario.toLowerCase().equals("marcus")) {
						//
						this.trataGeracaoDefault(usuario);
						// -------------------
						MpFacesUtil.addErrorMessage("Usuário inválido! Erro(002) - Default.Create!");
						//
						return;
					}
					//
					MpFacesUtil.addErrorMessage("Usuário inválido! Erro(002)");
					//
					return;
				} else {
					if (!this.mpUsuario.getMpStatus().toString().equals("ATIVO")) {
						MpFacesUtil.addErrorMessage("Usuário erro! - Status! ( " + this.mpUsuario.getMpStatus()
																					+ " ) - Contate o SUPORTE !");
						return;
					}
					// Grava MovimentoLogin ...
					MpMovimentoLogin mpMovimentoLogin = new MpMovimentoLogin();

					mpMovimentoLogin.setAtividade("Login");
					mpMovimentoLogin.setUsuarioLogin(this.mpUsuario.getLogin());
					mpMovimentoLogin.setUsuarioEmail(this.mpUsuario.getEmail());
					mpMovimentoLogin.setDtHrMovimento(new Date());
					mpMovimentoLogin.setNumeroIP(mpSeguranca.getNumeroIP());
					mpMovimentoLogin.setColigada("00");
					//
					mpMovimentoLogin = this.mpMovimentoLoginService.salvar(mpMovimentoLogin);
					//
					this.mpUsuario.setDataUltimoLoginAnt(this.mpUsuario.getDataUltimoLogin());
					this.mpUsuario.setDataUltimoLogin(mpMovimentoLogin.getDtHrMovimento());
					this.mpUsuario.setNumIpUltimoLogin(mpSeguranca.getNumeroIP());

					this.mpUsuario = this.mpUsuarioService.salvar(this.mpUsuario);
					//
					// Trata Preferências USUÁRIO ...
					if (this.scIndMenuTop)
						this.scIndMenuTop = this.mpUsuario.getIndMenuTop();
					if (this.scIndMenuLeft)
						this.scIndMenuLeft = this.mpUsuario.getIndMenuLeft();
					if (this.scIndBarraNavegacao)
						this.scIndBarraNavegacao = this.mpUsuario.getIndBarraNavegacao();
					if (this.scIndRodapeSistema)
						this.scIndRodapeSistema = this.mpUsuario.getIndRodapeSistema();
					if (this.scIndLabelCampo)
						this.scIndLabelCampo = this.mpUsuario.getIndLabelCampo();
					//
					MpGrupo mpGrupo = mpGrupos.porNome("USUARIOS");
					if (null == mpGrupo)
						mpGrupo = new MpGrupo();
					//
					if (this.mpUsuario.getMpGrupos().contains(mpGrupo) && this.mpUsuario.
																	getMpGrupos().size() == 1)
						this.indMenuDoseCerta = true;
					else
						this.montaMenu();
					//
					this.mpSeguranca.isAdministradores();
					this.isAdministrador();
					//					
					MpUsuarioLogados.getMpUsuarioLogadoList().add(this.mpUsuario);
				}
			}
			//
			this.contloginErroRegistro = 0;

			dispatcher.forward(request, response);
		}
		//
        System.out.println("MpLoginBean.login() - 000 ");
		//
		facesContext.responseComplete();
	}

	public void trataGeracaoDefault(String usuario) {
		//
    	// Trata Grupos ...
        List<MpGrupo> mpGrupoList =	new ArrayList<MpGrupo>();

        List<MpMenuGlobalGrupo> mpMenuGlobalGrupoList = 
											Arrays.asList(MpMenuGlobalGrupo.values());
        //
        System.out.println("MpLoginBean.login() - 000 ( " + mpMenuGlobalGrupoList.size());

        Iterator<MpMenuGlobalGrupo> itrG = mpMenuGlobalGrupoList.iterator(); 
        //
        while(itrG.hasNext()) {
        	//
        	MpMenuGlobalGrupo mpMenuGlobalGrupo = (MpMenuGlobalGrupo) itrG.next();

        	MpGrupo mpGrupo = mpGrupos.porNome(mpMenuGlobalGrupo.getNome());
        	if (null == mpGrupo) {
        		//
    			mpGrupo = new MpGrupo();
    			
    			mpGrupo.setNome(mpMenuGlobalGrupo.getNome());
    			mpGrupo.setDescricao(mpMenuGlobalGrupo.getDescricao());
    			mpGrupo.setMpStatus(MpStatus.valueOf(mpMenuGlobalGrupo.getMpStatus()));
    			
    			mpGrupo = mpGrupoService.salvar(mpGrupo);
    			
    			mpGrupoList.add(mpGrupo);
		        //
		        System.out.println("MpLoginBean.login() - 001 ( " +	mpGrupo.getNome());
        	}
        }
		// Trata Usuários...
        List<MpMenuGlobalUsuario> mpMenuGlobalUsuarioList = 
        										Arrays.asList(MpMenuGlobalUsuario.values());
		//
		System.out.println("MpLoginBean.login() - 002 ( " + mpMenuGlobalUsuarioList.size());
        
    	Iterator<MpMenuGlobalUsuario> itrU = mpMenuGlobalUsuarioList.iterator(); 
    	//
    	while(itrU.hasNext()) {
	    	//
    		MpMenuGlobalUsuario mpMenuGlobalUsuario = (MpMenuGlobalUsuario) itrU.next();
    		
    		MpUsuario mpUsuario = mpUsuarios.porLogin(mpMenuGlobalUsuario.getLogin());
    		if (null == mpUsuario) {
    			//    			
    			mpUsuario = new MpUsuario();
    			
    			MpAlerta mpAlerta = new MpAlerta(false, false, false, false, false, false, false);

    			mpAlerta = mpAlertaService.salvar(mpAlerta);

    			mpUsuario.setMpAlerta(mpAlerta);

    			mpUsuario.setLogin(mpMenuGlobalUsuario.getLogin());
    			mpUsuario.setNome(mpMenuGlobalUsuario.getNome());
    			mpUsuario.setEmail(mpMenuGlobalUsuario.getEmail());
    			mpUsuario.setSenha(mpMenuGlobalUsuario.getSenha());
    			mpUsuario.setMpStatus(MpStatus.valueOf(mpMenuGlobalUsuario.getMpStatus()));
    			mpUsuario.setMpSexo(MpSexo.valueOf(mpMenuGlobalUsuario.getMpSexo()));
    			
    			if (usuario.toLowerCase().equals("marcus")) {
    				mpUsuario.setMpGrupos(mpGrupoList);
    		        System.out.println("MpLoginBean  GRUPOS - 001 ( " +	usuario);
    			}

    			//    			
    			mpUsuario = mpUsuarioService.salvar(mpUsuario);

    			// Trata MultiTenancy ...
    			if (usuario.toLowerCase().equals("marcus") || this.scIndMultiTenancy) {
    				MpTenant mpTenant = new MpTenant(mpUsuario.getId().toString(),
    											mpUsuario.getNome(), MpStatusObjeto.ATIVO);
    				    				        			
        			mpTenant = mpTenantService.salvar(mpTenant);
        			
        			mpUsuario.setIndTenant(true);        			
        			mpUsuario.setTenantId(mpTenant.getId().toString());        			
    			}
    			
    			System.out.println("MpLoginBean.login() - 003xxx ( " + mpUsuario.getNome());
    		}
    	}
    	// Trata Objetos...
		List<MpObjeto> mpObjetoList = mpObjetos.mpObjetoList("0");
		//
		System.out.println("MpLoginBean.login() - 003 - ListObjeto.size ( " + mpObjetoList.size());

		if (mpObjetoList.size() == 0) { // Gerar Cadastro Objetos !
			//
			this.geraCadastroObjetos();
		}
    	// Trata Categorias e SubCategorias ...
		List<MpTipoProdutoCategoria> mpTipoProdutoCategoriaList = 
											Arrays.asList(MpTipoProdutoCategoria.values());
		//
		System.out.println("MpLoginBean.login() - 004 - ListCategoria.size( " + 
														mpTipoProdutoCategoriaList.size());
        
    	Iterator<MpTipoProdutoCategoria> itrC = mpTipoProdutoCategoriaList.iterator(); 
    	//
		MpCategoria mpCategoriaPai = new MpCategoria();
		MpCategoria mpCategoria = new MpCategoria();

		while(itrC.hasNext()) {
	    	//            
			MpTipoProdutoCategoria mpTipoProdutoCategoria = (MpTipoProdutoCategoria)
    																			itrC.next();
    		if (null == mpTipoProdutoCategoria.getMpTipoProdutoCategoria()) {
	    		mpCategoriaPai = new MpCategoria();
	    		
	    		mpCategoriaPai.setMpTipoProduto(mpTipoProdutoCategoria.getMpTipoProduto());
	    		mpCategoriaPai.setDescricao(mpTipoProdutoCategoria.getDescricao());

    			try {
					mpCategoriaPai = mpCategoriaService.salvar(mpCategoriaPai);    				
    			}
    			catch(Exception e){
    				System.out.println("MpLoginBean.login() - Exception MpCategoriaPai.salvar() 000 ( " + e.getMessage());     				
    			}
    		} else {
	    		mpCategoria = new MpCategoria();
	    		
	    		mpCategoria.setMpTipoProduto(mpTipoProdutoCategoria.getMpTipoProduto());
	    		mpCategoria.setDescricao(mpTipoProdutoCategoria.getDescricao());
    			mpCategoria.setMpCategoriaPai(mpCategoriaPai);			    			

    			try {
    				mpCategoria = mpCategoriaService.salvar(mpCategoria);
    				
    			}
    			catch(Exception e){
    				System.out.println("MpLoginBean.login() - Exception MpCategoria.salvar() ( " + e.getMessage());     				
    			}
    			//
    			mpCategoriaPai = mpCategorias.porId(mpCategoriaPai.getId());
    			
    			mpCategoriaPai.getMpSubcategorias().add(mpCategoria);

    			try {
    					mpCategoriaPai = mpCategoriaService.salvar(mpCategoriaPai);    				
    			}
    			catch(Exception e){
    				System.out.println("MpLoginBean.login() - Exception MpCategoriaPai.salvar() 001 ( " + e.getMessage());     				
    			}
    		}
    	}
		// Trata Sistema Configuração ...
        List<MpMenuGlobalSistemaConfig> mpMenuGlobalSistemaConfigList = 
											Arrays.asList(MpMenuGlobalSistemaConfig.values());

        Iterator<MpMenuGlobalSistemaConfig> itrGSC = mpMenuGlobalSistemaConfigList.iterator(); 
        //
        while(itrGSC.hasNext()) {
        	//
        	MpMenuGlobalSistemaConfig mpMenuGlobalSistemaConfig = (MpMenuGlobalSistemaConfig)
        																		itrGSC.next();
        	MpSistemaConfig mpSistemaConfig = new MpSistemaConfig();
        	
        	mpSistemaConfig.setParametro(mpMenuGlobalSistemaConfig.getParametro());
        	mpSistemaConfig.setDescricao(mpMenuGlobalSistemaConfig.getDescricao());
        	mpSistemaConfig.setMpTipoCampo(mpMenuGlobalSistemaConfig.getMpTipoCampo());
        	mpSistemaConfig.setValorT(mpMenuGlobalSistemaConfig.getValorT());
        	mpSistemaConfig.setValorN(mpMenuGlobalSistemaConfig.getValorN());
        	mpSistemaConfig.setIndValor(mpMenuGlobalSistemaConfig.getIndValor());
        	mpSistemaConfig.setObjeto(mpMenuGlobalSistemaConfig.getObjeto());
        	
        	this.mpSistemaConfigService.salvar(mpSistemaConfig);
        }
		// =======================================
	}

	public void enviaRegistro() {
		//
		// Trata Esqueci Senha ...
		// -----------------------
		if (this.indEsqueciSenha) {
			this.emailSenha = this.emailRegistro;
			
			this.enviaSenha();
			//
			return ;
		}
		//
//		System.out.println("MpSeguranca.enviaMpNotificacao() - Entrou!");
		//
		String mensagem = "";
		if (this.usuarioRegistro.isEmpty()) mensagem = mensagem + "(Informar Usuário!)";
		else if (this.usuarioRegistro.length() < 5)
			mensagem = mensagem + "(Usuário c/tam.Inválido! Min.5)";
		else if (this.usuarioRegistro.length() > 15)
			mensagem = mensagem + "(Usuário c/tam.Inválido! Max.15)";
		
		if (this.nomeRegistro.isEmpty()) mensagem = mensagem + "(Informar Nome!)";
		else if (this.nomeRegistro.length() < 5)
			mensagem = mensagem + "\n(Nome c/tam.Inválido! Min.5)";
		else if (this.nomeRegistro.length() > 100)
			mensagem = mensagem + "\n(Nome c/tam.Inválido! Max.100)";
		
		if (this.emailRegistro.isEmpty()) mensagem = mensagem + "\n(Informar Email!)";
		
		if (this.senhaRegistro.isEmpty()) mensagem = mensagem + "\n(Informar Senha!)";
		else if (this.senhaRegistro.length() < 5)
			mensagem = mensagem + "\n(Senha c/tam.Inválido! Min.5)";
		else if (this.senhaRegistro.length() > 20)
			mensagem = mensagem + "\n(Senha c/tam.Inválido! Max.20)";
		
		if (this.senhaConfirmaRegistro.isEmpty()) mensagem = mensagem + 
															"\n(Informar Senha Confirmação!)";
		
		if (!this.indAceiteTermo) mensagem = mensagem + "\n(Informar Aceite de Termo!)";
			
		if (!mensagem.isEmpty()) {
			MpFacesUtil.addInfoMessage(mensagem);
			return;
		}
		if (!this.senhaRegistro.equals(this.senhaConfirmaRegistro)) {
			MpFacesUtil.addInfoMessage("Senhas não conferem !");
			return;
		}
		//
		this.mpUsuario = this.mpUsuarios.porLogin(usuarioRegistro);
		if (null == this.mpUsuario)
			this.mpUsuario = new MpUsuario();
		else {
			MpFacesUtil.addInfoMessage(
							"Usuário... já se encontra cadastrado na nossa base de dados!");
			return;
		}
		//
		this.mpUsuario = this.mpUsuarios.porEmail(emailRegistro);
		if (null == mpUsuario)
			this.mpUsuario = new MpUsuario();
		else {
			MpFacesUtil.addInfoMessage(
							"E-mail... já se encontra cadastrado na nossa base de dados!");
			return;
		}

		// -------------------- Trata Grupo ---------------------------
		List<MpGrupo> mpGrupoList = new ArrayList<MpGrupo>();
		
//		MpGrupo mpGrupo = mpCadastroGrupoService.porNome("USUARIOS");
		MpGrupo mpGrupo = mpGrupos.porNome("PROTESTOS_ADMIN");
		if (null == mpGrupo) {
			MpFacesUtil.addInfoMessage("Grupo USUARIOS...não existe! Contactar o Suporte!");
			return;
		}
		//
		mpGrupoList.add(mpGrupo);
		// ------------------------------
		this.mpUsuario = new MpUsuario();

		this.mpUsuario.setLogin(this.usuarioRegistro);
		this.mpUsuario.setLoginGrupo("G." + this.usuarioRegistro.toUpperCase());
		this.mpUsuario.setNome(this.nomeRegistro);
		this.mpUsuario.setEmail(this.emailRegistro);
		this.mpUsuario.setSenha(this.senhaRegistro);
		this.mpUsuario.setMpStatus(MpStatus.REGISTRO);
		
		this.mpUsuario.setMpGrupos(mpGrupoList);
		
		this.mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
		
		// Trata Multitenancy por DISCRIMINATOR (TenantID) ...
		if (this.scIndMultiTenancy) {
			this.mpUsuario.setTenantId(this.mpUsuario.getId().toString());
			this.mpUsuario.setIndTenant(true);
			
			this.mpUsuario = this.mpUsuarioService.salvar(mpUsuario);
		}
		//
		MailMessage message = mpMailer.novaMensagem();
		
		try {
			//
			message.to(this.emailRegistro)
				.subject("MPXDS - Confirmação de REGISTRO")
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
															"/emails/mpUsuarioRegistro.template")))
				.put("mpUsuario", this.mpUsuario)
				.put("scSistemaURL", this.scSistemaURL)
				.put("locale", new Locale("pt", "BR"))
				.send();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro envio do email ! (" + e.toString());
			
			return;
		}
		// 
		String msg = "\n========================";
		msg = msg + "\nUma mensagem foi enviada para o seu e-mail.";

		msg = msg + "\n\nEsta mensagem contém um link, para completar";
		msg = msg + "\no seu registro.";
		msg = msg + "\nSe a mensagem não constar em sua caixa de entrada,";
		msg = msg + "\nverifique as pastas de SPAM e a Lixeira de seu e-mail.";
		msg = msg + "\n\nSe você encontrar dificuldades contate o ";
		msg = msg + "\nadministrador pela nossa página de contato.";
		msg = msg + "\n==========================";
		//
		MpFacesUtil.addInfoMessage(msg);
		// logs info
		logger.info("Usuário REGISTRO! (Login=" + mpUsuario.getLogin());
	}

	public void enviaSenha() {
//		System.out.println("MpLoginBean.enviaSenha() - ( Email = " + this.emailSenha);
		//
		if (this.emailSenha.isEmpty()) {
			MpFacesUtil.addInfoMessage("Informar Email... para recuperar Senha!");
			return;
		}
		//
		if (indLoginAmbiente) {
			this.mpUsuarioTenant = mpUsuarioTenants.porEmail(this.emailSenha);
			if (null == this.mpUsuarioTenant) {
				MpFacesUtil.addErrorMessage(
							"E-mail informado... não consta na nossa base de Dados!");
				return;
			}
		} else {
			this.mpUsuario = mpUsuarios.porEmail(this.emailSenha);
			if (null == this.mpUsuario) {
				MpFacesUtil.addErrorMessage(
							"E-mail informado... não consta na nossa base de Dados!");
				return;
			}
		}
		//
//		System.out.println("MpLoginBean.enviaSenha() - ( Email = " + this.emailSenha +
//															" ( Usuario = " + this.mpUsuario);
		//
		MailMessage message = mpMailer.novaMensagem();
		
		if (indLoginAmbiente) {
			message.to(this.emailSenha)
				.subject("MPXDS - Solicitação Recuperação Senha")
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
											"/emails/mpRecuperacaoSenhaTenant.template")))
				.put("mpUsuarioTenant", this.mpUsuarioTenant)
				.put("locale", new Locale("pt", "BR"))
				.send();
			// logs info
			logger.info("Usuário Ambiente Recuperação Senha! (Login=" + mpUsuarioTenant.getLogin()
											+ "/" + mpUsuarioTenant.getMpPessoa().getEmail());
		} else {
			message.to(this.emailSenha)
				.subject("MPXDS - Solicitação Recuperação Senha")
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
														"/emails/mpRecuperacaoSenha.template")))
				.put("mpUsuario", this.mpUsuario)
				.put("locale", new Locale("pt", "BR"))
				.send();
			// logs info
			logger.info("Usuário Recuperação Senha! (Login=" + mpUsuario.getLogin() + "/" +
																		mpUsuario.getEmail());
		}
		//
		MpFacesUtil.addInfoMessage("Recuperação Senha... enviada por e-mail com sucesso!");
	}

	public void montaMenu() {
		//
//		System.out.println("MpLoginBean.montaMenu - 000 ( " + this.loginEmail + " / " +
//																		this.loginEmailAnt);
		//
		if (!this.loginEmail.equals(this.loginEmailAnt)) {
			//
			this.loginEmailAnt = this.loginEmail;
			this.indMenu = false;
			this.indMenuDoseCerta = false;
		}
		//
		if (this.indMenu) return;
		//	
		this.indMenu = true;
		//
        this.modelMenu = new DefaultMenuModel();
        this.modelMenuLeft = new DefaultMenuModel();
        //
        List<MpGrupoMenu> mpGrupoMenuList = Arrays.asList(MpGrupoMenu.values());
        
//    	Iterator<MpGrupoMenu> itr = mpGrupoMenuList.iterator(); 
    	//
//    	while(itr.hasNext()) {
    	for (MpGrupoMenu mpGrupoMenu : mpGrupoMenuList) {
	    	//
//    		MpGrupoMenu mpGrupoMenu = (MpGrupoMenu) itr.next();

//    		System.out.println("MpLoginBean.montaMenu - 000 ( " + mpGrupoMenu.getDescricao() +
//    														" / " + this.mpUsuario.getNome());
    		//
    		List<MpObjeto> mpObjetoList = mpUsuarios.porMpGrupoMenu(mpGrupoMenu,
    															this.mpUsuario.getMpGrupos());    		
    		if (mpObjetoList.size() > 0) {
    			//
//        		System.out.println("MpLoginBean.montaMenu - 001 ( " + mpGrupoMenu.getDescricao() +
//        						" / " + this.mpUsuario.getNome() + " / " + mpObjetoList.size());
    			//
    	        DefaultSubMenu mySubmenu = new DefaultSubMenu();        
        		DefaultSubMenu mySubmenu1 = new DefaultSubMenu();        
    	        
    	        DefaultSubMenu mySubmenuLeft = new DefaultSubMenu();        
        		DefaultSubMenu mySubmenuLeft1 = new DefaultSubMenu();        
    	        
    	        mySubmenu.setLabel(mpGrupoMenu.getDescricao());
    	        mySubmenuLeft.setLabel(mpGrupoMenu.getDescricao());
    	        //
	            if (null == mpGrupoMenu.getIcon() || mpGrupoMenu.getIcon().isEmpty())
	            	assert(true);
	            else
	            	mySubmenuLeft.setIcon(mpGrupoMenu.getIcon());
    	        
//    	    	Iterator<MpObjeto> itrObj = mpObjetoList.iterator(); 
    	    	//
    	    	MpGrupamentoMenu mpGrupamentoMenuAnt = null;
    	    	//
//    	    	while(itrObj.hasNext()) {
    	    	for (MpObjeto mpObjeto : mpObjetoList) {
    		    	//
    	    		// Evitar acesso as transações no ambitente de homologação...
    	    		//
    	    		if (this.mpUsuario.getLogin().toLowerCase().equals("marcus")
    	    	    ||  this.mpUsuario.getLogin().toLowerCase().equals("mpAdmin")
    	    		||  this.mpUsuario.getLogin().toLowerCase().equals("prisco")
        	    	||  this.mpUsuario.getLogin().toLowerCase().equals("anacampos"))
    	    			assert(true); //nop
    	    		else 
    	    			if (mpObjeto.getTransacao().toLowerCase().equals("cadusu")
        	    		||	mpObjeto.getTransacao().toLowerCase().equals("cadgrup"))
    	    				continue;
    	    		//
//    	    		MpObjeto mpObjeto = (MpObjeto) itrObj.next();

//    	    		System.out.println("MpLoginBean.montaMenu - 001 ( " + mpObjeto.getNome() + 
//    	    			" / " + mpGrupamentoMenuAnt + " / " + mpObjeto.getMpGrupamentoMenu());
    	    		//
    	            if (mpObjeto.getIndSeparator()) {
    	            	//
        	            DefaultSeparator separator = new DefaultSeparator();
        	            
        	            separator.setStyle("width:100%; height:2px");
        	            //
        	            mySubmenu.addElement(separator);
    	            }
    	            
    	            DefaultMenuItem item = new DefaultMenuItem();
    	            DefaultMenuItem itemLeft = new DefaultMenuItem();
    	            //
    	            item.setOutcome(mpObjeto.getCodigo());
    	            itemLeft.setOutcome(mpObjeto.getCodigo());
    	            
    	            if (null == mpObjeto.getIcon() || mpObjeto.getIcon().isEmpty()) {
        	            item.setValue(mpObjeto.getNome());
        	            itemLeft.setValue(mpObjeto.getNome());
    	            } else {
    	            	item.setIcon(mpObjeto.getIcon());
    	            	itemLeft.setIcon(mpObjeto.getIcon());
        	            item.setValue(mpObjeto.getNome());
        	            itemLeft.setValue(mpObjeto.getNome());
    	            }    	            
    	            // Trata Agrupamento 2o.Nivel Menu ...
    	            if (null == mpObjeto.getMpGrupamentoMenu())
    	            	mpObjeto.setMpGrupamentoMenu(MpGrupamentoMenu.NULO);
    	            
    	            if (mpObjeto.getMpGrupamentoMenu().equals(MpGrupamentoMenu.NULO)) {
        	            //
        	            mySubmenu.addElement(item);
        	            mySubmenuLeft.addElement(item);
        	            //
    	            } else // Trata Agrupamento ...
    	            	if (null == mpGrupamentoMenuAnt) {
    	            		//
    	            		mySubmenu1.setStyle("color:black;");
    	            		// mySubmenuLeft1.setStyle("color:black;");
    	            		mySubmenu1.setLabel(mpObjeto.getMpGrupamentoMenu().getDescricao());
    	            		mySubmenuLeft1.setLabel(mpObjeto.getMpGrupamentoMenu().
    	            															getDescricao());
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);

    	            		mpGrupamentoMenuAnt = mpObjeto.getMpGrupamentoMenu();
    	            		//
    	            	} else
    	            	if (mpGrupamentoMenuAnt.equals(mpObjeto.getMpGrupamentoMenu())) {
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);
    	            	} else {
    	            		//
    	            		mySubmenu.addElement(mySubmenu1);
    	            		mySubmenuLeft.addElement(mySubmenuLeft1);

    	            		mySubmenu1 = new DefaultSubMenu();        
    	            		mySubmenuLeft1 = new DefaultSubMenu();        
    	        	        
    	            		mySubmenu1.setStyle("color:black;");
    	            		// mySubmenuLeft1.setStyle("color:black;");
    	            		mySubmenu1.setLabel(mpObjeto.getMpGrupamentoMenu().getDescricao());
    	            		mySubmenuLeft1.setLabel(mpObjeto.getMpGrupamentoMenu().
    	            															getDescricao());
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);

    	            		mpGrupamentoMenuAnt = mpObjeto.getMpGrupamentoMenu(); 	
    	            	}
    	    	}
	            //
    	    	if (mySubmenu1.getElements().size() > 0)
            		mySubmenu.addElement(mySubmenu1);
    	    	if (mySubmenuLeft1.getElements().size() > 0)
            		mySubmenuLeft.addElement(mySubmenuLeft1);
    	    	//
	            this.modelMenu.addElement(mySubmenu);
	            this.modelMenuLeft.addElement(mySubmenuLeft);
    		}
    	}
		//
//		System.out.println("MpLoginBean.montaMenu - 003 ( " + this.modelMenu.getElements().size());
		//
//        DefaultMenuItem item = new DefaultMenuItem();
//		
//        item.setValue("Sair");
//        item.setOutcome("/MpLogout");
//        item.setIcon("ui-icon-extlink");
//        //
//        this.modelMenu.addElement(item);
//        //
	}
	
	public void montaMenuTenant() {
		//
		if (!this.loginEmail.equals(this.loginEmailAnt)) {
			//
			this.loginEmailAnt = this.loginEmail;
			this.indMenu = false;
			this.indMenuDoseCerta = false;
		}
		//
		if (this.indMenu) return;
		//	
		this.indMenu = true;
		//
        this.modelMenu = new DefaultMenuModel();
        this.modelMenuLeft = new DefaultMenuModel();

        List<MpGrupoMenu> mpGrupoMenuList = Arrays.asList(MpGrupoMenu.values());
        
//    	Iterator<MpGrupoMenu> itr = mpGrupoMenuList.iterator(); 
    	//
    	for (MpGrupoMenu mpGrupoMenu : mpGrupoMenuList) {
	    	//
    		List<MpObjeto> mpObjetoList = mpUsuarioTenants.porMpGrupoMenu(mpGrupoMenu, mpUsuarioTenant);
    		if (mpObjetoList.size() > 0) {
    			//
    	        DefaultSubMenu mySubmenu = new DefaultSubMenu();        
        		DefaultSubMenu mySubmenu1 = new DefaultSubMenu();        
    	        
    	        DefaultSubMenu mySubmenuLeft = new DefaultSubMenu();        
        		DefaultSubMenu mySubmenuLeft1 = new DefaultSubMenu();        
    	        
    	        mySubmenu.setLabel(mpGrupoMenu.getDescricao());
    	        mySubmenuLeft.setLabel(mpGrupoMenu.getDescricao());
    	        //
	            if (null == mpGrupoMenu.getIcon() || mpGrupoMenu.getIcon().isEmpty())
	            	assert(true);
	            else
	            	mySubmenuLeft.setIcon(mpGrupoMenu.getIcon());
    	    	//
    	    	MpGrupamentoMenu mpGrupamentoMenuAnt = null;
    	    	//
    	    	for (MpObjeto mpObjeto : mpObjetoList) {
    		    	//
    	            if (mpObjeto.getIndSeparator()) {
    	            	//
        	            DefaultSeparator separator = new DefaultSeparator();
        	            
        	            separator.setStyle("width:100%; height:2px");
        	            //
        	            mySubmenu.addElement(separator);
    	            }
    	            
    	            DefaultMenuItem item = new DefaultMenuItem();
    	            DefaultMenuItem itemLeft = new DefaultMenuItem();
    	            //
    	            item.setOutcome(mpObjeto.getCodigo());
    	            itemLeft.setOutcome(mpObjeto.getCodigo());
    	            
    	            if (null == mpObjeto.getIcon() || mpObjeto.getIcon().isEmpty()) {
        	            item.setValue(mpObjeto.getNome());
        	            itemLeft.setValue(mpObjeto.getNome());
    	            } else {
    	            	item.setIcon(mpObjeto.getIcon());
    	            	itemLeft.setIcon(mpObjeto.getIcon());
        	            item.setValue(mpObjeto.getNome());
        	            itemLeft.setValue(mpObjeto.getNome());
    	            }    	            
    	            // Trata Agrupamento 2o.Nivel Menu ...
    	            if (null == mpObjeto.getMpGrupamentoMenu())
    	            	mpObjeto.setMpGrupamentoMenu(MpGrupamentoMenu.NULO);
    	            
    	            if (mpObjeto.getMpGrupamentoMenu().equals(MpGrupamentoMenu.NULO)) {
        	            //
        	            mySubmenu.addElement(item);
        	            mySubmenuLeft.addElement(item);
        	            //
    	            } else // Trata Agrupamento ...
    	            	if (null == mpGrupamentoMenuAnt) {
    	            		//
    	            		mySubmenu1.setStyle("color:black;");
    	            		// mySubmenuLeft1.setStyle("color:black;");
    	            		mySubmenu1.setLabel(mpObjeto.getMpGrupamentoMenu().getDescricao());
    	            		mySubmenuLeft1.setLabel(mpObjeto.getMpGrupamentoMenu().
    	            															getDescricao());
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);

    	            		mpGrupamentoMenuAnt = mpObjeto.getMpGrupamentoMenu();
    	            		//
    	            	} else
    	            	if (mpGrupamentoMenuAnt.equals(mpObjeto.getMpGrupamentoMenu())) {
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);
    	            	} else {
    	            		//
    	            		mySubmenu.addElement(mySubmenu1);
    	            		mySubmenuLeft.addElement(mySubmenuLeft1);

    	            		mySubmenu1 = new DefaultSubMenu();        
    	            		mySubmenuLeft1 = new DefaultSubMenu();        
    	        	        
    	            		mySubmenu1.setStyle("color:black;");
    	            		// mySubmenuLeft1.setStyle("color:black;");
    	            		mySubmenu1.setLabel(mpObjeto.getMpGrupamentoMenu().getDescricao());
    	            		mySubmenuLeft1.setLabel(mpObjeto.getMpGrupamentoMenu().
    	            															getDescricao());
            	            //
            	            mySubmenu1.addElement(item);
            	            mySubmenuLeft1.addElement(itemLeft);

    	            		mpGrupamentoMenuAnt = mpObjeto.getMpGrupamentoMenu(); 	
    	            	}
    	    	}
	            //
    	    	if (mySubmenu1.getElements().size() > 0)
            		mySubmenu.addElement(mySubmenu1);
    	    	if (mySubmenuLeft1.getElements().size() > 0)
            		mySubmenuLeft.addElement(mySubmenuLeft1);
    	    	//
	            this.modelMenu.addElement(mySubmenu);
	            this.modelMenuLeft.addElement(mySubmenuLeft);
    		}
    	}
    	//
	}
	
	public void geraCadastroObjetos() {
		//
		MpGrupo mpGrupoAdm = mpGrupos.porNome("ADMINISTRADORES");
		if (null == mpGrupoAdm) {
			//
			MpFacesUtil.addInfoMessage(
								"Grupo ADMINISTRADORES... não existe! Contactar o SUPORTE!");
			return;
		}
		MpGrupo mpGrupoProtestoAdm = mpGrupos.porNome("PROTESTOS_ADMIN");
		if (null == mpGrupoProtestoAdm) {
			//
			MpFacesUtil.addInfoMessage("Grupo Protesto ADMIN. não existe! Contactar o SUPORTE!");
			return;
		}
		MpGrupo mpGrupoProtesto = mpGrupos.porNome("PROTESTOS");
		if (null == mpGrupoProtesto) {
			//
			MpFacesUtil.addInfoMessage("Grupo Protesto não existe! Contactar o SUPORTE!");
			return;
		}
		//
		List<MpObjeto> mpObjetoList = new ArrayList<MpObjeto>();
		List<MpObjeto> mpObjetoProtestoAdmList = new ArrayList<MpObjeto>();
		List<MpObjeto> mpObjetoProtestoList = new ArrayList<MpObjeto>();
		//
        List<MpMenuGlobalObjeto> mpMenuGlobalObjetoList = Arrays.
        												asList(MpMenuGlobalObjeto.values());
		//
		System.out.println("MpLoginBean.geraCadastroObjetos() - 000 ( " +
													        mpMenuGlobalObjetoList.size());
        
    	Iterator<MpMenuGlobalObjeto> itrO = mpMenuGlobalObjetoList.iterator(); 
    	//
    	while(itrO.hasNext()) {
	    	//
    		MpMenuGlobalObjeto mpMenuGlobalObjeto = (MpMenuGlobalObjeto) itrO.next();
    		
    		MpObjeto mpObjeto = new MpObjeto();
    		List<MpItemObjeto> mpItemObjetoList = new ArrayList<MpItemObjeto>();

    		System.out.println("...........................MpLoginBean.geraCadastroObjetos() - 000.01 ( Transação: " +
    				mpMenuGlobalObjeto.getTransacao() + " / Codigo: " +
    				mpMenuGlobalObjeto.getCodigo() + " / CodigoId:" +
    				mpMenuGlobalObjeto.getCodigoId() + " / Nome:" +
    				mpMenuGlobalObjeto.getNome());
    		
    		mpObjeto.setTransacao(mpMenuGlobalObjeto.getTransacao());
    		mpObjeto.setCodigo(mpMenuGlobalObjeto.getCodigo());
    		mpObjeto.setCodigoId(mpMenuGlobalObjeto.getCodigoId());
    		mpObjeto.setNome(mpMenuGlobalObjeto.getNome());
    		mpObjeto.setDescricao(mpMenuGlobalObjeto.getDescricao());
    		mpObjeto.setIcon(mpMenuGlobalObjeto.getIcon());
    		mpObjeto.setIndSeparator(mpMenuGlobalObjeto.getIndSeparator());
    		mpObjeto.setMpStatusObjeto(MpStatusObjeto.valueOf(mpMenuGlobalObjeto.
    																	getMpStatusObjeto()));
    		mpObjeto.setMpGrupoMenu(MpGrupoMenu.valueOf(mpMenuGlobalObjeto.getMpGrupoMenu()));
    		mpObjeto.setMpGrupamentoMenu(MpGrupamentoMenu.valueOf(mpMenuGlobalObjeto.
    																	getMpGrupamentoMenu()));
    		mpObjeto.setMpTipoObjeto(MpTipoObjeto.valueOf(mpMenuGlobalObjeto.getMpTipoObjeto()));
    		mpObjeto.setIndResponsive(mpMenuGlobalObjeto.getIndResponsive());

    		mpObjeto.setMpItemObjetos(mpItemObjetoList);
    		
    		mpObjeto = mpObjetoService.salvar(mpObjeto);
    		//
    		mpObjetoList.add(mpObjeto);
    		//
    		if (mpMenuGlobalObjeto.getPerfil_0().equals("PROTESTOS_ADMIN"))
    			mpObjetoProtestoAdmList.add(mpObjeto);
    		if (mpMenuGlobalObjeto.getPerfil_1().equals("PROTESTOS"))
    			mpObjetoProtestoList.add(mpObjeto);
    		//
    	}
    	//
		mpGrupoAdm.setMpObjetos(mpObjetoList);
		
		mpGrupoAdm = mpGrupoService.salvar(mpGrupoAdm);
		//
		if (mpObjetoProtestoAdmList.size() > 0) {
			mpGrupoProtestoAdm.setMpObjetos(mpObjetoProtestoAdmList);
		
			mpGrupoProtestoAdm = mpGrupoService.salvar(mpGrupoProtestoAdm);
		}
		if (mpObjetoProtestoList.size() > 0) {
			mpGrupoProtesto.setMpObjetos(mpObjetoProtestoList);
		
			mpGrupoProtesto = mpGrupoService.salvar(mpGrupoProtesto);
		}
		
		//
		System.out.println("MpLoginBean.geraCadastroObjetos() - 001 ( " + mpObjetoList.size());
	}

	public void trataTransacaoSistema() {
		//
//		System.out.println("MpLoginBean.trataTransacaoSistema() - Entrou! ( " +
//																this.getTransacaoSistema());
		//
		if (null == this.getTransacaoSistema() || this.getTransacaoSistema().length() < 5) {
//			System.out.println("MpLoginBean.trataTransacaoSistema() -  Transação inválida ! ( " +
//																	this.getTransacaoSistema());
			//
			this.setTransacaoSistema("");
			
			return;
		}
		//
		MpObjeto mpObjeto = mpObjetos.porTransacao(this.getTransacaoSistema().trim(), "0");
		if (null == mpObjeto) {
//			System.out.println("MpLoginBean.trataTransacaoSistema() - Transação não encontrada !" +
//																	this.getTransacaoSistema());
			//
			this.setTransacaoSistema("");
			
			return;
		}
		//
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
//			String url = "http://" + this.scSistemaURL + ec.getRequestContextPath() +
//														mpObjeto.getCodigo().trim() + ".xhtml";
//			System.out.println("MpLoginBean.trataTransacaoSistema() - Transação URL ( " + url); 
			
			this.setTransacaoSistema("");
			
			ec.redirect(ec.getRequestContextPath() + mpObjeto.getCodigo().trim());
		//
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void trataEsqueciSenha() {
		//
		if (this.indEsqueciSenha)
			this.indVisivelRegistro = false;
		else
			this.indVisivelRegistro = true;
	}

	public Boolean isAdministrador() {
		//
		if (null == this.mpUsuario || null == this.mpUsuario.getMpGrupos()) {
			//
			this.setIndAdministrador(false);
			//
//			System.out.println("MpLoginBean.isAdministrador(1) ( false");
			
			return false;
		}
		//
		List<MpGrupo> mpGrupoList = this.mpUsuario.getMpGrupos();
		
		for (MpGrupo mpGrupo : mpGrupoList) {
			//
			if (mpGrupo.getDescricao().toUpperCase().equals("ADMINISTRADORES")) {
				//
				this.setIndAdministrador(true);
				//
//				System.out.println("MpLoginBean.isAdministrador(2) ( true");
				
				return true;
			}
		}
		//
		this.setIndAdministrador(false);

//		System.out.println("MpLoginBean.isAdministrador(3) ( false");
		
		return false;
	}
	
	// ---------------------------

	public String getIdI() { return idI; }
	public void setIdI(String idI) { this.idI = idI; }
	public String getIdU() { return idU; }
	public void setIdU(String idU) { this.idU = idU; }

	// ---
	public Boolean getScIndMultiTenancy() { return scIndMultiTenancy; }
	public void setScIndMultiTenancy(Boolean scIndMultiTenancy)
												{ this.scIndMultiTenancy = scIndMultiTenancy; }
	
	public Boolean getScIndMenuTop() { return scIndMenuTop; }
	public void setScIndMenuTop(Boolean scIndMenuTop) { this.scIndMenuTop = scIndMenuTop; }

	public Boolean getScIndMenuLeft() { return scIndMenuLeft; }
	public void setScIndMenuLeft(Boolean scIndMenuLeft) { this.scIndMenuLeft = scIndMenuLeft; }
	
	public Boolean getScIndBarraNavegacao() { return scIndBarraNavegacao; }
	public void setScIndBarraNavegacao(Boolean scIndBarraNavegacao) { 
											this.scIndBarraNavegacao = scIndBarraNavegacao; }
	
	public Boolean getScIndRodapeSistema() { return scIndRodapeSistema; }
	public void setScIndRodapeSistema(Boolean scIndRodapeSistema) { 
												this.scIndRodapeSistema = scIndRodapeSistema; }
	// ---
	
	public MpUsuario getMpUsuario() { return mpUsuario; }
	public void setMpUsuario(MpUsuario mpUsuario) { this.mpUsuario = mpUsuario; }
	
	public MpUsuarioTenant getMpUsuarioTenant() { return mpUsuarioTenant; }
	public void setMpUsuarioTenant(MpUsuarioTenant mpUsuarioTenant) { 
													this.mpUsuarioTenant = mpUsuarioTenant; }
	
	public MpUsuarioLogados getMpUsuarioLogados() { return mpUsuarioLogados; }	

	public String getEmailSenha() { return emailSenha; }
	public void setEmailSenha(String emailSenha) { this.emailSenha = emailSenha; }
	
	public Boolean getIndLoginAmbiente() { return indLoginAmbiente; }
	public void setIndLoginAmbiente(Boolean indLoginAmbiente) {
													this.indLoginAmbiente = indLoginAmbiente; }
	public String getLoginAmbiente() { return loginAmbiente; }
	public void setLoginAmbiente(String loginAmbiente) { this.loginAmbiente = loginAmbiente; }
	
	public Boolean getIndAdministrador() { return indAdministrador; }
	public void setIndAdministrador(Boolean indAdministrador) {
													this.indAdministrador = indAdministrador; }

	public String getLoginEmail() {	return loginEmail; }
	public void setLoginEmail(String loginEmail) { this.loginEmail = loginEmail; }
	
	public String getLoginEmailAnt() {	return loginEmailAnt; }
	public void setLoginEmailAnt(String loginEmailAnt) { this.loginEmailAnt = loginEmailAnt; }
	
	public String getMensagemSistema() { return mensagemSistema; }
	public void setMensagemSistema(String mensagemSistema) {
													this.mensagemSistema = mensagemSistema;	}

	public String getTransacaoSistema() { return transacaoSistema; }
	public void setTransacaoSistema(String transacaoSistema) {
													this.transacaoSistema = transacaoSistema; }
	
	public String getUsuarioRegistro() { return usuarioRegistro; }
	public void setUsuarioRegistro(String usuarioRegistro) {
													this.usuarioRegistro = usuarioRegistro; }

	public String getNomeRegistro() { return nomeRegistro; }
	public void setNomeRegistro(String nomeRegistro) { this.nomeRegistro = nomeRegistro; }

	public String getEmailRegistro() { return emailRegistro; }
	public void setEmailRegistro(String emailRegistro) { this.emailRegistro = emailRegistro; }

	public String getSenhaRegistro() { return senhaRegistro; }
	public void setSenhaRegistro(String senhaRegistro) { this.senhaRegistro = senhaRegistro; }

	public String getSenhaConfirmaRegistro() { return senhaConfirmaRegistro; }
	public void setSenhaConfirmaRegistro(String senhaConfirmaRegistro) { 
										this.senhaConfirmaRegistro = senhaConfirmaRegistro; }
	
    public MenuModel getModelMenu() { return modelMenu; }
    public MenuModel getModelMenuLeft() { return modelMenuLeft; }
    
    public Boolean getIndMenu() { return indMenu; }
    public void setIndMenu(Boolean indMenu) { this.indMenu = indMenu; }
    
    public Boolean getIndMenuDoseCerta() { return indMenuDoseCerta; }
    public void setIndMenuDoseCerta(Boolean indMenuDoseCerta) {
    												this.indMenuDoseCerta = indMenuDoseCerta; }
    
    public Boolean getIndAceiteTermo() { return indAceiteTermo; }
    public void setIndAceiteTermo(Boolean indAceiteTermo) {
    												this.indAceiteTermo = indAceiteTermo; }
    
    public Boolean getIndEsqueciSenha() { return indEsqueciSenha; }
    public void setIndEsqueciSenha(Boolean indEsqueciSenha) {
    												this.indEsqueciSenha = indEsqueciSenha; }
    
    public Boolean getIndVisivelRegistro() { return indVisivelRegistro; }
    public void setIndVisivelRegistro(Boolean indVisivelRegistro) {
    											this.indVisivelRegistro = indVisivelRegistro; }
    
    public Boolean getIndMenuDashboard() { return indMenuDashboard; }
    public void setIndMenuDashboard(Boolean indMenuDashboard) {
    												this.indMenuDashboard = indMenuDashboard; }
    
    public Boolean getScIndLabelCampo() { return scIndLabelCampo; }
    public void setScIndLabelCampo(Boolean scIndLabelCampo) {
    												this.scIndLabelCampo = scIndLabelCampo; }

    public Boolean getScIndAtivaEmail() { return scIndAtivaEmail; }
    public void setScIndAtivaEmail(Boolean scIndAtivaEmail) {
    												this.scIndAtivaEmail = scIndAtivaEmail; }
    public Boolean getScIndAtivaSMS() { return scIndAtivaSMS; }
    public void setScIndAtivaSMS(Boolean scIndAtivaSMS) {
    												this.scIndAtivaSMS = scIndAtivaSMS; }
    public Boolean getScIndAtivaPush() { return scIndAtivaPush; }
    public void setScIndAtivaPush(Boolean scIndAtivaPush) {
    												this.scIndAtivaPush = scIndAtivaPush; }
    public Boolean getScIndAtivaTelegram() { return scIndAtivaTelegram; }
    public void setScIndAtivaTelegram(Boolean scIndAtivaTelegram) {
    											this.scIndAtivaTelegram = scIndAtivaTelegram; }
    public Boolean getScIndAtivaWhatsapp() { return scIndAtivaWhatsapp; }
    public void setScIndAtivaWhatsapp(Boolean scIndAtivaWhatsapp) {
    											this.scIndAtivaWhatsapp = scIndAtivaWhatsapp; }
    public Boolean getScIndAtivaMpComunicator() { return scIndAtivaMpComunicator; }
    public void setScIndAtivaMpComunicator(Boolean scIndAtivaMpComunicator) {
    								this.scIndAtivaMpComunicator = scIndAtivaMpComunicator; }

}