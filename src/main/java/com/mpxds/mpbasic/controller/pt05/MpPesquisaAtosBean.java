package com.mpxds.mpbasic.controller.pt05;

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

import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.repository.pt05.MpAtos;
import com.mpxds.mpbasic.repository.filter.pt05.MpAtoFilter;

@Named
@ViewScoped
public class MpPesquisaAtosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpAtos mpAtos;
	
	private MpAtoFilter mpFiltro;
	private List<MpAto> mpAtosFiltrados;
	
	private MpAto mpAtoSelecionado;
	
	private LazyDataModel<MpAto> model;
	
	//---
	
	public MpPesquisaAtosBean() {
		mpFiltro = new MpAtoFilter();
		
		model = new LazyDataModel<MpAto>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpAto> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpAtos.quantidadeFiltrados(mpFiltro));
				
				return mpAtos.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpAtosFiltrados = mpAtos.filtrados(mpFiltro);
	}

	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	public MpAtoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpAto> getModel() { return model; }

	public List<MpAto> getMpAtosFiltrados() { return mpAtosFiltrados; }

	public MpAto getMpAtoSelecionado() { return mpAtoSelecionado; }
	public void setMpAtoSelecionado(MpAto mpAtoSelecionado) {
													this.mpAtoSelecionado = mpAtoSelecionado; }
	
}