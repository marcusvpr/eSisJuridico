package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.mpxds.mpbasic.model.MpReceita;
import com.mpxds.mpbasic.repository.MpReceitas;
import com.mpxds.mpbasic.repository.filter.MpReceitaFilter;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaReceitasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpReceitas mpReceitas;
	
	private MpReceitaFilter mpFiltro;
	private List<MpReceita> mpReceitasFiltrados;
	
	private MpReceita mpReceitaSelecionado;

	// --------------
	
	public MpPesquisaReceitasBean() {
		mpFiltro = new MpReceitaFilter();
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
			mpReceitas.remover(mpReceitaSelecionado);
			mpReceitasFiltrados.remove(mpReceitaSelecionado);
			
			MpFacesUtil.addInfoMessage("Receita " + mpReceitaSelecionado.getDataReceita() 
															+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		mpReceitasFiltrados = mpReceitas.filtrados(mpFiltro);
	}
	
	public List<MpReceita> getMpReceitasFiltrados() {
		return mpReceitasFiltrados;
	}

	public MpReceitaFilter getMpFiltro() {
		return mpFiltro;
	}

	public MpReceita getMpReceitaSelecionado() {
		return mpReceitaSelecionado;
	}

	public void setMpReceitaSelecionado(MpReceita mpReceitaSelecionado) {
		this.mpReceitaSelecionado = mpReceitaSelecionado;
	}
	
}