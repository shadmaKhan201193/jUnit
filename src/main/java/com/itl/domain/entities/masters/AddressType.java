package com.itl.domain.entities.masters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.envers.Audited;

import com.itl.domain.entities.base.Base;

@Entity
@Audited(auditParents = { Base.class })
@NamedQueries({ @NamedQuery(name = "AddressType.getUniqueAddressType", query = "SELECT e FROM AddressType e WHERE e.id=:Id")
})
public class AddressType extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 8)
	private String addressTypeId = "";

	@Column(nullable = false, length = 48)
	private String addressTypeName = "";

	@Column(nullable = false, length = 48)
	private String addressTypeDisplayName = "";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus="";

	public String getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(String addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getAddressTypeName() {
		return addressTypeName;
	}

	public void setAddressTypeName(String addressTypeName) {
		this.addressTypeName = addressTypeName;
	}

	public String getAddressTypeDisplayName() {
		return addressTypeDisplayName;
	}

	public void setAddressTypeDisplayName(String addressTypeDisplayName) {
		this.addressTypeDisplayName = addressTypeDisplayName;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
}