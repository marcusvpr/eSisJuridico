package com.mpxds.mpbasic.model.livroDiario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mpxds.mpbasic.model.MpBaseEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "mp_ld_livro_diario")
public class MpLivroDiario extends MpBaseEntity {
	private static final long serialVersionUID = 1L;

	private Date dataEntrada;
	private String creditoDebito;
	private Double valorAto;
	private String observacao;
	private Integer numFolha;
	private Date dataProtocolo;
	private String numProtocolo;
	private String statusEntrada;

	private MpTipoLancamento mpTipoLancamento;
    private MpLivroDiario mpPai;
    
    private List<MpLivroDiario> mpFilhoList = new ArrayList<MpLivroDiario>();
	
	// --------------------------------------
	public MpLivroDiario() {
		super();
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, length = 10)
	public Date getDataEntrada() { return this.dataEntrada; }
	public void setDataEntrada(Date dataEntrada) { this.dataEntrada = dataEntrada; }

	@Column(nullable = false, length=1)
	public String getCreditoDebito() { return this.creditoDebito; }
	public void setCreditoDebito(String creditoDebito) { this.creditoDebito = creditoDebito; }

	@Column(nullable = false)
	public Double getValorAto() { return this.valorAto; }
	public void setValorAto(Double valorAto) { this.valorAto = valorAto; }

	@Column(nullable = false, length=100)
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Column(nullable = false, length=10)
	public Integer getNumFolha() { return this.numFolha; }
	public void setNumFolha(Integer numFolha) { this.numFolha = numFolha; }

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataProtocolo() { return this.dataProtocolo; }
	public void setDataProtocolo(Date dataProtocolo) { this.dataProtocolo = dataProtocolo; }

	@Column(nullable = false, length=30)
	public String getNumProtocolo() { return this.numProtocolo; }
	public void setNumProtocolo(String numProtocolo) { this.numProtocolo = numProtocolo; }

	@Column(nullable = false, length=10)
	public String getStatusEntrada() { return this.statusEntrada; }
	public void setStatusEntrada(String statusEntrada) { this.statusEntrada = statusEntrada; }

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "mpTipoLancamentoId")
	public MpTipoLancamento getMpTipoLancamento() { return this.mpTipoLancamento; }
	public void setMpTipoLancamento(MpTipoLancamento mpTipoLancamento) {
													this.mpTipoLancamento = mpTipoLancamento; }

    @ManyToOne(fetch=FetchType.EAGER)
	public MpLivroDiario getMpPai() { return mpPai; }
	public void setMpPai(MpLivroDiario mpPai) { this.mpPai = mpPai; }
	
    @OneToMany(mappedBy="mpPai", fetch=FetchType.EAGER)
	public List<MpLivroDiario> getMpFilhoList() { return mpFilhoList; }
	public void setMpFilhoList(List<MpLivroDiario> mpFilhoList) { this.mpFilhoList = mpFilhoList; }	

	// -------------------------------------------
	
	public int sizeMpFilhoList() { return mpFilhoList.size(); }

	public void addMpFilhoList(MpLivroDiario mpLivroDiario) { mpFilhoList.add(mpLivroDiario); }
	
}