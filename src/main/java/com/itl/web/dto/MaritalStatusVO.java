package com.itl.web.dto;

public class MaritalStatusVO {
	private String maritalStatusId = "";
	private String maritalStatusName ="";
	private String maritalStatusDisplayName ="";
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
	public String getMaritalStatusId() {
		return maritalStatusId;
	}
	public void setMaritalStatusId(String maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}
	public String getMaritalStatusName() {
		return maritalStatusName;
	}
	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}
	public String getMaritalStatusDisplayName() {
		return maritalStatusDisplayName;
	}
	public void setMaritalStatusDisplayName(String maritalStatusDisplayName) {
		this.maritalStatusDisplayName = maritalStatusDisplayName;
	}
	

}
