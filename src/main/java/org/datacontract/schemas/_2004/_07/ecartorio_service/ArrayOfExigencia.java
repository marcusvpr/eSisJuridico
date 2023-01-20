
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfExigencia complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfExigencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Exigencia" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}Exigencia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfExigencia", propOrder = {
    "exigencia"
})
public class ArrayOfExigencia {

    @XmlElement(name = "Exigencia", nillable = true)
    protected List<Exigencia> exigencia;

    /**
     * Gets the value of the exigencia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exigencia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExigencia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Exigencia }
     * 
     * 
     */
    public List<Exigencia> getExigencia() {
        if (exigencia == null) {
            exigencia = new ArrayList<Exigencia>();
        }
        return this.exigencia;
    }

}
