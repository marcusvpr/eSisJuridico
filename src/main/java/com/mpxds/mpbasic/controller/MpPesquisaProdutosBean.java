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

import com.mpxds.mpbasic.model.MpProduto;
import com.mpxds.mpbasic.repository.MpProdutos;
import com.mpxds.mpbasic.repository.filter.MpProdutoFilter;

@Named
@ViewScoped
public class MpPesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpProdutos mpProdutos;
	
	private MpProdutoFilter mpFiltro;
	private List<MpProduto> mpProdutosFiltrados;
	
	private MpProduto mpProdutoSelecionado;
	
	private LazyDataModel<MpProduto> model;

	// ---
	
	public MpPesquisaProdutosBean() {
		mpFiltro = new MpProdutoFilter();
		
		model = new LazyDataModel<MpProduto>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpProduto> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpProdutos.quantidadeFiltrados(mpFiltro));
				
				return mpProdutos.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpProdutosFiltrados = mpProdutos.filtrados(mpFiltro);
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
	
	public MpProdutoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpProduto> getModel() { return model; }
	
	public List<MpProduto> getMpProdutosFiltrados() { return mpProdutosFiltrados; }

	public MpProduto getMpProdutoSelecionado() { return mpProdutoSelecionado; }
	public void setMpProdutoSelecionado(MpProduto mpProdutoSelecionado) {
												this.mpProdutoSelecionado = mpProdutoSelecionado; }
	
}