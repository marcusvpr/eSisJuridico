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

import com.mpxds.mpbasic.model.sisJuri.MpPessoaJuridica;
import com.mpxds.mpbasic.repository.sisJuri.MpPessoaJuridicas;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpPessoaJuridicaFilter;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaPessoaJuridicasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpPessoaJuridicas mpPessoaJuridicas;
	
	private MpPessoaJuridicaFilter mpFiltro;
	private List<MpPessoaJuridica> mpPessoaJuridicasFiltrados;
	
	private MpPessoaJuridica mpPessoaJuridicaSelecionado;
	
	private LazyDataModel<MpPessoaJuridica> model;
	
	// ---------
	
	public MpPesquisaPessoaJuridicasBean() {
		mpFiltro = new MpPessoaJuridicaFilter();
		
		model = new LazyDataModel<MpPessoaJuridica>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<MpPessoaJuridica> load(int first, int pageSize, String sortField,
										SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				setRowCount(mpPessoaJuridicas.quantidadeFiltrados(mpFiltro));
				
				return mpPessoaJuridicas.filtrados(mpFiltro);
			}
			
		};
	}
		
	public void pesquisar() {
		mpPessoaJuridicasFiltrados = mpPessoaJuridicas.filtrados(mpFiltro);
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
			mpPessoaJuridicas.remover(mpPessoaJuridicaSelecionado);
			mpPessoaJuridicasFiltrados.remove(mpPessoaJuridicaSelecionado);
			
			MpFacesUtil.addInfoMessage("Pessoa Jurídica... ( " + mpPessoaJuridicaSelecionado.getNome() 
																	+ " )... excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public MpPessoaJuridicaFilter getMpFiltro() { return mpFiltro; }

	public LazyDataModel<MpPessoaJuridica> getModel() { return model; }
	
	public List<MpPessoaJuridica> getMpPessoaJuridicasFiltrados() { return mpPessoaJuridicasFiltrados; }

	public MpPessoaJuridica getMpPessoaJuridicaSelecionado() { return mpPessoaJuridicaSelecionado; }
	public void setMpPessoaJuridicaSelecionado(MpPessoaJuridica mpPessoaJuridicaSelecionado) {
												this.mpPessoaJuridicaSelecionado = mpPessoaJuridicaSelecionado; }
	
}