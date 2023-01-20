package com.mpxds.mpbasic.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Audited
@AuditTable(value="MP_DOLAR_")
@Table(name="MP_DOLAR")
@ToString
public class MpDolar extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	@Column(name =  "DATA_MOVIMENTO", nullable = false, length = 20)
	@Getter @Setter
	private Date dataMovimento; 
	//
}
