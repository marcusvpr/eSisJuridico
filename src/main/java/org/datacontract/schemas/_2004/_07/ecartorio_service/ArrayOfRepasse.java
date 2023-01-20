
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfRepasse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRepasse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Repasse" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}Repasse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRepasse", propOrder = {
    "repasse"
})
public class ArrayOfRepasse {

    @XmlElement(name = "Repasse", nillable = true)
    protected List<Repasse> repasse;

    /**
     * Gets the value of the repasse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the repasse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepasse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Repasse }
     * 
     * 
     */
    public List<Repasse> getRepasse() {
        if (repasse == null) {
            repasse = new ArrayList<Repasse>();
        }
        return this.repasse;
    }

}
