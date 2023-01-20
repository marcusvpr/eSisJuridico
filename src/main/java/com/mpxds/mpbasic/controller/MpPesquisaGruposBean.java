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

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.filter.MpGrupoFilter;

@Named
@ViewScoped
public class MpPesquisaGruposBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpGrupos mpGrupos;
	
	private MpGrupoFilter mpFiltro;
	private List<MpGrupo> mpGruposFiltrados;
	
	private MpGrupo mpGrupoSelecionado;
	
	private LazyDataModel<MpGrupo> model;

	// ---
	
	public MpPesquisaGruposBean() {
		mpFiltro = new MpGrupoFilter();
		
		model = new LazyDataModel<MpGrupo>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpGrupo> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpGrupos.quantidadeFiltrados(mpFiltro));
				
				return mpGrupos.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpGruposFiltrados = mpGrupos.filtrados(mpFiltro);
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
	
	public MpGrupoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpGrupo> getModel() { return model; }
	
	public List<MpGrupo> getMpGruposFiltrados() { return mpGruposFiltrados; }

	public MpGrupo getMpGrupoSelecionado() { return mpGrupoSelecionado; }
	public void setMpGrupoSelecionado(MpGrupo mpGrupoSelecionado) {
												this.mpGrupoSelecionado = mpGrupoSelecionado; }
	
}