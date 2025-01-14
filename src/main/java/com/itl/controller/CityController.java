package com.itl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itl.domain.entities.masters.CityMst;
import com.itl.service.masters.CityService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.CityVO;
import com.itl.web.dto.CityVOList;


@RestController
@RequestMapping("/City")
public class CityController {

	private static final Logger logger = LoggerFactory.getLogger(CityController.class);
	@Autowired
	private CityService CityService;
	
	@PostMapping(value = "/createCity", consumes = "application/json", produces = "application/json")
	public String createcity(@RequestBody CityVO city) {
		logger.debug("Add city for cityId : {}" + city.getCityId());
		try {
			CityMst cityMst = CityService.getByCityId(city.getCityId());
			if (null != cityMst) {
				logger.info("city with cityId {} already exists.", city.getCityId());
				return "Failure.. Record already exists";
			} else {
				cityMst = new CityMst();
				Date dt = new Date();
				cityMst.setCreatedBy("login");
				cityMst.setCreatedDate(dt);
				cityMst.setCreatedTime(dt);
				cityMst.setLastModifiedBy("login");
				cityMst.setLastModifiedDate(dt);
				cityMst.setLastModifiedTime(dt);
				cityMst.setAuthStatus(OmniConstants.AUTH_PENDING);
				cityMst.setIsActive(1);
				cityMst.setIsDeleted(Boolean.FALSE);
				cityMst.setCityId(city.getCityId());
				cityMst.setCityName(city.getCityName());
				cityMst.setStateId(city.getStateId());
				cityMst.setCountryId(city.getCountryId());
				cityMst.setCityDisplayName(city.getCityDisplayName());
	
				CityMst cityMstNew = CityService.saveOrUpdate("login", cityMst);
				if (null != cityMstNew) {
					CityService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
					logger.info("city & Cache - {} - saved Successfully", city.getCityId());
					return "Successfully saved record";
				} else {
					logger.info("Failed to save city - {}", city.getCityId());
					return "Failure while saving record";
				}
			}
		} catch (Exception e) {
			return "Failure while saving record";
		}
	}
	
	
	@PostMapping(value = "/updateCity", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updatecity(@RequestBody CityVO city) {
		logger.debug("Updating city with cityId : {}" + city.getCityId());
		CityMst cityMst = CityService.getByCityId(city.getCityId());
		if (null != cityMst) {
			Date dt = new Date();
			cityMst.setLastModifiedBy("login2");
			cityMst.setLastModifiedDate(dt);
			cityMst.setLastModifiedTime(dt);
			cityMst.setAuthStatus(OmniConstants.AUTH_PENDING);
			cityMst.setIsActive(Integer.parseInt(city.getIsActive()));
			cityMst.setIsDeleted(city.getIsDeleted().equals("1") ? true : false);
			cityMst.setCityId(city.getCityId());
			cityMst.setCityName(city.getCityName());
			cityMst.setCityDisplayName(city.getCityDisplayName());
			cityMst.setStateId(city.getStateId());	
			cityMst.setCountryId(city.getCountryId());

			CityMst cityMstNew = CityService.saveOrUpdate("login", cityMst);
			if (null != cityMstNew) {
				CityService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("city & Cache updated successfully for {}", city.getCityId());
				//return "Successfully updated Record";
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				logger.info("Failure to update city with id : {}", city.getCityId());
			//	return "Failure while updating record";
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
	public ResponseEntity<CityVO> getcityById(@PathVariable String id) {
		logger.info("Fetch city by cityId : {}", id);
		CityMst cityMst = CityService.getByCityId(id);
		CityVO city = null;
		if (null != cityMst) {
			city = new CityVO();
			city.setCityId(cityMst.getCityId());
			city.setCityName(cityMst.getCityName());
			city.setCityDisplayName(cityMst.getCityDisplayName());
			city.setCountryId(cityMst.getCountryId());
			city.setStateId(cityMst.getStateId());	
			city.setAuthStatus(cityMst.getAuthStatus());
			city.setIsActive(Integer.toString(cityMst.getIsActive()));
			city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
			return new ResponseEntity<CityVO>(city, HttpStatus.OK);
		}else {
	
		return new ResponseEntity<CityVO>(city, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping(value = "/name/{name}", produces = "application/json")
	public ResponseEntity<CityVO> getcityByName(@PathVariable String name) {
		logger.info("Fetch city by cityName : {}", name);
		String cityId = CityService.getCityIdByCityName(name);
		CityMst cityMst = CityService.getByCityId(cityId);
		CityVO city = null;
		if (null != cityMst) {
			city = new CityVO();
			city.setCityId(cityMst.getCityId());
			city.setCityName(cityMst.getCityName());
			city.setCityDisplayName(cityMst.getCityDisplayName());
			city.setCountryId(cityMst.getCountryId());
			city.setStateId(cityMst.getStateId());
			city.setAuthStatus(cityMst.getAuthStatus());
			city.setIsActive(Integer.toString(cityMst.getIsActive()));
			city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
			return new ResponseEntity<CityVO>(city, HttpStatus.OK);
		}else {
		
		return new ResponseEntity<CityVO>(city, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public ResponseEntity<CityVOList>  getCityByAuthorizedStatus() { 
		
		logger.info("Fetch countries by AuthStatus : A");
		List<CityMst> cityList = CityService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		CityVOList voList = new CityVOList();
		List<CityVO> cityVOList = new ArrayList<CityVO>();  
		
		if (null != cityList) {		
			for (CityMst cityMst: cityList) {				
				CityVO city = new CityVO();
				city.setCityId(cityMst.getCityId());
				city.setCityId(cityMst.getCityId());
				city.setCityName(cityMst.getCityName());
				city.setCityDisplayName(cityMst.getCityDisplayName());
				city.setCountryId(cityMst.getCountryId());
				city.setAuthStatus(cityMst.getAuthStatus());
				city.setIsActive(Integer.toString(cityMst.getIsActive()));					
				city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
				city.setStateId(cityMst.getStateId());
				
				cityVOList.add(city);
			}
			voList.setCity(cityVOList);
			return new ResponseEntity<CityVOList>(voList, HttpStatus.OK);
		}
	
		return new ResponseEntity<CityVOList>(voList, HttpStatus.NOT_FOUND);
    }
	
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public ResponseEntity<CityVOList> getCityByPendingStatus() { 
		logger.info("Fetch countries by AuthStatus : P");
		List<CityMst> cityList = CityService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		CityVOList voList = new CityVOList();
		List<CityVO> cityVOList = new ArrayList<CityVO>();
		
		if (null != cityList) {
			for (CityMst cityMst: cityList) {
				CityVO city = new CityVO();
				city.setCityId(cityMst.getCityId());
				city.setCityName(cityMst.getCityName());
				city.setCityDisplayName(cityMst.getCityDisplayName());			
				city.setAuthStatus(cityMst.getAuthStatus());
				city.setCountryId(cityMst.getCountryId());
				city.setIsActive(Integer.toString(cityMst.getIsActive()));				
				city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
				
				cityVOList.add(city);
			}
		}
		voList.setCity(cityVOList);		
		return new ResponseEntity<CityVOList>(voList, HttpStatus.OK);
    }
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public ResponseEntity<CityVOList> getCityByRejectedStatus() { 
		logger.info("Fetch countries by AuthStatus : R");
		List<CityMst> cityList = CityService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		CityVOList voList = new CityVOList();
		List<CityVO> cityVOList = new ArrayList<CityVO>();  
		
		if (null != cityList) {
			for (CityMst cityMst: cityList) {
				CityVO city = new CityVO();
				city.setCityId(cityMst.getCityId());
				city.setCityId(cityMst.getCityId());
				city.setCityName(cityMst.getCityName());
				city.setCityDisplayName(cityMst.getCityDisplayName());
				city.setCountryId(cityMst.getCountryId());
				city.setStateId(cityMst.getStateId());
				city.setAuthStatus(cityMst.getAuthStatus());
				city.setIsActive(Integer.toString(cityMst.getIsActive()));					
				city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
				
				cityVOList.add(city);
			}
		}
		voList.setCity(cityVOList);
		return new ResponseEntity<CityVOList>(voList, HttpStatus.OK);
    }
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public ResponseEntity<CityVOList> getCityByDeleted() { 
		logger.info("Fetch countries by AuthStatus : D");
		List<CityMst> cityList = CityService.getByDeleted(Boolean.TRUE);
		CityVOList voList = new CityVOList();
		List<CityVO> cityVOList = new ArrayList<CityVO>();  
		
		if (null != cityList) {		
			for (CityMst cityMst: cityList) {
				CityVO city = new CityVO();
				city.setCityId(cityMst.getCityId());
				city.setCityName(cityMst.getCityName());
				city.setCityDisplayName(cityMst.getCityDisplayName());
				city.setStateId(cityMst.getStateId());
				city.setCountryId(cityMst.getCountryId());
				city.setAuthStatus(cityMst.getAuthStatus());
				city.setIsActive(Integer.toString(cityMst.getIsActive()));					
				city.setIsDeleted(String.valueOf(cityMst.getIsDeleted()));
				
				cityVOList.add(city);
			}
			voList.setCity(cityVOList);
			return new ResponseEntity<CityVOList>(voList, HttpStatus.OK); 
		}
	
		return new ResponseEntity<CityVOList>(voList, HttpStatus.NOT_FOUND);  
    }
	
	
	@PostMapping(value = "/deleteCity/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> deletecity(@PathVariable(name = "id") String cityId) {
		logger.debug("Delete city by cityId : {}", cityId);
		CityMst cityMst = CityService.getByCityId(cityId);
		if (null != cityMst) {
			Date dt = new Date();
			cityMst.setDeletedBy("login3");
			cityMst.setDeletedDate(dt);
			cityMst.setDeletedTime(dt);
			cityMst.setAuthStatus("D");
			cityMst.setIsActive(0);
			cityMst.setIsDeleted(Boolean.TRUE);
		
			CityMst cityMstNew = CityService.saveOrUpdate("login", cityMst);
			if (null != cityMstNew) {
				logger.debug("Record & Cache deleted Successfully");
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				logger.info("Failure while deleting record");
				return new ResponseEntity<String>(HttpStatus.PARTIAL_CONTENT);
			}
		} else {
			logger.debug("Exit from method");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	 
	
}
