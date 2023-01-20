package com.mpxds.mpbasic.model.pt05;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt05_ato_")
@Table(name = "mp_pt05_ato")
public class MpAto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String sequencia;
	private String descricao;
	private String tipoSelo;
	
	private Boolean indAlteraValorAto; 

	private MpValorAto mpValorAto = new MpValorAto(); // MpTitulo usa também ! 
	
	private List<MpAtoComposicao> mpAtoComposicaos = new ArrayList<MpAtoComposicao>();

	// ---
		
	public MpAto(){
		super();
	}

	@NotBlank(message = "Por favor, informe o Código")	
	@Column(nullable = false, length = 4)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo.toUpperCase(); }

	@NotBlank(message = "Por favor, informe a Sequência")	
	@Column(nullable = false, length = 1)
	public String getSequencia() { return sequencia; }
	public void setSequencia(String sequencia) { this.sequencia = sequencia.toUpperCase(); }

	@NotBlank(message = "Por favor, informe a Descrição")	
	@Column(nullable = false, length = 100)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao.toUpperCase(); }

	@NotBlank(message = "Por favor, informe o Tipo Selo")	
	@Column(name = "tipo_selo", nullable = false, length = 1)
	public String getTipoSelo() { return tipoSelo; }
	public void setTipoSelo(String tipoSelo) { this.tipoSelo = tipoSelo.toUpperCase(); }
		
	@Column(name = "ind_altera_valor_ato", nullable = true)
	public Boolean getIndAlteraValorAto() { return indAlteraValorAto; }
	public void setIndAlteraValorAto(Boolean indAlteraValorAto) {
													this.indAlteraValorAto = indAlteraValorAto; }
	
	@Embedded
	public MpValorAto getMpValorAto() { return mpValorAto; }
	public void setMpValorAto(MpValorAto mpValorAto) { this.mpValorAto = mpValorAto; }	

	@OneToMany(mappedBy = "mpAto", cascade = CascadeType.ALL)
	public List<MpAtoComposicao> getMpAtoComposicaos() { return mpAtoComposicaos; }
	public void setMpAtoComposicaos(List<MpAtoComposicao> mpAtoComposicaos) { 
														this.mpAtoComposicaos = mpAtoComposicaos; }

	// ---
	
	@Transient
	public String getCodigoSequencia() {
		if (null==this.codigo) this.codigo = "";
		if (null==this.sequencia) this.sequencia = "";
		//
	    return this.codigo.trim() + "." + this.sequencia.trim();
	}
	
}