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
	@NamedQuery(name="ResidentialStatusMst.getUniqueResidentialStatus", query = "SELECT e FROM ResidentialStatusMst e WHERE e.id=:Id"),
	@NamedQuery(name="ResidentialStatusMst.getresidentialStatusName", query = "SELECT e FROM ResidentialStatusMst e WHERE e.residentialStatusName=:residentialStatusName"),
	@NamedQuery(name="ResidentialStatusMst.getresidentialStatusId", query = "SELECT e FROM ResidentialStatusMst e WHERE e.residentialStatusId=:residentialStatusId"),
	@NamedQuery(name = "ResidentialStatusMst.getByAuthStatus", query = "SELECT e FROM ResidentialStatusMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="ResidentialStatusMst.getByDeleted", query = "SELECT e FROM ResidentialStatusMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="ResidentialStatusMst.getResidentialStatusIdByResidentialStatusName", query = "SELECT e FROM ResidentialStatusMst e WHERE e.residentialStatusName=:residentialStatusName"),

})
public class ResidentialStatusMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String residentialStatusId = "";
	
	@Column(nullable = false,length = 48)
	private String residentialStatusName ="";
	
	@Column(nullable = false,length = 48)
	private String residentialStatusDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getResidentialStatusId() {
		return residentialStatusId;
	}

	public void setResidentialStatusId(String residentialStatusId) {
		this.residentialStatusId = residentialStatusId;
	}

	public String getResidentialStatusName() {
		return residentialStatusName;
	}

	public void setResidentialStatusName(String residentialStatusName) {
		this.residentialStatusName = residentialStatusName;
	}

	public String getResidentialStatusDisplayName() {
		return residentialStatusDisplayName;
	}

	public void setResidentialStatusDisplayName(String residentialStatusDisplayName) {
		this.residentialStatusDisplayName = residentialStatusDisplayName;
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
