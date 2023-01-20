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

import com.mpxds.mpbasic.model.log.MpMovimentoLogin;
import com.mpxds.mpbasic.repository.MpMovimentoLogins;
import com.mpxds.mpbasic.repository.filter.MpMovimentoLoginFilter;

@Named
@ViewScoped
public class MpPesquisaMovimentoLoginsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpMovimentoLogins mpMovimentoLogins;
	
	private MpMovimentoLoginFilter mpFiltro;
	private List<MpMovimentoLogin> mpMovimentoLoginsFiltrados;
	
	private MpMovimentoLogin mpMovimentoLoginSelecionado;
	
	private LazyDataModel<MpMovimentoLogin> model;
	
	// ---------
	
	public MpPesquisaMovimentoLoginsBean() {
		mpFiltro = new MpMovimentoLoginFilter();
		
		model = new LazyDataModel<MpMovimentoLogin>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpMovimentoLogin> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpMovimentoLogins.quantidadeFiltrados(mpFiltro));
				
				return mpMovimentoLogins.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpMovimentoLoginsFiltrados = mpMovimentoLogins.filtrados(mpFiltro);
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
	
	public MpMovimentoLoginFilter getMpFiltro() { return mpFiltro; 	}

	public LazyDataModel<MpMovimentoLogin> getModel() { return model; }
	
	public List<MpMovimentoLogin> getMpMovimentoLoginsFiltrados() {
																return mpMovimentoLoginsFiltrados; }

	public MpMovimentoLogin getMpMovimentoLoginSelecionado() { return mpMovimentoLoginSelecionado; }
	public void setMpMovimentoLoginSelecionado(MpMovimentoLogin mpMovimentoLoginSelecionado) {
									this.mpMovimentoLoginSelecionado = mpMovimentoLoginSelecionado; }
	
}