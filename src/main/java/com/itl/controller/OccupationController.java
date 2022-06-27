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

import com.itl.domain.entities.masters.OccupationMst;
import com.itl.service.masters.OccupationService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.OccupationVO;
import com.itl.web.dto.OccupationVOList;

@RestController
@RequestMapping("/Occupation")
public class OccupationController {
	
	private static final Logger logger = LoggerFactory.getLogger(OccupationController.class);
	@Autowired
	private OccupationService OccupationService;
		
	@PostMapping(value = "/createOccupation", consumes = "application/json", produces = "application/json")
    public String createOccupation(@RequestBody OccupationVO occupation) {
		logger.debug("Add occupation for occupationId : {}" + occupation.getOccupationId());
		OccupationMst occupationMst = OccupationService.getByOccupationId(occupation.getOccupationId());
		logger.info("After Service call");
		if (null != occupationMst ) {
			logger.info("occupation with occupationId : {}" + occupation.getOccupationId());   
			return "Failure.. Record already exists";
		}
		else {
		
			occupationMst = new OccupationMst();
			Date dt = new Date();
			occupationMst.setCreatedBy("login");
			occupationMst.setCreatedDate(dt);
			occupationMst.setCreatedTime(dt);
			occupationMst.setLastModifiedBy("login");
			occupationMst.setLastModifiedDate(dt);
			occupationMst.setLastModifiedTime(dt);
			occupationMst.setAuthStatus("A");
			occupationMst.setIsActive(1);
			occupationMst.setIsDeleted(Boolean.FALSE);
			occupationMst.setOccupationId(occupation.getOccupationId());
			occupationMst.setOccupationName(occupation.getOccupationName());
			occupationMst.setOccupationDisplayName(occupation.getOccupationDisplayName());
			
			
			OccupationMst occupationMstNew = OccupationService.saveOrUpdate("login", occupationMst);
			if (null != occupationMstNew) {
				OccupationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.debug("occupation && cache : {}" + occupation.getOccupationId());
				return "Successfully saved record";
			} else {
				logger.debug("failed to save occupation" + occupation.getOccupationId());
				return "Failure while saving record";
			}
		}
    }
	
	
	
