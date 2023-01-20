package com.mpxds.mpbasic.model.pt08;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt08_feriado_")
@Table(name="mp_pt08_feriado")
public class MpFeriado extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataFeriado;  
	private String descricao;  

	// ---
	
	public MpFeriado() {
		super();
	}
	
	// ---

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_feriado", nullable = false, length = 10)
	public Date getDataFeriado() { return dataFeriado; }
	public void setDataFeriado(Date dataFeriado) { this.dataFeriado = dataFeriado; }

  	@Column(nullable = false, length = 100)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
  	 	
}
