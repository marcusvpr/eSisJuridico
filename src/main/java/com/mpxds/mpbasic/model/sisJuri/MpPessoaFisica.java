package com.mpxds.mpbasic.model.sisJuri;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MP_SJ_PESSOA_FISICA")
@PrimaryKeyJoinColumn(name="id")
public class MpPessoaFisica extends MpPessoaSJ {
	//
	private static final long serialVersionUID = 1L;

	private String cpf;

	// ---
	
	public MpPessoaFisica() {
		super();
	}
	
	// ---

	@Column(nullable = false, length = 20)
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	
}
