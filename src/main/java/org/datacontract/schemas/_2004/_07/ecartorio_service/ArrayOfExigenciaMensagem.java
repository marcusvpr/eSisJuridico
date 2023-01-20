
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfExigenciaMensagem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfExigenciaMensagem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExigenciaMensagem" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}ExigenciaMensagem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfExigenciaMensagem", propOrder = {
    "exigenciaMensagem"
})
public class ArrayOfExigenciaMensagem {

    @XmlElement(name = "ExigenciaMensagem", nillable = true)
    protected List<ExigenciaMensagem> exigenciaMensagem;

    /**
     * Gets the value of the exigenciaMensagem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exigenciaMensagem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExigenciaMensagem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExigenciaMensagem }
     * 
     * 
     */
    public List<ExigenciaMensagem> getExigenciaMensagem() {
        if (exigenciaMensagem == null) {
            exigenciaMensagem = new ArrayList<ExigenciaMensagem>();
        }
        return this.exigenciaMensagem;
    }

}
