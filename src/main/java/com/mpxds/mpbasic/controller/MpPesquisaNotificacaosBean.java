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

import com.mpxds.mpbasic.model.MpNotificacao;
import com.mpxds.mpbasic.repository.MpNotificacaos;
import com.mpxds.mpbasic.repository.filter.MpNotificacaoFilter;

@Named
@ViewScoped
public class MpPesquisaNotificacaosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpNotificacaos mpNotificacaos;
	
	private MpNotificacaoFilter mpFiltro;
	private List<MpNotificacao> mpNotificacaosFiltrados;
	
	private MpNotificacao mpNotificacaoSelecionado;
	
	private LazyDataModel<MpNotificacao> model;
	
	//---
		
	public MpPesquisaNotificacaosBean() {
		mpFiltro = new MpNotificacaoFilter();
		
		model = new LazyDataModel<MpNotificacao>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpNotificacao> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpNotificacaos.quantidadeFiltrados(mpFiltro));
				
				return mpNotificacaos.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpNotificacaosFiltrados = mpNotificacaos.filtrados(mpFiltro);
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
	
	public MpNotificacaoFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpNotificacao> getModel() {
		return model;
	}
	
	public List<MpNotificacao> getMpNotificacaosFiltrados() { return mpNotificacaosFiltrados; }

	public MpNotificacao getMpNotificacaoSelecionado() { return mpNotificacaoSelecionado; }
	public void setMpNotificacaoSelecionado(MpNotificacao mpNotificacaoSelecionado) {
										this.mpNotificacaoSelecionado = mpNotificacaoSelecionado; }
	
}