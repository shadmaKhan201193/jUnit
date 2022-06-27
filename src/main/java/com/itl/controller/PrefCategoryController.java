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
import com.itl.domain.entities.masters.PrefCategoryMst;
import com.itl.service.masters.PrefCategoryService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.PrefCategoryVO;
import com.itl.web.dto.PrefCategoryVOList;

@RestController
@RequestMapping("/PrefCategory")
public class PrefCategoryController {
	

private static final Logger logger = LoggerFactory.getLogger(PrefCategoryController.class);
	
	@Autowired
	private PrefCategoryService PrefCategoryService;
	
	@PostMapping(value = "/createPrefCategory", consumes = "application/json", produces = "application/json")
    public String createPrefCategory(@RequestBody PrefCategoryVO prefCategory) {
		logger.debug("Add prefCategory for prefCategoryId : {}" + prefCategory.getPrefCategoryId());
		PrefCategoryMst prefCategoryMst = PrefCategoryService.getByPrefCategoryId(prefCategory.getPrefCategoryId());
		logger.info("After Service call");
		if (null != prefCategoryMst) {
			logger.info("prefCategory with prefCategoryId {} already exists.", prefCategory.getPrefCategoryId());
			return "Failure.. Record already exists";
		} else {
			
			prefCategoryMst = new PrefCategoryMst();
			Date dt = new Date();
			prefCategoryMst.setCreatedBy("login");
			prefCategoryMst.setCreatedDate(dt);
			prefCategoryMst.setCreatedTime(dt);
			prefCategoryMst.setLastModifiedBy("login");
			prefCategoryMst.setLastModifiedDate(dt);
			prefCategoryMst.setLastModifiedTime(dt);
			prefCategoryMst.setAuthStatus("A");
			prefCategoryMst.setIsActive(1);
			prefCategoryMst.setIsDeleted(Boolean.FALSE);
			prefCategoryMst.setPrefCategoryId(prefCategory.getPrefCategoryId());
			prefCategoryMst.setPrefCategoryName(prefCategory.getPrefCategoryName());
			prefCategoryMst.setPrefCategoryDisplayName(prefCategory.getPrefCategoryDisplayName());
			
			
			PrefCategoryMst prefCategoryMstNew = PrefCategoryService.saveOrUpdate("login", prefCategoryMst);
			if (null != prefCategoryMstNew) {
				PrefCategoryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);	
				logger.info("prefCategory & Cache - {} - saved Successfully", prefCategory.getPrefCategoryId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save prefCategory - {}", prefCategory.getPrefCategoryId());
				return "Failure while saving record";
			}
		}
    }
	
	
	
