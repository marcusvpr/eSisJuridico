package com.mpxds.mpbasic.model.enums;

public enum MpMenuGlobalObjeto {
	//
//	M03(3, "/pedidos/MpCadastroPedido", "Cadastrar Novo Pedidos", "", "NULO", "PEDIDO", "ATIVO",
//			 										"XHTML", "Novo Pedido", "cadped", false, false),
//	M04(4, "/pedidos/MpPesquisaPedidos", "Pesquisar Pedidos", "", "NULO", "PEDIDO", "ATIVO",
//			 										"XHTML", "Pesquisa", "pesqped", false, false),
//	M24(24, "/clientes/MpCatalogoClientes", "Catálogo de Clientes", "", "PESSOA", "CADASTRO", "ATIVO",
//												"XHTML", "Catálogo Cliente", "catcli", false, false),
//	M41(41, "/controles/MpCadastroTenantUsuario", "Cadastrar Tenants x Usuários", "", "SISTEMA",
//			 "CADASTRO", "ATIVO", "XHTML", "Cadastro Tenants x Usuários", "cadtenusu", false, false),
//	M46(46, "/telegrams/MpTelegram", "Telegram Messagens", "icon-paper-plane-empty", "NULO", 
//								"CONTROLE", "ATIVO", "XHTML", "Telegram", "telegram", false, false),
	M47(47, "FC.CLI.0001", "/sentinel/clientes/mpCadastroCliente.xhtml", "Cadastro Cliente", "icon-male", 
				"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Cliente", "cadcli", false, true, "", ""),
	M48(48, "FC.CLI.0001", "/sentinel/mpDashboard.xhtml", "Dashboard", "icon-home-outline", "NULO", "SENTINEL",
			"ATIVO", "XHTML", "DEFINIR", "Dashboard", "dash", false, true, "PROTESTOS_ADMIN", "PROTESTOS"),
	M49(49, "FC.CLI.0001", "/sentinel/usuarios/mpCadastroUsuario.xhtml", "Cadastro Usuário", "icon-user",
			"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Usuário", "cadusu", false, true, "", ""),
	M50(50, "FC.CLI.0001", "/sentinel/sistemaConfigs/mpCadastroSistemaConfig.xhtml", "Cadastro Sistema " + 
		"Configuração", "icon-cog", "SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Sistema Configuração",
		"cadsistc", false, true, "", ""),
	M51(51, "FC.CLI.0001", "/sentinel/calendarios/mpCalendario.xhtml", "Calendário", "icon-calendar", "NULO",
					"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Calendário DashBoard", "calend", false, true, 
					"PROTESTOS_ADMIN", "PROTESTOS"),
	M52(52, "FC.CLI.0001", "/sentinel/cartorios/custasComposicaos/mpCadastroCustasComposicao", 
			"Cadastrar Custas Composição", 	"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", 
			"Custas Composição", "cadcustcomp", false, true, "", ""),
	M53(53, "FC.CLI.0001", "/sentinel/dolars/mpCadastroDolar.xhtml", "Cadastro Dolar", "icon-money", "NULO",
						"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Dolar", "caddol", false, true, "", ""),
	M54(54, "FC.CLI.0001", "/sentinel/atividades/mpCadastroAtividade.xhtml", "Cadastro Atividade",
			"icon-clipboard", "ALERTA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Atividade", "cadativ",
			false, true, "PROTESTOS_ADMIN", ""),
	M55(55, "FC.CLI.0001", "/sentinel/pacientes/mpCadastroPaciente.xhtml", "Cadastro Paciente", "icon-user",
				"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Paciente", "cadpac", false, true,
				"PROTESTOS_ADMIN", ""),
	M56(56, "FC.CLI.0001", "/sentinel/pacientes/mpWizardPaciente.xhtml", "Wizard Paciente", "icon-user",
				"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "PacienteWizard", "wizpac", false, true,
				"PROTESTOS_ADMIN", ""),
	M57(57, "FC.CLI.0001", "/sentinel/calendarios/mpCadastroCalendario.xhtml", "Cadastro Calendário",
				"icon-calendar", "ALERTA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Calendário", "cadcal", 
				false, true, "PROTESTOS_ADMIN", ""),
	M58(58, "FC.CLI.0001", "/sentinel/alarmes/mpCadastroAlarme.xhtml", "Cadastro Alarme", "icon-clock",
				"ALERTA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Alarme", "cadalarm", false, true,
				"PROTESTOS_ADMIN", ""),
	M59(59, "FC.CLI.0001", "/sentinel/notificacaos/mpCadastroNotificacao.xhtml", "Cadastro Notificação",
			"icon-clock", "ALERTA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Notificação", "cadnotif",
			false, true, "PROTESTOS_ADMIN", ""),
	M60(60, "FC.CLI.0001", "/sentinel/objetos/mpCadastroObjeto.xhtml", "Cadastro Objeto", "icon-coffee-1",
				"SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Objeto", "cadobj", false, true, "", ""),
	M61(61, "FC.CLI.0001", "/sentinel/grupos/mpCadastroGrupo.xhtml", "Cadastro Grupo", " icon-indent-right",
				"SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Grupo", "cadgrup", false, true, "", ""),
	M62(62, "FC.CLI.0001", "/sentinel/usuarios/mpLiberaUsuarios.xhtml", "Libera Usuário Bloqueados", 
		"icon-lock-open", "SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Libera Usuário", "libUsu",
		false, true, "", ""),
	M63(63, "FC.CLI.0001", "/sentinel/controles/mpPesquisaTabelaBDs.xhtml", "Pesquisa Tabela BD", 
			"icon-lock-open", "PESQUISA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Pesquisa Tabela BDs",
			"pesqtabBD", false, true, "", ""),
	M64(64, "FC.CLI.0001", "/sentinel/logs/mpPesquisaLoginLogs", "Pesquisar Login Logs", "icon-eye", "LOGS",
			"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Login Logs", "pesqloglogin", false, false, "", ""),
	M65(65, "FC.CLI.0001", "/sentinel/logs/mpPesquisaSistemaLogs", "Pesquisar Sistema Logs", "icon-eye", "LOGS", 
			"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Sistema Logs", "pesqlogsist", false, false, "", ""),
	M66(66, "FC.CLI.0001", "/sentinel/logs/mpPesquisaErrorLogs", "Pesquisar Error Logs", "icon-eye", "LOGS", 
			"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Error Logs", "pesqlogerro", false, false, "", ""),
	M67(67, "FC.CLI.0001", "/sentinel/logs/mpPesquisaAlertaLogs", "Pesquisar Alerta Logs", "icon-eye", "LOGS", 
			"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Alerta Logs", "pesqlogalert", false, false, "", ""),
	M68(68, "FC.CLI.0001", "/sentinel/logs/mpPesquisaMovimentoLoginLogs", "Pesquisar Movimento Login Logs", 
			"icon-eye", "LOGS",	"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Movimento Logins", "movlogin",
			false, false, "", ""),
	M69(69, "FC.CLI.0001", "/sentinel/tabelaInternas/mpCadastroTabelaInterna", "Cadastrar Tabela Interna",
			"icon-eye", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Tabela Interna", "cadtabint",
			false,	false, "", ""),
	M70(70, "FC.CLI.0001", "/sentinel/graficos/mpGraficoClienteIdade", "Gráfico Cliente Idade", "icon-eye", "NULO", 
		"GRAFICO", "ATIVO", "XHTML", "DEFINIR", "Gráfico Cliente Idade", "grafcliida", false, false, "", ""),
	M71(71, "FC.CLI.0001", "/sentinel/graficos/mpGraficoDolar", "Gráfico Dolar", "icon-eye", "NULO", 
			"GRAFICO", "ATIVO", "XHTML", "DEFINIR", "Gráfico Dolar", "grafdol", false, false, "", ""),
	M72(72, "FC.CLI.0001", "/sentinel/graficos/mpGraficoPedidoCriado", "Gráfico Pedido Criado", "icon-eye", "NULO", 
		"GRAFICO", "ATIVO", "XHTML", "DEFINIR", "Gráfico Pedido Criado", "grafpedcri", false, false, "", ""),
	M73(73, "FC.CLI.0001", "/sentinel/graficos/mpGraficoUsuarioLogin", "Gráfico Usuário Login", "icon-eye", "NULO", 
		"GRAFICO", "ATIVO", "XHTML", "DEFINIR", "Gráfico Usuário Login", "grafusulog", false, false, "", ""),
	M74(74, "FC.CLI.0001", "/sentinel/albumFotos/mpCadastroAlbumFoto", "Cadastrar Album Foto", "icon-picture",
		"NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Album Foto/Arquivo", "cadalbum", false, false, "", ""),
	M75(75, "FC.CLI.0001", "/sentinel/relatorios/mpRelatorioPedidosEmitidos", "Listagem Pedidos emitidos",
			"icon-print", "NULO", "RELATORIO", "ATIVO", "XHTML", "DEFINIR", "Pedidos Emitidos", "relped",
			false, false, "", ""),
	M76(76, "FC.CLI.0001", "/sentinel/pessoas/mpCadastroPessoa", "Cadastrar Pessoas", "icon-user", "PESSOA",
				"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Pessoa", "cadpessoa", false, false,
				"PROTESTOS_ADMIN", ""),
	M77(77, "FC.CLI.0001", "/sentinel/categorias/mpCadastroCategoria", "Cadastrar Categorias", "icon-ticket", 
			"NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Categoria", "cadcateg", false, false, "", ""),
	M78(78, "FC.CLI.0001", "/sentinel/produtos/mpCadastroProduto", "Cadastrar Produtos", "icon-basket", "NULO",
						"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Produto", "cadprod", true, false, "", ""),
	M79(79, "FC.CLI.0001", "/sentinel/receitas/mpCadastroReceita", "Cadastrar Receita", "icon-medkit", "NULO",
					"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Receita", "cadreceit", true, false, "", ""),
	M80(80, "FC.CLI.0001", "/sentinel/tenants/mpCadastroTenant", "Cadastrar Tenants", "icon-user-male",
			"SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Tenant", "cadtenant", true, false, "", ""),
	M81(81, "FC.CLI.0001", "/sentinel/pedidos/mpCadastroPedido", "Cadastrar Pedido", "icon-truck", "NULO",
						"COMERCIO", "ATIVO", "XHTML", "DEFINIR", "Pedido", "cadped", true, false, "", ""),
	M82(82, "FC.CLI.0001", "/sentinel/clientes/mpCatalogoClientes", "Catálogo de Clientes", "icon-users-2",
			"PESQUISA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Catálogo Cliente", "catcli",	false, false,
			"", ""),
	M83(83, "FC.CLI.0001", "/sentinel/usuarios/mpPesquisaUsuariosOnline", "Pesquisar Usuários Online",
			"icon-users-2",	"SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Usuários Online", 
			"pesqusuon", false, false, "", ""),
//	M84(84, "FC.CLI.0001", "/sentinel/orcamentos/mpCadastroOrcamento", "Cadastrar Orçamentos", "icon-truck", 
//			"NULO", "COMERCIO", "ATIVO", "XHTML", "DEFINIR", "Orçamento", "cadorcam", true, false, "", ""),
	M85(85, "FC.CLI.0001", "/sentinel/usuarios/mpCadastroUsuarioTenant.xhtml", "Cadastro Usuário TENANT", 
			"icon-user", "PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Usuário Ambiente", "cadusuamb",
			false, true, "PROTESTOS_ADMIN", ""),
	M86(86, "FC.CLI.0001", "/sentinel/cartorios/atos/mpCadastroAto.xhtml", "Cadastrar Ato", "icon-blank", "NULO",
			"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Ato", "cadato", false, true, "", ""),
	M87(87, "FC.CLI.0001", "/sentinel/cartorios/tipoProtocolos/mpCadastroTipoProtocolo", 
			"Cadastrar Tipo Protocolo", "icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", 
			"Tipo Protocolo", "cadtipprotoc", false, true, "", ""),
	M88(88, "FC.CLI.0001", "/sentinel/cartorios/procedimentos/mpCalculaAtos.xhtml", "Calcular Atos", 
		"icon-calculator", "PROCEDIMENTO",	"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Calcula Atos",
		"calcato", false, true, "", ""),
	M89(89, "FC.CLI.0001", "/sentinel/cartorios/procedimentos/mpCargaDadosBD.xhtml", "Carregar Dados BD", 
		"icon-blank", "PROCEDIMENTO",	"SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Carga Dados", "cargabd",
		false, true, "", ""),
	M90(90, "FC.CLI.0001", "/sentinel/turnos/mpCadastroTurno.xhtml", "Cadastro Turno", "icon-clock", "NULO", 
		"CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Turno", "cadturno", false, true,	"PROTESTOS_ADMIN", ""),
	M91(91, "FC.CLI.0001", "/sentinel/sistemaInternos/mpEnviaSql.xhtml", "Enviar SQL para Banco Dados", 
		"icon-blank", "SISTEMA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Envia SQL", "envsql", false, 
		true, "", ""),
	M92(92, "FC.CLI.0001", "/sentinel/cartorios/importacaos/mpRecebeTitulos.xhtml", "Receber Titulos",
		"icon-blank", "PROCEDIMENTO", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Recebe Titulos", "rectit",
		false, true, "", ""),
	M93(93, "FC.CLI.0001", "/sentinel/cartorios/importacaos/mpRecebimento.xhtml", "Recebimento", "icon-blank",
		"PROCEDIMENTO", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Recebimento", "receb", false, true, "", ""),
	M94(94, "FC.CLI.0001", "/sentinel/cartorios/feriados/mpCadastroFeriado.xhtml", "Cadastrar Feriados",
		"icon-blank", "CADASTRO", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Feriado", "cadferiado", false,
		true, "", ""),
	M95(95, "FC.CLI.0001", "/sentinel/cartorios/atualizacaos/mpAtualizaTitulos.xhtml", "Atualizar Titulos",
		"icon-blank", "PROCEDIMENTO", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Atualiza Titulos", "atutit",
		false, true, "", ""),
	M96(96, "FC.CLI.0001", "/sentinel/cartorios/dataProcessos/mpCadastroDataProcesso", "Cadastrar Data Processo",
			"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Data Processo", "caddatproc",
			false, true, "", ""),
	M97(97, "FC.CLI.0001", "/sentinel/notificacaos/mpCadastroNotificacaoUsuario.xhtml", "Cadastro Notificação" +
		" Usuário", "icon-clock", "ALERTA", "SENTINEL", "ATIVO", "XHTML", "DEFINIR", "Notificação Usuário",
		"cadnotifusu", false, true, "PROTESTOS_ADMIN", ""),
	M98(98, "FC.CLI.0001", "/sentinel/usuarioIffs/mpCadastroUsuarioIff.xhtml", "Cadastro Usuário Iff", "icon-users",
			"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Cadastro Usuário Iff", "cadusuiff", false, true,
			"ADMIN_IFF", ""),
	M99(99, "FC.CLI.0001", "/sentinel/cartorios/observacaos/mpCadastroObservacao.xhtml", "Cadastrar Observação",
			"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Observação", "cadobs", false,
			true, "", ""),
	M100(100, "FC.CLI.0001", "/sentinel/cartorios/titulos/mpCadastroTitulo.xhtml", "Cadastrar Titulo",
			"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Titulo", "cadtit", false,
			true, "", ""),
	M101(101, "FC.CLI.0001", "/sentinel/cartorios/pessoaTitulos/mpCadastroPessoaTitulo.xhtml", "Cadastrar Pessoa Titulo",
			"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Pessoa Titulo", "cadpestit", false,
			true, "", ""),
	M102(102, "FC.CLI.0001", "/sentinel/engReqs/projetos/mpCadastroProjeto.xhtml", "Cadastrar Projeto",
			"icon-blank", "NULO", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Projeto", "cadproj", false,
			true, "", ""),
	M103(103, "FC.CLI.0001", "/sentinel/relatorios/mpRelatorioProdutos", "Listagem Produtos",
			"icon-print", "NULO", "RELATORIO", "ATIVO", "XHTML", "DEFINIR", "Produtos", "relprod",
			false, false, "", ""),
	M104(104, "FC.CON.0001", "/sentinel/contatos/mpCadastroContato.xhtml", "Cadastro Contato", "icon-male", 
			"PESSOA", "CADASTRO", "ATIVO", "XHTML", "DEFINIR", "Contato", "cadcont", false, true, "", "");
	//
	private Integer id;
	private String codigoId;
	private String codigo;
	private String descricao;
	private String icon;
	private String mpGrupamentoMenu;
	private String mpGrupoMenu;
	private String mpStatusObjeto;
	private String mpTipoObjeto;
	private String mpTipoObjetoSistema;
	private String nome;
	private String transacao;
	private Boolean indSeparator;
	private Boolean indResponsive;
	private String perfil_0;
	private String perfil_1;
	
	// ---
	
	MpMenuGlobalObjeto(Integer id, 
				String codigoId,
				String codigo,
				String descricao,
				String icon,
				String mpGrupamentoMenu,
				String mpGrupoMenu,
				String mpStatusObjeto,
				String mpTipoObjeto,
				String mpTipoObjetoSistema,
				String nome,
				String transacao,
				Boolean indSeparator,
				Boolean indResponsive,
				String perfil_0,
				String perfil_1) {
		this.id = id;
		this.codigoId = codigoId;
		this.codigo = codigo;
		this.descricao = descricao;
		this.icon = icon;
		this.mpGrupamentoMenu = mpGrupamentoMenu;
		this.mpGrupoMenu = mpGrupoMenu;
		this.mpStatusObjeto = mpStatusObjeto;
		this.mpTipoObjeto = mpTipoObjeto;
		this.mpTipoObjetoSistema = mpTipoObjetoSistema;
		this.nome = nome;
		this.transacao = transacao;
		this.indSeparator = indSeparator;
		this.indResponsive = indResponsive;
		this.perfil_0 = perfil_0;
		this.perfil_1 = perfil_1;
	}

	public Integer getId() { return id; }
	
	public String getCodigoId() {	return this.codigoId; }

	public String getCodigo() {	return this.codigo; }
	
	public String getDescricao() { return this.descricao; }

	public String getIcon() { return this.icon; }

	public String getMpGrupamentoMenu() { return this.mpGrupamentoMenu; }

	public String getMpGrupoMenu() { return this.mpGrupoMenu; }

	public String getMpStatusObjeto() { return this.mpStatusObjeto; }

	public String getMpTipoObjetoSistema() { return this.mpTipoObjetoSistema; }

	public String getMpTipoObjeto() { return this.mpTipoObjeto;	}

	public String getNome() { return this.nome; }

	public String getTransacao() { return this.transacao; }
	  
	public Boolean getIndSeparator() { return this.indSeparator; }
	  
	public Boolean getIndResponsive() { return this.indResponsive; }

	public String getPerfil_0() { return this.perfil_0; }

	public String getPerfil_1() { return this.perfil_1; }
	
}