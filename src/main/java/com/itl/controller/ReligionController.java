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

import com.itl.domain.entities.masters.ReligionMst;
import com.itl.service.masters.ReligionService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.ReligionVO;
import com.itl.web.dto.ReligionVOList;

@RestController
@RequestMapping("/Religion")
public class ReligionController {
	private static final Logger logger = LoggerFactory.getLogger(ReligionController.class);

	@Autowired
	private ReligionService ReligionService;
	
	@PostMapping(value = "/createReligion", consumes = "application/json", produces = "application/json")
    public String createReligion(@RequestBody ReligionVO religion) {
		logger.debug("Add religion for religionId : {}" + religion.getReligionId());
		ReligionMst religionMst = ReligionService.getByReligionId(religion.getReligionId());
		if (null != religionMst) {
			logger.info(" religion and religionId  already exists." + religion.getReligionId());
			return "Failure.. Record already exists";
		} else {
			logger.info("Inside else  Condition");
		    religionMst = new ReligionMst();
			Date dt = new Date();
			religionMst.setCreatedBy("login");
			religionMst.setCreatedDate(dt);
			religionMst.setCreatedTime(dt);
			religionMst.setLastModifiedBy("login");
			religionMst.setLastModifiedDate(dt);
			religionMst.setLastModifiedTime(dt);
			religionMst.setAuthStatus("A");
			religionMst.setIsActive(1);
			religionMst.setIsDeleted(Boolean.FALSE);
			religionMst.setReligionId(religion.getReligionId());
			religionMst.setReligionName(religion.getReligionName());
			religionMst.setReligionDisplayName(religion.getReligionDisplayName());
			
			
			ReligionMst religionMstNew = ReligionService.saveOrUpdate("login", religionMst);
			if (null != religionMstNew) {
				ReligionService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Religion & cache saved Successfully" + religion.getReligionId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save Religion" + religion.getReligionId());
				return "Failure while saving record";
			}
		}
    } 
	
	
	@PostMapping(value = "/updateReligion", consumes = "application/json", produces = "application/json")
    public String updateReligion(@RequestBody ReligionVO religion) {    
		logger.debug("updating religion with religionId : {}" + religion.getReligionId());
		ReligionMst religionMst = ReligionService.getByReligionId(religion.getReligionId());
		
		if (null != religionMst) {

			Date dt = new Date();
			religionMst.setLastModifiedBy("login2");
			religionMst.setLastModifiedDate(dt); 
			religionMst.setLastModifiedTime(dt);
			religionMst.setAuthStatus(religion.getAuthStatus());
			religionMst.setIsActive(Integer.parseInt(religion.getIsActive()));
			religionMst.setIsDeleted(religion.getIsDeleted().equals("1")?true:false);	
			religionMst.setReligionId(religion.getReligionId());
			religionMst.setReligionName(religion.getReligionName());
			religionMst.setReligionDisplayName(religion.getReligionDisplayName());
			ReligionMst religionMstNew = ReligionService.saveOrUpdate("login", religionMst);
			
			if (null != religionMstNew) {
				ReligionService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.debug("update religion & chache for: {}" + religion.getReligionId());
				return "Successfully updated Record";
			} else {
				logger.debug("Failed to update Relgion" + religion.getReligionId());
				return "Failure while updating record";
			}
		} else {
			logger.info("Exit from method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public ReligionVO getReligionById(@PathVariable String id) {
		logger.info("Fetch Religion by ReligionId : {}", id);
		ReligionMst religionMst = ReligionService.getByReligionId(id);
		ReligionVO religion = null;
		if (null != religionMst) {
			religion = new ReligionVO();
			religion.setReligionId(religionMst.getReligionId());
			religion.setReligionName(religionMst.getReligionName());
			religion.setReligionDisplayName(religionMst.getReligionDisplayName());
			religion.setAuthStatus(religionMst.getAuthStatus());
			religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
			religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
		}
        return religion;
    }
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public ReligionVO getReligionByName(@PathVariable String name) {
		logger.info("Fetch Religion by ReligionName : {}", name);
		//String religionId 
		ReligionMst religionMst = ReligionService.getReligionIdByReligionName(name);
		//ReligionMst religionMst = ReligionService.getByReligionId(religionId);
		ReligionVO religion = null;
		if (null != religionMst) {
			religion = new ReligionVO();
			religion.setReligionId(religionMst.getReligionId());
			religion.setReligionName(religionMst.getReligionName());
			religion.setReligionDisplayName(religionMst.getReligionDisplayName());
			religion.setAuthStatus(religionMst.getAuthStatus());
			religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
			religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
		}
        return religion;
    }
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public ReligionVOList getReligionByAuthorizedStatus() { 
		logger.info("Fetch Religion by AuthStatus : A");
		List<ReligionMst> stateList = ReligionService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		ReligionVOList voList = new ReligionVOList();
		List<ReligionVO> religionVOList = new ArrayList<ReligionVO>();  
		
		if (null != stateList) {
			for (ReligionMst religionMst: stateList) {
				ReligionVO religion = new ReligionVO();
				religion.setReligionId(religionMst.getReligionId());
				religion.setReligionName(religionMst.getReligionName());
				religion.setReligionDisplayName(religionMst.getReligionDisplayName());
				religion.setAuthStatus(religionMst.getAuthStatus());
				religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
				religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
				religionVOList.add(religion);
			}
		}
		voList.setReligion(religionVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public ReligionVOList getReligionByPendingStatus() { 
		logger.info("Fetch Religion by AuthStatus : P");
		List<ReligionMst> religionList = ReligionService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		ReligionVOList voList = new ReligionVOList();
		List<ReligionVO> religionVOList = new ArrayList<ReligionVO>();  
		
		if (null != religionList) {
			for (ReligionMst religionMst: religionList) {
				ReligionVO religion = new ReligionVO();
				religion.setReligionId(religionMst.getReligionId());
				religion.setReligionName(religionMst.getReligionName());
				religion.setReligionDisplayName(religionMst.getReligionDisplayName());
				religion.setAuthStatus(religionMst.getAuthStatus());
				religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
				religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
				
				religionVOList.add(religion);
			}
		}
		voList.setReligion(religionVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public ReligionVOList getReligionByRejectedStatus() { 
		logger.info("Fetch Religion by AuthStatus : R");
		List<ReligionMst> stateList = ReligionService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		ReligionVOList voList = new ReligionVOList();
		List<ReligionVO> ReligionVOList = new ArrayList<ReligionVO>();  
		
		if (null != stateList) {
			for (ReligionMst religionMst: stateList) {
				ReligionVO religion = new ReligionVO();
				religion.setReligionId(religionMst.getReligionId());
				religion.setReligionName(religionMst.getReligionName());
				religion.setReligionDisplayName(religionMst.getReligionDisplayName());
				religion.setAuthStatus(religionMst.getAuthStatus());
				religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
				religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
				
				ReligionVOList.add(religion);
			}
		}
		voList.setReligion(ReligionVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public ReligionVOList getReligionByDeleted() { 
		logger.info("Fetch Religion by AuthStatus : D");
		List<ReligionMst> religionList = ReligionService.getByDeleted(Boolean.TRUE);
		ReligionVOList voList = new ReligionVOList();
		List<ReligionVO> religionVOList = new ArrayList<ReligionVO>();  
		
		if (null != religionList) {
			for (ReligionMst religionMst: religionList) {
				ReligionVO religion = new ReligionVO();
				religion.setReligionId(religionMst.getReligionId());
				religion.setReligionName(religionMst.getReligionName());
				religion.setReligionDisplayName(religionMst.getReligionDisplayName());
				religion.setAuthStatus(religionMst.getAuthStatus());
				religion.setIsActive(Integer.toString(religionMst.getIsActive()));					
				religion.setIsDeleted(String.valueOf(religionMst.getIsDeleted()));
				
				religionVOList.add(religion);
			}
		}
		voList.setReligion(religionVOList);
        return voList;  
    }
	
	
	
	
	@PostMapping(value = "/deleteReligion/{id}", consumes = "application/json", produces = "application/json")
    public String deleteReligion(@PathVariable (name="id") String religionId) {
		logger.debug("Delete religion by religionId : {}", religionId);
		ReligionMst religionMst = ReligionService.getByReligionId(religionId);
		
		if (null != religionMst) {
			Date dt = new Date();
			religionMst.setDeletedBy("login3");
			religionMst.setDeletedDate(dt);
			religionMst.setDeletedTime(dt);
			religionMst.setIsActive(0);
			religionMst.setIsDeleted(Boolean.TRUE);
			
			ReligionMst religionMstNew = ReligionService.saveOrUpdate("login", religionMst);
			if (null != religionMstNew) {
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
