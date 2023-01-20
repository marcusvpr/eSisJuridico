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

import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.repository.filter.MpCalendarioFilter;

@Named
@ViewScoped
public class MpPesquisaCalendariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpCalendarios mpCalendarios;
	
	private MpCalendarioFilter mpFiltro;
	private List<MpCalendario> mpCalendariosFiltrados;
	
	private MpCalendario mpCalendarioSelecionado;
		
	private LazyDataModel<MpCalendario> model;
	
	//---
	
	public MpPesquisaCalendariosBean() {
		mpFiltro = new MpCalendarioFilter();
		
		model = new LazyDataModel<MpCalendario>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpCalendario> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(sortOrder));
				setRowCount(mpCalendarios.quantidadeFiltrados(mpFiltro));
				
				return mpCalendarios.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpCalendariosFiltrados = mpCalendarios.filtrados(mpFiltro);
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
	
	public MpCalendarioFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpCalendario> getModel() {	return model; }
	
	public List<MpCalendario> getMpCalendariosFiltrados() { return mpCalendariosFiltrados; }

	public MpCalendario getMpCalendarioSelecionado() { return mpCalendarioSelecionado; }
	public void setMpCalendarioSelecionado(MpCalendario mpCalendarioSelecionado) {
											this.mpCalendarioSelecionado = mpCalendarioSelecionado; }
	
}