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

import com.itl.domain.entities.masters.BranchMst;
import com.itl.service.masters.BranchService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.BranchVO;
import com.itl.web.dto.BranchVOList;

@RestController
@RequestMapping("/Branch")
public class BranchController {

     private static final Logger logger = LoggerFactory.getLogger(BranchController.class);
	
     @Autowired
     private BranchService BranchService;
	
    @PostMapping(value = "/createBranch", consumes = "application/json", produces = "application/json")
    public String createBranch(@RequestBody BranchVO branch) {
		logger.info("Inside createBranch method");
		BranchMst branchMst = BranchService.getByBranchId(branch.getBranchId());
		logger.info("After Service call");
		if (null != branchMst ) {
			logger.info("Branch with BranchId {} already exists.", branch.getBranchId());
			return "Failure.. Record already exists";
		}
		else {
			
			logger.info("Inside else  Condition");
			 branchMst = new BranchMst();
			Date dt = new Date();
			branchMst.setCreatedBy("login");
			branchMst.setCreatedDate(dt);
			branchMst.setCreatedTime(dt);
			branchMst.setLastModifiedBy("login");
			branchMst.setLastModifiedDate(dt);
			branchMst.setLastModifiedTime(dt);
			branchMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			branchMst.setIsActive(1);
			branchMst.setIsDeleted(Boolean.FALSE);
			branchMst.setBranchId(branch.getBranchId());
			branchMst.setBranchName(branch.getBranchName());
			branchMst.setBranchDisplayName(branch.getBranchDisplayName());
			branchMst.setBaseCurrency(branch. getBaseCurrency());
			
			
			BranchMst branchMstNew = BranchService.saveOrUpdate("login", branchMst);
			if (null != branchMstNew) {	
				BranchService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Branch & Cache - {} - saved Successfully", branch.getBranchId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save branch - {}", branch.getBranchId());
				return "Failure while saving record";
			}
		}
    }
	
	
	@PostMapping(value = "/updateBranch", consumes = "application/json", produces = "application/json")
    public String updateBranch(@RequestBody BranchVO branch) {
		logger.debug("Updating Branch with BranchId : {}" + branch.getBranchId());
		BranchMst branchMst = BranchService.getByBranchId(branch.getBranchId());
		if (null != branchMst) {				
			Date dt = new Date();
			branchMst.setLastModifiedBy("login2");
			branchMst.setLastModifiedDate(dt);
			branchMst.setLastModifiedTime(dt);
        	branchMst.setAuthStatus(branch.getAuthStatus());      		
			branchMst.setIsActive(Integer.parseInt(branch.getIsActive()));
			branchMst.setIsDeleted(branch.getIsDeleted().equals("1")?true:false);	
			branchMst.setBranchDisplayName(branch.getBranchDisplayName());
			branchMst.setBranchId(branch.getBranchId());
			branchMst.setBranchName(branch.getBranchName());
			branchMst.setBaseCurrency(branch.getBaseCurrency());
			
			BranchMst branchMstNew = BranchService.saveOrUpdate("login", branchMst);
			if (null != branchMstNew) {
				BranchService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("Branch & Cache updated successfully for {}", branch.getBranchId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update Branch with id : {}",  branch.getBranchId());
				return "Failure while updating record";
			}
		} else {
		
			return "Failure.. Record does not exists";
		}
    }

	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public BranchVO getBranchById(@PathVariable String id) {
		logger.info("Fetch Branch by branchId : {}", id);
	    BranchMst branchMst = BranchService.getByBranchId(id);
		BranchVO branch = null;
		if (null != branchMst) {			
			branch = new BranchVO();
			branch.setBranchId(branchMst.getBranchId());
			branch.setBranchName(branchMst.getBranchName());
			branch.setBranchDisplayName(branchMst.getBranchDisplayName());
			branch.setBaseCurrency(branchMst. getBaseCurrency());
			branch.setAuthStatus(branchMst.getAuthStatus());
			branch.setIsActive(Integer.toString(branchMst.getIsActive()));				
			branch.setIsDeleted(String.valueOf(branchMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the branch is not found
        return branch;
    }
		
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public BranchVO getBranchIdByBranchName(@PathVariable String name) {		
		logger.info("Fetch Branch by branchName : {}", name);
		String branchId = BranchService.getBranchIdByBranchName(name);
		BranchMst branchList = BranchService.getByBranchId(branchId);	
		BranchVO branch = null;
		if (null != branchList) {
			logger.info("Inside if Condition");
			branch = new BranchVO();
			branch.setBranchId(branchList.getBranchId());
			branch.setBranchName(branchList.getBranchName());
			branch.setBranchDisplayName(branchList.getBranchDisplayName());
			branch.setBaseCurrency(branchList. getBaseCurrency());
			branch.setAuthStatus(branchList.getAuthStatus());
			branch.setIsActive(Integer.toString(branchList.getIsActive()));				
			branch.setIsDeleted(String.valueOf(branchList.getIsDeleted()));
		}
		
        return branch;
    }

	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public BranchVOList getBranchByAuthorizedStatus() { 
		logger.info("Fetch Branch by AuthStatus : A");
		List<BranchMst> branchList = BranchService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		BranchVOList voList = new BranchVOList();
		List<BranchVO> branchVOList = new ArrayList<BranchVO>();  		
		if (null != branchList) {
			logger.info("Inside if loop");
			for (BranchMst branchMst: branchList) {				
				BranchVO branch = new BranchVO();
				branch.setBranchId(branchMst.getBranchId());
				branch.setBranchName(branchMst.getBranchName());
				branch.setBranchDisplayName(branchMst.getBranchDisplayName());
				branch.setBaseCurrency(branchMst. getBaseCurrency());
				branch.setAuthStatus(branchMst.getAuthStatus());
				branch.setIsActive(Integer.toString(branchMst.getIsActive()));				
				branch.setIsDeleted(String.valueOf(branchMst.getIsDeleted()));
				
				branchVOList.add(branch);
			}
		}
		voList.setBranch(branchVOList);		
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public BranchVOList getBranchByPendingStatus() { 
		logger.info("Fetch Branch by AuthStatus : P");
		List<BranchMst> branchList = BranchService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		BranchVOList voList = new BranchVOList();
		List<BranchVO> branchVOList = new ArrayList<BranchVO>();  
		
		if (null != branchList) {
			logger.info("Inside if loop");
			for (BranchMst branchMst: branchList) {
				logger.info("Inside for loop");
				BranchVO branch = new BranchVO();
				branch.setBranchId(branchMst.getBranchId());
				branch.setBranchName(branchMst.getBranchName());
				branch.setBranchDisplayName(branchMst.getBranchDisplayName());
				branch.setBaseCurrency(branchMst. getBaseCurrency());
				branch.setAuthStatus(branchMst.getAuthStatus());
				branch.setIsActive(Integer.toString(branchMst.getIsActive()));				
				branch.setIsDeleted(String.valueOf(branchMst.getIsDeleted()));
				branchVOList.add(branch);
			}
		}
		voList.setBranch(branchVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public BranchVOList getBranchByRejectedStatus() { 
		logger.info("Fetch branch by AuthStatus : R");
		List<BranchMst> branchList = BranchService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		BranchVOList voList = new BranchVOList();
		List<BranchVO> branchVOList = new ArrayList<BranchVO>();  
		
		if (null != branchList) {
			logger.info("Inside if loop");
			for (BranchMst branchMst: branchList) {
				logger.info("Inside for loop");
				BranchVO branch = new BranchVO();
				branch.setBranchId(branchMst.getBranchId());
				branch.setBranchName(branchMst.getBranchName());
				branch.setBranchDisplayName(branchMst.getBranchDisplayName());
				branch.setBaseCurrency(branchMst. getBaseCurrency());
				branch.setAuthStatus(branchMst.getAuthStatus());
				branch.setIsActive(Integer.toString(branchMst.getIsActive()));				
				branch.setIsDeleted(String.valueOf(branchMst.getIsDeleted()));
				
				branchVOList.add(branch);
			}
		}
		voList.setBranch(branchVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public BranchVOList getBranchByDeleted() { 
		logger.info("Fetch Branch by AuthStatus : D");
		List<BranchMst> branchList = BranchService.getByDeleted(Boolean.TRUE);
		BranchVOList voList = new BranchVOList();
		List<BranchVO> branchVOList = new ArrayList<BranchVO>();  
		
		if (null != branchList) {
			for (BranchMst branchMst: branchList) {			
				BranchVO branch = new BranchVO();
				branch.setBranchId(branchMst.getBranchId());
				branch.setBranchName(branchMst.getBranchName());
				branch.setBranchDisplayName(branchMst.getBranchDisplayName());
				branch.setBaseCurrency(branchMst. getBaseCurrency());
				branch.setAuthStatus(branchMst.getAuthStatus());
				branch.setIsActive(Integer.toString(branchMst.getIsActive()));				
				branch.setIsDeleted(String.valueOf(branchMst.getIsDeleted()));
				branchVOList.add(branch);
			}
		}
		voList.setBranch(branchVOList);
        return voList;  
    }
		
	
	@PostMapping(value = "/deleteBranch/{id}", consumes = "application/json", produces = "application/json")
    public String deleteBranch(@PathVariable(name="id") String branchId) {
		logger.debug("Delete branch by BranchId : {}", branchId);
		BranchMst branchMst = BranchService.getByBranchId(branchId);
		
		if (null != branchMst ) {			
			Date dt = new Date();
			branchMst.setDeletedBy("login3");
			branchMst.setDeletedDate(dt);
			branchMst.setDeletedTime(dt);
			branchMst.setAuthStatus("D");
			branchMst.setIsActive(0);
			branchMst.setIsDeleted(Boolean.TRUE);
			
			BranchMst branchMstNew = BranchService.saveOrUpdate("login", branchMst);
			if (null != branchMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteBranch  method");
			return "Failure.. Record does not exists";
		}
    }
	

	
}
