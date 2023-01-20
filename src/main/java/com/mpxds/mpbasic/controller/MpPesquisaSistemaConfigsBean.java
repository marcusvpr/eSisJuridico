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

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.filter.MpSistemaConfigFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaSistemaConfigsBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpSistemaConfigs mpSistemaConfigs;
	
	private MpSistemaConfigFilter mpFiltro;
	private List<MpSistemaConfig> mpSistemaConfigsFiltrados;
	
	private MpSistemaConfig mpSistemaConfigSelecionado;
	
	private LazyDataModel<MpSistemaConfig> model;

	// ---
	public MpPesquisaSistemaConfigsBean() {
		mpFiltro = new MpSistemaConfigFilter();
		
		model = new LazyDataModel<MpSistemaConfig>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpSistemaConfig> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpSistemaConfigs.quantidadeFiltrados(mpFiltro));
				
				return mpSistemaConfigs.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpSistemaConfigsFiltrados = mpSistemaConfigs.filtrados(mpFiltro);
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

	public void excluir() {
		try {
			mpSistemaConfigs.remover(mpSistemaConfigSelecionado);
			mpSistemaConfigsFiltrados.remove(mpSistemaConfigSelecionado);
			
			MpFacesUtil.addInfoMessage("SistemaConfig " + mpSistemaConfigSelecionado.getParametro() 
																		+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpSistemaConfigFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpSistemaConfig> getModel() {
		return model;
	}
	
	public List<MpSistemaConfig> getMpSistemaConfigsFiltrados() {
															return mpSistemaConfigsFiltrados; }

	public MpSistemaConfig getMpSistemaConfigSelecionado() { return mpSistemaConfigSelecionado; }
	public void setMpSistemaConfigSelecionado(MpSistemaConfig mpSistemaConfigSelecionado) {
									this.mpSistemaConfigSelecionado = mpSistemaConfigSelecionado; }
	
}