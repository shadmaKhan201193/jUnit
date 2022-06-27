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

import com.itl.domain.entities.masters.SectorMst;
import com.itl.service.masters.SectorService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.SectorVO;
import com.itl.web.dto.SectorVOList;

@RestController
@RequestMapping("/Sector")
public class SectorController {
	
private static final Logger logger = LoggerFactory.getLogger(SectorController.class);
	
	@Autowired
	private SectorService SectorService;
	
	@PostMapping(value = "/createSector", consumes = "application/json", produces = "application/json")
    public String createSector(@RequestBody SectorVO sector) {
		logger.debug("Add sector for sectorId : {}" + sector.getSectorId());
		SectorMst sectorMst = SectorService.getBySectorId(sector.getSectorId());	
		if (null != sectorMst ) {
			logger.info("Sector with SectorId {} already exists.", sector.getSectorId());
			return "Failure.. Record already exists";
		} else {
			
		   sectorMst = new SectorMst();
			Date dt = new Date();
			sectorMst.setCreatedBy("login");
			sectorMst.setCreatedDate(dt);
			sectorMst.setCreatedTime(dt);
			sectorMst.setLastModifiedBy("login");
			sectorMst.setLastModifiedDate(dt);
			sectorMst.setLastModifiedTime(dt);
			sectorMst.setAuthStatus("A");
			sectorMst.setIsActive(1);
			sectorMst.setIsDeleted(Boolean.FALSE);
			sectorMst.setSectorId(sector.getSectorId());
			sectorMst.setSectorName(sector.getSectorName());
			sectorMst.setSectorDisplayName(sector.getSectorDisplayName());
			
			
			SectorMst sectorMstNew = SectorService.saveOrUpdate("login", sectorMst);
			if (null != sectorMstNew) {
				SectorService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Sector & Cache - {} - saved Successfully",  sector.getSectorId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save Sector - {}",  sector.getSectorId());
			
				return "Failure while saving record";
			}
		}
    }
	
	
	
	@PostMapping(value = "/updateSector", consumes = "application/json", produces = "application/json")
    public String updateSector(@RequestBody SectorVO sector) {    
		logger.info("Inside updateSector method");
		SectorMst sectorMst = SectorService.getBySectorId(sector.getSectorId());
		
		if (null != sectorMst) {
			Date dt = new Date();
			sectorMst.setLastModifiedBy("login2");
			sectorMst.setLastModifiedDate(dt); 
			sectorMst.setLastModifiedTime(dt);
			sectorMst.setAuthStatus(sector.getAuthStatus());
			sectorMst.setIsActive(Integer.parseInt(sector.getIsActive()));
			sectorMst.setIsDeleted(sector.getIsDeleted().equals("1")?true:false);
			sectorMst.setSectorId(sector.getSectorId());
			sectorMst.setSectorName(sector.getSectorName());
			sectorMst.setSectorDisplayName(sector.getSectorDisplayName());
			logger.info("Before saveOrUpdate call" );
			SectorMst sectorMstNew = SectorService.saveOrUpdate("login", sectorMst);
			
			if (null != sectorMstNew) {
				SectorService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Sector & Cache updated successfully for {}", sector.getSectorId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update Sector with id : {}",sector.getSectorId());
				return "Failure while updating record";
			}
		} else {
			logger.info("Exit from method");
			return "Failure.. Record does not exists";
		}
    }
	

	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public SectorVO getSectorById(@PathVariable String id) {
		logger.info("Fetch sector by  sectorId : {}", id);
	    SectorMst sectorMst = SectorService.getBySectorId(id);		
		SectorVO sector = null;
		logger.debug("Before if condition " +sectorMst);
		if (null != sectorMst) {
			sector = new SectorVO();
			sector.setSectorId(sectorMst.getSectorId());
			sector.setSectorName(sectorMst.getSectorName());
			sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
			sector.setAuthStatus(sectorMst.getAuthStatus());
			sector.setIsActive(Integer.toString(sectorMst.getIsActive()));					
			sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
		}
		logger.info("Exit from getSectorById method");
        return sector;
    }

	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
    public SectorVO getSectorByName(@PathVariable String name) {
		logger.info("Fetch sector by  sectorName : {}", name);
		String sectorId = SectorService.getSectorIdBySectorName(name);
		SectorMst sectorMst = SectorService.getBySectorId(sectorId);
	
		SectorVO sector = null;
		if (null != sectorMst) {
			logger.info("Inside if Condition");
			sector = new SectorVO();
			sector.setSectorId(sectorMst.getSectorId());
			sector.setSectorName(sectorMst.getSectorName());
			sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
			sector.setAuthStatus(sectorMst.getAuthStatus());
			sector.setIsActive(Integer.toString(sectorMst.getIsActive()));					
			sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
		}
		logger.debug("Exit from getSectorByName method");
        return sector;
    }
	
	
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public SectorVOList getSectorByAuthorizedStatus() { 
		logger.info("Fetch Sector by AuthStatus : A");		
		List<SectorMst> sectorList = SectorService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		SectorVOList voList = new SectorVOList();
		List<SectorVO> sectorVOList = new ArrayList<SectorVO>();  
		
		if (null != sectorList) {
			logger.info("Inside if loop");
			for (SectorMst sectorMst: sectorList) {
				logger.info("Inside for loop");
				SectorVO sector = new SectorVO();
				sector.setSectorId(sectorMst.getSectorId());
				sector.setSectorName(sectorMst.getSectorName());
				sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
				sector.setAuthStatus(sectorMst.getAuthStatus());
				sector.setIsActive(Integer.toString(sectorMst.getIsActive()));				
				sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
				sectorVOList.add(sector);
			}
		}
		voList.setSector(sectorVOList);
		logger.debug("Exit from getSectorByAuthorizedStatus method");
        return voList;  
    }
	

	

	@GetMapping(value = "/list/pending", produces = "application/json")
    public SectorVOList getSectorByPendingStatus() { 
		logger.info("Fetch Sector by AuthStatus : P");		
		List<SectorMst> sectorList = SectorService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		SectorVOList voList = new SectorVOList();
		List<SectorVO> sectorVOList = new ArrayList<SectorVO>();  
		
		if (null != sectorList) {
			logger.info("Inside if loop");
			for (SectorMst sectorMst: sectorList) {
				logger.info("Inside for loop");
				SectorVO sector = new SectorVO();
				sector.setSectorId(sectorMst.getSectorId());
				sector.setSectorName(sectorMst.getSectorName());
				sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
				sector.setAuthStatus(sectorMst.getAuthStatus());
				sector.setIsActive(Integer.toString(sectorMst.getIsActive()));				
				sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
				
				sectorVOList.add(sector);
			}
		}
		voList.setSector(sectorVOList);
		logger.debug("Exit from getSectorByPendingStatus method");
        return voList;  
    }

	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public SectorVOList getSectorByRejectedStatus() { 
		logger.info("Fetch Sector by AuthStatus : R");		
		List<SectorMst> sectorList = SectorService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		SectorVOList voList = new SectorVOList();
		List<SectorVO> sectorVOList = new ArrayList<SectorVO>();  
		
		if (null != sectorList) {
			logger.info("Inside if loop");
			for (SectorMst sectorMst: sectorList) {
				logger.info("Inside for loop");
				SectorVO sector = new SectorVO();
				sector.setSectorId(sectorMst.getSectorId());
				sector.setSectorName(sectorMst.getSectorName());
				sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
				sector.setAuthStatus(sectorMst.getAuthStatus());
				sector.setIsActive(Integer.toString(sectorMst.getIsActive()));				
				sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
				
				sectorVOList.add(sector);
			}
		}
		voList.setSector(sectorVOList);
		logger.debug("Exit from getSectorByRejectedStatus  method");
        return voList;  
    }
	

	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public SectorVOList getSectorByDeleted() { 
		logger.info("Fetch Sector by AuthStatus : D");		
		List<SectorMst> sectorList = SectorService.getByDeleted(Boolean.TRUE);
		SectorVOList voList = new SectorVOList();
		List<SectorVO> sectorVOList = new ArrayList<SectorVO>();  
		
		if (null != sectorList) {
			logger.info("Inside if loop");
			for (SectorMst sectorMst: sectorList) {
				logger.info("Inside for loop");
				SectorVO sector = new SectorVO();
				sector.setSectorId(sectorMst.getSectorId());
				sector.setSectorName(sectorMst.getSectorName());
				sector.setSectorDisplayName(sectorMst.getSectorDisplayName());
				sector.setAuthStatus(sectorMst.getAuthStatus());
				sector.setIsActive(Integer.toString(sectorMst.getIsActive()));				
				sector.setIsDeleted(String.valueOf(sectorMst.getIsDeleted()));
				sectorVOList.add(sector);
			}
		}
		voList.setSector(sectorVOList);
		logger.debug("Exit from getSectorByDeleted method");
        return voList;  
    }
	
	

	
	@PostMapping(value = "/deleteSector/{id}", consumes = "application/json", produces = "application/json")
    public String deleteSector(@PathVariable(name="id") String sectorId) {
		logger.debug("Delete sector by sectorId : {}", sectorId);
		SectorMst sectorMst= SectorService.getBySectorId(sectorId);
		
		if (null != sectorMst) {
			logger.info("Inside if condition");
			Date dt = new Date();
			sectorMst.setDeletedBy("login3");
			sectorMst.setDeletedDate(dt);
			sectorMst.setDeletedTime(dt);
			sectorMst.setAuthStatus("D");
			sectorMst.setIsActive(0);
			sectorMst.setIsDeleted(Boolean.TRUE);
			
			SectorMst sectorMstNew = SectorService.saveOrUpdate("login", sectorMst);
			if (null != sectorMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteSector  method");
			return "Failure.. Record does not exists";
		}
    }
	
	

}
