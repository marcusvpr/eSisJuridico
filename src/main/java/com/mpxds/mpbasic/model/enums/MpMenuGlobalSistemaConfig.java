package com.mpxds.mpbasic.model.enums;

public enum MpMenuGlobalSistemaConfig {
	
	G01("indLoginDolar", "Ativa Captura Dolar após Login", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G02("indMultiTenancy", "Ativa Ambiente/MultiTenant Login", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G03("numeroLoginError", "Número Tentativas de Login", MpTipoCampo.NUMERO, "", 3, false, ""),
	G04("numeroDiasTrocaSenha", "Número Dias Troca Senha", MpTipoCampo.NUMERO, "", 180, false, ""),
	G05("sistemaTelaInicial", "Tela Inicial do Sistema", MpTipoCampo.TEXTO, "default", 0, false, ""),
	G06("indAlertaCalendario", "Ativa Alerta Calendário", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G07("tempoAlertaCalendario", "Tempo(Em Minutos) Alerta Calendário", MpTipoCampo.NUMERO, "", 60,
																				false, "MpAlertaTempo"),
	G08("tipoAlertaCalendario", "Tipo Alerta Calendário", MpTipoCampo.TEXTO, "NOTIFICACAO", 0,
																				false, "MpAlertaTipo"),
	G09("indAlertaAtividade", "Ativa Alerta Atividade", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G10("tempoAlertaAtividade", "Tempo(Em Minutos) Alerta Atividade", MpTipoCampo.NUMERO, "", 60,
																			false, "MpAlertaTempo"),
	G11("tipoAlertaAtividade", "Tipo Alerta Atividade", MpTipoCampo.TEXTO, "NOTIFICACAO", 0, false, "MpAlertaTipo"),
	G12("indAlertaAlarme", "Ativa Alerta Alarme", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G13("tipoAlertaAlarme", "Tipo Alerta Alarme", MpTipoCampo.TEXTO, "NOTIFICACAO", 0, false, "MpAlertaTipo"),
	G14("numeroAlertaAlarme", "Número Alerta Alarme", MpTipoCampo.NUMERO, "", 3, false, ""),
	G15("indAlertaEstoqueReposicao", "Ativa Alerta Estoque Reposição", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G16("numeroDiasEstoqueReposicao", "Número Dias Estoque Reposição", MpTipoCampo.NUMERO, "", 60, false, ""),
	G17("sistemaURL", "URL do Sistema", MpTipoCampo.TEXTO, "www.mpxds.com", 0, false, ""),
	G18("indMenuTop", "Ativa Menu Topo Sistema", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G19("indMenuLeft", "Ativa Menu Left Sistema", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G20("indBarraNavegacao", "Ativa Barra Navegação", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G21("indRodapeSistema", "Ativa Rodapé Sistema", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G22("indAtivaJob", "Ativa Job Scheduler", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G23("indCapturaFoto", "Ativa Captura Foto", MpTipoCampo.BOOLEAN, "", 0, true, ""),
	G24("oficVariavel", "Valor Variavel(%)", MpTipoCampo.NUMERO, "", 2, true, ""),
	G25("oficLei3217", "Valor Lei.3217(%)", MpTipoCampo.NUMERO, "", 20, true, ""),
	G26("oficLei4664", "Valor Lei.4664(%)", MpTipoCampo.NUMERO, "", 5, true, ""),
	G27("oficLei111", "Valor Lei.111(%)", MpTipoCampo.NUMERO, "", 5, true, ""),
	G28("oficLei6281", "Valor Lei.6281(%)", MpTipoCampo.NUMERO, "", 4, true, ""),
	G29("oficPathRecebeTXT", "Caminho(Path) recebimento arquivo TXT", MpTipoCampo.TEXTO,
														   "C:\\RECEBE_TXT\\", 0, true, ""),
	G30("oficPathRecebeATO", "Caminho(Path) recebimento arquivo ATO", MpTipoCampo.TEXTO,
															"C:\\RECEBE_ATO\\", 0, true, ""),
	G31("indLabelCampo", "Ativa Exibição Label nos Campos", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G32("indAtivaEmail", "Ativa Envio de EMAIL", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G33("indAtivaSMS", "Ativa Envio de SMS", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G34("indAtivaPush", "Ativa Envio de Push", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G35("indAtivaTelegram", "Ativa Envio de Telegram", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G36("indAtivaWhatsapp", "Ativa Envio de Whatsapp", MpTipoCampo.BOOLEAN, "", 0,	false, ""),
	G37("indAtivaMpComunicator", "Ativa Envio de MpComunicator", MpTipoCampo.BOOLEAN, "", 0, false, ""),
	G38("oficServentiaECartorioRJ", "Código Escrevente - eCartorioRJ", MpTipoCampo.TEXTO, "????", 0, false, "");

	// ---
	
	private String parametro;
	private String descricao;

	private MpTipoCampo mpTipoCampo;
	
	private String valorT;
	private Integer valorN;
	private Boolean indValor;
	

	private String objeto;
	
	// ---
	
	MpMenuGlobalSistemaConfig(String parametro
							  ,	String descricao
							  , MpTipoCampo mpTipoCampo
							  , String valorT
							  , Integer valorN
							  , Boolean indValor
							  , String objeto) {
		this.parametro = parametro;
		this.descricao = descricao;
		this.mpTipoCampo = mpTipoCampo;
		this.valorT = valorT;
		this.valorN = valorN;
		this.indValor = indValor;
		this.objeto = objeto;
	}

	public String getParametro() { return this.parametro; }

	public String getDescricao() { return this.descricao; }

	public MpTipoCampo getMpTipoCampo() { return this.mpTipoCampo; }

	public String getValorT() { return this.valorT; }
	public Integer getValorN() { return this.valorN; }
	public Boolean getIndValor() { return this.indValor; }

	public String getObjeto() { return this.objeto; }
	
}