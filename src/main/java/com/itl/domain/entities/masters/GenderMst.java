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
	@NamedQuery(name="GenderMst.getUniqueGender", query = "SELECT e FROM GenderMst e WHERE e.id=:Id"),
	@NamedQuery(name="GenderMst.getByDeleted", query = "SELECT e FROM GenderMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="GenderMst.getByAuthStatus", query = "SELECT e FROM GenderMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="GenderMst.getmaritalStatusName", query = "SELECT e FROM GenderMst e WHERE e.genderName=:genderName"),
	@NamedQuery(name="GenderMst.getGenderIdByGenderName", query = "SELECT e FROM GenderMst e WHERE e.genderName=:genderName"),
	@NamedQuery(name="GenderMst.getgenderId", query = "SELECT e FROM GenderMst e WHERE e.genderId=:genderId")
})
public class GenderMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String genderId = "";
	
	@Column(nullable = false,length = 48)
	private String genderName ="";
	
	@Column(nullable = false,length = 48)
	private String genderDisplayName ="";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getGenderDisplayName() {
		return genderDisplayName;
	}

	public void setGenderDisplayName(String genderDisplayName) {
		this.genderDisplayName = genderDisplayName;
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
