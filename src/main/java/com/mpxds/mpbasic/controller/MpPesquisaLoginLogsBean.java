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

import com.mpxds.mpbasic.model.log.MpLoginLog;
import com.mpxds.mpbasic.repository.MpLoginLogs;
import com.mpxds.mpbasic.repository.filter.MpLoginLogFilter;

@Named
@ViewScoped
public class MpPesquisaLoginLogsBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpLoginLogs mpLoginLogs;
	
	private MpLoginLogFilter mpFiltro;
	private List<MpLoginLog> mpLoginLogsFiltrados;
	
	private MpLoginLog mpLoginLogSelecionado;
	
	private LazyDataModel<MpLoginLog> model;

	// ---
	
	public MpPesquisaLoginLogsBean() {
		mpFiltro = new MpLoginLogFilter();
		
		model = new LazyDataModel<MpLoginLog>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpLoginLog> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpLoginLogs.quantidadeFiltrados(mpFiltro));
				
				return mpLoginLogs.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpLoginLogsFiltrados = mpLoginLogs.filtrados(mpFiltro);
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
	
	public MpLoginLogFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpLoginLog> getModel() { return model; }
	
	public List<MpLoginLog> getMpLoginLogsFiltrados() { return mpLoginLogsFiltrados; }

	public MpLoginLog getMpLoginLogSelecionado() { return mpLoginLogSelecionado; }
	public void setMpLoginLogSelecionado(MpLoginLog mpLoginLogSelecionado) {
												this.mpLoginLogSelecionado = mpLoginLogSelecionado; }
	
}