
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
 *         &lt;element name="propriedade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cd_dominio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_dominio_pai" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "propriedade",
    "cdDominio",
    "idDominioPai"
})
@XmlRootElement(name = "listarPropriedade")
public class ListarPropriedade {

    @XmlElementRef(name = "propriedade", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> propriedade;
    @XmlElementRef(name = "cd_dominio", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdDominio;
    @XmlElementRef(name = "id_dominio_pai", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idDominioPai;

    /**
     * Obtém o valor da propriedade propriedade.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPropriedade() {
        return propriedade;
    }

    /**
     * Define o valor da propriedade propriedade.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPropriedade(JAXBElement<String> value) {
        this.propriedade = value;
    }

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
     * Obtém o valor da propriedade idDominioPai.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdDominioPai() {
        return idDominioPai;
    }

    /**
     * Define o valor da propriedade idDominioPai.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdDominioPai(JAXBElement<String> value) {
        this.idDominioPai = value;
    }

}
