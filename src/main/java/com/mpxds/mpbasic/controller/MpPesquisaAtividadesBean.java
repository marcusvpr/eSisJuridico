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

import com.mpxds.mpbasic.model.MpAtividade;
import com.mpxds.mpbasic.repository.MpAtividades;
import com.mpxds.mpbasic.repository.filter.MpAtividadeFilter;

@Named
@ViewScoped
public class MpPesquisaAtividadesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpAtividades mpAtividades;
	private List<MpAtividade> mpAtividadesFiltrados;
	
	private MpAtividade mpAtividadeSelecionado;
		
	private MpAtividadeFilter mpFiltro;
	
	private LazyDataModel<MpAtividade> model;
	
	public MpPesquisaAtividadesBean() {
		mpFiltro = new MpAtividadeFilter();
		
		model = new LazyDataModel<MpAtividade>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpAtividade> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				setRowCount(mpAtividades.quantidadeFiltrados(mpFiltro));
				
				return mpAtividades.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpAtividadesFiltrados = mpAtividades.filtrados(mpFiltro);
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
	
	public MpAtividadeFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpAtividade> getModel() { return model; }
	
	public List<MpAtividade> getMpAtividadesFiltrados() { return mpAtividadesFiltrados; }

	public MpAtividade getMpAtividadeSelecionado() { return mpAtividadeSelecionado; }
	public void setMpAtividadeSelecionado(MpAtividade mpAtividadeSelecionado) {
											this.mpAtividadeSelecionado = mpAtividadeSelecionado; }
	
}