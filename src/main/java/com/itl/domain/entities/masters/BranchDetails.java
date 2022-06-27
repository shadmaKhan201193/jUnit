package com.itl.domain.entities.masters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.itl.domain.entities.base.Base;

@Entity
@Audited(auditParents = { Base.class })
public class BranchDetails extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false)
	private Integer brSrNo = 1;

	@ManyToOne
	@JoinColumn(name = "fkBranchPk")
	@LazyCollection(LazyCollectionOption.FALSE)
	private BranchMst branch;

	@NotAudited
	@ElementCollection
	@JoinTable(name = "BranchAddress", joinColumns = @JoinColumn(name = "BranchDetail_ID"))
	@GenericGenerator(name = "sequence_gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "Id") }, generator = "sequence_gen", type = @Type(type = "long"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Address> branchAddresses = new ArrayList<Address>();
	
	@Temporal(TemporalType.TIME)
	private Date brFromTime;
	
	@Temporal(TemporalType.TIME)
	private Date brToTime;
	
	@Column(nullable = true)
	private Integer brFromDay=0;
	
	@Column(nullable = true)
	private Integer brToDay=0;
	
	@Column(nullable = false, length = 8)
	private String branchType="";
	
	@Column(nullable = true)
	private Integer finYearFrom=4;
	
	@Column(nullable = true)
	private Integer finYearTo=3;
	
	@Column(nullable = false, length = 48)
	private String reportingManager="";
		
	@Column(nullable = true)
	private Integer WeeklyOff1=6;
	
	@Column(nullable = true)
	private Integer WeeklyOff2=7;
	
	@Column(nullable = false, length = 48)
	private String rmEmailId="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus;

	public Integer getBrSrNo() {
		return brSrNo;
	}

	public void setBrSrNo(Integer brSrNo) {
		this.brSrNo = brSrNo;
	}

	public BranchMst getBranch() {
		return branch;
	}

	public void setBranch(BranchMst branch) {
		this.branch = branch;
	}

	public Collection<Address> getBranchAddresses() {
		return branchAddresses;
	}

	public void setBranchAddresses(Collection<Address> branchAddresses) {
		this.branchAddresses = branchAddresses;
	}

	public Date getBrFromTime() {
		return brFromTime;
	}

	public void setBrFromTime(Date brFromTime) {
		this.brFromTime = brFromTime;
	}

	public Date getBrToTime() {
		return brToTime;
	}

	public void setBrToTime(Date brToTime) {
		this.brToTime = brToTime;
	}

	public Integer getBrFromDay() {
		return brFromDay;
	}

	public void setBrFromDay(Integer brFromDay) {
		this.brFromDay = brFromDay;
	}

	public Integer getBrToDay() {
		return brToDay;
	}

	public void setBrToDay(Integer brToDay) {
		this.brToDay = brToDay;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public Integer getFinYearFrom() {
		return finYearFrom;
	}

	public void setFinYearFrom(Integer finYearFrom) {
		this.finYearFrom = finYearFrom;
	}

	public Integer getFinYearTo() {
		return finYearTo;
	}

	public void setFinYearTo(Integer finYearTo) {
		this.finYearTo = finYearTo;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public Integer getWeeklyOff1() {
		return WeeklyOff1;
	}

	public void setWeeklyOff1(Integer weeklyOff1) {
		WeeklyOff1 = weeklyOff1;
	}

	public Integer getWeeklyOff2() {
		return WeeklyOff2;
	}

	public void setWeeklyOff2(Integer weeklyOff2) {
		WeeklyOff2 = weeklyOff2;
	}

	public String getRmEmailId() {
		return rmEmailId;
	}

	public void setRmEmailId(String rmEmailId) {
		this.rmEmailId = rmEmailId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
}