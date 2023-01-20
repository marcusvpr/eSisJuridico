
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
 *         &lt;element name="nu_cpf_cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_servico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_tipo_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_tipo_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_tipo_doc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_pd_finalidade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_requisicao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_sla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cd_origem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_rgi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tp_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_numero_a" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_complemento_a" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_numero_b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_complemento_b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_bairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_busca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_cpf_cnpj_busca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_pai" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_mae" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_livro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_folha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_natureza_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dt_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ds_obs_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_ato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nu_valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "nuCpfCnpj",
    "idServico",
    "dsPdTipoPedido",
    "dsPdTipoAto",
    "dsPdTipoDoc",
    "dsPdFinalidade",
    "idRequisicao",
    "dtSla",
    "cdOrigem",
    "nuRgi",
    "tpLogradouro",
    "dsLogradouro",
    "nuLogradouro",
    "nuNumeroA",
    "dsComplementoA",
    "nuNumeroB",
    "dsComplementoB",
    "dsBairro",
    "nmBusca",
    "nuCpfCnpjBusca",
    "nmPai",
    "nmMae",
    "nmLivro",
    "nuFolha",
    "nmNaturezaAto",
    "dtAto",
    "dsObsAto",
    "nuAto",
    "nuValor"
})
@XmlRootElement(name = "gerarPedido")
public class GerarPedido {

