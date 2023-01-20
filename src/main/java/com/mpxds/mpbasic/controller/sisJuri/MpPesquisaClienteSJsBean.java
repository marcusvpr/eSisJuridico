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

import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.model.sisJuri.MpClienteSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpClienteSJs;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpClienteSJFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaClienteSJsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpClienteSJs mpClienteSJs;
	
	private MpClienteSJFilter mpFiltro;
	private List<MpClienteSJ> mpClienteSJsFiltrados;
	
	private MpClienteSJ mpClienteSJSelecionado;
	
	private LazyDataModel<MpClienteSJ> model;
	
	// ---------
	
	public MpPesquisaClienteSJsBean() {
		mpFiltro = new MpClienteSJFilter();
		
		model = new LazyDataModel<MpClienteSJ>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpClienteSJ> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				setRowCount(mpClienteSJs.quantidadeFiltrados(mpFiltro));
				
				return mpClienteSJs.filtrados(mpFiltro);
			}
			
		};
	}
		
	public void pesquisar() {
		//
		mpClienteSJsFiltrados = mpClienteSJs.filtrados(mpFiltro);
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

	public void excluir() {
		//
		try {
			mpClienteSJs.remover(mpClienteSJSelecionado);
			mpClienteSJsFiltrados.remove(mpClienteSJSelecionado);
			
			MpFacesUtil.addInfoMessage("Cliente... ( " + mpClienteSJSelecionado.getNome() 
																	+ " )... excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpClienteSJFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpClienteSJ> getModel() { return model; }
	
	public List<MpClienteSJ> getMpClienteSJsFiltrados() { return mpClienteSJsFiltrados; }

	public MpClienteSJ getMpClienteSJSelecionado() { return mpClienteSJSelecionado; }
	public void setMpClienteSJSelecionado(MpClienteSJ mpClienteSJSelecionado) {
												this.mpClienteSJSelecionado = mpClienteSJSelecionado; }
	
}