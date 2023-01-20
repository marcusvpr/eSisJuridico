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

import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.model.MpPedido;
import com.mpxds.mpbasic.repository.MpPedidos;
import com.mpxds.mpbasic.repository.filter.MpPedidoFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPedidos mpPedidos;
	
	private MpPedidoFilter mpFiltro;
	private List<MpPedido> mpPedidosFiltrados;
	
	private MpPedido mpPedidoSelecionado;
	
	private LazyDataModel<MpPedido> model;
	
	// ---------
	
	public MpPesquisaPedidosBean() {
		mpFiltro = new MpPedidoFilter();
		
		model = new LazyDataModel<MpPedido>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPedido> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpPedidos.quantidadeFiltrados(mpFiltro));
				
				return mpPedidos.filtrados(mpFiltro);
			}
			
		};
	}
		
	public void pesquisar() {
		mpPedidosFiltrados = mpPedidos.filtrados(mpFiltro);
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

	public void excluir() {
		try {
			mpPedidos.remover(mpPedidoSelecionado);
			mpPedidosFiltrados.remove(mpPedidoSelecionado);
			
			MpFacesUtil.addInfoMessage("Pedido... " + mpPedidoSelecionado.getMpContato().getNomeContato()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpPedidoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPedido> getModel() { return model; }
	
	public List<MpPedido> getMpPedidosFiltrados() { return mpPedidosFiltrados; }

	public MpPedido getMpPedidoSelecionado() { return mpPedidoSelecionado; }
	public void setMpPedidoSelecionado(MpPedido mpPedidoSelecionado) {
												this.mpPedidoSelecionado = mpPedidoSelecionado; }
	
}