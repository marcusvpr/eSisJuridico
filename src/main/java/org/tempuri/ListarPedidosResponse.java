
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;


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
 *         &lt;element name="listarPedidosResult" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ArrayOfPedido" minOccurs="0"/>
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
    "listarPedidosResult"
})
@XmlRootElement(name = "listarPedidosResponse")
public class ListarPedidosResponse {

    @XmlElementRef(name = "listarPedidosResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfPedido> listarPedidosResult;

    /**
     * Obtém o valor da propriedade listarPedidosResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPedido }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPedido> getListarPedidosResult() {
        return listarPedidosResult;
    }

    /**
     * Define o valor da propriedade listarPedidosResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPedido }{@code >}
     *     
     */
    public void setListarPedidosResult(JAXBElement<ArrayOfPedido> value) {
        this.listarPedidosResult = value;
    }

}
