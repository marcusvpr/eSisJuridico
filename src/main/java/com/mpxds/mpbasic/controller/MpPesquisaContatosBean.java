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

import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.repository.MpContatos;
import com.mpxds.mpbasic.repository.filter.MpContatoFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaContatosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpContatos mpContatos;
	
	private MpContatoFilter mpFiltro;
	private List<MpContato> mpContatosFiltrados;
	
	private MpContato mpContatoSelecionado;
	
	private LazyDataModel<MpContato> model;
	
	// ---------
	
	public MpPesquisaContatosBean() {
		mpFiltro = new MpContatoFilter();
		
		model = new LazyDataModel<MpContato>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpContato> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpContatos.quantidadeFiltrados(mpFiltro));
				
				return mpContatos.filtrados(mpFiltro);
			}
			
		};
	}
		
	public void pesquisar() {
		mpContatosFiltrados = mpContatos.filtrados(mpFiltro);
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
			mpContatos.remover(mpContatoSelecionado);
			mpContatosFiltrados.remove(mpContatoSelecionado);
			
			MpFacesUtil.addInfoMessage("Contato " + mpContatoSelecionado.getNomeRazaoSocial() 
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpContatoFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpContato> getModel() { return model; }
	
	public List<MpContato> getMpContatosFiltrados() { return mpContatosFiltrados; }

	public MpContato getMpContatoSelecionado() { return mpContatoSelecionado; }
	public void setMpContatoSelecionado(MpContato mpContatoSelecionado) {
												this.mpContatoSelecionado = mpContatoSelecionado; }
	
}