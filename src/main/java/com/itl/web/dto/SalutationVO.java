package com.itl.web.dto;

public class SalutationVO {
	private String salutationId = "";
	private String salutationName ="";
	private String salutationDisplayName ="";
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

	public String getSalutationId() {
		return salutationId;
	}
	
	public void setSalutationId(String salutationId) {
		this.salutationId = salutationId;
	}
	
	public String getSalutationName() {
		return salutationName;
	}
	
	public void setSalutationName(String salutationName) {
		this.salutationName = salutationName;
	}
	
	public String getSalutationDisplayName() {
		return salutationDisplayName;
	}
	
	public void setSalutationDisplayName(String salutationDisplayName) {
		this.salutationDisplayName = salutationDisplayName;
	}
	
	

}
