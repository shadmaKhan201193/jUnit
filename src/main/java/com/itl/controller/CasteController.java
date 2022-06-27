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

import com.itl.domain.entities.masters.CasteMst;

import com.itl.service.masters.CasteService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.CasteVO;
import com.itl.web.dto.CasteVOList;



@RestController
@RequestMapping("/Caste")
public class CasteController {
	
private static final Logger logger = LoggerFactory.getLogger(CasteController.class);
	
	@Autowired
	private CasteService CasteService;
	
	@PostMapping(value = "/createCaste", consumes = "application/json", produces = "application/json")
	public String createCaste(@RequestBody CasteVO caste) {
		logger.debug("Add caste for casteId : {}" + caste.getCasteId());
		CasteMst casteMst = CasteService.getByCasteId(caste.getCasteId());
		if (null != casteMst) {
			logger.info("caste with casteId {} already exists.", caste.getCasteId());
			return "Failure.. Record already exists";
		} else {
			casteMst = new CasteMst();
			Date dt = new Date();
			casteMst.setCreatedBy("login");
			casteMst.setCreatedDate(dt);
			casteMst.setCreatedTime(dt);
			casteMst.setLastModifiedBy("login");
			casteMst.setLastModifiedDate(dt);
			casteMst.setLastModifiedTime(dt);
			casteMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			casteMst.setIsActive(1);
			casteMst.setIsDeleted(Boolean.FALSE);
			casteMst.setCasteDisplayName(caste.getCasteDisplayName());
			casteMst.setCasteId(caste.getCasteId());
			casteMst.setCasteName(caste.getCasteName());
			casteMst.setReligionId(caste.getReligionId());

			CasteMst casteMstNew = CasteService.saveOrUpdate("login", casteMst);
			if (null != casteMstNew) {
				CasteService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("caste & Cache - {} - saved Successfully", caste.getCasteId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save caste - {}", caste.getCasteId());
				return "Failure while saving record";
			}
		}
	}

	
	@PostMapping(value = "/updateCaste", consumes = "application/json", produces = "application/json")
    public String updateCaste(@RequestBody CasteVO caste) {
		logger.debug("Updating caste with casteId : {}" + caste.getCasteId());
		CasteMst casteMst = CasteService.getByCasteId(caste.getCasteId());
		logger.debug("After Service call");
		if (null != casteMst) {
			logger.info("Inside if  Condition");			
			Date dt = new Date();
			casteMst.setLastModifiedBy("login2");
			casteMst.setLastModifiedDate(dt);
			casteMst.setLastModifiedTime(dt);
			casteMst.setAuthStatus(caste.getAuthStatus());
			casteMst.setIsActive(Integer.parseInt(caste.getIsActive()));
			casteMst.setIsDeleted(caste.getIsDeleted().equals("1")?true:false);				
			casteMst.setCasteDisplayName(caste.getCasteDisplayName());
			casteMst.setCasteId(caste.getCasteId());
			casteMst.setCasteName(caste.getCasteName());
			casteMst.setReligionId(caste.getReligionId());
			
			CasteMst casteMstNew = CasteService.saveOrUpdate("login", casteMst);
			if (null != casteMstNew) {
				CasteService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("Caste & Cache updated successfully for {}", caste.getCasteId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update Caste with id : {}", caste.getCasteId());
				return "Failure while updating record";
			}
		} else {
			logger.debug("Exit from method");
			return "Failure.. Record does not exists";
		}
    }
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public CasteVO getCasteById(@PathVariable String id) {
		logger.info("Fetch caste by casteId : {}", id);
		CasteMst casteMst = CasteService.getByCasteId(id);	
		CasteVO caste = null;		
		if (null != casteMst) {
			caste = new CasteVO();
			caste.setCasteId(casteMst.getCasteId());
			caste.setCasteName(casteMst.getCasteName());
			caste.setCasteDisplayName(casteMst.getCasteDisplayName());
			caste.setReligionId(casteMst.getReligionId());
			caste.setAuthStatus(casteMst.getAuthStatus());
			caste.setIsActive(Integer.toString(casteMst.getIsActive()));				
			caste.setIsDeleted(String.valueOf(casteMst.getIsDeleted()));
		}
        return caste;
    }
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public CasteVO getCasteByName(@PathVariable String name) {
		logger.info("Fetch caste by casteId : {}", name);
		String casteId = CasteService.getCasteIdByCasteName(name);
		CasteMst casteMst = CasteService.getByCasteId(casteId);
		CasteVO caste = null;		
		if (null != casteMst) {			
			caste = new CasteVO();
			caste.setCasteId(casteMst.getCasteId());
			caste.setCasteName(casteMst.getCasteName());
			caste.setCasteDisplayName(casteMst.getCasteDisplayName());
			caste.setReligionId(casteMst.getReligionId());
			caste.setAuthStatus(casteMst.getAuthStatus());
			caste.setIsActive(Integer.toString(casteMst.getIsActive()));				
			caste.setIsDeleted(String.valueOf(casteMst.getIsDeleted()));
		}
	
        return caste;
    }
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public CasteVOList getCasteByAuthorizedStatus() { 
		logger.info("Fetch caste by AuthStatus : A");
		List<CasteMst> casteList = CasteService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		CasteVOList voList = new CasteVOList();
		List<CasteVO> casteVOList = new ArrayList<CasteVO>();  
		
		if (null != casteList) {			
			for (CasteMst casteMst: casteList) {				
				CasteVO caste = new CasteVO();
				caste.setCasteId(casteMst.getCasteId());
				caste.setCasteName(casteMst.getCasteName());
				caste.setCasteDisplayName(casteMst.getCasteDisplayName());
				caste.setReligionId(casteMst.getReligionId());
				caste.setAuthStatus(casteMst.getAuthStatus());
				caste.setIsActive(Integer.toString(casteMst.getIsActive()));				
				caste.setIsDeleted(String.valueOf(casteMst.getIsDeleted()));
				
				casteVOList.add(caste);
			}
		}
		voList.setCaste(casteVOList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public CasteVOList getCasteByPendingStatus() { 
		logger.info("Fetch countries by AuthStatus : P");
		List<CasteMst> casteList = CasteService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		CasteVOList voList = new CasteVOList();
		List<CasteVO> casteVOList = new ArrayList<CasteVO>();  
		
		if (null != casteList) {		
			for (CasteMst castemst: casteList) {			
				CasteVO caste = new CasteVO();
				caste.setCasteId(castemst.getCasteId());
				caste.setCasteName(castemst.getCasteName());
				caste.setCasteDisplayName(castemst.getCasteDisplayName());
				caste.setReligionId(castemst.getReligionId());
				caste.setAuthStatus(castemst.getAuthStatus());
				caste.setIsActive(Integer.toString(castemst.getIsActive()));				
				caste.setIsDeleted(String.valueOf(castemst.getIsDeleted( )));
				
				casteVOList.add(caste);
			}
		}
		voList.setCaste(casteVOList);	
        return voList;  
    }

	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public CasteVOList getCasteByRejectedStatus() { 
		logger.info("Fetch countries by AuthStatus : R");
		List<CasteMst> casteList = CasteService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		CasteVOList voList = new CasteVOList();
		List<CasteVO> casteVOList = new ArrayList<CasteVO>();  
		
		if (null != casteList) {			
			for (CasteMst casteMst: casteList) {			
				CasteVO caste = new CasteVO();
				caste.setCasteId(casteMst.getCasteId());
				caste.setCasteName(casteMst.getCasteName());
				caste.setCasteDisplayName(casteMst.getCasteDisplayName());
				caste.setReligionId(casteMst.getReligionId());
				caste.setAuthStatus(casteMst.getAuthStatus());
				caste.setIsActive(Integer.toString(casteMst.getIsActive()));				
				caste.setIsDeleted(String.valueOf(casteMst.getIsDeleted()));
				
				casteVOList.add(caste);
			}
		}
		voList.setCaste(casteVOList);	
        return voList;  
    }
	
	
	

	@GetMapping(value = "/list/deleted", produces = "application/json")
    public CasteVOList getCasteByDeleted(Boolean isDeleted) { 
		logger.info("Fetch countries by AuthStatus : D");
		List<CasteMst> casteList = CasteService.getByDeleted(isDeleted);
		CasteVOList voList = new CasteVOList();
		List<CasteVO> casteVOList = new ArrayList<CasteVO>();  		
		if (null != casteList) {		
			for (CasteMst casteMst: casteList) {			
				CasteVO caste = new CasteVO();
				caste.setCasteId(casteMst.getCasteId());
				caste.setCasteName(casteMst.getCasteName());
				caste.setCasteDisplayName(casteMst.getCasteDisplayName());
				caste.setReligionId(casteMst.getReligionId());
				caste.setAuthStatus(casteMst.getAuthStatus());
				caste.setIsActive(Integer.toString(casteMst.getIsActive()));				
				caste.setIsDeleted(String.valueOf(casteMst.getIsDeleted()));
				
				casteVOList.add(caste);
			}
		}
		voList.setCaste(casteVOList);		
        return voList;  
    }
	
	
	@PostMapping(value = "/deleteCaste/{id}", consumes = "application/json", produces = "application/json")
    public String deleteCaste(@PathVariable(name="id") String casteId) {
		logger.debug("Delete caste by casteId : {}", casteId);		
		CasteMst casteMst = CasteService.getByCasteId(casteId);
		
		if (null != casteMst) {					
			Date dt = new Date();
			casteMst.setDeletedBy("login3");
			casteMst.setDeletedDate(dt);
			casteMst.setDeletedTime(dt);
			casteMst.setAuthStatus("D");
			casteMst.setIsActive(0);
			casteMst.setIsDeleted(Boolean.TRUE);
			
			CasteMst casteMstNew = CasteService.saveOrUpdate("login", casteMst);
			if (null != casteMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteCaste  method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	
	

}
