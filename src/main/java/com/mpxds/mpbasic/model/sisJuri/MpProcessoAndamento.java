package com.mpxds.mpbasic.model.sisJuri;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.sisJuri.MpTabelaInternaSJ;

@Entity
@Table(name = "MP_SJ_PROCESSO_ANDAMENTO")
public class MpProcessoAndamento extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	
    private MpProcesso mpProcesso;

	private Date dataCadastro = new Date();
  	private String detalhamento; // <1070>
	
	private MpTabelaInternaSJ mpAndamentoTipo; // <1022>
	private MpTabelaInternaSJ mpAtoPraticado; // <1040>

	private String obsFotoBD;
	private String tipoFotoBD;

	private byte[] fotoBD;
	
    private Long idCarga;

	// ---
	
	@ManyToOne
	@JoinColumn(name = "mpProcesso_id", nullable = false)
	public MpProcesso getMpProcesso() { return mpProcesso; }
	public void setMpProcesso(MpProcesso mpProcesso) { this.mpProcesso = mpProcesso; }

  	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro", nullable = false)
	public Date getDataCadastro() { return dataCadastro; }
	public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

	@Column(nullable = true, length = 250)
	public String getDetalhamento() { return detalhamento; }
	public void setDetalhamento(String detalhamento) { this.detalhamento = detalhamento; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAndamento_Tipo_Id")
	public MpTabelaInternaSJ getMpAndamentoTipo() { return mpAndamentoTipo; }
	public void setMpAndamentoTipo(MpTabelaInternaSJ mpAndamentoTipo) { this.mpAndamentoTipo = mpAndamentoTipo; }

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "mpAto_Praticado_Id")
	public MpTabelaInternaSJ getMpAtoPraticado() { return mpAtoPraticado; }
	public void setMpAtoPraticado(MpTabelaInternaSJ mpAtoPraticado) { this.mpAtoPraticado = mpAtoPraticado; }

	@Column(name = "obs_Foto_BD", nullable = true, length = 100)
	public String getObsFotoBD() { return obsFotoBD; }
	public void setObsFotoBD(String obsFotoBD) { this.obsFotoBD = obsFotoBD; }

	@Column(name = "tipo_Foto_BD", nullable = true, length = 10)
	public String getTipoFotoBD() { return tipoFotoBD; }
	public void setTipoFotoBD(String tipoFotoBD) { this.tipoFotoBD = tipoFotoBD; }

	@Lob
	@Column(name = "foto_BD", columnDefinition = "blob", nullable = true, length = 10000)
	public byte[] getFotoBD() { return fotoBD; }
	public void setFotoBD(byte[] fotoBD) { this.fotoBD = fotoBD; }

	@Column(name = "id_carga", nullable = true)
	public Long getIdCarga() { return this.idCarga; }
	public void setIdCarga(Long idCarga) { this.idCarga = idCarga; }

	// ---
	
	@Transient
	public boolean checkFotoBD() {
		//
		if (null == this.fotoBD) return false;
		else return true;
	}
	
	@Transient
	public String getDataCadastroSDF() {
		//		
		if (null == this.dataCadastro) this.dataCadastro = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		return sdf.format(this.dataCadastro);
	}
	
}