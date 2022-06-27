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
	@NamedQuery(name="OrgTypeMst.getUniqueOrgType", query = "SELECT e FROM OrgTypeMst e WHERE e.id=:Id"),
	@NamedQuery(name="OrgTypeMst.getOrgTypeName", query = "SELECT e FROM OrgTypeMst e WHERE e.orgTypeName=:orgTypeName"),
	@NamedQuery(name="OrgTypeMst.getorgTypeId", query = "SELECT e FROM OrgTypeMst e WHERE e.orgTypeId=:orgTypeId"),
	@NamedQuery(name = "OrgTypeMst.getByAuthStatus", query = "SELECT e FROM OrgTypeMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="OrgTypeMst.getByDeleted", query = "SELECT e FROM OrgTypeMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="OrgTypeMst.getOrgTypeIdByOrgTypeName", query = "SELECT e FROM OrgTypeMst e WHERE e.orgTypeName=:orgTypeName"),
})
public class OrgTypeMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String orgTypeId = "";
	
	@Column(nullable = false,length = 48)
	private String orgTypeName ="";
	
	@Column(nullable = false,length = 48)
	private String orgTypeDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(String orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getOrgTypeDisplayName() {
		return orgTypeDisplayName;
	}

	public void setOrgTypeDisplayName(String orgTypeDisplayName) {
		this.orgTypeDisplayName = orgTypeDisplayName;
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
