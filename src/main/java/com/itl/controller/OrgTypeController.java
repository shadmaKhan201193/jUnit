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

import com.itl.domain.entities.masters.OrgTypeMst;
import com.itl.service.masters.OrgTypeService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.OrgTypeVO;
import com.itl.web.dto.OrgTypeVOList;

@RestController
@RequestMapping("/OrgType")
public class OrgTypeController {


private static final Logger logger = LoggerFactory.getLogger(OrgTypeController.class);
	
	@Autowired
	private OrgTypeService OrgTypeService;
	
	@PostMapping(value = "/createOrgType", consumes = "application/json", produces = "application/json")
    public String createOrgType(@RequestBody OrgTypeVO orgType) {
		logger.debug("Add orgType for orgTypeId : {}" + orgType.getOrgTypeId());
		OrgTypeMst orgTypeMst = OrgTypeService.getByOrgTypeId(orgType.getOrgTypeId());
		
		if (null != orgTypeMst) {
			logger.info("orgType with orgTypeId {} already exists.", orgType.getOrgTypeId());
			return "Failure.. Record already exists";
		} else {
			orgTypeMst = new OrgTypeMst();
			Date dt = new Date();
			orgTypeMst.setCreatedBy("login");
			orgTypeMst.setCreatedDate(dt);
			orgTypeMst.setCreatedTime(dt);
			orgTypeMst.setLastModifiedBy("login");
			orgTypeMst.setLastModifiedDate(dt);
			orgTypeMst.setLastModifiedTime(dt);
			orgTypeMst.setAuthStatus("A");
			orgTypeMst.setIsActive(1);
			orgTypeMst.setIsDeleted(Boolean.FALSE);
			
			orgTypeMst.setOrgTypeId(orgType.getOrgTypeId());
			orgTypeMst.setOrgTypeName(orgType.getOrgTypeName());
			orgTypeMst.setOrgTypeDisplayName(orgType.getOrgTypeDisplayName());
			
			
			OrgTypeMst orgTypeMstNew = OrgTypeService.saveOrUpdate("login", orgTypeMst);
			if (null != orgTypeMstNew) {
				OrgTypeService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("orgType & Cache - {} - saved Successfully", orgType.getOrgTypeId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save orgType - {}", orgType.getOrgTypeId());
				return "Failure while saving record";
			}
		}
    }
	
	
	
	@PostMapping(value = "/updateOrgType", consumes = "application/json", produces = "application/json")
    public String updateOrgType(@RequestBody OrgTypeVO orgType) {
		logger.debug("Updating orgType with orgTypeId : {}" + orgType.getOrgTypeId());
		OrgTypeMst orgTypeMst = OrgTypeService.getByOrgTypeId(orgType.getOrgTypeId());
		
		if (null != orgTypeMst) {		
			Date dt = new Date();
			orgTypeMst.setLastModifiedBy("login2");
			orgTypeMst.setLastModifiedDate(dt); 
			orgTypeMst.setLastModifiedTime(dt);
			orgTypeMst.setAuthStatus(orgType.getAuthStatus());
			orgTypeMst.setIsActive(Integer.parseInt(orgType.getIsActive()));
			orgTypeMst.setIsDeleted(orgType.getIsDeleted().equals("1")?true:false);
			orgTypeMst.setOrgTypeId(orgType.getOrgTypeId());
			orgTypeMst.setOrgTypeName(orgType.getOrgTypeName());
			orgTypeMst.setOrgTypeDisplayName(orgType.getOrgTypeDisplayName());
			logger.info("Before saveOrUpdate call" );
			OrgTypeMst orgTypeMstNew = OrgTypeService.saveOrUpdate("login", orgTypeMst);
			
			if (null != orgTypeMstNew) {
				OrgTypeService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("orgType & Cache updated successfully for {}", orgType.getOrgTypeId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update orgType with id : {}", orgType.getOrgTypeId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
    }
	
	

	@GetMapping(value = "/id/{id}", produces = "application/json")
    public OrgTypeVO getOrgTypeById(@PathVariable String id) {
		logger.info("Fetch OrgType by OrgType : {}", id);
		OrgTypeMst orgTypeMst = OrgTypeService.getByOrgTypeId(id);
		OrgTypeVO orgType = null;
		if (null != orgTypeMst) {
			orgType = new OrgTypeVO();
			orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
			orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
			orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
			orgType.setAuthStatus(orgTypeMst.getAuthStatus());
			orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));				
			orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
		}
        return orgType;
    }
	
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public OrgTypeVO getOrgTypeByName(@PathVariable String name) {
		logger.info("Fetch orgType by orgTypeName : {}", name);
	   String orgTypeId = OrgTypeService.getOrgTypeIdByOrgTypeName(name);
	   OrgTypeMst orgTypeMst = OrgTypeService.getByOrgTypeId(orgTypeId);	
	   OrgTypeVO orgType = null;
		if (null != orgTypeMst) {
			orgType = new OrgTypeVO();
			orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
			orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
			orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
			orgType.setAuthStatus(orgTypeMst.getAuthStatus());
			orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));				
			orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
		}
        return orgType;
    }

	
	

