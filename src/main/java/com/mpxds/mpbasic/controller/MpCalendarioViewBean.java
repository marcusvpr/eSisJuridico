package com.mpxds.mpbasic.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import java.util.Date;

import org.primefaces.event.SelectEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.mpxds.mpbasic.repository.filter.MpCalendarioViewFilter;

@ManagedBean
@ViewScoped
public class MpCalendarioViewBean {
	//
	@Inject
	private MpCalendarioEventBean mpCalendarioEventBean;
	
	private MpCalendarioViewFilter mpFiltro = new MpCalendarioViewFilter();	
	
	private ScheduleModel eventModel;

	private ScheduleEvent event;
	
	private Boolean indVisivel = false;
	
	// ------------------
					
	public MpCalendarioViewBean() {
		//
//		System.out.println("MpCalendarioViewBean() - Entrou.000"); 

		this.mpFiltro = new MpCalendarioViewFilter();
		this.mpFiltro.setNumDiaExpande(45);

		this.eventModel = new DefaultScheduleModel();
		this.event = new DefaultScheduleEvent();
		
		this.indVisivel = false;	
	}
	
	public void filtrar() {
		//
		this.indVisivel = true;
		
		this.mpCalendarioEventBean = new MpCalendarioEventBean() ;
		this.mpCalendarioEventBean.setMpFiltro(this.mpFiltro);
		this.mpCalendarioEventBean.filtrar();

		this.eventModel = this.mpCalendarioEventBean.getEventModel();
		//
		RequestContext requestContext = RequestContext.getCurrentInstance();
        
        requestContext.update("frmCalend:scheduleId");
		
//		System.out.println("MpCalendarioEventBean.filtrar() - Entrou.001 (FiltroDescricao =  " + 
//							this.mpFiltro.getDescricao());
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
															(Date) selectEvent.getObject());
	}
	
	
	// ---

	public ScheduleModel getEventModel() { return eventModel; }
	public void setEventModel(ScheduleModel eventModel) { this.eventModel = eventModel; }

	public ScheduleEvent getEvent() { return event; }
	public void setEvent(ScheduleEvent event) { this.event = event; }

	public MpCalendarioViewFilter getMpFiltro() { return mpFiltro; }
	public void setMpFiltro(MpCalendarioViewFilter mpFiltro) { this.mpFiltro = mpFiltro; }
	
	public Boolean getIndVisivel() { return indVisivel; }
	public void setIndVisivel(Boolean indVisivel) { this.indVisivel = indVisivel; }
	
}