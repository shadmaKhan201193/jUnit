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
	@NamedQuery(name="CasteMst.getUniqueCaste", query = "SELECT e FROM CasteMst e WHERE e.id=:Id"),
	@NamedQuery(name="CasteMst.getcasteName", query = "SELECT e FROM CasteMst e WHERE e.casteName=:casteName"),
	@NamedQuery(name="CasteMst.getcasteId", query = "SELECT e FROM CasteMst e WHERE e.casteId=:casteId"),
	@NamedQuery(name="CasteMst.getCasteIdByCasteName", query = "SELECT e FROM CasteMst e WHERE e.casteName=:casteName "),
	@NamedQuery(name="CasteMst.getByReligioncasteId", query = "SELECT e FROM CasteMst e WHERE e.religionId=:religionId AND e.casteId=:casteId"),
	@NamedQuery(name = "CasteMst.getByAuthStatus", query = "SELECT e FROM CasteMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="CasteMst.getByDeleted", query = "SELECT e FROM CasteMst e WHERE e.isDeleted=:isDeleted")
})
public class CasteMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String religionId = "";
	
	@Column(nullable = false,length = 8)
	private String casteId = "";
	
	@Column(nullable = false,length = 48)
	private String casteName ="";
	
	@Column(nullable = false,length = 48)
	private String casteDisplayName ="";
	
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

	public String getCasteId() {
		return casteId;
	}

	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}

	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}

	public String getCasteDisplayName() {
		return casteDisplayName;
	}

	public void setCasteDisplayName(String casteDisplayName) {
		this.casteDisplayName = casteDisplayName;
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
