package com.mpxds.mpbasic.model.dto.sisJuri;

import java.util.Date;

public class MpObjetoProcessoDTO {
    //
	private Long id;
	private String atoPraticado; // Tab.1040 ;
	private String andamentoTipo;
	private Date dataCadastro;
	private String localTramiteDescricao;
	private String comarcaDescricao;
	private String parteContrariaNome;
	private String clienteNome;
	private String processoCodigo;
	private String autorNome;
	private String detalhamento;
	private String responsavel;
	private String resumo;
	private String telefone;
	//
  
	public MpObjetoProcessoDTO() {
		super();
	}
	
	public MpObjetoProcessoDTO(
				Long id,
				String atoPraticado,
				String andamentoTipo,
				Date dataCadastro,
				String comarcaDescricao,
				String localTramiteDescricao,
				String parteContrariaNome,
				String clienteNome,
				String processoCodigo,
				String autorNome,
				String detalhamento,
				String responsavel,
				String resumo,
				String telefone) {
		//
		super();
		//
		this.id = id;
		this.atoPraticado = atoPraticado;
		this.andamentoTipo = andamentoTipo;
		this.dataCadastro = dataCadastro;
		this.comarcaDescricao = comarcaDescricao;
		this.localTramiteDescricao = localTramiteDescricao;
		this.parteContrariaNome = parteContrariaNome;
		this.clienteNome = clienteNome;
		this.processoCodigo = processoCodigo;
		this.autorNome = autorNome;
		this.detalhamento = detalhamento;
		this.responsavel = responsavel;
		this.resumo = resumo;
		this.telefone = telefone;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getAtoPraticado() { return atoPraticado; }
	public void setAtoPraticado(String atoPraticado) { this.atoPraticado = atoPraticado; }

	public String getAndamentoTipo() { return andamentoTipo; }
	public void setAndamentoTipo(String andamentoTipo) { this.andamentoTipo = andamentoTipo; }

	public Date getDataCadastro() { return dataCadastro; }
	public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

	public String getComarcaDescricao() { return comarcaDescricao; }
	public void setComarcaDescricao(String comarcaDescricao) { this.comarcaDescricao = comarcaDescricao; }

	public String getLocalTramiteDescricao() { return localTramiteDescricao; }
	public void setLocalTramiteDescricao(String localTramiteDescricao) { 
														this.localTramiteDescricao = localTramiteDescricao; }

	public String getParteContrariaNome() { return parteContrariaNome; }
	public void setParteContrariaNome(String parteContrariaNome) { this.parteContrariaNome = parteContrariaNome; }

	public String getClienteNome() { return clienteNome; }
	public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

	public String getProcessoCodigo() { return processoCodigo; }
	public void setProcessoCodigo(String processoCodigo) { this.processoCodigo = processoCodigo; }

	public String getAutorNome() { return autorNome; }
	public void setAutorNome(String autorNome) { this.autorNome = autorNome; }
	
	public String getDetalhamento() { return detalhamento; }
	public void setDetalhamento(String detalhamento) { this.detalhamento = detalhamento; }
	
	public String getResponsavel() { return responsavel; }
	public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

	public String getResumo() { return resumo; }
	public void setResumo(String resumo) { this.resumo = resumo; }

	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }
	
}
