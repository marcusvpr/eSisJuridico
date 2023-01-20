
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfPropriedadeDominio complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPropriedadeDominio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropriedadeDominio" type="{http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts}PropriedadeDominio" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPropriedadeDominio", propOrder = {
    "propriedadeDominio"
})
public class ArrayOfPropriedadeDominio {

    @XmlElement(name = "PropriedadeDominio", nillable = true)
    protected List<PropriedadeDominio> propriedadeDominio;

    /**
     * Gets the value of the propriedadeDominio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propriedadeDominio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropriedadeDominio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropriedadeDominio }
     * 
     * 
     */
    public List<PropriedadeDominio> getPropriedadeDominio() {
        if (propriedadeDominio == null) {
            propriedadeDominio = new ArrayList<PropriedadeDominio>();
        }
        return this.propriedadeDominio;
    }

}
