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
import com.itl.domain.entities.masters.ResidentialStatusMst;
import com.itl.service.masters.ResidentialStatusService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.ResidentialStatusVO;
import com.itl.web.dto.ResidentialStatusVOList;

@RestController
@RequestMapping("/ResidentialStatus")
public class ResidentialStatusController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResidentialStatusController.class);
	
	@Autowired
	private ResidentialStatusService ResidentialStatusService;
	
	@PostMapping(value = "/createResidentialStatus", consumes = "application/json", produces = "application/json")
    public String createResidentialStatus(@RequestBody ResidentialStatusVO residentialStatus) {
		logger.debug("Add ResidentialStatus for ResidentialStatusId : {}" + residentialStatus.getResidentialStatusId());
		ResidentialStatusMst residentialStatusMst = ResidentialStatusService.getByResidentialStatusId(residentialStatus.getResidentialStatusId());
		if (null != residentialStatusMst) {
			logger.info("ResidentialStatus with ResidentialStatusId {} already exists.", residentialStatus.getResidentialStatusId());
			return "Failure.. Record already exists";
		} else {

		   residentialStatusMst = new ResidentialStatusMst();
			Date dt = new Date();
			residentialStatusMst.setCreatedBy("login");
			residentialStatusMst.setCreatedDate(dt);
			residentialStatusMst.setCreatedTime(dt);
			residentialStatusMst.setLastModifiedBy("login");
			residentialStatusMst.setLastModifiedDate(dt);
			residentialStatusMst.setLastModifiedTime(dt);
			residentialStatusMst.setAuthStatus("A");
			residentialStatusMst.setIsActive(1);
			residentialStatusMst.setIsDeleted(Boolean.FALSE);
			residentialStatusMst.setResidentialStatusId(residentialStatus.getResidentialStatusId());
			residentialStatusMst.setResidentialStatusName(residentialStatus.getResidentialStatusName());
			residentialStatusMst.setResidentialStatusDisplayName(residentialStatus.getResidentialStatusDisplayName());
			
			
			ResidentialStatusMst residentialStatusMstNew = ResidentialStatusService.saveOrUpdate("login", residentialStatusMst);
			if (null != residentialStatusMstNew) {
				ResidentialStatusService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Country & Cache - {} - saved Successfully", residentialStatus.getResidentialStatusId());
				return "Successfully saved record";
			} else {
				logger.debug("Inside else Condition.. Failure while saving record");
				logger.info("Exit from method");
				return "Failure while saving record";
			}
		}
    }
	
	
	
	@PostMapping(value = "/updateResidentialStatus", consumes = "application/json", produces = "application/json")
    public String updateResidentialStatus(@RequestBody ResidentialStatusVO residentialStatus) {
		logger.debug("Updating country with countryId : {}" + residentialStatus.getResidentialStatusId());
		ResidentialStatusMst residentialStatusMst = ResidentialStatusService.getByResidentialStatusId(residentialStatus.getResidentialStatusId());
		
		if (null != residentialStatusMst ) {
			logger.info("Inside if  Condition");
			
			Date dt = new Date();
			residentialStatusMst.setLastModifiedBy("login2");
			residentialStatusMst.setLastModifiedDate(dt); 
			residentialStatusMst.setLastModifiedTime(dt);
			residentialStatusMst.setAuthStatus(residentialStatus.getAuthStatus());
			residentialStatusMst.setIsActive(Integer.parseInt(residentialStatus.getIsActive()));
			residentialStatusMst.setIsDeleted(residentialStatus.getIsDeleted().equals("1")?true:false);	
			residentialStatusMst.setResidentialStatusId(residentialStatus.getResidentialStatusId());
			residentialStatusMst.setResidentialStatusName(residentialStatus.getResidentialStatusName());
			residentialStatusMst.setResidentialStatusDisplayName(residentialStatus.getResidentialStatusDisplayName());
			logger.info("Before saveOrUpdate call" );
			ResidentialStatusMst residentialStatusMstNew = ResidentialStatusService.saveOrUpdate("login", residentialStatusMst);
			
			if (null != residentialStatusMstNew) {
				ResidentialStatusService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("Inside if Condition ..record updated Successfully");
				return "Successfully updated Record";
			} else {
				logger.info("Inside else Condition.. Failure while updating record");
				return "Failure while updating record";
			}
		} else {
			logger.info("Exit from method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public ResidentialStatusVO getResidentialStatusById(@PathVariable String id) {
		logger.info("Inside getResidentialStatusById method");
		ResidentialStatusMst residentialStatusMst = ResidentialStatusService.getByResidentialStatusId(id);
		ResidentialStatusVO residentialStatus = null;
		logger.debug("Before if condition " +residentialStatusMst);
		if (null != residentialStatusMst) {
			residentialStatus = new ResidentialStatusVO();
			residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
			residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
			residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());
			residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
			residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
			residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
		}
		logger.debug("Exit from method");
        return residentialStatus;
    }

	

	@GetMapping(value = "/name/{name}", produces = "application/json")
    public ResidentialStatusVO getResidentialStatusByName(@PathVariable String name) {
		logger.info("Fetch residentialStatus by residentialStatusName : {}", name);
		String residentialStatusId = ResidentialStatusService.getResidentialStatusIdByResidentialStatusName(name);
		ResidentialStatusMst residentialStatusMst = ResidentialStatusService.getByResidentialStatusId(residentialStatusId);
		ResidentialStatusVO residentialStatus = null;
		if (null != residentialStatusMst) {
			logger.info("Inside if Condition");
			residentialStatus = new ResidentialStatusVO();
			residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
			residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
			residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());
			residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
			residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
			residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
		}
		logger.debug("Exit from getResidentialStatusByName method");
        return residentialStatus;
    }
	
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public ResidentialStatusVOList getResidentialStatusByAuthorizedStatus() { 
		logger.info("Fetch residentialStatus by AuthStatus : A");
		List<ResidentialStatusMst> residentialStatusList = ResidentialStatusService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		ResidentialStatusVOList voList = new ResidentialStatusVOList();
		List<ResidentialStatusVO> residentialStatusVOList = new ArrayList<ResidentialStatusVO>();  
		
		if (null != residentialStatusList) {
			logger.info("Inside if loop");
			for (ResidentialStatusMst residentialStatusMst: residentialStatusList) {
				logger.info("Inside for loop");
				ResidentialStatusVO residentialStatus = new ResidentialStatusVO();
				residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
				residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
				residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());
				residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
				residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
				residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
				
				residentialStatusVOList.add(residentialStatus);
			}
		}
		voList.setResidentialStatus(residentialStatusVOList);
		logger.debug("Exit from getResidentialStatusByAuthorizedStatus method");
        return voList;  
    }
	
	

	@GetMapping(value = "/list/pending", produces = "application/json")
    public ResidentialStatusVOList getResidentialStatusByPendingStatus() { 
		logger.info("Fetch residentialStatus by AuthStatus : P");
		List<ResidentialStatusMst> residentialStatusList = ResidentialStatusService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		ResidentialStatusVOList voList = new ResidentialStatusVOList();
		List<ResidentialStatusVO> residentialStatusVOList = new ArrayList<ResidentialStatusVO>();  
		
		if (null != residentialStatusList) {
			logger.info("Inside if loop");
			for (ResidentialStatusMst residentialStatusMst: residentialStatusList) {
				logger.info("Inside for loop");
				ResidentialStatusVO residentialStatus = new ResidentialStatusVO();
				residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
				residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
				residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());

				residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
				residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
				residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
				
				residentialStatusVOList.add(residentialStatus);
			}
		}
		voList.setResidentialStatus(residentialStatusVOList);
		logger.debug("Exit from getResidentialStatusByPendingStatus method");
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public ResidentialStatusVOList getResidentialStatusByRejectedStatus() { 
		logger.info("Fetch residentialStatus by AuthStatus : R");
		List<ResidentialStatusMst> residentialStatusList = ResidentialStatusService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		ResidentialStatusVOList voList = new ResidentialStatusVOList();
		List<ResidentialStatusVO> residentialStatusVOList = new ArrayList<ResidentialStatusVO>();  
		
		if (null != residentialStatusList) {
			logger.info("Inside if loop");
			for (ResidentialStatusMst residentialStatusMst: residentialStatusList) {
				logger.info("Inside for loop");
				ResidentialStatusVO residentialStatus = new ResidentialStatusVO();
				residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
				residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
				residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());
				residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
				residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
				residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
				
				residentialStatusVOList.add(residentialStatus);
			}
		}
		voList.setResidentialStatus(residentialStatusVOList);
		logger.debug("Exit from getResidentialStatusByRejectedStatus  method");
        return voList;  
    }
	
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public ResidentialStatusVOList getResidentialStatusByDeleted() { 
		logger.info("Fetch residentialStatus by AuthStatus : D");
		List<ResidentialStatusMst> residentialStatusList = ResidentialStatusService.getByDeleted(Boolean.TRUE);
		ResidentialStatusVOList voList = new ResidentialStatusVOList();
		List<ResidentialStatusVO> residentialStatusVOList = new ArrayList<ResidentialStatusVO>();  
		
		if (null != residentialStatusList) {
			logger.info("Inside if loop");
			for (ResidentialStatusMst residentialStatusMst: residentialStatusList) {
				logger.info("Inside for loop");
				ResidentialStatusVO residentialStatus = new ResidentialStatusVO();
				residentialStatus.setResidentialStatusId(residentialStatusMst.getResidentialStatusId());
				residentialStatus.setResidentialStatusName(residentialStatusMst.getResidentialStatusName());
				residentialStatus.setResidentialStatusDisplayName(residentialStatusMst.getResidentialStatusDisplayName());
				residentialStatus.setAuthStatus(residentialStatusMst.getAuthStatus());
				residentialStatus.setIsActive(Integer.toString(residentialStatusMst.getIsActive()));					
				residentialStatus.setIsDeleted(String.valueOf(residentialStatusMst.getIsDeleted()));
				
				residentialStatusVOList.add(residentialStatus);
			}
		}
		voList.setResidentialStatus(residentialStatusVOList);
		logger.debug("Exit from getResidentialStatusByDeleted method");
        return voList;  
    }
	
	
	
	@PostMapping(value = "/deleteResidentialStatus/{id}", consumes = "application/json", produces = "application/json")
    public String deleteResidentialStatus(@PathVariable (name="id") String residentialStatusId) {
		logger.debug("Delete country by countryId : {}", residentialStatusId);
		ResidentialStatusMst residentialStatusMst = ResidentialStatusService.getByResidentialStatusId(residentialStatusId);
		
		if (null != residentialStatusMst) {
			logger.info("Inside if condition");
	
			Date dt = new Date();
			residentialStatusMst.setDeletedBy("login3");
			residentialStatusMst.setDeletedDate(dt);
			residentialStatusMst.setDeletedTime(dt);
			residentialStatusMst.setAuthStatus("D");
			residentialStatusMst.setIsActive(0);
			residentialStatusMst.setIsDeleted(Boolean.TRUE);
			
			ResidentialStatusMst residentialStatusMstNew = ResidentialStatusService.saveOrUpdate("login", residentialStatusMst);
			if (null != residentialStatusMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteResidentialStatus  method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	
	
}
