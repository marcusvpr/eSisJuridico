
package org.datacontract.schemas._2004._07.ecartorio_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Exigencia complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Exigencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ds_exigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_conclusao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_exigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_prazo_conclusao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cerp" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/>
 *         &lt;element name="id_exigencia" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_pedido" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_servico" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_usuario_origem" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nu_exigencia" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vl_transacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exigencia", propOrder = {
    "dsExigencia",
    "dtConclusao",
    "dtExigencia",
    "dtPrazoConclusao",
    "idCerp",
    "idExigencia",
    "idPedido",
    "idServico",
    "idUsuarioOrigem",
    "nuExigencia",
    "vlTransacao"
})
public class Exigencia {

    @XmlElementRef(name = "ds_exigencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsExigencia;
    @XmlElementRef(name = "dt_conclusao", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtConclusao;
    @XmlElementRef(name = "dt_exigencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtExigencia;
    @XmlElementRef(name = "dt_prazo_conclusao", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtPrazoConclusao;
    @XmlElementRef(name = "id_cerp", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElement(name = "id_exigencia")
    protected Integer idExigencia;
    @XmlElement(name = "id_pedido")
    protected Integer idPedido;
    @XmlElement(name = "id_servico")
    protected Integer idServico;
    @XmlElementRef(name = "id_usuario_origem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idUsuarioOrigem;
    @XmlElement(name = "nu_exigencia")
    protected Integer nuExigencia;
    @XmlElementRef(name = "vl_transacao", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vlTransacao;

    /**
     * Obtém o valor da propriedade dsExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsExigencia() {
        return dsExigencia;
    }

    /**
     * Define o valor da propriedade dsExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsExigencia(JAXBElement<String> value) {
        this.dsExigencia = value;
    }

    /**
     * Obtém o valor da propriedade dtConclusao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtConclusao() {
        return dtConclusao;
    }

    /**
     * Define o valor da propriedade dtConclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtConclusao(JAXBElement<String> value) {
        this.dtConclusao = value;
    }

    /**
     * Obtém o valor da propriedade dtExigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtExigencia() {
        return dtExigencia;
    }

    /**
     * Define o valor da propriedade dtExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtExigencia(JAXBElement<String> value) {
        this.dtExigencia = value;
    }

    /**
     * Obtém o valor da propriedade dtPrazoConclusao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtPrazoConclusao() {
        return dtPrazoConclusao;
    }

    /**
     * Define o valor da propriedade dtPrazoConclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtPrazoConclusao(JAXBElement<String> value) {
        this.dtPrazoConclusao = value;
    }

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
     * Obtém o valor da propriedade idExigencia.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdExigencia() {
        return idExigencia;
    }

    /**
     * Define o valor da propriedade idExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdExigencia(Integer value) {
        this.idExigencia = value;
    }

    /**
     * Obtém o valor da propriedade idPedido.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPedido() {
        return idPedido;
    }

    /**
     * Define o valor da propriedade idPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPedido(Integer value) {
        this.idPedido = value;
    }

    /**
     * Obtém o valor da propriedade idServico.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdServico() {
        return idServico;
    }

    /**
     * Define o valor da propriedade idServico.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdServico(Integer value) {
        this.idServico = value;
    }

    /**
     * Obtém o valor da propriedade idUsuarioOrigem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIdUsuarioOrigem() {
        return idUsuarioOrigem;
    }

    /**
     * Define o valor da propriedade idUsuarioOrigem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIdUsuarioOrigem(JAXBElement<Integer> value) {
        this.idUsuarioOrigem = value;
    }

    /**
     * Obtém o valor da propriedade nuExigencia.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNuExigencia() {
        return nuExigencia;
    }

    /**
     * Define o valor da propriedade nuExigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNuExigencia(Integer value) {
        this.nuExigencia = value;
    }

    /**
     * Obtém o valor da propriedade vlTransacao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVlTransacao() {
        return vlTransacao;
    }

    /**
     * Define o valor da propriedade vlTransacao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVlTransacao(JAXBElement<String> value) {
        this.vlTransacao = value;
    }

}
