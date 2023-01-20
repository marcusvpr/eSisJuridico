package com.mpxds.mpbasic.model.pt05;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="MP_PT05_ATO_COMPOSICAO_")
@Table(name = "MP_PT05_ATO_COMPOSICAO")
public class MpAtoComposicao extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpCustasComposicao mpCustasComposicao;

	private String complemento;
	private String excecao;
	private BigDecimal valorCusta = BigDecimal.ZERO; 
	
	private MpAto mpAto;

	// ---
	
	public MpAtoComposicao(){
		super();
	}
	
	// ---
	
	@ManyToOne
	@JoinColumn(name = "MP_CUSTAS_COMPOSICAO_ID", nullable = true)
	public MpCustasComposicao getMpCustasComposicao() { return mpCustasComposicao; 	}
	public void setMpCustasComposicao(MpCustasComposicao mpCustasComposicao) {
															this.mpCustasComposicao = mpCustasComposicao; }

	@Column(name = "COMPLEMENTO", nullable = true, length = 20)
	public String getComplemento() { return complemento; }
	public void setComplemento(String complemento) { this.complemento = complemento; }
	
	@Column(name = "EXCECAO", nullable = true, length = 50)
	public String getExcecao() { return excecao; }
	public void setExcecao(String excecao) { this.excecao = excecao; }

	@Column(name = "VALOR_CUSTA", nullable = true, precision = 10, scale = 2)
	public BigDecimal getValorCusta() { return valorCusta; }
	public void setValorCusta(BigDecimal valorCusta) { this.valorCusta = valorCusta; }

	@ManyToOne
	@JoinColumn(name = "MP_ATO_ID", nullable = false)
	public MpAto getMpAto() { return mpAto; }
	public void setMpAto(MpAto mpAto) { this.mpAto = mpAto; }
		
}