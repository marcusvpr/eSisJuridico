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

import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.repository.pt05.MpCustasComposicaos;
import com.mpxds.mpbasic.repository.filter.pt05.MpCustasComposicaoFilter;

@Named
@ViewScoped
public class MpPesquisaCustasComposicaosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpCustasComposicaos mpCustasComposicaos;
	
	private MpCustasComposicaoFilter mpFiltro;
	private List<MpCustasComposicao> mpCustasComposicaosFiltrados;
	
	private MpCustasComposicao mpCustasComposicaoSelecionado;
	
	private LazyDataModel<MpCustasComposicao> model;
	
	//---
	
	public MpPesquisaCustasComposicaosBean() {
		mpFiltro = new MpCustasComposicaoFilter();
		
		model = new LazyDataModel<MpCustasComposicao>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpCustasComposicao> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpCustasComposicaos.quantidadeFiltrados(mpFiltro));
				
				return mpCustasComposicaos.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpCustasComposicaosFiltrados = mpCustasComposicaos.filtrados(mpFiltro);
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
	
	public MpCustasComposicaoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpCustasComposicao> getModel() { return model; }

	public List<MpCustasComposicao> getMpCustasComposicaosFiltrados() {
															return mpCustasComposicaosFiltrados; }

	public MpCustasComposicao getMpCustasComposicaoSelecionado() {
															return mpCustasComposicaoSelecionado; }
	public void setMpCustasComposicaoSelecionado(MpCustasComposicao mpCustasComposicaoSelecionado) {
								this.mpCustasComposicaoSelecionado = mpCustasComposicaoSelecionado; }
	
}