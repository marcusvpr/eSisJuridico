package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpAlerta;
import com.mpxds.mpbasic.model.MpCalendario;
import com.mpxds.mpbasic.model.enums.MpCalendarioRepetir;
import com.mpxds.mpbasic.repository.MpCalendarios;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaService;
import com.mpxds.mpbasic.service.MpCalendarioService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.cdi.MpCDIServiceLocator;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroCalendarioBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpCalendarios mpCalendarios;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpCalendarioService mpCalendarioService;

	@Inject
	private MpAlertaService mpAlertaService;
	
	// ---

	private MpCalendario mpCalendario = new MpCalendario();
	private MpCalendario mpCalendarioAnt;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	private Boolean indShowDatas = true;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private MpCalendarioRepetir mpCalendarioRepetir = MpCalendarioRepetir.UNICO;
	private List<MpCalendarioRepetir> mpCalendarioRepetirList;
	
	// ------------------
	
	public MpCadastroCalendarioBean() {
//		System.out.println("MpCadastroCalendarioBean - Entrou 0000 ");
		//
		if (null == this.mpCalendario)
			this.limpar();
		//
		if (null == mpSeguranca)
			this.mpSeguranca = MpCDIServiceLocator.getBean(MpSeguranca.class);
	}
	
	public void inicializar() {
		//
		if (null == this.mpCalendario) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpCalendario.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpCalendarioAnt(this.mpCalendario);
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		//
		this.mpCalendarioRepetirList = Arrays.asList(MpCalendarioRepetir.values());
		//
		if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
			this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuarioTenant().
																		getIndBarraNavegacao();
		else
			this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	public void salvar() {
		//
		if (null == this.mpCalendario.getMpCalendarioRepetir())
			this.mpCalendario.setMpCalendarioRepetir(MpCalendarioRepetir.UNICO);

		// Trata Alerta ---------------------------
		MpAlerta mpAlerta = this.mpAlertaService.salvar(this.mpCalendario.getMpAlerta());
		this.mpCalendario.setMpAlerta(mpAlerta);
		// Trata Alerta ---------------------------

		//
		this.mpCalendario = this.mpCalendarioService.salvar(this.mpCalendario);

		// this.limpar();

		MpFacesUtil.addInfoMessage("Calendario... salvo com sucesso!");
	}
	
	public void trataShowDatas() {
		//
		if (this.getIndShowDatas()) {
			this.setIndShowDatas(false);
			//
			Calendar calendar = Calendar.getInstance();
			//
			if (null == this.mpCalendario.getDataMovimento())
				calendar.setTime(new Date());
			else
				calendar.setTime(this.mpCalendario.getDataMovimento());
			//
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			//
			this.mpCalendario.setDataMovimento(calendar.getTime());
			//
			this.mpCalendario.setDataFimMovimento(this.mpCalendario.getDataMovimento());
		} else
			this.setIndShowDatas(true);
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		try {
			this.mpCalendario = this.mpCalendarios.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpCalendario)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		if (null == this.mpCalendario.getDataMovimento()) return;
		//
		this.setMpCalendarioAnt(this.mpCalendario);
		//
		this.mpCalendario = this.mpCalendarios.porNavegacao("mpPrev", mpCalendario.getDataMovimento());
		if (null == this.mpCalendario) {
			this.mpCalendario = this.mpCalendarioAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpCalendarioAnt(this.mpCalendario);
		
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
		if (null == this.mpCalendario.getId()) return;
		//
		this.setMpCalendarioAnt(this.mpCalendario);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpCalendario.getId()) return;
		//
		try {
			this.mpCalendarios.remover(mpCalendario);
			
			MpFacesUtil.addInfoMessage("Calendario... " + 
										this.sdf.format(this.mpCalendario.getDataMovimento())
																	+ " excluído com sucesso.");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		if (null == this.mpCalendario.getDataFimMovimento())
			assert(true); // nop
		else
			if (this.mpCalendario.getDataMovimento().
												after(this.mpCalendario.getDataFimMovimento())) {
				MpFacesUtil.addInfoMessage("Data Inicial maior que Data Final!");
				return;
			}
		//
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());
			return;
		}

		this.setMpCalendarioAnt(this.mpCalendario);
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
		this.mpCalendario = this.mpCalendarioAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpCalendario.getDataMovimento()) return;
		//
		this.setMpCalendarioAnt(this.mpCalendario);
		//
		this.mpCalendario = this.mpCalendarios.porNavegacao("mpNext", 
															mpCalendario.getDataMovimento());
		if (null == this.mpCalendario) {
			this.mpCalendario = this.mpCalendarioAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		try {
			this.mpCalendario = this.mpCalendarios.porNavegacao("mpEnd", sdf.parse("01/01/2099"));
			if (null == this.mpCalendario)
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
		if (null == this.mpCalendario.getId()) return;

		try {
			this.setMpCalendarioAnt(this.mpCalendario);
			
			this.mpCalendario = (MpCalendario) this.mpCalendario.clone();
			//
			this.mpCalendario.setId(null);
			
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
		this.mpCalendario = new MpCalendario();
		this.mpCalendario.setIndAtivo(true);
		//
		this.mpCalendario.setDataMovimento(new Date());
		this.mpCalendario.setDataFimMovimento(new Date());
		//
		MpAlerta mpAlertaX = new MpAlerta(false, false, false, false, false, false, false);
		
		try {
			if (null == this.mpSeguranca.getMpUsuarioLogado().getMpUsuario())
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
														getMpUsuarioTenant().getMpAlerta().clone();
			else
				mpAlertaX = (MpAlerta) this.mpSeguranca.getMpUsuarioLogado().
														getMpUsuario().getMpAlerta().clone();
			//
//			System.out.println("MpCadastroAtividadeBean.limpar() - ( MpAlerta = " +
//																mpAlertaX.getConfiguracao());
			//
		} catch (CloneNotSupportedException e) {
				e.printStackTrace();
		}
		//
		this.mpCalendario.setMpAlerta(mpAlertaX);		
	}
	
	// ---

	public MpCalendario getMpCalendario() { return mpCalendario; }
	public void setMpCalendario(MpCalendario mpCalendario) { this.mpCalendario = mpCalendario; }

	public MpCalendario getMpCalendarioAnt() { return mpCalendarioAnt; }
	public void setMpCalendarioAnt(MpCalendario mpCalendarioAnt) {
		try {
			this.mpCalendarioAnt = (MpCalendario) this.mpCalendario.clone();
			this.mpCalendarioAnt.setId(this.mpCalendario.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpCalendario.getId() != null; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public boolean getIndShowDatas() { return indShowDatas; }
	public void setIndShowDatas(Boolean indShowDatas) { this.indShowDatas = indShowDatas; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public MpCalendarioRepetir getMpCalendarioRepetir() { return mpCalendarioRepetir; }
	public void setMpCalendarioRepetir(MpCalendarioRepetir mpCalendarioRepetir) {
													this.mpCalendarioRepetir = mpCalendarioRepetir;	}
	public List<MpCalendarioRepetir> getMpCalendarioRepetirList() { return mpCalendarioRepetirList;	}

}