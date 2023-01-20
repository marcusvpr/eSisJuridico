package com.mpxds.mpbasic.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.mpxds.mpbasic.repository.MpMovimentoLogins;
import com.mpxds.mpbasic.security.MpUsuarioLogado;
import com.mpxds.mpbasic.security.MpUsuarioSistema;

@Named
@RequestScoped
public class MpGraficoUsuariosLoginsBean {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private MpMovimentoLogins mpMovimentoLogins;
	
	@Inject
	@MpUsuarioLogado
	private MpUsuarioSistema mpUsuarioLogado;
	
	private LineChartModel model;

	// -----------------
	
	public void preRender() {
		//
		this.model = new LineChartModel();
		
		this.model.setTitle("Logins efetuados");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		adicionarSerie("Todos os Logins");		
	}
	
	private void adicionarSerie(String rotulo) {
		Map<Date, Long> valoresPorData =
								this.mpMovimentoLogins.valoresTotaisPorData(15);
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
	}

	public LineChartModel getModel() {
		return model;
	}
	
}
