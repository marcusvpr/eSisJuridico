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

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.repository.filter.MpObjetoFilter;
import com.mpxds.mpbasic.security.MpSeguranca;

@Named
@ViewScoped
public class MpPesquisaObjetosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpObjetos mpObjetos;
	
	@Inject
	private MpSeguranca mpSeguranca;
	
	private MpObjetoFilter mpFiltro;
	private List<MpObjeto> mpObjetosFiltrados;
	
	private MpObjeto mpObjetoSelecionado;
	
	private LazyDataModel<MpObjeto> model;

	// ---
	public MpPesquisaObjetosBean() {
		mpFiltro = new MpObjetoFilter();
		
		model = new LazyDataModel<MpObjeto>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpObjeto> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpObjetos.quantidadeFiltrados(mpFiltro, 
														mpSeguranca.capturaTenantId().trim()));
				
				return mpObjetos.filtrados(mpFiltro, mpSeguranca.capturaTenantId().trim());
			}
			
		};
	}

	public void pesquisar() {
		mpObjetosFiltrados = mpObjetos.filtrados(mpFiltro, mpSeguranca.capturaTenantId().trim());
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
	
	public MpObjetoFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpObjeto> getModel() {
		return model;
	}
	
	public List<MpObjeto> getMpObjetosFiltrados() { return mpObjetosFiltrados; }

	public MpObjeto getMpObjetoSelecionado() { return mpObjetoSelecionado; }
	public void setMpObjetoSelecionado(MpObjeto mpObjetoSelecionado) {
												this.mpObjetoSelecionado = mpObjetoSelecionado; }
	
}