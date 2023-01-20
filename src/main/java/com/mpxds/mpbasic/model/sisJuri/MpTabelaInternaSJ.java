package com.mpxds.mpbasic.model.sisJuri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;

@Entity
@Table(name="mp_sj_tabela_interna")
public class MpTabelaInternaSJ extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ;	  

	private String codigo;	
	private String descricao;
		
	private MpTabelaInternaSJ mpPai;
	
	private List<MpTabelaInternaSJ> mpFilhas = new ArrayList<>();
	
    private Long idCarga;

	// ---
	
	public MpTabelaInternaSJ() {
		super();
	}
    
	// --- 
	
	@NotNull(message = "Por favor, informe o Tipo Tabela")
	@Column(name="mptipo_tabela_interna_id", nullable = false, length = 10)
	public MpTipoTabelaInternaSJ getMpTipoTabelaInternaSJ() { return mpTipoTabelaInternaSJ; }
	public void setMpTipoTabelaInternaSJ(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
																this.mpTipoTabelaInternaSJ = mpTipoTabelaInternaSJ; }

	@NotBlank(message = "Por favor, informe o Código")
	@Column(nullable = false, length = 50)
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	@NotBlank(message = "Por favor, informe a Descrição")
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@ManyToOne
	@JoinColumn(name = "mpPai_Id")
	public MpTabelaInternaSJ getMpPai() { return mpPai; }
	public void setMpPai(MpTabelaInternaSJ mpPai) { this.mpPai = mpPai; }

	@OneToMany(mappedBy = "mpPai", cascade = CascadeType.ALL)
	public List<MpTabelaInternaSJ> getMpFilhas() { return mpFilhas; }
	public void setMpFilhas(List<MpTabelaInternaSJ> mpFilhas) { this.mpFilhas = mpFilhas; }

	@Column(name = "id_carga", nullable = true)
	public Long getIdCarga() { return this.idCarga; }
	public void setIdCarga(Long idCarga) { this.idCarga = idCarga; }
	
	// ---
	
	@Transient
	public String getDescricaoNumero() { 
		//
		if (null==this.descricao) this.descricao = "";
		//
	    return this.descricao.trim() + " ( " + this.mpTipoTabelaInternaSJ.getDescricao() + " / " +
	    									   this.mpTipoTabelaInternaSJ.getTabela() + " )";
	}

}
