package com.mpxds.mpbasic.controller.sisJuri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;
import com.mpxds.mpbasic.repository.sisJuri.MpTabelaInternaSJs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.sisJuri.MpTabelaInternaSJServiceX;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTabelaInternaSJBeanY implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpTabelaInternaSJs mpTabelaInternaSJs;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTabelaInternaSJServiceX mpTabelaInternaSJService;
	
	// ---

	private MpTabelaInternaSJ mpTabelaInternaSJ = new MpTabelaInternaSJ();
	private MpTabelaInternaSJ mpTabelaInternaSJAnt;

	private MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ;
	private List<MpTipoTabelaInternaSJ> mpTipoTabelaInternaSJList;
	
	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelSubcat = false;
	
	private String txtModoTela = "";
	private String txtModoSubtabelaInternaSJDialog = "";

	private MpTabelaInternaSJ mpTabelaInternaSJPai = new MpTabelaInternaSJ();
	private MpTabelaInternaSJ mpSubtabelaInternaSJ = new MpTabelaInternaSJ();
	
	private List<MpTabelaInternaSJ> mpTabelaInternaSJsRaizes = new ArrayList<MpTabelaInternaSJ>();
	
	private List<MpTabelaInternaSJ> mpSubtabelaInternaSJs;
	private List<MpTabelaInternaSJ> mpSubtabelaInternaSJExcluidaList = new ArrayList<MpTabelaInternaSJ>();	
		
	// -----------------------
	
	public MpCadastroTabelaInternaSJBeanY() {
		//
		if (null == this.mpTabelaInternaSJ)
			this.limpar();
		//
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
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().getIndBarraNavegacao();
						
		this.mpTipoTabelaInternaSJList = Arrays.asList(MpTipoTabelaInternaSJ.values());
		this.carregarMpTabelaInternaSJs();
	}

	public void carregarMpTabelaInternaSJs() {
		//
		if (this.mpTabelaInternaSJ.getMpTipoTabelaInternaSJ() != null) {
			//
			if (this.mpTabelaInternaSJPai != null) {
				this.carregarMpSubtabelaInternaSJs();
			}
		}
	}
	
	public void carregarMpSubtabelaInternaSJs() {
		//
		this.mpSubtabelaInternaSJs = this.mpTabelaInternaSJs.mpFilhaList(this.mpTabelaInternaSJPai);
	}
	
	public void salvar() {
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJService.salvar(this.mpTabelaInternaSJ);
		//
		if (this.mpSubtabelaInternaSJExcluidaList.size() > 0) {
			for (MpTabelaInternaSJ mpSubtabelaInternaSJ : this.mpSubtabelaInternaSJExcluidaList) {
				//
				if (null == mpSubtabelaInternaSJ.getId()) continue;

				this.mpTabelaInternaSJs.remover(mpSubtabelaInternaSJ);
			}
		}
		//
		MpFacesUtil.addInfoMessage("TabelaInternaSJ... salva com sucesso!");
	}

	// ---
	
	public void alterarMpSubtabelaInternaSJ() {
		//
		this.txtModoSubtabelaInternaSJDialog = "Edição";
		
		this.indEditavelSubcat = true;
	}			
	
	public void adicionarMpSubtabelaInternaSJX() {
		//
		this.txtModoSubtabelaInternaSJDialog = "Novo";
		
		if (this.mpSubtabelaInternaSJ != null) {
			this.mpSubtabelaInternaSJ.setMpTipoTabelaInternaSJ(this.mpTabelaInternaSJ.getMpTipoTabelaInternaSJ());
			this.mpSubtabelaInternaSJ.setMpPai(this.mpTabelaInternaSJ);
			this.mpSubtabelaInternaSJ.setTenantId(mpSeguranca.capturaTenantId());

			this.mpSubtabelaInternaSJ.setCodigo("");
			this.mpSubtabelaInternaSJ.setDescricao("");
			
			this.mpTabelaInternaSJ.getMpFilhas().add(this.mpSubtabelaInternaSJ);
		}
		//
		this.indEditavelSubcat = true;
	}

	public void removerMpSubtabelaInternaSJX() {
		//
		try {
			this.mpTabelaInternaSJ.getMpFilhas().remove(this.mpSubtabelaInternaSJ);
			
			this.mpSubtabelaInternaSJExcluidaList.add(this.mpSubtabelaInternaSJ);
			
			MpFacesUtil.addInfoMessage("SubtabelaInternaSJ... " + this.mpSubtabelaInternaSJ.getDescricao()
																	+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpSubtabelaInternaSJ() {
		//
		this.indEditavelSubcat = false;
		
		this.mpSubtabelaInternaSJ = new MpTabelaInternaSJ();
	}			

	public void fecharMpSubtabelaInternaSJ() {
		//
		if (this.txtModoSubtabelaInternaSJDialog.equals("Novo"))
			this.mpTabelaInternaSJ.getMpFilhas().remove(this.mpSubtabelaInternaSJ);
	}			
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpFirst", 
																MpTipoTabelaInternaSJ.TAB_0000.getDescricao(), " "); 
		if (null == this.mpTabelaInternaSJ) {
//			System.out.println("MpCadastroTabelaInternaSJBean.mpFirst() ( Entrou 000");
			this.limpar();
		}
		else {
			this.mpTabelaInternaSJPai = this.mpTabelaInternaSJ.getMpPai();
			
			this.carregarMpTabelaInternaSJs();
		}
//			System.out.println("MpCadastroTabelaInternaSJBean.mpFirst() ( Entrou 0001 = " +
//															this.mpTabelaInternaSJ.getDescricao());
		//
		this.txtModoTela = "( Início )";
		//
		this.mpComplementaNavegacao();
	}
	
	public void mpPrev() {
		//
		if (null == this.mpTabelaInternaSJ.getDescricao()) return;
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpPrev",
													mpTabelaInternaSJ.getMpTipoTabelaInternaSJ().getDescricao(),
													mpTabelaInternaSJ.getCodigo());
		if (null == this.mpTabelaInternaSJ) {
			this.mpTabelaInternaSJ = this.mpTabelaInternaSJAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		
		this.carregarMpTabelaInternaSJs();
		//
		this.mpComplementaNavegacao();
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
			
			MpFacesUtil.addInfoMessage("Sistema Configuração... " + 
									this.mpTabelaInternaSJ.getDescricao()	+ " excluído com sucesso.");
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

		this.mpTabelaInternaSJAnt = this.mpTabelaInternaSJ;
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
		if (null == this.mpTabelaInternaSJ.getDescricao()) return;
		//
		this.setMpTabelaInternaSJAnt(this.mpTabelaInternaSJ);
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpNext",
													mpTabelaInternaSJ.getMpTipoTabelaInternaSJ().getDescricao(),
													mpTabelaInternaSJ.getCodigo());
		if (null == this.mpTabelaInternaSJ) {
			this.mpTabelaInternaSJ = this.mpTabelaInternaSJAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		
		this.carregarMpTabelaInternaSJs();
		//
		this.mpComplementaNavegacao();
	}
	
	public void mpEnd() {
		//
		this.mpTabelaInternaSJ = this.mpTabelaInternaSJs.porNavegacao("mpEnd", 
														MpTipoTabelaInternaSJ.TAB_1070.getDescricao(), "ZZZZZ"); 
		if (null == this.mpTabelaInternaSJ)
			this.limpar();
		else {
			this.mpTabelaInternaSJPai = this.mpTabelaInternaSJ.getMpPai();
				
			this.carregarMpTabelaInternaSJs();
		}
//		else
//			System.out.println("MpCadastroTabelaInternaSJBean.mpEnd() ( Entrou 0001 = " +
//															this.mpTabelaInternaSJ.getDescricao());
		//
		this.txtModoTela = "( Fim )";
		//
		this.mpComplementaNavegacao();
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
	
	public void mpComplementaNavegacao() {
		//
		if (this.mpTabelaInternaSJ.getMpFilhas().isEmpty())
			this.mpTabelaInternaSJ.setMpFilhas(this.mpTabelaInternaSJs.mpFilhaList(this.mpTabelaInternaSJ));
	}

	private void limpar() {
		//
		this.mpTabelaInternaSJ = new MpTabelaInternaSJ();
	
		this.mpTabelaInternaSJ.setTenantId(mpSeguranca.capturaTenantId());		
		//
		this.mpTabelaInternaSJ.setDescricao("");
		this.mpTabelaInternaSJ.setMpFilhas(new ArrayList<MpTabelaInternaSJ>());
		//
		this.mpTabelaInternaSJPai = null;
		//
		this.mpSubtabelaInternaSJ = new MpTabelaInternaSJ();
		this.mpSubtabelaInternaSJs = new ArrayList<>();
	}
	
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	public boolean getIndEditavelSubcat() { return indEditavelSubcat; }
	public void setIndEditavelSubcat(Boolean indEditavelSubcat) { this.indEditavelSubcat = indEditavelSubcat; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public String getTxtModoSubtabelaInternaSJDialog() { return txtModoSubtabelaInternaSJDialog; }
	public void setTxtModoSubtabelaInternaSJDialog(String txtModoSubtabelaInternaSJDialog) {
										this.txtModoSubtabelaInternaSJDialog = txtModoSubtabelaInternaSJDialog; }
	
	public MpTabelaInternaSJ getMpTabelaInternaSJ() { return mpTabelaInternaSJ; }
	public void setMpTabelaInternaSJ(MpTabelaInternaSJ mpTabelaInternaSJ) {
		this.mpTabelaInternaSJ = mpTabelaInternaSJ;		
		
		if (this.mpTabelaInternaSJ != null) {
			this.mpTabelaInternaSJPai = this.mpTabelaInternaSJ.getMpPai();
		}
	}

	public MpTabelaInternaSJ getMpTabelaInternaSJAnt() { return mpTabelaInternaSJAnt; }
	public void setMpTabelaInternaSJAnt(MpTabelaInternaSJ mpTabelaInternaSJAnt) {
		try {
			this.mpTabelaInternaSJAnt = (MpTabelaInternaSJ) this.mpTabelaInternaSJ.clone();
			this.mpTabelaInternaSJAnt.setId(this.mpTabelaInternaSJ.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public List<MpTabelaInternaSJ> getMpTabelaInternaSJsRaizes() { return mpTabelaInternaSJsRaizes; }

	@NotNull
	public MpTabelaInternaSJ getMpTabelaInternaSJPai() { return mpTabelaInternaSJPai; }
	public void setMpTabelaInternaSJPai(MpTabelaInternaSJ mpTabelaInternaSJPai) {	
																this.mpTabelaInternaSJPai = mpTabelaInternaSJPai; }
	public List<MpTabelaInternaSJ> getMpSubtabelaInternaSJs() {	return mpSubtabelaInternaSJs; }
	
	public MpTabelaInternaSJ getMpSubtabelaInternaSJ() { return mpSubtabelaInternaSJ; }
	public void setMpSubtabelaInternaSJ(MpTabelaInternaSJ mpSubtabelaInternaSJ) { 
																this.mpSubtabelaInternaSJ = mpSubtabelaInternaSJ; }
	
	public MpTipoTabelaInternaSJ getMpTipoTabelaInternaSJ() { return mpTipoTabelaInternaSJ; }
	public void setMpTipoTabelaInternaSJ(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
													this.mpTipoTabelaInternaSJ = mpTipoTabelaInternaSJ; }
	public List<MpTipoTabelaInternaSJ> getMpTipoTabelaInternaSJList() { return mpTipoTabelaInternaSJList; }
		
	public boolean isEditando() { return this.mpTabelaInternaSJ.getId() != null; }

}