package com.mpxds.mpbasic.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.mpxds.mpbasic.model.enums.MpTabelaBD;
import com.mpxds.mpbasic.model.dto.MpTabelaBDDTO;

public class MpTabelaBDs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<MpTabelaBDDTO> mpTabelaBDDTOList = new ArrayList<MpTabelaBDDTO>();
	
	private List<MpTabelaBD> mpTabelaBDList;
	
	// ----------------
		
	public MpTabelaBDDTO porTabela(String tabela) {
		this.mpTabelaBDList = Arrays.asList(MpTabelaBD.values());
    	//
    	System.out.println("MpTabelaBDs.porTabela() = " + mpTabelaBDList.size() + " (" +
    																				tabela);
    	//		
    	Iterator<MpTabelaBD> itr = this.mpTabelaBDList.iterator(); 
    	
	    while(itr.hasNext()) {
	    	//
	    	MpTabelaBD mpTabelaBD = (MpTabelaBD) itr.next();  
	        //
	    	MpTabelaBDDTO mpTabelaBDDTO = new MpTabelaBDDTO();
	    	
	    	mpTabelaBDDTO.setTabela(mpTabelaBD.toString());
	    	mpTabelaBDDTO.setEntidade(mpTabelaBD.getEntidade());
	    	mpTabelaBDDTO.setDescricao(mpTabelaBD.getDescricao());
	    	mpTabelaBDDTO.setNumeroRegistros(0L);
	    	//
//	    	System.out.println("MpTabelaBDs.porTabela() = " + mpTabelaBDList.toString() + " (" +
//																						tabela);
	    	//
	    	if (mpTabelaBDList.toString().equals(tabela))
	    		return mpTabelaBDDTO;
	    	//
	    }
	    //
		return null;
	}

	public List<MpTabelaBDDTO> getMpTabelaBDDTOList() {
		return mpTabelaBDDTOList;
	}
	
}