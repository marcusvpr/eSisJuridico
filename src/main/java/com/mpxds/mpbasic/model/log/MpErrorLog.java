/**
 * Copyright 2014 the original author or authors.
 * Oxitec. http://www.oxitec.de 
 * 
 * This file is part of ZKBoost.
 * 
 * This code is bundled with the eBook: <br> 
 * 'Mastering ZK web applications with responsive design<br>
 *  A practical and comprehensive guide for ZKBoost'<br>
 */
package com.mpxds.mpbasic.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mpxds.mpbasic.model.MpBaseEntity;

@Entity
@Table(name = "mp_error_log")
public class MpErrorLog extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String className = new String("");
	private Date createdDate;
	private String errorCode = new String("");
	private Integer lineNumber;
	private String message = new String("");
	private String methodName = new String("");
	private String sqlState = new String("");
	private Integer type = 0;

	// ---------
	
	public MpErrorLog() {
		super();
	}

	@Column(name = "erro_classname", nullable = true)
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String _className) {
		this.className = _className;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "erro_created_date", nullable = false)
	public Date getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(Date _createdDate) {
		this.createdDate = _createdDate;
	}

	@Column(name = "erro_errorcode", nullable = true)
	public String getErrorCode() {
		return this.errorCode;
	}
	public void setErrorCode(String _errorCode) {
		this.errorCode = _errorCode;
	}

	@Column(name = "erro_line", nullable = true)
	public int getLineNumber() {
		return this.lineNumber;
	}
	public void setLineNumber(int _lineNumber) {
		this.lineNumber = _lineNumber;
	}

	@Column(name = "erro_message", nullable = true)
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String _message) {
		this.message = _message;
	}

	@Column(name = "erro_methodname", nullable = true)
	public String getMethodName() {
		return this.methodName;
	}
	public void setMethodName(String _methodName) {
		this.methodName = _methodName;
	}

	@Column(name = "erro_sqlstate", nullable = true)
	public String getSqlState() {
		return this.sqlState;
	}
	public void setSqlState(String _sqlState) {
		this.sqlState = _sqlState;
	}

	// 1 = DB exception. <br>
	// 2 = Other exception.<br>
	// 3 = ARITHMETIC exception <br>
	// 4 = ZKoss UI exception <br>
	@Column(name = "erro_type", nullable = true)
	public Integer getType() {
		return this.type;
	}
	public void setType(Integer _type) {
		this.type = _type;
	}

}
