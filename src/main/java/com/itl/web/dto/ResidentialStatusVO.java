package com.itl.web.dto;

public class ResidentialStatusVO {
   
	private String residentialStatusId = "";
	private String residentialStatusName ="";
	private String residentialStatusDisplayName ="";
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

	public String getResidentialStatusId() {
		return residentialStatusId;
	}
	
	public void setResidentialStatusId(String residentialStatusId) {
		this.residentialStatusId = residentialStatusId;
	}
	
	public String getResidentialStatusName() {
		return residentialStatusName;
	}
	
	public void setResidentialStatusName(String residentialStatusName) {
		this.residentialStatusName = residentialStatusName;
	}
	
	public String getResidentialStatusDisplayName() {
		return residentialStatusDisplayName;
	}
	
	
	public void setResidentialStatusDisplayName(String residentialStatusDisplayName) {
		this.residentialStatusDisplayName = residentialStatusDisplayName;
	}

}
