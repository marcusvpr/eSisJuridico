package com.mpxds.mpbasic.model.pt01;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.pt05.MpValorAto;

@Audited
@AuditTable(value="mp_pt01_titulo_")
@Table(name="mp_pt01_titulo")
@Entity
public class MpTitulo extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataProtocolo;	
	private String numeroProtocolo;
	private Date dataAte;
	private String codigoApresentante;
	private String convenio;
	private String empresa;
	private Date dataDistribuicao;
	private String numeroDistribuicao;	
	private Date dataEmissao;	
	private Date dataVencimento;
	private String numeroTitulo;
	private String numeroBanco;	
	private String codigoEndosso;	
	private String agenciaCedente;	
	private BigDecimal valor;
	private BigDecimal saldo;	
	private String especieCodigo;	
	private String codigoAlinea;
	private String tipoApresentante;
	private String edital;
	private String microEmpresa;
	
	private MpValorAto mpValorAto = new MpValorAto(); // MpAto usa também ! 
	
	private BigDecimal valorVariavel;	
	private String faixa;
	private BigDecimal intimacao;	
	private BigDecimal coobrigado;	
	private BigDecimal diligencia;
	private BigDecimal valorEdital;	
	private BigDecimal outros;
	
	/* TODO */
	
	private BigDecimal totalEmolumento; // Campo Calculado? Vide Calculo abaixo  !	
	private BigDecimal totalPagar; // Campo Calculado? Vide Calculo abaixo  !	
	private BigDecimal totalDeposito; // Campo Calculado? Vide Calculo abaixo  !
	
	private Integer quantidadeDevedor; // cco virou Quantidade !	
	private Integer quantidadeAvalista;	
	private Integer quantidadeEndossante;	
	private Integer quantidadeNotificado;	
	private Integer quantidadeEnderecoIgual;	
	private Integer quantidadeEnderecoDiferente;
	
	private String usuarioTitulo;	
	private Date dataSistemaTitulo;	
	private Date dataOcorrencia;
	private String codigoOcorrencia;	
	private Integer numeroCancelamento;
	private String dinheiroCheque;
	private String observacaoOcorrencia;
	private String bloqueioOcorrencia;	
	private String usuarioOcorrencia;	
	private Date dataSistemaOcorrencia;	
	private Date tituloDataRetirada;
	private Date tituloDataRepasse;	
	private String tituloChequeRepasse;	
	private String observacaoTitulo;
	private String motivo;	
	private String custasPagar;	
	private Date dataResultadoIntimacao;	
	private String resultadoIntimacao;
	private Date dataEnvioCorreio;
	private Date dataRetornoCorreio;	
	private String resultadoCorreio;	
	private Date dataEdital;	
	private String intimado;	
	private String respondido;	
	private Date dataEnvioSuspensaoEfeito;	
	private Date dataResultadoSuspensaoEfeito;	
	private String resultadoSuspensaoEfeito;
	private Date dataEnvioSustacaoProtesto;
	private Date dataResultadoSustacaoProtesto;
	private String resultadoSustacaoProtesto;	
	private BigDecimal tituloValorReembolsoEdital;	
	private String numeroCliente;	
	private Date dataAviso;	
	private Date dataResultadoAviso;
	
	private MpPessoaTitulo mpPessoaTituloApresentante; // Normalizado !	
	private MpPessoaTitulo mpPessoaTituloFavorecido; // Normalizado !	
	private MpPessoaTitulo mpPessoaTituloSacador; // Normalizado !
			
	private String numeroLivro;	
	private String numeroFolha;
	private Boolean indDigital;	
	private String codigoIrregularidade;
	private String nihil; // Sigla = Gratis (Recolhendo Emolumentos salvo Justiça Gratis)
	private String pagamentoAVista; 

	//	private String rsDesiste; // Não Utilizado !

	private String talaoNumeroLivro;	
	private String convenioNumeroLivro;
	private String numeroTalao3Oficio;	
	private String aceite;	
 	private String finsFAlimentares;	
	private String arquivamento;
	
	// ---
	
	public MpTitulo() {
		super();
	}

	// ---
	
  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_protocolo", nullable = false)
	public Date getDataProtocolo() { return dataProtocolo; }
	public void setDataProtocolo(Date dataProtocolo) { this.dataProtocolo = dataProtocolo; }

  	@Column(name = "numero_protocolo", nullable = false, length = 6)
	public String getNumeroProtocolo() { return numeroProtocolo; }
	public void setNumeroProtocolo(String numeroProtocolo) { 
													this.numeroProtocolo = numeroProtocolo; }

 	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_ate", nullable = false)
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }

  	@Column(name = "codigo_Apresentante", length = 7)
	public String getCodigoApresentante() { return codigoApresentante; }
	public void setCodigoApresentante(String codigoApresentante) {
												this.codigoApresentante = codigoApresentante; }

  	@Column(name = "convenio", length = 1)
	public String getConvenio() { return convenio; }
	public void setConvenio(String convenio) { this.convenio = convenio; }

  	@Column(name = "empresa", length = 1)
	public String getEmpresa() { return empresa; }
	public void setEmpresa(String empresa) { this.empresa = empresa; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_distribuicao", nullable = true) // nullable = false ????
	public Date getDataDistribuicao() { return dataDistribuicao; }
	public void setDataDistribuicao(Date dataDistribuicao) {
													this.dataDistribuicao = dataDistribuicao; }

  	@Column(name = "numero_Distribuicao", length = 10)
	public String getNumeroDistribuicao() { return numeroDistribuicao; }
	public void setNumeroDistribuicao(String numeroDistribuicao) { 
												this.numeroDistribuicao = numeroDistribuicao; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_emissao", nullable = true) // nullable = false ????
	public Date getDataEmissao() { return dataEmissao; }
	public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_vencimento", nullable = true) // nullable = false ????
	public Date getDataVencimento() { return dataVencimento; }
	public void setDataVencimento(Date dataVencimento) { this.dataVencimento = dataVencimento; }

  	@Column(name = "numero_Titulo", length = 20)
	public String getNumeroTitulo() { return numeroTitulo; }
	public void setNumeroTitulo(String numeroTitulo) { this.numeroTitulo = numeroTitulo; }

  	@Column(name = "numero_Banco", length = 20)
	public String getNumeroBanco() { return numeroBanco; }
	public void setNumeroBanco(String numeroBanco) { this.numeroBanco = numeroBanco; }

  	@Column(name = "codigo_Endosso", length = 1)
	public String getCodigoEndosso() { return codigoEndosso; }
	public void setCodigoEndosso(String codigoEndosso) { this.codigoEndosso = codigoEndosso; }

  	@Column(name = "agencia_Cedente", length = 15)
	public String getAgenciaCedente() { return agenciaCedente; }
	public void setAgenciaCedente(String agenciaCedente) { this.agenciaCedente = agenciaCedente; }

	@Column(precision = 15, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getValor() { return valor; }
	public void setValor(BigDecimal valor) { this.valor = valor; }

	@Column(precision = 15, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getSaldo() { return saldo; }
	public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

  	@Column(name = "especie_Codigo", length = 3)
	public String getEspecieCodigo() { return especieCodigo; }
	public void setEspecieCodigo(String especieCodigo) { this.especieCodigo = especieCodigo; }

  	@Column(name = "codigo_Alinea", length = 11)
	public String getCodigoAlinea() { return codigoAlinea; }
	public void setCodigoAlinea(String codigoAlinea) { this.codigoAlinea = codigoAlinea; }

  	@Column(name = "tipo_Apresentante", length = 1)
	public String getTipoApresentante() { return tipoApresentante; }
	public void setTipoApresentante(String tipoApresentante) {
													this.tipoApresentante = tipoApresentante; }

  	@Column(name = "edital", length = 1)
	public String getEdital() { return edital; }
	public void setEdital(String edital) { this.edital = edital; }

  	@Column(name = "micro_Empresa", length = 1)
	public String getMicroEmpresa() { return microEmpresa; }
	public void setMicroEmpresa(String microEmpresa) { this.microEmpresa = microEmpresa; }

	@Embedded
	public MpValorAto getMpValorAto() { return mpValorAto; }
	public void setMpValorAto(MpValorAto mpValorAto) { this.mpValorAto = mpValorAto; }

  	@Column(name = "faixa", length = 1)
	public String getFaixa() { return faixa; }
	public void setFaixa(String faixa) { this.faixa = faixa; }

	@Column(name = "valor_variavel", precision = 10, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getValorVariavel() { return valorVariavel; }
	public void setValorVariavel(BigDecimal valorVariavel) { this.valorVariavel = valorVariavel; }

	@Column(precision = 12, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getIntimacao() { return intimacao; }
	public void setIntimacao(BigDecimal intimacao) { this.intimacao = intimacao; }

	@Column(precision = 12, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getCoobrigado() { return coobrigado; }
	public void setCoobrigado(BigDecimal coobrigado) { this.coobrigado = coobrigado; }

	@Column(precision = 12, scale = 2, nullable = true) // nullable = false ????)
	public BigDecimal getDiligencia() { return diligencia; }
	public void setDiligencia(BigDecimal diligencia) { this.diligencia = diligencia; }

	@Column(name = "valor_edital", precision = 12, scale = 2, nullable = true)  // nullable = false ????)
	public BigDecimal getValorEdital() { return valorEdital; }
	public void setValorEdital(BigDecimal valorEdital) { this.valorEdital = valorEdital; }

	@Column(precision = 12, scale = 2, nullable = true)  // nullable = false ????))
	public BigDecimal getOutros() { return outros; }
	public void setOutros(BigDecimal outros) { this.outros = outros; }

	@Column(name = "total_emolumento", precision = 15, scale = 2, nullable = true)  // nullable = false ????))
	public BigDecimal getTotalEmolumento() { return totalEmolumento; }
	public void setTotalEmolumento(BigDecimal totalEmolumento) {
													this.totalEmolumento = totalEmolumento; }

	@Column(name = "total_pagar", precision = 15, scale = 2, nullable = true)  // nullable = false ????))
	public BigDecimal getTotalPagar() { return totalPagar; }
	public void setTotalPagar(BigDecimal totalPagar) { this.totalPagar = totalPagar; }

	@Column(name = "total_deposito", precision = 15, scale = 2, nullable = true)  // nullable = false ????))
	public BigDecimal getTotalDeposito() { return totalDeposito; }
	public void setTotalDeposito(BigDecimal totalDeposito) { this.totalDeposito = totalDeposito; }

  	@Column(name = "quantidade_Devedor")
	public Integer getQuantidadeDevedor() { return quantidadeDevedor; }
	public void setQuantidadeDevedor(Integer quantidadeDevedor) {
														this.quantidadeDevedor = quantidadeDevedor; }

  	@Column(name = "quantidade_Avalista")
	public Integer getQuantidadeAvalista() { return quantidadeAvalista; }
	public void setQuantidadeAvalista(Integer quantidadeAvalista) {
													this.quantidadeAvalista = quantidadeAvalista; }

  	@Column(name = "quantidade_Endossante")
	public Integer getQuantidadeEndossante() { return quantidadeEndossante; }
	public void setQuantidadeEndossante(Integer quantidadeEndossante) {
												this.quantidadeEndossante = quantidadeEndossante; }

  	@Column(name = "quantidade_Notificado")
	public Integer getQuantidadeNotificado() { return quantidadeNotificado; }
	public void setQuantidadeNotificado(Integer quantidadeNotificado) {
												this.quantidadeNotificado = quantidadeNotificado; }

  	@Column(name = "quantidade_Endereco_Igual")
	public Integer getQuantidadeEnderecoIgual() { return quantidadeEnderecoIgual; }
	public void setQuantidadeEnderecoIgual(Integer quantidadeEnderecoIgual) {
											this.quantidadeEnderecoIgual = quantidadeEnderecoIgual; }

  	@Column(name = "quantidade_Endereco_Diferente")
	public Integer getQuantidadeEnderecoDiferente() { return quantidadeEnderecoDiferente; }
	public void setQuantidadeEnderecoDiferente(Integer quantidadeEnderecoDiferente) {
									this.quantidadeEnderecoDiferente = quantidadeEnderecoDiferente; }

  	@Column(name = "usuario_Titulo", length = 25)
	public String getUsuarioTitulo() { return usuarioTitulo; }
	public void setUsuarioTitulo(String usuarioTitulo) { this.usuarioTitulo = usuarioTitulo; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Sistema_Titulo", nullable = true)  // nullable = false ????))
	public Date getDataSistemaTitulo() { return dataSistemaTitulo; }
	public void setDataSistemaTitulo(Date dataSistemaTitulo) { 
														this.dataSistemaTitulo = dataSistemaTitulo; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_ocorrencia", nullable = true)  // nullable = false ????))
	public Date getDataOcorrencia() { return dataOcorrencia; }
	public void setDataOcorrencia(Date dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }

  	@Column(name = "codigo_Ocorrencia", length = 2)
	public String getCodigoOcorrencia() { return codigoOcorrencia; }
	public void setCodigoOcorrencia(String codigoOcorrencia) {
													this.codigoOcorrencia = codigoOcorrencia; }

  	@Column(name = "numero_Cancelamento")
	public Integer getNumeroCancelamento() { return numeroCancelamento; }
	public void setNumeroCancelamento(Integer numeroCancelamento) {
												this.numeroCancelamento = numeroCancelamento; }

  	@Column(name = "dinheiro_Cheque", length = 1)
	public String getDinheiroCheque() { return dinheiroCheque; }
	public void setDinheiroCheque(String dinheiroCheque) {
														this.dinheiroCheque = dinheiroCheque; }

  	@Column(name = "observacao_Ocorrencia", length = 20)
	public String getObservacaoOcorrencia() { return observacaoOcorrencia; }
	public void setObservacaoOcorrencia(String observacaoOcorrencia) {
											this.observacaoOcorrencia = observacaoOcorrencia; }

  	@Column(name = "bloqueio_Ocorrencia", length = 1)
	public String getBloqueioOcorrencia() { return bloqueioOcorrencia; }
	public void setBloqueioOcorrencia(String bloqueioOcorrencia) {
												this.bloqueioOcorrencia = bloqueioOcorrencia; }

  	@Column(name = "usuario_Ocorrencia", length = 25)
	public String getUsuarioOcorrencia() { return usuarioOcorrencia; }
	public void setUsuarioOcorrencia(String usuarioOcorrencia) {
												this.usuarioOcorrencia = usuarioOcorrencia; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_sistema_ocorrencia", nullable = true)  // nullable = false ????))
	public Date getDataSistemaOcorrencia() { return dataSistemaOcorrencia; }
	public void setDataSistemaOcorrencia(Date dataSistemaOcorrencia) {
										this.dataSistemaOcorrencia = dataSistemaOcorrencia; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "titulo_data_Retirada", nullable = true)  // nullable = false ????))
	public Date getTituloDataRetirada() { return tituloDataRetirada; }
	public void setTituloDataRetirada(Date tituloDataRetirada) {
											this.tituloDataRetirada = tituloDataRetirada; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "Titulo_Data_Repasse", nullable = true)  // nullable = false ????))
	public Date getTituloDataRepasse() { return tituloDataRepasse; }
	public void setTituloDataRepasse(Date tituloDataRepasse) {
											this.tituloDataRepasse = tituloDataRepasse; }

  	@Column(name = "titulo_Cheque_Repasse", length = 20)
	public String getTituloChequeRepasse() { return tituloChequeRepasse; }
	public void setTituloChequeRepasse(String tituloChequeRepasse) {
											this.tituloChequeRepasse = tituloChequeRepasse; }

  	@Column(name = "observacao_Titulo")
	public String getObservacaoTitulo() { return observacaoTitulo; }
	public void setObservacaoTitulo(String observacaoTitulo) { 
												this.observacaoTitulo = observacaoTitulo; }

  	@Column(name = "motivo", length = 30)
	public String getMotivo() { return motivo; }
	public void setMotivo(String motivo) { this.motivo = motivo; }

  	@Column(name = "custas_Pagar", length = 1)
	public String getCustasPagar() { return custasPagar; }
	public void setCustasPagar(String custasPagar) { this.custasPagar = custasPagar; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "Data_Resultado_Intimacao", nullable = true)  // nullable = false ????))
	public Date getDataResultadoIntimacao() { return dataResultadoIntimacao; }
	public void setDataResultadoIntimacao(Date dataResultadoIntimacao) {
									this.dataResultadoIntimacao = dataResultadoIntimacao; }

  	@Column(name = "resultado_Intimacao", length = 1)
	public String getResultadoIntimacao() { return resultadoIntimacao; }
	public void setResultadoIntimacao(String resultadoIntimacao) {
											this.resultadoIntimacao = resultadoIntimacao; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "Data_Envio_Correio", nullable = true)  // nullable = false ????))
	public Date getDataEnvioCorreio() { return dataEnvioCorreio; }
	public void setDataEnvioCorreio(Date dataEnvioCorreio) {
												this.dataEnvioCorreio = dataEnvioCorreio; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "Data_Retorno_Correio", nullable = true)  // nullable = false ????))
	public Date getDataRetornoCorreio() { return dataRetornoCorreio; }
	public void setDataRetornoCorreio(Date dataRetornoCorreio) {
											this.dataRetornoCorreio = dataRetornoCorreio; }

  	@Column(name = "resultado_Correio", length = 1)
	public String getResultadoCorreio() { return resultadoCorreio; }
	public void setResultadoCorreio(String resultadoCorreio) {
												this.resultadoCorreio = resultadoCorreio; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_edital", nullable = true) // nullable = false ????
	public Date getDataEdital() { return dataEdital; }
	public void setDataEdital(Date dataEdital) { this.dataEdital = dataEdital; }

  	@Column(name = "intimado", length = 15)
	public String getIntimado() { return intimado; }
	public void setIntimado(String intimado) { this.intimado = intimado; }

  	@Column(name = "respondido", length = 1)
	public String getRespondido() { return respondido; }
	public void setRespondido(String respondido) { this.respondido = respondido; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Envio_Suspensao_Efeito", nullable = true) // nullable = false ????
	public Date getDataEnvioSuspensaoEfeito() { return dataEnvioSuspensaoEfeito; }
	public void setDataEnvioSuspensaoEfeito(Date dataEnvioSuspensaoEfeito) {
								this.dataEnvioSuspensaoEfeito = dataEnvioSuspensaoEfeito; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Resultado_Suspensao_Efeito", nullable = true)  // nullable = false ????))
	public Date getDataResultadoSuspensaoEfeito() { return dataResultadoSuspensaoEfeito; }
	public void setDataResultadoSuspensaoEfeito(Date dataResultadoSuspensaoEfeito) {
						this.dataResultadoSuspensaoEfeito = dataResultadoSuspensaoEfeito; }

  	@Column(name = "resultado_Suspensao_Efeito", length = 1, nullable = true)  // nullable = false ????)))
	public String getResultadoSuspensaoEfeito() { return resultadoSuspensaoEfeito; }
	public void setResultadoSuspensaoEfeito(String resultadoSuspensaoEfeito) {
								this.resultadoSuspensaoEfeito = resultadoSuspensaoEfeito; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Envio_Sustacao_Protesto", nullable = true)  // nullable = false ????))
	public Date getDataEnvioSustacaoProtesto() { return dataEnvioSustacaoProtesto; }
	public void setDataEnvioSustacaoProtesto(Date dataEnvioSustacaoProtesto) {
								this.dataEnvioSustacaoProtesto = dataEnvioSustacaoProtesto; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Resultado_Sustacao_Protesto", nullable = true)  // nullable = false ????))
	public Date getDataResultadoSustacaoProtesto() { return dataResultadoSustacaoProtesto; }
	public void setDataResultadoSustacaoProtesto(Date dataResultadoSustacaoProtesto) {
						this.dataResultadoSustacaoProtesto = dataResultadoSustacaoProtesto; }

  	@Column(name = "resultado_Sustacao_Protesto", length = 1)
	public String getResultadoSustacaoProtesto() { return resultadoSustacaoProtesto; }
	public void setResultadoSustacaoProtesto(String resultadoSustacaoProtesto) {
								this.resultadoSustacaoProtesto = resultadoSustacaoProtesto; }

	@Column(name = "titulo_Valor_Reembolso_Edital", precision = 10, scale = 2, nullable = true)  // nullable = false ????)))
	public BigDecimal getTituloValorReembolsoEdital() { return tituloValorReembolsoEdital; }
	public void setTituloValorReembolsoEdital(BigDecimal tituloValorReembolsoEdital) {
								this.tituloValorReembolsoEdital = tituloValorReembolsoEdital; }

  	@Column(name = "numero_Cliente", length = 20)
	public String getNumeroCliente() { return numeroCliente; }
	public void setNumeroCliente(String numeroCliente) { this.numeroCliente = numeroCliente; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_aviso", nullable = true) //  nullable = false???
	public Date getDataAviso() { return dataAviso; }
	public void setDataAviso(Date dataAviso) { this.dataAviso = dataAviso; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_Resultado_Aviso", nullable = true) //  nullable = false???
	public Date getDataResultadoAviso() { return dataResultadoAviso; }
	public void setDataResultadoAviso(Date dataResultadoAviso) {
												this.dataResultadoAviso = dataResultadoAviso; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpPessoa_Titulo_Apresentante_id", nullable = true)
	public MpPessoaTitulo getMpPessoaTituloApresentante() { return mpPessoaTituloApresentante; }
	public void setMpPessoaTituloApresentante(MpPessoaTitulo mpPessoaTituloApresentante) {
								this.mpPessoaTituloApresentante = mpPessoaTituloApresentante; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpPessoa_Titulo_Favorecido_id", nullable = true)
	public MpPessoaTitulo getMpPessoaTituloFavorecido() { return mpPessoaTituloFavorecido; }
	public void setMpPessoaTituloFavorecido(MpPessoaTitulo mpPessoaTituloFavorecido) {
									this.mpPessoaTituloFavorecido = mpPessoaTituloFavorecido; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpPessoa_Titulo_Sacador_id", nullable = true)
	public MpPessoaTitulo getMpPessoaTituloSacador() { return mpPessoaTituloSacador; }
	public void setMpPessoaTituloSacador(MpPessoaTitulo mpPessoaTituloSacador) {
										this.mpPessoaTituloSacador = mpPessoaTituloSacador; }

  	@Column(name = "numero_Livro", length = 50)
	public String getNumeroLivro() { return numeroLivro; }
	public void setNumeroLivro(String numeroLivro) { this.numeroLivro = numeroLivro; }

  	@Column(name = "numero_Folha", length = 50)
	public String getNumeroFolha() { return numeroFolha; }
	public void setNumeroFolha(String numeroFolha) { this.numeroFolha = numeroFolha; }

	//	private String encerrado; // Virou indDigital !
  	@Column(name = "ind_Digital")
	public Boolean getIndDigital() { return indDigital; }
	public void setIndDigital(Boolean indDigital) { this.indDigital = indDigital; }

  	@Column(name = "codigo_Irregularidade", length = 2)
	public String getCodigoIrregularidade() { return codigoIrregularidade; }
	public void setCodigoIrregularidade(String codigoIrregularidade) {
		this.codigoIrregularidade = codigoIrregularidade; }

  	@Column(name = "nihil", length = 1)
	public String getNihil() { return nihil; }
	public void setNihil(String nihil) { this.nihil = nihil; }

  	@Column(name = "pagamento_AVista", length = 2)
	public String getPagamentoAVista() { return pagamentoAVista; }
	public void setPagamentoAVista(String pagamentoAVista) {
											this.pagamentoAVista = pagamentoAVista; }

  	@Column(name = "talao_Numero_Livro", length = 50)
	public String getTalaoNumeroLivro() { return talaoNumeroLivro; }
	public void setTalaoNumeroLivro(String talaoNumeroLivro) {
											this.talaoNumeroLivro = talaoNumeroLivro; }

  	@Column(name = "convenio_Numero_Livro", length = 50)
	public String getConvenioNumeroLivro() { return convenioNumeroLivro; }
	public void setConvenioNumeroLivro(String convenioNumeroLivro) {
									this.convenioNumeroLivro = convenioNumeroLivro; }

  	@Column(name = "numero_Talao_3Oficio", length = 8)
	public String getNumeroTalao3Oficio() { return numeroTalao3Oficio; }
	public void setNumeroTalao3Oficio(String numeroTalao3Oficio) {
									this.numeroTalao3Oficio = numeroTalao3Oficio; }

  	@Column(name = "aceite", length = 1)
	public String getAceite() { return aceite; }
	public void setAceite(String aceite) { this.aceite = aceite; }

 	@Column(name = "fins_F_Alimentares", length = 1)
	public String getFinsFAlimentares() { return finsFAlimentares; }
	public void setFinsFAlimentares(String finsFAlimentares) { 
										this.finsFAlimentares = finsFAlimentares; }

 	@Column(name = "arquivamento", length = 15)
	public String getArquivamento() { return arquivamento; }
	public void setArquivamento(String arquivamento) { this.arquivamento = arquivamento; }

	// ---
	
	@Transient
	public void recalcularTotalEmolumento() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
//		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
//		
//		for (MpItemPedido item : this.getMpItens()) {
//			if (item.getMpProduto() != null && item.getMpProduto().getId() != null) {
//				total = total.add(item.getValorTotal());
//			}
//		}
		//
		this.setTotalEmolumento(total);
	}

	@Transient
	public void recalcularTotalPagar() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
//		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
//		
//		for (MpItemPedido item : this.getMpItens()) {
//			if (item.getMpProduto() != null && item.getMpProduto().getId() != null) {
//				total = total.add(item.getValorTotal());
//			}
//		}
		//
		this.setTotalPagar(total);
	}

	@Transient
	public void recalcularTotalDeposito() {
		//
		BigDecimal total = BigDecimal.ZERO;
		
//		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
//		
//		for (MpItemPedido item : this.getMpItens()) {
//			if (item.getMpProduto() != null && item.getMpProduto().getId() != null) {
//				total = total.add(item.getValorTotal());
//			}
//		}
		//
		this.setTotalDeposito(total);
	}
	
}
