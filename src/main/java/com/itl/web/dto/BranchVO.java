package com.itl.web.dto;

public class BranchVO {
	
	private String branchId = "";
	private String branchName = "";
	private String branchDisplayName = "";
	private String baseCurrency ="";
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
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchDisplayName() {
		return branchDisplayName;
	}
	
	public void setBranchDisplayName(String branchDisplayName) {
		this.branchDisplayName = branchDisplayName;
	}
	
	public String getBaseCurrency() {
		return baseCurrency;
	}
	
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	

}
