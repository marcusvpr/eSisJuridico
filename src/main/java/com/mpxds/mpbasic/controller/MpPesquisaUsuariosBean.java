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

import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.filter.MpUsuarioFilter;

@Named
@ViewScoped
public class MpPesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpUsuarios mpUsuarios;
	
	private MpUsuarioFilter mpFiltro;
	private List<MpUsuario> mpUsuariosFiltrados;
	
	private MpUsuario mpUsuarioSelecionado;
	
	private LazyDataModel<MpUsuario> model;
	
	public MpPesquisaUsuariosBean() {
		mpFiltro = new MpUsuarioFilter();
		
		model = new LazyDataModel<MpUsuario>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpUsuario> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpUsuarios.quantidadeFiltrados(mpFiltro));
				
				return mpUsuarios.filtrados(mpFiltro);
			}
		};
	}
	
	public void pesquisar() {
		mpUsuariosFiltrados = mpUsuarios.filtrados(mpFiltro);
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
	
	public MpUsuarioFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpUsuario> getModel() { return model; 	}
	
	public List<MpUsuario> getMpUsuariosFiltrados() { return mpUsuariosFiltrados; }

	public MpUsuario getMpUsuarioSelecionado() { return mpUsuarioSelecionado; }
	public void setMpUsuarioSelecionado(MpUsuario mpUsuarioSelecionado) {
												this.mpUsuarioSelecionado = mpUsuarioSelecionado; }

}