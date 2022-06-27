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

import com.itl.domain.entities.masters.SalutationMst;
import com.itl.service.masters.SalutationService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.SalutationVO;
import com.itl.web.dto.SalutationVOList;

@RestController
@RequestMapping("/Salutation")
public class SalutationController {

	private static final Logger logger = LoggerFactory.getLogger(SalutationController.class);

	@Autowired
	private SalutationService SalutationService;

	@PostMapping(value = "/createSalutation", consumes = "application/json", produces = "application/json")
	public String createSalutation(@RequestBody SalutationVO salutation) {
		logger.debug("Add Salutation for SalutationId : {}" + salutation.getSalutationId());
		SalutationMst salutationMst = SalutationService.getBySalutationId(salutation.getSalutationId());
		logger.info("After Service call");
		if (null != salutationMst ) {
			logger.info("Salutation with SalutationId {} already exists.", salutation.getSalutationId());
			return "Failure.. Record already exists";
		} else {
			
			salutationMst = new SalutationMst();
			Date dt = new Date();
			salutationMst.setCreatedBy("login");
			salutationMst.setCreatedDate(dt);
			salutationMst.setCreatedTime(dt);
			salutationMst.setLastModifiedBy("login");
			salutationMst.setLastModifiedDate(dt);
			salutationMst.setLastModifiedTime(dt);
			salutationMst.setAuthStatus("A");
			salutationMst.setIsActive(1);
			salutationMst.setIsDeleted(Boolean.FALSE);
			salutationMst.setSalutationId(salutation.getSalutationId());
			salutationMst.setSalutationName(salutation.getSalutationName());
			salutationMst.setSalutationDisplayName(salutation.getSalutationDisplayName());

			SalutationMst salutationMstNew = SalutationService.saveOrUpdate("login", salutationMst);
			if (null != salutationMstNew) {
				SalutationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Salutation & Cache - {} - saved Successfully", salutation.getSalutationId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save country - {}", salutation.getSalutationId());
				logger.info("Exit from method");
				return "Failure while saving record";
			}
		}
	}
	
	
	
	@PostMapping(value = "/updateSalutation", consumes = "application/json", produces = "application/json")
    public String updateSalutation(@RequestBody SalutationVO salutation) {
		logger.info("Inside updateState method " + salutation);
		SalutationMst salutationMst = SalutationService.getBySalutationId(salutation.getSalutationId());
		logger.debug("After Service call");
		if (null != salutationMst){
			Date dt = new Date();
			salutationMst.setLastModifiedBy("login2");
			salutationMst.setLastModifiedDate(dt);
			salutationMst.setLastModifiedTime(dt);
			salutationMst.setAuthStatus(salutation.getAuthStatus());
			salutationMst.setIsActive(Integer.parseInt(salutation.getIsActive()));
			salutationMst.setIsDeleted(salutation.getIsDeleted().equals("1")?true:false);			
			salutationMst.setSalutationDisplayName(salutation.getSalutationDisplayName());
			salutationMst.setSalutationId(salutation.getSalutationId());
			salutationMst.setSalutationName(salutation.getSalutationName());
			
			SalutationMst salutationMstNew = SalutationService.saveOrUpdate("login", salutationMst);
			if (null != salutationMstNew) {
				SalutationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Salutation & Cache updated successfully for {}", salutation.getSalutationId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update Salutation with id : {}", salutation.getSalutationId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
    }
	
	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
	public SalutationVO getSalutationById(@PathVariable String id) {
		logger.info("Inside getSalutationById method");
		SalutationMst salutationMst = SalutationService.getBySalutationId(id);
		SalutationVO salutation = null;
		if (null != salutationMst) {
			salutation = new SalutationVO();
			salutation.setSalutationId(salutationMst.getSalutationId());
			salutation.setSalutationName(salutationMst.getSalutationName());
			salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
			salutation.setAuthStatus(salutationMst.getAuthStatus());
			salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));					
			salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));
		}
		return salutation;
	}
	

	@GetMapping(value = "/name/{name}", produces = "application/json")
	public SalutationVO getSalutationByName(@PathVariable String name) {
		logger.info("Fetch Salutation by SalutationName : {}", name);
		String SalutationId=SalutationService.getSalutationIdBySalutationName(name);
		SalutationMst salutationMst = SalutationService.getBySalutationId(SalutationId);		
		SalutationVO salutation = null;
		if (null != salutationMst) {
			salutation = new SalutationVO();
			salutation.setSalutationId(salutationMst.getSalutationId());
			salutation.setSalutationName(salutationMst.getSalutationName());
			salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
			salutation.setAuthStatus(salutationMst.getAuthStatus());
			salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));					
			salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));
		}

		return salutation;
	}

	@GetMapping(value = "/list/authorized", produces = "application/json")
	public SalutationVOList getSalutationByAuthorizedStatus() {
		logger.info("Fetch salutation by AuthStatus : A");
		List<SalutationMst> salutationList = SalutationService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		SalutationVOList voList = new SalutationVOList();
		List<SalutationVO> salutationVOList = new ArrayList<SalutationVO>();

		if (null != salutationList) {
			logger.info("Inside if loop");
			for (SalutationMst salutationMst : salutationList) {
				logger.info("Inside for loop");
				SalutationVO salutation = new SalutationVO();
				salutation.setSalutationId(salutationMst.getSalutationId());
				salutation.setSalutationName(salutationMst.getSalutationName());
				salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
				salutation.setAuthStatus(salutationMst.getAuthStatus());
				salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));				
				salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));

				salutationVOList.add(salutation);
			}
		}
		voList.setSalutation(salutationVOList);
		logger.debug("Exit from getSalutationByAuthorizedStatus method");
		return voList;
	}
	

	@GetMapping(value = "/list/pending", produces = "application/json")
	public SalutationVOList getSalutationByPendingStatus() {
		logger.info("Fetch salutation by AuthStatus : P");
		List<SalutationMst> salutationList = SalutationService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		SalutationVOList voList = new SalutationVOList();
		List<SalutationVO> salutationVOList = new ArrayList<SalutationVO>();

		if (null != salutationList) {
			logger.info("Inside if loop");
			for (SalutationMst salutationMst : salutationList) {
				logger.info("Inside for loop");
				SalutationVO salutation = new SalutationVO();
				salutation.setSalutationId(salutationMst.getSalutationId());
				salutation.setSalutationName(salutationMst.getSalutationName());
				salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
				salutation.setAuthStatus(salutationMst.getAuthStatus());
				salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));				
				salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));

				salutationVOList.add(salutation);
			}
		}
		voList.setSalutation(salutationVOList);
		logger.debug("Exit from getSalutationByPendingStatus method");
		return voList;
	}

	@GetMapping(value = "/list/rejected", produces = "application/json")
	public SalutationVOList getSalutationByRejectedStatus() {
		logger.info("Fetch salutation by AuthStatus : R");
		List<SalutationMst> salutationList = SalutationService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		SalutationVOList voList = new SalutationVOList();
		List<SalutationVO> salutationVOList = new ArrayList<SalutationVO>();

		if (null != salutationList) {
			logger.info("Inside if loop");
			for (SalutationMst salutationMst : salutationList) {
				logger.info("Inside for loop");
				SalutationVO salutation = new SalutationVO();
				salutation.setSalutationId(salutationMst.getSalutationId());
				salutation.setSalutationName(salutationMst.getSalutationName());
				salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
				salutation.setAuthStatus(salutationMst.getAuthStatus());
				salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));				
				salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));

				salutationVOList.add(salutation);
			}
		}
		voList.setSalutation(salutationVOList);
		logger.debug("Exit from getSalutationByRejectedStatus  method");
		return voList;
	}

	@GetMapping(value = "/list/deleted", produces = "application/json")
	public SalutationVOList getSalutationByDeleted() {
		logger.info("Fetch salutation by AuthStatus : D");
		List<SalutationMst> salutationList = SalutationService.getByDeleted(Boolean.TRUE);
		SalutationVOList voList = new SalutationVOList();
		List<SalutationVO> salutationVOList = new ArrayList<SalutationVO>();

		if (null != salutationList) {
			logger.info("Inside if loop");
			for (SalutationMst salutationMst : salutationList) {
				logger.info("Inside for loop");
				SalutationVO salutation = new SalutationVO();
				salutation.setSalutationId(salutationMst.getSalutationId());
				salutation.setSalutationName(salutationMst.getSalutationName());
				salutation.setSalutationDisplayName(salutationMst.getSalutationDisplayName());
				salutation.setAuthStatus(salutationMst.getAuthStatus());
				salutation.setIsActive(Integer.toString(salutationMst.getIsActive()));				
				salutation.setIsDeleted(String.valueOf(salutationMst.getIsDeleted()));

				salutationVOList.add(salutation);
			}
		}
		voList.setSalutation(salutationVOList);
		logger.debug("Exit from getSalutationByDeleted method");
		return voList;
	}

	
	@PostMapping(value = "/deleteSalutation/{id}", consumes = "application/json", produces = "application/json")
	public String deleteSalutation(@PathVariable (name="id") String salutationId) {
		logger.debug("Inside deleteSalutation method");
		SalutationMst salutationMst = SalutationService.getBySalutationId(salutationId);

		if (null != salutationMst) {		
			Date dt = new Date();
			salutationMst.setDeletedBy("login3");
			salutationMst.setDeletedDate(dt);
			salutationMst.setDeletedTime(dt);
			salutationMst.setAuthStatus("A");
			salutationMst.setIsActive(0);
			salutationMst.setIsDeleted(Boolean.TRUE);

			SalutationMst salutationMstNew = SalutationService.saveOrUpdate("login", salutationMst);
			if (null != salutationMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteSalutation  method");
			return "Failure.. Record does not exists";
		}
	}
}
