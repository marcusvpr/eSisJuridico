
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
 *         &lt;element name="id_cerp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_exigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_prazo_conclusao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fl_tp_financeiro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "idCerp",
    "dsExigencia",
    "dtPrazoConclusao",
    "flTpFinanceiro",
    "nuValor"
})
@XmlRootElement(name = "marcarExigencia")
public class MarcarExigencia {

    @XmlElementRef(name = "id_cerp", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElementRef(name = "ds_exigencia", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsExigencia;
    @XmlElementRef(name = "dt_prazo_conclusao", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPrazoConclusao;
    @XmlElementRef(name = "fl_tp_financeiro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> flTpFinanceiro;
    @XmlElementRef(name = "nu_valor", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuValor;

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
     * Obtém o valor da propriedade dsExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsExigencia() {
        return dsExigencia;
    }

    /**
     * Define o valor da propriedade dsExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsExigencia(JAXBElement<String> value) {
        this.dsExigencia = value;
    }

    /**
     * Obtém o valor da propriedade dtPrazoConclusao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPrazoConclusao() {
        return dtPrazoConclusao;
    }

    /**
     * Define o valor da propriedade dtPrazoConclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPrazoConclusao(JAXBElement<String> value) {
        this.dtPrazoConclusao = value;
    }

    /**
     * Obtém o valor da propriedade flTpFinanceiro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFlTpFinanceiro() {
        return flTpFinanceiro;
    }

    /**
     * Define o valor da propriedade flTpFinanceiro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFlTpFinanceiro(JAXBElement<String> value) {
        this.flTpFinanceiro = value;
    }

    /**
     * Obtém o valor da propriedade nuValor.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuValor() {
        return nuValor;
    }

    /**
     * Define o valor da propriedade nuValor.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuValor(JAXBElement<String> value) {
        this.nuValor = value;
    }

}
