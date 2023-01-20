
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfExigenciaMensagem;


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
 *         &lt;element name="listarExigenciaMensagemResult" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ArrayOfExigenciaMensagem" minOccurs="0"/>
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
    "listarExigenciaMensagemResult"
})
@XmlRootElement(name = "listarExigenciaMensagemResponse")
public class ListarExigenciaMensagemResponse {

    @XmlElementRef(name = "listarExigenciaMensagemResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfExigenciaMensagem> listarExigenciaMensagemResult;

    /**
     * Obtém o valor da propriedade listarExigenciaMensagemResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfExigenciaMensagem }{@code >}
     *     
     */
    public JAXBElement<ArrayOfExigenciaMensagem> getListarExigenciaMensagemResult() {
        return listarExigenciaMensagemResult;
    }

    /**
     * Define o valor da propriedade listarExigenciaMensagemResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfExigenciaMensagem }{@code >}
     *     
     */
    public void setListarExigenciaMensagemResult(JAXBElement<ArrayOfExigenciaMensagem> value) {
        this.listarExigenciaMensagemResult = value;
    }

}
