package com.mpxds.mpbasic.model.webVideo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpPessoa;

@Entity
@Table(name="Mp_wv_Dependente") // (Base.MFCL050.DBF)
public class MpDependenteWV extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	//
    private MpClienteWV mpClienteWV;
    
	private Integer codigo = 0 ;

	private MpPessoa mpPessoa ; //

	private Long pessoaDepId = 0L;

	private String loja; // SistemaConfig = 00-Matriz/01-EmpresaFilial01 ...

	// ---
	public MpDependenteWV() {
	  super();
	}
	
	public MpDependenteWV(MpClienteWV mpClienteWV
            		, Integer codigo
            		, MpPessoa mpPessoa
            		, String loja
				) {
			this.mpClienteWV = mpClienteWV; 
			this.codigo = codigo; 
			this.mpPessoa = mpPessoa; 
			this.loja = loja; 
	}
	  
    @ManyToOne
    @JoinColumn(name = "mpClienteWVID")
    public MpClienteWV getMpClienteWV() { return this.mpClienteWV; }
    public void setMpClienteWV(MpClienteWV mpClienteWV) { this.mpClienteWV = mpClienteWV; }

	@Column(nullable = false, length = 15)
	public Integer getCodigo() { return this.codigo; }
	public void setCodigo(Integer newCodigo) { this.codigo = newCodigo; }

	@OneToOne(fetch=FetchType.EAGER) 
	@JoinColumn(name="mpPessoaId")
	public MpPessoa getMpPessoa() { return this.mpPessoa; }
	public void setMpPessoa(MpPessoa newMpPessoa) { this.mpPessoa = newMpPessoa; }

	public Long getPessoaDepId() { return this.pessoaDepId; }
	public void setPessoaDepId(Long newPessoaDepId) { this.pessoaDepId = newPessoaDepId; }
	  
	@Column(nullable = false, length = 2)
	public String getLoja() { return this.loja; }
	public void setLoja(String newLoja) { this.loja = newLoja; }
	
}
