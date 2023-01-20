package com.mpxds.mpbasic.controller.sisJuri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.repository.filter.sisJuri.MpTabelaInternaSJFilter;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.sisJuri.MpTabelaInternaSJServiceX;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTabelaInternaSJBeanX implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTabelaInternaSJServiceX mpTabelaInternaSJServiceX;
	
	// ---

	private MpTabelaInternaSJ mpTabelaInternaSJ;
	private MpTabelaInternaSJ mpTabelaInternaSJAnt;
	
	private MpTabelaInternaSJ mpTabelaInternaSJPai;
	private List<MpTabelaInternaSJ> mpPais = new ArrayList<MpTabelaInternaSJ>();

	private MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ;
	private List<MpTipoTabelaInternaSJ> mpTipoTabelaInternaSJList;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	// --- 
		
	public MpCadastroTabelaInternaSJBeanX() {
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
		//
		if (null == this.mpTabelaInternaSJ)
			limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpTabelaInternaSJ) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpTabelaInternaSJ.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		//	
		MpTabelaInternaSJFilter filtroX = new MpTabelaInternaSJFilter();
		
		this.mpPais.clear();
				
		List<MpTabelaInternaSJ> mpPaisTemp = mpTabelaInternaSJs.filtrados(filtroX);
		
		for (MpTabelaInternaSJ mpPaiX : mpPaisTemp) {
			//
			if (mpPaiX.getMpTipoTabelaInternaSJ().getIndPai())
				this.mpPais.add(mpPaiX);
		}		
		//
		this.mpTipoTabelaInternaSJList = Arrays.asList(MpTipoTabelaInternaSJ.values());
		
//		.out.println("MpCadastroTabelaInternaSJBean.inicializar() ( Entrou 000");
		//
		// this.mpTabelaInternaSJ.adicionarItemVazio();
	}
	
	public void salvar() {
		//
		this.mpTabelaInternaSJ = mpTabelaInternaSJServiceX.salvar(this.mpTabelaInternaSJ);
		//
		MpFacesUtil.addInfoMessage("Tabela Interna SJ... salva com sucesso!");
	}

	// -------- Trata Navegação ...

	public void mpFirst() {
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpFirst",
														MpTipoTabelaInternaSJ.TAB_0001.getDescricao(), " "); 
		if (null == this.mpTabelaInternaSJ) {
//			System.out.println("MpCadastroTabelaInternaSJBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroTabelaInternaSJBean.mpFirst() ( Entrou 0001 = " +
//															this.mpTabelaInternaSJ.getParametro());
			
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpTabelaInternaSJ.getMpTipoTabelaInternaSJ()) return;
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao( "mpPrev",
				  	mpTabelaInternaSJ.getMpTipoTabelaInternaSJ().getDescricao(), mpTabelaInternaSJ.getCodigo());
		if (null == this.mpTabelaInternaSJ) {
			this.mpTabelaInternaSJ = this.mpTabelaInternaSJAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		
		this.mpTabelaInternaSJ = new MpTabelaInternaSJ();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpTabelaInternaSJ.getId()) return;
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpTabelaInternaSJ.getId()) return;
		//
		try {
			this.mpTabelaInternaSJs.remover(mpTabelaInternaSJ);
			
			MpFacesUtil.addInfoMessage("Tabela InternaSJ... " + 
												this.mpTabelaInternaSJ.getMpTipoTabelaInternaSJ() + "/"
												+ this.mpTabelaInternaSJ.getCodigo() + " excluída com sucesso.");
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
		
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpTabelaInternaSJ.getMpTipoTabelaInternaSJ()) return;
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpNext", 
				  	mpTabelaInternaSJ.getMpTipoTabelaInternaSJ().getDescricao(), mpTabelaInternaSJ.getCodigo());
		if (null == this.mpTabelaInternaSJ) {
			this.mpTabelaInternaSJ = this.mpTabelaInternaSJAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpEnd",
														MpTipoTabelaInternaSJ.TAB_0007.getDescricao(), "ZZZZZ"); 
		if (null == this.mpTabelaInternaSJ) {
//			System.out.println("MpCadastroTabelaInternaSJBean.mpEnd() ( Entrou 000");
			this.limpar();
		}
//		else
//			System.out.println("MpCadastroTabelaInternaSJBean.mpEnd() ( Entrou 0001 = " +
//															this.mpTabelaInternaSJ.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpTabelaInternaSJ.getId()) return;

		try {
			this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);

			this.mpTabelaInternaSJ = (MpTabelaInternaSJ) this.mpTabelaInternaSJ.clone();
			//
			this.mpTabelaInternaSJ.setId(null);
			
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
		this.mpTabelaInternaSJ = new MpTabelaInternaSJ();

		this.mpTabelaInternaSJ.setTenantId(mpSeguranca.capturaTenantId());
		
		this.mpTabelaInternaSJ.setMpTipoTabelaInternaSJ(null);		
		this.mpTabelaInternaSJ.setCodigo("");		
		this.mpTabelaInternaSJ.setDescricao("");
		//
	}
	
	// ---
	
	public MpTabelaInternaSJ getMpTabelaInternaSJ() { return mpTabelaInternaSJ; }
	public void setMpTabelaInternaSJ(MpTabelaInternaSJ mpTabelaInternaSJ) {
														this.mpTabelaInternaSJ = mpTabelaInternaSJ; }

	public MpTabelaInternaSJ getMpTabelaInternaSJAnt() { return mpTabelaInternaSJAnt; }
	public void setMpTabelaInternaSJAnt(MpTabelaInternaSJ mpTabelaInternaSJAnt) {
		//
		try {
			this.mpTabelaInternaSJAnt = (MpTabelaInternaSJ) this.mpTabelaInternaSJ.clone();
			this.mpTabelaInternaSJAnt.setId(this.mpTabelaInternaSJ.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public MpTabelaInternaSJ getMpTabelaInternaSJPai() { return mpTabelaInternaSJPai; }
	public void setMpTabelaInternaSJPai(MpTabelaInternaSJ mpTabelaInternaSJPai) {
													this.mpTabelaInternaSJPai = mpTabelaInternaSJPai; }
	
	public boolean isEditando() { return this.mpTabelaInternaSJ.getId() != null; }
	
	public List<MpTabelaInternaSJ> getMpPais() { return mpPais; }

	public MpTipoTabelaInternaSJ getMpTipoTabelaInternaSJ() { return mpTipoTabelaInternaSJ; }
	public void setMpTipoTabelaInternaSJ(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
													this.mpTipoTabelaInternaSJ = mpTipoTabelaInternaSJ; }
	public List<MpTipoTabelaInternaSJ> getMpTipoTabelaInternaSJList() { return mpTipoTabelaInternaSJList; }
		
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}