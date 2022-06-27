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
		@NamedQuery(name = "OccupationMst.getUniqueOccupation", query = "SELECT e FROM OccupationMst e WHERE e.id=:Id"),
		@NamedQuery(name = "OccupationMst.getOccupationName", query = "SELECT e FROM OccupationMst e WHERE e.occupationName=:occupationName"),
		@NamedQuery(name = "OccupationMst.getOccupationId", query = "SELECT e FROM OccupationMst e WHERE e.occupationId=:occupationId"),
		@NamedQuery(name = "OccupationMst.getByAuthStatus", query = "SELECT e FROM OccupationMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
		@NamedQuery(name="OccupationMst.getByDeleted", query = "SELECT e FROM OccupationMst e WHERE e.isDeleted=:isDeleted"),
		@NamedQuery(name="OccupationMst.getOccupationIdByOccupationName", query = "SELECT e FROM OccupationMst e WHERE e.occupationName=:occupationName"),

})
public class OccupationMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String occupationId = "";

	@Column(nullable = false, length = 48)
	private String occupationName = "";

	@Column(nullable = false, length = 48)
	private String occupationDisplayName = "";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus;

	public String getOccupationId() {
		return occupationId;
	}

	public void setOccupationId(String occupationId) {
		this.occupationId = occupationId;
	}

	public String getOccupationName() {
		return occupationName;
	}

	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}

	public String getOccupationDisplayName() {
		return occupationDisplayName;
	}

	public void setOccupationDisplayName(String occupationDisplayName) {
		this.occupationDisplayName = occupationDisplayName;
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
