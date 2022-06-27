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
	@NamedQuery(name="IndustryMst.getUniqueIndustry", query = "SELECT e FROM IndustryMst e WHERE e.id=:Id"),
	@NamedQuery(name="IndustryMst.getByDeleted", query = "SELECT e FROM IndustryMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="IndustryMst.getByAuthStatus", query = "SELECT e FROM IndustryMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="IndustryMst.getindustryName", query = "SELECT e FROM IndustryMst e WHERE e.industryName=:industryName"),
	@NamedQuery(name="IndustryMst.getIndustryIdByIndustryName", query = "SELECT e FROM IndustryMst e WHERE e.industryName=:industryName"),
	@NamedQuery(name="IndustryMst.getindustryId", query = "SELECT e FROM IndustryMst e WHERE e.industryId=:industryId")
})
public class IndustryMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String industryId = "";
	
	@Column(nullable = false,length = 48)
	private String industryName ="";
	
	@Column(nullable = false,length = 48)
	private String industryDisplayName ="";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryDisplayName() {
		return industryDisplayName;
	}

	public void setIndustryDisplayName(String industryDisplayName) {
		this.industryDisplayName = industryDisplayName;
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
