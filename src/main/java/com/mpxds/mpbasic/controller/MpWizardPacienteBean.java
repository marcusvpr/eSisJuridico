package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.model.enums.MpCor;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
 
@ManagedBean
@ViewScoped
public class MpWizardPacienteBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private MpPaciente mpPaciente = new MpPaciente();
     
    private boolean skip;
    	
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList;
	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList;
	
	private MpCor mpCor;
	private List<MpCor> mpCorList = new ArrayList<MpCor>();
     
    // ---
    
    public MpPaciente getMpPaciente() { return mpPaciente;  }
    public void setMpPaciente(MpPaciente mpPaciente) { this.mpPaciente = mpPaciente; }
    
	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }

	public MpCor getMpCor() { return mpCor; }
	public void setMpCor(MpCor mpCor) {	this.mpCor = mpCor; }
	public List<MpCor> getMpCorList() {	return mpCorList; }

	public void inicializar() {
		//
		if (null == this.mpPaciente) 
			this.mpPaciente = new MpPaciente();
		//
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpSexoList = Arrays.asList(MpSexo.values());
		this.mpCorList = Arrays.asList(MpCor.values());
	}
	
    public void save() {
    	//
        FacesMessage msg = new FacesMessage("Sucesso", "Bem vindo :" + mpPaciente.getNome());
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public boolean isSkip() { return skip; }
 
    public void setSkip(boolean skip) { this.skip = skip; }
     
    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case mpPaciente goes back
            return "confirm";
        }
        else
            return event.getNewStep();
        //
    }
    
}