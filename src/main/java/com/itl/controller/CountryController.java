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

import com.itl.domain.entities.masters.CountryMst;
import com.itl.service.masters.CountryService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.CountryVO;
import com.itl.web.dto.CountryVOList;

@RestController
@RequestMapping("/country")
@CrossOrigin(origins ="*",maxAge =3600)
public class CountryController {
	private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
	@Autowired
	private CountryService countryService;

	@PostMapping(value = "/createCountry", consumes = "application/json", produces = "application/json")
	public String createCountry(@RequestBody CountryVO country) {
		logger.debug("Add country for countryId : {}" + country.getCountryId());
		CountryMst countryMst = countryService.getByCountryId(country.getCountryId());
		if (null != countryMst) {
			logger.info("Country with countryId {} already exists.", country.getCountryId());
			return "Failure.. Record already exists";
		} else {
			countryMst = new CountryMst();
			Date dt = new Date();
			countryMst.setCreatedBy("login");
			countryMst.setCreatedDate(dt);
			countryMst.setCreatedTime(dt);
			countryMst.setLastModifiedBy("login");
			countryMst.setLastModifiedDate(dt);
			countryMst.setLastModifiedTime(dt);
			countryMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			countryMst.setIsActive(1);
			countryMst.setIsDeleted(Boolean.FALSE);
			countryMst.setCountryAbbr(country.getCountryAbbr());
			countryMst.setCountryDisplayName(country.getCountryDisplayName());
			countryMst.setCountryId(country.getCountryId());
			countryMst.setCountryName(country.getCountryName());

			CountryMst countryMstNew = countryService.saveOrUpdate("login", countryMst);
			if (null != countryMstNew) {
				countryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("Country & Cache - {} - saved Successfully", country.getCountryId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save country - {}", country.getCountryId());
				return "Failure while saving record";
			}
		}
	}

	@PostMapping(value = "/updateCountry", consumes = "application/json", produces = "application/json")
	public String updateCountry(@RequestBody CountryVO country) {
		logger.debug("Updating country with countryId : {}" + country.getCountryId());
		CountryMst countryMst = countryService.getByCountryId(country.getCountryId());
		if (null != countryMst) {
			Date dt = new Date();
			countryMst.setLastModifiedBy("login2");
			countryMst.setLastModifiedDate(dt);
			countryMst.setLastModifiedTime(dt);
			countryMst.setAuthStatus(country.getAuthStatus());
			countryMst.setIsActive(Integer.parseInt(country.getIsActive()));
			countryMst.setIsDeleted(country.getIsDeleted().equals("1") ? true : false);
			countryMst.setCountryAbbr(country.getCountryAbbr());
			countryMst.setCountryDisplayName(country.getCountryDisplayName());
			countryMst.setCountryId(country.getCountryId());
			countryMst.setCountryName(country.getCountryName());

			CountryMst countryMstNew = countryService.saveOrUpdate("login", countryMst);
			if (null != countryMstNew) {
				countryService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("Country & Cache updated successfully for {}", country.getCountryId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update country with id : {}", country.getCountryId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
	}

	@GetMapping(value = "/id/{id}", produces = "application/json")
	public CountryVO getCountryById(@PathVariable String id) {
		logger.info("Fetch country by countryId : {}", id);
		CountryMst countryMst = countryService.getByCountryId(id);
		CountryVO country = null;
		if (null != countryMst) {
			country = new CountryVO();
			country.setCountryId(countryMst.getCountryId());
			country.setCountryName(countryMst.getCountryName());
			country.setCountryDisplayName(countryMst.getCountryDisplayName());
			country.setCountryAbbr(countryMst.getCountryAbbr());
			country.setAuthStatus(countryMst.getAuthStatus());
			country.setIsActive(Integer.toString(countryMst.getIsActive()));
			country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the country is not found
		return country;
	}

	
	@GetMapping(value = "/name/{name}", produces = "application/json")
	public CountryVO getCountryByName(@PathVariable String name) {
		logger.info("Fetch country by countryName : {}", name);
		String countryId = countryService.getCountryIdByCountryName(name);
		CountryMst countryMst = countryService.getByCountryId(countryId);
		CountryVO country = null;
		if (null != countryMst) {
			country = new CountryVO();
			country.setCountryId(countryMst.getCountryId());
			country.setCountryName(countryMst.getCountryName());
			country.setCountryDisplayName(countryMst.getCountryDisplayName());
			country.setCountryAbbr(countryMst.getCountryAbbr());
			country.setAuthStatus(countryMst.getAuthStatus());
			country.setIsActive(Integer.toString(countryMst.getIsActive()));
			country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));
		}
		//TODO - add else part to reply countries not found
		return country;
	}

	@GetMapping(value = "/list/authorized", produces = "application/json")
	public CountryVOList getCountryByAuthorizedStatus() {
		logger.info("Fetch countries by AuthStatus : A");
		List<CountryMst> countryList = countryService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, Boolean.FALSE);
		CountryVOList voList = new CountryVOList();
		List<CountryVO> countryVOList = new ArrayList<CountryVO>();
		if (null != countryList) {
			for (CountryMst countryMst : countryList) {
				CountryVO country = new CountryVO();
				country.setCountryName(countryMst.getCountryName());
				country.setCountryDisplayName(countryMst.getCountryDisplayName());
				country.setCountryId(countryMst.getCountryId());
				country.setCountryAbbr(countryMst.getCountryAbbr());
				country.setAuthStatus(countryMst.getAuthStatus());
				country.setIsActive(Integer.toString(countryMst.getIsActive()));
				country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));

				countryVOList.add(country);
			}
		}
		//TODO - add else part to reply countries not found
		voList.setCountry(countryVOList);
		return voList;
	}

