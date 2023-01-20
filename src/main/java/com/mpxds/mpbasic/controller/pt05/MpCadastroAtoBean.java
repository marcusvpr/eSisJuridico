package com.mpxds.mpbasic.controller.pt05;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpSistemaConfig;
import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.model.pt05.MpCustasComposicao;
import com.mpxds.mpbasic.model.pt05.MpAtoComposicao;

import com.mpxds.mpbasic.repository.pt05.MpAtos;
import com.mpxds.mpbasic.repository.pt05.MpCustasComposicaos;
import com.mpxds.mpbasic.repository.MpSistemaConfigs;
import com.mpxds.mpbasic.repository.pt05.MpAtoComposicaos;

import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt05.MpAtoService;
import com.mpxds.mpbasic.exception.MpNegocioException;

import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroAtoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAtos mpAtos;

	@Inject
	private MpSeguranca mpSeguranca;
	
	@Inject
	private MpAtoComposicaos mpAtoComposicaos;

	@Inject
	private MpAtoService mpAtoService;

	@Inject
	private MpCustasComposicaos mpCustasComposicaos;

	@Inject
	private MpSistemaConfigs mpSistemaConfigs;

	// --- 
	
	private MpAto mpAto = new MpAto();
	private MpAto mpAtoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private Boolean indEditavelValorAto = true;
	private Boolean indEditavelMpAtoComposicao = false;
	private Boolean indEditavelMpCustasComposicao = false;
	
	private String txtModoTela = "";
	private String txtModoMpAtoComposicaoDialog = "";
	private String txtModoMpCustasComposicaoDialog = "";	
	//	
	private BigDecimal valorAto = BigDecimal.ZERO;
	//	
	private Integer scOficVariavel = 2;
	private Integer scOficLei3217 = 20;
	private Integer scOficLei4664 = 5;
	private Integer scOficLei111 = 5;
	private Integer scOficLei6281 = 4;
	//
	private MpAtoComposicao mpAtoComposicao = new MpAtoComposicao();
	private List<MpAtoComposicao> mpAtoComposicaoExcluidoList = new ArrayList<MpAtoComposicao>();

	private MpCustasComposicao mpCustasComposicao = new MpCustasComposicao();
	private List<MpCustasComposicao> mpCustasComposicaoList = new ArrayList<MpCustasComposicao>();
	
	// ---------------------

	public MpCadastroAtoBean() {
		//
		if (null == this.mpAto)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpAto) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpAto.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpAtoAnt(this.mpAto);
		// ---
		this.mpCustasComposicaoList = mpCustasComposicaos.byTabItemSubList();
		//
		// Trata Configuração Sistema ...
		// ===============================
		String msg = "";
		
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficVariavel");
		if (null == mpSistemaConfig)
			msg = msg + "<oficVariavel>";
		else
			this.scOficVariavel = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei3217");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei3217>";
		else
			this.scOficLei3217 = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei111");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei111>";
		else
			this.scOficLei111 = mpSistemaConfig.getValorN();
		mpSistemaConfig = this.mpSistemaConfigs.porParametro("oficLei4664");
		if (null == mpSistemaConfig)
			msg = msg + "<oficLei4664>";
		else
			this.scOficLei4664 = mpSistemaConfig.getValorN();
		
		if (!msg.isEmpty())
			MpFacesUtil.addInfoMessage("Alerta Sistema Configuração : ( " + msg
															+ " não encontrados!");
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}

	public void limpaValorAto() {
		//
		this.mpAto.getMpValorAto().setValorAtoEmolumento(null);
		this.mpAto.getMpValorAto().setValorAtoVariavel(null);
		this.mpAto.getMpValorAto().setValorAtoLei3217(null);
		this.mpAto.getMpValorAto().setValorAtoLei4664(null);
		this.mpAto.getMpValorAto().setValorAtoLei111(null);
		this.mpAto.getMpValorAto().setValorAtoLei3761(null);
		this.mpAto.getMpValorAto().setValorAtoLei590(null);
		this.mpAto.getMpValorAto().setValorAtoLei6281(null);
		this.mpAto.getMpValorAto().setValorAtoDistribuicao(null);
	}
				
	public void salvar() {
		//
		this.mpAto = this.mpAtoService.salvar(this.mpAto);
		//
//		System.out.println("MpCadastroAtoBean.salvar() - ( " + this.mpAto.getMpAtoTipos().size());		
		//
		if (this.mpAtoComposicaoExcluidoList.size() > 0) {
			for (MpAtoComposicao mpAtoComposicaoX : this.mpAtoComposicaoExcluidoList) {
				//
				if (null == mpAtoComposicaoX.getId()) continue;

				this.mpAtoComposicaos.remover(mpAtoComposicaoX);
			}
		}
		//
		MpFacesUtil.addInfoMessage("Ato... salvo com sucesso!");
	}
	
	public void salvarValorAto() {
		//
		this.mpAto.setIndAlteraValorAto(true);
		
		this.setValorAto(this.mpAto.getMpValorAto().calcularValorTotal());
		//
//		System.out.println("MpCadastroBean.alterarMpAtoTipos() - ( Valor = " + 
//				this.getValorAto() + " / Distrib. = " + this.mpAtoTipoDistribuicao.getValor());
	}			
	
	public void alterarValorAto() {
		//
		this.indEditavelValorAto = true;
		//
	}			
	
	public void alterarMpAtoComposicao() {
		//
		this.txtModoMpAtoComposicaoDialog = "Edição";

		this.indEditavelMpAtoComposicao = true;
		//
		System.out.println("MpCadastroAtoBean.alterarMpAtoComposicao() - ( " +
																this.mpAtoComposicao.getId());
	}			
	
	public void adicionarMpAtoComposicao() {
		//
		this.txtModoMpAtoComposicaoDialog = "Novo";
		
		this.mpAtoComposicao = new MpAtoComposicao();
		
		this.mpAtoComposicao.setMpAto(this.mpAto);
		//
//		if (this.mpAto.getMpAtoComposicaos().size() > 0) ???
			this.mpAto.getMpAtoComposicaos().add(this.mpAtoComposicao);
		//
		this.indEditavelMpAtoComposicao = true;
		//
		System.out.println("MpCadastroAtoBean.adicionarMpAtoComposicao() - ( " +
																this.mpAtoComposicao.getId());
	}

	public void removerMpAtoComposicao() {
		//
		try {
			this.mpAto.getMpAtoComposicaos().remove(this.mpAtoComposicao);
			//
			this.calculaValorAto();
			//
			this.mpAtoComposicaoExcluidoList.add(this.mpAtoComposicao);
			
			MpFacesUtil.addInfoMessage("Ato Composição " + this.mpAtoComposicao.getComplemento()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpAtoComposicao() {
		//
		this.indEditavelMpAtoComposicao = false;
		//
		this.calculaValorAto();
		//
//		System.out.println("MpCadastroAtoBean.salvarMpAtoComposicao() - (Ato.atoComposicao.size = " +
//														this.mpAto.getMpAtoComposicaos().size());
	}			

	public void calculaValorAto() {
		//
		if (this.mpAto.getIndAlteraValorAto())
			assert(true); // nop
		else
			this.mpAto.getMpValorAto().zerarValorTotal();
		// 
		if (null == this.mpAto.getId())
			assert(true); // nop
		else {
			this.mpAto = mpAtoService.tratarValorTotal(this.mpAto, scOficVariavel, scOficLei3217, scOficLei4664,
															   		scOficLei111, scOficLei6281);
			//
			this.setValorAto(mpAto.getMpValorAto().calcularValorTotal());
		}
		//
	}			
		
	// -------------------------------- //
	// -------- Trata Navegação ------- //
	// -------------------------------- //

	public void mpFirst() {
		//
		this.mpAto = this.mpAtos.porNavegacao("mpFirst", " ", " "); 
		if (null == this.mpAto)
			this.limpar();
		else
			this.calculaValorAto();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpAto.getCodigo()) return;
		//
		this.setMpAtoAnt(this.mpAto);
		//
		this.mpAto = this.mpAtos.porNavegacao("mpPrev", mpAto.getCodigo(),
																		mpAto.getSequencia());
		if (null == this.mpAto) {
			this.mpAto = this.mpAtoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
		//
		this.calculaValorAto();
	}

	public void mpNew() {
		//
		this.setMpAtoAnt(this.mpAto);
		
		this.limpar();
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpAto.getId()) return;
		//
		this.setMpAtoAnt(this.mpAto);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpAto.getId()) return;
		//
		try {
			this.mpAtos.remover(mpAto);
			
			MpFacesUtil.addInfoMessage("Ato... " + this.mpAto.getCodigo()
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

		this.setMpAtoAnt(this.mpAto);
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
		this.mpAto = this.mpAtoAnt;		
		//
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
		//
		this.calculaValorAto();
	}
	
	public void mpNext() {
		//
		if (null == this.mpAto.getCodigo()) return;
		//
		this.setMpAtoAnt(this.mpAto);
		//
		this.mpAto = this.mpAtos.porNavegacao("mpNext", mpAto.getCodigo(),
																		mpAto.getSequencia());
		if (null == this.mpAto) {
			this.mpAto = this.mpAtoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
		//
		this.calculaValorAto();
	}
	
	public void mpEnd() {
		//
		this.mpAto = this.mpAtos.porNavegacao("mpEnd", "ZZZZ", "Z"); 
		if (null == this.mpAto)
			this.limpar();
		else
			this.calculaValorAto();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpAto.getId()) return;

		try {
			this.setMpAtoAnt(this.mpAto);

			this.mpAto = (MpAto) this.mpAto.clone();
			//
			this.mpAto.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}

	public void mpHelp() {
		//
		this.mpObjetoHelp = mpSeguranca.mpHelp(this.getClass().getSimpleName());
		//
	}	
	
	// ---
	
	private void limpar() {
		//
		this.mpAto = new MpAto();
		
		this.mpAto.setCodigo("");
		this.mpAto.setMpAtoComposicaos(new ArrayList<MpAtoComposicao>());
		//
		this.limpaValorAto();
		
		this.valorAto = BigDecimal.ZERO;
		//
		this.mpAtoComposicao = new MpAtoComposicao();
		//		
		this.indEditavelMpAtoComposicao = false;
		this.indEditavelMpCustasComposicao = false;
	}
		
	// ---

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	
	public BigDecimal getValorAto() { return valorAto; }
	public void setValorAto(BigDecimal valorAto) {	this.valorAto = valorAto; }
	
	public MpAto getMpAto() { return mpAto; }
	public void setMpAto(MpAto mpAto) {	this.mpAto = mpAto; }

	public MpAto getMpAtoAnt() { return mpAtoAnt; }
	public void setMpAtoAnt(MpAto mpAtoAnt) {	
		//
		try {
			this.mpAtoAnt = (MpAto) this.mpAto.clone();
			this.mpAtoAnt.setId(this.mpAto.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpAtoComposicao getMpAtoComposicao() {return mpAtoComposicao; }
	public void setMpAtoComposicao(MpAtoComposicao mpAtoComposicao) { 
														this.mpAtoComposicao = mpAtoComposicao; }

	public MpCustasComposicao getMpCustasComposicao() {return mpCustasComposicao; }
	public void setMpCustasComposicao(MpCustasComposicao mpCustasComposicao) { 
												this.mpCustasComposicao = mpCustasComposicao; }
	public List<MpCustasComposicao> getMpCustasComposicaoList() { return mpCustasComposicaoList; }

	public boolean getIndEditavelValorAto() { return indEditavelValorAto; }
	public void setIndEditavelValorAto(Boolean indEditavelValorAto) { 
												this.indEditavelValorAto = indEditavelValorAto; }

	public boolean getIndEditavelMpAtoComposicao() { return indEditavelMpAtoComposicao; }
	public void setIndEditavelMpAtoComposicao(Boolean indEditavelMpAtoComposicao) { 
								this.indEditavelMpAtoComposicao = indEditavelMpAtoComposicao; }

	public boolean getIndEditavelMpCustasComposicao() { return indEditavelMpCustasComposicao; }
	public void setIndEditavelMpCustasComposicao(Boolean indEditavelMpCustasComposicao) { 
							this.indEditavelMpCustasComposicao = indEditavelMpCustasComposicao; }
	
	public String getTxtModoMpAtoComposicaoDialog() { return txtModoMpAtoComposicaoDialog; }
	public void setTxtModoMpAtoComposicaoDialog(String txtModoMpAtoComposicaoDialog) {
							this.txtModoMpAtoComposicaoDialog = txtModoMpAtoComposicaoDialog; }
	
	public String getTxtModoMpCustasComposicaoDialog() { return txtModoMpCustasComposicaoDialog; }
	public void setTxtModoMpCustasComposicaoDialog(String txtModoMpCustasComposicaoDialog) {
						this.txtModoMpCustasComposicaoDialog = txtModoMpCustasComposicaoDialog; }
	
	// --- 
	
	public boolean isEditando() { return this.mpAto.getId() != null; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}