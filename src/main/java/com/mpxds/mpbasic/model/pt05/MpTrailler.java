package com.mpxds.mpbasic.model.pt05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt05_trailler_")
@Table(name="mp_pt05_trailler")
public class MpTrailler extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpRemessa mpRemessa;
	
	private String dataDistribuicao;
	private String somaSegQtdRemessa;
	private String somaSegValRemessa;
	private String complementoRegistro;
	private String numeroSeqRegistro;

	// ----------
	
	public MpTrailler() {
		super();
	}

  	public MpTrailler(MpRemessa mpRemessa
             , String dataDistribuicao
             , String somaSegQtdRemessa
             , String somaSegValRemessa
             , String complementoRegistro
             , String numeroSeqRegistro
             ) {
  		this.mpRemessa = mpRemessa;
  		this.dataDistribuicao = dataDistribuicao;
  		this.somaSegQtdRemessa = somaSegQtdRemessa;
  		this.somaSegValRemessa = somaSegValRemessa;
  		this.complementoRegistro = complementoRegistro;
  		this.numeroSeqRegistro = numeroSeqRegistro;
  	}
  	
  	// ---

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpRemessa_Id")
	public MpRemessa getMpRemessa() { return mpRemessa; }
	public void setMpRemessa(MpRemessa mpRemessa) { this.mpRemessa = mpRemessa; }

  	@Column(name = "data_distribuicao", nullable = false, length = 8)
	public String getDataDistribuicao() { return dataDistribuicao; }
	public void setDataDistribuicao(String dataDistribuicao) { this.dataDistribuicao = dataDistribuicao; }

  	@Column(name = "soma_seg_qtd_remessa", nullable = false, length = 15)
	public String getSomaSegQtdRemessa() { return somaSegQtdRemessa; }
	public void setSomaSegQtdRemessa(String somaSegQtdRemessa) { this.somaSegQtdRemessa = somaSegQtdRemessa; }

  	@Column(name = "soma_seg_val_remessa", nullable = false, length = 18)
	public String getSomaSegValRemessa() { return somaSegValRemessa; }
	public void setSomaSegValRemessa(String somaSegValRemessa) { this.somaSegValRemessa = somaSegValRemessa; }

  	@Column(name = "complemento_registro", nullable = false, length = 1)
	public String getComplementoRegistro() { return complementoRegistro; }
	public void setComplementoRegistro(String complementoRegistro) { 
															this.complementoRegistro = complementoRegistro; }

  	@Column(name = "numero_seq_registro", nullable = false, length = 4)
	public String getNumeroSeqRegistro() { return numeroSeqRegistro; } 
	public void setNumeroSeqRegistro(String numeroSeqRegistro) { this.numeroSeqRegistro = numeroSeqRegistro; }
   	  	
}
