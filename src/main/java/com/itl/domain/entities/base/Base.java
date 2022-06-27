package com.itl.domain.entities.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class Base implements Serializable {

	private static final long serialVersionUID = 1701926931204630606L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	protected Long id;

	@Version
	protected Integer version = 0;

	//@Column(length=6, nullable = false)
	//protected Integer tenantId=0;

	@Column(nullable = true)
	protected Long activityId=0L;
	
	@Column(length = 100, nullable = true)
	private String description = "";

	@Column(length = 10, nullable = true)
	protected String createdBy="";

	@Column(nullable =true) 
	protected Date createdDate;

	@Column(nullable = false)
	private Date createdTime;
	
	@Column(length = 10, nullable = true)
	protected String lastModifiedBy="";

	@Column(nullable =true)
	protected Date lastModifiedDate;
	
	@Column( nullable = false)
	private Date lastModifiedTime;
	
	@Column(length = 10, nullable = true)
	protected String deletedBy="";

	@Column(nullable =true)
	protected Date deletedDate;
	
	@Column(nullable = true)
	private Date deletedTime;
	
	@Basic
	Boolean isDeleted=false;
	
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
	
	public Long getId() {
		return id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/*public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	} */

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	
	@Transient
	public Integer noOfAuthNeeded = 1;

	@Transient
	public Boolean conCurChkYN = true;

	@Transient
	public Boolean uaeYN = false;

	public Integer getNoOfAuthNeeded() {
		return noOfAuthNeeded;
	}

	public void setNoOfAuthNeeded(Integer noOfAuthNeeded) {
		this.noOfAuthNeeded = noOfAuthNeeded;
	}

	public Boolean getConCurChkYN() {
		return conCurChkYN;
	}

	public void setConCurChkYN(Boolean conCurChkYN) {
		this.conCurChkYN = conCurChkYN;
	}

	public Boolean getUaeYN() {
		return uaeYN;
	}

	public void setUaeYN(Boolean uaeYN) {
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

}
