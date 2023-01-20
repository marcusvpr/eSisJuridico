
package org.datacontract.schemas._2004._07.ecartorio_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de PropriedadeDominio complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="PropriedadeDominio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cd_dominio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_dominio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vl_dominio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropriedadeDominio", propOrder = {
    "cdDominio",
    "idDominio",
    "vlDominio"
})
public class PropriedadeDominio {

    @XmlElementRef(name = "cd_dominio", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdDominio;
    @XmlElement(name = "id_dominio")
    protected Integer idDominio;
    @XmlElementRef(name = "vl_dominio", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vlDominio;

    /**
     * Obtém o valor da propriedade cdDominio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdDominio() {
        return cdDominio;
    }

    /**
     * Define o valor da propriedade cdDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdDominio(JAXBElement<String> value) {
        this.cdDominio = value;
    }

    /**
     * Obtém o valor da propriedade idDominio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdDominio() {
        return idDominio;
    }

    /**
     * Define o valor da propriedade idDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdDominio(Integer value) {
        this.idDominio = value;
    }

    /**
     * Obtém o valor da propriedade vlDominio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVlDominio() {
        return vlDominio;
    }

    /**
     * Define o valor da propriedade vlDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVlDominio(JAXBElement<String> value) {
        this.vlDominio = value;
    }

}
