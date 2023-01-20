package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpTurno;
import com.mpxds.mpbasic.model.enums.MpTipoJornada;
import com.mpxds.mpbasic.repository.MpTurnos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpTurnoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroTurnoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpTurnos mpTurnos;

	@Inject
	private MpSeguranca mpSeguranca;

	@Inject
	private MpTurnoService mpTurnoService;
	
	// ---

	private MpTurno mpTurno = new MpTurno();
	private MpTurno mpTurnoAnt;
	
	private MpObjeto mpObjetoHelp;

	private Boolean indEditavel = true;
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private MpTipoJornada mpTipoJornada;
	private List<MpTipoJornada> mpTipoJornadaList;
	
	// ------------------
	
	public MpCadastroTurnoBean() {
//		System.out.println("MpCadastroTurnoBean - Entrou 0000 ");
		//
		if (null == this.mpTurno)
			this.limpar();
	}
	
	public void inicializar() {
		//
		if (null == this.mpTurno) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpTurno.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpTurnoAnt(this.mpTurno);
		//
		this.mpTipoJornadaList = Arrays.asList(MpTipoJornada.values());
		//
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
	}
	
	private void limpar() {
		//
		this.mpTurno = new MpTurno();
		//
//		this.mpTurno.setHoraMovimento(new Time());
		//
	}
	
	public void salvar() {
		//
		this.mpTurno = this.mpTurnoService.salvar(this.mpTurno);
		//
		MpFacesUtil.addInfoMessage("Turno... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		//
		this.mpTurno = this.mpTurnos.porNavegacao("mpFirst", " ");
		if (null == this.mpTurno)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpTurno.getDescricao()) return;
		//
		this.setMpTurnoAnt(this.mpTurno);
		//
		this.mpTurno = this.mpTurnos.porNavegacao("mpPrev", mpTurno.getDescricao());
		if (null == this.mpTurno) {
			this.mpTurno = this.mpTurnoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.setMpTurnoAnt(this.mpTurno);
		
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
		if (null == this.mpTurno.getId()) return;
		//
		this.setMpTurnoAnt(this.mpTurno);
		
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpTurno.getId()) return;
		//
		try {
			this.mpTurnos.remover(mpTurno);
			//
			MpFacesUtil.addInfoMessage("Turno... " + this.sdf.format(
									this.mpTurno.getDescricao()) + " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
		String msg = "";
		if (null == this.mpTurno.getHoraEntrada())
			msg = msg + "\n(Hora Entrada)";
		if (null == this.mpTurno.getDescricao() || this.mpTurno.getDescricao().isEmpty())
			msg = msg + "\n(Descrição)";
			
		if (!msg.isEmpty()) {
			MpFacesUtil.addInfoMessage("Informar... " + msg);
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

		this.setMpTurnoAnt(this.mpTurno);
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
		this.mpTurno = this.mpTurnoAnt;
		
		this.indEditavel = true;
		this.indEditavelNav = this.mpSeguranca.getMpUsuarioLogado().getMpUsuario().
																		getIndBarraNavegacao();
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		if (null == this.mpTurno.getDescricao()) return;
		//
		this.setMpTurnoAnt(this.mpTurno);
		//
		this.mpTurno = this.mpTurnos.porNavegacao("mpNext", mpTurno.getDescricao());
		if (null == this.mpTurno) {
			this.mpTurno = this.mpTurnoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		this.mpTurno = this.mpTurnos.porNavegacao("mpEnd", "ZZZZZZZ");
		if (null == this.mpTurno)
			this.limpar();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpTurno.getId()) return;

		try {
			this.setMpTurnoAnt(this.mpTurno);
			
			this.mpTurno = (MpTurno) this.mpTurno.clone();
			//
			this.mpTurno.setId(null);
			
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
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	// ---

	public MpTurno getMpTurno() { return mpTurno; }
	public void setMpTurno(MpTurno mpTurno) { this.mpTurno = mpTurno; }

	public MpTurno getMpTurnoAnt() { return mpTurnoAnt; }
	public void setMpTurnoAnt(MpTurno mpTurnoAnt) {
		//
		try {
			this.mpTurnoAnt = (MpTurno) this.mpTurno.clone();
			this.mpTurnoAnt.setId(this.mpTurno.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public MpObjeto getMpObjetoHelp() { return mpObjetoHelp; }
	public void setMpObjetoHelp(MpObjeto mpObjetoHelp) { this.mpObjetoHelp = mpObjetoHelp; }
	
	public boolean isEditando() { return this.mpTurno.getId() != null; }

	public MpTipoJornada getMpTipoJornada() { return mpTipoJornada; }
	public void setMpTipoJornada(MpTipoJornada mpTipoJornada) {
														this.mpTipoJornada = mpTipoJornada;	}
	public List<MpTipoJornada> getMpTipoJornadaList() { return mpTipoJornadaList;	}
	
}