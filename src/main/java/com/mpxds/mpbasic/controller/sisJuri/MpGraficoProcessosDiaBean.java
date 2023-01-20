package com.mpxds.mpbasic.controller.sisJuri;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.mpxds.mpbasic.model.sisJuri.MpProcesso;
import com.mpxds.mpbasic.repository.sisJuri.MpProcessos;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpGraficoProcessosDiaBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpProcessos mpProcessos;
	
	private LineChartModel lineModel;

	private Date dataInicial;
	private Date dataFinal;

//	private String mensagem;
	
	private Boolean indGeraGrafico = false;

	// -----------------

	public void preRender() {
		//
		if (this.indGeraGrafico) return;
		//
		Calendar dataIni = Calendar.getInstance();
		
		dataIni.setTime(new Date());
		dataIni.add(Calendar.DAY_OF_MONTH, -30);
		
		this.dataInicial = dataIni.getTime();
		this.dataFinal = new Date();
		//
//		this.mensagem = "( Entrou preRender() )";
		
		this.geraGraficoProcesso();
	}
		
	public void geraGraficoProcesso() {
		//
		this.indGeraGrafico = true;
		//
		String msg = "";
		if (null == this.dataInicial) msg = msg + "(Data Inicial)";
		if (null == this.dataFinal) msg = msg + "(Data Final)";

		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage(msg + "... inválida(s)");
			return;
		}
		
		if (this.dataInicial.after(dataFinal)) {
			MpFacesUtil.addInfoMessage("Data Inicial maior Data Final!");
			return;
		}
		//
		this.lineModel = new LineChartModel();
		
		this.lineModel.setTitle("Num.Processos X Dia :");
		this.lineModel.setLegendPosition("e");
		this.lineModel.setAnimate(true);
		
		this.lineModel.getAxes().put(AxisType.X, new CategoryAxis());
		//
		Calendar dataIni = Calendar.getInstance();
		Calendar dataFim = Calendar.getInstance();

		dataIni.setTime(this.dataInicial);
		dataFim.setTime(this.dataFinal);
		//
		Calendar calP = Calendar.getInstance();

		Date dataCadastroAnt = null;
		Integer qtdProcesso = 1;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    	ChartSeries series = new ChartSeries("Num.Processos X Dia : " + sdf.format(dataIni.getTime()) + " / " +
    																	sdf.format(dataFim.getTime()));
    	//
		List<MpProcesso> mpProcessoList = this.mpProcessos.findAllByDtHr(dataIni.getTime(), dataFim.getTime());
		//
		for (MpProcesso mpProcesso : mpProcessoList) {
			//
			calP.setTime(mpProcesso.getDataCadastro());
			//
			if (null == dataCadastroAnt) {
				dataCadastroAnt = calP.getTime();
			} else if (sdf.format(dataCadastroAnt).equals(sdf.format(calP.getTime()))) {
				qtdProcesso++;
			} else {
				series.set(sdf.format(dataCadastroAnt), qtdProcesso);
				//
				dataCadastroAnt = calP.getTime();
				qtdProcesso = 1;
			}
			//
		}
		if (qtdProcesso > 1)
			series.set(sdf.format(dataCadastroAnt), qtdProcesso);
		//		
		this.lineModel.addSeries(series);
		//
//		this.mensagem = this.mensagem + " ( MpGraficoProcessosDiaBean.geraGrafico() ( " + 
//			sdf.format(dataIni.getTime()) + " / " + sdf.format(dataFim.getTime()) + " / QtdP = " + qtdProcesso +
//			" / Size = " + mpProcessoList.size() + " )";
	}
	
	// ---
	
	public LineChartModel getLineModel() { return lineModel; }

  	public Date getDataInicial() { return this.dataInicial; }
  	public void setDataInicial(Date newDataInicial) { this.dataInicial = newDataInicial; }

  	public Date getDataFinal() { return this.dataFinal; }
  	public void setDataFinal(Date newDataFinal) { this.dataFinal = newDataFinal; }	
	
  	public Boolean getIndGeraGrafico() { return this.indGeraGrafico; }
  	public void setIndGeraGrafico(Boolean newIndGeraGrafico) { this.indGeraGrafico = newIndGeraGrafico; }

//  public String getMensagem() { return this.mensagem; }
//  public void setMensagem(String newMensagem) { this.mensagem = newMensagem; }	
}
