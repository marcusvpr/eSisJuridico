package com.mpxds.mpbasic.model.pt05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt05_transacao_")
@Table(name="mp_pt05_transacao")
public class MpTransacao extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private MpRemessa mpRemessa;
	
	private String numeroCodigoPortador;
	private String agenciaCodCedente;
	private String nomeCedFav;
	private String nomeSacador;
	private String documentoSacador;
	private String enderecoSacador;
	private String cepSacador;
	private String cidadeSacador;
	private String ufSacador;
	private String nossoNumero;
	private String especieTitulo;
	private String numeroTitulo;
	private String dataEmissaoTitulo;
	private String dataVencimentoTitulo;
	private String tipoMoeda;
	private String valorTitulo;
	private String saldoTitulo;
	private String pracaPagamento;
	private String tipoEndosso;
	private String informacaoAceite;
	private String numeroControleDevedor;
	private String nomeDevedor;
	private String tipoIdentDevedor;
	private String numeroIdentDevedor;
	private String documentoDevedor;
	private String enderecoDevedor;
	private String cepDevedor;
	private String cidadeDevedor;
	private String ufDevedor;
	private String numeroCartorio;
	private String numeroProtocoloCartorio;
	private String tipoOcorrencia;
	private String dataProtocoloCartorio;
	private String valorCustasCartorio;
	private String declaracaoPortador;
	private String dataOcorrenciaCartorio;
	private String codigoIrregularidade;
	private String bairroDevedor;
	private String valorCustasCartorioDist;
	private String nomePortador;
	private String numeroDistribuicao;
	private String complementoRegistro;
	private String seloDistribuidor;
	private String finsFAlimentares;
	private String convenio;
	private String empresa;
	private String numeroSeqRegistro;	

	// ---
	
	public MpTransacao() { 
		super();
	}

	// ---	

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpRemessaId")
	public MpRemessa getMpRemessa() { return mpRemessa; }
	public void setMpRemessa(MpRemessa mpRemessa) { this.mpRemessa = mpRemessa; }

  	@Column(name = "numero_codigo_portador", nullable = false, length = 3)
	public String getNumeroCodigoPortador() { return numeroCodigoPortador; }
	public void setNumeroCodigoPortador(String numeroCodigoPortador) { 
															this.numeroCodigoPortador = numeroCodigoPortador; }

  	@Column(name = "agencia_cod_cedente", nullable = false, length = 15)
	public String getAgenciaCodCedente() { return agenciaCodCedente; }
	public void setAgenciaCodCedente(String agenciaCodCedente) { this.agenciaCodCedente = agenciaCodCedente; }

  	@Column(name = "nome_ced_fav", nullable = false, length = 45)
	public String getNomeCedFav() { return nomeCedFav; }
	public void setNomeCedFav(String nomeCedFav) { this.nomeCedFav = nomeCedFav; }

  	@Column(name = "nome_sacador", nullable = false, length = 45)
	public String getNomeSacador() { return nomeSacador; }
	public void setNomeSacador(String nomeSacador) { this.nomeSacador = nomeSacador; }

  	@Column(name = "documento_sacador", nullable = false, length = 14)
	public String getDocumentoSacador() { return documentoSacador; }
	public void setDocumentoSacador(String documentoSacador) { this.documentoSacador = documentoSacador; }

  	@Column(name = "endereco_sacador", nullable = false, length = 45)
	public String getEnderecoSacador() { return enderecoSacador; }
	public void setEnderecoSacador(String enderecoSacador) { this.enderecoSacador = enderecoSacador; }

  	@Column(name = "cep_sacador", nullable = false, length = 8)
	public String getCepSacador() { return cepSacador; }
	public void setCepSacador(String cepSacador) { this.cepSacador = cepSacador; }

 	@Column(name = "cidade_sacador", nullable = false, length = 20)
	public String getCidadeSacador() { return cidadeSacador; }
	public void setCidadeSacador(String cidadeSacador) { this.cidadeSacador = cidadeSacador; }

  	@Column(name = "uf_sacador", nullable = false, length = 2)
	public String getUfSacador() { return ufSacador; }
	public void setUfSacador(String ufSacador) { this.ufSacador = ufSacador; }

  	@Column(name = "nosso_numero", nullable = false, length = 15)
	public String getNossoNumero() { return nossoNumero; }
	public void setNossoNumero(String nossoNumero) { this.nossoNumero = nossoNumero; }

  	@Column(name = "especie_titulo", nullable = false, length = 3)
	public String getEspecieTitulo() { return especieTitulo; }
	public void setEspecieTitulo(String especieTitulo) { this.especieTitulo = especieTitulo; }

 	@Column(name = "numero_titulo", nullable = false, length = 11)
	public String getNumeroTitulo() { return numeroTitulo; }
	public void setNumeroTitulo(String numeroTitulo) { this.numeroTitulo = numeroTitulo; }

  	@Column(name = "data_emissao_titulo", nullable = false, length = 8)
	public String getDataEmissaoTitulo() { return dataEmissaoTitulo; }
	public void setDataEmissaoTitulo(String dataEmissaoTitulo) { this.dataEmissaoTitulo = dataEmissaoTitulo; }

  	@Column(name = "data_vencimento_titulo", nullable = false, length = 8)
	public String getDataVencimentoTitulo() { return dataVencimentoTitulo; }
	public void setDataVencimentoTitulo(String dataVencimentoTitulo) { 
															this.dataVencimentoTitulo = dataVencimentoTitulo; }

  	@Column(name = "tipo_moeda", nullable = false, length = 3)
	public String getTipoMoeda() { return tipoMoeda; }
	public void setTipoMoeda(String tipoMoeda) { this.tipoMoeda = tipoMoeda; }

  	@Column(name = "valor_titulo", nullable = false, length = 14)
	public String getValorTitulo() { return valorTitulo; }
	public void setValorTitulo(String valorTitulo) { this.valorTitulo = valorTitulo; }

  	@Column(name = "saldo_titulo", nullable = false, length = 14)
	public String getSaldoTitulo() { return saldoTitulo; }
	public void setSaldoTitulo(String saldoTitulo) { this.saldoTitulo = saldoTitulo; }

  	@Column(name = "praca_pagamento", nullable = false, length = 20)
	public String getPracaPagamento() { return pracaPagamento; }
	public void setPracaPagamento(String pracaPagamento) { this.pracaPagamento = pracaPagamento; }

  	@Column(name = "tipo_endosso", nullable = false, length = 1)
	public String getTipoEndosso() { return tipoEndosso; }
	public void setTipoEndosso(String tipoEndosso) { this.tipoEndosso = tipoEndosso; }

  	@Column(name = "informacao_aceite", nullable = false, length = 1)
	public String getInformacaoAceite() { return informacaoAceite; }
	public void setInformacaoAceite(String informacaoAceite) { this.informacaoAceite = informacaoAceite; }

  	@Column(name = "numero_controle_devedor", nullable = false, length = 1)
	public String getNumeroControleDevedor() { return numeroControleDevedor; }
	public void setNumeroControleDevedor(String numeroControleDevedor) { 
														this.numeroControleDevedor = numeroControleDevedor;	}

  	@Column(name = "nome_devedor", nullable = false, length = 45)
	public String getNomeDevedor() { return nomeDevedor; }
	public void setNomeDevedor(String nomeDevedor) { this.nomeDevedor = nomeDevedor; }

  	@Column(name = "tipo_ident_devedor", nullable = false, length = 3)
	public String getTipoIdentDevedor() { return tipoIdentDevedor; }
	public void setTipoIdentDevedor(String tipoIdentDevedor) { this.tipoIdentDevedor = tipoIdentDevedor; }

  	@Column(name = "numero_ident_devedor", nullable = false, length = 14)
	public String getNumeroIdentDevedor() { return numeroIdentDevedor; }
	public void setNumeroIdentDevedor(String numeroIdentDevedor) { this.numeroIdentDevedor = numeroIdentDevedor; }

  	@Column(name = "documento_devedor", nullable = false, length = 11)
	public String getDocumentoDevedor() { return documentoDevedor; }
	public void setDocumentoDevedor(String documentoDevedor) { this.documentoDevedor = documentoDevedor; }

  	@Column(name = "endereco_devedor", nullable = false, length = 45)
	public String getEnderecoDevedor() { return enderecoDevedor; }
	public void setEnderecoDevedor(String enderecoDevedor) { this.enderecoDevedor = enderecoDevedor; }

  	@Column(name = "cep_devedor", nullable = false, length = 8)
	public String getCepDevedor() { return cepDevedor; }
	public void setCepDevedor(String cepDevedor) { this.cepDevedor = cepDevedor; }

  	@Column(name = "cidade_devedor", nullable = false, length = 20)
	public String getCidadeDevedor() { return cidadeDevedor;}
	public void setCidadeDevedor(String cidadeDevedor) { this.cidadeDevedor = cidadeDevedor; }

  	@Column(name = "uf_devedor", nullable = false, length = 2)
	public String getUfDevedor() { return ufDevedor; }
	public void setUfDevedor(String ufDevedor) { this.ufDevedor = ufDevedor; }

  	@Column(name = "numero_cartorio", nullable = false, length = 2)
	public String getNumeroCartorio() { return numeroCartorio; }
	public void setNumeroCartorio(String numeroCartorio) { this.numeroCartorio = numeroCartorio; }

  	@Column(name = "numero_protocolo_cartorio", nullable = false, length = 10)
	public String getNumeroProtocoloCartorio() { return numeroProtocoloCartorio; }
	public void setNumeroProtocoloCartorio(String numeroProtocoloCartorio) { 
													this.numeroProtocoloCartorio = numeroProtocoloCartorio;	}

  	@Column(name = "tipo_ocorrencia", nullable = false, length = 1)
	public String getTipoOcorrencia() { return tipoOcorrencia; }
	public void setTipoOcorrencia(String tipoOcorrencia) { this.tipoOcorrencia = tipoOcorrencia; }

  	@Column(name = "data_protocolo_cartorio", nullable = false, length = 8)
	public String getDataProtocoloCartorio() { return dataProtocoloCartorio; }
	public void setDataProtocoloCartorio(String dataProtocoloCartorio) { 
															this.dataProtocoloCartorio = dataProtocoloCartorio; }

  	@Column(name = "valor_custas_cartorio", nullable = false, length = 14)
	public String getValorCustasCartorio() { return valorCustasCartorio; }
	public void setValorCustasCartorio(String valorCustasCartorio) { 
															this.valorCustasCartorio = valorCustasCartorio; }

  	@Column(name = "declaracao_portador", nullable = false, length = 1)
	public String getDeclaracaoPortador() { return declaracaoPortador; }
	public void setDeclaracaoPortador(String declaracaoPortador) { 
															this.declaracaoPortador = declaracaoPortador; }

  	@Column(name = "data_ocorrencia_cartorio", nullable = false, length = 8)
	public String getDataOcorrenciaCartorio() {	return dataOcorrenciaCartorio; }
	public void setDataOcorrenciaCartorio(String dataOcorrenciaCartorio) {
														this.dataOcorrenciaCartorio = dataOcorrenciaCartorio; }

  	@Column(name = "codigo_irregularidade", nullable = false, length = 2)
	public String getCodigoIrregularidade() { return codigoIrregularidade; }
	public void setCodigoIrregularidade(String codigoIrregularidade) { 
															this.codigoIrregularidade = codigoIrregularidade; }

  	@Column(name = "bairro_devedor", nullable = false, length = 20)
	public String getBairroDevedor() { return bairroDevedor; }
	public void setBairroDevedor(String bairroDevedor) { this.bairroDevedor = bairroDevedor; }

  	@Column(name = "valor_custas_cartorio_dist", nullable = false, length = 14)
	public String getValorCustasCartorioDist() { return valorCustasCartorioDist; }
	public void setValorCustasCartorioDist(String valorCustasCartorioDist) {
													this.valorCustasCartorioDist = valorCustasCartorioDist; }

  	@Column(name = "nome_portador", nullable = false, length = 45)
	public String getNomePortador() { return nomePortador; }
	public void setNomePortador(String nomePortador) { this.nomePortador = nomePortador; }

  	@Column(name = "numero_distribuicao", nullable = false, length = 7)
	public String getNumeroDistribuicao() { return numeroDistribuicao; }
	public void setNumeroDistribuicao(String numeroDistribuicao) { this.numeroDistribuicao = numeroDistribuicao; }

  	@Column(name = "complemento_registro", nullable = false, length = 1)
	public String getComplementoRegistro() { return complementoRegistro; }
	public void setComplementoRegistro(String complementoRegistro) { 
																this.complementoRegistro = complementoRegistro;	}

 	@Column(name = "selo_distribuidor", nullable = false, length = 12)
	public String getSeloDistribuidor() { return seloDistribuidor; }
	public void setSeloDistribuidor(String seloDistribuidor) { this.seloDistribuidor = seloDistribuidor; }

  	@Column(name = "fins_f_alimentares", nullable = false, length = 1)
	public String getFinsFAlimentares() { return finsFAlimentares; }
	public void setFinsFAlimentares(String finsFAlimentares) { this.finsFAlimentares = finsFAlimentares; }

  	@Column(nullable = false, length = 1)
	public String getConvenio() { return convenio; }
	public void setConvenio(String convenio) { this.convenio = convenio; }

  	@Column(nullable = false, length = 1)
	public String getEmpresa() { return empresa; }
	public void setEmpresa(String empresa) { this.empresa = empresa; }

  	@Column(name = "numero_seq_registro", nullable = false, length = 4)
	public String getNumeroSeqRegistro() { return numeroSeqRegistro; }
	public void setNumeroSeqRegistro(String numeroSeqRegistro) { this.numeroSeqRegistro = numeroSeqRegistro; }		
}
