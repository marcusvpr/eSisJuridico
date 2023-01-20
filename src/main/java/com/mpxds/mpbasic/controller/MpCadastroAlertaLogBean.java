package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.mpxds.mpbasic.model.log.MpAlertaLog;
import com.mpxds.mpbasic.repository.MpAlertaLogs;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpAlertaLogService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroAlertaLogBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpAlertaLogs mpAlertaLogs;

	@Inject
	private MpAlertaLogService mpAlertaLogService;

	@Inject
	private MpSeguranca mpSeguranca;

	// ---
	
	private MpAlertaLog mpAlertaLog = new MpAlertaLog();
	private MpAlertaLog mpAlertaLogAnt;

	private Boolean indEditavel = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// ------------------
	
	public MpCadastroAlertaLogBean() {
//		System.out.println("MpCadastroAlertaLogBean - Entrou 0000 ");
		//
		if (null == this.mpAlertaLog)
			this.limpar();
	}
	
	public void inicializar() {
		//
		HttpServletRequest request = (HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext().getRequest();
		
		String param = request.getParameter("param");
		if (null == param) param = "null";
//		System.out.println("MpCadastroAlertaLogBean.inicializar() - ( param = " + param);
		if (param.equals("login"))
			this.mpNew(); // Posiciona no novo registro !!!
		else
			if (null == this.mpAlertaLog)
				this.mpEnd(); // Posiciona no último registro !!!
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpAlertaLog.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		//
		this.setMpAlertaLogAnt(this.mpAlertaLog);
	}
	
	private void limpar() {
		//
		this.mpAlertaLog = new MpAlertaLog();
		//
		this.mpAlertaLog.setDataMovimento(new Date());
		//
	}
	
	public void salvar() {
		//
		this.mpAlertaLog = this.mpAlertaLogService.salvar(this.mpAlertaLog);

		MpFacesUtil.addInfoMessage("AlertaLog... salvo com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() {
		try {
			this.mpAlertaLog = this.mpAlertaLogs.porNavegacao("mpFirst", sdf.parse("01/01/1900"));
			if (null == this.mpAlertaLog)
				this.limpar();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() {
		//
		if (null == this.mpAlertaLog.getDataMovimento()) return;
		//
		this.setMpAlertaLogAnt(this.mpAlertaLog);
		//
		this.mpAlertaLog = this.mpAlertaLogs.porNavegacao("mpPrev", mpAlertaLog.getDataMovimento());
		if (null == this.mpAlertaLog) {
			this.mpAlertaLog = this.mpAlertaLogAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else
			this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		//
		this.mpAlertaLogAnt = this.mpAlertaLog;
		
		this.mpAlertaLog = new MpAlertaLog();
		//
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpAlertaLog.getId()) return;
		//
		this.mpAlertaLogAnt = this.mpAlertaLog;
		
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpAlertaLog.getId()) return;
		//
		try {
			this.mpAlertaLogs.remover(mpAlertaLog);
			
			MpFacesUtil.addInfoMessage("AlertaLog... " + this.sdf.format(this.mpAlertaLog.getDataMovimento())
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

		this.setMpAlertaLogAnt(this.mpAlertaLog);
		//
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpAlertaLog = this.mpAlertaLogAnt;
		
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		if (null == this.mpAlertaLog.getDataMovimento()) return;
		//
		this.setMpAlertaLogAnt(this.mpAlertaLog);
		//
		this.mpAlertaLog = this.mpAlertaLogs.porNavegacao("mpNext", mpAlertaLog.getDataMovimento());
		if (null == this.mpAlertaLog) {
			this.mpAlertaLog = this.mpAlertaLogAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else
			this.txtModoTela = "( Próximo )";
	}
	
	public void mpEnd() {
		//
		try {
			this.mpAlertaLog = this.mpAlertaLogs.porNavegacao("mpEnd", sdf.parse("01/01/2099"));
			if (null == this.mpAlertaLog)
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
		if (null == this.mpAlertaLog.getId()) return;

		try {
			this.setMpAlertaLogAnt(this.mpAlertaLog);
			
			this.mpAlertaLog = (MpAlertaLog) this.mpAlertaLog.clone();
			//
			this.mpAlertaLog.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	// ----
	
	public MpAlertaLog getMpAlertaLog() { return mpAlertaLog; }
	public void setMpAlertaLog(MpAlertaLog mpAlertaLog) { this.mpAlertaLog = mpAlertaLog; }

	public MpAlertaLog getMpAlertaLogAnt() { return mpAlertaLogAnt; }
	public void setMpAlertaLogAnt(MpAlertaLog mpAlertaLogAnt) {
		//
		try {
			this.mpAlertaLogAnt = (MpAlertaLog) this.mpAlertaLog.clone();
			this.mpAlertaLogAnt.setId(this.mpAlertaLog.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpAlertaLog.getId() != null; }
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

}