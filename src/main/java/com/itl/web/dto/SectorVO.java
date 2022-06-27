package com.itl.web.dto;

public class SectorVO {

	private String sectorId = "";
	private String sectorName ="";
	private String sectorDisplayName ="";
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

	

}
