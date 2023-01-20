package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.mpxds.mpbasic.model.enums.MpTabelaBD;
import com.mpxds.mpbasic.repository.filter.MpTabelaBDDTOFilter;
import com.mpxds.mpbasic.model.dto.MpTabelaBDDTO;

@Named
@ViewScoped
public class MpPesquisaTabelaBDsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	@Inject
//	private EntityManager manager;
		
	private List<MpTabelaBDDTO> mpTabelaBDDTOList = new ArrayList<MpTabelaBDDTO>();
	
	private List<MpTabelaBDDTO> mpTabelaBDDTOListAnt = new ArrayList<MpTabelaBDDTO>();
	
	private List<MpTabelaBD> mpTabelaBDList;
	
	private MpTabelaBDDTOFilter mpFiltro = new MpTabelaBDDTOFilter();
	private List<MpTabelaBD> mpTabelaBDsFiltrados;
	
	private MpTabelaBD mpTabelaBDSelecionado;
	
	// ---
	
	public MpPesquisaTabelaBDsBean() {
		//
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("MpProtestoPU");
		
		EntityManager manager = factory.createEntityManager();
		//
		this.mpTabelaBDList = Arrays.asList(MpTabelaBD.values());
    	//
//    	System.out.println("MpPesquisaTabelaBDsBean() - 000 = " + mpTabelaBDList.size());
    	//		
    	Iterator<MpTabelaBD> itr = this.mpTabelaBDList.iterator(); 
	    	
    	long numeroRegistros = 0;
    	//
    	while(itr.hasNext()) {
	    	//
	    	MpTabelaBD mpTabelaBD = (MpTabelaBD) itr.next();  
	        //
	    	MpTabelaBDDTO mpTabelaBDDTO = new MpTabelaBDDTO();
	    	
	    	mpTabelaBDDTO.setNumTabela(mpTabelaBD.getNumTabela());
	    	mpTabelaBDDTO.setTabela(mpTabelaBD.toString());
	    	mpTabelaBDDTO.setEntidade(mpTabelaBD.getEntidade());
	    	mpTabelaBDDTO.setDescricao(mpTabelaBD.getDescricao());
	    	mpTabelaBDDTO.setNumeroRegistros(0L);
	    	//
//	    	if (mpTabelaBDDTO.getTabela().trim().equals("MP_CATEGORIA")) {
	    		// long numeroRegistros = mpCategorias.numeroRegistros();
	    	//
	    	String query = "select count(c) from " + mpTabelaBDDTO.getEntidade() + " c";

//	    	System.out.println("MpPesquisaTabelaBDsBean() - 001 = " + query);
//	    	
	    	numeroRegistros = (long) manager.createQuery(query).getSingleResult();
	    	
	    	mpTabelaBDDTO.setNumeroRegistros(numeroRegistros);
	    	//
	    	this.mpTabelaBDDTOList.add(mpTabelaBDDTO);	    	
	    }
    	//
		this.mpTabelaBDDTOListAnt.addAll(mpTabelaBDDTOList);		
    	//
    	manager.close();
	}

	public void pesquisar() {
		//
		this.mpTabelaBDDTOList.clear();
		//
		for (MpTabelaBDDTO mpTabelaBDDTO : mpTabelaBDDTOListAnt) {
			//
			if (mpTabelaBDDTO.getTabela().toUpperCase().contains(this.mpFiltro.getCodigo().toUpperCase()))
				this.mpTabelaBDDTOList.add(mpTabelaBDDTO);
		}
	}
		
	public void posProcessarXls(Object documento) {
		//
		System.out.println("MpPesquisaTabelaBDsBean.posProcessarXls() - Entrou !");
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
	
	// --- 
	
	public MpTabelaBDDTOFilter getMpFiltro() { return mpFiltro; }

	public List<MpTabelaBDDTO> getMpTabelaBDDTOList() { return mpTabelaBDDTOList; }
	
	public List<MpTabelaBD> getMpTabelaBDsFiltrados() { return mpTabelaBDsFiltrados; }

	public MpTabelaBD getMpTabelaBDSelecionado() { return mpTabelaBDSelecionado; }
	public void setMpTabelaBDSelecionado(MpTabelaBD mpTabelaBDSelecionado) {
												this.mpTabelaBDSelecionado = mpTabelaBDSelecionado; }
	
}