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
	@NamedQuery(name="QualificationMst.getUniqueQualification", query = "SELECT e FROM QualificationMst e WHERE e.id=:Id"),
	@NamedQuery(name="QualificationMst.getqualificationName", query = "SELECT e FROM QualificationMst e WHERE e.qualificationName=:qualificationName"),
	@NamedQuery(name="QualificationMst.getqualificationId", query = "SELECT e FROM QualificationMst e WHERE e.qualificationId=:qualificationId"),
	@NamedQuery(name = "QualificationMst.getByAuthStatus", query = "SELECT e FROM QualificationMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="QualificationMst.getByDeleted", query = "SELECT e FROM QualificationMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="QualificationMst.getQualificationIdByQualificationName", query = "SELECT e FROM QualificationMst e WHERE e.qualificationName=:qualificationName"),
	
})
public class QualificationMst extends Base {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String qualificationId = "";
	
	@Column(nullable = false,length = 48)
	private String qualificationName ="";
	
	@Column(nullable = false,length = 48)
	private String qualificationDisplayName ="";
	
	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";
	
	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getQualificationName() {
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}

	public String getQualificationDisplayName() {
		return qualificationDisplayName;
	}

	public void setQualificationDisplayName(String qualificationDisplayName) {
		this.qualificationDisplayName = qualificationDisplayName;
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
