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

/**
 * The model for login log.<br>
 * --------------------------------------------------------------------<br>
 * 
 * @author Stephan Gerth
 */
@Entity
@Table(name = "mp_login_log")
public class MpLoginLog extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	/**
	 * UserID to which affects the login.
	 */
	@Column(name = "log_usr_id", nullable = true)
	private Long userId;

	/**
	 * The used login name.
	 */
	@Column(name = "log_login_name", nullable = true)
	private String loginName = new String("");
	/**
	 * Client-IP from the login.
	 */
	@Column(name = "log_client_ip", nullable = true)
	private String clientIp = new String("");

	/**
	 * Result of the login. 1=success; 2=failed.
	 */
	@Column(name = "log_result", nullable = true)
	private int result;

	/**
	 * Failed login name if login failed for an unregistered user. To detect
	 * account hacking.
	 */
	@Column(name = "log_failed_loginname", nullable = true)
	private String failedLoginName = new String("");

	/**
	 * Failed password if login failed for an unregistered user. To detect
	 * account hacking.
	 */
	@Column(name = "log_failed_password", nullable = true)
	private String failedPassword = new String("");

	/**
	 * Date when the user try to log in.
	 */
	@Column(name = "created_date", updatable = true, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	/**
	 * The default constructor.
	 */
	public MpLoginLog() {
		super();
	}

	/**
	 * Returns the login result.
	 * 
	 * @return Login result.
	 */
	public int getResult() { return this.result; }

	/**
	 * Sets the login result.
	 * 
	 * @param _result
	 *            Login result.
	 */
	public void setResult(int _result) { this.result = _result; }

	/**
	 * Returns the client IP.
	 * 
	 * @return The client IP.
	 */
	public String getClientIp() { return this.clientIp; }

	/**
	 * Returns the Loginname if login is failed.
	 * 
	 * @return Loginname if login is failed.
	 */
	public String getFailedLoginName() { return this.failedLoginName; }

	/**
	 * Returns the Password if login is failed for unregistered user.
	 * 
	 * @return Password if login is failed for unregistered user.
	 */
	public String getFailedPassword() { return this.failedPassword; }

	/**
	 * Returns the user ID from the login if the user is registered.
	 * 
	 * @return User ID from the login if the user is registered.
	 */
	public Long getUserId() { return this.userId; }

	/**
	 * Sets the user ID from the login if the user is registered.
	 * 
	 * @param _userId
	 *            User ID from the login if the user is registered.
	 */
	public void setUserId(Long _userId) { this.userId = _userId; }

	/**
	 * Returns the used login name.
	 * 
	 * @return Used login name.
	 */
	public String getLoginName() { return this.loginName; }

	/**
	 * Sets the used login name.
	 * 
	 * @param _loginName
	 *            Used login name.
	 */
	public void setLoginName(String _loginName) { this.loginName = _loginName; }

	/**
	 * Sets the client IP.
	 * 
	 * @param _clientIp
	 *            The client IP.
	 */
	public void setClientIp(String _clientIp) { this.clientIp = _clientIp; }

	/**
	 * Sets the Loginname if login is failed for unregistered user.
	 * 
	 * @param _failedLoginname
	 *            Loginname if login is failed for unregistered user.
	 */
	public void setFailedLoginName(String _failedLoginname) { 
														this.failedLoginName = _failedLoginname; }

	/**
	 * Sets the Password if login is failed for unregistered user.
	 * 
	 * @param _failedPassword
	 *            Password if login is failed for unregistered user.
	 */
	public void setFailedPassword(String _failedPassword) {	this.failedPassword = _failedPassword; }

	/**
	 * Gets date when the login is created.
	 * 
	 * @param _createdDate
	 *            Timestamp for login try.
	 */
	public Date getCreatedDate() { return this.createdDate; }

	/**
	 * Sets date when the login is created.
	 * 
	 * @param _createdDate
	 *            Timestamp for login try.
	 */
	public void setCreatedDate(Date _createdDate) { this.createdDate = _createdDate; }

}