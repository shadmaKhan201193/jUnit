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
	@NamedQuery(name="SalutationMst.getUniqueSalutation", query = "SELECT e FROM SalutationMst e WHERE e.id=:Id"),
	@NamedQuery(name="SalutationMst.getsalutationName", query = "SELECT e FROM SalutationMst e WHERE e.salutationName=:salutationName"),
	@NamedQuery(name="SalutationMst.getsalutationId", query = "SELECT e FROM SalutationMst e WHERE e.salutationId=:salutationId"),
	@NamedQuery(name="SalutationMst.getSalutationIdBySalutationName", query = "SELECT e FROM SalutationMst e WHERE e.salutationName=:salutationName"),
	@NamedQuery(name = "SalutationMst.getByAuthStatus", query = "SELECT e FROM SalutationMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="SalutationMst.getByDeleted", query = "SELECT e FROM SalutationMst e WHERE e.isDeleted=:isDeleted")

})
public class SalutationMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String salutationId = "";
	
	@Column(nullable = false,length = 48)
	private String salutationName ="";
	
	@Column(nullable = false,length = 48)
	private String salutationDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getSalutationId() {
		return salutationId;
	}

	public void setSalutationId(String salutationId) {
		this.salutationId = salutationId;
	}

	public String getSalutationName() {
		return salutationName;
	}

	public void setSalutationName(String salutationName) {
		this.salutationName = salutationName;
	}

	public String getSalutationDisplayName() {
		return salutationDisplayName;
	}

	public void setSalutationDisplayName(String salutationDisplayName) {
		this.salutationDisplayName = salutationDisplayName;
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
