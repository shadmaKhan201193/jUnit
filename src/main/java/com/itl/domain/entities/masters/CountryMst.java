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
	@NamedQuery(name="CountryMst.getUniqueCountry", query = "SELECT e FROM CountryMst e WHERE e.id=:Id"),
	@NamedQuery(name="CountryMst.getCountryName", query = "SELECT e FROM CountryMst e WHERE e.countryName=:countryName"),
	@NamedQuery(name="CountryMst.getCountryId", query = "SELECT e FROM CountryMst e WHERE e.countryId=:countryId"),
	@NamedQuery(name="CountryMst.getCountryIdByCountryName", query = "SELECT e FROM CountryMst e WHERE e.countryName=:countryName"),
	@NamedQuery(name="CountryMst.getByAuthStatus", query = "SELECT e FROM CountryMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="CountryMst.getByDeleted", query = "SELECT e FROM CountryMst e WHERE e.isDeleted=:isDeleted")
	
})
public class CountryMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String countryId = "";
	
	@Column(nullable = false,length = 48)
	private String countryName ="";
	
	@Column(nullable = false,length = 48)
	private String countryDisplayName ="";
	
	@Column(nullable = false,length = 8)
	private String countryAbbr="";
	
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryDisplayName() {
		return countryDisplayName;
	}

	public void setCountryDisplayName(String countryDisplayName) {
		this.countryDisplayName = countryDisplayName;
	}

	public String getCountryAbbr() {
		return countryAbbr;
	}

	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
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

	@Override
	public String toString() {
		return "CountryMst [countryId=" + countryId + ", countryName=" + countryName + ", countryDisplayName="
				+ countryDisplayName + ", countryAbbr=" + countryAbbr + ", isActive=" + isActive + ", authStatus="
				+ authStatus + "]";
	}
	
	
}
