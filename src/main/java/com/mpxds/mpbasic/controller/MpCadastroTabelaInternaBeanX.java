package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpTipoTabelaInterna;
import com.mpxds.mpbasic.repository.MpTabelaInternas;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpTabelaInternaServiceX;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTabelaInternaBeanX implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpTabelaInternas mpTabelaInternas;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTabelaInternaServiceX mpTabelaInternaServiceX;
	
	// ---

	private MpTabelaInterna mpTabelaInterna;
	private MpTabelaInterna mpTabelaInternaAnt;
	
	private MpTabelaInterna mpTabelaInternaPai;
	private List<MpTabelaInterna> mpPais = new ArrayList<MpTabelaInterna>();

	private MpTipoTabelaInterna mpTipoTabelaInterna;
	private List<MpTipoTabelaInterna> mpTipoTabelaInternaList;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	// --- 
		
	public MpCadastroTabelaInternaBeanX() {
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
		//
		if (null == this.mpTabelaInterna)
			limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpTabelaInterna) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpTabelaInterna.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		//	
		this.mpPais = mpTabelaInternas.mpPaiList();
		this.mpTipoTabelaInternaList = Arrays.asList(MpTipoTabelaInterna.values());
		
//		.out.println("MpCadastroTabelaInternaBean.inicializar() ( Entrou 000");
		//
		// this.mpTabelaInterna.adicionarItemVazio();
	}
	
	public void salvar() {
		//
		this.mpTabelaInterna = mpTabelaInternaServiceX.salvar(this.mpTabelaInterna);
		//
		MpFacesUtil.addInfoMessage("Tabela Interna... salva com sucesso!");
	}

	// -------- Trata Navegação ...

	public void mpFirst() {
		this.mpTabelaInterna = this.mpTabelaInternas.porNavegacao("mpFirst",
																MpTipoTabelaInterna.TAB_0001, " "); 
		if (null == this.mpTabelaInterna) {
//			System.out.println("MpCadastroTabelaInternaBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroTabelaInternaBean.mpFirst() ( Entrou 0001 = " +
//															this.mpTabelaInterna.getParametro());
			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpTabelaInterna.getMpTipoTabelaInterna()) return;
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		//
		this.mpTabelaInterna = this.mpTabelaInternas.porNavegacao( "mpPrev",
				  			mpTabelaInterna.getMpTipoTabelaInterna(), mpTabelaInterna.getCodigo());
		if (null == this.mpTabelaInterna) {
			this.mpTabelaInterna = this.mpTabelaInternaAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		
		this.mpTabelaInterna = new MpTabelaInterna();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpTabelaInterna.getId()) return;
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpTabelaInterna.getId()) return;
		//
		try {
			this.mpTabelaInternas.remover(mpTabelaInterna);
			
			MpFacesUtil.addInfoMessage("Tabela Interna... " + 
													this.mpTabelaInterna.getMpTipoTabelaInterna() + "/"
													+ this.mpTabelaInterna.getCodigo()
																		+ " excluída com sucesso.");
			//
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
		
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
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
		this.mpTabelaInterna = this.mpTabelaInternaAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpTabelaInterna.getMpTipoTabelaInterna()) return;
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		//
		this.mpTabelaInterna = this.mpTabelaInternas.porNavegacao("mpNext", 
				  			mpTabelaInterna.getMpTipoTabelaInterna(), mpTabelaInterna.getCodigo());
		if (null == this.mpTabelaInterna) {
			this.mpTabelaInterna = this.mpTabelaInternaAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		this.mpTabelaInterna = this.mpTabelaInternas.porNavegacao("mpEnd",
														MpTipoTabelaInterna.TAB_0007, "ZZZZZ"); 
		if (null == this.mpTabelaInterna) {
//			System.out.println("MpCadastroTabelaInternaBean.mpEnd() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroTabelaInternaBean.mpEnd() ( Entrou 0001 = " +
//															this.mpTabelaInterna.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpTabelaInterna.getId()) return;

		try {
			this.setMpTabelaInternaAnt(this.mpTabelaInterna);

			this.mpTabelaInterna = (MpTabelaInterna) this.mpTabelaInterna.clone();
			//
			this.mpTabelaInterna.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	private void limpar() {
		//
		this.mpTabelaInterna = new MpTabelaInterna();

		this.mpTabelaInterna.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpTabelaInterna.setMpTipoTabelaInterna(null);		
		this.mpTabelaInterna.setCodigo("");		
		this.mpTabelaInterna.setDescricao("");
		//
	}
	
	// ---
	
	public MpTabelaInterna getMpTabelaInterna() { return mpTabelaInterna; }
	public void setMpTabelaInterna(MpTabelaInterna mpTabelaInterna) {
														this.mpTabelaInterna = mpTabelaInterna; }

	public MpTabelaInterna getMpTabelaInternaAnt() { return mpTabelaInternaAnt; }
	public void setMpTabelaInternaAnt(MpTabelaInterna mpTabelaInternaAnt) {
		//
		try {
			this.mpTabelaInternaAnt = (MpTabelaInterna) this.mpTabelaInterna.clone();
			this.mpTabelaInternaAnt.setId(this.mpTabelaInterna.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpTabelaInterna getMpTabelaInternaPai() { return mpTabelaInternaPai; }
	public void setMpTabelaInternaPai(MpTabelaInterna mpTabelaInternaPai) {
													this.mpTabelaInternaPai = mpTabelaInternaPai; }
	
	public boolean isEditando() { return this.mpTabelaInterna.getId() != null; }
	
	public List<MpTabelaInterna> getMpPais() { return mpPais; }

	public MpTipoTabelaInterna getMpTipoTabelaInterna() { return mpTipoTabelaInterna; }
	public void setMpTipoTabelaInterna(MpTipoTabelaInterna mpTipoTabelaInterna) {
													this.mpTipoTabelaInterna = mpTipoTabelaInterna; }
	public List<MpTipoTabelaInterna> getMpTipoTabelaInternaList() { return mpTipoTabelaInternaList; }
		
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}