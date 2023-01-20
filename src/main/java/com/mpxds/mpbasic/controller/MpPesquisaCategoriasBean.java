package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpCategoria;
import com.mpxds.mpbasic.repository.MpCategorias;
import com.mpxds.mpbasic.repository.filter.MpCategoriaFilter;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpPesquisaCategoriasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MpCategorias mpCategorias;
	
	private MpCategoriaFilter mpFiltro;
	private List<MpCategoria> mpCategoriasFiltrados;
	
	private MpCategoria mpCategoriaSelecionado;
	
	public MpPesquisaCategoriasBean() {
		mpFiltro = new MpCategoriaFilter();
	}
	
	public void excluir() {
		try {
			mpCategorias.remover(mpCategoriaSelecionado);
			mpCategoriasFiltrados.remove(mpCategoriaSelecionado);
			
			MpFacesUtil.addInfoMessage("Categoria... ( " + mpCategoriaSelecionado.getDescricao()  
																		+ " )... excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		mpCategoriasFiltrados = mpCategorias.filtrados(mpFiltro);
	}
	
	public List<MpCategoria> getMpCategoriasFiltrados() {
		return mpCategoriasFiltrados;
	}

	public MpCategoriaFilter getMpFiltro() {
		return mpFiltro;
	}

	public MpCategoria getMpCategoriaSelecionado() {
		return mpCategoriaSelecionado;
	}
	public void setMpCategoriaSelecionado(MpCategoria mpCategoriaSelecionado) {
		this.mpCategoriaSelecionado = mpCategoriaSelecionado;
	}
	
}