
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfRetorno;


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
 *         &lt;element name="responderExigenciaResult" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ArrayOfRetorno" minOccurs="0"/>
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
    "responderExigenciaResult"
})
@XmlRootElement(name = "responderExigenciaResponse")
public class ResponderExigenciaResponse {

    @XmlElementRef(name = "responderExigenciaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRetorno> responderExigenciaResult;

    /**
     * Obtém o valor da propriedade responderExigenciaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRetorno> getResponderExigenciaResult() {
        return responderExigenciaResult;
    }

    /**
     * Define o valor da propriedade responderExigenciaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRetorno }{@code >}
     *     
     */
    public void setResponderExigenciaResult(JAXBElement<ArrayOfRetorno> value) {
        this.responderExigenciaResult = value;
    }

}
