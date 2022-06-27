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
	@NamedQuery(name="SectorMst.getUniqueSector", query = "SELECT e FROM SectorMst e WHERE e.id=:Id"),
	@NamedQuery(name="SectorMst.getSectorName", query = "SELECT e FROM SectorMst e WHERE e.sectorName=:sectorName"),
	@NamedQuery(name="SectorMst.getSectorId", query = "SELECT e FROM SectorMst e WHERE e.sectorId=:sectorId"),
	@NamedQuery(name = "SectorMst.getByAuthStatus", query = "SELECT e FROM SectorMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="SectorMst.getByDeleted", query = "SELECT e FROM SectorMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="SectorMst.getSectorIdBySectorName", query = "SELECT e FROM SectorMst e WHERE e.sectorName=:sectorName"),
})
public class SectorMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String sectorId = "";
	
	@Column(nullable = false,length = 48)
	private String sectorName ="";
	
	@Column(nullable = false,length = 48)
	private String sectorDisplayName ="";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSectorDisplayName() {
		return sectorDisplayName;
	}

	public void setSectorDisplayName(String sectorDisplayName) {
		this.sectorDisplayName = sectorDisplayName;
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
