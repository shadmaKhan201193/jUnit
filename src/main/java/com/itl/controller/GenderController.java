package com.itl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itl.domain.entities.masters.GenderMst;
import com.itl.service.masters.GenderService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.GenderVO;
import com.itl.web.dto.GenderVOList;

@RestController
@RequestMapping("/gender")
@CrossOrigin(origins ="*",maxAge =3600)
public class GenderController {
	private static final Logger logger = LoggerFactory.getLogger(GenderController.class);
	@Autowired
	private GenderService GenderService;
	
	@PostMapping(value = "/createGender", consumes = "application/json", produces = "application/json")
	public String createGende(@RequestBody GenderVO gender) {
		logger.debug("Add gender for genderId : {}" + gender.getGenderId());
		GenderMst genderMst = GenderService.getByGenderId(gender.getGenderId());
		if (null != genderMst) {
			logger.info("gender with genderId {} already exists.", gender.getGenderId());
			return "Failure.. Record already exists";
		} else {
			genderMst = new GenderMst();
			Date dt = new Date();
			genderMst.setCreatedBy("login");
			genderMst.setCreatedDate(dt);
			genderMst.setCreatedTime(dt);
			genderMst.setLastModifiedBy("login");
			genderMst.setLastModifiedDate(dt);
			genderMst.setLastModifiedTime(dt);
			genderMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			genderMst.setIsActive(1);
			genderMst.setIsDeleted(Boolean.FALSE);
			genderMst.setGenderId(gender.getGenderId());
			genderMst.setGenderName(gender.getGenderName());
			genderMst.setGenderDisplayName(gender.getGenderDisplayName());	

			GenderMst genderMstNew = GenderService.saveOrUpdate("login", genderMst);
			if (null != genderMstNew) {
				GenderService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("gender & Cache - {} - saved Successfully", gender.getGenderId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save gender - {}", gender.getGenderId());
				return "Failure while saving record";
			}
		}
	}
	
	@PostMapping(value = "/updateGender", consumes = "application/json", produces = "application/json")
	public String updateGender(@RequestBody GenderVO gender) {
		logger.debug("Updating gender with genderId : {}" + gender.getGenderId());
		GenderMst genderMst = GenderService.getByGenderId(gender.getGenderId());
		if (null != genderMst) {
			Date dt = new Date();
			genderMst.setLastModifiedBy("login2");
			genderMst.setLastModifiedDate(dt);
			genderMst.setLastModifiedTime(dt);
			genderMst.setAuthStatus(gender.getAuthStatus());
			genderMst.setIsActive(Integer.parseInt(gender.getIsActive()));
			genderMst.setIsDeleted(gender.getIsDeleted().equals("1") ? true : false);
			genderMst.setGenderId(gender.getGenderId());
			genderMst.setGenderName(gender.getGenderName());
			genderMst.setGenderDisplayName(gender.getGenderDisplayName());

			GenderMst genderMstNew = GenderService.saveOrUpdate("login", genderMst);
			if (null != genderMstNew) {
				GenderService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("gender & Cache updated successfully for {}", gender.getGenderId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update gender with id : {}", gender.getGenderId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
	}
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
	public GenderVO getGenderById(@PathVariable String id) {
		logger.info("Fetch gender by genderId : {}", id);
		GenderMst genderMst = GenderService.getByGenderId(id);
		GenderVO gender = null;
		if (null != genderMst) {
			gender = new GenderVO();
			gender.setGenderId(genderMst.getGenderId());
			gender.setGenderName(genderMst.getGenderName());
			gender.setGenderDisplayName(genderMst.getGenderDisplayName());
			gender.setAuthStatus(genderMst.getAuthStatus());
			gender.setIsActive(Integer.toString(genderMst.getIsActive()));
			gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the gender is not found
		return gender;
	}

	
	@GetMapping(value = "/name/{name}", produces = "application/json")
	public GenderVO getGenderByName(@PathVariable String name) {
		logger.info("Fetch gender by genderName : {}", name);
		String genderId = GenderService.getGenderIdByGenderName(name);
		GenderMst genderMst = GenderService.getByGenderId(genderId);
		GenderVO gender = null;
		if (null != genderMst) {
			gender = new GenderVO();
			gender.setGenderId(genderMst.getGenderId());
			gender.setGenderName(genderMst.getGenderName());
			gender.setGenderDisplayName(genderMst.getGenderDisplayName());
			gender.setAuthStatus(genderMst.getAuthStatus());
			gender.setIsActive(Integer.toString(genderMst.getIsActive()));
			gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
		}
		//TODO - add else part to reply countries not found
		return gender;
	}
	
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public GenderVOList getGenderByAuthorizedStatus() { 
		logger.debug("Inside getGenderByAuthorizedStatus method");
		List<GenderMst> genderList = GenderService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, Boolean.FALSE);
		GenderVOList voList = new GenderVOList();
		List<GenderVO> genderVOList = new ArrayList<GenderVO>();   
		 
		if (null != genderList) {
			logger.debug("Inside if loop");
			for (GenderMst genderMst: genderList) {
				logger.debug("Inside for loop");
				GenderVO gender = new GenderVO();
				gender.setGenderId(genderMst.getGenderId());
				gender.setGenderName(genderMst.getGenderName());
				gender.setGenderDisplayName(genderMst.getGenderDisplayName());
				gender.setAuthStatus(genderMst.getAuthStatus());
				gender.setIsActive(Integer.toString(genderMst.getIsActive()));				
				gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
				
				genderVOList.add(gender);
			}
		}
		voList.setGender(genderVOList);
		logger.info("Exit from getGenderByAuthorizedStatus method.. voList= "+ voList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public GenderVOList getGenderByPendingStatus() { 
		logger.debug("Inside getGenderByPendingStatus method");
		List<GenderMst> genderList = GenderService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		GenderVOList voList = new GenderVOList();
		List<GenderVO> genderVOList = new ArrayList<GenderVO>();  
		 
		if (null != genderList) {
			logger.debug("Inside if loop");
			for (GenderMst genderMst: genderList) {
				logger.debug("Inside for loop");
				GenderVO gender = new GenderVO();
				gender.setGenderId(genderMst.getGenderId());
				gender.setGenderName(genderMst.getGenderName());
				gender.setGenderDisplayName(genderMst.getGenderDisplayName());
				gender.setAuthStatus(genderMst.getAuthStatus());
				gender.setIsActive(Integer.toString(genderMst.getIsActive()));				
				gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
				
				genderVOList.add(gender);
			}
		}
		voList.setGender(genderVOList);
		logger.info("Exit from getGenderByPendingStatus method ..voList= "+ voList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public GenderVOList getGenderByRejectedStatus() { 
		logger.debug("Inside getGenderByAuthorizedStatus method");
		List<GenderMst> genderList = GenderService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		GenderVOList voList = new GenderVOList();
		List<GenderVO> genderVOList = new ArrayList<GenderVO>();  
		 
		if (null != genderList) {
			logger.debug("Inside if loop");
			for (GenderMst genderMst: genderList) {
				logger.debug("Inside for loop");
				GenderVO gender = new GenderVO();
				gender.setGenderId(genderMst.getGenderId());
				gender.setGenderName(genderMst.getGenderName());
				gender.setGenderDisplayName(genderMst.getGenderDisplayName());
				gender.setAuthStatus(genderMst.getAuthStatus());
				gender.setIsActive(Integer.toString(genderMst.getIsActive()));				
				gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
				
				genderVOList.add(gender);
			}
		}
		voList.setGender(genderVOList);
		logger.info("Exit from getGenderByRejectedStatus method ..voList= "+voList);
        return voList;  
    }
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public GenderVOList getGenderByDeleted() { 
		logger.debug("Inside getGenderByDeleted method");
		List<GenderMst> genderList = GenderService.getByDeleted(Boolean.TRUE);
		GenderVOList voList = new GenderVOList();
		List<GenderVO> genderVOList = new ArrayList<GenderVO>();  
		
		if (null != genderList) {
			logger.debug("Inside if loop");
			for (GenderMst genderMst: genderList) {
				logger.debug("Inside for loop");
				GenderVO gender = new GenderVO();
				gender.setGenderId(genderMst.getGenderId());
				gender.setGenderName(genderMst.getGenderName());
				gender.setGenderDisplayName(genderMst.getGenderDisplayName());
				gender.setAuthStatus(genderMst.getAuthStatus());
				gender.setIsActive(Integer.toString(genderMst.getIsActive()));				
				gender.setIsDeleted(String.valueOf(genderMst.getIsDeleted()));
				
				genderVOList.add(gender);
			}
		}
		voList.setGender(genderVOList);
		logger.info("Exit from getGenderByDeleted method ...voList= "+voList);
        return voList;  
    }
	
	
	@PostMapping(value = "/deleteGender/{id}", consumes = "application/json", produces = "application/json")
    public String deleteGender(@PathVariable(name="id")  String genderId) {
		logger.debug("Delete Gender by GenderId : {}", genderId);
		GenderMst genderMst = GenderService.getByGenderId(genderId);

		if (null != genderMst) {
			Date dt = new Date();
			genderMst.setDeletedBy("login3");
			genderMst.setDeletedDate(dt);
			genderMst.setDeletedTime(dt);
			genderMst.setAuthStatus("D");
			genderMst.setIsActive(0);
			genderMst.setIsDeleted(Boolean.TRUE);

			GenderMst genderMstNew = GenderService.saveOrUpdate("login", genderMst);

			if (null != genderMstNew) {
				logger.debug("Record & Cache deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from method");
			return "Failure.. Record does not exists";
		}
	}
	
	
	
}
