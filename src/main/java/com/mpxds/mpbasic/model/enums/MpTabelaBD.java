package com.mpxds.mpbasic.model.enums;

public enum MpTabelaBD {

	MP_ALARME(1, "MpAlarme", "Alarme", "Sistema"), 
	MP_ALERTA(2, "MpAlerta", "Alerta", "Sistema"),
	MP_ARQUIVOBD(3, "MpArquivoBD", "Arquivo Banco Dados(BD)", "Sistema"),
	MP_ATIVIDADE(4, "MpAtividade", "Atividade", "Sistema"), 
	MP_CALENDARIO(5, "MpCalendario", "Calendário", "Sistema"), 
	MP_CATEGORIA(6, "MpCategoria", "Categoria", "Sistema"), 
	MP_CHAMADO(7, "MpChamado", "Sistema Chamado", "Sistema"),
	MP_CLIENTE(8, "MpCliente", "Cliente", "Pessoa"),
	MP_COMPRA(9, "MpCompra", "Compra", "Produto"),
	MP_CONTATO(10, "MpContato", "Contato", "Pessoa"),
	MP_DEPENDENTE(11, "MpDependente", "Dependente", "Pessoa"),
	MP_DOLAR(12, "MpDolar", "Dolar", "Sistema"),
	MP_ENDERECO(13, "MpEndereco", "Endereço", "Sistema"),
	MP_GRUPO(14, "MpGrupo", "Grupo Usuário", "Sistema"),
	MP_GRUPO_OBJETO(15, "MpGrupoObjeto", "Grupo Usuário x Objeto", "Sistema"),
	MP_ITEM_COMPRA(16, "MpItemCompra", "Item Compra", "Produto"),
	MP_ITEM_PEDIDO(17, "MpItemPedido", "Item Pedido", "Produto"),
	MP_ITEM_RECEITA(18, "MpItemReceita", "Item Receita", "Produto"),
	MP_MENSAGEM_MOVIMENTO(19, "MpMensagemMovimento", "Mensagem Movimento", "Sistema"),
	MP_MOVIMENTO_PRODUTO(20, "MpMovimentoProduto", "Movimento Produto", "Produto"),
	MP_NOTIFICACAO(21, "MpNotificacao", "Sistema Notificação", "Sistema"),
	MP_NOTIFICACAO_USUARIO(22, "MpNotificacaoUsuario", "Sistema Notificação Usuário", "Sistema"),
	MP_OBJETO(23, "MpObjeto", "Sistema Objeto", "Sistema"),
	MP_PACIENTE(25, "MpPaciente", "Paciente", "Pessoa"),
	MP_PEDIDO(26, "MpPedido", "Pedido", "Produto"),
	MP_PESSOA(27, "MpPessoa", "Pessoa", "Pessoa"),
	MP_PESSOA_PACIENTE(28, "MpPessoaPaciente", "Pessoa x Paciente", "Pessoa"),
	MP_PRODUTO(29, "MpProduto", "Produto", "Produto"),
	MP_RECEITA(30, "MpReceita", "Receita", "Produto"),
	MP_SISTEMA_CONFIG(31, "MpSistemaConfig", "Sistema Configuração", "Sistema"),
	MP_TABELA_INTERNA(32, "MpTabelaInterna", "Sistema Tabela Interna", "Sistema"),
	MP_TENANT(33, "MpTenant", "Tenant", "Sistema"),
	MP_TENANT_USUARIO(34, "MpTenantUsuario", "Tenant x Usuário", "Sistema"),
	MP_TURNO(35, "MpTurno", "Turno", "Pessoa"),
	MP_USUARIO(36, "MpUsuario", "Usuário", "Pessoa"),
	MP_USUARIO_TENANT(37, "MpUsuarioTenant", "Usuário Tenant", "Pessoa"),
	
	MP_ER_FUNCIONALIDADE(38, "MpFuncionalidade", "Funcionalidade", "Sistema ER"),
	MP_ER_ITEM_OBJETO(39, "MpItemObjeto", "Item Objeto", "Sistema ER"),
	MP_ER_MACRO_REQUISITO(40, "MpMacroRequisito", "Macro Requisito", "Sistema ER"),
	MP_ER_MODULO(41, "MpModulo", "Modulo", "Sistema ER"),
	MP_ER_PROJETO(42, "MpProjeto", "Projeto", "Sistema ER"),
	MP_ER_REGRA_NEGOCIO(43, "MpRegraNegocio", "Regra Negocio", "Sistema ER"),
	MP_ER_REQUISITO_FUNCIONAL(44, "MpRequisitoFuncional", "Requisito Funcional", "Sistema ER"),
	MP_ER_REQUISITO_NAO_FUNCIONAL(45, "MpRequisitoNaoFuncional", "Requisito Não Funcional", "Sistema ER"),
	MP_ER_SEQUENCIA(46, "MpSequencia", "Sequencia", "Sistema ER"),

