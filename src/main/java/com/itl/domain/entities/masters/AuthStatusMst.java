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
	@NamedQuery(name="AuthStatusMst.getUniqueAuthStatus", query = "SELECT e FROM AuthStatusMst e WHERE e.id=:Id"),
	@NamedQuery(name="AuthStatusMst.getauthStatusName", query = "SELECT e FROM AuthStatusMst e WHERE e.authStatusName=:authStatusName"),
	@NamedQuery(name="AuthStatusMst.getauthStatusId", query = "SELECT e FROM AuthStatusMst e WHERE e.authStatusId=:authStatusId")
})
public class AuthStatusMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String authStatusId = "";
	
	@Column(nullable = false,length = 48)
	private String authStatusName ="";
	
	@Column(nullable = false,length = 48)
	private String authStatusDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true,length = 8)
	private String authStatus = "";

	public String getAuthStatusId() {
		return authStatusId;
	}

	public void setAuthStatusId(String authStatusId) {
		this.authStatusId = authStatusId;
	}

	public String getAuthStatusName() {
		return authStatusName;
	}

	public void setAuthStatusName(String authStatusName) {
		this.authStatusName = authStatusName;
	}

	public String getAuthStatusDisplayName() {
		return authStatusDisplayName;
	}

	public void setAuthStatusDisplayName(String authStatusDisplayName) {
		this.authStatusDisplayName = authStatusDisplayName;
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
