package com.mpxds.mpbasic.model.engreq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.MpSexo;

@Entity
@Audited
@AuditTable(value="mp_er_pessoa_")
@Table(name = "mp_er_pessoa")
public class MpPessoaER extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String cargo;
	private String email;
	private String celular;
	private String telefone;
	private Date dataNascimento;
	private String observacao;
		
	private MpSexo mpSexo;
	
	// ----------
		
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	
	@NotBlank(message = "Por favor, informe o CARGO")
	@Column(nullable = false, length = 100)
	public String getCargo() { return cargo; }
	public void setCargo(String cargo) { this.cargo = cargo; }
	
	@NotBlank(message = "Por favor, informe o E-MAIL")
	@Column(nullable = false, unique = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@Column(nullable = true, length = 100)
	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }
	
	@Column(nullable = true, length = 100) 
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	public Date getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
		
	@Column(nullable = true, length = 255)
	public String getObservacao() {	return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10)
	public MpSexo getMpSexo() {	return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	
}
