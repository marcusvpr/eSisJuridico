package com.mpxds.mpbasic.controller.pt01;

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

import com.mpxds.mpbasic.model.pt01.MpTitulo;
import com.mpxds.mpbasic.repository.pt01.MpTitulos;
import com.mpxds.mpbasic.repository.filter.pt01.MpTituloFilter;

@Named
@ViewScoped
public class MpPesquisaTitulosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTitulos mpTitulos;
	
	private MpTituloFilter mpFiltro;
	private List<MpTitulo> mpTitulosFiltrados;
	
	private MpTitulo mpTituloSelecionado;
	
	private LazyDataModel<MpTitulo> model;
	
	//---
	
	public MpPesquisaTitulosBean() {
		mpFiltro = new MpTituloFilter();
		
		model = new LazyDataModel<MpTitulo>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpTitulo> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpTitulos.quantidadeFiltrados(mpFiltro));
				
				return mpTitulos.filtrados(mpFiltro);
			}			
		};
	}

	public void pesquisar() {
		mpTitulosFiltrados = mpTitulos.filtrados(mpFiltro);
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
	
	public MpTituloFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpTitulo> getModel() { return model; }
	
	public List<MpTitulo> getMpTitulosFiltrados() { return mpTitulosFiltrados; }

	public MpTitulo getMpTituloSelecionado() { return mpTituloSelecionado; }
	public void setMpTituloSelecionado(MpTitulo mpTituloSelecionado) {
											this.mpTituloSelecionado = mpTituloSelecionado; }
	
}