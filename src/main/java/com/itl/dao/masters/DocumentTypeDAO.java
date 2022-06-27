package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.DocumentTypeMst;
import com.itl.exceptions.NGException;

public interface DocumentTypeDAO extends JPADAO<DocumentTypeMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public DocumentTypeMst getPrimaryKey(Long Id) throws NGException;
	
	public List<DocumentTypeMst> getByDocTypeName(String docTypeName) throws NGException;
	
	public List<DocumentTypeMst> getBydocTypeId(String docTypeId) throws NGException;
	
	public List<DocumentTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<DocumentTypeMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getdocTypeIdBydocTypeName(String docTypeName) throws NGException;

	


	
}
