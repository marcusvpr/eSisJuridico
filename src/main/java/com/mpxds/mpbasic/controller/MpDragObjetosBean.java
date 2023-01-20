package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DragDropEvent;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.security.MpSeguranca;
 
@ManagedBean(name = "dndMpObjetosView")
@ViewScoped
public class MpDragObjetosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
  
    @ManagedProperty("#{mpCadastroObjetos}")
    private MpObjetos service;
    
    private MpSeguranca mpSeguranca;
    
    private List<MpObjeto> mpObjetos;
     
    private List<MpObjeto> droppedMpObjetos;
     
    private MpObjeto selectedMpObjeto;
    
    // ---
     
    @PostConstruct
    public void init() {
        mpObjetos = service.mpObjetoList(mpSeguranca.capturaTenantId().trim());
		//
		System.out.println("MpDragObjetosBean.init() - Entrou 000 (" + mpObjetos);
        
        droppedMpObjetos = new ArrayList<MpObjeto>();
    }
     
    public void onMpObjetoDrop(DragDropEvent ddEvent) {
        MpObjeto mpObjeto = ((MpObjeto) ddEvent.getData());
  
        droppedMpObjetos.add(mpObjeto);
        mpObjetos.remove(mpObjeto);
    }
     
    public void setService(MpObjetos service) { this.service = service; }
 
    public List<MpObjeto> getMpObjetos() { return mpObjetos; }
 
    public List<MpObjeto> getDroppedMpObjetos() { return droppedMpObjetos; }    
 
    public MpObjeto getSelectedMpObjeto() { return selectedMpObjeto; }
 
    public void setSelectedMpObjeto(MpObjeto selectedMpObjeto) {
        											this.selectedMpObjeto = selectedMpObjeto; }
    
}