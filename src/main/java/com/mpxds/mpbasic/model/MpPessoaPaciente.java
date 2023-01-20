package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_pessoa_paciente")
public class MpPessoaPaciente extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpPessoa mpPessoa;
	private MpStatus mpStatus;

	private MpPaciente mpPaciente;

	// ---
		
	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }

	@NotNull(message = "Por favor, informe a PESSOA")
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpPessoa_Id")
	public MpPessoa getMpPessoa() { return mpPessoa; }
	public void setMpPessoa(MpPessoa mpPessoa) { this.mpPessoa = mpPessoa; }
		
	@ManyToOne
	@JoinColumn(name = "mpPaciente_id", nullable = false)
	public MpPaciente getMpPaciente() { return mpPaciente; }
	public void setMpPaciente(MpPaciente mpPaciente) { this.mpPaciente = mpPaciente; }

}