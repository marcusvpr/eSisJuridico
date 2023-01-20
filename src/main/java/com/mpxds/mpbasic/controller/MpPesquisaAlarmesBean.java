package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mpxds.mpbasic.model.MpAlarme;
import com.mpxds.mpbasic.repository.MpAlarmes;
import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;

@Named
@ViewScoped
public class MpPesquisaAlarmesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpAlarmes mpAlarmes;
	
	private MpAlarmeFilter mpFiltro;
	private List<MpAlarme> mpAlarmesFiltrados;
	
	private MpAlarme mpAlarmeSelecionado;
		
	private LazyDataModel<MpAlarme> model;
	
	//---
	
	public MpPesquisaAlarmesBean() {
		//
		mpFiltro = new MpAlarmeFilter();
		
		model = new LazyDataModel<MpAlarme>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpAlarme> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(sortOrder));
				setRowCount(mpAlarmes.quantidadeFiltrados(mpFiltro));
				
				return mpAlarmes.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpAlarmesFiltrados = mpAlarmes.filtrados(mpFiltro);
	}

	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	public MpAlarmeFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpAlarme> getModel() {
		return model;
	}
	
	public List<MpAlarme> getMpAlarmesFiltrados() { return mpAlarmesFiltrados; }

	public MpAlarme getMpAlarmeSelecionado() { return mpAlarmeSelecionado; }
	public void setMpAlarmeSelecionado(MpAlarme mpAlarmeSelecionado) {
												this.mpAlarmeSelecionado = mpAlarmeSelecionado; }
	
}