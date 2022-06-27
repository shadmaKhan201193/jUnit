package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.OrgTypeMst;
import com.itl.exceptions.NGException;

public interface OrgTypeDAO extends JPADAO<OrgTypeMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public OrgTypeMst getPrimaryKey(Long Id) throws NGException;
	
	public List<OrgTypeMst> getByOrgTypeName(String orgTypeName) throws NGException;
	
	public List<OrgTypeMst> getByOrgTypeId(String orgTypeId) throws NGException;

	public List<OrgTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<OrgTypeMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getOrgTypeIdByOrgTypeName(String orgTypeName) throws NGException;
	
}
