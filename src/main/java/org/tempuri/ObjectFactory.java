
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfExigencia;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfExigenciaMensagem;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPropriedadeDominio;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfRepasse;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfRetorno;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
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

    private final static QName _ResponderExigenciaNuExigencia_QNAME = new QName("http://tempuri.org/", "nu_exigencia");
    private final static QName _ResponderExigenciaIdCerp_QNAME = new QName("http://tempuri.org/", "id_cerp");
    private final static QName _ResponderExigenciaDsResposta_QNAME = new QName("http://tempuri.org/", "ds_resposta");
    private final static QName _MarcarPedidoCartorioResponseMarcarPedidoCartorioResult_QNAME = new QName("http://tempuri.org/", "marcarPedidoCartorioResult");
    private final static QName _MarcarStatusResponseMarcarStatusResult_QNAME = new QName("http://tempuri.org/", "marcarStatusResult");
    private final static QName _ListarRepassesResponseListarRepassesResult_QNAME = new QName("http://tempuri.org/", "listarRepassesResult");
    private final static QName _ListarPedidosDtPagamentoFin_QNAME = new QName("http://tempuri.org/", "dt_pagamento_fin");
    private final static QName _ListarPedidosStatusPedido_QNAME = new QName("http://tempuri.org/", "status_pedido");
    private final static QName _ListarPedidosIdServico_QNAME = new QName("http://tempuri.org/", "id_servico");
    private final static QName _ListarPedidosDtPedidoFin_QNAME = new QName("http://tempuri.org/", "dt_pedido_fin");
    private final static QName _ListarPedidosDtStatusPedidoFinal_QNAME = new QName("http://tempuri.org/", "dt_status_pedido_final");
    private final static QName _ListarPedidosNuCpfCnpj_QNAME = new QName("http://tempuri.org/", "nu_cpf_cnpj");
    private final static QName _ListarPedidosNuRequisicao_QNAME = new QName("http://tempuri.org/", "nu_requisicao");
    private final static QName _ListarPedidosDsPdStatusPedidoExt_QNAME = new QName("http://tempuri.org/", "ds_pd_status_pedido_ext");
    private final static QName _ListarPedidosDtPedidoIni_QNAME = new QName("http://tempuri.org/", "dt_pedido_ini");
    private final static QName _ListarPedidosDtStatusPedidoInicial_QNAME = new QName("http://tempuri.org/", "dt_status_pedido_inicial");
    private final static QName _ListarPedidosTipoAto_QNAME = new QName("http://tempuri.org/", "tipo_ato");
    private final static QName _ListarPedidosDtPagamentoIni_QNAME = new QName("http://tempuri.org/", "dt_pagamento_ini");
    private final static QName _CumprirExigenciaResponseCumprirExigenciaResult_QNAME = new QName("http://tempuri.org/", "cumprirExigenciaResult");
    private final static QName _ListarPropriedadeResponseListarPropriedadeResult_QNAME = new QName("http://tempuri.org/", "listarPropriedadeResult");
    private final static QName _ListarRepassesDtRepasseFinal_QNAME = new QName("http://tempuri.org/", "dt_repasse_final");
    private final static QName _ListarRepassesDtRepasseInicial_QNAME = new QName("http://tempuri.org/", "dt_repasse_inicial");
    private final static QName _ListarRepassesModelo_QNAME = new QName("http://tempuri.org/", "modelo");
    private final static QName _ListarPropriedadeCdDominio_QNAME = new QName("http://tempuri.org/", "cd_dominio");
    private final static QName _ListarPropriedadeIdDominioPai_QNAME = new QName("http://tempuri.org/", "id_dominio_pai");
    private final static QName _ListarPropriedadePropriedade_QNAME = new QName("http://tempuri.org/", "propriedade");
    private final static QName _MarcarStatusDsStatus_QNAME = new QName("http://tempuri.org/", "ds_status");
    private final static QName _MarcarPedidoCartorioCdOrigem_QNAME = new QName("http://tempuri.org/", "cd_origem");
    private final static QName _MarcarExigenciaResponseMarcarExigenciaResult_QNAME = new QName("http://tempuri.org/", "marcarExigenciaResult");
    private final static QName _ListarExigenciaDtConclusaoFin_QNAME = new QName("http://tempuri.org/", "dt_conclusao_fin");
    private final static QName _ListarExigenciaDtExigenciaIni_QNAME = new QName("http://tempuri.org/", "dt_exigencia_ini");
    private final static QName _ListarExigenciaDtConclusaoIni_QNAME = new QName("http://tempuri.org/", "dt_conclusao_ini");
    private final static QName _ListarExigenciaDtExigenciaFin_QNAME = new QName("http://tempuri.org/", "dt_exigencia_fin");
    private final static QName _ListarExigenciaMensagemDtExigenciaInicial_QNAME = new QName("http://tempuri.org/", "dt_exigencia_inicial");
    private final static QName _ListarExigenciaMensagemDtExigenciaFinal_QNAME = new QName("http://tempuri.org/", "dt_exigencia_final");
    private final static QName _ListarExigenciaMensagemFlMensagemLida_QNAME = new QName("http://tempuri.org/", "fl_mensagem_lida");
    private final static QName _ListarExigenciaMensagemResponseListarExigenciaMensagemResult_QNAME = new QName("http://tempuri.org/", "listarExigenciaMensagemResult");
    private final static QName _ResponderExigenciaResponseResponderExigenciaResult_QNAME = new QName("http://tempuri.org/", "responderExigenciaResult");
    private final static QName _ListarPedidosResponseListarPedidosResult_QNAME = new QName("http://tempuri.org/", "listarPedidosResult");
    private final static QName _GerarPedidoNuCpfCnpjBusca_QNAME = new QName("http://tempuri.org/", "nu_cpf_cnpj_busca");
    private final static QName _GerarPedidoDsComplementoB_QNAME = new QName("http://tempuri.org/", "ds_complemento_b");
    private final static QName _GerarPedidoNmBusca_QNAME = new QName("http://tempuri.org/", "nm_busca");
    private final static QName _GerarPedidoNmLivro_QNAME = new QName("http://tempuri.org/", "nm_livro");
    private final static QName _GerarPedidoDtAto_QNAME = new QName("http://tempuri.org/", "dt_ato");
    private final static QName _GerarPedidoDsComplementoA_QNAME = new QName("http://tempuri.org/", "ds_complemento_a");
    private final static QName _GerarPedidoDsBairro_QNAME = new QName("http://tempuri.org/", "ds_bairro");
    private final static QName _GerarPedidoNmNaturezaAto_QNAME = new QName("http://tempuri.org/", "nm_natureza_ato");
    private final static QName _GerarPedidoDsPdTipoPedido_QNAME = new QName("http://tempuri.org/", "ds_pd_tipo_pedido");
    private final static QName _GerarPedidoDsPdTipoDoc_QNAME = new QName("http://tempuri.org/", "ds_pd_tipo_doc");
    private final static QName _GerarPedidoNuValor_QNAME = new QName("http://tempuri.org/", "nu_valor");
    private final static QName _GerarPedidoNuRgi_QNAME = new QName("http://tempuri.org/", "nu_rgi");
    private final static QName _GerarPedidoDtSla_QNAME = new QName("http://tempuri.org/", "dt_sla");
    private final static QName _GerarPedidoNuNumeroB_QNAME = new QName("http://tempuri.org/", "nu_numero_b");
    private final static QName _GerarPedidoNuNumeroA_QNAME = new QName("http://tempuri.org/", "nu_numero_a");
    private final static QName _GerarPedidoNuLogradouro_QNAME = new QName("http://tempuri.org/", "nu_logradouro");
    private final static QName _GerarPedidoNmMae_QNAME = new QName("http://tempuri.org/", "nm_mae");
    private final static QName _GerarPedidoNuAto_QNAME = new QName("http://tempuri.org/", "nu_ato");
    private final static QName _GerarPedidoNmPai_QNAME = new QName("http://tempuri.org/", "nm_pai");
    private final static QName _GerarPedidoDsLogradouro_QNAME = new QName("http://tempuri.org/", "ds_logradouro");
    private final static QName _GerarPedidoDsPdTipoAto_QNAME = new QName("http://tempuri.org/", "ds_pd_tipo_ato");
    private final static QName _GerarPedidoDsObsAto_QNAME = new QName("http://tempuri.org/", "ds_obs_ato");
    private final static QName _GerarPedidoIdRequisicao_QNAME = new QName("http://tempuri.org/", "id_requisicao");
    private final static QName _GerarPedidoDsPdFinalidade_QNAME = new QName("http://tempuri.org/", "ds_pd_finalidade");
    private final static QName _GerarPedidoTpLogradouro_QNAME = new QName("http://tempuri.org/", "tp_logradouro");
    private final static QName _GerarPedidoNuFolha_QNAME = new QName("http://tempuri.org/", "nu_folha");
    private final static QName _GerarPedidoResponseGerarPedidoResult_QNAME = new QName("http://tempuri.org/", "gerarPedidoResult");
    private final static QName _MarcarExigenciaDsExigencia_QNAME = new QName("http://tempuri.org/", "ds_exigencia");
    private final static QName _MarcarExigenciaFlTpFinanceiro_QNAME = new QName("http://tempuri.org/", "fl_tp_financeiro");
    private final static QName _MarcarExigenciaDtPrazoConclusao_QNAME = new QName("http://tempuri.org/", "dt_prazo_conclusao");
    private final static QName _ListarExigenciaResponseListarExigenciaResult_QNAME = new QName("http://tempuri.org/", "listarExigenciaResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListarExigencia }
     * 
     */
    public ListarExigencia createListarExigencia() {
        return new ListarExigencia();
    }

    /**
     * Create an instance of {@link ResponderExigencia }
     * 
     */
    public ResponderExigencia createResponderExigencia() {
        return new ResponderExigencia();
    }

    /**
     * Create an instance of {@link ListarExigenciaResponse }
     * 
     */
    public ListarExigenciaResponse createListarExigenciaResponse() {
        return new ListarExigenciaResponse();
    }

    /**
     * Create an instance of {@link ListarExigenciaMensagemResponse }
     * 
     */
    public ListarExigenciaMensagemResponse createListarExigenciaMensagemResponse() {
        return new ListarExigenciaMensagemResponse();
    }

    /**
     * Create an instance of {@link ListarPedidos }
     * 
     */
    public ListarPedidos createListarPedidos() {
        return new ListarPedidos();
    }

    /**
     * Create an instance of {@link ListarRepassesResponse }
     * 
     */
    public ListarRepassesResponse createListarRepassesResponse() {
        return new ListarRepassesResponse();
    }

    /**
     * Create an instance of {@link ListarPropriedade }
     * 
     */
    public ListarPropriedade createListarPropriedade() {
        return new ListarPropriedade();
    }

    /**
     * Create an instance of {@link MarcarStatus }
     * 
     */
    public MarcarStatus createMarcarStatus() {
        return new MarcarStatus();
    }

    /**
     * Create an instance of {@link ListarRepasses }
     * 
     */
    public ListarRepasses createListarRepasses() {
        return new ListarRepasses();
    }

    /**
     * Create an instance of {@link MarcarPedidoCartorioResponse }
     * 
     */
    public MarcarPedidoCartorioResponse createMarcarPedidoCartorioResponse() {
        return new MarcarPedidoCartorioResponse();
    }

    /**
     * Create an instance of {@link GerarPedidoResponse }
     * 
     */
    public GerarPedidoResponse createGerarPedidoResponse() {
        return new GerarPedidoResponse();
    }

    /**
     * Create an instance of {@link MarcarExigenciaResponse }
     * 
     */
    public MarcarExigenciaResponse createMarcarExigenciaResponse() {
        return new MarcarExigenciaResponse();
    }

    /**
     * Create an instance of {@link ResponderExigenciaResponse }
     * 
     */
    public ResponderExigenciaResponse createResponderExigenciaResponse() {
        return new ResponderExigenciaResponse();
    }

    /**
     * Create an instance of {@link MarcarPedidoCartorio }
     * 
     */
    public MarcarPedidoCartorio createMarcarPedidoCartorio() {
        return new MarcarPedidoCartorio();
    }

    /**
     * Create an instance of {@link MarcarStatusResponse }
     * 
     */
    public MarcarStatusResponse createMarcarStatusResponse() {
        return new MarcarStatusResponse();
    }

    /**
     * Create an instance of {@link ListarPedidosResponse }
     * 
     */
    public ListarPedidosResponse createListarPedidosResponse() {
        return new ListarPedidosResponse();
    }

    /**
     * Create an instance of {@link CumprirExigencia }
     * 
     */
    public CumprirExigencia createCumprirExigencia() {
        return new CumprirExigencia();
    }

    /**
     * Create an instance of {@link CumprirExigenciaResponse }
     * 
     */
    public CumprirExigenciaResponse createCumprirExigenciaResponse() {
        return new CumprirExigenciaResponse();
    }

    /**
     * Create an instance of {@link ListarExigenciaMensagem }
     * 
     */
    public ListarExigenciaMensagem createListarExigenciaMensagem() {
        return new ListarExigenciaMensagem();
    }

    /**
     * Create an instance of {@link GerarPedido }
     * 
     */
    public GerarPedido createGerarPedido() {
        return new GerarPedido();
    }

    /**
     * Create an instance of {@link MarcarExigencia }
     * 
     */
    public MarcarExigencia createMarcarExigencia() {
        return new MarcarExigencia();
    }

    /**
     * Create an instance of {@link ListarPropriedadeResponse }
     * 
     */
    public ListarPropriedadeResponse createListarPropriedadeResponse() {
        return new ListarPropriedadeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_exigencia", scope = ResponderExigencia.class)
    public JAXBElement<String> createResponderExigenciaNuExigencia(String value) {
        return new JAXBElement<String>(_ResponderExigenciaNuExigencia_QNAME, String.class, ResponderExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = ResponderExigencia.class)
    public JAXBElement<String> createResponderExigenciaIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, ResponderExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_resposta", scope = ResponderExigencia.class)
    public JAXBElement<String> createResponderExigenciaDsResposta(String value) {
        return new JAXBElement<String>(_ResponderExigenciaDsResposta_QNAME, String.class, ResponderExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "marcarPedidoCartorioResult", scope = MarcarPedidoCartorioResponse.class)
    public JAXBElement<ArrayOfRetorno> createMarcarPedidoCartorioResponseMarcarPedidoCartorioResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_MarcarPedidoCartorioResponseMarcarPedidoCartorioResult_QNAME, ArrayOfRetorno.class, MarcarPedidoCartorioResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "marcarStatusResult", scope = MarcarStatusResponse.class)
    public JAXBElement<ArrayOfRetorno> createMarcarStatusResponseMarcarStatusResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_MarcarStatusResponseMarcarStatusResult_QNAME, ArrayOfRetorno.class, MarcarStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRepasse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listarRepassesResult", scope = ListarRepassesResponse.class)
    public JAXBElement<ArrayOfRepasse> createListarRepassesResponseListarRepassesResult(ArrayOfRepasse value) {
        return new JAXBElement<ArrayOfRepasse>(_ListarRepassesResponseListarRepassesResult_QNAME, ArrayOfRepasse.class, ListarRepassesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_pagamento_fin", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtPagamentoFin(String value) {
        return new JAXBElement<String>(_ListarPedidosDtPagamentoFin_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "status_pedido", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosStatusPedido(String value) {
        return new JAXBElement<String>(_ListarPedidosStatusPedido_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_servico", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosIdServico(String value) {
        return new JAXBElement<String>(_ListarPedidosIdServico_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_pedido_fin", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtPedidoFin(String value) {
        return new JAXBElement<String>(_ListarPedidosDtPedidoFin_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_status_pedido_final", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtStatusPedidoFinal(String value) {
        return new JAXBElement<String>(_ListarPedidosDtStatusPedidoFinal_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_cpf_cnpj", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosNuCpfCnpj(String value) {
        return new JAXBElement<String>(_ListarPedidosNuCpfCnpj_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_requisicao", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosNuRequisicao(String value) {
        return new JAXBElement<String>(_ListarPedidosNuRequisicao_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_pd_status_pedido_ext", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDsPdStatusPedidoExt(String value) {
        return new JAXBElement<String>(_ListarPedidosDsPdStatusPedidoExt_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_pedido_ini", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtPedidoIni(String value) {
        return new JAXBElement<String>(_ListarPedidosDtPedidoIni_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_status_pedido_inicial", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtStatusPedidoInicial(String value) {
        return new JAXBElement<String>(_ListarPedidosDtStatusPedidoInicial_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "tipo_ato", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosTipoAto(String value) {
        return new JAXBElement<String>(_ListarPedidosTipoAto_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_pagamento_ini", scope = ListarPedidos.class)
    public JAXBElement<String> createListarPedidosDtPagamentoIni(String value) {
        return new JAXBElement<String>(_ListarPedidosDtPagamentoIni_QNAME, String.class, ListarPedidos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cumprirExigenciaResult", scope = CumprirExigenciaResponse.class)
    public JAXBElement<ArrayOfRetorno> createCumprirExigenciaResponseCumprirExigenciaResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_CumprirExigenciaResponseCumprirExigenciaResult_QNAME, ArrayOfRetorno.class, CumprirExigenciaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPropriedadeDominio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listarPropriedadeResult", scope = ListarPropriedadeResponse.class)
    public JAXBElement<ArrayOfPropriedadeDominio> createListarPropriedadeResponseListarPropriedadeResult(ArrayOfPropriedadeDominio value) {
        return new JAXBElement<ArrayOfPropriedadeDominio>(_ListarPropriedadeResponseListarPropriedadeResult_QNAME, ArrayOfPropriedadeDominio.class, ListarPropriedadeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_repasse_final", scope = ListarRepasses.class)
    public JAXBElement<String> createListarRepassesDtRepasseFinal(String value) {
        return new JAXBElement<String>(_ListarRepassesDtRepasseFinal_QNAME, String.class, ListarRepasses.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_servico", scope = ListarRepasses.class)
    public JAXBElement<String> createListarRepassesIdServico(String value) {
        return new JAXBElement<String>(_ListarPedidosIdServico_QNAME, String.class, ListarRepasses.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_repasse_inicial", scope = ListarRepasses.class)
    public JAXBElement<String> createListarRepassesDtRepasseInicial(String value) {
        return new JAXBElement<String>(_ListarRepassesDtRepasseInicial_QNAME, String.class, ListarRepasses.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = ListarRepasses.class)
    public JAXBElement<String> createListarRepassesIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, ListarRepasses.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "modelo", scope = ListarRepasses.class)
    public JAXBElement<String> createListarRepassesModelo(String value) {
        return new JAXBElement<String>(_ListarRepassesModelo_QNAME, String.class, ListarRepasses.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cd_dominio", scope = ListarPropriedade.class)
    public JAXBElement<String> createListarPropriedadeCdDominio(String value) {
        return new JAXBElement<String>(_ListarPropriedadeCdDominio_QNAME, String.class, ListarPropriedade.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_dominio_pai", scope = ListarPropriedade.class)
    public JAXBElement<String> createListarPropriedadeIdDominioPai(String value) {
        return new JAXBElement<String>(_ListarPropriedadeIdDominioPai_QNAME, String.class, ListarPropriedade.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "propriedade", scope = ListarPropriedade.class)
    public JAXBElement<String> createListarPropriedadePropriedade(String value) {
        return new JAXBElement<String>(_ListarPropriedadePropriedade_QNAME, String.class, ListarPropriedade.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_status", scope = MarcarStatus.class)
    public JAXBElement<String> createMarcarStatusDsStatus(String value) {
        return new JAXBElement<String>(_MarcarStatusDsStatus_QNAME, String.class, MarcarStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = MarcarStatus.class)
    public JAXBElement<String> createMarcarStatusIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, MarcarStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_servico", scope = MarcarPedidoCartorio.class)
    public JAXBElement<String> createMarcarPedidoCartorioIdServico(String value) {
        return new JAXBElement<String>(_ListarPedidosIdServico_QNAME, String.class, MarcarPedidoCartorio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cd_origem", scope = MarcarPedidoCartorio.class)
    public JAXBElement<String> createMarcarPedidoCartorioCdOrigem(String value) {
        return new JAXBElement<String>(_MarcarPedidoCartorioCdOrigem_QNAME, String.class, MarcarPedidoCartorio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = MarcarPedidoCartorio.class)
    public JAXBElement<String> createMarcarPedidoCartorioIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, MarcarPedidoCartorio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "marcarExigenciaResult", scope = MarcarExigenciaResponse.class)
    public JAXBElement<ArrayOfRetorno> createMarcarExigenciaResponseMarcarExigenciaResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_MarcarExigenciaResponseMarcarExigenciaResult_QNAME, ArrayOfRetorno.class, MarcarExigenciaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_cpf_cnpj", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaNuCpfCnpj(String value) {
        return new JAXBElement<String>(_ListarPedidosNuCpfCnpj_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_conclusao_fin", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaDtConclusaoFin(String value) {
        return new JAXBElement<String>(_ListarExigenciaDtConclusaoFin_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_exigencia_ini", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaDtExigenciaIni(String value) {
        return new JAXBElement<String>(_ListarExigenciaDtExigenciaIni_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_servico", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaIdServico(String value) {
        return new JAXBElement<String>(_ListarPedidosIdServico_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_conclusao_ini", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaDtConclusaoIni(String value) {
        return new JAXBElement<String>(_ListarExigenciaDtConclusaoIni_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_exigencia_fin", scope = ListarExigencia.class)
    public JAXBElement<String> createListarExigenciaDtExigenciaFin(String value) {
        return new JAXBElement<String>(_ListarExigenciaDtExigenciaFin_QNAME, String.class, ListarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_exigencia", scope = ListarExigenciaMensagem.class)
    public JAXBElement<String> createListarExigenciaMensagemNuExigencia(String value) {
        return new JAXBElement<String>(_ResponderExigenciaNuExigencia_QNAME, String.class, ListarExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_exigencia_inicial", scope = ListarExigenciaMensagem.class)
    public JAXBElement<String> createListarExigenciaMensagemDtExigenciaInicial(String value) {
        return new JAXBElement<String>(_ListarExigenciaMensagemDtExigenciaInicial_QNAME, String.class, ListarExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = ListarExigenciaMensagem.class)
    public JAXBElement<String> createListarExigenciaMensagemIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, ListarExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_exigencia_final", scope = ListarExigenciaMensagem.class)
    public JAXBElement<String> createListarExigenciaMensagemDtExigenciaFinal(String value) {
        return new JAXBElement<String>(_ListarExigenciaMensagemDtExigenciaFinal_QNAME, String.class, ListarExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fl_mensagem_lida", scope = ListarExigenciaMensagem.class)
    public JAXBElement<String> createListarExigenciaMensagemFlMensagemLida(String value) {
        return new JAXBElement<String>(_ListarExigenciaMensagemFlMensagemLida_QNAME, String.class, ListarExigenciaMensagem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfExigenciaMensagem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listarExigenciaMensagemResult", scope = ListarExigenciaMensagemResponse.class)
    public JAXBElement<ArrayOfExigenciaMensagem> createListarExigenciaMensagemResponseListarExigenciaMensagemResult(ArrayOfExigenciaMensagem value) {
        return new JAXBElement<ArrayOfExigenciaMensagem>(_ListarExigenciaMensagemResponseListarExigenciaMensagemResult_QNAME, ArrayOfExigenciaMensagem.class, ListarExigenciaMensagemResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "responderExigenciaResult", scope = ResponderExigenciaResponse.class)
    public JAXBElement<ArrayOfRetorno> createResponderExigenciaResponseResponderExigenciaResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_ResponderExigenciaResponseResponderExigenciaResult_QNAME, ArrayOfRetorno.class, ResponderExigenciaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPedido }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listarPedidosResult", scope = ListarPedidosResponse.class)
    public JAXBElement<ArrayOfPedido> createListarPedidosResponseListarPedidosResult(ArrayOfPedido value) {
        return new JAXBElement<ArrayOfPedido>(_ListarPedidosResponseListarPedidosResult_QNAME, ArrayOfPedido.class, ListarPedidosResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_cpf_cnpj_busca", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuCpfCnpjBusca(String value) {
        return new JAXBElement<String>(_GerarPedidoNuCpfCnpjBusca_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_complemento_b", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsComplementoB(String value) {
        return new JAXBElement<String>(_GerarPedidoDsComplementoB_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nm_busca", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNmBusca(String value) {
        return new JAXBElement<String>(_GerarPedidoNmBusca_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nm_livro", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNmLivro(String value) {
        return new JAXBElement<String>(_GerarPedidoNmLivro_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_ato", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDtAto(String value) {
        return new JAXBElement<String>(_GerarPedidoDtAto_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_complemento_a", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsComplementoA(String value) {
        return new JAXBElement<String>(_GerarPedidoDsComplementoA_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_cpf_cnpj", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuCpfCnpj(String value) {
        return new JAXBElement<String>(_ListarPedidosNuCpfCnpj_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_bairro", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsBairro(String value) {
        return new JAXBElement<String>(_GerarPedidoDsBairro_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nm_natureza_ato", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNmNaturezaAto(String value) {
        return new JAXBElement<String>(_GerarPedidoNmNaturezaAto_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_pd_tipo_pedido", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsPdTipoPedido(String value) {
        return new JAXBElement<String>(_GerarPedidoDsPdTipoPedido_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_pd_tipo_doc", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsPdTipoDoc(String value) {
        return new JAXBElement<String>(_GerarPedidoDsPdTipoDoc_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_valor", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuValor(String value) {
        return new JAXBElement<String>(_GerarPedidoNuValor_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_rgi", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuRgi(String value) {
        return new JAXBElement<String>(_GerarPedidoNuRgi_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_sla", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDtSla(String value) {
        return new JAXBElement<String>(_GerarPedidoDtSla_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_numero_b", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuNumeroB(String value) {
        return new JAXBElement<String>(_GerarPedidoNuNumeroB_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_numero_a", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuNumeroA(String value) {
        return new JAXBElement<String>(_GerarPedidoNuNumeroA_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_logradouro", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuLogradouro(String value) {
        return new JAXBElement<String>(_GerarPedidoNuLogradouro_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nm_mae", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNmMae(String value) {
        return new JAXBElement<String>(_GerarPedidoNmMae_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_ato", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuAto(String value) {
        return new JAXBElement<String>(_GerarPedidoNuAto_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_servico", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoIdServico(String value) {
        return new JAXBElement<String>(_ListarPedidosIdServico_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cd_origem", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoCdOrigem(String value) {
        return new JAXBElement<String>(_MarcarPedidoCartorioCdOrigem_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nm_pai", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNmPai(String value) {
        return new JAXBElement<String>(_GerarPedidoNmPai_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_logradouro", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsLogradouro(String value) {
        return new JAXBElement<String>(_GerarPedidoDsLogradouro_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_pd_tipo_ato", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsPdTipoAto(String value) {
        return new JAXBElement<String>(_GerarPedidoDsPdTipoAto_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_obs_ato", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsObsAto(String value) {
        return new JAXBElement<String>(_GerarPedidoDsObsAto_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_requisicao", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoIdRequisicao(String value) {
        return new JAXBElement<String>(_GerarPedidoIdRequisicao_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_pd_finalidade", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoDsPdFinalidade(String value) {
        return new JAXBElement<String>(_GerarPedidoDsPdFinalidade_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "tp_logradouro", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoTpLogradouro(String value) {
        return new JAXBElement<String>(_GerarPedidoTpLogradouro_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_folha", scope = GerarPedido.class)
    public JAXBElement<String> createGerarPedidoNuFolha(String value) {
        return new JAXBElement<String>(_GerarPedidoNuFolha_QNAME, String.class, GerarPedido.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "gerarPedidoResult", scope = GerarPedidoResponse.class)
    public JAXBElement<ArrayOfRetorno> createGerarPedidoResponseGerarPedidoResult(ArrayOfRetorno value) {
        return new JAXBElement<ArrayOfRetorno>(_GerarPedidoResponseGerarPedidoResult_QNAME, ArrayOfRetorno.class, GerarPedidoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ds_exigencia", scope = MarcarExigencia.class)
    public JAXBElement<String> createMarcarExigenciaDsExigencia(String value) {
        return new JAXBElement<String>(_MarcarExigenciaDsExigencia_QNAME, String.class, MarcarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "fl_tp_financeiro", scope = MarcarExigencia.class)
    public JAXBElement<String> createMarcarExigenciaFlTpFinanceiro(String value) {
        return new JAXBElement<String>(_MarcarExigenciaFlTpFinanceiro_QNAME, String.class, MarcarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = MarcarExigencia.class)
    public JAXBElement<String> createMarcarExigenciaIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, MarcarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_valor", scope = MarcarExigencia.class)
    public JAXBElement<String> createMarcarExigenciaNuValor(String value) {
        return new JAXBElement<String>(_GerarPedidoNuValor_QNAME, String.class, MarcarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "dt_prazo_conclusao", scope = MarcarExigencia.class)
    public JAXBElement<String> createMarcarExigenciaDtPrazoConclusao(String value) {
        return new JAXBElement<String>(_MarcarExigenciaDtPrazoConclusao_QNAME, String.class, MarcarExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfExigencia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "listarExigenciaResult", scope = ListarExigenciaResponse.class)
    public JAXBElement<ArrayOfExigencia> createListarExigenciaResponseListarExigenciaResult(ArrayOfExigencia value) {
        return new JAXBElement<ArrayOfExigencia>(_ListarExigenciaResponseListarExigenciaResult_QNAME, ArrayOfExigencia.class, ListarExigenciaResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "nu_exigencia", scope = CumprirExigencia.class)
    public JAXBElement<String> createCumprirExigenciaNuExigencia(String value) {
        return new JAXBElement<String>(_ResponderExigenciaNuExigencia_QNAME, String.class, CumprirExigencia.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "id_cerp", scope = CumprirExigencia.class)
    public JAXBElement<String> createCumprirExigenciaIdCerp(String value) {
        return new JAXBElement<String>(_ResponderExigenciaIdCerp_QNAME, String.class, CumprirExigencia.class, value);
    }

}
