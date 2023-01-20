
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfRepasse;


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
 *         &lt;element name="listarRepassesResult" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ArrayOfRepasse" minOccurs="0"/>
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
    "listarRepassesResult"
})
@XmlRootElement(name = "listarRepassesResponse")
public class ListarRepassesResponse {

    @XmlElementRef(name = "listarRepassesResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRepasse> listarRepassesResult;

    /**
     * Obtém o valor da propriedade listarRepassesResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRepasse }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRepasse> getListarRepassesResult() {
        return listarRepassesResult;
    }

    /**
     * Define o valor da propriedade listarRepassesResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRepasse }{@code >}
     *     
     */
    public void setListarRepassesResult(JAXBElement<ArrayOfRepasse> value) {
        this.listarRepassesResult = value;
    }

}
