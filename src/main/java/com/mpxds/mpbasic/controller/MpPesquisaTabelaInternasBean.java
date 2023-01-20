package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.repository.filter.MpTabelaInternaFilter;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaTabelaInternasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpTabelaInternas mpTabelaInternas;
	
	private MpTabelaInternaFilter mpFiltro;
	private List<MpTabelaInterna> mpTabelaInternasFiltrados;
	
	private MpTabelaInterna mpTabelaInternaSelecionado;
	
	public MpPesquisaTabelaInternasBean() {
		mpFiltro = new MpTabelaInternaFilter();
		
//		mpFiltro.setNumero("9999"); // 9999=Consulta ALL ;
	}
	
	public void excluir() {
		try {
			mpTabelaInternas.remover(mpTabelaInternaSelecionado);
			mpTabelaInternasFiltrados.remove(mpTabelaInternaSelecionado);
			
			MpFacesUtil.addInfoMessage("Tabela Interna " + 
						mpTabelaInternaSelecionado.getMpTipoTabelaInterna() +
						" / " + mpTabelaInternaSelecionado.getCodigo() + " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		mpTabelaInternasFiltrados = mpTabelaInternas.filtrados(mpFiltro);
	}
	
	public List<MpTabelaInterna> getMpTabelaInternasFiltrados() {
		return mpTabelaInternasFiltrados;
	}

	public MpTabelaInternaFilter getMpFiltro() {
		return mpFiltro;
	}

	public MpTabelaInterna getMpTabelaInternaSelecionado() {
		return mpTabelaInternaSelecionado;
	}

	public void setMpTabelaInternaSelecionado(MpTabelaInterna mpTabelaInternaSelecionado) {
		this.mpTabelaInternaSelecionado = mpTabelaInternaSelecionado;
	}
	
}