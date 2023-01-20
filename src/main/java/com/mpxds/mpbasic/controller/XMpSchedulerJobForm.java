package com.mpxds.mpbasic.controller; 

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.model.MpMovimentoProduto;
import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.enums.MpAlertaStatus;
import com.mpxds.mpbasic.model.enums.MpTipoMovimento;
import com.mpxds.mpbasic.model.MpSistemaConfig;

import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.repository.MpAlertaLogs;
import com.mpxds.mpbasic.repository.MpAtividades;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;

import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;

import com.mpxds.mpbasic.util.MpAppUtil;
import com.mpxds.mpbasic.util.mail.MpMailer;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@ViewScoped
public class XMpSchedulerJobForm implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlarmes mpAlarmes;	
	@Inject
	private MpAtividades mpAtividades;
	@Inject
	private MpCalendarios mpCalendarios;
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

	private MpAlarme mpAlarme = new MpAlarme();	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//	private SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
	
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
	
	// ----------
	
	public XMpSchedulerJobForm() {
		System.out.println("MpSchedulerJobForm() - Entrou 0000 ");
		//
	}
	
	public void inicializar() {
		//
		System.out.println("MpSchedulerJobForm.inicializar() - Entrou 0000 ");

		Date dataHoraJob = new Date();
		
		// Trata captura Configuração Sistema ...
		// ======================================

		MpSistemaConfig mpSistemaConfig = mpSistemaConfigs.porParametro("sistemaURL");
		if (null == mpSistemaConfig)
			this.scSistemaURL = "localhost:8080"; // Default = localhost ...
		else
			this.scSistemaURL = mpSistemaConfig.getValor();

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaAlarme");
		if (null == mpSistemaConfig)
			this.scIndAlertaAlarme = false;
		else
			this.scIndAlertaAlarme = Boolean.parseBoolean(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaAtividade");
		if (null == mpSistemaConfig)
			this.scIndAlertaAtividade = false;
		else
			this.scIndAlertaAtividade = Boolean.parseBoolean(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaCalendario");
		if (null == mpSistemaConfig)
			this.scIndAlertaCalendario = false;
		else
			this.scIndAlertaCalendario = Boolean.parseBoolean(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("indAlertaEstoqueReposicao");
		if (null == mpSistemaConfig)
			this.scIndAlertaEstoqueReposicao = false;
		else
			this.scIndAlertaEstoqueReposicao = Boolean.parseBoolean(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("numeroAlertaAlarme");
		if (null == mpSistemaConfig)
			this.scNumeroAlertaAlarme = 3; // Default = 3 vezes ...
		else
			this.scNumeroAlertaAlarme = Integer.parseInt(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("tempoAlertaAtividade");
		if (null == mpSistemaConfig)
			this.scAlertaTempo = 60; // Default = 60 minutos ...
		else
			this.scAlertaTempo = Integer.parseInt(mpSistemaConfig.getValor());

		mpSistemaConfig = mpSistemaConfigs.porParametro("numeroDiasEstoqueReposicao");
		if (null == mpSistemaConfig)
			this.scNumDiasEstoqueReposicao = 60; // Default = 60 dias ...
		else
			this.scNumDiasEstoqueReposicao = Integer.parseInt(mpSistemaConfig.getValor());

		// ==========
		if (this.scIndAlertaAlarme)
			this.trataAlarme(dataHoraJob);
		if (this.scIndAlertaAtividade)
			this.trataAtividade(dataHoraJob);
		if (this.scIndAlertaCalendario)
			this.trataCalendario(dataHoraJob);
		if (this.scIndAlertaEstoqueReposicao)
			this.trataEstoqueReposicao(dataHoraJob);
		// ==========
		System.out.println("MpSchedulerJobForm.inicializar() = Calendário/Alarme/Atividade - " +
																		sdf.format(dataHoraJob));
	}
		
	private void trataAlarme(Date dataJob) {
		//
		MpAlarmeFilter mpAlarmeFilter = MpAppUtil.capturaAlarme(dataJob);
		
		mpAlarmeFilter.setIndAtivo(true);
		//
		List<MpAlarme> mpAlarmeList = mpAlarmes.filtrados(mpAlarmeFilter);

		Iterator<MpAlarme> itrAlarme = mpAlarmeList.iterator(); 
		//
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		//
		System.out.println("MpSchedulerJobForm.trataAlarme() - Entrou 0000 ( Inds = "  +
				mpAlarmeFilter.getIndDomingo() + "/" + mpAlarmeFilter.getIndSegunda() + "/" +
				mpAlarmeFilter.getIndTerca() + "/" + mpAlarmeFilter.getIndQuarta() + "/" +
				mpAlarmeFilter.getIndQuinta() + "/" + mpAlarmeFilter.getIndSexta() + "/" +
				mpAlarmeFilter.getIndSabado() + "/ DataJob = " +
							sdf.format(dataJob) + " / AlarmeList.size = " + mpAlarmeList.size());
		//
		Calendar calendarI = Calendar.getInstance();
		Calendar calendarF = Calendar.getInstance();
		//
        while(itrAlarme.hasNext()) {
        	//
        	this.mpAlarme = (MpAlarme) itrAlarme.next();
        	
        	if (null == this.mpAlarme.getIndSemanalmente())
        		this.mpAlarme.setIndSemanalmente(false);
        	
        	// Tratar intervalo(-5min / +5Min) para selecionar o Alarme x DataHora.Job ...
			calendarI.setTime(this.mpAlarme.getHoraMovimento());
			calendarI.add(Calendar.MINUTE, -5);
			calendarF.setTime(this.mpAlarme.getHoraMovimento());
			calendarF.add(Calendar.MINUTE, 5);
        	
			Date dataI = calendarI.getTime();
			Date dataF = calendarF.getTime();
			
//        	if (!this.mpAlarme.getIndSemanalmente()) {
        		if (this.mpAlarme.getHoraMovimento().after(dataI)
        		&&  this.mpAlarme.getHoraMovimento().before(dataF))
        			assert true; // Idem IGNORE ou NOP !
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
		List<MpAtividade> mpAtividadeList = mpAtividades.mpAtividadeList();

		Iterator<MpAtividade> itrAtividade = mpAtividadeList.iterator(); 
        //
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		
        while(itrAtividade.hasNext()) {
        	//
        	MpAtividade mpAtividade = (MpAtividade) itrAtividade.next();
        	
        	//
    		MailMessage message = mpMailer.novaMensagem();
    		
    		if (null == mpUsuarioAnt
    		||	mpUsuarioAnt != mpAtividade.getMpAuditoriaObjeto().getUserInc()) {
    			mpUsuario = mpUsuarios.porLoginEmail(mpAtividade.getMpAuditoriaObjeto().getUserInc());
    			//
    			mpUsuarioAnt = mpAtividade.getMpAuditoriaObjeto().getUserInc();
    		}
    		//
			if (null == mpUsuario) {
				System.out.println("MpSchedulerJob.trataAtividade() ( Email NULL = " +
													mpAtividade.getMpAuditoriaObjeto().getUserInc());
				return;
			}
    		//
    		message.to(mpUsuario.getEmail().trim())
    			.subject("Atividade : " + mpAtividade.getId())
    			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream(
    																"/emails/mpAtividade.template")))
    			.put("mpAtividade", mpAtividade)
				.put("scSistemaURL", this.scSistemaURL)
    			.put("numberTool", new NumberTool())
    			.put("locale", new Locale("pt", "BR"))
    			.send();
        }
	}

	private void trataCalendario(Date dataJob) {
		//
		List<MpCalendario> mpCalendarioList = mpCalendarios.mpCalendarioList();

		Iterator<MpCalendario> itrCalendario = mpCalendarioList.iterator(); 
        //
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		
        while(itrCalendario.hasNext()) {
        	//
        	MpCalendario mpCalendario = (MpCalendario) itrCalendario.next();
        	
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
		// Calcula Data Final Validade...
		Calendar calendarValidade = Calendar.getInstance();
		
		calendarValidade.setTime(dataJob);
		calendarValidade.add(Calendar.DATE, scNumDiasEstoqueReposicao);
		
		Date dataFinalValidade = calendarValidade.getTime();
		// ----------------------------------------------
		
		List<MpProduto> mpProdutoList = mpProdutos.porProdutoList();

		Iterator<MpProduto> itrMpProduto = mpProdutoList.iterator(); 
        //
		MpUsuario mpUsuario = new MpUsuario();
		String mpUsuarioAnt = null;
		
		List<MpMovimentoProduto> mpMovimentoProdutoListX = new ArrayList<MpMovimentoProduto>();
		
        while(itrMpProduto.hasNext()) {
        	//
        	MpProduto mpProduto = (MpProduto) itrMpProduto.next();
    
        	List<MpMovimentoProduto> mpMovimentoProdutoList = mpProduto.getMpMovimentoProdutos();
        	
        	Iterator<MpMovimentoProduto> itrMpMovimentoProduto = mpMovimentoProdutoList.iterator();

        	while(itrMpMovimentoProduto.hasNext()) {
        		//
        		MpMovimentoProduto mpMovimentoProduto = (MpMovimentoProduto) 
        																itrMpMovimentoProduto.next();
        		
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