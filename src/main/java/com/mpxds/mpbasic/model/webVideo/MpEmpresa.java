package com.mpxds.mpbasic.model.webVideo;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpTabelaInterna;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.webVideo.MpEnderecoComercial;

@Entity
@Table(name = "mp_wv_empresa", uniqueConstraints = @UniqueConstraint(columnNames = "NOME"))
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.INTEGER)
public class MpEmpresa extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	//
	private String nome;
	private String email;
	private Boolean indFabricante;
	private Boolean indFornecedor;
	private Boolean indDistribuidor;
	private Boolean indConvenio;
	private String contato;
	private String telTrabalho;
	private String telCelular;
	private String telFax;
	private String cnpj;
	private String inscMunicipal;
	private String inscEstadual;
	private String cae;
	private String webPage;
	private String obs = "";
	private String pathLogo;

	private MpEnderecoComercial mpEnderecoComercial; 

	private MpStatus mpStatus = MpStatus.ATIVO;
	private MpTabelaInterna mpFormaTributaria; // TAB.0030
	private MpTabelaInterna mpRamoAtividade; // TAB.0045

	private byte[] fotoBD;

	// ---

	public MpEmpresa() {
		super();
	}

	public MpEmpresa(String nome,
					MpTabelaInterna mpFormaTributaria,
					MpTabelaInterna mpRamoAtividade,
					MpStatus mpStatus) {
		super();
		this.nome = nome;
		this.mpFormaTributaria = mpFormaTributaria;
		this.mpRamoAtividade = mpRamoAtividade;
		this.mpStatus = mpStatus;
	}

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(min = 5, max = 100, message = "Nome tam.inválido(<5 ou >100)")
	@Column(nullable = false, length = 100, unique = true)
	public String getNome() { return this.nome; }
	public void setNome(String newNome) { this.nome = newNome; }

	@Embedded
	public MpEnderecoComercial getMpEnderecoComercial() { return this.mpEnderecoComercial; }
	public void setMpEnderecoComercial(MpEnderecoComercial newMpEnderecoComercial) {
											this.mpEnderecoComercial = newMpEnderecoComercial; }

	public String getEmail() { return this.email; }
	public void setEmail(String newEmail) { this.email = newEmail; }

	@Column(name = "ind_fabricante", nullable = true)
	public Boolean getIndFabricante() {	return this.indFabricante; }
	public void setIndFabricante(Boolean newIndFabricante) { this.indFabricante = newIndFabricante; }

	@Column(name = "ind_fornecedor", nullable = true)
	public Boolean getIndFornecedor() { return this.indFornecedor; }
	public void setIndFornecedor(Boolean newIndFornecedor) {
															this.indFornecedor = newIndFornecedor; 	}

	@Column(name = "ind_distribuidor", nullable = true)
	public Boolean getIndDistribuidor() { return this.indDistribuidor; }
	public void setIndDistribuidor(Boolean newIndDistribuidor) { 
														this.indDistribuidor = newIndDistribuidor; }

	@Column(name = "ind_convenio", nullable = true)
	public Boolean getIndConvenio() { return this.indConvenio; }
	public void setIndConvenio(Boolean newIndConvenio) { this.indConvenio = newIndConvenio; }

	public String getContato() { return this.contato; }
	public void setContato(String newContato) { this.contato = newContato; }

	@Column(name = "tel_trabalho", nullable = true)
	public String getTelTrabalho() { return this.telTrabalho; }
	public void setTelTrabalho(String newTelTrabalho) { this.telTrabalho = newTelTrabalho; }

	@Column(name = "tel_celular", nullable = true)
	public String getTelCelular() {	return this.telCelular; }
	public void setTelCelular(String newTelCelular) { this.telCelular = newTelCelular; }

	@Column(name = "tel_fax", nullable = true)
	public String getTelFax() {	return this.telFax; }
	public void setTelFax(String newTelFax) { this.telFax = newTelFax; }

	public String getCnpj() { return this.cnpj; }
	public void setCnpj(String newCnpj) { this.cnpj = newCnpj; }

	@Column(name = "insc_municipal", nullable = true)
	public String getInscMunicipal() { return this.inscMunicipal; }
	public void setInscMunicipal(String newInscMunicipal) { this.inscMunicipal = newInscMunicipal; }

	@Column(name = "insc_estadual", nullable = true)
	public String getInscEstadual() { return this.inscEstadual; }
	public void setInscEstadual(String newInscEstadual) { this.inscEstadual = newInscEstadual; }

	public String getCae() { return this.cae;  }
	public void setCae(String newCae) { this.cae = newCae; }

	public String getWebPage() { return this.webPage; }
	public void setWebPage(String newWebPage) { this.webPage = newWebPage; }

	public String getObs() { return this.obs; }
	public void setObs(String newObs) { this.obs = newObs; }

	public MpStatus getMpStatus() { return this.mpStatus; }
	public void setMpStatus(MpStatus newMpStatus) { this.mpStatus = newMpStatus; }

	@Column(name = "mpForma_tributaria", nullable = true)
	public MpTabelaInterna getMpFormaTributaria() { return this.mpFormaTributaria; }
	public void setMpFormaTributaria(MpTabelaInterna newMpFormaTributaria) {
												this.mpFormaTributaria = newMpFormaTributaria; }

	@Column(name = "mpRamo_atividade", nullable = true)
	public MpTabelaInterna getMpRamoAtividade() { return this.mpRamoAtividade; }
	public void setMpRamoAtividade(MpTabelaInterna newMpRamoAtividade) { 
														this.mpRamoAtividade = newMpRamoAtividade; }

	@Column(name = "path_logo", nullable = true)
	public String getPathLogo() { return this.pathLogo; }
	public void setPathLogo(String newPathLogo) { this.pathLogo = newPathLogo; }

	@Lob
	public byte[] getFotoBD() {	return fotoBD; }
	public void setFotoBD(byte[] fotoBD) { this.fotoBD = fotoBD; }

}
