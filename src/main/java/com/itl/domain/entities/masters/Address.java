package com.itl.domain.entities.masters;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(length = 50, nullable = true, updatable = true)
	private String address1 = "";

	@Column(length = 50, nullable = true, updatable = true)
	private String address2 = "";

	@Column(length = 50, nullable = true, updatable = true)
	private String address3 = "";

	@Column(length = 50, nullable = true, updatable = true)
	private String landmark = "";
	
	@Column(length = 15, nullable = true)
	private String zipCode = "";

	@Column(nullable = true, length = 8)
	private String addressType="";
	
	@Column(nullable = true, length = 8)
	private String country="";
	
	@Column(nullable = true, length = 8)
	private String state="";
	
	@Column(nullable = true, length = 8)
	private String city="";

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}