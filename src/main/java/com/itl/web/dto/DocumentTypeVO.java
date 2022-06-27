package com.itl.web.dto;

public class DocumentTypeVO {
	private String docTypeId = "";
	private String docTypeName ="";
	private String docTypeDisplayName ="";
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
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getDocTypeName() {
		return docTypeName;
	}
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	public String getDocTypeDisplayName() {
		return docTypeDisplayName;
	}
	public void setDocTypeDisplayName(String docTypeDisplayName) {
		this.docTypeDisplayName = docTypeDisplayName;
	}
	
}
