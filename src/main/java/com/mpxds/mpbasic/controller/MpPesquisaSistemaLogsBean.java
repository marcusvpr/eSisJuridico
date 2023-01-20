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

import com.mpxds.mpbasic.model.log.MpSistemaLog;
import com.mpxds.mpbasic.repository.MpSistemaLogs;
import com.mpxds.mpbasic.repository.filter.MpSistemaLogFilter;

@Named
@ViewScoped
public class MpPesquisaSistemaLogsBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpSistemaLogs mpSistemaLogs;
	
	private MpSistemaLogFilter mpFiltro;
	private List<MpSistemaLog> mpSistemaLogsFiltrados;
	
	private MpSistemaLog mpSistemaLogSelecionado;
	
	private LazyDataModel<MpSistemaLog> model;

	// ---
	
	public MpPesquisaSistemaLogsBean() {
		mpFiltro = new MpSistemaLogFilter();
		
		model = new LazyDataModel<MpSistemaLog>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpSistemaLog> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpSistemaLogs.quantidadeFiltrados(mpFiltro));
				
				return mpSistemaLogs.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpSistemaLogsFiltrados = mpSistemaLogs.filtrados(mpFiltro);
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
	
	public MpSistemaLogFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpSistemaLog> getModel() { return model; }
	
	public List<MpSistemaLog> getMpSistemaLogsFiltrados() { return mpSistemaLogsFiltrados; }

	public MpSistemaLog getMpSistemaLogSelecionado() { return mpSistemaLogSelecionado; }
	public void setMpSistemaLogSelecionado(MpSistemaLog mpSistemaLogSelecionado) {
											this.mpSistemaLogSelecionado = mpSistemaLogSelecionado; }
	
}