package com.mpxds.mpbasic.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.repository.MpDolars;
import com.mpxds.mpbasic.repository.MpCompras;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.security.MpUsuarioLogado;
import com.mpxds.mpbasic.security.MpUsuarioSistema;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@RequestScoped
public class MpGraficoComprasCriadosBean {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private MpCompras mpCompras;
	
	@Inject
	private MpDolars mpDolars;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	@Inject
	private MpLoginBean mpLoginBean;
	
	@Inject
	@MpUsuarioLogado
	private MpUsuarioSistema mpUsuarioLogado;
	
	private LineChartModel model;

	@ManagedProperty(value = "#{param.idP}")
	private String idP;
	
    // Configuração Sistema ...
    // ------------------------
	private Boolean indLoginDolar;
	
	// -----------------

	public void preRender() {
		//
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("indLoginDolar");
		if (null == mpSistemaConfig)
			this.indLoginDolar = true;
		else
			this.indLoginDolar = Boolean.parseBoolean(mpSistemaConfig.getValor().trim());
		
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("sistemaTelaInicial");
		if (null == mpSistemaConfig)
			mpSistemaConfig = null; // Ignora !
		else {
			if (!mpSistemaConfig.getValor().trim().toLowerCase().equals("default")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(
																mpSistemaConfig.getValor().trim());
				} catch (IOException e) {
					e.printStackTrace();
				}			
				return;
			}
		}
		//
		if (mpLoginBean.getIndMenuDoseCerta()) {
			//
//			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
//				handleNavigation(FacesContext.getCurrentInstance(), null, "/doseCertas/MpProdutos.xhtml");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
												"/mpProtesto/doseCertas/MpPesquisaProdutos.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return;
		}
		//
		if (this.indLoginDolar)
			this.trataDolar();
		//
		this.model = new LineChartModel();
		this.model.setTitle("Compras criadas");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		adicionarSerie("Todos as compras", null);
		
		if (null == mpUsuarioLogado)
			adicionarSerie("Minhas compras", null);
		else
			adicionarSerie("Minhas compras", mpUsuarioLogado.getMpUsuario());
		//
//		System.out.println("MpGraficoComprasCriadosBean.preRender - ( mpUsuarioLogado = " +
//																	mpUsuarioLogado.getMpUsuario());
	}
	
	public void trataDolar() {
		//
		if (null == this.idP) this.idP = "login";
//		System.out.println("MpGraficoComprasCriadosBean.trataDolar - ( idP = " + this.idP);
		//
		if (this.idP.equals("login")) {
//			System.out.println("MpGraficoComprasCriadosBean - ( " +
//																mpSistemaConfig.getValor());
			if (this.mpCompras.isDolarLogin()) {
				//
				MpDolar mpDolar = this.mpDolars.porDtHrMovimento(new Date());
				if (null == mpDolar) {
					//
					MpFacesUtil.addInfoMessage("Favor... Informar dolar do dia!");
					//
//					Map<String, Object> options = new HashMap<String, Object>();
//					
//					options.put("modal", true);
//					
//					Map<String, List<String>> params = new HashMap<String, List<String>>();
//					
//					List<String> values = new ArrayList<String>();
//					
//					values.add("new");
//					params.put("param", values);
//					
//					RequestContext.getCurrentInstance().openDialog("/dolars/MpCadastroDolar",
//		        																options, params);
				}
			}
		}
	}
		
	private void adicionarSerie(String rotulo, MpUsuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.mpCompras.valoresTotaisPorData(15, criadoPor);
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
		//
//		System.out.println("MpGraficoComprasCriadosBean.adicionarSerie - ( rotulo = " + rotulo);
	}

	// ---------------------------

	public LineChartModel getModel() { return model; }

	public String getIdP() { return idP; }
	public void setIdP(String idP) { this.idP = idP; }
	
}
