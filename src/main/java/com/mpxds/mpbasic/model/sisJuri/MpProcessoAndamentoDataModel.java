package com.mpxds.mpbasic.model.sisJuri;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class MpProcessoAndamentoDataModel extends ListDataModel<MpProcessoAndamento> implements 
																SelectableDataModel<MpProcessoAndamento> {  

    public MpProcessoAndamentoDataModel() {
    	//
    }

    public MpProcessoAndamentoDataModel(List<MpProcessoAndamento> data) {
    	//
        super(data);
    }

    @Override
    public MpProcessoAndamento getRowData(String rowKey) {
    	//
        @SuppressWarnings("unchecked")
		List<MpProcessoAndamento> mpProcessoAndamentos = (List<MpProcessoAndamento>) getWrappedData();

        for(MpProcessoAndamento mpProcessoAndamento : mpProcessoAndamentos) {
            if(mpProcessoAndamento.getDataCadastroSDF().equals(rowKey))
                return mpProcessoAndamento;
        }
        //
        return null;
    }

	@Override
	public Object getRowKey(MpProcessoAndamento mpProcessoAndamento) {
		//
		return mpProcessoAndamento.getDataCadastroSDF();
	}

}