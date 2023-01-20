package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.repository.MpClientes;

@Named
@ViewScoped
public class MpSelecaoClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpClientes mpClientes;
	
	private String nome;
	
	private List<MpCliente> mpClientesFiltrados;
	
	public void pesquisar() {
		mpClientesFiltrados = mpClientes.porNomeList(nome);
	}

	public void selecionar(MpCliente mpCliente) {
		RequestContext.getCurrentInstance().closeDialog(mpCliente);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/MpSelecaoCliente", opcoes, null);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<MpCliente> getMpClientesFiltrados() {
		return mpClientesFiltrados;
	}

}