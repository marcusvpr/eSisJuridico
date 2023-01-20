package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mpxds.mpbasic.model.MpContato;
import com.mpxds.mpbasic.repository.MpContatos;

@Named
@ViewScoped
public class MpSelecaoContatoBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpContatos mpContatos;
	
	private String nomeRazaoSocial;
	
	private List<MpContato> mpContatosFiltrados;
	
	public void pesquisar() {
		//
		mpContatosFiltrados = mpContatos.porNomeRazaoSocialList(nomeRazaoSocial);
	}

	public void selecionar(MpContato mpContato) {
		//
		RequestContext.getCurrentInstance().closeDialog(mpContato);
	}
	
	public void abrirDialogo() {
		//
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/MpSelecaoContato", opcoes, null);
	}
	
	public String getNomeRazaoSocial() { return nomeRazaoSocial; }
	public void setNomeRazaoSocial(String nomeRazaoSocial) { this.nomeRazaoSocial = nomeRazaoSocial; }
	public List<MpContato> getMpContatosFiltrados() { return mpContatosFiltrados; }

}