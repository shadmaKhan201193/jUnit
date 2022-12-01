package com.itl.web.dto;

public class CityVO extends BaseVO {
	private String countryId = "";
	private String stateId = "";
	private String cityId = "";
	private String cityName ="";
	private String cityDisplayName ="";
	private String isActive ="";
	private String isDeleted ="";
	private String authStatus ="";
	
	
	
	
	public CityVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CityVO(String countryId, String stateId, String cityId, String cityName, String cityDisplayName,
			String isActive, String isDeleted, String authStatus) {
		super();
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.cityName = cityName;
		this.cityDisplayName = cityDisplayName;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.authStatus = authStatus;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
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
	
	
	
	

}
