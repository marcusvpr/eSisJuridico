
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPropriedadeDominio;


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
 *         &lt;element name="listarPropriedadeResult" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ArrayOfPropriedadeDominio" minOccurs="0"/>
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
    "listarPropriedadeResult"
})
@XmlRootElement(name = "listarPropriedadeResponse")
public class ListarPropriedadeResponse {

    @XmlElementRef(name = "listarPropriedadeResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfPropriedadeDominio> listarPropriedadeResult;

    /**
     * Obtém o valor da propriedade listarPropriedadeResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPropriedadeDominio }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPropriedadeDominio> getListarPropriedadeResult() {
        return listarPropriedadeResult;
    }

    /**
     * Define o valor da propriedade listarPropriedadeResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPropriedadeDominio }{@code >}
     *     
     */
    public void setListarPropriedadeResult(JAXBElement<ArrayOfPropriedadeDominio> value) {
        this.listarPropriedadeResult = value;
    }

}
