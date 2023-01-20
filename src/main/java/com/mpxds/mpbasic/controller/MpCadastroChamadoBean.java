package com.mpxds.mpbasic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.mpxds.mpbasic.model.MpChamado;
import com.mpxds.mpbasic.model.enums.MpChamadoStatus;
import com.mpxds.mpbasic.model.enums.MpChamadoTipo;
import com.mpxds.mpbasic.model.enums.MpChamadoAreaTipo;
import com.mpxds.mpbasic.model.enums.MpChamadoSeveridade;
import com.mpxds.mpbasic.repository.MpChamados;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.service.MpChamadoService;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpCadastroChamadoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpChamados mpChamados;

	@Inject
	private MpChamadoService mpChamadoService;
	
	@Inject
	private MpSeguranca mpSeguranca;
	
	// ---

	private MpChamado mpChamado = new MpChamado();
	private MpChamado mpChamadoAnt;

	private Boolean indEditavel = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
		
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
    private UploadedFile file;
	
	private MpChamadoTipo mpChamadoTipo;
	private List<MpChamadoTipo> mpChamadoTipoList;
	
	private MpChamadoAreaTipo mpChamadoAreaTipo;
	private List<MpChamadoAreaTipo> mpChamadoAreaTipoList;

	private MpChamadoSeveridade mpChamadoSeveridade;
	private List<MpChamadoSeveridade> mpChamadoSeveridadeList;

	private MpChamadoStatus mpChamadoStatus;
	private List<MpChamadoStatus> mpChamadoStatusList;
	
	// ------
	
	public MpCadastroChamadoBean() {
		if (null == this.mpChamado)
			limpar();
		//
	}
	
	public void inicializar() throws ParseException, IOException {
		//
		if (null == this.mpChamado) {
			this.limpar();
			//
			this.mpFirst(); // Posiciona no primeiro registro !!!
		}
		// Verifica TenantId ?
		if (!mpSeguranca.capturaTenantId().trim().equals("0")) {
			if (!this.mpChamado.getTenantId().trim().equals(mpSeguranca.capturaTenantId().trim())) {
				//
				MpFacesUtil.addInfoMessage("Error Violação! Contactar o Suporte!");
				//
				this.limpar();
				return;
			}
		}
		
		this.setMpChamadoAnt(this.mpChamado);
		//
		this.mpChamadoTipoList = Arrays.asList(MpChamadoTipo.values());
		this.mpChamadoAreaTipoList = Arrays.asList(MpChamadoAreaTipo.values());
		this.mpChamadoSeveridadeList = Arrays.asList(MpChamadoSeveridade.values());
		this.mpChamadoStatusList = Arrays.asList(MpChamadoStatus.values());
	}
	
	private void limpar() {
		this.mpChamado = new MpChamado();
		//
		this.mpChamado.setDtHrChamado(new Date());		
	}
	
	public void salvar() {
		//
		this.mpChamado = mpChamadoService.salvar(this.mpChamado);
			
		MpFacesUtil.addInfoMessage("Notificação... salva com sucesso!");
	}
	
	// -------- Trata Navegação ...

	public void mpFirst() throws ParseException, IOException {
		this.mpChamado = this.mpChamados.porNavegacao("mpFirst", sdf.parse("01/01/1900")); 
		if (null == this.mpChamado)
			this.limpar();
		//
		this.txtModoTela = "( Início )";
	}
	
	public void mpPrev() throws IOException {
		if (null == this.mpChamado.getDtHrChamado()) return;
		//
		this.setMpChamadoAnt(this.mpChamado);
		//
		this.mpChamado = this.mpChamados.porNavegacao("mpPrev", mpChamado.getDtHrChamado());
		if (null == this.mpChamado) {
			this.mpChamado = this.mpChamadoAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else {
			this.txtModoTela = "( Anterior )";
		}
	}

	public void mpNew() {
		//
		this.setMpChamadoAnt(this.mpChamado);
		
		this.mpChamado = new MpChamado();
		
		this.mpChamado.setDtHrChamado(new Date());
		//
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		if (null == this.mpChamado.getId()) return;
		//
		this.setMpChamadoAnt(this.mpChamado);
		
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpDelete() {
		if (null == this.mpChamado.getId()) return;
		//
		try {
			this.mpChamados.remover(mpChamado);
			
			MpFacesUtil.addInfoMessage("Notificação... "
												+ sdf.format(this.mpChamado.getDtHrChamado())
												+ " excluída com sucesso.");			
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

		this.setMpChamadoAnt(this.mpChamado);
		//
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		this.mpChamado = this.mpChamadoAnt;
		
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() throws IOException {
		if (null == this.mpChamado.getDtHrChamado()) return;
		//
		this.setMpChamadoAnt(this.mpChamado);
		//
		this.mpChamado = this.mpChamados.porNavegacao("mpNext", mpChamado.getDtHrChamado());
		if (null == this.mpChamado) {
			this.mpChamado = this.mpChamadoAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else {
			this.txtModoTela = "( Próximo )";
		}
	}
	public void mpEnd() throws ParseException, IOException {
		this.mpChamado = this.mpChamados.porNavegacao("mpEnd",	sdf.parse("01/01/2900")); 
		if (null == this.mpChamado)
			this.limpar();
		//
//		else
//			System.out.println("MpCadastroChamadoBean.mpEnd() ( Entrou 0001 = " +
//															this.mpChamado.getParametro());
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpChamado.getId()) return;

		try {
			this.setMpChamadoAnt(this.mpChamado);

			this.mpChamado = (MpChamado) this.mpChamado.clone();
			//
			this.mpChamado.setId(null);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	// ---
	
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) { this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }

	public MpChamado getMpChamado() { return mpChamado; }
	public void setMpChamado(MpChamado mpChamado) { this.mpChamado = mpChamado; }

	public MpChamado getMpChamadoAnt() { return mpChamadoAnt; }
	public void setMpChamadoAnt(MpChamado mpChamadoAnt) {
		try {
			this.mpChamadoAnt = (MpChamado) this.mpChamado.clone();
			this.mpChamadoAnt.setId(this.mpChamado.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public boolean isEditando() { return this.mpChamado.getId() != null; }

	public MpChamadoSeveridade getMpChamadoSeveridade() { return mpChamadoSeveridade; }
	public void setMpChamadoSeveridade(MpChamadoSeveridade mpChamadoSeveridade) {
														this.mpChamadoSeveridade = mpChamadoSeveridade;	}
	public List<MpChamadoSeveridade> getMpChamadoSeveridadeList() { return mpChamadoSeveridadeList; }

	public MpChamadoAreaTipo getMpChamadoAreaTipo() { return mpChamadoAreaTipo; }
	public void setMpChamadoAreaTipo(MpChamadoAreaTipo mpChamadoAreaTipo) {
															this.mpChamadoAreaTipo = mpChamadoAreaTipo;	}
	public List<MpChamadoAreaTipo> getMpChamadoAreaTipoList() {	return mpChamadoAreaTipoList; }

	public MpChamadoTipo getMpChamadoTipo() { return mpChamadoTipo; }
	public void setMpChamadoTipo(MpChamadoTipo mpChamadoTipo) { this.mpChamadoTipo = mpChamadoTipo; }
	public List<MpChamadoTipo> getMpChamadoTipoList() { return mpChamadoTipoList; }

	public MpChamadoStatus getMpChamadoStatus() { return mpChamadoStatus; }
	public void setMpChamadoStatus(MpChamadoStatus mpChamadoStatus) {
																this.mpChamadoStatus = mpChamadoStatus;	}
	public List<MpChamadoStatus> getMpChamadoStatusList() {	return mpChamadoStatusList; }

	public UploadedFile getFile() { return this.file; }	
    public void setFile(UploadedFile file) { this.file = file; }
    
}