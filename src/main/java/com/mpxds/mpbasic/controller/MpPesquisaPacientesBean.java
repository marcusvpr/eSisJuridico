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

import com.mpxds.mpbasic.model.MpPaciente;
import com.mpxds.mpbasic.repository.MpPacientes;
import com.mpxds.mpbasic.repository.filter.MpPacienteFilter;

@Named
@ViewScoped
public class MpPesquisaPacientesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPacientes mpPacientes;
	
	private MpPacienteFilter mpFiltro;
	private List<MpPaciente> mpPacientesFiltrados;
	
	private MpPaciente mpPacienteSelecionado;
		
	private LazyDataModel<MpPaciente> model;
	
	public MpPesquisaPacientesBean() {
		mpFiltro = new MpPacienteFilter();
		
		model = new LazyDataModel<MpPaciente>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPaciente> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpPacientes.quantidadeFiltrados(mpFiltro));
				
				return mpPacientes.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpPacientesFiltrados = mpPacientes.filtrados(mpFiltro);
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
	
	public MpPacienteFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPaciente> getModel() { return model; }
	
	public List<MpPaciente> getMpPacientesFiltrados() { return mpPacientesFiltrados; }

	public MpPaciente getMpPacienteSelecionado() { return mpPacienteSelecionado; }
	public void setMpPacienteSelecionado(MpPaciente mpPacienteSelecionado) {
												this.mpPacienteSelecionado = mpPacienteSelecionado; }
	
}