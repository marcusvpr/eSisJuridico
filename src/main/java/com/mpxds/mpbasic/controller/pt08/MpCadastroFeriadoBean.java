package com.mpxds.mpbasic.controller.pt08;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.pt08.MpFeriado;
import com.mpxds.mpbasic.repository.pt08.MpFeriados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.pt08.MpFeriadoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroFeriadoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpFeriados mpFeriados;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpFeriadoService mpFeriadoService;
	
	// ---

	private MpFeriado mpFeriado = new MpFeriado();
	private MpFeriado mpFeriadoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// ------------------
	
	public MpCadastroFeriadoBean() {
//		System.out.println("MpCadastroFeriadoBean - Entrou 0000 ");
		//
		if (null == this.mpFeriado)
			this.limpar();
	}
	
	public void inicializar() {
		//
		HttpServletRequest request = (HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext().getRequest();
		
		String param = request.getParameter("param");
		if (null == param) param = "null";
//		System.out.println("MpCadastroFeriadoBean.inicializar() - ( param = " + param);
		if (param.equals("login"))
			this.mpNew(); // Posiciona no novo registro !!!
		else
			if (null == this.mpFeriado) {
				this.limpar();
				//
				this.mpEnd(); // Posiciona no último registro !!!
			}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpFeriado.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpFeriadoAnt(this.mpFeriado);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																			getIndBarraNavegacao();
	}
	
	private void limpar() {
		//
		this.mpFeriado = new MpFeriado();
		
		this.mpFeriado.setDataFeriado(new Date());
	}
	
	public void salvar() {
		//
		this.mpFeriado = this.mpFeriadoService.salvar(this.mpFeriado);
		//	
		MpFacesUtil.addInfoMessage("Feriado... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		try {
			this.mpFeriado = this.mpFeriados.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpFeriado)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		if (null == this.mpFeriado.getDataFeriado()) return;
		//
		this.setMpFeriadoAnt(this.mpFeriado);
		//
		this.mpFeriado = this.mpFeriados.porNavegacao("mpPrev", mpFeriado.getDataFeriado());
		if (null == this.mpFeriado) {
			this.mpFeriado = this.mpFeriadoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		this.setMpFeriadoAnt(this.mpFeriado);
		
		this.mpFeriado = new MpFeriado();
		
		this.mpFeriado.setTenantId(mpSeguranca.capturaTenantId());
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		if (null == this.mpFeriado.getId()) return;
		//
		this.setMpFeriadoAnt(this.mpFeriado);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		if (null == this.mpFeriado.getId()) return;
		//
		try {
			this.mpFeriados.remover(mpFeriado);
			
			MpFacesUtil.addInfoMessage("Feriado... " + this.sdf.format(this.mpFeriado.getDataFeriado())
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
		
		this.setMpFeriadoAnt(this.mpFeriado);
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
		this.mpFeriado = this.mpFeriadoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		if (null == this.mpFeriado.getDataFeriado()) return;
		//
		this.setMpFeriadoAnt(this.mpFeriado);
		//
		this.mpFeriado = this.mpFeriados.porNavegacao("mpNext", mpFeriado.getDataFeriado());
		if (null == this.mpFeriado) {
			this.mpFeriado = this.mpFeriadoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		try {
			this.mpFeriado = this.mpFeriados.porNavegacao("mpEnd", sdf.parse("01/01/2099"));
			if (null == this.mpFeriado)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpFeriado.getId()) return;
		
		try {
			this.setMpFeriadoAnt(this.mpFeriado);

			this.mpFeriado = (MpFeriado) this.mpFeriado.clone();
			//
			this.mpFeriado.setId(null);
			
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

	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }

	public MpFeriado getMpFeriado() { return mpFeriado; }
	public void setMpFeriado(MpFeriado mpFeriado) { this.mpFeriado = mpFeriado; }

	public MpFeriado getMpFeriadoAnt() { return mpFeriadoAnt; }
	public void setMpFeriadoAnt(MpFeriado mpFeriadoAnt) {
		//
		try {
			this.mpFeriadoAnt = (MpFeriado) this.mpFeriado.clone();
			this.mpFeriadoAnt.setId(this.mpFeriado.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpFeriado.getId() != null; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}