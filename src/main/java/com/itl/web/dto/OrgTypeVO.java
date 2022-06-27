package com.itl.web.dto;

public class OrgTypeVO {

	private String orgTypeId = "";
	
	private String orgTypeName ="";
	
	private String orgTypeDisplayName ="";
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

	public String getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(String orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getOrgTypeDisplayName() {
		return orgTypeDisplayName;
	}

	public void setOrgTypeDisplayName(String orgTypeDisplayName) {
		this.orgTypeDisplayName = orgTypeDisplayName;
	}
	
}
