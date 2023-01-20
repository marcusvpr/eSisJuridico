
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
 *         &lt;element name="id_cerp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_exigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_exigencia_inicial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_exigencia_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fl_mensagem_lida" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "idCerp",
    "nuExigencia",
    "dtExigenciaInicial",
    "dtExigenciaFinal",
    "flMensagemLida"
})
@XmlRootElement(name = "listarExigenciaMensagem")
public class ListarExigenciaMensagem {

    @XmlElementRef(name = "id_cerp", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElementRef(name = "nu_exigencia", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuExigencia;
    @XmlElementRef(name = "dt_exigencia_inicial", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtExigenciaInicial;
    @XmlElementRef(name = "dt_exigencia_final", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtExigenciaFinal;
    @XmlElementRef(name = "fl_mensagem_lida", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> flMensagemLida;

    /**
     * Obtém o valor da propriedade idCerp.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdCerp() {
        return idCerp;
    }

    /**
     * Define o valor da propriedade idCerp.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdCerp(JAXBElement<String> value) {
        this.idCerp = value;
    }

    /**
     * Obtém o valor da propriedade nuExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuExigencia() {
        return nuExigencia;
    }

    /**
     * Define o valor da propriedade nuExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuExigencia(JAXBElement<String> value) {
        this.nuExigencia = value;
    }

    /**
     * Obtém o valor da propriedade dtExigenciaInicial.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtExigenciaInicial() {
        return dtExigenciaInicial;
    }

    /**
     * Define o valor da propriedade dtExigenciaInicial.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtExigenciaInicial(JAXBElement<String> value) {
        this.dtExigenciaInicial = value;
    }

    /**
     * Obtém o valor da propriedade dtExigenciaFinal.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtExigenciaFinal() {
        return dtExigenciaFinal;
    }

    /**
     * Define o valor da propriedade dtExigenciaFinal.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtExigenciaFinal(JAXBElement<String> value) {
        this.dtExigenciaFinal = value;
    }

    /**
     * Obtém o valor da propriedade flMensagemLida.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFlMensagemLida() {
        return flMensagemLida;
    }

    /**
     * Define o valor da propriedade flMensagemLida.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFlMensagemLida(JAXBElement<String> value) {
        this.flMensagemLida = value;
    }

}
