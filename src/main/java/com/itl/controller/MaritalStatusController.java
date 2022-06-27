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

import com.itl.domain.entities.masters.MaritalStatusMst;
import com.itl.service.masters.MaritalStatusService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.MaritalStatusVO;
import com.itl.web.dto.MaritalStatusVOList;


@RestController
@RequestMapping("/MaritalStatus")
public class MaritalStatusController {
	private static final Logger logger = LoggerFactory.getLogger(StateController.class);
	
	@Autowired
	private MaritalStatusService MaritalStatusService;
	
	@PostMapping(value = "/createMaritalStatus", consumes = "application/json", produces = "application/json")
    public String createMaritalStatus(@RequestBody MaritalStatusVO maritalStatus) {
		logger.debug("Add maritalStatus for maritalStatusId : {}" +maritalStatus.getMaritalStatusId());
		MaritalStatusMst maritalStatusMst = MaritalStatusService.getByMaritalStatusId(maritalStatus.getMaritalStatusId());
		if (null != maritalStatusMst) {
			logger.info("city with citycityId {} already exists.", maritalStatus.getMaritalStatusId());
			return "Failure.. Record already exists";
		} 
		else {
		
		    maritalStatusMst = new MaritalStatusMst();
			Date dt = new Date();
			maritalStatusMst.setCreatedBy("login");
			maritalStatusMst.setCreatedDate(dt);
			maritalStatusMst.setCreatedTime(dt);
			maritalStatusMst.setLastModifiedBy("login");
			maritalStatusMst.setLastModifiedDate(dt);
			maritalStatusMst.setLastModifiedTime(dt);
			maritalStatusMst.setAuthStatus("A");
			maritalStatusMst.setIsActive(1);
			maritalStatusMst.setIsDeleted(Boolean.FALSE);
			maritalStatusMst.setMaritalStatusId(maritalStatus.getMaritalStatusId());
			maritalStatusMst.setMaritalStatusName(maritalStatus.getMaritalStatusName());
			maritalStatusMst.setMaritalStatusDisplayName(maritalStatus.getMaritalStatusDisplayName());
		
			MaritalStatusMst maritalStatusMstNew = MaritalStatusService.saveOrUpdate("login", maritalStatusMst);
			
			if (null != maritalStatusMstNew) {
				MaritalStatusService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("getMaritalStatus & Cache - {} - saved Successfully", maritalStatus.getMaritalStatusId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save maritalStatus - {}",maritalStatus.getMaritalStatusId());
				return "Failure while saving record";
			}
		}
    }
	
	
	@PostMapping(value = "/updateMaritalStatus", consumes = "application/json", produces = "application/json")
    public String updatemaritalStatus(@RequestBody MaritalStatusVO maritalstatus) {
		logger.debug("Updating getMaritalStatus with getMaritalStatusId : {}" + maritalstatus.getMaritalStatusId());
		MaritalStatusMst maritalStatusMst = MaritalStatusService.getByMaritalStatusId(maritalstatus.getMaritalStatusId());
		if (null != maritalStatusMst) {
			Date dt = new Date();
			maritalStatusMst.setLastModifiedBy("login2");
			maritalStatusMst.setLastModifiedDate(dt);
			maritalStatusMst.setLastModifiedTime(dt);
			maritalStatusMst.setAuthStatus(maritalstatus.getAuthStatus());
			maritalStatusMst.setIsActive(Integer.parseInt(maritalstatus.getIsActive()));
			maritalStatusMst.setIsDeleted(maritalstatus.getIsDeleted().equals("1")?true:false);	
			maritalStatusMst.setMaritalStatusId(maritalstatus.getMaritalStatusId());
			maritalStatusMst.setMaritalStatusName(maritalstatus.getMaritalStatusName());
			maritalStatusMst.setMaritalStatusDisplayName(maritalstatus.getMaritalStatusDisplayName());
			
			MaritalStatusMst maritalstatusMstNew = MaritalStatusService.saveOrUpdate("login",maritalStatusMst);
			if (null != maritalstatusMstNew) {
				MaritalStatusService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);	
				logger.info("maritalstatus & Cache updated successfully for {}", maritalstatus.getMaritalStatusId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update maritalstatus with id : {}",  maritalstatus.getMaritalStatusId());
				return "Failure while updating record";
			}
		} else {
			
			return "Failure.. Record does not exists";
		}
    

}
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public MaritalStatusVO getMaritalStatusById(@PathVariable String id) {
		logger.info("Fetch maritalStatus by maritalStatusId : {}", id);
		MaritalStatusMst maritalStatusMst = MaritalStatusService.getByMaritalStatusId(id);
	    MaritalStatusVO maritalStatus = null;
		if (null != maritalStatusMst) {
			logger.info("Inside if loop");
			maritalStatus = new MaritalStatusVO();
			maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
			maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
			maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());			
			maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
			maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));				
			maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
		}
        return maritalStatus;
    }
	

	@GetMapping(value = "/name/{name}", produces = "application/json")
    public MaritalStatusVO getMaritalStatusByName(@PathVariable String name) {
		logger.info("Fetch maritalStatus by maritalStatusName : {}", name);		
		String maritalStatusId = MaritalStatusService.getMaritalStatusIdByMaritalStatusName(name);
		MaritalStatusMst maritalStatusMst = MaritalStatusService.getByMaritalStatusId(maritalStatusId);	
		MaritalStatusVO maritalStatus = null;
		if (null != maritalStatusMst) {
			logger.debug("Inside if Condition");
			maritalStatus = new MaritalStatusVO();
			maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
			maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
			maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());		
			maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
			maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));				
			maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
		}
        return maritalStatus;
    }

	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public MaritalStatusVOList getMaritalStatusByAuthorizedStatus() { 
		logger.info("Fetch MaritalStatus by AuthStatus : A");
		List<MaritalStatusMst> maritalStatusList = MaritalStatusService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		MaritalStatusVOList voList = new MaritalStatusVOList();
		List<MaritalStatusVO> maritalStatusVOList = new ArrayList<MaritalStatusVO>();		
		if (null != maritalStatusList) {		
			for (MaritalStatusMst maritalStatusMst: maritalStatusList) {			
				MaritalStatusVO maritalStatus = new MaritalStatusVO();
				maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
				maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
				maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());				
				maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
				maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));				
				maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
				
				
				maritalStatusVOList.add(maritalStatus);
			}
		}
		voList.setMaritalStatus(maritalStatusVOList);
        return voList;
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public MaritalStatusVOList getMaritalStatusByPendingStatus() { 
		logger.info("Fetch MaritalStatus by AuthStatus : P");
		List<MaritalStatusMst> maritalStatusList = MaritalStatusService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		MaritalStatusVOList voList = new MaritalStatusVOList();
		List<MaritalStatusVO> maritalStatusVOList = new ArrayList<MaritalStatusVO>();	
		if (null != maritalStatusList) {
			for (MaritalStatusMst maritalStatusMst: maritalStatusList) {
				MaritalStatusVO maritalStatus = new MaritalStatusVO();
				maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
				maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
				maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());				
				maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
				maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));				
				maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
				
				maritalStatusVOList.add(maritalStatus);
			}
		}
		voList.setMaritalStatus(maritalStatusVOList);
        return voList;
    }
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public MaritalStatusVOList getMaritalStatusByRejectedStatus() { 
		logger.info("Fetch MaritalStatus by AuthStatus : R");
		List<MaritalStatusMst> maritalStatusList = MaritalStatusService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		MaritalStatusVOList voList = new MaritalStatusVOList();
		List<MaritalStatusVO> maritalStatusVOList = new ArrayList<MaritalStatusVO>();
		
		if (null != maritalStatusList) {
			for (MaritalStatusMst maritalStatusMst: maritalStatusList) {
				MaritalStatusVO maritalStatus = new MaritalStatusVO();
				maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
				maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
				maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());				
				maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
				maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));				
				maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
				
				maritalStatusVOList.add(maritalStatus);
			}
		}
		voList.setMaritalStatus(maritalStatusVOList);
        return voList;
    }

	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public MaritalStatusVOList getMaritalStatusByDeleted() { 
		logger.info("Fetch MaritalStatus by AuthStatus : D");
		List<MaritalStatusMst> maritalStatusList = MaritalStatusService.getByDeleted(Boolean.TRUE);
		MaritalStatusVOList voList = new MaritalStatusVOList();
		List<MaritalStatusVO> maritalStatusVOList = new ArrayList<MaritalStatusVO>();  		
		if (null != maritalStatusList) {
			for (MaritalStatusMst maritalStatusMst: maritalStatusList) {
				MaritalStatusVO maritalStatus = new MaritalStatusVO();
				maritalStatus.setMaritalStatusId(maritalStatusMst.getMaritalStatusId());
				maritalStatus.setMaritalStatusName(maritalStatusMst.getMaritalStatusName());
				maritalStatus.setMaritalStatusDisplayName(maritalStatusMst.getMaritalStatusDisplayName());
				
				maritalStatus.setAuthStatus(maritalStatusMst.getAuthStatus());
				maritalStatus.setIsActive(Integer.toString(maritalStatusMst.getIsActive()));					
				maritalStatus.setIsDeleted(String.valueOf(maritalStatusMst.getIsDeleted()));
				maritalStatusVOList.add(maritalStatus);
			}
		}
		voList.setMaritalStatus(maritalStatusVOList);
        return voList;  
    }
	
	
	
	
	@PostMapping(value = "/deleteMaritalStatus/{id}", consumes = "application/json", produces = "application/json")
    public String deleteMaritalStatus(@PathVariable(name="id") String maritalStatusId) {
		logger.debug("Delete MaritalStatus  by MaritalStatus Id : {}", maritalStatusId);
		MaritalStatusMst maritalStatusMst = MaritalStatusService.getByMaritalStatusId(maritalStatusId);		
		if (null != maritalStatusMst) {
			logger.info("Inside in condition");
			Date dt = new Date();
			maritalStatusMst.setDeletedBy("login3");
			maritalStatusMst.setDeletedDate(dt);
			maritalStatusMst.setDeletedTime(dt);
			maritalStatusMst.setAuthStatus("D");
			maritalStatusMst.setIsActive(0);
			maritalStatusMst.setIsDeleted(Boolean.TRUE);
			
			MaritalStatusMst maritalStatusMstNew = MaritalStatusService.saveOrUpdate("login", maritalStatusMst);
			
			if (null != maritalStatusMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from method");
			return "Failure.. Record does not exists";
		}
    }
	
}




