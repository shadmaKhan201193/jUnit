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
	@NamedQuery(name="PrefCategoryMst.getUniquePrefCategory", query = "SELECT e FROM PrefCategoryMst e WHERE e.id=:Id"),
	@NamedQuery(name="PrefCategoryMst.getprefCategoryName", query = "SELECT e FROM PrefCategoryMst e WHERE e.prefCategoryName=:prefCategoryName"),
	@NamedQuery(name="PrefCategoryMst.getprefCategoryId", query = "SELECT e FROM PrefCategoryMst e WHERE e.prefCategoryId=:prefCategoryId"),
	@NamedQuery(name ="PrefCategoryMst.getByAuthStatus", query = "SELECT e FROM PrefCategoryMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="PrefCategoryMst.getByDeleted", query = "SELECT e FROM PrefCategoryMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="PrefCategoryMst.getPrefCategoryIdByPrefCategoryName", query = "SELECT e FROM PrefCategoryMst e WHERE e.prefCategoryName=:prefCategoryName")
})
public class PrefCategoryMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String prefCategoryId = "";
	
	@Column(nullable = false,length = 48)
	private String prefCategoryName ="";
	
	@Column(nullable = false,length = 48)
	private String prefCategoryDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getPrefCategoryId() {
		return prefCategoryId;
	}

	public void setPrefCategoryId(String prefCategoryId) {
		this.prefCategoryId = prefCategoryId;
	}

	public String getPrefCategoryName() {
		return prefCategoryName;
	}

	public void setPrefCategoryName(String prefCategoryName) {
		this.prefCategoryName = prefCategoryName;
	}

	public String getPrefCategoryDisplayName() {
		return prefCategoryDisplayName;
	}

	public void setPrefCategoryDisplayName(String prefCategoryDisplayName) {
		this.prefCategoryDisplayName = prefCategoryDisplayName;
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
