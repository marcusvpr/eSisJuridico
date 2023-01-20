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

import com.mpxds.mpbasic.model.sisJuri.MpPessoaFisica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaFisicas;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpPessoaFisicaFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaPessoaFisicasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPessoaFisicas mpPessoaFisicas;
	
	private MpPessoaFisicaFilter mpFiltro;
	private List<MpPessoaFisica> mpPessoaFisicasFiltrados;
	
	private MpPessoaFisica mpPessoaFisicaSelecionado;
	
	private LazyDataModel<MpPessoaFisica> model;
	
	// ---------
	
	public MpPesquisaPessoaFisicasBean() {
		mpFiltro = new MpPessoaFisicaFilter();
		
		model = new LazyDataModel<MpPessoaFisica>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPessoaFisica> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				setRowCount(mpPessoaFisicas.quantidadeFiltrados(mpFiltro));
				
				return mpPessoaFisicas.filtrados(mpFiltro);
			}
			
		};
	}
		
	public void pesquisar() {
		mpPessoaFisicasFiltrados = mpPessoaFisicas.filtrados(mpFiltro);
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
			mpPessoaFisicas.remover(mpPessoaFisicaSelecionado);
			mpPessoaFisicasFiltrados.remove(mpPessoaFisicaSelecionado);
			
			MpFacesUtil.addInfoMessage("Pessoa Física... ( " + mpPessoaFisicaSelecionado.getNome() 
																	+ " )... excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpPessoaFisicaFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPessoaFisica> getModel() { return model; }
	
	public List<MpPessoaFisica> getMpPessoaFisicasFiltrados() { return mpPessoaFisicasFiltrados; }

	public MpPessoaFisica getMpPessoaFisicaSelecionado() { return mpPessoaFisicaSelecionado; }
	public void setMpPessoaFisicaSelecionado(MpPessoaFisica mpPessoaFisicaSelecionado) {
												this.mpPessoaFisicaSelecionado = mpPessoaFisicaSelecionado; }
	
}