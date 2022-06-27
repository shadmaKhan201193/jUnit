package com.itl.domain.entities.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public class Base2 implements Serializable {

	private static final long serialVersionUID = 1701926931204630606L;

	@Version
	protected Integer version = 0;

	@Column(nullable = true)
	protected Long activityId=0L;
	
	@Column(length = 100, nullable = true)
	private String description = "";

	@Column(length = 10, nullable = true)
	protected String createdBy="";

	@Column(nullable =true) 
	protected Date createdDate;

	@Column(nullable = true)
	private Date createdTime;
	
	@Column(length = 10, nullable = true)
	protected String lastModifiedBy="";

	@Column(nullable =true)
	protected Date lastModifiedDate;
	
	@Column(nullable = true)
	private Date lastModifiedTime;
	
	@Column(length = 10, nullable = true)
	protected String deprecatedBy="";

	@Column(nullable =true)
	protected Date deprecatedDate;
	
	@Column(nullable = true)
	private Date deprecatedTime;
	
	@Basic
	Integer deprecated=0;
	
	@Transient
	public Double initDouble = 0.00;
	@Transient
	public int initInteger = 0;
	@Transient
	public String initString = " ";
	@Transient
	public long initLongInt = 0L;
	@Transient
	public BigDecimal initBDInt = BigDecimal.ZERO;
	@Transient
	public Integer noOfAuthNeeded = 1;

	@Transient
	public Integer conCurChkYN = 1;

	@Transient
	public Integer uaeYN = 0;

	@Transient
	private String ipAddr = "";
	@Transient
	private String rejectReason = "";
	
	@Transient
	public Integer bCodeInt = 0;
	
	@Transient
	public String menuCodeString = "";
	
	@Transient
	public String authMKCKUrl = "";
	
	@Transient
	public Integer authMKCKtobeDone = 0;
	
	public Date getCreatedTime() {
		if(null!=createdTime ){} else {createdTime = initDate();}
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastModifiedTime() {
		if(null!=lastModifiedTime ){} else {lastModifiedTime = initDate();}
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getDeprecatedTime() {
		if(null!=deprecatedTime ){} else {deprecatedTime = initDate();}
		return deprecatedTime;
	}

	public void setDeprecatedTime(Date deprecatedTime) {
		this.deprecatedTime = deprecatedTime;
	}

	public Integer getVersion() {
		if(null!=version ){} else {version = initInteger;}
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescription() {
		if(null!=description ){} else {description = initString;}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		if(null!=createdBy ){} else {createdBy = initString;}
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeprecatedBy() {
		if(null!=deprecatedBy ){} else {deprecatedBy = initString;}
		return deprecatedBy;
	}

	public void setDeprecatedBy(String deprecatedBy) {
		this.deprecatedBy = deprecatedBy;
	}

	public String getLastModifiedBy() {
		if(null!=lastModifiedBy ){} else {lastModifiedBy = initString;}
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		if(null!=createdDate ){} else {createdDate = initDate();}
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDeprecatedDate() {
		if(null!=deprecatedDate ){} else {deprecatedDate = initDate();}
		return deprecatedDate;
	}

	public void setDeprecatedDate(Date deprecatedDate) {
		this.deprecatedDate = deprecatedDate;
	}

	public Date getLastModifiedDate() {
		if(null!=lastModifiedDate ){} else {lastModifiedDate = initDate();}
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getDeprecated() {
		if(null!=deprecated ){} else {deprecated = initInteger;}
		return deprecated;
	}

	public void setDeprecated(Integer deprecated) {
		this.deprecated = deprecated;
	}

	public Long getActivityId() {
		if(null!=activityId ){} else {activityId = initLongInt;}
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Date initDate(){ 
		String intialDate ="01-JAN-1900";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			return sdf.parse(intialDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public Integer getNoOfAuthNeeded() {
		if(null!=noOfAuthNeeded ){} else {noOfAuthNeeded = initInteger;}
		return noOfAuthNeeded;
	}

	public void setNoOfAuthNeeded(Integer noOfAuthNeeded) {
		this.noOfAuthNeeded = noOfAuthNeeded;
	}

	public Integer getConCurChkYN() {
		if(null!=conCurChkYN ){} else {conCurChkYN = initInteger;}
		return conCurChkYN;
	}

	public void setConCurChkYN(Integer conCurChkYN) {
		this.conCurChkYN = conCurChkYN;
	}

	public Integer getUaeYN() {
		if(null!=uaeYN ){} else {uaeYN = initInteger;}
		return uaeYN;
	}

	public void setUaeYN(Integer uaeYN) {
		this.uaeYN = uaeYN;
	}
	
	public String setterStringReplace(String str) {
		str = str.replace("_", "\\_")
				.replace("%", "\\%")
				.replace("'", "\\'");
		return str;
	}
	
	public String getterStringReplace(String str) {
		str = str.replace("\\_","_")
				.replace("\\%","%")
				.replace("\\'","'");
		return str;
	}

	public String getIpAddr() {
		if(null!=ipAddr ){} else {ipAddr = initString;}
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getRejectReason() {
		if(null!=rejectReason ){} else {rejectReason = initString;}
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getbCodeInt() {
		return bCodeInt;
	}

	public void setbCodeInt(Integer bCodeInt) {
		this.bCodeInt = bCodeInt;
	}

	public String getMenuCodeString() {
		if(null!=menuCodeString ){} else {menuCodeString = initString;}
		return menuCodeString;
	}

	public void setMenuCodeString(String menuCodeString) {
		this.menuCodeString = menuCodeString;
	}

	public String getAuthMKCKUrl() {
		return authMKCKUrl;
	}

	public void setAuthMKCKUrl(String authMKCKUrl) {
		this.authMKCKUrl = authMKCKUrl;
	}

	public Integer getAuthMKCKtobeDone() {
		return authMKCKtobeDone;
	}

	public void setAuthMKCKtobeDone(Integer authMKCKtobeDone) {
		this.authMKCKtobeDone = authMKCKtobeDone;
	}

}
