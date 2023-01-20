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

import com.mpxds.mpbasic.model.MpDolar;
import com.mpxds.mpbasic.repository.MpDolars;
import com.mpxds.mpbasic.repository.filter.MpDolarFilter;

@Named
@ViewScoped
public class MpPesquisaDolarsBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpDolars mpDolars;
	
	private MpDolarFilter mpFiltro;
	private List<MpDolar> mpDolarsFiltrados;
	
	private MpDolar mpDolarSelecionado;
	
	private LazyDataModel<MpDolar> model;
	
	//---
	
	public MpPesquisaDolarsBean() {
		mpFiltro = new MpDolarFilter();
		
		model = new LazyDataModel<MpDolar>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpDolar> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpDolars.quantidadeFiltrados(mpFiltro));
				
				return mpDolars.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpDolarsFiltrados = mpDolars.filtrados(mpFiltro);
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
	
	// ---
	
	public MpDolarFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpDolar> getModel() { return model; }
	
	public List<MpDolar> getMpDolarsFiltrados() { return mpDolarsFiltrados; }

	public MpDolar getMpDolarSelecionado() { return mpDolarSelecionado; }
	public void setMpDolarSelecionado(MpDolar mpDolarSelecionado) {
												this.mpDolarSelecionado = mpDolarSelecionado; }
	
}