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
	@NamedQuery(name="StateMst.getUniquestate", query = "SELECT e FROM StateMst e WHERE e.id=:Id"),
	@NamedQuery(name="StateMst.getByDeleted", query = "SELECT e FROM StateMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="StateMst.getstateName", query = "SELECT e FROM StateMst e WHERE e.stateName=:stateName"),
	@NamedQuery(name="StateMst.getstateId", query = "SELECT e FROM StateMst e WHERE e.stateId=:stateId"),
	@NamedQuery(name="StateMst.getByAuthStatus", query = "SELECT e FROM StateMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="StateMst.getstateByCountryId", query = "SELECT e FROM StateMst e WHERE e.countryId=:countryId AND e.stateId=:stateId"),
	@NamedQuery(name="StateMst.getStateIdByStateName", query = "SELECT e FROM StateMst e WHERE e.stateName=:stateName"),
})
public class StateMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String countryId = "";
	
	@Column(nullable = false,length = 8)
	private String stateId = "";
	
	@Column(nullable = false,length = 48)
	private String stateName ="";
	
	@Column(nullable = false,length = 48)
	private String stateDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateDisplayName() {
		return stateDisplayName;
	}

	public void setStateDisplayName(String stateDisplayName) {
		this.stateDisplayName = stateDisplayName;
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
