package com.itl.domain.entities.masters;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itl.domain.entities.base.Base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CityMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String countryId = "";

	@Column(nullable = false, length = 8)
	private String stateId = "";

	@Column(nullable = false, length = 8)
	private String cityId = "";

	@Column(nullable = false, length = 48)
	private String cityName = "";

	@Column(nullable = false, length = 48)
	private String cityDisplayName = "";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

	@Column(nullable = true, length = 8)
	private Boolean isDeleted = true;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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

//	@Builder()
//	public CityMst(String countryId, String stateId, String cityId, String cityName, String cityDisplayName,
//			Integer isActive, String authStatus, Boolean isDeleted) {
//		super();
//		this.countryId = countryId;
//		this.stateId = stateId;
//		this.cityId = cityId;
//		this.cityName = cityName;
//		this.cityDisplayName = cityDisplayName;
//		this.isActive = isActive;
//		this.authStatus = authStatus;
//		this.isDeleted = isDeleted;
//	}
//
//	public CityMst() {
//		super();
//		// TODO Auto-generated constructor stub
//	}

}
