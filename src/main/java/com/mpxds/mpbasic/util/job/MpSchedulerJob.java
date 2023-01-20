package com.mpxds.mpbasic.util.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.velocity.tools.generic.NumberTool;
//import org.primefaces.model.DefaultScheduleEvent;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.model.MpMensagemMovimento;
import com.mpxds.mpbasic.model.MpMovimentoProduto;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.enums.MpAlertaStatus;
import com.mpxds.mpbasic.model.enums.MpPeriodicidade;
import com.mpxds.mpbasic.model.enums.MpTipoMovimento;
import com.mpxds.mpbasic.model.enums.MpStatusMensagem;
import com.mpxds.mpbasic.model.enums.MpTipoContato;
import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.repository.MpAlertaLogs;
import com.mpxds.mpbasic.repository.MpAtividades;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.repository.MpMensagemMovimentos;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.mail.MpMailer;
import com.mpxds.mpbasic.util.sms.MpUtilSMS;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Scheduled(cronExpression = "0 0 21 * * ?") // "0 0/50 * * * ?") // Mvpr-06/2017 Passei para 50 ! 
public class MpSchedulerJob implements Job {
	//
//	private Log log = LogFactory.getLog(MpSchedulerJob.class);

	@Inject
	private MpAlarmes mpAlarmes;	
	@Inject
	private MpAtividades mpAtividades;
	@Inject
	private MpCalendarios mpCalendarios;
	@Inject
	private MpSeguranca mpSeguranca;
	@Inject
	private MpMailer mpMailer;
	@Inject
	private MpUsuarios mpUsuarios;
	@Inject
	private MpAlertaLogs mpAlertaLogs;
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	@Inject
	private MpProdutos mpProdutos;
	@Inject
	private MpMensagemMovimentos mpMensagemMovimentos;

	private MpAlarme mpAlarme = new MpAlarme();	

	private MpMensagemMovimento mpMensagemMovimento;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//	private SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
	//
	private Calendar calendarJobI = Calendar.getInstance();
	private Calendar calendarJobF = Calendar.getInstance();    	
	private Calendar calendarEvent = Calendar.getInstance(); 
	
	private String diaSemJob;
	private String diaSemEvent;
	
	// Sistema Configuração...
	@SuppressWarnings("unused")
	private Integer scAlertaTempo = 0;
	private Integer scNumeroAlertaAlarme = 0;
	private String scSistemaURL = "";
	private Boolean scIndAlertaAlarme = true;
	private Boolean scIndAlertaAtividade = true;
	private Boolean scIndAlertaCalendario = true;
	private Boolean scIndAlertaEstoqueReposicao = true;
	private Integer scNumDiasEstoqueReposicao = 0;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//
		System.out.println("MpSchedulerJobForm.execute()");
//		this.log.info("MpSchedulerJob - Entrou 000");
		
		// ===========================================
		// Trata SMS mensangens de RETORNO não lidas !
		// ===========================================		
		MpUtilSMS.listaMensagensRecebidas();
				
		Date dataHoraJob = new Date();
		
		this.diaSemJob = MpAppUtil.diaSemana(dataHoraJob);
		
		// ==========
    	// Tratar intervalo(+4Min) para selecionar o Alarme x DataHora.Job ...
		// ==========
		this.calendarJobI.setTime(dataHoraJob);
		this.calendarJobF.setTime(dataHoraJob);
		this.calendarJobF.add(Calendar.MINUTE, 4);

