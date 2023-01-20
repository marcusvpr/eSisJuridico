
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de Pedido complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Pedido">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cd_origem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_bairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_complemento_a" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_complemento_b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_obs_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_ds_tipo_doc_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_ds_tipo_pedido_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_finalidade_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_status_pedido_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_tipo_ato_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_requerente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_ato" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_nascimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_pagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_pedido" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_repasse" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_sla" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dt_status" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id_cerp" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/>
 *         &lt;element name="id_cerp_original" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/>
 *         &lt;element name="id_pd_tipo_ato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_requisicao" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_servico" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nm_busca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_livro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_mae" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_natureza_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_pai" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_aleatorio_original" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_ato_origem" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nu_cpf_cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_cpf_cnpj_busca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_folha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_numero_a" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_numero_b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_pedido" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nu_requisicao" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nu_rgi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_selo_original" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_valor_total" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tp_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pedido", propOrder = {
    "cdOrigem",
    "dsBairro",
    "dsComplementoA",
    "dsComplementoB",
    "dsLogradouro",
    "dsObsAto",
    "dsPdDsTipoDocExt",
    "dsPdDsTipoPedidoExt",
    "dsPdFinalidadeExt",
    "dsPdStatusPedidoExt",
    "dsPdTipoAtoExt",
    "dsRequerente",
    "dtAto",
    "dtNascimento",
    "dtPagamento",
    "dtPedido",
    "dtRepasse",
    "dtSla",
    "dtStatus",
    "idCerp",
    "idCerpOriginal",
    "idPdTipoAto",
    "idRequisicao",
    "idServico",
    "nmBusca",
    "nmLivro",
    "nmMae",
    "nmNaturezaAto",
    "nmPai",
    "nuAleatorioOriginal",
    "nuAto",
    "nuAtoOrigem",
    "nuCpfCnpj",
    "nuCpfCnpjBusca",
    "nuFolha",
    "nuLogradouro",
    "nuNumeroA",
    "nuNumeroB",
    "nuPedido",
    "nuRequisicao",
    "nuRgi",
    "nuSeloOriginal",
    "nuValorTotal",
    "tpLogradouro"
})
public class Pedido {

