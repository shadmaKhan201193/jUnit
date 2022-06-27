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
	@NamedQuery(name="CityMst.getUniqueCity", query = "SELECT e FROM CityMst e WHERE e.id=:Id"),
	@NamedQuery(name="CityMst.getByAuthStatus", query = "SELECT e FROM CityMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="CityMst.getByDeleted", query = "SELECT e FROM CityMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="CityMst.getCityIdByCityName", query = "SELECT e FROM CityMst e WHERE e.cityName=:cityName"),
	@NamedQuery(name="CityMst.getcityName", query = "SELECT e FROM CityMst e WHERE e.cityName=:cityName"),
	@NamedQuery(name="CityMst.getcityId", query = "SELECT e FROM CityMst e WHERE e.cityId=:cityId"),
	@NamedQuery(name="CityMst.getcityByCountryState", query = "SELECT e FROM CityMst e "
		+" WHERE e.countryId=:countryId AND e.stateId=:stateId AND e.cityId=:cityId")
})
public class CityMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String countryId = "";
	
	@Column(nullable = false,length = 8)
	private String stateId = "";
	
	@Column(nullable = false,length = 8)
	private String cityId = "";
	
	@Column(nullable = false,length = 48)
	private String cityName ="";
	
	@Column(nullable = false,length = 48)
	private String cityDisplayName ="";
	
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityDisplayName() {
		return cityDisplayName;
	}

	public void setCityDisplayName(String cityDisplayName) {
		this.cityDisplayName = cityDisplayName;
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
