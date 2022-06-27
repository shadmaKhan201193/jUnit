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
	@NamedQuery(name="ReligionMst.getUniqueReligion", query = "SELECT e FROM ReligionMst e WHERE e.id=:Id"),
	@NamedQuery(name="ReligionMst.getreligionName", query = "SELECT e FROM ReligionMst e WHERE e.religionName=:religionName"),
	@NamedQuery(name="ReligionMst.getreligionId", query = "SELECT e FROM ReligionMst e WHERE e.religionId=:religionId"),
	@NamedQuery(name = "ReligionMst.getByAuthStatus", query = "SELECT e FROM ReligionMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="ReligionMst.getByDeleted", query = "SELECT e FROM ReligionMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="ReligionMst.getReligionIdByReligionName", query = "SELECT e FROM ReligionMst e WHERE e.religionName=:religionName")
})
public class ReligionMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String religionId = "";
	
	@Column(nullable = false,length = 48)
	private String religionName ="";
	
	@Column(nullable = false,length = 48)
	private String religionDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getReligionDisplayName() {
		return religionDisplayName;
	}

	public void setReligionDisplayName(String religionDisplayName) {
		this.religionDisplayName = religionDisplayName;
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
