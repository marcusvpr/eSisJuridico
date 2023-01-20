package com.mpxds.mpbasic.model.dto.engreq;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mpxds.mpbasic.model.enums.engreq.MpProjetoFaseEtapa;

public class MpProjetoFaseEtapaDTO implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	private MpProjetoFaseEtapa mpProjetoFaseEtapa;

	private Integer esforco;
	private Integer duracao;
	private BigDecimal custo;
				
	// ---

	public MpProjetoFaseEtapa getMpProjetoFaseEtapa() { return mpProjetoFaseEtapa; }
	public void setMpProjetoFaseEtapa(MpProjetoFaseEtapa mpProjetoFaseEtapa) { 
													this.mpProjetoFaseEtapa = mpProjetoFaseEtapa; }
	
	public Integer getEsforco() { return esforco; }
	public void setEsforco(Integer esforco) { this.esforco = esforco; }
	
	public Integer getDuracao() { return duracao; }
	public void setDuracao(Integer duracao) { this.duracao = duracao; }
	
	public BigDecimal getCusto() { return custo; }
	public void setCusto(BigDecimal custo) { this.custo = custo; }
	
}