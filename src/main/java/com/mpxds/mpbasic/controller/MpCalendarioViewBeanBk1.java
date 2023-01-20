package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.model.MpPessoa;
import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.model.enums.MpFeriadoOficial;
import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.repository.MpAtividades;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.repository.MpPessoas;
import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;
import com.mpxds.mpbasic.repository.filter.MpCalendarioViewFilter;
import com.mpxds.mpbasic.util.MpAppUtil;

@Named
@ViewScoped
public class MpCalendarioViewBeanBk1 implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlarmes mpAlarmes;
	@Inject
	private MpAtividades mpAtividades;
	@Inject
	private MpCalendarios mpCalendarios;
	@Inject
	private MpPessoas mpPessoas;

	private MpCalendarioViewFilter mpFiltro;

	private MpCalendario mpCalendario;
	
	private ScheduleModel eventModel;
	private ScheduleModel eventModelX;

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event;
		   
	private Date dataAux = new Date();
	private String textoFiltro;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	//---
	
	public MpCalendarioViewBeanBk1() {
		//
		System.out.println("MpCalendarioViewBean() - Entrou.000 ( " + this.textoFiltro);
		
		if (null == this.mpFiltro) {
			this.mpFiltro = new MpCalendarioViewFilter();
			//
			System.out.println("MpCalendarioViewBean() - Entrou.001");
		}
	}
	
	public void inicializar() {
		//
		System.out.println("MpCalendarioViewBean.inicializar() - Entrou.000 ( " + this.textoFiltro);

		if (null == this.mpFiltro) {
			this.mpFiltro = new MpCalendarioViewFilter();
			//
			System.out.println("MpCalendarioViewBean.inicializar() - Entrou.001( " + this.textoFiltro);
		}
	}

	public void filtrar() {
		//
		System.out.println("MpCalendarioViewBean.filtrar() - Entrou.000 ( " + this.textoFiltro);
		// Trata null values ...
		String dataDe = ""; 
		if (null == this.mpFiltro.getDataCriacaoDe()) 
			dataDe = "";
		else
			dataDe = sdf.format(this.mpFiltro.getDataCriacaoDe());
		
		String dataAte = ""; 
		if (null == this.mpFiltro.getDataCriacaoAte()) 
			dataAte = "";
		else
			dataAte = sdf.format(this.mpFiltro.getDataCriacaoAte());
		//
		this.textoFiltro = "Dat.De : " + dataDe + 
							" / Dat.Até : " + dataAte + 
							" / Descr. : " + this.mpFiltro.getDescricao() +
							" / Alarme : " + this.mpFiltro.getIndShowAlarme() +
							" / Ativ. : " + this.mpFiltro.getIndShowAtividade() +
							" / Turno : " + this.mpFiltro.getIndShowTurno() +
							" / Calend. : " + this.mpFiltro.getIndShowCalendario();
		//
		System.out.println("MpCalendarioViewBean.filtrar() - Entrou.001 ( " + this.textoFiltro);
		//
		this.eventModel = new DefaultScheduleModel();
		this.eventModelX = new DefaultScheduleModel();
		
		if (this.mpFiltro.getIndShowAlarme())
			this.trataAlarme();
		
		if (this.mpFiltro.getIndShowCalendario())
			this.trataCalendario();
		
		if (this.mpFiltro.getIndShowAtividade())
			this.trataAtividade();
		
		if (this.mpFiltro.getIndShowTurno())
			this.trataTurno();
	}

	@SuppressWarnings("deprecation")
	public void trataAlarme() {
		// ================ //
		// Trata Alarme ... //
		// ================ //
		System.out.println("MpCalendarioViewBean.trataAlarme() - Entrou.000 ");
		//
		MpAlarmeFilter mpAlarmeFilter = MpAppUtil.capturaAlarme(new Date());
		
		mpAlarmeFilter.setIndAtivo(true);
		//
		List<MpAlarme> mpAlarmeList = mpAlarmes.filtrados(mpAlarmeFilter);

        for (MpAlarme mpAlarme : mpAlarmeList) {
        	//
        	if (null == mpAlarme.getNome()) mpAlarme.setNome(".");
    		//
    		// Filtra...
    		//
    		if (null == this.mpFiltro.getDescricao()
    		||	this.mpFiltro.getDescricao().isEmpty())
    			assert(true);
    		else
    			if (mpAlarme.getNome().trim().toUpperCase().contains(
    									this.mpFiltro.getDescricao().trim().toUpperCase()))
        			assert(true);
        		else
        			continue;
    		//
    		Calendar t = Calendar.getInstance();
    		//
    		t.setTime(this.dataAux);
//    		t.add(Calendar.DATE, -1);
//    		t.set(Calendar.AM_PM, Calendar.PM);
    		t.set(Calendar.HOUR, mpAlarme.getHoraMovimento().getHours());
    		t.set(Calendar.MINUTE, mpAlarme.getHoraMovimento().getMinutes());
    		//
    		this.eventModel.addEvent(new DefaultScheduleEvent("Alarme: " + mpAlarme.getNome(),
    																	t.getTime(), t.getTime()));        	
    		this.eventModelX.addEvent(new DefaultScheduleEvent("Alarme: " + mpAlarme.getNome(),
																		t.getTime(), t.getTime()));        	

    		System.out.println("MpCalendarioViewBean.trataAlarme() - ALARME ( " + mpAlarme.getNome());
//    				" / size = " + mpAlarmeList.size() + " / iDom = " + mpAlarmeFilter.getIndDomingo() +
//    				" / iSeg = " + mpAlarmeFilter.getIndSegunda() +
//    				" / hora = " + mpAlarme.getHoraMovimento() + " / " + t.getTime());
        }
		// ============================ //
	}
	
	public void trataAtividade() {
        // ==================== //
		// Trata Atividades ... //
		// ==================== //
		System.out.println("MpCalendarioViewBean.trataAtividade() - Entrou.000 ");
		//
		List<MpAtividade> mpAtividadeList = mpAtividades.mpAtividadeList();

        for (MpAtividade mpAtividade : mpAtividadeList) {
    		//
    		// Filtra...
    		//
    		if (null == this.mpFiltro.getDescricao()
    		||	this.mpFiltro.getDescricao().isEmpty())
    			assert(true);
    		else
    			if (mpAtividade.getDescricao().trim().toUpperCase().contains(
    									this.mpFiltro.getDescricao().trim().toUpperCase()))
        			assert(true);
        		else
        			continue;
    		//
    		this.eventModel.addEvent(new DefaultScheduleEvent("Atividade: " + mpAtividade.getDescricao(),
    						mpAtividade.getDtHrAtividade(), mpAtividade.getDtHrAtividade()));        	

    		System.out.println("MpCalendarioViewBean.trataAtividade() - ATIVIDADE ( size = " +
    					mpAtividadeList.size() + " / dtHr = " + mpAtividade.getDtHrAtividade() +
    					" / Descr.= " + mpAtividade.getDescricao()) ;
        }
        // ============================ //
	}

	public void trataCalendarioFeriado() {
        // =============================== //
		// Trata Calendário/Feriados ...   //
		// =============================== //
		System.out.println("MpCalendarioViewBean.trataCalendarioFeriado() - Entrou.000 ");
		//
        List<MpFeriadoOficial> mpFeriadoOficialList = Arrays.asList(MpFeriadoOficial.values());

        for (MpFeriadoOficial mpFeriadoOficial : mpFeriadoOficialList) {
    		//
    		// Filtra...
    		//
    		if (null == this.mpFiltro.getDescricao()
    		||	this.mpFiltro.getDescricao().isEmpty())
    			assert(true);
    		else
    			if (mpFeriadoOficial.getDescricao().trim().toUpperCase().contains(
    									this.mpFiltro.getDescricao().trim().toUpperCase()))
        			assert(true);
        		else
        			continue;
    		//
    		Calendar t = Calendar.getInstance();
    		//
    		Integer diaAux = Integer.parseInt(mpFeriadoOficial.getDataEvento().substring(0, 2));
    		Integer mesAux = Integer.parseInt(mpFeriadoOficial.getDataEvento().substring(3, 5));
    		//
    		t.setTime(this.dataAux);
    		t.set(Calendar.DAY_OF_MONTH, diaAux);
    		t.set(Calendar.MONTH, mesAux);
    		//
    		this.dataAux = t.getTime();
        	
    		this.eventModel.addEvent(new DefaultScheduleEvent("Feriado: " +
    								mpFeriadoOficial.getDescricao(), this.dataAux, this.dataAux));
    		this.eventModelX.addEvent(new DefaultScheduleEvent("Feriado: " +
									mpFeriadoOficial.getDescricao(), this.dataAux, this.dataAux));

    		// Carrega no ano seguinte ...
    		t.add(Calendar.YEAR, 1);
    		this.dataAux = t.getTime();
    		this.eventModel.addEvent(new DefaultScheduleEvent("Feriado: " +
									mpFeriadoOficial.getDescricao(), this.dataAux, this.dataAux));  
    		// Carrega no ano anterior ...
    		t.add(Calendar.YEAR, -2);
    		this.dataAux = t.getTime();
    		this.eventModel.addEvent(new DefaultScheduleEvent("Feriado: " +
									mpFeriadoOficial.getDescricao(), this.dataAux, this.dataAux));  

    		System.out.println("MpCalendarioViewBean.trataCalendarioFeriado() - Feriado ( size = " +
    								mpFeriadoOficialList.size() + " / dtHr = " + this.dataAux + 
    								" / Descr.= " + mpFeriadoOficial.getDescricao()) ;
        }
        
        // ----------------------------------------
	}
	
	public void trataCalendario() {
        // ====================== //
		// Trata Calendário ...   //
		// ====================== //
		System.out.println("MpCalendarioViewBean.trataCalendario() - Entrou.000 ");
		//    	
		List<MpCalendario> mpCalendarioList = mpCalendarios.mpCalendarioList();

        for (MpCalendario mpCalendario : mpCalendarioList) {
    		//
    		// Filtra...
    		//
    		if (null == this.mpFiltro.getDescricao()
    		||	this.mpFiltro.getDescricao().isEmpty())
    			assert(true);
    		else
    			if (mpCalendario.getDescricao().trim().toUpperCase().contains(
    									this.mpFiltro.getDescricao().trim().toUpperCase()))
        			assert(true);
        		else
        			continue;
    		//
    		this.eventModel.addEvent(new DefaultScheduleEvent("Calendário: " +
    						mpCalendario.getDescricao(), mpCalendario.getDataMovimento(),
    						mpCalendario.getDataMovimento()));        	

    		System.out.println("MpCalendarioViewBean.trataCalendario() - Calendário ( size = " +
    			mpCalendarioList.size() + " / dtHr = " + mpCalendario.getDataMovimento() + 
    			" / Descr.= " + mpCalendario.getDescricao()) ;
        }
	}
	
	@SuppressWarnings("deprecation")
	public void trataTurno() {
        // ====================== //
		// Trata Pessoa x Turno   //
		// ====================== //
		System.out.println("MpCalendarioViewBean.trataTurno() - Entrou.000 ");
		//    	    	
		List<MpPessoa> mpPessoaList = mpPessoas.porPessoaAtivoList();

        for (MpPessoa mpPessoa : mpPessoaList) {
        	//
        	if (null == mpPessoa.getMpTurno()
        	|| 	null == mpPessoa.getDataInicio())
        		continue;
    		//
    		// Filtra...
    		//
    		if (null == this.mpFiltro.getDescricao()
    		||	this.mpFiltro.getDescricao().isEmpty())
    			assert(true);
    		else
    			if (mpPessoa.getNome().trim().toUpperCase().contains(
    									this.mpFiltro.getDescricao().trim().toUpperCase()))
        			assert(true);
        		else
        			continue;
    		//
        	//
    		// Trata Turno ...
    		//
    		MpTurno mpTurno = mpPessoa.getMpTurno();

    		Calendar tIni = Calendar.getInstance();
    		Calendar tFim = Calendar.getInstance();
    		Calendar tEvt = Calendar.getInstance();
    		Calendar tEvtNext = Calendar.getInstance();
    		//
    		tIni.setTime(mpPessoa.getDataInicio());
    		tIni.set(Calendar.HOUR, mpTurno.getHoraEntrada().getHours());
    		tIni.set(Calendar.MINUTE, mpTurno.getHoraEntrada().getMinutes());
    		tIni.set(Calendar.SECOND, 0);
    		
    		tEvt.setTime(tIni.getTime());
    		tEvtNext.setTime(tIni.getTime());
    		
    		tFim.setTime(tIni.getTime());
    		tFim.add(Calendar.MONDAY, 6);
    		//
    		System.out.println("MpCalendarioViewBean.trataTurno() - Turno ( size = " +
    										mpPessoaList.size() + " / dtHr = " + tEvt.getTime() + 
    										" / Nome= " + mpPessoa.getNome()) ;
    		//
    		String diaSemana = "";
    		Integer numeroHorasFolga = 0;
    		
    		for (Date dateX = tIni.getTime(); tIni.before(tFim); tIni.add(Calendar.DATE, 1),
    																	dateX = tIni.getTime()) {
    			diaSemana = MpAppUtil.diaSemana(dateX).toUpperCase();
    			//
    			if (null == mpTurno.getHoraSegunda()) mpTurno.setHoraSegunda(0);
    			if (null == mpTurno.getHoraTerca()) mpTurno.setHoraTerca(0);
    			if (null == mpTurno.getHoraQuarta()) mpTurno.setHoraQuarta(0);
    			if (null == mpTurno.getHoraQuinta()) mpTurno.setHoraQuinta(0);
    			if (null == mpTurno.getHoraSexta()) mpTurno.setHoraSexta(0);
    			if (null == mpTurno.getHoraSabado()) mpTurno.setHoraSabado(0);
    			if (null == mpTurno.getHoraDomingo()) mpTurno.setHoraDomingo(0);
    			//
    			numeroHorasFolga = 0;
    			if (diaSemana.equals("DOMINGO") && mpTurno.getHoraDomingo() > 0)
    				numeroHorasFolga = mpTurno.getHoraDomingo();
    			else
    			if (diaSemana.equals("SEGUNDA-FEIRA") && mpTurno.getHoraSegunda() > 0)
    				numeroHorasFolga = mpTurno.getHoraSegunda();
    			else 
    			if (diaSemana.equals("TERÇA-FEIRA") && mpTurno.getHoraTerca() > 0)
    				numeroHorasFolga = mpTurno.getHoraTerca();
    			else 
    			if (diaSemana.equals("QUARTA-FEIRA") && mpTurno.getHoraQuarta() > 0)
    				numeroHorasFolga = mpTurno.getHoraQuarta();
    			else
    			if (diaSemana.equals("QUINTA-FEIRA") && mpTurno.getHoraQuinta() > 0)
    				numeroHorasFolga = mpTurno.getHoraQuinta();
    			else
    			if (diaSemana.equals("SEXTA-FEIRA") && mpTurno.getHoraSexta() > 0)
    				numeroHorasFolga = mpTurno.getHoraSexta();
    			else
    			if (diaSemana.equals("SÁBADO") && mpTurno.getHoraSabado() > 0)
    				numeroHorasFolga = mpTurno.getHoraSabado();
	    		
//	    		System.out.println("MpCalendarioViewBean.init() - Turno ( tEvt = " +
//	    						tEvt.getTime() + " / tEvtNext = " + tEvtNext.getTime());
    			//
				tEvt.setTime(dateX);
    			if (tEvt.equals(tEvtNext)) {
    				//
    				if (numeroHorasFolga == 24) // Ignora
    					assert(true);
    				else {
    					this.eventModel.addEvent(new DefaultScheduleEvent("Turno: " +
    										mpPessoa.getNome(), tEvt.getTime(), tEvt.getTime()));
    					
//    		    		System.out.println("MpCalendarioViewBean.trataTurno() - Turno ( tEvt = " +
//    		    							tEvt.getTime() + " / tEvtNext = " + tEvtNext.getTime());
    				}
    	    		//
    	    		tEvtNext.add(Calendar.HOUR, mpTurno.getMpTipoJornada().getNumeroHorasFolga());
    			}
    		}    		
        }
        // ============================ //
	}
		
	// ---

	public MpCalendario getMpCalendario() { return mpCalendario; }
	public void setMpCalendario(MpCalendario mpCalendario) { this.mpCalendario = mpCalendario; }
	
	public String getTextoFiltro() { return textoFiltro; }
	public void setTextoFiltro(String textoFiltro) { this.textoFiltro = textoFiltro; }

	public ScheduleEvent getEvent() { return event; }
	public void setEvent(ScheduleEvent event) { this.event = event; }
	
	public MpCalendarioViewFilter getMpFiltro() { return mpFiltro; }
	public void setMpFiltro(MpCalendarioViewFilter mpFiltro) { this.mpFiltro = mpFiltro; }
	
	public ScheduleModel getEventModel() { return eventModel; }
	public void setEventModel(ScheduleModel eventModel) { this.eventModel = eventModel; }
	
	public ScheduleModel getEventModelX() { return eventModelX; }
	public void setEventModelX(ScheduleModel eventModelX) { this.eventModelX = eventModelX; }

	public ScheduleModel getLazyEventModel() { return lazyEventModel; }
	
}