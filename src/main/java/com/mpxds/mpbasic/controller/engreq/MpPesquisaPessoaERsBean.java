package com.mpxds.mpbasic.controller.engreq;

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

import com.mpxds.mpbasic.model.engreq.MpPessoaER;
import com.mpxds.mpbasic.repository.engreq.MpPessoaERs;
import com.mpxds.mpbasic.repository.filter.engreq.MpPessoaERFilter;

@Named
@ViewScoped
public class MpPesquisaPessoaERsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPessoaERs mpPessoaERs;
	
	private MpPessoaERFilter mpFiltro;
	private List<MpPessoaER> mpPessoaERsFiltrados;
	
	private MpPessoaER mpPessoaERSelecionado;
	
	private LazyDataModel<MpPessoaER> model;
	
	// ---------
	
	public MpPesquisaPessoaERsBean() {
		mpFiltro = new MpPessoaERFilter();
		
		model = new LazyDataModel<MpPessoaER>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPessoaER> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpPessoaERs.quantidadeFiltrados(mpFiltro));
				
				return mpPessoaERs.filtrados(mpFiltro);
			}
			
		};
	}

	public void pesquisar() {
		mpPessoaERsFiltrados = mpPessoaERs.filtrados(mpFiltro);
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
	
	public MpPessoaERFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPessoaER> getModel() { return model; }
	
	public List<MpPessoaER> getMpPessoaERsFiltrados() { return mpPessoaERsFiltrados; }

	public MpPessoaER getMpPessoaERSelecionado() { return mpPessoaERSelecionado; }
	public void setMpPessoaERSelecionado(MpPessoaER mpPessoaERSelecionado) {
												this.mpPessoaERSelecionado = mpPessoaERSelecionado; }
	
}