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
@AuditTable(value="mp_pt05_header_")
@Table(name="mp_pt05_header")
public class MpHeader extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpRemessa mpRemessa;
	
//  @Column(name = "identificacao_registro", nullable = false, length = 1)
//	@Getter @Setter
//	private String identificacaoRegistro; // ???? Já é definido na carga !

//  @Column(name = "codigo_portador", nullable = false, length = 3)
//	@Getter @Setter
//	private String codigoPortador; // ??? Passar Remessa 

//  @Column(name = "nome_portador", nullable = false, length = 45)
//	@Getter @Setter
//	private String nomePortador; // ??? Passar Remessa
	
//	@Column(name = "data_distribuicao", nullable = false, length = 8)
//	@Getter @Setter
//	private String dataDistribuicao;
	
	private String idenTransRemetente;	
	private String idenTransDestinatario;
	private String idenTransTipo;
	private String numeroSeqRemessa;
	private String qtdRegRemessa;
	private String qtdTitRemessa;
	private String qtdIndRemessa;
	private String qtdOrigRemessa;
	private String agenciaCentralizadora;
	private String versaoLayout;
	private String complementoRegistro;
	private String numeroSeqRegistro;	

	// ---
	
	public MpHeader() {
		super();
	}

	// ---
	
	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpRemessa_Id")
	public MpRemessa getMpRemessa() { return mpRemessa; }
	public void setMpRemessa(MpRemessa mpRemessa) { this.mpRemessa = mpRemessa; }

  	@Column(name = "iden_trans_remetente", nullable = false, length = 3)
	public String getIdenTransRemetente() { return idenTransRemetente; }
	public void setIdenTransRemetente(String idenTransRemetente) { 
														this.idenTransRemetente = idenTransRemetente; }

  	@Column(name = "iden_trans_destinatario", nullable = false, length = 3)
	public String getIdenTransDestinatario() { return idenTransDestinatario; }
	public void setIdenTransDestinatario(String idenTransDestinatario) { 
														this.idenTransDestinatario = idenTransDestinatario; }

  	@Column(name = "iden_trans_tipo", nullable = false, length = 3)
	public String getIdenTransTipo() { return idenTransTipo; }
	public void setIdenTransTipo(String idenTransTipo) { this.idenTransTipo = idenTransTipo; }

  	@Column(name = "numero_seq_remessa", nullable = false, length = 6)
	public String getNumeroSeqRemessa() { return numeroSeqRemessa; }
	public void setNumeroSeqRemessa(String numeroSeqRemessa) { this.numeroSeqRemessa = numeroSeqRemessa; }

  	@Column(name = "qtd_reg_remessa", nullable = false, length = 4)
	public String getQtdRegRemessa() { return qtdRegRemessa; }
	public void setQtdRegRemessa(String qtdRegRemessa) { this.qtdRegRemessa = qtdRegRemessa; }

  	@Column(name = "qtd_tit_remessa", nullable = false, length = 4)
	public String getQtdTitRemessa() { return qtdTitRemessa; }
	public void setQtdTitRemessa(String qtdTitRemessa) { this.qtdTitRemessa = qtdTitRemessa; }

  	@Column(name = "qtd_ind_remessa", nullable = false, length = 4)
	public String getQtdIndRemessa() { return qtdIndRemessa; }
	public void setQtdIndRemessa(String qtdIndRemessa) { this.qtdIndRemessa = qtdIndRemessa; }

  	@Column(name = "qtd_orig_remessa", nullable = false, length = 4)
	public String getQtdOrigRemessa() { return qtdOrigRemessa; }
	public void setQtdOrigRemessa(String qtdOrigRemessa) { this.qtdOrigRemessa = qtdOrigRemessa; }

  	@Column(name = "agencia_centralizadora", nullable = false, length = 6)
	public String getAgenciaCentralizadora() { return agenciaCentralizadora; }
	public void setAgenciaCentralizadora(String agenciaCentralizadora) { 
		                                                   this.agenciaCentralizadora = agenciaCentralizadora; }

  	@Column(name = "versao_layout", nullable = false, length = 3)
	public String getVersaoLayout() { return versaoLayout; }
	public void setVersaoLayout(String versaoLayout) { this.versaoLayout = versaoLayout; }

  	@Column(name = "complemento_registro", nullable = false, length = 1)
	public String getComplementoRegistro() { return complementoRegistro; }
	public void setComplementoRegistro(String complementoRegistro) { 
														this.complementoRegistro = complementoRegistro; }

  	@Column(name = "numero_seq_registro", nullable = false, length = 4)
	public String getNumeroSeqRegistro() { return numeroSeqRegistro; }
	public void setNumeroSeqRegistro(String numeroSeqRegistro) { this.numeroSeqRegistro = numeroSeqRegistro; }

}
