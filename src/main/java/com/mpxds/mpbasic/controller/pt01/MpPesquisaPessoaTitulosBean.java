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

import com.mpxds.mpbasic.model.pt01.MpPessoaTitulo;
import com.mpxds.mpbasic.repository.pt01.MpPessoaTitulos;
import com.mpxds.mpbasic.repository.filter.pt01.MpPessoaTituloFilter;

@Named
@ViewScoped
public class MpPesquisaPessoaTitulosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPessoaTitulos mpPessoaTitulos;
	
	private MpPessoaTituloFilter mpFiltro;
	private List<MpPessoaTitulo> mpPessoaTitulosFiltrados;
	
	private MpPessoaTitulo mpPessoaTituloSelecionado;
	
	private LazyDataModel<MpPessoaTitulo> model;
	
	//---
	
	public MpPesquisaPessoaTitulosBean() {
		mpFiltro = new MpPessoaTituloFilter();
		
		model = new LazyDataModel<MpPessoaTitulo>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPessoaTitulo> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpPessoaTitulos.quantidadeFiltrados(mpFiltro));
				
				return mpPessoaTitulos.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpPessoaTitulosFiltrados = mpPessoaTitulos.filtrados(mpFiltro);
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
	
	public MpPessoaTituloFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPessoaTitulo> getModel() { return model; }

	public List<MpPessoaTitulo> getMpPessoaTitulosFiltrados() { return mpPessoaTitulosFiltrados; }

	public MpPessoaTitulo getMpPessoaTituloSelecionado() { return mpPessoaTituloSelecionado; }
	public void setMpPessoaTituloSelecionado(MpPessoaTitulo mpPessoaTituloSelecionado) {
										this.mpPessoaTituloSelecionado = mpPessoaTituloSelecionado; }
	
}