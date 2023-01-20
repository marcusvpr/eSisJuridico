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

import com.mpxds.mpbasic.model.log.MpErrorLog;
import com.mpxds.mpbasic.repository.MpErrorLogs;
import com.mpxds.mpbasic.repository.filter.MpErrorLogFilter;

@Named
@ViewScoped
public class MpPesquisaErrorLogsBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpErrorLogs mpErrorLogs;
	
	private MpErrorLogFilter mpFiltro;
	private List<MpErrorLog> mpErrorLogsFiltrados;
	
	private MpErrorLog mpErrorLogSelecionado;
	
	private LazyDataModel<MpErrorLog> model;

	// ---
	
	public MpPesquisaErrorLogsBean() {
		mpFiltro = new MpErrorLogFilter();
		
		model = new LazyDataModel<MpErrorLog>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpErrorLog> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpErrorLogs.quantidadeFiltrados(mpFiltro));
				
				return mpErrorLogs.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpErrorLogsFiltrados = mpErrorLogs.filtrados(mpFiltro);
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
	
	public MpErrorLogFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpErrorLog> getModel() { return model; }
	
	public List<MpErrorLog> getMpErrorLogsFiltrados() { return mpErrorLogsFiltrados; }

	public MpErrorLog getMpErrorLogSelecionado() { return mpErrorLogSelecionado; }
	public void setMpErrorLogSelecionado(MpErrorLog mpErrorLogSelecionado) {
												this.mpErrorLogSelecionado = mpErrorLogSelecionado; }
	
}