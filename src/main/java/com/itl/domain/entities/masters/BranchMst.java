package com.itl.domain.entities.masters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;

import com.itl.domain.entities.base.Base;

@Entity
@Audited(auditParents = { Base.class })
@NamedQueries({ @NamedQuery(name = "BranchMst.getUniqueBranch", query = "SELECT e FROM BranchMst e WHERE e.id=:Id"),
		@NamedQuery(name = "BranchMst.getBranchName", query = "SELECT e FROM BranchMst e WHERE e.branchName=:branchName"),
		@NamedQuery(name = "BranchMst.getBranchId", query = "SELECT e FROM BranchMst e WHERE e.branchId=:branchId"),
		@NamedQuery(name="BranchMst.getBranchIdByBranchName", query = "SELECT e FROM BranchMst e WHERE e.branchName=:branchName"),
		@NamedQuery(name = "BranchMst.getByAuthStatus", query = "SELECT e FROM BranchMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
		@NamedQuery(name="BranchMst.getByDeleted", query = "SELECT e FROM BranchMst e WHERE e.isDeleted=:isDeleted")
})
public class BranchMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String branchId = "";

	@Column(nullable = false, length = 48)
	private String branchName = "";

	@Column(nullable = false, length = 48)
	private String branchDisplayName = "";
	
	@Column(nullable = false, length = 8)
	private String baseCurrency ="";
	
	@Temporal(TemporalType.DATE)
	private Date bod;
	
	@Column(nullable = true)
	private Boolean bodflag = true;
	
	@Temporal(TemporalType.DATE)
	private Date eod;
	
	@Column(nullable = true)
	private Boolean eodFlag = true;
	
	@Column(nullable = false, length = 16)
	private String micr="";

	@Column(nullable = false, length = 16)
	private String ifsc="";

	@Column(nullable = true)
	private Integer isActive = 1;
	
	@Column(nullable = true,length = 8)
	private String authStatus;
	
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BranchDetails> branchDetail = new ArrayList<BranchDetails>();
	

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchDisplayName() {
		return branchDisplayName;
	}

	public void setBranchDisplayName(String branchDisplayName) {
		this.branchDisplayName = branchDisplayName;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Date getBod() {
		return bod;
	}

	public void setBod(Date bod) {
		this.bod = bod;
	}

	public Boolean getBodflag() {
		return bodflag;
	}

	public void setBodflag(Boolean bodflag) {
		this.bodflag = bodflag;
	}

	public Date getEod() {
		return eod;
	}

	public void setEod(Date eod) {
		this.eod = eod;
	}

	public Boolean getEodFlag() {
		return eodFlag;
	}

	public void setEodFlag(Boolean eodFlag) {
		this.eodFlag = eodFlag;
	}

	public String getMicr() {
		return micr;
	}

	public void setMicr(String micr) {
		this.micr = micr;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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