	@PostMapping(value = "/updatePrefCategory", consumes = "application/json", produces = "application/json")
    public String updatePrefCategory(@RequestBody PrefCategoryVO prefCategory) {
		logger.debug("Updating prefCategory with prefCategoryId : {}" + prefCategory.getPrefCategoryId());
		PrefCategoryMst prefCategoryMst= PrefCategoryService.getByPrefCategoryId(prefCategory.getPrefCategoryId());
		
		if (null != prefCategoryMst ) {
			Date dt = new Date();
			prefCategoryMst.setLastModifiedBy("login2");
			prefCategoryMst.setLastModifiedDate(dt); 
			prefCategoryMst.setLastModifiedTime(dt);
			prefCategoryMst.setAuthStatus(prefCategory.getAuthStatus());
			prefCategoryMst.setIsActive(Integer.parseInt(prefCategory.getIsActive()));
			prefCategoryMst.setIsDeleted(prefCategory.getIsDeleted().equals("1")?true:false);	
			prefCategoryMst.setPrefCategoryId(prefCategory.getPrefCategoryId());
			prefCategoryMst.setPrefCategoryName(prefCategory.getPrefCategoryName());
			prefCategoryMst.setPrefCategoryDisplayName(prefCategory.getPrefCategoryDisplayName());

			PrefCategoryMst prefCategoryMstNew = PrefCategoryService.saveOrUpdate("login", prefCategoryMst);
			
			if (null != prefCategoryMstNew) {
				PrefCategoryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("PrefCategory & Cache updated successfully for {}",  prefCategory.getPrefCategoryId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update prefCategory with id : {}",  prefCategory.getPrefCategoryId());
				return "Failure while updating record";
			}
		} else {
			
			return "Failure.. Record does not exists";
		}
    }
	
	
	

	@GetMapping(value = "/id/{id}", produces = "application/json")
    public PrefCategoryVO getPrefCategoryById(@PathVariable String id) {
		logger.info("Fetch prefCategory by prefCategoryId : {}", id);
		PrefCategoryMst prefCategoryMst = PrefCategoryService.getByPrefCategoryId(id);
		PrefCategoryVO prefCategory = null;
		if (null != prefCategoryMst) {
			prefCategory = new PrefCategoryVO();
			prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
			prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
			prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
			prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
			prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
			prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
		}
        return prefCategory;
    }
	
	
	

	@GetMapping(value = "/name/{name}", produces = "application/json")
    public PrefCategoryVO getPrefCategoryByName(@PathVariable String name) {
		logger.info("Fetch prefCategory by prefCategoryName : {}", name);
		String prefCategoryId = PrefCategoryService.getPrefCategoryIdByPrefCategoryName(name);
		PrefCategoryMst prefCategoryMst = PrefCategoryService.getByPrefCategoryId(prefCategoryId);
		PrefCategoryVO prefCategory = null;
		if (null != prefCategoryMst) {
			prefCategory = new PrefCategoryVO();
			prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
			prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
			prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
			prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
			prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
			prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
		}
        return prefCategory;
    }
	
	

	@GetMapping(value = "/list/authorized", produces = "application/json")
    public PrefCategoryVOList getPrefCategoryByAuthorizedStatus() { 
		logger.info("Fetch prefCategory by AuthStatus : A");
		List<PrefCategoryMst> prefCategoryList = PrefCategoryService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		PrefCategoryVOList voList = new PrefCategoryVOList();
		List<PrefCategoryVO> prefCategoryVOList = new ArrayList<PrefCategoryVO>();  
		
		if (null != prefCategoryList) {
			for (PrefCategoryMst prefCategoryMst: prefCategoryList) {
				PrefCategoryVO prefCategory = new PrefCategoryVO();
				prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
				prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
				prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
				prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
				prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
				prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
				
				prefCategoryVOList.add(prefCategory);
			}
		}
		voList.setPrefCategory(prefCategoryVOList);
        return voList;  
    }
	
	

	@GetMapping(value = "/list/pending", produces = "application/json")
    public PrefCategoryVOList getPrefCategoryByPendingStatus() { 
		logger.info("Fetch prefCategory by AuthStatus : P");
		List<PrefCategoryMst> prefCategoryList = PrefCategoryService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		PrefCategoryVOList voList = new PrefCategoryVOList();
		List<PrefCategoryVO> prefCategoryVOList = new ArrayList<PrefCategoryVO>();  
		
		if (null != prefCategoryList) {
			for (PrefCategoryMst prefCategoryMst: prefCategoryList) {
				PrefCategoryVO prefCategory = new PrefCategoryVO();
				prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
				prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
				prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
				prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
				prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
				prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
				prefCategoryVOList.add(prefCategory);
			}
		}
		voList.setPrefCategory(prefCategoryVOList);
        return voList;  
    }
	
	

	@GetMapping(value = "/list/rejected", produces = "application/json")
    public PrefCategoryVOList getPrefCategoryByRejectedStatus() { 
		logger.info("Fetch prefCategory by AuthStatus : R");
		List<PrefCategoryMst> PrefCategoryList = PrefCategoryService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		PrefCategoryVOList voList = new PrefCategoryVOList();
		List<PrefCategoryVO> PrefCategoryVOList = new ArrayList<PrefCategoryVO>();  
		
		if (null != PrefCategoryList) {
			for (PrefCategoryMst prefCategoryMst: PrefCategoryList) {
				PrefCategoryVO prefCategory = new PrefCategoryVO();
				prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
				prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
				prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
				prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
				prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
				prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
				
				PrefCategoryVOList.add(prefCategory);
			}
		}
		voList.setPrefCategory(PrefCategoryVOList);
        return voList;  
    }


	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public PrefCategoryVOList getPrefCategoryByDeleted() { 
		logger.info("Fetch prefCategory by AuthStatus : D");
		List<PrefCategoryMst> prefCategoryList = PrefCategoryService.getByDeleted(Boolean.TRUE);
		PrefCategoryVOList voList = new PrefCategoryVOList();
		List<PrefCategoryVO> prefCategoryVOList = new ArrayList<PrefCategoryVO>();  		
		if (null != prefCategoryList) {
			for (PrefCategoryMst prefCategoryMst: prefCategoryList) {
				PrefCategoryVO prefCategory = new PrefCategoryVO();
				prefCategory.setPrefCategoryId(prefCategoryMst.getPrefCategoryId());
				prefCategory.setPrefCategoryName(prefCategoryMst.getPrefCategoryName());
				prefCategory.setPrefCategoryDisplayName(prefCategoryMst.getPrefCategoryDisplayName());
				prefCategory.setAuthStatus(prefCategoryMst.getAuthStatus());
				prefCategory.setIsActive(Integer.toString(prefCategoryMst.getIsActive()));				
				prefCategory.setIsDeleted(String.valueOf(prefCategoryMst.getIsDeleted()));
				
				prefCategoryVOList.add(prefCategory);
			}
		}
		voList.setPrefCategory(prefCategoryVOList);
        return voList;  
    }
	
	
	@PostMapping(value = "/deletePrefCategory/{id}", consumes = "application/json", produces = "application/json")
    public String deletePrefCategory(@PathVariable(name="id") String prefCategoryId) {
		logger.debug("Delete prefCategory by prefCategoryId : {}", prefCategoryId);
		PrefCategoryMst prefCategoryMst = PrefCategoryService.getByPrefCategoryId(prefCategoryId);
		
		if (null != prefCategoryMst) {
			Date dt = new Date();
			prefCategoryMst.setDeletedBy("login3");
			prefCategoryMst.setDeletedDate(dt);
			prefCategoryMst.setDeletedTime(dt);
			prefCategoryMst.setAuthStatus("D");
			prefCategoryMst.setIsActive(0);
			prefCategoryMst.setIsDeleted(Boolean.TRUE);
			
			PrefCategoryMst prefCategoryMstNew = PrefCategoryService.saveOrUpdate("login", prefCategoryMst);
			if (null != prefCategoryMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deletePrefCategory  method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	

}
