
package org.datacontract.schemas._2004._07.ecartorio_service;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Repasse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Repasse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cd_agencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cd_conta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cd_origem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_resumo_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_servico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_repasse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cerp" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/>
 *         &lt;element name="id_remessa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_repasse_servico" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_servico" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id_transacao" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vl_repasse" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Repasse", propOrder = {
    "cdAgencia",
    "cdConta",
    "cdOrigem",
    "dsItem",
    "dsResumoPedido",
    "dsServico",
    "dtRepasse",
    "idCerp",
    "idRemessa",
    "idRepasseServico",
    "idServico",
    "idTransacao",
    "vlRepasse"
})
public class Repasse {

    @XmlElementRef(name = "cd_agencia", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdAgencia;
    @XmlElementRef(name = "cd_conta", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdConta;
    @XmlElementRef(name = "cd_origem", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdOrigem;
    @XmlElementRef(name = "ds_item", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsItem;
    @XmlElementRef(name = "ds_resumo_pedido", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsResumoPedido;
    @XmlElementRef(name = "ds_servico", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsServico;
    @XmlElementRef(name = "dt_repasse", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtRepasse;
    @XmlElementRef(name = "id_cerp", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idCerp;
    @XmlElement(name = "id_remessa")
    protected Integer idRemessa;
    @XmlElement(name = "id_repasse_servico")
    protected Integer idRepasseServico;
    @XmlElement(name = "id_servico")
    protected Integer idServico;
    @XmlElement(name = "id_transacao")
    protected Integer idTransacao;
    @XmlElementRef(name = "vl_repasse", namespace = "http://schemas.datacontract.org/2004/07/eCartorio.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> vlRepasse;

    /**
     * Obtém o valor da propriedade cdAgencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdAgencia() {
        return cdAgencia;
    }

    /**
     * Define o valor da propriedade cdAgencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdAgencia(JAXBElement<String> value) {
        this.cdAgencia = value;
    }

    /**
     * Obtém o valor da propriedade cdConta.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdConta() {
        return cdConta;
    }

    /**
     * Define o valor da propriedade cdConta.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdConta(JAXBElement<String> value) {
        this.cdConta = value;
    }

    /**
     * Obtém o valor da propriedade cdOrigem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdOrigem() {
        return cdOrigem;
    }

    /**
     * Define o valor da propriedade cdOrigem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdOrigem(JAXBElement<String> value) {
        this.cdOrigem = value;
    }

    /**
     * Obtém o valor da propriedade dsItem.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsItem() {
        return dsItem;
    }

    /**
     * Define o valor da propriedade dsItem.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsItem(JAXBElement<String> value) {
        this.dsItem = value;
    }

    /**
     * Obtém o valor da propriedade dsResumoPedido.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsResumoPedido() {
        return dsResumoPedido;
    }

    /**
     * Define o valor da propriedade dsResumoPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsResumoPedido(JAXBElement<String> value) {
        this.dsResumoPedido = value;
    }

    /**
     * Obtém o valor da propriedade dsServico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsServico() {
        return dsServico;
    }

    /**
     * Define o valor da propriedade dsServico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsServico(JAXBElement<String> value) {
        this.dsServico = value;
    }

    /**
     * Obtém o valor da propriedade dtRepasse.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtRepasse() {
        return dtRepasse;
    }

    /**
     * Define o valor da propriedade dtRepasse.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtRepasse(JAXBElement<String> value) {
        this.dtRepasse = value;
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
     * Obtém o valor da propriedade idRemessa.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdRemessa() {
        return idRemessa;
    }

    /**
     * Define o valor da propriedade idRemessa.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdRemessa(Integer value) {
        this.idRemessa = value;
    }

    /**
     * Obtém o valor da propriedade idRepasseServico.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdRepasseServico() {
        return idRepasseServico;
    }

    /**
     * Define o valor da propriedade idRepasseServico.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdRepasseServico(Integer value) {
        this.idRepasseServico = value;
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
     * Obtém o valor da propriedade idTransacao.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdTransacao() {
        return idTransacao;
    }

    /**
     * Define o valor da propriedade idTransacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdTransacao(Integer value) {
        this.idTransacao = value;
    }

    /**
     * Obtém o valor da propriedade vlRepasse.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getVlRepasse() {
        return vlRepasse;
    }

    /**
     * Define o valor da propriedade vlRepasse.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setVlRepasse(JAXBElement<BigDecimal> value) {
        this.vlRepasse = value;
    }

}
