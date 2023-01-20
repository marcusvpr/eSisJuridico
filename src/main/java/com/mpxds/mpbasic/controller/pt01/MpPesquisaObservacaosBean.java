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

import com.mpxds.mpbasic.model.pt01.MpObservacao;
import com.mpxds.mpbasic.repository.pt01.MpObservacaos;
import com.mpxds.mpbasic.repository.filter.pt01.MpObservacaoFilter;

@Named
@ViewScoped
public class MpPesquisaObservacaosBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObservacaos mpObservacaos;

	private MpObservacaoFilter mpFiltro;
	private List<MpObservacao> mpObservacaosFiltrados;
	private MpObservacao mpObservacaoSelecionado;
	private LazyDataModel<MpObservacao> model;

	// ---

	public MpPesquisaObservacaosBean() {
		//
		mpFiltro = new MpObservacaoFilter();

		model = new LazyDataModel<MpObservacao>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<MpObservacao> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpObservacaos.quantidadeFiltrados(mpFiltro));
				
				return mpObservacaos.filtrados(mpFiltro);
			}
		};
	}

	public void pesquisar() {
		//
		mpObservacaosFiltrados = mpObservacaos.filtrados(mpFiltro);
	}

	public void posProcessarXls(Object documento) {
		//
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
	
	public MpObservacaoFilter getMpFiltro() {return mpFiltro;}

	public LazyDataModel<MpObservacao> getModel() {return model;  }               
		
	public List<MpObservacao> getMpObservacaosFiltrados() {return mpObservacaosFiltrados; }
	
	public MpObservacao getMpObservacaoSelecionado() { return mpObservacaoSelecionado;}
	public void setMpObservacaoSelecionado(MpObservacao mpObservacaoSelecionado) {
		                      		this.mpObservacaoSelecionado = mpObservacaoSelecionado; }
		
}	
			