	@GetMapping(value = "/list/rejected", produces = "application/json")
	public CountryVOList getCountryByRejectedStatus() {
		logger.info("Fetch countries by AuthStatus : R");
		List<CountryMst> countryList = countryService.getByAuthStatus(OmniConstants.AUTH_REJECTED, Boolean.FALSE);
		CountryVOList voList = new CountryVOList();
		List<CountryVO> countryVOList = new ArrayList<CountryVO>();

		if (null != countryList) {
			for (CountryMst countryMst : countryList) {
				CountryVO country = new CountryVO();
				country.setCountryId(countryMst.getCountryId());
				country.setCountryName(countryMst.getCountryName());
				country.setCountryDisplayName(countryMst.getCountryDisplayName());
				country.setCountryAbbr(countryMst.getCountryAbbr());
				country.setAuthStatus(countryMst.getAuthStatus());
				country.setIsActive(Integer.toString(countryMst.getIsActive()));
				country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));

				countryVOList.add(country);
			}
		}
		//TODO - add else part to reply countries not found
		voList.setCountry(countryVOList);
		return voList;
	}

	@GetMapping(value = "/list/pending", produces = "application/json")
	public CountryVOList getCountryByPendingStatus() {
		logger.info("Fetch countries by AuthStatus : P");
		List<CountryMst> countryList = countryService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		CountryVOList voList = new CountryVOList();
		List<CountryVO> countryVOList = new ArrayList<CountryVO>();

		if (null != countryList) {
			for (CountryMst countryMst : countryList) {
				CountryVO country = new CountryVO();
				country.setCountryId(countryMst.getCountryId());
				country.setCountryName(countryMst.getCountryName());
				country.setCountryDisplayName(countryMst.getCountryDisplayName());
				country.setCountryAbbr(countryMst.getCountryAbbr());
				country.setAuthStatus(countryMst.getAuthStatus());
				country.setIsActive(Integer.toString(countryMst.getIsActive()));
				country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));

				countryVOList.add(country);
			}
		}
		//TODO - add else part to reply countries not found
		voList.setCountry(countryVOList);
		return voList;
	}

	@GetMapping(value = "/list/deleted", produces = "application/json")
	public CountryVOList getCountryByDeleted() {
		logger.info("Fetch countries by AuthStatus : D");
		List<CountryMst> countryList = countryService.getByDeleted(Boolean.TRUE);
		CountryVOList voList = new CountryVOList();
		List<CountryVO> countryVOList = new ArrayList<CountryVO>();

		if (null != countryList) {
			for (CountryMst countryMst : countryList) {
				CountryVO country = new CountryVO();
				country.setCountryId(countryMst.getCountryId());
				country.setCountryName(countryMst.getCountryName());
				country.setCountryDisplayName(countryMst.getCountryDisplayName());
				country.setCountryAbbr(countryMst.getCountryAbbr());
				country.setAuthStatus(countryMst.getAuthStatus());
				country.setIsActive(Integer.toString(countryMst.getIsActive()));
				country.setIsDeleted(String.valueOf(countryMst.getIsDeleted()));

				countryVOList.add(country);
			}
		}
		voList.setCountry(countryVOList);
		return voList;
	}

	@PostMapping(value = "/deleteCountry/{id}", consumes = "application/json", produces = "application/json")
	public String deleteCountry(@PathVariable(name = "id") String countryId) {
		logger.debug("Delete country by countryId : {}", countryId);
		CountryMst countryMst = countryService.getByCountryId(countryId);

		if (null != countryMst) {
			Date dt = new Date();
			countryMst.setDeletedBy("login3");
			countryMst.setDeletedDate(dt);
			countryMst.setDeletedTime(dt);
			countryMst.setAuthStatus("D");
			countryMst.setIsActive(0);
			countryMst.setIsDeleted(Boolean.TRUE);

			CountryMst countryMstNew = countryService.saveOrUpdate("login", countryMst);

			if (null != countryMstNew) {
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