		System.out.println("MpSchedulerJobForm.execute() (DateI/F = " + sdf.format(dataHoraJob)
														+ sdf.format(calendarJobF.getTime()));		
		// ======================================
		// Trata captura Configuração Sistema ...
		// ======================================
		MpSistemaConfig mpSistemaConfig = mpSistemaConfigs.porParametro("indAtivaJob");
		if (null == mpSistemaConfig) {
			System.out.println("MpSchedulerJobForm.execute() - 001 (mpSistemaConfig = NULL");
			return; // Não executa !
		} else 
			if (!mpSistemaConfig.getIndValor()) {
				System.out.println("MpSchedulerJobForm.execute() - 002 " + 
							"(mpSistemaConfig.IndValor = " + mpSistemaConfig.getIndValor());
				return; // Não executa !
			}
		//
		System.out.println("MpSchedulerJobForm.execute() - 003 (Date() = " + sdf.format(new Date()));
		//
		mpSistemaConfig = mpSistemaConfigs.porParametro("sistemaURL");
		if (null == mpSistemaConfig)
			this.scSistemaURL = mpSeguranca.getSistemaURL(); // Default = localhost ...
		else
			this.scSistemaURL = mpSistemaConfig.getValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaAlarme");
		if (null == mpSistemaConfig)
			this.scIndAlertaAlarme = false;
		else
			this.scIndAlertaAlarme = mpSistemaConfig.getIndValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaAtividade");
		if (null == mpSistemaConfig)
			this.scIndAlertaAtividade = false;
		else
			this.scIndAlertaAtividade = mpSistemaConfig.getIndValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaCalendario");
		if (null == mpSistemaConfig)
			this.scIndAlertaCalendario = false;
		else
			this.scIndAlertaCalendario = mpSistemaConfig.getIndValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaEstoqueReposicao");
		if (null == mpSistemaConfig)
			this.scIndAlertaEstoqueReposicao = false;
		else
			this.scIndAlertaEstoqueReposicao = mpSistemaConfig.getIndValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("numeroAlertaAlarme");
		if (null == mpSistemaConfig)
			this.scNumeroAlertaAlarme = 3; // Default = 3 vezes ...
		else
			this.scNumeroAlertaAlarme = mpSistemaConfig.getValorN();

		mpSistemaConfig = mpSistemaConfigs.porParametro("tempoAlertaAtividade");
		if (null == mpSistemaConfig)
			this.scAlertaTempo = 60; // Default = 60 minutos ...
		else
			this.scAlertaTempo = mpSistemaConfig.getValorN();

