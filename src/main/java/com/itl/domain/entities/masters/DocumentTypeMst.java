package com.itl.domain.entities.masters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.envers.Audited;

import com.itl.domain.entities.base.Base;

@Entity
@Audited(auditParents = { Base.class })
	@NamedQueries({
	@NamedQuery(name="DocumentTypeMst.getUniqueDocumentType", query = "SELECT e FROM DocumentTypeMst e WHERE e.id=:Id"),
	@NamedQuery(name="DocumentTypeMst.getByDeleted", query = "SELECT e FROM DocumentTypeMst e WHERE e.isDeleted=:isDeleted"),
	@NamedQuery(name="DocumentTypeMst.getByAuthStatus", query = "SELECT e FROM DocumentTypeMst e WHERE e.authStatus=:authStatus AND e.isDeleted=:isDeleted"),
	@NamedQuery(name="DocumentTypeMst.getdocTypeIdBydocTypeName", query = "SELECT e FROM DocumentTypeMst e WHERE e.docTypeName=:docTypeName"),
	@NamedQuery(name="DocumentTypeMst.getdocTypeName", query = "SELECT e FROM DocumentTypeMst e WHERE e.docTypeName=:docTypeName"),
	@NamedQuery(name="DocumentTypeMst.getdocTypeId", query = "SELECT e FROM DocumentTypeMst e WHERE e.docTypeId=:docTypeId")
})
public class DocumentTypeMst extends Base {

	private static final long serialVersionUID = -1L;

	@Column(nullable = false,length = 8)
	private String docTypeId = "";
	
	@Column(nullable = false,length = 48)
	private String docTypeName ="";
	
	@Column(nullable = false,length = 48)
	private String docTypeDisplayName ="";

	@Column(nullable = true)
	private Integer isActive = 1;

	@Column(nullable = true, length = 8)
	private String authStatus = "";

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
