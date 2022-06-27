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

import com.itl.domain.entities.masters.IndustryMst;
import com.itl.service.masters.IndustryService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.IndustryVO;
import com.itl.web.dto.IndustryVOList;

@RestController
@RequestMapping("/industry")
public class IndustryController {
	private static final Logger logger = LoggerFactory.getLogger(IndustryController.class);
	@Autowired
	private IndustryService IndustryService;
	
	@PostMapping(value = "/createIndustry", consumes = "application/json", produces = "application/json")
    public String createIndustry(@RequestBody IndustryVO industry) {
		logger.debug("Add industry for industryId : {}" + industry.getIndustryId());
		IndustryMst industryMst = IndustryService.getByIndustryId(industry.getIndustryId());
		if (null != industryMst) {
			logger.info("industry with industryId {} already exists.", industry.getIndustryId());
			return "Failure.. Record already exists";
		} else {
			industryMst = new IndustryMst();
			Date dt = new Date();
			industryMst.setCreatedBy("login");
			industryMst.setCreatedDate(dt);
			industryMst.setCreatedTime(dt);
			industryMst.setLastModifiedBy("login");
			industryMst.setLastModifiedDate(dt);
			industryMst.setLastModifiedTime(dt);
			industryMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			industryMst.setIsActive(1);
			industryMst.setIsDeleted(Boolean.FALSE);
			industryMst.setIndustryId(industryMst.getIndustryId());
			industryMst.setIndustryName(industryMst.getIndustryName());
			industryMst.setIndustryDisplayName(industryMst.getIndustryDisplayName());

			IndustryMst industryMstNew = IndustryService.saveOrUpdate("login", industryMst);
			if (null != industryMstNew) {
				IndustryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("industry & Cache - {} - saved Successfully", industry.getIndustryId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save industry - {}", industry.getIndustryId());
				return "Failure while saving record";
			}
		}
	}
	
	
	@PostMapping(value = "/updateIndustry", consumes = "application/json", produces = "application/json")
    public String updateIndustry(@RequestBody IndustryVO industry) {
		logger.debug("Updating industry with industryId : {}" + industry.getIndustryId());
		IndustryMst industryMst = IndustryService.getByIndustryId(industry.getIndustryId());
		if (null != industryMst) {
			Date dt = new Date();
			industryMst.setLastModifiedBy("login2");
			industryMst.setLastModifiedDate(dt);
			industryMst.setLastModifiedTime(dt);
			industryMst.setAuthStatus(industryMst.getAuthStatus());
			industryMst.setIsActive(industryMst.getIsActive());
			industryMst.setIsDeleted(industryMst.getIsDeleted().equals("1") ? true : false);
			industryMst.setIndustryId(industryMst.getIndustryId());
			industryMst.setIndustryName(industryMst.getIndustryName());
			industryMst.setIndustryDisplayName(industryMst.getIndustryDisplayName());

			IndustryMst industryMstNew = IndustryService.saveOrUpdate("login", industryMst);
			if (null != industryMstNew) {
				IndustryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("industry & Cache updated successfully for {}", industry.getIndustryId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update industry with id : {}", industry.getIndustryId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
	}
	
	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
	public IndustryVO getIndustryById(@PathVariable String id) {
		logger.info("Fetch industry by industryId : {}", id);
		IndustryMst industryMst = IndustryService.getByIndustryId(id);
		IndustryVO industry = null;
		if (null != industryMst) {
			industry = new IndustryVO();
			industry.setIndustryId(industryMst.getIndustryId());
			industry.setIndustryName(industryMst.getIndustryName());
			industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
			industry.setAuthStatus(industryMst.getAuthStatus());
			industry.setIsActive(Integer.toString(industryMst.getIsActive()));
			industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the industry is not found
		return industry;
	}
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
	public IndustryVO getIndustryByName(@PathVariable String name) {
		logger.info("Fetch industry by industryName : {}", name);
		String industryId= IndustryService.getIndustryIdByIndustryName(name);
		IndustryMst industryMst = IndustryService.getByIndustryId(industryId);
		IndustryVO industry = null;
		if (null != industryMst) {
			industry = new  IndustryVO();
			industry.setIndustryId(industryMst.getIndustryId());
			industry.setIndustryName(industryMst.getIndustryName());
			industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
			industry.setAuthStatus(industryMst.getAuthStatus());
			industry.setIsActive(Integer.toString(industryMst.getIsActive()));
			industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
		}
		//TODO - add else part to reply industry not found
		return industry;
	}	
	

	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public IndustryVOList getIndustryByAuthorizedStatus() { 
		logger.info("Fetch countries by AuthStatus : A");
		List<IndustryMst> industryList = IndustryService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);		
		IndustryVOList voList = new IndustryVOList();
		List<IndustryVO> industryVOList = new ArrayList<IndustryVO>(); 
		if (null != industryList) {
			for (IndustryMst industryMst: industryList) {
				IndustryVO industry = new IndustryVO();
				industry.setIndustryId(industryMst.getIndustryId());
				industry.setIndustryName(industryMst.getIndustryName());
				industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
				industry.setAuthStatus(industryMst.getAuthStatus());
				industry.setIsActive(Integer.toString(industryMst.getIsActive()));				
				industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
				industryVOList.add(industry);
			}
		}
		voList.setIndustry(industryVOList);
        return voList; 
    } 
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public IndustryVOList getIndustryBypendingStatus() { 
		logger.info("Fetch countries by AuthStatus : P");
		List<IndustryMst> industryList = IndustryService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		IndustryVOList voList = new IndustryVOList();
		List<IndustryVO> industryVOList = new ArrayList<IndustryVO>(); 
		if (null != industryList) {
			for (IndustryMst industryMst: industryList) {
				IndustryVO industry = new IndustryVO();
				industry.setIndustryId(industryMst.getIndustryId());
				industry.setIndustryName(industryMst.getIndustryName());
				industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
				industry.setAuthStatus(industryMst.getAuthStatus());
				industry.setIsActive(Integer.toString(industryMst.getIsActive()));				
				industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
				industryVOList.add(industry);
			}
		}
		voList.setIndustry(industryVOList);
        return voList; 
    } 
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public IndustryVOList getIndustryByRejectedStatus() { 
		logger.info("Fetch countries by AuthStatus : R");
		List<IndustryMst> industryList = IndustryService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		IndustryVOList voList = new IndustryVOList();
		List<IndustryVO> industryVOList = new ArrayList<IndustryVO>(); 
		if (null != industryList) {
			for (IndustryMst industryMst: industryList) {
				IndustryVO industry = new IndustryVO();
				industry.setIndustryId(industryMst.getIndustryId());
				industry.setIndustryName(industryMst.getIndustryName());
				industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
				industry.setAuthStatus(industryMst.getAuthStatus());
				industry.setIsActive(Integer.toString(industryMst.getIsActive()));				
				industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
				industryVOList.add(industry);
			}
		}
		voList.setIndustry(industryVOList);
        return voList; 
    } 
	
	 
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public IndustryVOList getIndustryByDeleted() { 
		logger.debug("Inside getIndustryByDeleted method");
		List<IndustryMst> industryList = IndustryService.getByDeleted(Boolean.TRUE); 
		logger.info("After Service call...ServiceindustryList= "+industryList);
		IndustryVOList voList = new IndustryVOList(); 
		List<IndustryVO> industryVOList = new ArrayList<IndustryVO>();
		if (null != industryList) {
			for (IndustryMst industryMst: industryList) {
				logger.debug("Inside for loop");
				IndustryVO industry = new IndustryVO();
				industry.setIndustryId(industryMst.getIndustryId());
				industry.setIndustryName(industryMst.getIndustryName());
				industry.setIndustryDisplayName(industryMst.getIndustryDisplayName());
				industry.setAuthStatus(industryMst.getAuthStatus());
				industry.setIsActive(Integer.toString(industryMst.getIsActive()));				
				industry.setIsDeleted(String.valueOf(industryMst.getIsDeleted()));
				industryVOList.add(industry);
			}
		}
		voList.setIndustry(industryVOList);
		logger.debug("Exit from getIndustryByDeleted method...voList= "+voList);
        return voList;
    }
	
	@PostMapping(value = "/deleteIndustry/{id}", consumes = "application/json", produces = "application/json")
    public String deleteIndustry(@PathVariable(name="id") String industryId) {
		logger.debug("Delete country by countryId : {}", industryId);
		IndustryMst industryMst = IndustryService.getByIndustryId(industryId);
		if (null != industryMst) {
			logger.debug("Inside if condition");			
			Date dt = new Date();
			industryMst.setDeletedBy("login3");
			industryMst.setDeletedDate(dt);
			industryMst.setDeletedTime(dt);
			industryMst.setAuthStatus("D");
			industryMst.setIsActive(0);
			industryMst.setIsDeleted(Boolean.TRUE);  
			
			IndustryMst industryMstNew = IndustryService.saveOrUpdate("login", industryMst);
			logger.debug("After saveOrUpdate call" );
			if (null != industryMstNew) {
				logger.debug("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.debug("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Inside else Condition ..Failure.. Record does not exists");
			return "Failure.. Record does not exists";
		}
    }
}
