package com.mpxds.mpbasic.model.pt05;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Audited
@AuditTable(value="mp_pt05_importar_controle_")
@Table(name="mp_pt05_importar_controle")
public class MpImportarControle extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private Date dataImportar;  
	private Date dataControle;  
	private Date dataAte;  

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// ---
	
	public MpImportarControle() {
		super();
	}

  	public MpImportarControle(Date dataImportar
             , Date dataControle
             , Date dataAte
             ) {
  		this.dataImportar = dataImportar;
  		this.dataControle = dataControle;
  		this.dataAte = dataAte;
  	}
  	
  	// ---
  	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_importar", nullable = false, length = 10)
	public Date getDataImportar() { return dataImportar; }
	public void setDataImportar(Date dataImportar) { this.dataImportar = dataImportar; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_controle", nullable = false, length = 10)
	public Date getDataControle() { return dataControle; }
	public void setDataControle(Date dataControle) { this.dataControle = dataControle; }

  	@Temporal(TemporalType.TIMESTAMP)
  	@Column(name = "data_ate", nullable = false, length = 10)
	public Date getDataAte() { return dataAte; }
	public void setDataAte(Date dataAte) { this.dataAte = dataAte; }
	
	// ---

	@Transient
	public String getDataImportarSDF() { return this.sdf.format(this.dataImportar); }

	@Transient
	public String getDataControleSDF() { return this.sdf.format(this.dataControle); }

	@Transient
	public String getDataAteSDF() { return this.sdf.format(this.dataAte); }
 
}
