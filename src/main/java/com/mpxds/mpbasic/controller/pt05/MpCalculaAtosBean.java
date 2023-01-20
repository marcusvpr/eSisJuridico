package com.mpxds.mpbasic.controller.pt05;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.pt05.MpAtos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt05.MpAtoService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCalculaAtosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpAtos mpAtos;

	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpAtoService mpAtoService;

	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---
	private Integer scOficVariavel = 2;
	private Integer scOficLei3217 = 20;
	private Integer scOficLei4664 = 5;
	private Integer scOficLei111 = 5;
	private Integer scOficLei6281 = 4;
	//		
	private MpAto mpAto;
	
	private MpObjeto mpObjetoHelp;

	private Integer progresso = 0;
	
	// ---
		
	public void calcular() {
		//
		// Trata Configuração Sistema ...
		// ===============================
		String msg = "";
		
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficVariavel");
		if (null == mpSistemaConfig)
			msg = msg + "<oficVariavel>";
		else
			this.scOficVariavel = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei3217");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei3217>";
		else
			this.scOficLei3217 = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei111");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei111>";
		else
			this.scOficLei111 = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei4664");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei4664>";
		else
			this.scOficLei4664 = mpSistemaConfig.getValorN();
		
		if (!msg.isEmpty())
			MpFacesUtil.addInfoMessage("Alerta Sistema Configuração : ( " + msg
															+ " não encontrados!");
		//
		List<MpAto> mpAtoList = mpAtos.byCodigoList();

		if (mpAtoList.isEmpty()) {
			MpFacesUtil.addInfoMessage("Não foram encontrados... ATOS para calcular !");
			return;
		}
		//
		this.progresso = 0;
		
		Integer contador = 0;

		Double contadorProgresso = (double) (100 / mpAtoList.size());
		Double totalProgresso = 0.0;
		//
		for (MpAto mpAto : mpAtoList) {
			//
			contador++;
			//
			this.mpAto = mpAto;
			
//			MpFacesUtil.addInfoMessage("Tratando : (Contador = ( " + contador + " ) / MpAto = " +
//																	this.mpAto.getCodigoSequencia());
			//
			this.mpAto.getMpValorAto().zerarValorTotal();
			//
			this.mpAto = mpAtoService.tratarValorTotal(this.mpAto, scOficVariavel, scOficLei3217, scOficLei4664,
																   scOficLei111, scOficLei6281);
			//
			this.mpAto.setIndAlteraValorAto(false);
			//
			this.mpAto = this.mpAtoService.salvar(this.mpAto);
			//
			totalProgresso = totalProgresso + contadorProgresso;
			if (totalProgresso > 20.0) {
				this.progresso = this.progresso + 20;
				totalProgresso = 0.0;
			}
			else
			if (totalProgresso > 10.0) {
				this.progresso = this.progresso + 10;
				totalProgresso = 0.0;
			}
			//
			if (this.progresso > 100) this.progresso = 100;
			//
			
			//sleep 5 seconds = 5000 
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				MpFacesUtil.addInfoMessage("Tread Interrompida !");
//			}
			//
		}
		//
		this.progresso = 100;
		//
		MpFacesUtil.addInfoMessage("Total ATOs calculados : ( " + contador + " )");
	}
	
    public void onComplete() {
		MpFacesUtil.addInfoMessage("Progresso Completado !");
    }
     
    public void cancel() {
        progresso = null;
    }	

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	

    // ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public Integer getProgresso() { return progresso; }
	public void setProgresso(Integer progresso) { this.progresso = progresso; }
	
}