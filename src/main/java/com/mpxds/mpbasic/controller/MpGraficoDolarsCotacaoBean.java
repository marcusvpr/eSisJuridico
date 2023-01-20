package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;



//import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.mpxds.mpbasic.repository.MpDolars;
import com.mpxds.mpbasic.security.MpUsuarioLogado;
import com.mpxds.mpbasic.security.MpUsuarioSistema;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped // @RequestScoped
public class MpGraficoDolarsCotacaoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	private static DateFormat DATE_FORMATX = new SimpleDateFormat("dd/MM/YYYY");
	
	@Inject
	private MpDolars mpDolars;
	
	@Inject
	@MpUsuarioLogado
	private MpUsuarioSistema mpUsuarioLogado;
	
	private LineChartModel model;

	private Date dataInicial;
	private Date dataFinal;
	
	private Boolean indGeraGrafico = false;
	
	// -----------------
	
	public void preRender() {
		//
		if (this.indGeraGrafico) return;
		//
		Calendar dataIni = Calendar.getInstance();
		
		dataIni.setTime(new Date());
		dataIni.add(Calendar.DAY_OF_MONTH, -14);
		
		this.dataInicial = dataIni.getTime();
		
		this.dataFinal = new Date();
		// 
		this.model = new LineChartModel();
		
		this.model.setTitle("Cotação Dolar");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		adicionarSerie("Cotação do Dolar");		
	}
	
	private void adicionarSerie(String rotulo) {
		//
		Map<Date, BigDecimal> valoresPorData = this.mpDolars.valoresTotaisPorData(15);
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		//
		this.model.addSeries(series);
	}

	public void geraGrafico() {
		//
//		System.out.println("MpGraficoDolarsCotacaoBean.geraGrafico() ( " + this.dataInicial + " / " +
//																						this.dataFinal);
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
		this.model = new LineChartModel();
		
		this.model.setTitle("Cotação do Dolar :");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		//
		Map<Date, BigDecimal> valoresPorDataX = this.mpDolars.valoresTotaisPorIntervalo(
																this.dataInicial, this.dataFinal);
		
		ChartSeries series = new ChartSeries("Cotação Dolar: " + DATE_FORMATX.format(this.dataInicial) + " / " +
																 DATE_FORMATX.format(this.dataFinal));
		
		for (Date data : valoresPorDataX.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorDataX.get(data));
		}
		//
		this.model.addSeries(series);
	}
	
	// ---
	
	public LineChartModel getModel() { return model; }

  	public Date getDataInicial() { return this.dataInicial; }
  	public void setDataInicial(Date newDataInicial) { this.dataInicial = newDataInicial; }

  	public Date getDataFinal() { return this.dataFinal; }
  	public void setDataFinal(Date newDataFinal) { this.dataFinal = newDataFinal; }
	
  	public Boolean getIndGeraGrafico() { return this.indGeraGrafico; }
  	public void setIndGeraGrafico(Boolean newIndGeraGrafico) { this.indGeraGrafico = newIndGeraGrafico; }
	
}
