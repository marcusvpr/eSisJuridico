package com.mpxds.mpbasic.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.mpxds.mpbasic.repository.MpClientes;

@Named
@RequestScoped
public class MpGraficoClientesFaixaEtariaBean {
	//
	@Inject
	private MpClientes mpClientes;

	private PieChartModel pieModel;

	// ----
	
	@PostConstruct
	public void init() {
		createPieModels();
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	private void createPieModels() {
		pieModel = new PieChartModel();

		Integer quantidade = 0;
		//
		quantidade = mpClientes.countByIdadeDeAte(1, 15);
		pieModel.set("1 - 15 anos", quantidade);
		quantidade = mpClientes.countByIdadeDeAte(16, 30);
		pieModel.set("16 - 30 anos",quantidade);
		quantidade = mpClientes.countByIdadeDeAte(31, 45);
		pieModel.set("31 - 45 anos",quantidade);
		quantidade = mpClientes.countByIdadeDeAte(46, 60);
		pieModel.set("46 - 60 anos",quantidade);
		quantidade = mpClientes.countByIdadeDeAte(61, 75);
		pieModel.set("61 - 75 anos",quantidade);
		quantidade = mpClientes.countByIdadeDeAte(76, 90);
		pieModel.set("76 - 90 anos",quantidade);
		quantidade = mpClientes.countByIdadeDeAte(91, 130);
		pieModel.set("91 - 130 anos",quantidade);

		pieModel.setTitle("Cliente Faixa Etária");
		pieModel.setLegendPosition("e");
		pieModel.setShowDataLabels(true);
	}

}