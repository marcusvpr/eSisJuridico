package com.mpxds.mpbasic.model.sisJuri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;

@Entity
@Table(name = "MP_SJ_PROCESSO")
public class MpProcesso extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
	private Date dataCadastro;	
	private String processoCodigo;
	private String autor;
	
	private MpPessoaSJ mpParteContraria;
	private MpClienteSJ mpClienteSJ;
	private MpPessoaSJ mpAdvogadoResponsavel;
	private MpTabelaInternaSJ mpComarca; // TAB_1008 (Pai)
	private MpTabelaInternaSJ mpComarcaCartorio; // TAB_1042 (Filha)
	
	private String pastaCliente;
	
	private List<MpProcessoAndamento> mpAndamentos = new ArrayList<MpProcessoAndamento>();
	
    private Long idCarga;
		
	// ---
	
	public MpProcesso() {
		super();
	}
    
	// --- 

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro", nullable = false, length = 10) // , unique = true
	public Date getDataCadastro() { return dataCadastro; }
	public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

	@NotBlank(message = "Código é obrigatório")
 	@Column(name = "processo_codigo", nullable = true, length = 30)
	public String getProcessoCodigo() { return processoCodigo; }
	public void setProcessoCodigo(String processoCodigo) { this.processoCodigo = processoCodigo; }

	@NotBlank(message = "Autor é obrigatório")
 	@Column(nullable = true, length = 150)
	public String getAutor() { return autor; }
	public void setAutor(String autor) { this.autor = autor; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpParte_Contraria_id")
	public MpPessoaSJ getMpParteContraria() { return mpParteContraria; }
	public void setMpParteContraria(MpPessoaSJ mpParteContraria) { this.mpParteContraria = mpParteContraria; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpCliente_id")
	public MpClienteSJ getMpClienteSJ() { return mpClienteSJ; }
	public void setMpClienteSJ(MpClienteSJ mpClienteSJ) { this.mpClienteSJ = mpClienteSJ; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAdvogado_Responsavel_id")
	public MpPessoaSJ getMpAdvogadoResponsavel() { return mpAdvogadoResponsavel; }
	public void setMpAdvogadoResponsavel(MpPessoaSJ mpAdvogadoResponsavel) { 
														this.mpAdvogadoResponsavel = mpAdvogadoResponsavel; }

	@ManyToOne
	@JoinColumn(name = "mpComarca_id", nullable = true)
	public MpTabelaInternaSJ getMpComarca() { return mpComarca; }
	public void setMpComarca(MpTabelaInternaSJ mpComarca) { this.mpComarca = mpComarca; }

	@ManyToOne
	@JoinColumn(name = "mpComarca_cartorio_id", nullable = true)
	public MpTabelaInternaSJ getMpComarcaCartorio() { return mpComarcaCartorio; }
	public void setMpComarcaCartorio(MpTabelaInternaSJ mpComarcaCartorio) {
																this.mpComarcaCartorio = mpComarcaCartorio; }

	@Column(name = "pasta_cliente", nullable = false, length = 200)
	public String getPastaCliente() { return pastaCliente; }
	public void setPastaCliente(String pastaCliente) { this.pastaCliente = pastaCliente; }

	@OneToMany(mappedBy = "mpProcesso", cascade = CascadeType.ALL, orphanRemoval = true,
																				fetch = FetchType.LAZY)
	public List<MpProcessoAndamento> getMpAndamentos() { return mpAndamentos; }
	public void setMpAndamentos(List<MpProcessoAndamento> mpAndamentos) { this.mpAndamentos = mpAndamentos; }

	@Column(name = "id_carga", nullable = true)
	public Long getIdCarga() { return this.idCarga; }
	public void setIdCarga(Long idCarga) { this.idCarga = idCarga; }
	
	// ---
	
	@Transient
	public MpProcessoAndamentoDataModel getMpAndamentosDataModel() {
		//
		return new MpProcessoAndamentoDataModel(this.mpAndamentos);
	}
		
}
