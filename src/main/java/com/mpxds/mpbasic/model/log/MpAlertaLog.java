package com.mpxds.mpbasic.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.MpAlertaStatus;

@Entity
@Table(name = "mp_alerta_log")
public class MpAlertaLog extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	@Column(name = "data_alerta", nullable = false, length = 10)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlerta;

	@Column(name = "data_movimento", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataMovimento;

	@Column(name = "alerta_id", nullable = false)
	private Long alertaId;

	@Column(name = "tipo_alerta", nullable = false, length = 50)
	private String tipoAlerta;

	@Column(name = "numero_alerta", nullable = false, length = 2)
	private Integer numeroAlerta = 0;

	@Enumerated(EnumType.STRING)
	@Column(name = "mpAleta_status", nullable = true, length = 30)
	private MpAlertaStatus mpAlertaStatus;

	@Column(name = "tempo_adiantamento", nullable = false, length = 5)
	private Integer tempoAdiantamento = 0;

	// ---
	
	public MpAlertaLog() {
		super();
	}

	public Date getDataAlerta() { return this.dataAlerta; }
	public void setDataAlerta(Date newDataAlerta) { this.dataAlerta = newDataAlerta; }

	public Date getDataMovimento() { return this.dataMovimento; }
	public void setDataMovimento(Date newDataMovimento) { this.dataMovimento = newDataMovimento; }

	public Long getAlertaId() { return this.alertaId; }
	public void setAlertaId(Long newAlertaId) { this.alertaId = newAlertaId; }

	public String getTipoAlerta() { return this.tipoAlerta; }
	public void setTipoAlerta(String newTipoAlerta) { this.tipoAlerta = newTipoAlerta; }

	public Integer getNumeroAlerta() { return this.numeroAlerta; }
	public void setNumeroAlerta(Integer newNumeroAlerta) { this.numeroAlerta = newNumeroAlerta; }

	public MpAlertaStatus getMpAlertaStatus() { return this.mpAlertaStatus; }
	public void setMpAlertaStatus(MpAlertaStatus newMpAlertaStatus) {
														this.mpAlertaStatus = newMpAlertaStatus; }

	public Integer getTempoAdiantamento() { return this.tempoAdiantamento; }
	public void setTempoAdiantamento(Integer newTempoAdiantamento) {
													this.tempoAdiantamento = newTempoAdiantamento; }

}