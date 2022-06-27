package com.itl.web.dto;

public class OccupationVO {
	private String occupationId = "";
	private String occupationName ="";
	private String occupationDisplayName ="";
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
	public String getOccupationId() {
		return occupationId;
	}
	public void setOccupationId(String occupationId) {
		this.occupationId = occupationId;
	}
	public String getOccupationName() {
		return occupationName;
	}
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	public String getOccupationDisplayName() {
		return occupationDisplayName;
	}
	public void setOccupationDisplayName(String occupationDisplayName) {
		this.occupationDisplayName = occupationDisplayName;
	}

}
