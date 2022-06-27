package com.itl.web.dto;

import java.io.Serializable;

public class CountryVO implements Serializable {

	private static final long serialVersionUID = 2576073876876557121L;

	private String countryId 			="";
	
	private String countryName 			="";
	
	private String countryDisplayName	="";
	
	private String countryAbbr			="";
	private String isActive 			="";
	private String isDeleted 			="";
	private String authStatus 			="";

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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryDisplayName() {
		return countryDisplayName;
	}

	public void setCountryDisplayName(String countryDisplayName) {
		this.countryDisplayName = countryDisplayName;
	}

	public String getCountryAbbr() {
		return countryAbbr;
	}

	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
	}

	@Override
	public String toString() {
		return "CountryVO [countryId=" + countryId + ", countryName=" + countryName + ", countryDisplayName="
				+ countryDisplayName + ", countryAbbr=" + countryAbbr + ", isActive=" + isActive + ", isDeleted="
				+ isDeleted + ", authStatus=" + authStatus + "]";
	}
	
	
}
