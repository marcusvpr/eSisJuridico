
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_servico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cerp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_status_pedido_ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_pedido_ini" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_pedido_fin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_cpf_cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_requisicao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_status_pedido_inicial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_status_pedido_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_pagamento_ini" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_pagamento_fin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idServico",
    "idCerp",
    "dsPdStatusPedidoExt",
    "dtPedidoIni",
    "dtPedidoFin",
    "nuCpfCnpj",
    "nuRequisicao",
    "tipoAto",
    "statusPedido",
    "dtStatusPedidoInicial",
    "dtStatusPedidoFinal",
    "dtPagamentoIni",
    "dtPagamentoFin"
})
@XmlRootElement(name = "listarPedidos")
public class ListarPedidos {

    @XmlElementRef(name = "id_servico", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idServico;
    @XmlElementRef(name = "id_cerp", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElementRef(name = "ds_pd_status_pedido_ext", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdStatusPedidoExt;
    @XmlElementRef(name = "dt_pedido_ini", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPedidoIni;
    @XmlElementRef(name = "dt_pedido_fin", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPedidoFin;
    @XmlElementRef(name = "nu_cpf_cnpj", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuCpfCnpj;
    @XmlElementRef(name = "nu_requisicao", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuRequisicao;
    @XmlElementRef(name = "tipo_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipoAto;
    @XmlElementRef(name = "status_pedido", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusPedido;
    @XmlElementRef(name = "dt_status_pedido_inicial", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtStatusPedidoInicial;
    @XmlElementRef(name = "dt_status_pedido_final", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtStatusPedidoFinal;
    @XmlElementRef(name = "dt_pagamento_ini", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPagamentoIni;
    @XmlElementRef(name = "dt_pagamento_fin", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPagamentoFin;

    /**
     * Obtém o valor da propriedade idServico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdServico() {
        return idServico;
    }

    /**
     * Define o valor da propriedade idServico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdServico(JAXBElement<String> value) {
        this.idServico = value;
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
     * Obtém o valor da propriedade dtPedidoIni.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPedidoIni() {
        return dtPedidoIni;
    }

    /**
     * Define o valor da propriedade dtPedidoIni.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPedidoIni(JAXBElement<String> value) {
        this.dtPedidoIni = value;
    }

    /**
     * Obtém o valor da propriedade dtPedidoFin.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPedidoFin() {
        return dtPedidoFin;
    }

    /**
     * Define o valor da propriedade dtPedidoFin.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPedidoFin(JAXBElement<String> value) {
        this.dtPedidoFin = value;
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
     * Obtém o valor da propriedade nuRequisicao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuRequisicao() {
        return nuRequisicao;
    }

    /**
     * Define o valor da propriedade nuRequisicao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuRequisicao(JAXBElement<String> value) {
        this.nuRequisicao = value;
    }

    /**
     * Obtém o valor da propriedade tipoAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoAto() {
        return tipoAto;
    }

    /**
     * Define o valor da propriedade tipoAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoAto(JAXBElement<String> value) {
        this.tipoAto = value;
    }

    /**
     * Obtém o valor da propriedade statusPedido.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusPedido() {
        return statusPedido;
    }

    /**
     * Define o valor da propriedade statusPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusPedido(JAXBElement<String> value) {
        this.statusPedido = value;
    }

    /**
     * Obtém o valor da propriedade dtStatusPedidoInicial.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtStatusPedidoInicial() {
        return dtStatusPedidoInicial;
    }

    /**
     * Define o valor da propriedade dtStatusPedidoInicial.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtStatusPedidoInicial(JAXBElement<String> value) {
        this.dtStatusPedidoInicial = value;
    }

    /**
     * Obtém o valor da propriedade dtStatusPedidoFinal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtStatusPedidoFinal() {
        return dtStatusPedidoFinal;
    }

    /**
     * Define o valor da propriedade dtStatusPedidoFinal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtStatusPedidoFinal(JAXBElement<String> value) {
        this.dtStatusPedidoFinal = value;
    }

    /**
     * Obtém o valor da propriedade dtPagamentoIni.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPagamentoIni() {
        return dtPagamentoIni;
    }

    /**
     * Define o valor da propriedade dtPagamentoIni.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPagamentoIni(JAXBElement<String> value) {
        this.dtPagamentoIni = value;
    }

    /**
     * Obtém o valor da propriedade dtPagamentoFin.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPagamentoFin() {
        return dtPagamentoFin;
    }

    /**
     * Define o valor da propriedade dtPagamentoFin.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPagamentoFin(JAXBElement<String> value) {
        this.dtPagamentoFin = value;
    }

}
