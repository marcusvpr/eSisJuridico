package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DualListModel;

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpGrupoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroGrupoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpGrupos mpGrupos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpGrupoService mpGrupoService;

	@Inject
	private MpObjetos service;
	
	// ---

	private MpGrupo mpGrupo;
	private MpGrupo mpGrupoAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indDragDropSplit = false; // Split ...
	
	private String txtModoTela = "";

	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList = new ArrayList<MpStatus>();
		
	private MpObjeto mpObjetoLinhaEditavel;
	
	// ===
    
    private List<MpObjeto> mpObjetoList = new ArrayList<MpObjeto>();
    private List<MpObjeto> droppedMpObjetos = new ArrayList<MpObjeto>();
     
    private MpObjeto selectedMpObjeto;
    
    //MpObjetos
    private DualListModel<MpObjeto> mpDualObjetos = new DualListModel<MpObjeto>(); 
    
    private MpObjeto mpObjeto = new MpObjeto();
    
	//---
		
    public MpCadastroGrupoBean() {
    	//
		if (null == this.mpGrupo)
			this.limpar();
		else
			this.mpTrataMpGrupo();
		//
	}
	
	public void inicializar() {
		//
		if (null == this.mpGrupo) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		} else
			this.mpTrataMpGrupo();
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpGrupo.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpGrupoAnt(this.mpGrupo);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		//
		this.mpStatusList = Arrays.asList(MpStatus.values());
		
//		this.mpGrupo.adicionarMpObjetoVazio();