	@PostMapping(value = "/updateOccupation", consumes = "application/json", produces = "application/json")
    public String updateOccupation(@RequestBody OccupationVO occupation) {
		logger.debug("updated occupation with occupationId : {}" + occupation.getOccupationId());
		OccupationMst occupationMst = OccupationService.getByOccupationId(occupation.getOccupationId());
		if (null != occupationMst) {		
			Date dt = new Date();
			occupationMst.setLastModifiedBy("login2");
			occupationMst.setLastModifiedDate(dt);
			occupationMst.setLastModifiedTime(dt);
			occupationMst.setAuthStatus(occupation.getAuthStatus());
			occupationMst.setIsActive(Integer.parseInt(occupation.getIsActive()));
			occupationMst.setIsDeleted(occupation.getIsDeleted().equals("1")?true:false);		
			occupationMst.setOccupationDisplayName(occupation.getOccupationDisplayName());
			occupationMst.setOccupationId(occupation.getOccupationId());
			occupationMst.setOccupationName(occupation.getOccupationName());
			
			OccupationMst occupationMstNew = OccupationService.saveOrUpdate("login", occupationMst);
			if (null != occupationMstNew) {
				OccupationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.debug("occupation && cache updated for {}" + occupation.getOccupationId());
				return "Successfully updated Record";
			} else {
				logger.debug("failed to update occupation with id" + occupation.getOccupationId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
    }
	


	@GetMapping(value = "/id/{id}", produces = "application/json")
    public OccupationVO getOccupationById(@PathVariable String id) {
		logger.info("Fetch occupation by occupationcityId : {}", id);
		OccupationMst occupationMst = OccupationService.getByOccupationId(id);	
		OccupationVO occupation = null;
		if (null != occupationMst) {
			occupation = new OccupationVO();
			occupation.setOccupationId(occupationMst.getOccupationId());
			occupation.setOccupationName(occupationMst.getOccupationName());
			occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
			occupation.setAuthStatus(occupationMst.getAuthStatus());
			occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));					
			occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
		}
		logger.info("Exit from method");
        return occupation;
    }
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public OccupationVO getOccupationByName(@PathVariable String name) {
		logger.info("Fetch occupation by occupationcityName : {}", name);
		String occupationId = OccupationService.getOccupationIdByOccupationName(name);
		OccupationMst occupationMst = OccupationService.getByOccupationId(occupationId);
	
		OccupationVO occupation = null;
		if (null != occupationMst) {
			logger.info("Inside if Condition");
			occupation = new OccupationVO();
			occupation.setOccupationId(occupationMst.getOccupationId());
			occupation.setOccupationName(occupationMst.getOccupationName());
			occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
			occupation.setAuthStatus(occupationMst.getAuthStatus());
			occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));					
			occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
		}
        return occupation;
    }
	 

	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public OccupationVOList getOccupationByAuthorizedStatus() { 
		logger.info("Fetch occupation by AuthStatus : A");
		List<OccupationMst> occupationList = OccupationService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		OccupationVOList voList = new OccupationVOList();
		List<OccupationVO> occupationVOList = new ArrayList<OccupationVO>();  	
		if (null != occupationList) {
			for (OccupationMst occupationMst: occupationList) {
				OccupationVO occupation = new OccupationVO();
				occupation.setOccupationId(occupationMst.getOccupationId());
				occupation.setOccupationName(occupationMst.getOccupationName());
				occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
				occupation.setAuthStatus(occupationMst.getAuthStatus());
				occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));				
				occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
				
				occupationVOList.add(occupation);
			}
		}
		voList.setOccupation(occupationVOList);
        return voList;  
    }

	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public OccupationVOList getOccupationByPendingStatus() { 
		logger.info("Fetch occupation by AuthStatus : P");
		List<OccupationMst> occupationList = OccupationService.getByAuthStatus(OmniConstants.AUTH_PENDING,Boolean.FALSE);
		OccupationVOList voList = new OccupationVOList();
		List<OccupationVO> occupationVOList = new ArrayList<OccupationVO>();  
		
		if (null != occupationList) {
			for (OccupationMst occupationMst: occupationList) {
				OccupationVO occupation = new OccupationVO();
				occupation.setOccupationId(occupationMst.getOccupationId());
				occupation.setOccupationName(occupationMst.getOccupationName());
				occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
				occupation.setAuthStatus(occupationMst.getAuthStatus());
				occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));				
				occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
				
				occupationVOList.add(occupation);
			}
		}
		voList.setOccupation(occupationVOList);
        return voList;  
    }

	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public OccupationVOList getOccupationByRejectedStatus() { 
		logger.info("Fetch occupation by AuthStatus : R");
		List<OccupationMst> occupationList = OccupationService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		OccupationVOList voList = new OccupationVOList();
		List<OccupationVO> occupationVOList = new ArrayList<OccupationVO>();  
		
		if (null != occupationList) {
			for (OccupationMst occupationMst: occupationList) {
				OccupationVO occupation = new OccupationVO();
				occupation.setOccupationId(occupationMst.getOccupationId());
				occupation.setOccupationName(occupationMst.getOccupationName());
				occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
				occupation.setAuthStatus(occupationMst.getAuthStatus());
				occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));				
				occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
				occupationVOList.add(occupation);
			}
		}
		voList.setOccupation(occupationVOList);
        return voList;  
    }

		
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public OccupationVOList getOccupationByDeleted() { 
		logger.info("Fetch occupation by AuthStatus : D");
		List<OccupationMst> occupationList = OccupationService.getByDeleted(Boolean.TRUE);
		OccupationVOList voList = new OccupationVOList();
		List<OccupationVO> occupationVOList = new ArrayList<OccupationVO>();  
		
		if (null != occupationList) {
			for (OccupationMst occupationMst: occupationList) {
				OccupationVO occupation = new OccupationVO();
				occupation.setOccupationId(occupationMst.getOccupationId());
				occupation.setOccupationName(occupationMst.getOccupationName());
				occupation.setOccupationDisplayName(occupationMst.getOccupationDisplayName());
				occupation.setAuthStatus(occupationMst.getAuthStatus());
				occupation.setIsActive(Integer.toString(occupationMst.getIsActive()));				
				occupation.setIsDeleted(String.valueOf(occupationMst.getIsDeleted()));
				
				occupationVOList.add(occupation);
			}
		}
		voList.setOccupation(occupationVOList);
        return voList;  
    }

	
	@PostMapping(value = "/deleteOccupation/{id}", consumes = "application/json", produces = "application/json")
    public String deleteOccupation(@PathVariable(name="id") String occupationId) {
		logger.debug("Delete occupation by occupationId : {}", occupationId);
		OccupationMst occupationMst = OccupationService.getByOccupationId(occupationId);
		
		if (null != occupationMst ) {
			logger.info("Inside if condition");
			Date dt = new Date();
			occupationMst.setDeletedBy("login3");
			occupationMst.setDeletedDate(dt);
			occupationMst.setDeletedTime(dt);
			occupationMst.setAuthStatus("D");
			occupationMst.setIsActive(0);
			occupationMst.setIsDeleted(Boolean.TRUE);
			
			OccupationMst occupationMstNew = OccupationService.saveOrUpdate("login", occupationMst);
			if (null != occupationMstNew) {
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