		mpSistemaConfig = mpSistemaConfigs.porParametro("numeroDiasEstoqueReposicao");
		if (null == mpSistemaConfig)
			this.scNumDiasEstoqueReposicao = 60; // Default = 60 dias ...
		else
			this.scNumDiasEstoqueReposicao = mpSistemaConfig.getValorN();
		//
		System.out.println("MpSchedulerJobForm.execute() - 004 (Inds = " + 
					this.scIndAlertaAlarme + "/" + this.scIndAlertaAtividade + "/" +
					this.scIndAlertaCalendario + "/" + this.scIndAlertaEstoqueReposicao + "/");
		//
//		if (this.scIndAlertaAlarme)
//			this.trataAlarme(dataHoraJob);
//		if (this.scIndAlertaAtividade)
//			this.trataAtividade(dataHoraJob);
//		if (this.scIndAlertaCalendario)
//			this.trataCalendario(dataHoraJob);
//		if (this.scIndAlertaEstoqueReposicao)
//			this.trataEstoqueReposicao(dataHoraJob);
		// ==========
		System.out.println("MpSchedulerJobForm.execute() = Calendário/Alarme/Atividade" +
													"/Estoque - " +	sdf.format(dataHoraJob));
	}
		
	private void trataAlarme(Date dataJob) {
		//
		System.out.println("MpSchedulerJobForm.trataAlarme() - Entrou 000");
		//
		MpAlarmeFilter mpAlarmeFilter = MpAppUtil.capturaAlarme(dataJob);
		
		mpAlarmeFilter.setIndAtivo(true);
		//
		List<MpAlarme> mpAlarmeList = mpAlarmes.filtrados(mpAlarmeFilter);
		//
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		//
		System.out.println("MpSchedulerJobForm.trataAlarme() - Entrou 001 ( Inds = "  +
				mpAlarmeFilter.getIndDomingo() + "/" + mpAlarmeFilter.getIndSegunda() + "/" +
				mpAlarmeFilter.getIndTerca() + "/" + mpAlarmeFilter.getIndQuarta() + "/" +
				mpAlarmeFilter.getIndQuinta() + "/" + mpAlarmeFilter.getIndSexta() + "/" +
				mpAlarmeFilter.getIndSabado() + "/ DataJob(I/F) = " + sdf.format(dataJob) + "/" +
				sdf.format(calendarJobF.getTime()) + " / AlarmeList.size = " +
				mpAlarmeList.size());
		//
        for (MpAlarme mpAlarmeX : mpAlarmeList) {
        	//
        	this.mpAlarme = mpAlarmeX;
        	
        	if (null == this.mpAlarme.getIndSemanalmente())
        		this.mpAlarme.setIndSemanalmente(false);
        	
//        	if (!this.mpAlarme.getIndSemanalmente()) {
        		if (this.mpAlarme.getHoraMovimento().after(this.calendarJobI.getTime())
        		&&  this.mpAlarme.getHoraMovimento().before(this.calendarJobF.getTime()))
        			assert(true); // nop
        		else
        			continue;
//        	}
        	//
    		if (null == mpUsuarioAnt
    		||  mpUsuarioAnt != this.mpAlarme.getMpAuditoriaObjeto().getUserInc()) {
    			mpUsuario = mpUsuarios.porLoginEmail(this.mpAlarme.getMpAuditoriaObjeto().
    																				getUserInc());
    			//
    			mpUsuarioAnt = this.mpAlarme.getMpAuditoriaObjeto().getUserInc();
    		}
    		//
			if (null == mpUsuario) {
				System.out.println("MpSchedulerJob.trataAlarme() ( Email NULL = " +
												this.mpAlarme.getMpAuditoriaObjeto().getUserInc());
				return;
			}
			// ============================== //
			// Trata Geração MpAlertaLog ...  //
			// ============================== //
			Boolean indAlertaLog = this.trataMpAlertaLog(this.mpAlarme, dataJob, "ALARME");
			//
			System.out.println("MpSchedulerJobForm.trataAlarme() - Alarme " + 
				sdf.format(this.mpAlarme.getHoraMovimento()) + " / indAlerta = " + indAlertaLog);
			//
			if (indAlertaLog == false) {
	    		MailMessage message = mpMailer.novaMensagem();
	    		
				message.to(mpUsuario.getEmail().trim())
					.subject("Alarme : " + this.mpAlarme.getId())
					.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
    															"/emails/mpAlarme.template")))
					.put("mpAlarme", this.mpAlarme)
					.put("scSistemaURL", this.scSistemaURL)
					.put("numberTool", new NumberTool())
					.put("locale", new Locale("pt", "BR"))
					.send();
			}
        }
	}
	
	private void trataAtividade(Date dataJob) {
		//
		System.out.println("MpSchedulerJobForm.trataAtividade() - 000 (DataJob = " + sdf.format(dataJob));
		//
		this.mpAtividades = MpCDIServiceLocator.getBean(MpAtividades.class);

		List<MpAtividade> mpAtividadeList = this.mpAtividades.mpAtividadeAllList();
		//
		System.out.println("MpSchedulerJobForm.trataAtividade() - 001 (AtivList.size = " + 
																				mpAtividadeList.size());
		//
		for (MpAtividade mpAtividade : mpAtividadeList) {
			//
			System.out.println("MpSchedulerJobForm.trataAtividade() - 002 "
									+ "(DtA= " + sdf.format(mpAtividade.getDtHrAtividade())
									+ " /Period= " + mpAtividade.getMpPeriodicidade()
									+ " /DtI= " + sdf.format(this.calendarJobI.getTime())
									+ " /DtF= " + sdf.format(this.calendarJobF.getTime())) ;
			//
			if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.UNICA)) {
				if (mpAtividade.getDtHrAtividade().after(this.calendarJobI.getTime())
						&& mpAtividade.getDtHrAtividade().before(this.calendarJobF.getTime()))
					assert (true); // nop
				else
					continue;
			} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.DIARIA)) {
				this.calendarEvent.setTime(mpAtividade.getDtHrAtividade());
				this.calendarEvent.set(Calendar.DAY_OF_MONTH, this.calendarJobI.get(
																		Calendar.DAY_OF_MONTH));
				this.calendarEvent.set(Calendar.MONTH, this.calendarJobI.get(Calendar.MONTH));
				this.calendarEvent.set(Calendar.YEAR, this.calendarJobI.get(Calendar.YEAR));
				//
				System.out.println("MpSchedulerJobForm.trataAtividade() - DIARIA "
						+ "(DtA.Evt= " + sdf.format(this.calendarEvent.getTime())
						+ " /DtI= " + sdf.format(this.calendarJobI.getTime())
						+ " /DtF= " + sdf.format(this.calendarJobF.getTime())) ;
				//
				if (this.calendarEvent.getTime().after(this.calendarJobI.getTime())
						&& this.calendarEvent.getTime().before(this.calendarJobF.getTime()))
					assert (true); // nop
				else
					continue;
			} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.SEMANAL)) {
				this.calendarEvent.setTime(mpAtividade.getDtHrAtividade());
				//
				this.diaSemEvent = MpAppUtil.diaSemana(mpAtividade.getDtHrAtividade());

				if (this.diaSemEvent.equals(this.diaSemJob))
					assert (true); // nop
				else
					continue;
			} else {
				if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.QUINZENAL)) {
					//
					Calendar tEvt = Calendar.getInstance();
					Calendar tEvtNext = Calendar.getInstance();

					this.calendarEvent.setTime(mpAtividade.getDtHrAtividade());
					//
					for (Date dateX = this.calendarEvent.getTime(); this.calendarEvent
							.before(this.calendarJobF.getTime()); this.calendarEvent.add(
									Calendar.DATE, 15), dateX = this.calendarEvent.getTime()) {
						tEvt.setTime(dateX);
						if (tEvt.equals(tEvtNext)) {
							//
						}
						//
						tEvtNext.add(Calendar.WEEK_OF_MONTH, 2);
						//
					}
					//
				} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.MENSAL)) {
					// tEvtNext.add(Calendar.MONTH, 1);
				} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.BIMENSAL)) {
					// tEvtNext.add(Calendar.MONTH, 2);
				} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.TRIMENSAL)) {
					// tEvtNext.add(Calendar.MONTH, 3);
				} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.SEMESTRAL)) {
					// tEvtNext.add(Calendar.MONTH, 6);
				} else if (mpAtividade.getMpPeriodicidade().equals(MpPeriodicidade.ANUAL)) {
					// tEvtNext.add(Calendar.YEAR, 1);
				} else {
					System.out.println(
							"MpCalendarioEventBean.trataAtividade() - Error.Periodicidade!");
					break;
				}
				// ---------------
				// Trata Email ...
				// ---------------
				MailMessage message = mpMailer.novaMensagem();

				System.out.println(
						"MpSchedulerJobForm.trataAtividade() - Entrou 001 (IEmailSms = "
						+ mpAtividade.getMpAlerta().getIndEmail() + " / " 
						+ mpAtividade.getMpAlerta().getIndSMS()
						+ " / " + mpAtividade.getDescricao());
				//
				if (mpAtividade.getMpAlerta().getIndEmail()) {
					//
					String emailTo = "";
					if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
						emailTo = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																getMpPessoa().getEmail().trim();
					else
						emailTo = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getEmail().trim();
					//
					if (this.mpMensagemMovimento.getIndRespostaUsuario()) {
						String urlUsuarioResposta = "<a href=" + "\"" + this.scSistemaURL
							+ "MpAlertaLogMensagem.xhtml?idMM=" + this.mpMensagemMovimento.getId()
							+ "\" target=\"_blank\">Clique aqui para confirmar!</a>";
						//
						message.to(emailTo).subject(
								"MPXDS MpComunicator : " + mpMensagemMovimento.getId())
								.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
										"/emails/mpAtividadeResposta.template")))
								.put("mpAtividade", mpAtividade).put("urlUsuarioResposta",
																			urlUsuarioResposta)
								.put("locale", new Locale("pt", "BR")).send();
					} else
						message.to(emailTo).subject(
								"MPXDS MpComunicator : " + mpMensagemMovimento.getId())
								.bodyHtml(new VelocityTemplate(
								getClass().getResourceAsStream("/emails/mpAtividade.template")))
								.put("mpAtividade", mpAtividade).put("scSistemaURL",
																			this.scSistemaURL)
								.put("locale", new Locale("pt", "BR")).send();
				}
				// -------------
				// Trata SMS ...
				// -------------
				if (mpAtividade.getMpAlerta().getIndSMS()) {
					//
					try {
						String celular = "";
						if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
							celular = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
															getMpPessoa().getCelular().trim();
						else
							celular = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getCelular().trim();
						//
						String mensagem = mpAtividade.getDescricaoCompleta();
						//
						if (null == mpAtividade.getMpAlerta().getIndRespostaUsuario())
							mpAtividade.getMpAlerta().setIndRespostaUsuario(false);

						if (mpAtividade.getMpAlerta().getIndRespostaUsuario())
							mensagem = mensagem + " ( Responda ? : CONFIRMAR ADIAR CANCELAR )";
						//
						String codigoRetorno = MpUtilSMS.simple(celular, mensagem);
						// -----------------------------
						// Trata Mensagem Movimento...
						// -----------------------------
						this.mpMensagemMovimento = mpMensagemMovimentos.porTelefoneData(celular,
								mpAtividade.getDtHrAtividade());
						if (null == this.mpMensagemMovimento) {
							this.mpMensagemMovimento = new MpMensagemMovimento();
							//
							this.mpMensagemMovimento.setDataMovimento(mpAtividade.
																			getDtHrAtividade());
							if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario()) {
								this.mpMensagemMovimento.setUsuarioNome(this.mpSeguranca.
																			getMpUsuarioLogado()
										.getMpUsuarioTenant().getMpPessoa().getNome());
								this.mpMensagemMovimento.setContatoNome(this.mpSeguranca.
										getMpUsuarioLogado().getMpUsuarioTenant().getMpPessoa().
										getNome());
							} else {
								this.mpMensagemMovimento
										.setUsuarioNome(this.mpSeguranca.getMpUsuarioLogado().
																	getMpUsuario().getNome());
								this.mpMensagemMovimento
										.setContatoNome(this.mpSeguranca.getMpUsuarioLogado().
																	getMpUsuario().getNome());
							}
							this.mpMensagemMovimento.setAssunto(mpAtividade.getDescricao());
							this.mpMensagemMovimento.setMensagem(mpAtividade.
																		getDescricaoCompleta());
							this.mpMensagemMovimento.setMpTipoContato(MpTipoContato.SMS);
						}
						//
						this.mpMensagemMovimento.setCodigoRetorno(codigoRetorno);

						this.mpMensagemMovimento.setMpStatusMensagem(MpStatusMensagem.ENVIADA);
						//
					} catch (Exception e) {
						System.out.println(
								"Erro envio do SMS... (ID = " + mpMensagemMovimento.getId() 
																	+ " / Exception = " + e);
						//
						this.mpMensagemMovimento.setMpStatusMensagem(MpStatusMensagem.
																					ERRO_ENVIO);
					}
					//
					this.mpMensagemMovimento = mpMensagemMovimentos.guardar(
																		this.mpMensagemMovimento);
				}
			}
		}
	}

	private void trataCalendario(Date dataJob) {
		//
		System.out.println("MpSchedulerJobForm.trataCalendario() - Entrou 000");
		//
		List<MpCalendario> mpCalendarioList = mpCalendarios.mpCalendarioList();

		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		//
        for (MpCalendario mpCalendario : mpCalendarioList) {
        	//
    		MailMessage message = mpMailer.novaMensagem();
    		
    		if (null == mpUsuarioAnt
    		||	mpUsuarioAnt != mpCalendario.getMpAuditoriaObjeto().getUserInc()) {
    			mpUsuario = mpUsuarios.porLoginEmail(mpCalendario.getMpAuditoriaObjeto().
    																				getUserInc());
    			//
    			mpUsuarioAnt = mpCalendario.getMpAuditoriaObjeto().getUserInc();
    		}
    		//
			if (null == mpUsuario) {
				System.out.println("MpSchedulerJob.trataCalendario() ( Email NULL = " +
												mpCalendario.getMpAuditoriaObjeto().getUserInc());
				return;
			}
    		//
    		message.to(mpUsuario.getEmail().trim())
    			.subject("Calendario : " + mpCalendario.getId())
    			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
    															"/emails/mpCalendario.template")))
    			.put("mpCalendario", mpCalendario)
				.put("scSistemaURL", this.scSistemaURL)
    			.put("numberTool", new NumberTool())
    			.put("locale", new Locale("pt", "BR"))
    			.send();
        }
	}

	private void trataEstoqueReposicao(Date dataJob) {
		//
		System.out.println("MpSchedulerJobForm.trataEstoqueReposicao() - Entrou 000");

		// Calcula Data Final Validade...
		Calendar calendarValidade = Calendar.getInstance();
		
		calendarValidade.setTime(dataJob);
		calendarValidade.add(Calendar.DATE, scNumDiasEstoqueReposicao);
		
		Date dataFinalValidade = calendarValidade.getTime();
		// ----------------------------------------------
		
		List<MpProduto> mpProdutoList = mpProdutos.porProdutoList();
        //
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		
		List<MpMovimentoProduto> mpMovimentoProdutoListX = new ArrayList<MpMovimentoProduto>();
		
        for (MpProduto mpProduto : mpProdutoList) {
        	//    
        	List<MpMovimentoProduto> mpMovimentoProdutoList = mpProduto.getMpMovimentoProdutos();
        	
        	for (MpMovimentoProduto mpMovimentoProduto : mpMovimentoProdutoList) {
        		//        		
        		if (mpMovimentoProduto.getMpTipoMovimento().equals(MpTipoMovimento.ENTRADA)) {
        			
        			// Ignora Produto Estoque com validade maior perido expiração validade !
        			if (mpMovimentoProduto.getDataValidade().after(dataFinalValidade))
        				continue;
        			//	
        			mpMovimentoProdutoListX.add(mpMovimentoProduto);
        			
//        			System.out.println("MpSchedulerJobForm.trataEstoqueReposicao -" +
//        							" MpMovimentoProduto ( " + mpProduto.getNome() + " / " +
//        							mpMovimentoProduto.getDataValidade()  + " / " +
//        							sdf.format(dataFinalValidade));
        		}
        	}

    		MailMessage message = mpMailer.novaMensagem();
    		
    		if (null == mpUsuarioAnt
    		||	mpUsuarioAnt != mpProduto.getMpAuditoriaObjeto().getUserInc()) {
    			mpUsuario = mpUsuarios.porLoginEmail(mpProduto.getMpAuditoriaObjeto().
    																				getUserInc());
    			//
    			mpUsuarioAnt = mpProduto.getMpAuditoriaObjeto().getUserInc();
    		}
    		//
			if (null == mpUsuario) {
				System.out.println("MpSchedulerJob.trataEstoqueReposicao() ( Email NULL = " +
												mpProduto.getMpAuditoriaObjeto().getUserInc());
				return;
			}
    		//
    		message.to(mpUsuario.getEmail().trim())
    			.subject("Produto : " + mpProduto.getId())
    			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
    														"/emails/mpEstoqueReposicao.template")))
    			.put("mpProduto", mpProduto)
    			.put("mpMovimentoProdutoListX", mpMovimentoProdutoListX)
				.put("scSistemaURL", this.scSistemaURL)
    			.put("numberTool", new NumberTool())
    			.put("locale", new Locale("pt", "BR"))
    			.send();
        }
	}

	private Boolean trataMpAlertaLog(Object object, Date dataJob, String tipoAlerta) {
		//
		Boolean indAlertaLog = false;
		Long alertaId = 0L;
		//
		if (tipoAlerta.equals("ALARME")) {
			MpAlarme mpAlarme = (MpAlarme) object;
			alertaId = mpAlarme.getId();
		} else
		if (tipoAlerta.equals("ATIVIDADE")) {
			MpAtividade mpAtividade = (MpAtividade) object;
			alertaId = mpAtividade.getId();
		} else
		if (tipoAlerta.equals("CALENDARIO")) {
			MpCalendario mpCalendario = (MpCalendario) object;
			alertaId = mpCalendario.getId();
		} else
		if (tipoAlerta.equals("ESTOQUE_REPOSICAO")) {
			MpMovimentoProduto mpMovimentoProduto = (MpMovimentoProduto) object;
			alertaId = mpMovimentoProduto.getId();
		}
		//
		MpAlertaLog mpAlertaLog = mpAlertaLogs.porDataMovTipoId(dataJob, tipoAlerta, alertaId);
		if (null == mpAlertaLog) {
			//
			mpAlertaLog = new MpAlertaLog();
			
			mpAlertaLog.setDataMovimento(dataJob);
			mpAlertaLog.setTipoAlerta(tipoAlerta);
			mpAlertaLog.setNumeroAlerta(1);
			
			if (null == alertaId)
				mpAlertaLog.setAlertaId(0L);
			else
				mpAlertaLog.setAlertaId(alertaId);
			
			if (null == mpAlarme.getHoraMovimento())
				mpAlertaLog.setDataAlerta(new Date());
			else
				mpAlertaLog.setDataAlerta(mpAlarme.getHoraMovimento());
			//
			mpAlertaLogs.guardar(mpAlertaLog);
			//		
		} else {
			//
        	// Tratar Status Alarme ...
        	if (mpAlertaLog.getMpAlertaStatus().equals(MpAlertaStatus.LIDO)
        	||	mpAlertaLog.getMpAlertaStatus().equals(MpAlertaStatus.CANCELADO))
				return true;

        	if (mpAlertaLog.getMpAlertaStatus().equals(MpAlertaStatus.A05M)) // Adiar.05minutos !
        		mpAlertaLog.setTempoAdiantamento(300); // 05minutos !
        	else
            if (mpAlertaLog.getMpAlertaStatus().equals(MpAlertaStatus.A15M)) // Adiar.15minutos !
           		mpAlertaLog.setTempoAdiantamento(900); // 15minutos !
        	else {	
        		// Verifica número de alertas enviados...
        		// --------------------------------------
        		if (mpAlertaLog.getNumeroAlerta() == this.scNumeroAlertaAlarme)
        			return true;
        		else
        			mpAlertaLog.setNumeroAlerta(mpAlertaLog.getNumeroAlerta() + 1);
        	}
        	//
			mpAlertaLogs.guardar(mpAlertaLog);
		}
		//
		return indAlertaLog;
	}
	
	// ------

	public MpAlarme getMpAlarme() { return mpAlarme; }
	public void setMpAlarme(MpAlarme mpAlarme) { this.mpAlarme = mpAlarme; }
	
}