package com.mpxds.mpbasic.model.pt01;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt01_data_processo_")
@Table(name="mp_pt01_data_processo")
public class MpDataProcesso extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataProtocolo;  	
	private Date dataAte;  
	private String protocoloInicial;  
	private String protocoloFinal;  

	// ---
	
	public MpDataProcesso() {
		super();
	}

  	public MpDataProcesso(Date dataProtocolo
             , Date dataAte
             , String protocoloInicial
             , String protocoloFinal
             ) {
  		this.dataProtocolo = dataProtocolo;
  		this.dataAte = dataAte;
  		this.protocoloInicial = protocoloInicial;
  		this.protocoloFinal = protocoloFinal;
  	}

  	// ---
  	
  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_protocolo", unique = true, nullable = false)
	public Date getDataProtocolo() { return dataProtocolo; }
	public void setDataProtocolo(Date dataProtocolo) { this.dataProtocolo = dataProtocolo; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_ate", nullable = false)
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }

    @Column(name = "protocolo_inicial", nullable = false, length = 6)
	public String getProtocoloInicial() { return protocoloInicial; }
	public void setProtocoloInicial(String protocoloInicial) { this.protocoloInicial = protocoloInicial; }

    @Column(name = "protocolo_final", nullable = false, length = 6)
	public String getProtocoloFinal() { return protocoloFinal; }
	public void setProtocoloFinal(String protocoloFinal) { this.protocoloFinal = protocoloFinal; }
  	 
}
