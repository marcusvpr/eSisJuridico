
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.ecartorio_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ArrayOfExigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfExigencia");
    private final static QName _ArrayOfRetorno_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfRetorno");
    private final static QName _ArrayOfExigenciaMensagem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfExigenciaMensagem");
    private final static QName _Exigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "Exigencia");
    private final static QName _ArrayOfPedido_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfPedido");
    private final static QName _Retorno_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "Retorno");
    private final static QName _ExigenciaMensagem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ExigenciaMensagem");
    private final static QName _ArrayOfRepasse_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfRepasse");
    private final static QName _ArrayOfPropriedadeDominio_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ArrayOfPropriedadeDominio");
    private final static QName _Repasse_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "Repasse");
    private final static QName _PropriedadeDominio_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "PropriedadeDominio");
    private final static QName _Pedido_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "Pedido");
    private final static QName _RetornoIdExigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "id_exigencia");
    private final static QName _RetornoIdCerp_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "id_cerp");
    private final static QName _RetornoCdRet_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "cd_ret");
    private final static QName _RetornoDsRet_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_ret");
    private final static QName _RetornoNuExigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_exigencia");
    private final static QName _ExigenciaMensagemDsNomeDestino_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_nome_destino");
    private final static QName _ExigenciaMensagemDsMensagem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_mensagem");
    private final static QName _ExigenciaMensagemDsNomeOrigem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_nome_origem");
    private final static QName _ExigenciaMensagemDtMensagem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_mensagem");
    private final static QName _ExigenciaDtConclusao_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_conclusao");
    private final static QName _ExigenciaDtPrazoConclusao_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_prazo_conclusao");
    private final static QName _ExigenciaDsExigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_exigencia");
    private final static QName _ExigenciaIdUsuarioOrigem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "id_usuario_origem");
    private final static QName _ExigenciaDtExigencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_exigencia");
    private final static QName _ExigenciaVlTransacao_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "vl_transacao");
    private final static QName _RepasseDsItem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_item");
    private final static QName _RepasseCdAgencia_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "cd_agencia");
    private final static QName _RepasseCdOrigem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "cd_origem");
    private final static QName _RepasseDtRepasse_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_repasse");
    private final static QName _RepasseVlRepasse_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "vl_repasse");
    private final static QName _RepasseDsServico_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_servico");
    private final static QName _RepasseCdConta_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "cd_conta");
    private final static QName _RepasseDsResumoPedido_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_resumo_pedido");
    private final static QName _PropriedadeDominioCdDominio_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "cd_dominio");
    private final static QName _PropriedadeDominioVlDominio_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "vl_dominio");
    private final static QName _PedidoDsComplementoB_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_complemento_b");
    private final static QName _PedidoDsPdFinalidadeExt_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_pd_finalidade_ext");
    private final static QName _PedidoDtAto_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_ato");
    private final static QName _PedidoNmBusca_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nm_busca");
    private final static QName _PedidoNmLivro_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nm_livro");
    private final static QName _PedidoDsComplementoA_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_complemento_a");
    private final static QName _PedidoNuCpfCnpjBusca_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_cpf_cnpj_busca");
    private final static QName _PedidoDtPagamento_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_pagamento");
    private final static QName _PedidoDtNascimento_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_nascimento");
    private final static QName _PedidoNuRgi_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_rgi");
    private final static QName _PedidoDtSla_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_sla");
    private final static QName _PedidoNuNumeroB_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_numero_b");
    private final static QName _PedidoNuNumeroA_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_numero_a");
    private final static QName _PedidoNuCpfCnpj_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_cpf_cnpj");
    private final static QName _PedidoDsPdDsTipoPedidoExt_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_pd_ds_tipo_pedido_ext");
    private final static QName _PedidoNuAtoOrigem_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_ato_origem");
    private final static QName _PedidoDsBairro_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_bairro");
    private final static QName _PedidoDtStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_status");
    private final static QName _PedidoDsPdStatusPedidoExt_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_pd_status_pedido_ext");
    private final static QName _PedidoIdCerpOriginal_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "id_cerp_original");
    private final static QName _PedidoNmNaturezaAto_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nm_natureza_ato");
    private final static QName _PedidoNuValorTotal_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_valor_total");
    private final static QName _PedidoDtPedido_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "dt_pedido");
    private final static QName _PedidoNmPai_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nm_pai");
    private final static QName _PedidoDsLogradouro_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_logradouro");
    private final static QName _PedidoNuLogradouro_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_logradouro");
    private final static QName _PedidoDsPdDsTipoDocExt_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_pd_ds_tipo_doc_ext");
    private final static QName _PedidoNmMae_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nm_mae");
    private final static QName _PedidoNuAto_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_ato");
    private final static QName _PedidoDsRequerente_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_requerente");
    private final static QName _PedidoTpLogradouro_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "tp_logradouro");
    private final static QName _PedidoNuFolha_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_folha");
    private final static QName _PedidoNuSeloOriginal_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_selo_original");
    private final static QName _PedidoNuRequisicao_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_requisicao");
    private final static QName _PedidoDsObsAto_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_obs_ato");
    private final static QName _PedidoDsPdTipoAtoExt_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "ds_pd_tipo_ato_ext");
    private final static QName _PedidoNuAleatorioOriginal_QNAME = new QName("http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", "nu_aleatorio_original");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.ecartorio_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfExigencia }
     * 
     */
    public ArrayOfExigencia createArrayOfExigencia() {
        return new ArrayOfExigencia();
    }

    /**
     * Create an instance of {@link ArrayOfExigenciaMensagem }
     * 
     */
    public ArrayOfExigenciaMensagem createArrayOfExigenciaMensagem() {
        return new ArrayOfExigenciaMensagem();
    }

    /**
     * Create an instance of {@link ArrayOfRepasse }
     * 
     */
    public ArrayOfRepasse createArrayOfRepasse() {
        return new ArrayOfRepasse();
    }

    /**
     * Create an instance of {@link ArrayOfRetorno }
     * 
     */
    public ArrayOfRetorno createArrayOfRetorno() {
        return new ArrayOfRetorno();
    }

    /**
     * Create an instance of {@link ArrayOfPedido }
     * 
     */
    public ArrayOfPedido createArrayOfPedido() {
        return new ArrayOfPedido();
    }

    /**
     * Create an instance of {@link ArrayOfPropriedadeDominio }
     * 
     */
    public ArrayOfPropriedadeDominio createArrayOfPropriedadeDominio() {
        return new ArrayOfPropriedadeDominio();
    }

    /**
     * Create an instance of {@link Repasse }
     * 
     */
    public Repasse createRepasse() {
        return new Repasse();
    }

    /**
     * Create an instance of {@link Exigencia }
     * 
     */
    public Exigencia createExigencia() {
        return new Exigencia();
    }

    /**
     * Create an instance of {@link ExigenciaMensagem }
     * 
     */
    public ExigenciaMensagem createExigenciaMensagem() {
        return new ExigenciaMensagem();
    }

    /**
     * Create an instance of {@link Pedido }
     * 
     */
    public Pedido createPedido() {
        return new Pedido();
    }

    /**
     * Create an instance of {@link Retorno }
     * 
     */
    public Retorno createRetorno() {
        return new Retorno();
    }

    /**
     * Create an instance of {@link PropriedadeDominio }
     * 
     */
    public PropriedadeDominio createPropriedadeDominio() {
        return new PropriedadeDominio();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfExigencia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfExigencia")
    public JAXBElement<ArrayOfExigencia> createArrayOfExigencia(ArrayOfExigencia value) {
        return new JAXBElement<ArrayOfExigencia>(_ArrayOfExigencia_QNAME, ArrayOfExigencia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfRetorno")
    public JAXBElement<ArrayOfRetorno> createArrayOfRetorno(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_ArrayOfRetorno_QNAME, ArrayOfRetorno.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfExigenciaMensagem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfExigenciaMensagem")
    public JAXBElement<ArrayOfExigenciaMensagem> createArrayOfExigenciaMensagem(ArrayOfExigenciaMensagem value) {
        return new JAXBElement<ArrayOfExigenciaMensagem>(_ArrayOfExigenciaMensagem_QNAME, ArrayOfExigenciaMensagem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exigencia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "Exigencia")
    public JAXBElement<Exigencia> createExigencia(Exigencia value) {
        return new JAXBElement<Exigencia>(_Exigencia_QNAME, Exigencia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPedido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfPedido")
    public JAXBElement<ArrayOfPedido> createArrayOfPedido(ArrayOfPedido value) {
        return new JAXBElement<ArrayOfPedido>(_ArrayOfPedido_QNAME, ArrayOfPedido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Retorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "Retorno")
    public JAXBElement<Retorno> createRetorno(Retorno value) {
        return new JAXBElement<Retorno>(_Retorno_QNAME, Retorno.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExigenciaMensagem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ExigenciaMensagem")
    public JAXBElement<ExigenciaMensagem> createExigenciaMensagem(ExigenciaMensagem value) {
        return new JAXBElement<ExigenciaMensagem>(_ExigenciaMensagem_QNAME, ExigenciaMensagem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRepasse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfRepasse")
    public JAXBElement<ArrayOfRepasse> createArrayOfRepasse(ArrayOfRepasse value) {
        return new JAXBElement<ArrayOfRepasse>(_ArrayOfRepasse_QNAME, ArrayOfRepasse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPropriedadeDominio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ArrayOfPropriedadeDominio")
    public JAXBElement<ArrayOfPropriedadeDominio> createArrayOfPropriedadeDominio(ArrayOfPropriedadeDominio value) {
        return new JAXBElement<ArrayOfPropriedadeDominio>(_ArrayOfPropriedadeDominio_QNAME, ArrayOfPropriedadeDominio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Repasse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "Repasse")
    public JAXBElement<Repasse> createRepasse(Repasse value) {
        return new JAXBElement<Repasse>(_Repasse_QNAME, Repasse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropriedadeDominio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "PropriedadeDominio")
    public JAXBElement<PropriedadeDominio> createPropriedadeDominio(PropriedadeDominio value) {
        return new JAXBElement<PropriedadeDominio>(_PropriedadeDominio_QNAME, PropriedadeDominio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pedido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "Pedido")
    public JAXBElement<Pedido> createPedido(Pedido value) {
        return new JAXBElement<Pedido>(_Pedido_QNAME, Pedido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_exigencia", scope = Retorno.class)
    public JAXBElement<Integer> createRetornoIdExigencia(Integer value) {
        return new JAXBElement<Integer>(_RetornoIdExigencia_QNAME, Integer.class, Retorno.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_cerp", scope = Retorno.class)
    public JAXBElement<String> createRetornoIdCerp(String value) {
        return new JAXBElement<String>(_RetornoIdCerp_QNAME, String.class, Retorno.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_ret", scope = Retorno.class)
    public JAXBElement<Integer> createRetornoCdRet(Integer value) {
        return new JAXBElement<Integer>(_RetornoCdRet_QNAME, Integer.class, Retorno.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_ret", scope = Retorno.class)
    public JAXBElement<String> createRetornoDsRet(String value) {
        return new JAXBElement<String>(_RetornoDsRet_QNAME, String.class, Retorno.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_exigencia", scope = Retorno.class)
    public JAXBElement<Integer> createRetornoNuExigencia(Integer value) {
        return new JAXBElement<Integer>(_RetornoNuExigencia_QNAME, Integer.class, Retorno.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_exigencia", scope = ExigenciaMensagem.class)
    public JAXBElement<Integer> createExigenciaMensagemIdExigencia(Integer value) {
        return new JAXBElement<Integer>(_RetornoIdExigencia_QNAME, Integer.class, ExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_nome_destino", scope = ExigenciaMensagem.class)
    public JAXBElement<String> createExigenciaMensagemDsNomeDestino(String value) {
        return new JAXBElement<String>(_ExigenciaMensagemDsNomeDestino_QNAME, String.class, ExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_mensagem", scope = ExigenciaMensagem.class)
    public JAXBElement<String> createExigenciaMensagemDsMensagem(String value) {
        return new JAXBElement<String>(_ExigenciaMensagemDsMensagem_QNAME, String.class, ExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_nome_origem", scope = ExigenciaMensagem.class)
    public JAXBElement<String> createExigenciaMensagemDsNomeOrigem(String value) {
        return new JAXBElement<String>(_ExigenciaMensagemDsNomeOrigem_QNAME, String.class, ExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_mensagem", scope = ExigenciaMensagem.class)
    public JAXBElement<String> createExigenciaMensagemDtMensagem(String value) {
        return new JAXBElement<String>(_ExigenciaMensagemDtMensagem_QNAME, String.class, ExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_cerp", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaIdCerp(String value) {
        return new JAXBElement<String>(_RetornoIdCerp_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_conclusao", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaDtConclusao(String value) {
        return new JAXBElement<String>(_ExigenciaDtConclusao_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_prazo_conclusao", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaDtPrazoConclusao(String value) {
        return new JAXBElement<String>(_ExigenciaDtPrazoConclusao_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_exigencia", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaDsExigencia(String value) {
        return new JAXBElement<String>(_ExigenciaDsExigencia_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_usuario_origem", scope = Exigencia.class)
    public JAXBElement<Integer> createExigenciaIdUsuarioOrigem(Integer value) {
        return new JAXBElement<Integer>(_ExigenciaIdUsuarioOrigem_QNAME, Integer.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_exigencia", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaDtExigencia(String value) {
        return new JAXBElement<String>(_ExigenciaDtExigencia_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "vl_transacao", scope = Exigencia.class)
    public JAXBElement<String> createExigenciaVlTransacao(String value) {
        return new JAXBElement<String>(_ExigenciaVlTransacao_QNAME, String.class, Exigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_item", scope = Repasse.class)
    public JAXBElement<String> createRepasseDsItem(String value) {
        return new JAXBElement<String>(_RepasseDsItem_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_agencia", scope = Repasse.class)
    public JAXBElement<String> createRepasseCdAgencia(String value) {
        return new JAXBElement<String>(_RepasseCdAgencia_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_origem", scope = Repasse.class)
    public JAXBElement<String> createRepasseCdOrigem(String value) {
        return new JAXBElement<String>(_RepasseCdOrigem_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_repasse", scope = Repasse.class)
    public JAXBElement<String> createRepasseDtRepasse(String value) {
        return new JAXBElement<String>(_RepasseDtRepasse_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_cerp", scope = Repasse.class)
    public JAXBElement<String> createRepasseIdCerp(String value) {
        return new JAXBElement<String>(_RetornoIdCerp_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "vl_repasse", scope = Repasse.class)
    public JAXBElement<BigDecimal> createRepasseVlRepasse(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_RepasseVlRepasse_QNAME, BigDecimal.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_servico", scope = Repasse.class)
    public JAXBElement<String> createRepasseDsServico(String value) {
        return new JAXBElement<String>(_RepasseDsServico_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_conta", scope = Repasse.class)
    public JAXBElement<String> createRepasseCdConta(String value) {
        return new JAXBElement<String>(_RepasseCdConta_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_resumo_pedido", scope = Repasse.class)
    public JAXBElement<String> createRepasseDsResumoPedido(String value) {
        return new JAXBElement<String>(_RepasseDsResumoPedido_QNAME, String.class, Repasse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_dominio", scope = PropriedadeDominio.class)
    public JAXBElement<String> createPropriedadeDominioCdDominio(String value) {
        return new JAXBElement<String>(_PropriedadeDominioCdDominio_QNAME, String.class, PropriedadeDominio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "vl_dominio", scope = PropriedadeDominio.class)
    public JAXBElement<String> createPropriedadeDominioVlDominio(String value) {
        return new JAXBElement<String>(_PropriedadeDominioVlDominio_QNAME, String.class, PropriedadeDominio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_complemento_b", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsComplementoB(String value) {
        return new JAXBElement<String>(_PedidoDsComplementoB_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_pd_finalidade_ext", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsPdFinalidadeExt(String value) {
        return new JAXBElement<String>(_PedidoDsPdFinalidadeExt_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_ato", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtAto(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtAto_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nm_busca", scope = Pedido.class)
    public JAXBElement<String> createPedidoNmBusca(String value) {
        return new JAXBElement<String>(_PedidoNmBusca_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nm_livro", scope = Pedido.class)
    public JAXBElement<String> createPedidoNmLivro(String value) {
        return new JAXBElement<String>(_PedidoNmLivro_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_complemento_a", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsComplementoA(String value) {
        return new JAXBElement<String>(_PedidoDsComplementoA_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_cpf_cnpj_busca", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuCpfCnpjBusca(String value) {
        return new JAXBElement<String>(_PedidoNuCpfCnpjBusca_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_pagamento", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtPagamento(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtPagamento_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_repasse", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtRepasse(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RepasseDtRepasse_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_nascimento", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtNascimento(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtNascimento_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_rgi", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuRgi(String value) {
        return new JAXBElement<String>(_PedidoNuRgi_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_sla", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtSla(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtSla_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_numero_b", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuNumeroB(String value) {
        return new JAXBElement<String>(_PedidoNuNumeroB_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_numero_a", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuNumeroA(String value) {
        return new JAXBElement<String>(_PedidoNuNumeroA_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_cpf_cnpj", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuCpfCnpj(String value) {
        return new JAXBElement<String>(_PedidoNuCpfCnpj_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_pd_ds_tipo_pedido_ext", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsPdDsTipoPedidoExt(String value) {
        return new JAXBElement<String>(_PedidoDsPdDsTipoPedidoExt_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_ato_origem", scope = Pedido.class)
    public JAXBElement<Integer> createPedidoNuAtoOrigem(Integer value) {
        return new JAXBElement<Integer>(_PedidoNuAtoOrigem_QNAME, Integer.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_bairro", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsBairro(String value) {
        return new JAXBElement<String>(_PedidoDsBairro_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_status", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtStatus(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtStatus_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_pd_status_pedido_ext", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsPdStatusPedidoExt(String value) {
        return new JAXBElement<String>(_PedidoDsPdStatusPedidoExt_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_cerp_original", scope = Pedido.class)
    public JAXBElement<String> createPedidoIdCerpOriginal(String value) {
        return new JAXBElement<String>(_PedidoIdCerpOriginal_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nm_natureza_ato", scope = Pedido.class)
    public JAXBElement<String> createPedidoNmNaturezaAto(String value) {
        return new JAXBElement<String>(_PedidoNmNaturezaAto_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_valor_total", scope = Pedido.class)
    public JAXBElement<BigDecimal> createPedidoNuValorTotal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_PedidoNuValorTotal_QNAME, BigDecimal.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "dt_pedido", scope = Pedido.class)
    public JAXBElement<XMLGregorianCalendar> createPedidoDtPedido(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PedidoDtPedido_QNAME, XMLGregorianCalendar.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "cd_origem", scope = Pedido.class)
    public JAXBElement<String> createPedidoCdOrigem(String value) {
        return new JAXBElement<String>(_RepasseCdOrigem_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nm_pai", scope = Pedido.class)
    public JAXBElement<String> createPedidoNmPai(String value) {
        return new JAXBElement<String>(_PedidoNmPai_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_logradouro", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsLogradouro(String value) {
        return new JAXBElement<String>(_PedidoDsLogradouro_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "id_cerp", scope = Pedido.class)
    public JAXBElement<String> createPedidoIdCerp(String value) {
        return new JAXBElement<String>(_RetornoIdCerp_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_logradouro", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuLogradouro(String value) {
        return new JAXBElement<String>(_PedidoNuLogradouro_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_pd_ds_tipo_doc_ext", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsPdDsTipoDocExt(String value) {
        return new JAXBElement<String>(_PedidoDsPdDsTipoDocExt_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nm_mae", scope = Pedido.class)
    public JAXBElement<String> createPedidoNmMae(String value) {
        return new JAXBElement<String>(_PedidoNmMae_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_ato", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuAto(String value) {
        return new JAXBElement<String>(_PedidoNuAto_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_requerente", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsRequerente(String value) {
        return new JAXBElement<String>(_PedidoDsRequerente_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "tp_logradouro", scope = Pedido.class)
    public JAXBElement<String> createPedidoTpLogradouro(String value) {
        return new JAXBElement<String>(_PedidoTpLogradouro_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_folha", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuFolha(String value) {
        return new JAXBElement<String>(_PedidoNuFolha_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_selo_original", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuSeloOriginal(String value) {
        return new JAXBElement<String>(_PedidoNuSeloOriginal_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_requisicao", scope = Pedido.class)
    public JAXBElement<Long> createPedidoNuRequisicao(Long value) {
        return new JAXBElement<Long>(_PedidoNuRequisicao_QNAME, Long.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_obs_ato", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsObsAto(String value) {
        return new JAXBElement<String>(_PedidoDsObsAto_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "ds_pd_tipo_ato_ext", scope = Pedido.class)
    public JAXBElement<String> createPedidoDsPdTipoAtoExt(String value) {
        return new JAXBElement<String>(_PedidoDsPdTipoAtoExt_QNAME, String.class, Pedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", name = "nu_aleatorio_original", scope = Pedido.class)
    public JAXBElement<String> createPedidoNuAleatorioOriginal(String value) {
        return new JAXBElement<String>(_PedidoNuAleatorioOriginal_QNAME, String.class, Pedido.class, value);
    }

}
