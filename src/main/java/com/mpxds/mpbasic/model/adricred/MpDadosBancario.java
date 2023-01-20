package com.mpxds.mpbasic.model.adricred;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.adricred.MpContaTipo;
import com.mpxds.mpbasic.model.enums.adricred.MpTipoConta;

@Embeddable
public class MpDadosBancario implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private String agencia;
	private String conta;
	private MpContaTipo mpContaTipo;
	private MpTipoConta mpTipoConta;

	// ---

	@NotBlank @Size(max = 50)
	@Column(nullable = true, length = 50)
	public String getAgencia() { return agencia; }
	public void setAgencia(String agencia) { this.agencia = agencia; }

	@NotBlank @Size(max = 20)
	@Column(nullable = true, length = 20)
	public String getConta() { return conta; }
	public void setConta(String conta) { this.conta = conta; }
	
//	@NotNull(message = "Por favor, informe a CONTA TIPO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 15)
	public MpContaTipo getMpContaTipo() { return mpContaTipo; 	}
	public void setMpContaTipo(MpContaTipo mpContaTipo) { this.mpContaTipo = mpContaTipo; }
	
//	@NotNull(message = "Por favor, informe a TIPO CONTA")
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 15)
	public MpTipoConta getMpTipoConta() { return mpTipoConta; 	}
	public void setMpTipoConta(MpTipoConta mpTipoConta) { this.mpTipoConta = mpTipoConta; }

}
