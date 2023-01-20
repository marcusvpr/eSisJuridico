package com.mpxds.mpbasic.controller.pt08;

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



import com.mpxds.mpbasic.model.pt08.MpTipoProtocolo;
import com.mpxds.mpbasic.repository.pt08.MpTipoProtocolos;
import com.mpxds.mpbasic.repository.filter.pt08.MpTipoProtocoloFilter;

@Named
@ViewScoped
public class MpPesquisaTipoProtocolosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTipoProtocolos mpTipoProtocolos;
	
	private MpTipoProtocoloFilter mpFiltro;
	private List<MpTipoProtocolo> mpTipoProtocolosFiltrados;
	private MpTipoProtocolo mpTipoProtocoloSelecionado;
	private LazyDataModel<MpTipoProtocolo> model;
	
	//---
	
	public MpPesquisaTipoProtocolosBean() {
		mpFiltro = new MpTipoProtocoloFilter();
		
		model = new LazyDataModel<MpTipoProtocolo>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpTipoProtocolo> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.DESCENDING.equals(
																					sortOrder));
				setRowCount(mpTipoProtocolos.quantidadeFiltrados(mpFiltro));
				
				return mpTipoProtocolos.filtrados(mpFiltro);
			}
			
		}; 
      }
		public void pesquisar() {
			mpTipoProtocolosFiltrados = mpTipoProtocolos.filtrados(mpFiltro);
		
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
	
	public MpTipoProtocoloFilter getMpFiltro() {return mpFiltro;}

	public LazyDataModel<MpTipoProtocolo> getModel() {return model;  }               
		
	public List<MpTipoProtocolo> getMpTipoProtocolosFiltrados() {
		                                                       return mpTipoProtocolosFiltrados; }
	
	public MpTipoProtocolo getMpTipoProtocoloSelecionado() {
		                                               return mpTipoProtocoloSelecionado;}
	public void setMpTipoProtocoloSelecionado(MpTipoProtocolo mpTipoProtocoloSelecionado) {
		                      this.mpTipoProtocoloSelecionado = mpTipoProtocoloSelecionado; }
		
}	
			