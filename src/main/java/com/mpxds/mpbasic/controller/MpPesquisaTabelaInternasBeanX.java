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

import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.repository.filter.MpTabelaInternaFilter;

@Named
@ViewScoped
public class MpPesquisaTabelaInternasBeanX implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTabelaInternas mpTabelaInternas;
	
	private MpTabelaInternaFilter mpFiltro;
	private List<MpTabelaInterna> mpTabelaInternasFiltrados;
	
	private MpTabelaInterna mpTabelaInternaSelecionado;
	
	private LazyDataModel<MpTabelaInterna> model;
	
	// --------------------
	
	public MpPesquisaTabelaInternasBeanX() {
		mpFiltro = new MpTabelaInternaFilter();
		
		model = new LazyDataModel<MpTabelaInterna>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpTabelaInterna> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpTabelaInternas.quantidadeFiltrados(mpFiltro));
				
				return mpTabelaInternas.filtrados(mpFiltro);
			}			
		};
	}

	public void pesquisar() {
		//
//		System.out.println("MpPesquisaTabelaInternasBeanX.pesquisar() - Entrou !");

		mpTabelaInternasFiltrados = mpTabelaInternas.filtrados(mpFiltro);
	}

	public void posProcessarXlsX(Object documento) {
		//
//		System.out.println("MpPesquisaTabelaInternasBeanX.posProcessarXlsX() - Entrou !");
		//
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
	
	public MpTabelaInternaFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpTabelaInterna> getModel() { return model; }
	
	public List<MpTabelaInterna> getMpTabelaInternasFiltrados() { return mpTabelaInternasFiltrados; }

	public MpTabelaInterna getMpTabelaInternaSelecionado() { return mpTabelaInternaSelecionado; }
	public void setMpTabelaInternaSelecionado(MpTabelaInterna mpTabelaInternaSelecionado) {
									this.mpTabelaInternaSelecionado = mpTabelaInternaSelecionado; }
	
}