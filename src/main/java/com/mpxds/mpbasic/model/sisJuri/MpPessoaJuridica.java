package com.mpxds.mpbasic.model.sisJuri;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MP_SJ_PESSOA_JURIDICA")
@PrimaryKeyJoinColumn(name="id")
public class MpPessoaJuridica extends MpPessoaSJ {
	//
	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	private String razaoSocial;
	private String responsavel;
	private String ramoAtividade;

	// ---
	
	public MpPessoaJuridica() {
		super();
	}

	// ---
	
	@Column(nullable = false, length = 20)
	public String getCnpj() { return cnpj; }
	public void setCnpj(String cnpj) { this.cnpj = cnpj; }

	@Column(name = "razao_social", nullable = true, length = 150)
	public String getRazaoSocial() { return razaoSocial; }
	public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

	@Column(nullable = true, length = 150)
	public String getResponsavel() { return responsavel; }
	public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

	@Column(name = "ramo_atividade", nullable = true, length = 150)
	public String getRamoAtividade() { return ramoAtividade; }
	public void setRamoAtividade(String ramoAtividade) { this.ramoAtividade = ramoAtividade; }

}
