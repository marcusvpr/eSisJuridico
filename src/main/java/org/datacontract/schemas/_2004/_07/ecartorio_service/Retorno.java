
package org.datacontract.schemas._2004._07.ecartorio_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Retorno complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Retorno">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cd_ret" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ds_ret" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cerp" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/>
 *         &lt;element name="id_exigencia" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nu_exigencia" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Retorno", propOrder = {
    "cdRet",
    "dsRet",
    "idCerp",
    "idExigencia",
    "nuExigencia"
})
public class Retorno {

    @XmlElementRef(name = "cd_ret", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> cdRet;
    @XmlElementRef(name = "ds_ret", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsRet;
    @XmlElementRef(name = "id_cerp", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElementRef(name = "id_exigencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idExigencia;
    @XmlElementRef(name = "nu_exigencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> nuExigencia;

    /**
     * Obtém o valor da propriedade cdRet.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCdRet() {
        return cdRet;
    }

    /**
     * Define o valor da propriedade cdRet.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCdRet(JAXBElement<Integer> value) {
        this.cdRet = value;
    }

    /**
     * Obtém o valor da propriedade dsRet.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsRet() {
        return dsRet;
    }

    /**
     * Define o valor da propriedade dsRet.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsRet(JAXBElement<String> value) {
        this.dsRet = value;
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
     * Obtém o valor da propriedade idExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIdExigencia() {
        return idExigencia;
    }

    /**
     * Define o valor da propriedade idExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIdExigencia(JAXBElement<Integer> value) {
        this.idExigencia = value;
    }

    /**
     * Obtém o valor da propriedade nuExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getNuExigencia() {
        return nuExigencia;
    }

    /**
     * Define o valor da propriedade nuExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setNuExigencia(JAXBElement<Integer> value) {
        this.nuExigencia = value;
    }

}
