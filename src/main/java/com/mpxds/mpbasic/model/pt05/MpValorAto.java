package com.mpxds.mpbasic.model.pt05;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class MpValorAto implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private BigDecimal valorAtoEmolumento;

	private BigDecimal valorAtoVariavel;
	private BigDecimal valorAtoLei3217; // Substitui Lei.713 (Vide MpTitulo) ?
	private BigDecimal valorAtoLei4664;
	private BigDecimal valorAtoLei111;
	private BigDecimal valorAtoLei3761;
	
	/* TODO */
	
	private BigDecimal valorAtoLei590; // 590 x 489 Prisco.Verificar ! 
	private BigDecimal valorAtoLei6281;
	private BigDecimal valorAtoDistribuicao;
	private BigDecimal valorAtoLei713; // (Origem MpTitulo)
	private BigDecimal valorAtoLei489; // (Origem MpTitulo)
	
//	@Column(name = "valor_ato", nullable = false, precision = 12, scale = 2)
//	private BigDecimal valorAto; // (?? Vrf.Prisco ?? -> Campo Calculado !)
	
	// ---

	@Column(name = "valor_ato_emolumento", precision = 12, scale = 2, nullable = true) // nullable = false ???
	public BigDecimal getValorAtoEmolumento() { return valorAtoEmolumento; }
	public void setValorAtoEmolumento(BigDecimal valorAtoEmolumento) { 
																this.valorAtoEmolumento = valorAtoEmolumento; }

	@Column(name = "valor_ato_variavel", precision = 12, scale = 2, nullable = true) // nullable = false ???
	public BigDecimal getValorAtoVariavel() { return valorAtoVariavel; }
	public void setValorAtoVariavel(BigDecimal valorAtoVariavel) { this.valorAtoVariavel = valorAtoVariavel; }

	@Column(name = "valor_ato_lei3217", precision = 12, scale = 2, nullable = true) // nullable = false ???
	public BigDecimal getValorAtoLei3217() { return valorAtoLei3217; }
	public void setValorAtoLei3217(BigDecimal valorAtoLei3217) { this.valorAtoLei3217 = valorAtoLei3217; }

	@Column(name = "valor_ato_lei4664", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei4664() { return valorAtoLei4664; }
	public void setValorAtoLei4664(BigDecimal valorAtoLei4664) { this.valorAtoLei4664 = valorAtoLei4664; }

	@Column(name = "valor_ato_lei111", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei111() { return valorAtoLei111; }
	public void setValorAtoLei111(BigDecimal valorAtoLei111) { this.valorAtoLei111 = valorAtoLei111; }

	@Column(name = "valor_ato_lei3761", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei3761() { return valorAtoLei3761; }
	public void setValorAtoLei3761(BigDecimal valorAtoLei3761) { this.valorAtoLei3761 = valorAtoLei3761; }

	@Column(name = "valor_ato_lei590", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei590() { return valorAtoLei590; }
	public void setValorAtoLei590(BigDecimal valorAtoLei590) {  this.valorAtoLei590 = valorAtoLei590; }

	@Column(name = "valor_ato_lei6281", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei6281() { return valorAtoLei6281; }
	public void setValorAtoLei6281(BigDecimal valorAtoLei6281) { this.valorAtoLei6281 = valorAtoLei6281; }

	@Column(name = "valor_ato_distribuicao", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoDistribuicao() { return valorAtoDistribuicao; }	
	public void setValorAtoDistribuicao(BigDecimal valorAtoDistribuicao) { 
														this.valorAtoDistribuicao = valorAtoDistribuicao; }

	@Column(name = "valor_ato_lei713", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei713() { return valorAtoLei713; }
	public void setValorAtoLei713(BigDecimal valorAtoLei713) { this.valorAtoLei713 = valorAtoLei713; }

	@Column(name = "valor_ato_lei489", precision = 12, scale = 2, nullable = true) // nullable = false ???)
	public BigDecimal getValorAtoLei489() { return valorAtoLei489; }
	public void setValorAtoLei489(BigDecimal valorAtoLei489) { this.valorAtoLei489 = valorAtoLei489; }
	
	// ---

	@Transient
	public BigDecimal calcularValorTotal() {
		//
		BigDecimal valorTotal = BigDecimal.ZERO;
		//
		// (Origem MpTitulo) ??? Não entra no calculo ! 
		if (null == this.valorAtoLei713)
			this.valorAtoLei713 = BigDecimal.ZERO;
		if (null == this.valorAtoLei489)
			this.valorAtoLei489 = BigDecimal.ZERO;
		//
		if (null == this.valorAtoEmolumento)
			this.valorAtoEmolumento = BigDecimal.ZERO;
		if (null == this.valorAtoVariavel)
			this.valorAtoVariavel = BigDecimal.ZERO;
		if (null == this.valorAtoLei3217)
			this.valorAtoLei3217 = BigDecimal.ZERO;
		if (null == this.valorAtoLei4664)
			this.valorAtoLei4664 = BigDecimal.ZERO;
		if (null == this.valorAtoLei111)
			this.valorAtoLei111 = BigDecimal.ZERO;
		if (null == this.valorAtoLei3761)
			this.valorAtoLei3761 = BigDecimal.ZERO;
		if (null == this.valorAtoLei590)
			this.valorAtoLei590 = BigDecimal.ZERO;
		if (null == this.valorAtoLei6281)
			this.valorAtoLei6281 = BigDecimal.ZERO;
		if (null == this.valorAtoDistribuicao)
			this.valorAtoDistribuicao = BigDecimal.ZERO;
		//
		valorTotal = this.valorAtoEmolumento.
						add(this.valorAtoVariavel).
						add(this.valorAtoLei3217).
						add(this.valorAtoLei4664).
						add(this.valorAtoLei111).
						add(this.valorAtoLei3761).
						add(this.valorAtoLei590).
						add(this.valorAtoLei6281).
						add(this.valorAtoDistribuicao);
		//
	    return valorTotal;
	}
	
	@Transient
	public BigDecimal calcularValorCustas() {
		//
		BigDecimal valorTotalCustas = BigDecimal.ZERO;
		//
		if (null == this.valorAtoEmolumento)
			this.valorAtoEmolumento = BigDecimal.ZERO;
		if (null == this.valorAtoLei489)
			this.valorAtoLei489 = BigDecimal.ZERO;
		if (null == this.valorAtoLei3761)
			this.valorAtoLei3761 = BigDecimal.ZERO;
		if (null == this.valorAtoLei3217)
			this.valorAtoLei3217 = BigDecimal.ZERO;
		if (null == this.valorAtoLei4664)
			this.valorAtoLei4664 = BigDecimal.ZERO;
		if (null == this.valorAtoLei111)			
			this.valorAtoLei111 = BigDecimal.ZERO;
		if (null == this.valorAtoLei6281)
			this.valorAtoLei6281 = BigDecimal.ZERO;
		//
		valorTotalCustas = this.valorAtoEmolumento.
						add(this.valorAtoLei489).
						add(this.valorAtoLei3761).
						add(this.valorAtoLei3217).
						add(this.valorAtoLei4664).
						add(this.valorAtoLei111).
						add(this.valorAtoLei6281);
		//
		return valorTotalCustas;
	}

	@Transient
	public void zerarValorTotal() {
		//
		// (Origem MpTitulo) ??? 
		this.setValorAtoLei713(BigDecimal.ZERO);
		this.setValorAtoLei489(BigDecimal.ZERO);

		this.setValorAtoEmolumento(BigDecimal.ZERO);
		this.setValorAtoVariavel(BigDecimal.ZERO);
		this.setValorAtoLei3217(BigDecimal.ZERO);
		this.setValorAtoLei4664(BigDecimal.ZERO);
		this.setValorAtoLei111(BigDecimal.ZERO);
		this.setValorAtoLei3761(BigDecimal.ZERO);
		this.setValorAtoLei590(BigDecimal.ZERO);
		this.setValorAtoLei6281(BigDecimal.ZERO);
		this.setValorAtoDistribuicao(BigDecimal.ZERO);
	}
	
}