//		this.mpObjetoList = this.service.listaTodos();
		//
	}
	
	private void limpar() {
		//
		this.mpGrupo = new MpGrupo();
		//
		this.mpGrupo.setNome("");		
		this.mpGrupo.setDescricao("");		
	}
	
	public void salvar() {
		//
		if (this.indDragDropSplit)
			this.mpGrupo.setMpObjetos(this.droppedMpObjetos);
		else
			this.mpGrupo.setMpObjetos(this.mpDualObjetos.getTarget());
		//
		this.mpGrupo = mpGrupoService.salvar(this.mpGrupo);
		//
		MpFacesUtil.addInfoMessage("Grupo... salva com sucesso!");

		this.mpGrupo.adicionarMpObjetoVazio();
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpGrupo = this.mpGrupos.porNavegacao("mpFirst", " "); 
		if (null == this.mpGrupo)
			this.limpar();
		else
			this.mpTrataMpGrupo();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpGrupo.getNome())	return;
		//
		this.setMpGrupoAnt(this.mpGrupo);
		//
		this.mpGrupo = this.mpGrupos.porNavegacao("mpPrev", mpGrupo.getNome());
		if (null == this.mpGrupo) {
			this.mpGrupo = this.mpGrupoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.mpTrataMpGrupo();
	}

	public void mpNew() {
		//
		this.setMpGrupoAnt(this.mpGrupo);
		
		this.mpGrupo = new MpGrupo();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpGrupo.getId()) return;
		//
		this.setMpGrupoAnt(this.mpGrupo);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpGrupo.getId()) return;
		//
		try {
			this.mpGrupos.remover(mpGrupo);
			//
			MpFacesUtil.addInfoMessage("Grupo... " + this.mpGrupo.getNome()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}
		//
		this.setMpGrupoAnt(this.mpGrupo);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																	getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpGrupo = this.mpGrupoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																	getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpGrupo.getNome()) return;
		//
		this.setMpGrupoAnt(this.mpGrupo);
		//
		this.mpGrupo = this.mpGrupos.porNavegacao("mpNext", mpGrupo.getNome());
		if (null == this.mpGrupo) {
			this.mpGrupo = this.mpGrupoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.mpTrataMpGrupo();
	}
	
	public void mpEnd() {
		//
		this.mpGrupo = this.mpGrupos.porNavegacao("mpEnd", "ZZZZZ"); 
		if (null == this.mpGrupo)
			this.limpar();
		else
			this.mpTrataMpGrupo();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpGrupo.getId()) return;

		try {
			this.setMpGrupoAnt(this.mpGrupo);

			this.mpGrupo = (MpGrupo) this.mpGrupo.clone();
			//
			this.mpGrupo.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	public void mpTrataMpGrupo() {
		//
		this.droppedMpObjetos = this.mpGrupo.getMpObjetos();
		//
		// Trata lista principal removendo objetos já selecionados !
		//
		this.mpObjetoList = this.service.mpObjetoList("0");
		
		this.mpObjetoList.removeAll(droppedMpObjetos);
		//
		List<MpObjeto> mpObjetosSource = this.mpObjetos.mpObjetoList("0");

		List<MpObjeto> mpObjetosTarget = this.mpGrupo.getMpObjetos();
	    //
	    mpObjetosSource.removeAll(mpObjetosTarget);
	    
        this.mpDualObjetos = new DualListModel<MpObjeto>(mpObjetosSource, mpObjetosTarget);		
	}
	
	// ---

	public MpGrupo getMpGrupo() { return mpGrupo; }
	public void setMpGrupo(MpGrupo mpGrupo) { this.mpGrupo = mpGrupo; }

	public MpGrupo getMpGrupoAnt() { return mpGrupoAnt; }
	public void setMpGrupoAnt(MpGrupo mpGrupoAnt) { 
		try {
			this.mpGrupoAnt = (MpGrupo) this.mpGrupo.clone();
			this.mpGrupoAnt.setId(this.mpGrupo.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	// -=-=-

	public boolean isEditando() {
		//
		return this.mpGrupo.getId() != null;
	}

	public List<MpObjeto> completarMpObjeto(String nome) {
		//
		return this.mpObjetos.porNome(nome, "0");
	}	
	
	// --- Drag and Drop ...

    public void onMpObjetoDrop(DragDropEvent ddEvent) {
    	//
        MpObjeto mpObjeto = ((MpObjeto) ddEvent.getData());
  
        this.droppedMpObjetos.add(mpObjeto);
        
        this.mpObjetoList.remove(mpObjeto);
    }
     
    public void setService(MpObjetos service) { this.service = service; }
 
    public List<MpObjeto> getMpObjetoList() { return mpObjetoList; }
    public List<MpObjeto> getDroppedMpObjetos() { return droppedMpObjetos; }    
 
    public MpObjeto getSelectedMpObjeto() { return selectedMpObjeto; } 
    public void setSelectedMpObjeto(MpObjeto selectedMpObjeto) {
        													this.selectedMpObjeto = selectedMpObjeto; }
    
    public MpObjeto getMpObjeto() { return mpObjeto; } 
    public void setMpObjeto(MpObjeto mpObjeto) { this.mpObjeto = mpObjeto; }
    
	public void mpDeleteObjeto() { 
		//
        this.droppedMpObjetos.remove(this.selectedMpObjeto);
        
        this.mpObjetoList.add(this.selectedMpObjeto);
        //
        MpFacesUtil.addInfoMessage(this.selectedMpObjeto.getNome() + " removido com sucesso.");
	}

    public DualListModel<MpObjeto> getMpDualObjetos() { return mpDualObjetos;  }
 
    public void setMpDualObjetos(DualListModel<MpObjeto> mpDualObjetos) {
        														this.mpDualObjetos = mpDualObjetos; }	
	// -=-=-
	

	public MpStatus getMpStatus() { return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) {
														this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { 
														this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public Boolean getIndDragDropSplit() { return indDragDropSplit; }
	public void setIndDragDropSplit(Boolean indDragDropSplit) {
													this.indDragDropSplit = indDragDropSplit; }
	
	public MpObjeto getMpObjetoLinhaEditavel() { return mpObjetoLinhaEditavel; }
	public void setMpObjetoLinhaEditavel(MpObjeto mpObjetoLinhaEditavel) {
											this.mpObjetoLinhaEditavel = mpObjetoLinhaEditavel; }

}