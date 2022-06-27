package com.itl.domain.entities.masters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.envers.Audited;

import com.itl.domain.entities.base.Base;

@Entity
@Audited(auditParents = { Base.class })
	@NamedQueries({
	@NamedQuery(name="MaritalStatusMst.getUniqueMaritalStatus", query = "SELECT e FROM MaritalStatusMst e WHERE e.id=:Id"),
	@NamedQuery(name="MaritalStatusMst.getMaritalStatusName", query = "SELECT e FROM MaritalStatusMst e WHERE e.maritalStatusName=:maritalStatusName"),
	@NamedQuery(name="MaritalStatusMst.getMaritalStatusId", query = "SELECT e FROM MaritalStatusMst e WHERE e.maritalStatusId=:maritalStatusId"),
	@NamedQuery(name="MaritalStatusMst.getMaritalStatusIdByMaritalStatusName", query = "SELECT e FROM MaritalStatusMst e WHERE e.maritalStatusName=:maritalStatusName"),
	@NamedQuery(name="MaritalStatusMst.getByAuthStatus", query = "SELECT e FROM MaritalStatusMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="MaritalStatusMst.getByDeleted", query = "SELECT e FROM MaritalStatusMst e WHERE e.isDeleted=:isDeleted")
})
public class MaritalStatusMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String maritalStatusId = "";
	
	@Column(nullable = false,length = 48)
	private String maritalStatusName ="";
	
	@Column(nullable = false,length = 48)
	private String maritalStatusDisplayName ="";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";
	
	public String getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(String maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getMaritalStatusDisplayName() {
		return maritalStatusDisplayName;
	}

	public void setMaritalStatusDisplayName(String maritalStatusDisplayName) {
		this.maritalStatusDisplayName = maritalStatusDisplayName;
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