    @XmlElementRef(name = "cd_origem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdOrigem;
    @XmlElementRef(name = "ds_bairro", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsBairro;
    @XmlElementRef(name = "ds_complemento_a", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsComplementoA;
    @XmlElementRef(name = "ds_complemento_b", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsComplementoB;
    @XmlElementRef(name = "ds_logradouro", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsLogradouro;
    @XmlElementRef(name = "ds_obs_ato", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsObsAto;
    @XmlElementRef(name = "ds_pd_ds_tipo_doc_ext", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdDsTipoDocExt;
    @XmlElementRef(name = "ds_pd_ds_tipo_pedido_ext", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdDsTipoPedidoExt;
    @XmlElementRef(name = "ds_pd_finalidade_ext", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdFinalidadeExt;
    @XmlElementRef(name = "ds_pd_status_pedido_ext", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdStatusPedidoExt;
    @XmlElementRef(name = "ds_pd_tipo_ato_ext", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdTipoAtoExt;
    @XmlElementRef(name = "ds_requerente", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsRequerente;
    @XmlElementRef(name = "dt_ato", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtAto;
    @XmlElementRef(name = "dt_nascimento", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtNascimento;
    @XmlElementRef(name = "dt_pagamento", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtPagamento;
    @XmlElementRef(name = "dt_pedido", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtPedido;
    @XmlElementRef(name = "dt_repasse", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtRepasse;
    @XmlElementRef(name = "dt_sla", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtSla;
    @XmlElementRef(name = "dt_status", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dtStatus;
    @XmlElementRef(name = "id_cerp", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElementRef(name = "id_cerp_original", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerpOriginal;
    @XmlElement(name = "id_pd_tipo_ato")
    protected Integer idPdTipoAto;
    @XmlElement(name = "id_requisicao")
    protected Integer idRequisicao;
    @XmlElement(name = "id_servico")
    protected Integer idServico;
    @XmlElementRef(name = "nm_busca", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmBusca;
    @XmlElementRef(name = "nm_livro", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmLivro;
    @XmlElementRef(name = "nm_mae", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmMae;
    @XmlElementRef(name = "nm_natureza_ato", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmNaturezaAto;
    @XmlElementRef(name = "nm_pai", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmPai;
    @XmlElementRef(name = "nu_aleatorio_original", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuAleatorioOriginal;
    @XmlElementRef(name = "nu_ato", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuAto;
    @XmlElementRef(name = "nu_ato_origem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> nuAtoOrigem;
    @XmlElementRef(name = "nu_cpf_cnpj", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuCpfCnpj;
    @XmlElementRef(name = "nu_cpf_cnpj_busca", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuCpfCnpjBusca;
    @XmlElementRef(name = "nu_folha", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuFolha;
    @XmlElementRef(name = "nu_logradouro", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuLogradouro;
    @XmlElementRef(name = "nu_numero_a", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuNumeroA;
    @XmlElementRef(name = "nu_numero_b", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuNumeroB;
    @XmlElement(name = "nu_pedido")
    protected Long nuPedido;
    @XmlElementRef(name = "nu_requisicao", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Long> nuRequisicao;
    @XmlElementRef(name = "nu_rgi", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuRgi;
    @XmlElementRef(name = "nu_selo_original", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuSeloOriginal;
    @XmlElementRef(name = "nu_valor_total", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> nuValorTotal;
    @XmlElementRef(name = "tp_logradouro", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tpLogradouro;

    /**
     * Obtém o valor da propriedade cdOrigem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdOrigem() {
        return cdOrigem;
    }

    /**
     * Define o valor da propriedade cdOrigem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdOrigem(JAXBElement<String> value) {
        this.cdOrigem = value;
    }

    /**
     * Obtém o valor da propriedade dsBairro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsBairro() {
        return dsBairro;
    }

    /**
     * Define o valor da propriedade dsBairro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsBairro(JAXBElement<String> value) {
        this.dsBairro = value;
    }

    /**
     * Obtém o valor da propriedade dsComplementoA.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsComplementoA() {
        return dsComplementoA;
    }

    /**
     * Define o valor da propriedade dsComplementoA.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsComplementoA(JAXBElement<String> value) {
        this.dsComplementoA = value;
    }

    /**
     * Obtém o valor da propriedade dsComplementoB.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsComplementoB() {
        return dsComplementoB;
    }

    /**
     * Define o valor da propriedade dsComplementoB.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsComplementoB(JAXBElement<String> value) {
        this.dsComplementoB = value;
    }

    /**
     * Obtém o valor da propriedade dsLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsLogradouro() {
        return dsLogradouro;
    }

    /**
     * Define o valor da propriedade dsLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsLogradouro(JAXBElement<String> value) {
        this.dsLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade dsObsAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsObsAto() {
        return dsObsAto;
    }

    /**
     * Define o valor da propriedade dsObsAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsObsAto(JAXBElement<String> value) {
        this.dsObsAto = value;
    }

    /**
     * Obtém o valor da propriedade dsPdDsTipoDocExt.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdDsTipoDocExt() {
        return dsPdDsTipoDocExt;
    }

    /**
     * Define o valor da propriedade dsPdDsTipoDocExt.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdDsTipoDocExt(JAXBElement<String> value) {
        this.dsPdDsTipoDocExt = value;
    }

    /**
     * Obtém o valor da propriedade dsPdDsTipoPedidoExt.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdDsTipoPedidoExt() {
        return dsPdDsTipoPedidoExt;
    }

    /**
     * Define o valor da propriedade dsPdDsTipoPedidoExt.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdDsTipoPedidoExt(JAXBElement<String> value) {
        this.dsPdDsTipoPedidoExt = value;
    }

    /**
     * Obtém o valor da propriedade dsPdFinalidadeExt.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdFinalidadeExt() {
        return dsPdFinalidadeExt;
    }

    /**
     * Define o valor da propriedade dsPdFinalidadeExt.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdFinalidadeExt(JAXBElement<String> value) {
        this.dsPdFinalidadeExt = value;
    }

    /**
     * Obtém o valor da propriedade dsPdStatusPedidoExt.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdStatusPedidoExt() {
        return dsPdStatusPedidoExt;
    }

    /**
     * Define o valor da propriedade dsPdStatusPedidoExt.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdStatusPedidoExt(JAXBElement<String> value) {
        this.dsPdStatusPedidoExt = value;
    }

    /**
     * Obtém o valor da propriedade dsPdTipoAtoExt.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdTipoAtoExt() {
        return dsPdTipoAtoExt;
    }

    /**
     * Define o valor da propriedade dsPdTipoAtoExt.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdTipoAtoExt(JAXBElement<String> value) {
        this.dsPdTipoAtoExt = value;
    }

    /**
     * Obtém o valor da propriedade dsRequerente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsRequerente() {
        return dsRequerente;
    }

    /**
     * Define o valor da propriedade dsRequerente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsRequerente(JAXBElement<String> value) {
        this.dsRequerente = value;
    }

    /**
     * Obtém o valor da propriedade dtAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtAto() {
        return dtAto;
    }

    /**
     * Define o valor da propriedade dtAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtAto(JAXBElement<XMLGregorianCalendar> value) {
        this.dtAto = value;
    }

    /**
     * Obtém o valor da propriedade dtNascimento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtNascimento() {
        return dtNascimento;
    }

    /**
     * Define o valor da propriedade dtNascimento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtNascimento(JAXBElement<XMLGregorianCalendar> value) {
        this.dtNascimento = value;
    }

    /**
     * Obtém o valor da propriedade dtPagamento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtPagamento() {
        return dtPagamento;
    }

    /**
     * Define o valor da propriedade dtPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtPagamento(JAXBElement<XMLGregorianCalendar> value) {
        this.dtPagamento = value;
    }

    /**
     * Obtém o valor da propriedade dtPedido.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtPedido() {
        return dtPedido;
    }

    /**
     * Define o valor da propriedade dtPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtPedido(JAXBElement<XMLGregorianCalendar> value) {
        this.dtPedido = value;
    }

    /**
     * Obtém o valor da propriedade dtRepasse.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtRepasse() {
        return dtRepasse;
    }

    /**
     * Define o valor da propriedade dtRepasse.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtRepasse(JAXBElement<XMLGregorianCalendar> value) {
        this.dtRepasse = value;
    }

    /**
     * Obtém o valor da propriedade dtSla.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtSla() {
        return dtSla;
    }

    /**
     * Define o valor da propriedade dtSla.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtSla(JAXBElement<XMLGregorianCalendar> value) {
        this.dtSla = value;
    }

    /**
     * Obtém o valor da propriedade dtStatus.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDtStatus() {
        return dtStatus;
    }

    /**
     * Define o valor da propriedade dtStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDtStatus(JAXBElement<XMLGregorianCalendar> value) {
        this.dtStatus = value;
    }

    /**
     * Obtém o valor da propriedade idCerp.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdCerp() {
        return idCerp;
    }

    /**
     * Define o valor da propriedade idCerp.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdCerp(JAXBElement<String> value) {
        this.idCerp = value;
    }

    /**
     * Obtém o valor da propriedade idCerpOriginal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdCerpOriginal() {
        return idCerpOriginal;
    }

    /**
     * Define o valor da propriedade idCerpOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdCerpOriginal(JAXBElement<String> value) {
        this.idCerpOriginal = value;
    }

    /**
     * Obtém o valor da propriedade idPdTipoAto.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPdTipoAto() {
        return idPdTipoAto;
    }

    /**
     * Define o valor da propriedade idPdTipoAto.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPdTipoAto(Integer value) {
        this.idPdTipoAto = value;
    }

    /**
     * Obtém o valor da propriedade idRequisicao.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdRequisicao() {
        return idRequisicao;
    }

    /**
     * Define o valor da propriedade idRequisicao.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdRequisicao(Integer value) {
        this.idRequisicao = value;
    }

    /**
     * Obtém o valor da propriedade idServico.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdServico() {
        return idServico;
    }

    /**
     * Define o valor da propriedade idServico.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdServico(Integer value) {
        this.idServico = value;
    }

    /**
     * Obtém o valor da propriedade nmBusca.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmBusca() {
        return nmBusca;
    }

    /**
     * Define o valor da propriedade nmBusca.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmBusca(JAXBElement<String> value) {
        this.nmBusca = value;
    }

    /**
     * Obtém o valor da propriedade nmLivro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmLivro() {
        return nmLivro;
    }

    /**
     * Define o valor da propriedade nmLivro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmLivro(JAXBElement<String> value) {
        this.nmLivro = value;
    }

    /**
     * Obtém o valor da propriedade nmMae.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmMae() {
        return nmMae;
    }

    /**
     * Define o valor da propriedade nmMae.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmMae(JAXBElement<String> value) {
        this.nmMae = value;
    }

    /**
     * Obtém o valor da propriedade nmNaturezaAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmNaturezaAto() {
        return nmNaturezaAto;
    }

    /**
     * Define o valor da propriedade nmNaturezaAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmNaturezaAto(JAXBElement<String> value) {
        this.nmNaturezaAto = value;
    }

    /**
     * Obtém o valor da propriedade nmPai.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmPai() {
        return nmPai;
    }

    /**
     * Define o valor da propriedade nmPai.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmPai(JAXBElement<String> value) {
        this.nmPai = value;
    }

    /**
     * Obtém o valor da propriedade nuAleatorioOriginal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuAleatorioOriginal() {
        return nuAleatorioOriginal;
    }

    /**
     * Define o valor da propriedade nuAleatorioOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuAleatorioOriginal(JAXBElement<String> value) {
        this.nuAleatorioOriginal = value;
    }

    /**
     * Obtém o valor da propriedade nuAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuAto() {
        return nuAto;
    }

    /**
     * Define o valor da propriedade nuAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuAto(JAXBElement<String> value) {
        this.nuAto = value;
    }

    /**
     * Obtém o valor da propriedade nuAtoOrigem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getNuAtoOrigem() {
        return nuAtoOrigem;
    }

    /**
     * Define o valor da propriedade nuAtoOrigem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setNuAtoOrigem(JAXBElement<Integer> value) {
        this.nuAtoOrigem = value;
    }

    /**
     * Obtém o valor da propriedade nuCpfCnpj.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuCpfCnpj() {
        return nuCpfCnpj;
    }

    /**
     * Define o valor da propriedade nuCpfCnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuCpfCnpj(JAXBElement<String> value) {
        this.nuCpfCnpj = value;
    }

    /**
     * Obtém o valor da propriedade nuCpfCnpjBusca.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuCpfCnpjBusca() {
        return nuCpfCnpjBusca;
    }

    /**
     * Define o valor da propriedade nuCpfCnpjBusca.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuCpfCnpjBusca(JAXBElement<String> value) {
        this.nuCpfCnpjBusca = value;
    }

    /**
     * Obtém o valor da propriedade nuFolha.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuFolha() {
        return nuFolha;
    }

    /**
     * Define o valor da propriedade nuFolha.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuFolha(JAXBElement<String> value) {
        this.nuFolha = value;
    }

    /**
     * Obtém o valor da propriedade nuLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuLogradouro() {
        return nuLogradouro;
    }

    /**
     * Define o valor da propriedade nuLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuLogradouro(JAXBElement<String> value) {
        this.nuLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade nuNumeroA.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuNumeroA() {
        return nuNumeroA;
    }

    /**
     * Define o valor da propriedade nuNumeroA.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuNumeroA(JAXBElement<String> value) {
        this.nuNumeroA = value;
    }

    /**
     * Obtém o valor da propriedade nuNumeroB.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuNumeroB() {
        return nuNumeroB;
    }

    /**
     * Define o valor da propriedade nuNumeroB.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuNumeroB(JAXBElement<String> value) {
        this.nuNumeroB = value;
    }

    /**
     * Obtém o valor da propriedade nuPedido.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNuPedido() {
        return nuPedido;
    }

    /**
     * Define o valor da propriedade nuPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNuPedido(Long value) {
        this.nuPedido = value;
    }

    /**
     * Obtém o valor da propriedade nuRequisicao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getNuRequisicao() {
        return nuRequisicao;
    }

    /**
     * Define o valor da propriedade nuRequisicao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setNuRequisicao(JAXBElement<Long> value) {
        this.nuRequisicao = value;
    }

    /**
     * Obtém o valor da propriedade nuRgi.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuRgi() {
        return nuRgi;
    }

    /**
     * Define o valor da propriedade nuRgi.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuRgi(JAXBElement<String> value) {
        this.nuRgi = value;
    }

    /**
     * Obtém o valor da propriedade nuSeloOriginal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuSeloOriginal() {
        return nuSeloOriginal;
    }

    /**
     * Define o valor da propriedade nuSeloOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuSeloOriginal(JAXBElement<String> value) {
        this.nuSeloOriginal = value;
    }

    /**
     * Obtém o valor da propriedade nuValorTotal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getNuValorTotal() {
        return nuValorTotal;
    }

    /**
     * Define o valor da propriedade nuValorTotal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setNuValorTotal(JAXBElement<BigDecimal> value) {
        this.nuValorTotal = value;
    }

    /**
     * Obtém o valor da propriedade tpLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTpLogradouro() {
        return tpLogradouro;
    }

    /**
     * Define o valor da propriedade tpLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTpLogradouro(JAXBElement<String> value) {
        this.tpLogradouro = value;
    }

}
