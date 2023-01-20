package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpItemObjeto;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.enums.MpStatusObjeto;
import com.mpxds.mpbasic.model.enums.MpGrupoMenu;
import com.mpxds.mpbasic.model.enums.MpGrupamentoMenu;
import com.mpxds.mpbasic.model.enums.MpTipoObjeto;
import com.mpxds.mpbasic.model.enums.MpTipoObjetoSistema;
import com.mpxds.mpbasic.model.enums.engreq.MpCardinalidade;
import com.mpxds.mpbasic.model.enums.engreq.MpFormato;
import com.mpxds.mpbasic.repository.MpItemObjetos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpObjetoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroObjetoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpObjetos mpObjetos;

	@Inject
	private MpItemObjetos mpItemObjetos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpObjetoService mpObjetoService;
	
	// ---

	private MpObjeto mpObjeto;
	private MpObjeto mpObjetoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelIte = false;
	
	private String txtModoTela = "";
	private String txtModoItemObjetoDialog = "";

	private MpStatusObjeto mpStatusObjeto;
	private List<MpStatusObjeto> mpStatusObjetoList = new ArrayList<MpStatusObjeto>();

	private MpGrupoMenu mpGrupoMenu;
	private List<MpGrupoMenu> mpGrupoMenuList = new ArrayList<MpGrupoMenu>();

	private MpGrupamentoMenu mpGrupamentoMenu;
	private List<MpGrupamentoMenu> mpGrupamentoMenuList = new ArrayList<MpGrupamentoMenu>();

	private MpTipoObjeto mpTipoObjeto;
	private List<MpTipoObjeto> mpTipoObjetoList = new ArrayList<MpTipoObjeto>();

	private MpTipoObjetoSistema mpTipoObjetoSistema;
	private List<MpTipoObjetoSistema> mpTipoObjetoSistemaList = new ArrayList<MpTipoObjetoSistema>();

	// ---
	
	private MpItemObjeto mpItemObjeto = new MpItemObjeto();
	private List<MpItemObjeto> mpItemObjetoExcluidoList = new ArrayList<MpItemObjeto>();	

	private MpFormato mpFormato;
	private List<MpFormato> mpFormatoList = new ArrayList<MpFormato>();

	private MpCardinalidade mpCardinalidade;
	private List<MpCardinalidade> mpCardinalidadeList = new ArrayList<MpCardinalidade>();
	
	//---
		
	public MpCadastroObjetoBean() {
		//
		if (null == this.mpObjeto)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpObjeto) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpObjeto.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		//
		this.setMpObjetoAnt(this.mpObjeto);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		//
		this.mpStatusObjetoList = Arrays.asList(MpStatusObjeto.values());
		this.mpGrupoMenuList = Arrays.asList(MpGrupoMenu.values());
		this.mpGrupamentoMenuList = Arrays.asList(MpGrupamentoMenu.values());
		this.mpTipoObjetoList = Arrays.asList(MpTipoObjeto.values());
		this.mpTipoObjetoSistemaList = Arrays.asList(MpTipoObjetoSistema.values());
		//
		this.mpFormatoList = Arrays.asList(MpFormato.values());
		this.mpCardinalidadeList = Arrays.asList(MpCardinalidade.values());
	}
	
	public void salvar() {
		//
		this.mpObjeto = mpObjetoService.salvar(this.mpObjeto);
		//
		if (this.mpItemObjetoExcluidoList.size() > 0) {
			for (MpItemObjeto mpItemObjeto : this.mpItemObjetoExcluidoList) {
				//
				if (null == mpItemObjeto.getId()) continue;

				this.mpItemObjetos.remover(mpItemObjeto);
			}
		}
		//	
		MpFacesUtil.addInfoMessage("Objeto... salva com sucesso!");
	}

	// ---
	
	public void alterarMpItemObjeto() {
		//
		this.txtModoItemObjetoDialog = "Edição";
		
		this.indEditavelIte = true;
	}			
		
	public void adicionarMpItemObjetoX() {
		//
		this.txtModoItemObjetoDialog = "Novo";

		this.mpItemObjeto = new MpItemObjeto();
		
		this.mpItemObjeto.setMpObjeto(this.mpObjeto);
		this.mpItemObjeto.setTenantId(mpSeguranca.capturaTenantId());

		this.mpObjeto.getMpItemObjetos().add(this.mpItemObjeto);			
		//
		this.indEditavelIte = true;
	}

	public void removerMpItemObjetoX() {
		//
		try {
			this.mpObjeto.getMpItemObjetos().remove(this.mpItemObjeto);
			
			this.mpItemObjetoExcluidoList.add(this.mpItemObjeto);
			
			MpFacesUtil.addInfoMessage("Item Objeto... " + this.mpItemObjeto.getNome()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpItemObjeto() {
		//
		this.indEditavelIte = false;
		
		this.mpItemObjeto = new MpItemObjeto();
	}			

	public void fecharMpItemObjeto() {
		//
		if (this.txtModoItemObjetoDialog.equals("Novo"))
			this.mpObjeto.getMpItemObjetos().remove(this.mpItemObjeto);
	}			
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpObjeto = this.mpObjetos.porNavegacao("mpFirst", " ", 
													mpSeguranca.capturaTenantId().trim()); 
		if (null == this.mpObjeto)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpObjeto.getNome()) return;
		//
		this.setMpObjetoAnt(this.mpObjeto);
		//
		this.mpObjeto = this.mpObjetos.porNavegacao("mpPrev", mpObjeto.getTransacao(),
													mpSeguranca.capturaTenantId().trim());
		if (null == this.mpObjeto) {
			this.mpObjeto = this.mpObjetoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpObjetoAnt(this.mpObjeto);
		
		this.mpObjeto = new MpObjeto();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpObjeto.getId())
			return;
		//
		this.setMpObjetoAnt(this.mpObjeto);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpObjeto.getId()) return;
		//
		try {
			this.mpObjetos.remover(mpObjeto);
			
			MpFacesUtil.addInfoMessage("Objeto... " + this.mpObjeto.getNome()
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

		this.setMpObjetoAnt(this.mpObjeto);
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
		this.mpObjeto = this.mpObjetoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																	getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpObjeto.getNome()) return;
		//
		this.setMpObjetoAnt(this.mpObjeto);
		//
		this.mpObjeto = this.mpObjetos.porNavegacao("mpNext", mpObjeto.getTransacao(),
													mpSeguranca.capturaTenantId().trim());
		if (null == this.mpObjeto) {
			this.mpObjeto = this.mpObjetoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		this.mpObjeto = this.mpObjetos.porNavegacao("mpEnd", "ZZZZZ",
													mpSeguranca.capturaTenantId().trim()); 
		if (null == this.mpObjeto)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpObjeto.getId()) return;

		try {
			this.setMpObjetoAnt(this.mpObjeto);
			
			this.mpObjeto = (MpObjeto) this.mpObjeto.clone();
			//
			this.mpObjeto.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
	}
	
	// ---
	
	private void limpar() {
		//
		this.mpObjeto = new MpObjeto();
		//
		this.mpObjeto.setNome("");		
		this.mpObjeto.setDescricao("");		

		this.mpObjeto.setMpItemObjetos(new ArrayList<MpItemObjeto>());
		//
		this.mpItemObjeto = new MpItemObjeto();
		this.mpItemObjeto.setNome("");
		//
		this.indEditavelIte = false;
	}
	
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public boolean getIndEditavelIte() { return indEditavelIte; }
	public void setIndEditavelIte(Boolean indEditavelIte) { this.indEditavelIte = indEditavelIte; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public MpObjeto getMpObjeto() { return mpObjeto; }
	public void setMpObjeto(MpObjeto mpObjeto) { this.mpObjeto = mpObjeto; }

	public MpObjeto getMpObjetoAnt() { return mpObjetoAnt; }
	public void setMpObjetoAnt(MpObjeto mpObjetoAnt) {
		//
		try {
			this.mpObjetoAnt = (MpObjeto) this.mpObjeto.clone();
			this.mpObjetoAnt.setId(this.mpObjeto.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }

	// ---
	
	public MpStatusObjeto getMpStatusObjeto() { return mpStatusObjeto; }
	public void setMpStatusObjeto(MpStatusObjeto mpStatusObjeto) { 
																this.mpStatusObjeto = mpStatusObjeto; }
	public List<MpStatusObjeto> getMpStatusObjetoList() { return mpStatusObjetoList; }

	public MpGrupoMenu getMpGrupoMenu() { return mpGrupoMenu; }
	public void setMpGrupoMenu(MpGrupoMenu mpGrupoMenu) { this.mpGrupoMenu = mpGrupoMenu; }
	public List<MpGrupoMenu> getMpGrupoMenuList() { return mpGrupoMenuList; }

	public MpGrupamentoMenu getMpGrupamentoMenu() { return mpGrupamentoMenu; }
	public void setMpGrupamentoMenu(MpGrupamentoMenu mpGrupamentoMenu) {
													this.mpGrupamentoMenu = mpGrupamentoMenu; }
	public List<MpGrupamentoMenu> getMpGrupamentoMenuList() { return mpGrupamentoMenuList; }

	public MpTipoObjeto getMpTipoObjeto() { return mpTipoObjeto; }
	public void setMpTipoObjeto(MpTipoObjeto mpTipoObjeto) { this.mpTipoObjeto = mpTipoObjeto; }
	public List<MpTipoObjeto> getMpTipoObjetoList() { return mpTipoObjetoList; }

	public MpTipoObjetoSistema getMpTipoObjetoSistema() { return mpTipoObjetoSistema; }
	public void setMpTipoObjetoSistema(MpTipoObjetoSistema mpTipoObjetoSistema) {
													this.mpTipoObjetoSistema = mpTipoObjetoSistema; }
	public List<MpTipoObjetoSistema> getMpTipoObjetoSistemaList() { return mpTipoObjetoSistemaList; }

	public MpFormato getMpFormato() { return mpFormato; }
	public void setMpFormato(MpFormato mpFormato) { this.mpFormato = mpFormato; }
	public List<MpFormato> getMpFormatoList() { return mpFormatoList; }

	public MpCardinalidade getMpCardinalidade() { return mpCardinalidade; }
	public void setMpCardinalidade(MpCardinalidade mpCardinalidade) { 
														this.mpCardinalidade = mpCardinalidade; }
	public List<MpCardinalidade> getMpCardinalidadeList() { return mpCardinalidadeList; }
	
	// ---
	
	public boolean isEditando() { return this.mpObjeto.getId() != null; }
	
	public MpItemObjeto getMpItemObjeto() { return mpItemObjeto; }
	public void setMpItemObjeto(MpItemObjeto mpItemObjeto) { this.mpItemObjeto = mpItemObjeto; }
	
	public String getTxtModoItemObjetoDialog() { return txtModoItemObjetoDialog; }
	public void setTxtModoItemObjetoDialog(String txtModoItemObjetoDialog) {
										this.txtModoItemObjetoDialog = txtModoItemObjetoDialog; }
	
}