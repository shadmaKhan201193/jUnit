package com.itl.web.dto;

public class QualificationVO {

	private String qualificationId = "";
	private String qualificationName ="";
	private String qualificationDisplayName ="";
	
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
	
	public String getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getQualificationName() {
		return qualificationName;
	}
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	public String getQualificationDisplayName() {
		return qualificationDisplayName;
	}
	public void setQualificationDisplayName(String qualificationDisplayName) {
		this.qualificationDisplayName = qualificationDisplayName;
	}

	
	
}
