package com.mpxds.mpbasic.model.webVideo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpPessoa;

@Entity
@Table(name="mp_wv_cliente") // (Base.MFCL001.DBF)
public class MpClienteWV extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

    private String codigo; //
	private String senha; //

	private MpPessoa mpPessoa; //
   	
    private String indicador; // Tab.0165 - TipoCliente(E=Especial/N=Normal...)
    private BigDecimal saldoCcCr = BigDecimal.ZERO; //
	private BigDecimal saldoCc = BigDecimal.ZERO; //
    private BigDecimal saldoCr = BigDecimal.ZERO; //
	private Integer percentDesc = 0; //
    private String limite; // Tab.0020 - Limites ! //
	private Date dtHrLogin; // -
	private String obs; // *
	private Boolean indBloqueado = false; 
	private Boolean indConvenio = false; 
	private Date dtHrLock; // - 
    private Date dtHrLoc;  //
	private Integer diaPag; //
	private Boolean indTaxa = false; //
	private String talao; //
	private String tipoCcCr; // Tab.0160 (CR=Credito...)
	private Boolean indCart = false; //
	private BigDecimal saldoBonus = BigDecimal.ZERO; //
	private Integer saldoTalao =  0; //

	private Set<MpDependenteWV> mpDependenteWVs = new HashSet<MpDependenteWV>(0);

	private String loja; // SistemaConfig = 00-Matriz/01-EmpresaFilial01 ...

	// ---
	
	public MpClienteWV() {
		super();
	}
 
	@Column(nullable = false, length = 15)
	public String getCodigo() { return this.codigo; }
	public void setCodigo(String newCodigo) { this.codigo = newCodigo; }
	 
	@Column(nullable = false, length = 15)
	public String getSenha() { return this.senha; }
	public void setSenha(String newSenha) { this.senha = newSenha; }

	@Column(nullable = false, length = 15)
	public MpPessoa getMpPessoa() { return this.mpPessoa; }
	public void setMpPessoa(MpPessoa newMpPessoa) { this.mpPessoa = newMpPessoa; }

	@Column(nullable = true, length = 15)
	public String getIndicador() { return this.indicador; }
	public void setIndicador(String newIndicador) { this.indicador = newIndicador; }
 
	@Column(name = "Saldo_Cc_Cr", nullable = true, precision = 10, scale = 2)
	public BigDecimal getSaldoCcCr() { return this.saldoCcCr; }
	public void setSaldoCcCr(BigDecimal newSaldoCcCr) { this.saldoCcCr = newSaldoCcCr; }
	 
	@Column(name = "Saldo_Cc", nullable = true, precision = 10, scale = 2)
	public BigDecimal getSaldoCc() { return this.saldoCc; }
	public void setSaldoCc(BigDecimal newSaldoCc) { this.saldoCc = newSaldoCc; }
 
	@Column(name = "Saldo_Cr", nullable = true, precision = 10, scale = 2)
	public BigDecimal getSaldoCr() { return this.saldoCr; }
	public void setSaldoCr(BigDecimal newSaldoCr) { this.saldoCr = newSaldoCr; }
 	 
	@Column(name = "Percent_Desc", nullable = true, precision = 10, scale = 2)
	public Integer getPercentDesc() { return this.percentDesc; }
	public void setPercentDesc(Integer newPercentDesc) { this.percentDesc = newPercentDesc; }
	
	public String getLimite() { return this.limite; }
	public void setLimite(String newLimite) { this.limite = newLimite; }
  
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Dt_Hr_Login", nullable = true)
	public Date getDtHrLogin() { return this.dtHrLogin; }
	public void setDtHrLogin(Date newDtHrLogin) { this.dtHrLogin = newDtHrLogin; }

	public String getObs() { return this.obs; }
	public void setObs(String newObs) { this.obs = newObs; }

    @Column(name = "Ind_Bloqueado", nullable = true)
	public Boolean getIndBloqueado() { return this.indBloqueado; }
	public void setIndBloqueado(Boolean newIndBloqueado) { this.indBloqueado = newIndBloqueado; }

    @Column(name = "Ind_Convenio", nullable = true)
  	public Boolean getIndConvenio() { return this.indConvenio; }
  	public void setIndConvenio(Boolean newIndConvenio) { this.indConvenio = newIndConvenio; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Dt_Hr_Lock", nullable = true)
	public Date getDtHrLock() { return this.dtHrLock; }
	public void setDtHrLock(Date newDtHrLock) { this.dtHrLock = newDtHrLock; }
 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Dt_Hr_Loc", nullable = true)
	public Date getDtHrLoc() { return this.dtHrLoc; }
	public void setDtHrLoc(Date newDtHrLoc) { this.dtHrLoc = newDtHrLoc; }
 
    @Column(name = "Dia_Pag", nullable = true, length = 2)
	public Integer getDiaPag() { return this.diaPag; }
	public void setDiaPag(Integer newDiaPag) { this.diaPag = newDiaPag; }
 
    @Column(name = "ind_Taxa", nullable = true)
	public Boolean getIndTaxa() { return this.indTaxa; }
	public void setIndTaxa(Boolean newIndTaxa) { this.indTaxa = newIndTaxa; }
  
    @Column(nullable = true, length = 30)
	public String getTalao() { return this.talao; }
	public void setTalao(String newTalao) { this.talao = newTalao; }
	  
    @Column(name = "Tipo_Cc_Cr", nullable = true, length = 30)
	public String getTipoCcCr() { return this.tipoCcCr; }
	public void setTipoCcCr(String newTipoCcCr) { this.tipoCcCr = newTipoCcCr; }
 
    @Column(name = "ind_Cart", nullable = true)
	public Boolean getIndCart() { return this.indCart; }
	public void setIndCart(Boolean newIndCart) { this.indCart = newIndCart; }
  
	@Column(name = "Saldo_Bonus", nullable = true, precision = 10, scale = 2)
	public BigDecimal getSaldoBonus() {	return this.saldoBonus; }
	public void setSaldoBonus(BigDecimal newSaldoBonus) { this.saldoBonus = newSaldoBonus; }
	  
    @Column(name = "SaldoTalao", nullable = true, length = 5)
	public Integer getSaldoTalao() { return this.saldoTalao; }
	public void setSaldoTalao(Integer newSaldoTalao) { this.saldoTalao = newSaldoTalao; }
	  
    @OneToMany(mappedBy="mpClienteWV", fetch=FetchType.EAGER)
    //    					cascade=CascadeType.ALL, orphanRemoval=true)
    @OrderBy("codigo")
	public Set<MpDependenteWV> getMpDependenteWVs() { return this.mpDependenteWVs; }
    public void setMpDependenteWVs(Set<MpDependenteWV> mpDependenteWVs) { 
    														this.mpDependenteWVs = mpDependenteWVs; }
	  
	@Column(nullable = false, length = 2)
	public String getLoja() { return this.loja; }
	public void setLoja(String newLoja) { this.loja = newLoja; }
	  
    // -----------------------------
    
	@Transient
	public int sizeDependentes() { return mpDependenteWVs.size(); }
	public void addMpDependenteWV(MpDependenteWV mpDependenteWV) { mpDependenteWVs.add(mpDependenteWV); }

}