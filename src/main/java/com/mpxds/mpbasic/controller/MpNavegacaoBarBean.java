package com.mpxds.mpbasic.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpNavegacaoBarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean indEditavel = true;
	private Boolean indNaoEditavel = false;
	
	private String txtModoTela = "";
	
	public MpNavegacaoBarBean() {	
	}
	
	public void inicializar() {
	}
	
	private void limpar() {
	}
	
	public void salvar() {
		try {			
			
			limpar();
			
			MpFacesUtil.addInfoMessage("Sistema Configuração... salva com sucesso!");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void mpFirst() {
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		//
		this.txtModoTela = "( Anterior )";
	}

	public void mpNew() {
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Novo )";
	}
	public void mpEdit() {
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Edição )";
	}
	public void mpDelete() {
	}
	
	public void mpGrava() {
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	public void mpDesfaz() {
		this.indEditavel = true;
		this.indNaoEditavel = false;
		//
		this.txtModoTela = "";
	}
	
	public void mpNext() {
		//
		this.txtModoTela = "( Próximo )";
	}
	public void mpEnd() {
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		this.indEditavel = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	public void mpSearch() {
	}
	
	public boolean getIndEditavel() {
		return indEditavel;
	}
	public void setIndEditavel(Boolean indEditavel) {
		this.indEditavel = indEditavel;
	}
	
	public boolean getIndNaoEditavel() {
		return indNaoEditavel;
	}
	public void setIndNaoEditavel(Boolean indNaoEditavel) {
		this.indNaoEditavel = indNaoEditavel;
	}
	
	public String getTxtModoTela() {
		return txtModoTela;
	}
	public void setTxtModoTela(String txtModoTela) {
		this.txtModoTela = txtModoTela;
	}

}