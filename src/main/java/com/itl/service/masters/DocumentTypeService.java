package com.itl.service.masters;

import java.util.List;


import com.itl.domain.entities.masters.DocumentTypeMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface DocumentTypeService extends NGService {

	public DocumentTypeMst getPrimaryKey(Long Id) throws NGException ;
	
	//public DocumentTypeMst getByDocumentTypeId(String documentTypeId) throws NGException;
	
	public DocumentTypeMst saveOrUpdate(String loginId, DocumentTypeMst entity) throws NGException;

	public List<DocumentTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<DocumentTypeMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<DocumentTypeMst> updateCacheList(String authStatus) throws NGException;
	
	public String getdocTypeIdBydocTypeName(String docTypeName)throws NGException;

	public DocumentTypeMst getBydocTypeId(String docTypeId) throws NGException;


	
	
}
