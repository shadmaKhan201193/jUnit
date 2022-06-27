package com.itl.web.dto;

public class ReligionVO {

	private String religionId = "";
	private String religionName ="";
	private String religionDisplayName ="";
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
	public String getReligionId() {
		return religionId;
	}
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	public String getReligionName() {
		return religionName;
	}
	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}
	public String getReligionDisplayName() {
		return religionDisplayName;
	}
	public void setReligionDisplayName(String religionDisplayName) {
		this.religionDisplayName = religionDisplayName;
	}


}