	MP_IFF_AREA(47, "MpAreaIff", "Area IFF", "IFF"),
	MP_IFF_SETOR_RAMAL(48, "MpSetorRamalIff", "Setor Ramal IFF", "IFF"),
	MP_IFF_USUARIO(49, "MpUsuarioIff", "Area IFF", "IFF"),

	MP_LD_LIVRO_DIARIO(50, "MpLivroDiario", "Livro Diário", "Cartório"),
	MP_LD_TIPO_LANCAMENTO(51, "MpTipoLancamento", "Tipo Lançamento", "Cartório"),

	MP_ALERTA_LOG(52, "MpAlertaLog", "Alerta Log", "Sistema LOG"),
	MP_ERROR_LOG(53, "MpErrorLog", "Error Log", "Sistema LOG"),
	MP_LOGIN_LOG(54, "MpLoginLog", "Login Log", "Sistema LOG"),
	MP_MOVIMENTO_LOGIN(55, "MpMovimentoLogin", "Movimento Login", "Sistema LOG"),
	MP_SISTEMA_LOG(56, "MpSistemaLog", "Sistema Log", "Sistema LOG"),
	
	MP_PT01_BANCO(57, "MpBanco", "Banco", "Cartório"),
	MP_PT01_DATA_PROCESSO(58, "MpDataProcesso", "Data Processo", "Cartório"),
	MP_PT01_ESPECIE(59, "MpEspecie", "Especie", "Cartório"),
	MP_PT01_OBSERVACAO(60, "MpObservacao", "Observacao", "Cartório"),
	MP_PT01_PESSOA_TITULO(61, "MpPessoaTitulo", "Pessoa Titulo", "Cartório"),
	MP_PT01_TITULO(62, "MpTitulo", "Titulo", "Cartório"),

	MP_PT05_ATO(63, "MpAto", "Ato", "Cartório"),
	MP_PT05_ATO_COMPOSICAO(64, "MpAtoComposicao", "Ato Composição", "Cartório"),
	MP_PT05_CUSTAS_COMPOSICAO(65, "MpCustasComposicao", "Custas Composição", "Cartório"),
	MP_PT05_HEADER(66, "MpHeader", "Header", "Cartório"),
	MP_PT05_IMPORTAR_CONTROLE(67, "MpImportarControle", "Importar Controle", "Cartório"),
	MP_PT05_REMESSA(68, "MpRemessa", "Remessa", "Cartório"),
	MP_PT05_TRAILLER(69, "MpTrailler", "Trailler", "Cartório"),
	MP_PT05_TRANSACAO(70, "MpTransacao", "Transação", "Cartório"),
	
	MP_PT08_FERIADO(72, "MpFeriado", "Feriado", "Cartório"),
	MP_PT08_TIPO_PROTOCOLO(73, "MpTipoProtocolo", "Tipo Protocolo", "Cartório"),

	MP_SJ_CLIENTE(74, "MpCliente", "Cliente", "SisJuri"),
	MP_SJ_PESSOA_FISICA(75, "MpPessoaFisica", "Pessoa Fisica", "SisJuri"),
	MP_SJ_PESSOA_JURIDICA(76, "MpPessoaJuridica", "Pessoa Juridica", "SisJuri"),
	MP_SJ_PESSOA(77, "MpPessoa", "Pessoa", "SisJuri"),
	MP_SJ_PROCESSO(78, "MpProcesso", "Processo", "SisJuri"),
	MP_SJ_PROCESSO_ANDAMENTO(79, "MpProcessoAndamento", "Processo Andamento", "SisJuri"),
	MP_SJ_TABELA_INTERNA(80, "MpTabelaInterna", "Tabela Interna", "SisJuri");

//	MP_USUARIO_GRUPO(X1, "MpArtista", "Artista", "WebVideo");
//	MP_USUARIO_GRUPO(X2, "MpEmpresa", "Empresa", "WebVideo");
//	MP_USUARIO_GRUPO(X3, "MpLoja", "Loja", "WebVideo");
	
	private Integer numTabela;
	private String entidade;
	private String descricao;
	private String grupo;
	
	// ---
	
	MpTabelaBD(Integer numTabela, String entidade, String descricao, String grupo) {
		this.numTabela = numTabela;
		this.entidade = entidade;
		this.descricao = descricao;
		this.grupo = grupo;
	}

	public Integer getNumTabela() { return numTabela; }
	public String getEntidade() { return entidade; }
	public String getDescricao() { return descricao; }
	public String getGrupo() { return grupo; }

}