	@GetMapping(value = "/list/authorized", produces = "application/json")
    public OrgTypeVOList getOrgTypeByAuthorizedStatus() { 
		logger.info("Fetch orgType by AuthStatus : A");
		List<OrgTypeMst> orgTypeList = OrgTypeService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		OrgTypeVOList voList = new OrgTypeVOList();
		List<OrgTypeVO> orgTypeVOList = new ArrayList<OrgTypeVO>();  
		
		if (null != orgTypeList) {
			for (OrgTypeMst orgTypeMst: orgTypeList) {
				OrgTypeVO orgType = new OrgTypeVO();
				orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
				orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
				orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
				orgType.setAuthStatus(orgTypeMst.getAuthStatus());
				orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));					
				orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
				
				orgTypeVOList.add(orgType);
			}
		}
		voList.setOrgType(orgTypeVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public OrgTypeVOList getOrgTypeByPendingStatus() { 
		logger.info("Fetch orgType by AuthStatus : P");
		List<OrgTypeMst> orgTypeList = OrgTypeService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		OrgTypeVOList voList = new OrgTypeVOList();
		List<OrgTypeVO> orgTypeVOList = new ArrayList<OrgTypeVO>();  
		if (null != orgTypeList) {
			for (OrgTypeMst orgTypeMst: orgTypeList) {
				OrgTypeVO orgType = new OrgTypeVO();
				orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
				orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
				orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
				orgType.setAuthStatus(orgTypeMst.getAuthStatus());
				orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));					
				orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
				
				orgTypeVOList.add(orgType);
			}
		}
		voList.setOrgType(orgTypeVOList);
        return voList;  
    }

	
	

	@GetMapping(value = "/list/rejected", produces = "application/json")
    public OrgTypeVOList getOrgTypeByRejectedStatus() { 
		logger.info("Fetch orgType by AuthStatus : R");
		List<OrgTypeMst> orgTypeList = OrgTypeService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		OrgTypeVOList voList = new OrgTypeVOList();
		List<OrgTypeVO> orgTypeVOList = new ArrayList<OrgTypeVO>();  		
		if (null != orgTypeList) {
			for (OrgTypeMst orgTypeMst: orgTypeList) {
				OrgTypeVO orgType = new OrgTypeVO();
				orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
				orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
				orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
				orgType.setAuthStatus(orgTypeMst.getAuthStatus());
				orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));					
				orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
				
				orgTypeVOList.add(orgType);
			}
		}
		voList.setOrgType(orgTypeVOList);
        return voList;  
    }

	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public OrgTypeVOList getOrgTypeByDeleted() { 
		logger.info("Fetch orgType by AuthStatus : D");
		List<OrgTypeMst> orgTypeList = OrgTypeService.getByDeleted(Boolean.TRUE);
		OrgTypeVOList voList = new OrgTypeVOList();
		List<OrgTypeVO> orgTypeVOList = new ArrayList<OrgTypeVO>();  		
		if (null != orgTypeList) {
			for (OrgTypeMst orgTypeMst: orgTypeList) {
				OrgTypeVO orgType = new OrgTypeVO();
				orgType.setOrgTypeId(orgTypeMst.getOrgTypeId());
				orgType.setOrgTypeName(orgTypeMst.getOrgTypeName());
				orgType.setOrgTypeDisplayName(orgTypeMst.getOrgTypeDisplayName());
				orgType.setAuthStatus(orgTypeMst.getAuthStatus());
				orgType.setIsActive(Integer.toString(orgTypeMst.getIsActive()));					
				orgType.setIsDeleted(String.valueOf(orgTypeMst.getIsDeleted()));
				
				orgTypeVOList.add(orgType);
			}
		}
		voList.setOrgType(orgTypeVOList);
        return voList;  
    }
	
	
	

	@PostMapping(value = "/deleteOrgType/{id}", consumes = "application/json", produces = "application/json")
    public String deleteOrgType(@PathVariable(name="id") String orgTypeId) {
		logger.debug("Delete orgType by orgTypeId : {}", orgTypeId);
		OrgTypeMst orgTypeMst = OrgTypeService.getByOrgTypeId(orgTypeId);		
		if (null != orgTypeMst) {				
			Date dt = new Date();
			orgTypeMst.setDeletedBy("login3");
			orgTypeMst.setDeletedDate(dt);
			orgTypeMst.setDeletedTime(dt);
			orgTypeMst.setAuthStatus("D");
			orgTypeMst.setIsActive(0);
			orgTypeMst.setIsDeleted(Boolean.TRUE);
			
			OrgTypeMst orgTypeMstNew = OrgTypeService.saveOrUpdate("login", orgTypeMst);
			if (null != orgTypeMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteOrgType  method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	
}
