package com.itl.web.dto;

public class PrefCategoryVO {
	private String prefCategoryId = "";
	private String prefCategoryName ="";
	private String prefCategoryDisplayName ="";
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
	public String getPrefCategoryId() {
		return prefCategoryId;
	}
	public void setPrefCategoryId(String prefCategoryId) {
		this.prefCategoryId = prefCategoryId;
	}
	public String getPrefCategoryName() {
		return prefCategoryName;
	}
	public void setPrefCategoryName(String prefCategoryName) {
		this.prefCategoryName = prefCategoryName;
	}
	public String getPrefCategoryDisplayName() {
		return prefCategoryDisplayName;
	}
	public void setPrefCategoryDisplayName(String prefCategoryDisplayName) {
		this.prefCategoryDisplayName = prefCategoryDisplayName;
	}

}
