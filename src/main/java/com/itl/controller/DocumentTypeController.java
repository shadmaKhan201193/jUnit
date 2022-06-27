package com.itl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itl.domain.entities.masters.DocumentTypeMst;
import com.itl.service.masters.DocumentTypeService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.DocumentTypeVO;
import com.itl.web.dto.DocumentTypeVOList;

@RestController
@RequestMapping("/DocumentType")
public class DocumentTypeController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentTypeController.class);
	@Autowired
	private DocumentTypeService DocumentTypeService;
	
	@PostMapping(value = "/createDocumentType", consumes = "application/json", produces = "application/json")
	 public String createDocumentType(@RequestBody DocumentTypeVO documentType) {
		logger.debug("Add DocumentType for DocumentTypeId : {}" + documentType.getDocTypeId());
		DocumentTypeMst documentTypeMst = DocumentTypeService.getBydocTypeId(documentType.getDocTypeId());
		if (null != documentTypeMst) {
			logger.info("DocumentType with DocumentTypeId {} already exists.", documentType.getDocTypeId());
			return "Failure.. Record already exists";
		} else {
			documentTypeMst = new DocumentTypeMst();
			Date dt = new Date();
			documentTypeMst.setCreatedBy("login");
			documentTypeMst.setCreatedDate(dt);
			documentTypeMst.setCreatedTime(dt);
			documentTypeMst.setLastModifiedBy("login");
			documentTypeMst.setLastModifiedDate(dt);
			documentTypeMst.setLastModifiedTime(dt);
			documentTypeMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			documentTypeMst.setIsActive(1);
			documentTypeMst.setIsDeleted(Boolean.FALSE);
			documentTypeMst.setDocTypeId(documentType.getDocTypeId());
			documentTypeMst.setDocTypeName(documentType.getDocTypeName());
			documentTypeMst.setDocTypeDisplayName(documentType.getDocTypeDisplayName());

			DocumentTypeMst documentTypeMstNew = DocumentTypeService.saveOrUpdate("login", documentTypeMst);
			if (null != documentTypeMstNew) {
				DocumentTypeService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("DocumentType & Cache - {} - saved Successfully", documentType.getDocTypeId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save DocumentType - {}", documentType.getDocTypeId());
				return "Failure while saving record";
			}
		}
	}
	
	
	@PostMapping(value = "/updateDocumentType", consumes = "application/json", produces = "application/json")
    public String updateDocumentType(@RequestBody DocumentTypeVO documentType) {
		logger.debug("Updating DocumentType with DocumentTypeId : {}" + documentType.getDocTypeId());
		DocumentTypeMst documentTypeMst = DocumentTypeService.getBydocTypeId(documentType.getDocTypeId());
		if (null != documentTypeMst) {
			Date dt = new Date();
			documentTypeMst.setLastModifiedBy("login2");
			documentTypeMst.setLastModifiedDate(dt);
			documentTypeMst.setLastModifiedTime(dt);
			documentTypeMst.setAuthStatus(documentType.getAuthStatus());
			documentTypeMst.setIsActive(Integer.parseInt(documentType.getIsActive()));
			documentTypeMst.setIsDeleted(documentType.getIsDeleted().equals("1") ? true : false);
			documentTypeMst.setDocTypeId(documentType.getDocTypeId());
			documentTypeMst.setDocTypeName(documentType.getDocTypeName());
			documentTypeMst.setDocTypeDisplayName(documentType.getDocTypeDisplayName());
			
			DocumentTypeMst documentTypeMstNew = DocumentTypeService.saveOrUpdate("login", documentTypeMst);
			if (null != documentTypeMstNew) {
				DocumentTypeService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("DocumentType & Cache updated successfully for {}", documentType.getDocTypeId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update DocumentType with id : {}", documentType.getDocTypeId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
	}
	

	
	@GetMapping(value = "/id/{id}", produces = "application/json")
	public DocumentTypeVO getDocumentTypeById(@PathVariable String id) {
		logger.info("Fetch DocumentType by DocumentTypeId : {}", id);
		DocumentTypeMst documentTypeMst = DocumentTypeService.getBydocTypeId(id);
		DocumentTypeVO documentType = null;
		if (null != documentTypeMst) {
			documentType = new DocumentTypeVO();
			documentType.setDocTypeId(documentTypeMst.getDocTypeId());
			documentType.setDocTypeName(documentTypeMst.getDocTypeName());
			documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
			documentType.setAuthStatus(documentTypeMst.getAuthStatus());
			documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));
			documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the DocumentType is not found
		return documentType;
	}


	@GetMapping(value = "/name/{name}", produces = "application/json")
	public DocumentTypeVO getDocumentTypeByName(@PathVariable String name) {
		logger.info("Fetch DocumentType by DocumentTypeByName : {}", name);
		String docTypeId = DocumentTypeService.getdocTypeIdBydocTypeName(name);
		DocumentTypeMst documentTypeMst = DocumentTypeService.getBydocTypeId(docTypeId);
		DocumentTypeVO documentType = null;
		if (null != documentTypeMst) {
			documentType = new DocumentTypeVO();
			documentType.setDocTypeId(documentTypeMst.getDocTypeId());
			documentType.setDocTypeName(documentTypeMst.getDocTypeName());
			documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
			documentType.setAuthStatus(documentTypeMst.getAuthStatus());
			documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));
			documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
		}
		//TODO - add else part to reply documentType not found
		return documentType;
	}
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public DocumentTypeVOList getDocumentTypeByAuthorizedStatus() { 
		logger.info("Fetch DocumentType by AuthStatus : A");
		List<DocumentTypeMst> documentTypeMstList = DocumentTypeService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		logger.info("After Service call " +documentTypeMstList);
		DocumentTypeVOList voList = new DocumentTypeVOList();
		List<DocumentTypeVO> documentTypeVOList = new ArrayList<DocumentTypeVO>(); 
		if (null != documentTypeMstList) {
			for (DocumentTypeMst documentTypeMst: documentTypeMstList) {
				DocumentTypeVO documentType = new DocumentTypeVO();
				documentType.setDocTypeId(documentTypeMst.getDocTypeId());
				documentType.setDocTypeName(documentTypeMst.getDocTypeName());
				documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
				documentType.setAuthStatus(documentTypeMst.getAuthStatus());
				documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));					
				documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
				documentTypeVOList.add(documentType);
			}
		}
		voList.setDocumentType(documentTypeVOList);
        return voList; 
    } 
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public DocumentTypeVOList getDocumentTypeByPendingStatus() { 
		logger.info("Fetch DocumentType by AuthStatus : P");
		List<DocumentTypeMst> documentTypeMstList = DocumentTypeService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		DocumentTypeVOList voList = new DocumentTypeVOList();
		List<DocumentTypeVO> documentTypeVOList = new ArrayList<DocumentTypeVO>(); 
		if (null != documentTypeMstList) {
			for (DocumentTypeMst documentTypeMst: documentTypeMstList) {
				DocumentTypeVO documentType = new DocumentTypeVO();
				documentType.setDocTypeId(documentTypeMst.getDocTypeId());
				documentType.setDocTypeName(documentTypeMst.getDocTypeName());
				documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
				documentType.setAuthStatus(documentTypeMst.getAuthStatus());
				documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));					
				documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
				documentTypeVOList.add(documentType);
			}
		}
		voList.setDocumentType(documentTypeVOList);
        return voList; 
    } 
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public DocumentTypeVOList getDocumentTypeByRejectedStatus() { 
		logger.info("Fetch DocumentType by AuthStatus : R");
		List<DocumentTypeMst> documentTypeMstList = DocumentTypeService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		DocumentTypeVOList voList = new DocumentTypeVOList();
		List<DocumentTypeVO> documentTypeVOList = new ArrayList<DocumentTypeVO>(); 
		if (null != documentTypeMstList) {
			for (DocumentTypeMst documentTypeMst: documentTypeMstList) {
				DocumentTypeVO documentType = new DocumentTypeVO();
				documentType.setDocTypeId(documentTypeMst.getDocTypeId());
				documentType.setDocTypeName(documentTypeMst.getDocTypeName());
				documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
				documentType.setAuthStatus(documentTypeMst.getAuthStatus());
				documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));					
				documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
				documentTypeVOList.add(documentType);
			}
		}
		voList.setDocumentType(documentTypeVOList);
        return voList; 
    } 
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public DocumentTypeVOList getDocumentTypeByDeleted() { 
		logger.info("Fetch DocumentType by AuthStatus : D");
		List<DocumentTypeMst> documentTypeMstList = DocumentTypeService.getByDeleted(Boolean.TRUE);   		
		DocumentTypeVOList voList = new DocumentTypeVOList();
		List<DocumentTypeVO> documentTypeVOList = new ArrayList<DocumentTypeVO>();
		if (null != documentTypeMstList) {
			for (DocumentTypeMst documentTypeMst: documentTypeMstList) {		
				DocumentTypeVO documentType = new DocumentTypeVO();
				documentType.setDocTypeId(documentTypeMst.getDocTypeId());
				documentType.setDocTypeName(documentTypeMst.getDocTypeName());
				documentType.setDocTypeDisplayName(documentTypeMst.getDocTypeDisplayName());
				documentType.setAuthStatus(documentTypeMst.getAuthStatus());
				documentType.setIsActive(Integer.toString(documentTypeMst.getIsActive()));					
				documentType.setIsDeleted(String.valueOf(documentTypeMst.getIsDeleted()));
				documentTypeVOList.add(documentType);
			}
		}  
		voList.setDocumentType(documentTypeVOList);		
        return voList;
    }
	
	
	
	@PostMapping(value = "/deleteDocumentType/{id}", consumes = "application/json", produces = "application/json")
    public String deleteDocumentType(@PathVariable(name="id") String docTypeId) {
		logger.debug("Delete DocumentType by DocumentTypeId : {}", docTypeId);
		DocumentTypeMst documentTypeMst = DocumentTypeService.getBydocTypeId(docTypeId);

		
		if (null != documentTypeMst ) {
			logger.debug("Inside in condition");		
			Date dt = new Date();
			documentTypeMst.setDeletedBy("login3");
			documentTypeMst.setDeletedDate(dt);
			documentTypeMst.setDeletedTime(dt);
			documentTypeMst.setAuthStatus("D");
			documentTypeMst.setIsActive(0);
			documentTypeMst.setIsDeleted(Boolean.TRUE);
			
			DocumentTypeMst documentTypeMstNew = DocumentTypeService.saveOrUpdate("login", documentTypeMst);
			if (null != documentTypeMstNew) {
				logger.info("record deleted Successfully....service value= " +documentTypeMstNew);
				return "Successfully deleted record";
			} else {
				logger.debug("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from method");
			return "Failure.. Record does not exists";
		}
    }

}
