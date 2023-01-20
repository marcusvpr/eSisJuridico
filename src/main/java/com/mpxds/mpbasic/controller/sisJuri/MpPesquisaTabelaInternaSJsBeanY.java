package com.mpxds.mpbasic.controller.sisJuri;

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

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpTabelaInternaSJFilter;

@Named
@ViewScoped
public class MpPesquisaTabelaInternaSJsBeanY implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;
	
	private MpTabelaInternaSJFilter mpFiltro;
	private List<MpTabelaInternaSJ> mpTabelaInternaSJsFiltrados;
	
	private MpTabelaInternaSJ mpTabelaInternaSJSelecionado;
	
	private LazyDataModel<MpTabelaInternaSJ> model;
	
	// --------------------
	
	public MpPesquisaTabelaInternaSJsBeanY() {
		mpFiltro = new MpTabelaInternaSJFilter();
		
		model = new LazyDataModel<MpTabelaInternaSJ>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpTabelaInternaSJ> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpTabelaInternaSJs.quantidadeFiltrados(mpFiltro));
				
				return mpTabelaInternaSJs.filtrados(mpFiltro);
			}			
		};
	}

	public void pesquisar() {
		//
//		System.out.println("MpPesquisaTabelaInternaSJsBeanX.pesquisar() - Entrou !");

		mpTabelaInternaSJsFiltrados = mpTabelaInternaSJs.filtrados(mpFiltro);
	}

	public void posProcessarXlsX(Object documento) {
		//
//		System.out.println("MpPesquisaTabelaInternaSJsBeanX.posProcessarXlsX() - Entrou !");
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
	
	public MpTabelaInternaSJFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpTabelaInternaSJ> getModel() { return model; }
	
	public List<MpTabelaInternaSJ> getMpTabelaInternaSJsFiltrados() { return mpTabelaInternaSJsFiltrados; }

	public MpTabelaInternaSJ getMpTabelaInternaSJSelecionado() { return mpTabelaInternaSJSelecionado; }
	public void setMpTabelaInternaSJSelecionado(MpTabelaInternaSJ mpTabelaInternaSJSelecionado) {
									this.mpTabelaInternaSJSelecionado = mpTabelaInternaSJSelecionado; }
	
}