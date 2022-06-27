package com.itl.web.dto;

public class StateVO {
	private String stateId = "";
	private String stateName ="";
	private String countryId = "";
	private String stateDisplayName ="";
	private String isActive ="";
	private String isDeleted ="";
	private String authStatus ="";
	
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
	
	
}
