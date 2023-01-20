package com.mpxds.mpbasic.controller.pt08;

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

import com.mpxds.mpbasic.model.pt08.MpFeriado;
import com.mpxds.mpbasic.repository.pt08.MpFeriados;
import com.mpxds.mpbasic.repository.filter.pt08.MpFeriadoFilter;

@Named
@ViewScoped
public class MpPesquisaFeriadosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpFeriados mpFeriados;
	
	private MpFeriadoFilter mpFiltro;
	private List<MpFeriado> mpFeriadosFiltrados;
	
	private MpFeriado mpFeriadoSelecionado;
	
	private LazyDataModel<MpFeriado> model;
	
	//---
	
	public MpPesquisaFeriadosBean() {
		mpFiltro = new MpFeriadoFilter();
		
		model = new LazyDataModel<MpFeriado>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpFeriado> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpFeriados.quantidadeFiltrados(mpFiltro));
				
				return mpFeriados.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpFeriadosFiltrados = mpFeriados.filtrados(mpFiltro);
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
	
	public MpFeriadoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpFeriado> getModel() { return model; }
	
	public List<MpFeriado> getMpFeriadosFiltrados() { return mpFeriadosFiltrados; }

	public MpFeriado getMpFeriadoSelecionado() { return mpFeriadoSelecionado; }
	public void setMpFeriadoSelecionado(MpFeriado mpFeriadoSelecionado) {
												this.mpFeriadoSelecionado = mpFeriadoSelecionado; }
	
}