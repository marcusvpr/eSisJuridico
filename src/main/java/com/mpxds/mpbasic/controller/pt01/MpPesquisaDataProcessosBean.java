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

import com.mpxds.mpbasic.model.pt01.MpDataProcesso;
import com.mpxds.mpbasic.repository.pt01.MpDataProcessos;
import com.mpxds.mpbasic.repository.filter.pt01.MpDataProcessoFilter;

@Named
@ViewScoped
public class MpPesquisaDataProcessosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpDataProcessos mpDataProcessos;
	
	private MpDataProcessoFilter mpFiltro;
	private List<MpDataProcesso> mpDataProcessosFiltrados;
	
	private MpDataProcesso mpDataProcessoSelecionado;
	
	private LazyDataModel<MpDataProcesso> model;
	
	//---
	
	public MpPesquisaDataProcessosBean() {
		mpFiltro = new MpDataProcessoFilter();
		
		model = new LazyDataModel<MpDataProcesso>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpDataProcesso> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpDataProcessos.quantidadeFiltrados(mpFiltro));
				
				return mpDataProcessos.filtrados(mpFiltro);
			}			
		};
	}

	public void pesquisar() {
		mpDataProcessosFiltrados = mpDataProcessos.filtrados(mpFiltro);
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
	
	public MpDataProcessoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpDataProcesso> getModel() { return model; }
	
	public List<MpDataProcesso> getMpDataProcessosFiltrados() { return mpDataProcessosFiltrados; }

	public MpDataProcesso getMpDataProcessoSelecionado() { return mpDataProcessoSelecionado; }
	public void setMpDataProcessoSelecionado(MpDataProcesso mpDataProcessoSelecionado) {
									this.mpDataProcessoSelecionado = mpDataProcessoSelecionado; }
	
}