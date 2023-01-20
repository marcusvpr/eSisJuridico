package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.repository.MpUsuarios;
import com.mpxds.mpbasic.repository.filter.MpUsuarioFilter;
import com.mpxds.mpbasic.service.MpUsuarioService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpLiberaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpUsuarios mpUsuarios;

	@Inject
	private MpUsuarioService mpUsuarioService;
	
	private MpUsuarioFilter mpFiltro;
	
	private List<MpUsuario> mpUsuariosFiltrados = new ArrayList<MpUsuario>();
	
	private MpUsuario mpUsuarioSelecionado = new MpUsuario();
	
	private LazyDataModel<MpUsuario> model;

	// --------------------------
	
	public MpLiberaUsuariosBean() {
		//
		mpFiltro = new MpUsuarioFilter();

		model = new LazyDataModel<MpUsuario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<MpUsuario> load(int first, int pageSize, String sortField,
											SortOrder sortOrder, Map<String, Object> filters) {
				mpFiltro.setStatus("BLOQUEADO");
				//
				mpFiltro.getMpFilterOrdenacao().setPrimeiroRegistro(first);
				mpFiltro.getMpFilterOrdenacao().setQuantidadeRegistros(pageSize);
				mpFiltro.getMpFilterOrdenacao().setPropriedadeOrdenacao(sortField);
				mpFiltro.getMpFilterOrdenacao().setAscendente(SortOrder.ASCENDING.equals(
																					sortOrder));
				setRowCount(mpUsuarios.quantidadeFiltrados(mpFiltro));

//				System.out.println("MpLiberaUsuariosBean - 000 (" + 
//													mpUsuarios.filtrados(mpFiltro).size());
//				
				return mpUsuarios.filtrados(mpFiltro);
			}
		};
	}

	public MpUsuarioFilter getMpFiltro() {
		return mpFiltro;
	}

	public LazyDataModel<MpUsuario> getModel() {
		return model;
	}

	public void liberar() {
		//
//		System.out.println("MpLiberaUsuariosBean.liberar() (" + mpUsuarioSelecionado.getNome());
//		//
		try {
			mpUsuarioSelecionado.setMpStatus(MpStatus.ATIVO);
			
			mpUsuarioService.salvar(mpUsuarioSelecionado);
			
			mpUsuariosFiltrados.remove(mpUsuarioSelecionado);
			
			MpFacesUtil.addInfoMessage("Usuário : ( " + mpUsuarioSelecionado.getLogin() 
																+ " )... Liberado com sucesso!");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	// ---
	
	public MpUsuario getMpUsuarioSelecionado() { return mpUsuarioSelecionado; }
	public void setMpUsuarioSelecionado(MpUsuario mpUsuarioSelecionado) {
												this.mpUsuarioSelecionado = mpUsuarioSelecionado; }

}