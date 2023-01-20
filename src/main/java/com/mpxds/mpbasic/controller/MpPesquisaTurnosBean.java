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

import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.repository.MpTurnos;
import com.mpxds.mpbasic.repository.filter.MpTurnoFilter;

@Named
@ViewScoped
public class MpPesquisaTurnosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTurnos mpTurnos;
	
	private MpTurnoFilter mpFiltro;
	private List<MpTurno> mpTurnosFiltrados;
	
	private MpTurno mpTurnoSelecionado;
		
	private LazyDataModel<MpTurno> model;
	
	//---
	
	public MpPesquisaTurnosBean() {
		//
		mpFiltro = new MpTurnoFilter();
		
		model = new LazyDataModel<MpTurno>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpTurno> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpTurnos.quantidadeFiltrados(mpFiltro));
				
				return mpTurnos.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpTurnosFiltrados = mpTurnos.filtrados(mpFiltro);
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
	
	public MpTurnoFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpTurno> getModel() {
		return model;
	}
	
	public List<MpTurno> getMpTurnosFiltrados() { return mpTurnosFiltrados; }

	public MpTurno getMpTurnoSelecionado() { return mpTurnoSelecionado; }
	public void setMpTurnoSelecionado(MpTurno mpTurnoSelecionado) {
												this.mpTurnoSelecionado = mpTurnoSelecionado; }
	
}