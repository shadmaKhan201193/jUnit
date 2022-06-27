package com.itl.web.dto;

public class IndustryVO {
	private String industryId = "";
	private String industryName ="";
	private String industryDisplayName ="";
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
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getIndustryDisplayName() {
		return industryDisplayName;
	}
	public void setIndustryDisplayName(String industryDisplayName) {
		this.industryDisplayName = industryDisplayName;
	}

}
