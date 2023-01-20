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

import com.mpxds.mpbasic.model.pt01.MpBanco;
import com.mpxds.mpbasic.repository.pt01.MpBancos;
import com.mpxds.mpbasic.repository.filter.pt01.MpBancoFilter;

@Named
@ViewScoped
public class MpPesquisaBancosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpBancos mpBancos;
	
	private MpBancoFilter mpFiltro;
	private List<MpBanco> mpBancosFiltrados;
	
	private MpBanco mpBancoSelecionado;
	
	private LazyDataModel<MpBanco> model;
	
	//---
	
	public MpPesquisaBancosBean() {
		mpFiltro = new MpBancoFilter();
		
		model = new LazyDataModel<MpBanco>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpBanco> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpBancos.quantidadeFiltrados(mpFiltro));
				
				return mpBancos.filtrados(mpFiltro);
			}
			
		};
	}
	
	public void pesquisar() {
		mpBancosFiltrados = mpBancos.filtrados(mpFiltro);
	}

	public void posProcessarXls(Object documento) {
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
	
	public MpBancoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpBanco> getModel() { return model; }

	public List<MpBanco> getMpBancosFiltrados() { return mpBancosFiltrados; }

	public MpBanco getMpBancoSelecionado() { return mpBancoSelecionado; }
	public void setMpBancoSelecionado(MpBanco mpBancoSelecionado) {
												this.mpBancoSelecionado = mpBancoSelecionado; }
	
}