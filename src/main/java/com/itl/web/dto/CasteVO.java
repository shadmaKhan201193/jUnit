package com.itl.web.dto;

public class CasteVO {

	private String religionId = "";
	private String casteId = "";
	private String casteName ="";
	private String casteDisplayName ="";
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
	public String getCasteId() {
		return casteId;
	}
	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}
	public String getCasteName() {
		return casteName;
	}
	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	public String getCasteDisplayName() {
		return casteDisplayName;
	}
	public void setCasteDisplayName(String casteDisplayName) {
		this.casteDisplayName = casteDisplayName;
	}
	
}
