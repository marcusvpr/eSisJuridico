
package org.datacontract.schemas._2004._07.ecartorio_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ExigenciaMensagem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ExigenciaMensagem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ds_mensagem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_nome_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_nome_origem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_mensagem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fl_mensagem_lida" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="id_exigencia" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExigenciaMensagem", propOrder = {
    "dsMensagem",
    "dsNomeDestino",
    "dsNomeOrigem",
    "dtMensagem",
    "flMensagemLida",
    "idExigencia"
})
public class ExigenciaMensagem {

    @XmlElementRef(name = "ds_mensagem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsMensagem;
    @XmlElementRef(name = "ds_nome_destino", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsNomeDestino;
    @XmlElementRef(name = "ds_nome_origem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsNomeOrigem;
    @XmlElementRef(name = "dt_mensagem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtMensagem;
    @XmlElement(name = "fl_mensagem_lida")
    protected Boolean flMensagemLida;
    @XmlElementRef(name = "id_exigencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idExigencia;

    /**
     * Obtém o valor da propriedade dsMensagem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsMensagem() {
        return dsMensagem;
    }

    /**
     * Define o valor da propriedade dsMensagem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsMensagem(JAXBElement<String> value) {
        this.dsMensagem = value;
    }

    /**
     * Obtém o valor da propriedade dsNomeDestino.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsNomeDestino() {
        return dsNomeDestino;
    }

    /**
     * Define o valor da propriedade dsNomeDestino.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsNomeDestino(JAXBElement<String> value) {
        this.dsNomeDestino = value;
    }

    /**
     * Obtém o valor da propriedade dsNomeOrigem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsNomeOrigem() {
        return dsNomeOrigem;
    }

    /**
     * Define o valor da propriedade dsNomeOrigem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsNomeOrigem(JAXBElement<String> value) {
        this.dsNomeOrigem = value;
    }

    /**
     * Obtém o valor da propriedade dtMensagem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtMensagem() {
        return dtMensagem;
    }

    /**
     * Define o valor da propriedade dtMensagem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtMensagem(JAXBElement<String> value) {
        this.dtMensagem = value;
    }

    /**
     * Obtém o valor da propriedade flMensagemLida.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlMensagemLida() {
        return flMensagemLida;
    }

    /**
     * Define o valor da propriedade flMensagemLida.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlMensagemLida(Boolean value) {
        this.flMensagemLida = value;
    }

    /**
     * Obtém o valor da propriedade idExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIdExigencia() {
        return idExigencia;
    }

    /**
     * Define o valor da propriedade idExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIdExigencia(JAXBElement<Integer> value) {
        this.idExigencia = value;
    }

}