    @XmlElementRef(name = "nu_cpf_cnpj", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuCpfCnpj;
    @XmlElementRef(name = "id_servico", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idServico;
    @XmlElementRef(name = "ds_pd_tipo_pedido", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdTipoPedido;
    @XmlElementRef(name = "ds_pd_tipo_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdTipoAto;
    @XmlElementRef(name = "ds_pd_tipo_doc", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdTipoDoc;
    @XmlElementRef(name = "ds_pd_finalidade", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsPdFinalidade;
    @XmlElementRef(name = "id_requisicao", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idRequisicao;
    @XmlElementRef(name = "dt_sla", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtSla;
    @XmlElementRef(name = "cd_origem", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cdOrigem;
    @XmlElementRef(name = "nu_rgi", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuRgi;
    @XmlElementRef(name = "tp_logradouro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tpLogradouro;
    @XmlElementRef(name = "ds_logradouro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsLogradouro;
    @XmlElementRef(name = "nu_logradouro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuLogradouro;
    @XmlElementRef(name = "nu_numero_a", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuNumeroA;
    @XmlElementRef(name = "ds_complemento_a", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsComplementoA;
    @XmlElementRef(name = "nu_numero_b", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuNumeroB;
    @XmlElementRef(name = "ds_complemento_b", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsComplementoB;
    @XmlElementRef(name = "ds_bairro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsBairro;
    @XmlElementRef(name = "nm_busca", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmBusca;
    @XmlElementRef(name = "nu_cpf_cnpj_busca", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuCpfCnpjBusca;
    @XmlElementRef(name = "nm_pai", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmPai;
    @XmlElementRef(name = "nm_mae", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmMae;
    @XmlElementRef(name = "nm_livro", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmLivro;
    @XmlElementRef(name = "nu_folha", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuFolha;
    @XmlElementRef(name = "nm_natureza_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nmNaturezaAto;
    @XmlElementRef(name = "dt_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dtAto;
    @XmlElementRef(name = "ds_obs_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dsObsAto;
    @XmlElementRef(name = "nu_ato", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuAto;
    @XmlElementRef(name = "nu_valor", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nuValor;

    /**
     * Obtém o valor da propriedade nuCpfCnpj.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuCpfCnpj() {
        return nuCpfCnpj;
    }

    /**
     * Define o valor da propriedade nuCpfCnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuCpfCnpj(JAXBElement<String> value) {
        this.nuCpfCnpj = value;
    }

    /**
     * Obtém o valor da propriedade idServico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdServico() {
        return idServico;
    }

    /**
     * Define o valor da propriedade idServico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdServico(JAXBElement<String> value) {
        this.idServico = value;
    }

    /**
     * Obtém o valor da propriedade dsPdTipoPedido.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdTipoPedido() {
        return dsPdTipoPedido;
    }

    /**
     * Define o valor da propriedade dsPdTipoPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdTipoPedido(JAXBElement<String> value) {
        this.dsPdTipoPedido = value;
    }

    /**
     * Obtém o valor da propriedade dsPdTipoAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdTipoAto() {
        return dsPdTipoAto;
    }

    /**
     * Define o valor da propriedade dsPdTipoAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdTipoAto(JAXBElement<String> value) {
        this.dsPdTipoAto = value;
    }

    /**
     * Obtém o valor da propriedade dsPdTipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdTipoDoc() {
        return dsPdTipoDoc;
    }

    /**
     * Define o valor da propriedade dsPdTipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdTipoDoc(JAXBElement<String> value) {
        this.dsPdTipoDoc = value;
    }

    /**
     * Obtém o valor da propriedade dsPdFinalidade.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsPdFinalidade() {
        return dsPdFinalidade;
    }

    /**
     * Define o valor da propriedade dsPdFinalidade.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsPdFinalidade(JAXBElement<String> value) {
        this.dsPdFinalidade = value;
    }

    /**
     * Obtém o valor da propriedade idRequisicao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdRequisicao() {
        return idRequisicao;
    }

    /**
     * Define o valor da propriedade idRequisicao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdRequisicao(JAXBElement<String> value) {
        this.idRequisicao = value;
    }

    /**
     * Obtém o valor da propriedade dtSla.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtSla() {
        return dtSla;
    }

    /**
     * Define o valor da propriedade dtSla.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtSla(JAXBElement<String> value) {
        this.dtSla = value;
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
     * Obtém o valor da propriedade nuRgi.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuRgi() {
        return nuRgi;
    }

    /**
     * Define o valor da propriedade nuRgi.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuRgi(JAXBElement<String> value) {
        this.nuRgi = value;
    }

    /**
     * Obtém o valor da propriedade tpLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTpLogradouro() {
        return tpLogradouro;
    }

    /**
     * Define o valor da propriedade tpLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTpLogradouro(JAXBElement<String> value) {
        this.tpLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade dsLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsLogradouro() {
        return dsLogradouro;
    }

    /**
     * Define o valor da propriedade dsLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsLogradouro(JAXBElement<String> value) {
        this.dsLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade nuLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuLogradouro() {
        return nuLogradouro;
    }

    /**
     * Define o valor da propriedade nuLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuLogradouro(JAXBElement<String> value) {
        this.nuLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade nuNumeroA.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuNumeroA() {
        return nuNumeroA;
    }

    /**
     * Define o valor da propriedade nuNumeroA.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuNumeroA(JAXBElement<String> value) {
        this.nuNumeroA = value;
    }

    /**
     * Obtém o valor da propriedade dsComplementoA.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsComplementoA() {
        return dsComplementoA;
    }

    /**
     * Define o valor da propriedade dsComplementoA.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsComplementoA(JAXBElement<String> value) {
        this.dsComplementoA = value;
    }

    /**
     * Obtém o valor da propriedade nuNumeroB.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuNumeroB() {
        return nuNumeroB;
    }

    /**
     * Define o valor da propriedade nuNumeroB.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuNumeroB(JAXBElement<String> value) {
        this.nuNumeroB = value;
    }

    /**
     * Obtém o valor da propriedade dsComplementoB.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsComplementoB() {
        return dsComplementoB;
    }

    /**
     * Define o valor da propriedade dsComplementoB.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsComplementoB(JAXBElement<String> value) {
        this.dsComplementoB = value;
    }

    /**
     * Obtém o valor da propriedade dsBairro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsBairro() {
        return dsBairro;
    }

    /**
     * Define o valor da propriedade dsBairro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsBairro(JAXBElement<String> value) {
        this.dsBairro = value;
    }

    /**
     * Obtém o valor da propriedade nmBusca.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmBusca() {
        return nmBusca;
    }

    /**
     * Define o valor da propriedade nmBusca.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmBusca(JAXBElement<String> value) {
        this.nmBusca = value;
    }

    /**
     * Obtém o valor da propriedade nuCpfCnpjBusca.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuCpfCnpjBusca() {
        return nuCpfCnpjBusca;
    }

    /**
     * Define o valor da propriedade nuCpfCnpjBusca.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuCpfCnpjBusca(JAXBElement<String> value) {
        this.nuCpfCnpjBusca = value;
    }

    /**
     * Obtém o valor da propriedade nmPai.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmPai() {
        return nmPai;
    }

    /**
     * Define o valor da propriedade nmPai.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmPai(JAXBElement<String> value) {
        this.nmPai = value;
    }

    /**
     * Obtém o valor da propriedade nmMae.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmMae() {
        return nmMae;
    }

    /**
     * Define o valor da propriedade nmMae.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmMae(JAXBElement<String> value) {
        this.nmMae = value;
    }

    /**
     * Obtém o valor da propriedade nmLivro.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmLivro() {
        return nmLivro;
    }

    /**
     * Define o valor da propriedade nmLivro.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmLivro(JAXBElement<String> value) {
        this.nmLivro = value;
    }

    /**
     * Obtém o valor da propriedade nuFolha.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuFolha() {
        return nuFolha;
    }

    /**
     * Define o valor da propriedade nuFolha.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuFolha(JAXBElement<String> value) {
        this.nuFolha = value;
    }

    /**
     * Obtém o valor da propriedade nmNaturezaAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNmNaturezaAto() {
        return nmNaturezaAto;
    }

    /**
     * Define o valor da propriedade nmNaturezaAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNmNaturezaAto(JAXBElement<String> value) {
        this.nmNaturezaAto = value;
    }

    /**
     * Obtém o valor da propriedade dtAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDtAto() {
        return dtAto;
    }

    /**
     * Define o valor da propriedade dtAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDtAto(JAXBElement<String> value) {
        this.dtAto = value;
    }

    /**
     * Obtém o valor da propriedade dsObsAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDsObsAto() {
        return dsObsAto;
    }

    /**
     * Define o valor da propriedade dsObsAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDsObsAto(JAXBElement<String> value) {
        this.dsObsAto = value;
    }

    /**
     * Obtém o valor da propriedade nuAto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuAto() {
        return nuAto;
    }

    /**
     * Define o valor da propriedade nuAto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuAto(JAXBElement<String> value) {
        this.nuAto = value;
    }

    /**
     * Obtém o valor da propriedade nuValor.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNuValor() {
        return nuValor;
    }

    /**
     * Define o valor da propriedade nuValor.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNuValor(JAXBElement<String> value) {
        this.nuValor = value;
    }

}
