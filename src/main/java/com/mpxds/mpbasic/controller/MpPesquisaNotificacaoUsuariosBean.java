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

import com.mpxds.mpbasic.model.MpNotificacaoUsuario;
import com.mpxds.mpbasic.repository.MpNotificacaoUsuarios;
import com.mpxds.mpbasic.repository.filter.MpNotificacaoUsuarioFilter;

@Named
@ViewScoped
public class MpPesquisaNotificacaoUsuariosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpNotificacaoUsuarios mpNotificacaoUsuarios;
	
	private MpNotificacaoUsuarioFilter mpFiltro;
	private List<MpNotificacaoUsuario> mpNotificacaoUsuariosFiltrados;
	
	private MpNotificacaoUsuario mpNotificacaoUsuarioSelecionado;
	
	private LazyDataModel<MpNotificacaoUsuario> model;

	// ---
	
	public MpPesquisaNotificacaoUsuariosBean() {
		//
		mpFiltro = new MpNotificacaoUsuarioFilter();
		
		model = new LazyDataModel<MpNotificacaoUsuario>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpNotificacaoUsuario> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpNotificacaoUsuarios.quantidadeFiltrados(mpFiltro));
				
				return mpNotificacaoUsuarios.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		//
		mpNotificacaoUsuariosFiltrados = mpNotificacaoUsuarios.filtrados(mpFiltro);
	}

	public void posProcessarXls(Object documento) {
		//
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
	
	public MpNotificacaoUsuarioFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpNotificacaoUsuario> getModel() { return model; }
	
	public List<MpNotificacaoUsuario> getMpNotificacaoUsuariosFiltrados() { 
														return mpNotificacaoUsuariosFiltrados; }

	public MpNotificacaoUsuario getMpNotificacaoUsuarioSelecionado() {
														return mpNotificacaoUsuarioSelecionado; }
	public void setMpNotificacaoUsuarioSelecionado(MpNotificacaoUsuario
															mpNotificacaoUsuarioSelecionado) {
						this.mpNotificacaoUsuarioSelecionado = mpNotificacaoUsuarioSelecionado; }
	
}