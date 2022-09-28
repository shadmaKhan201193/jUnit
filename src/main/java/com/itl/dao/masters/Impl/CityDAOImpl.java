package com.itl.dao.masters.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.dao.masters.CityDAO;
import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;
import com.itl.repository.CityRepository;

@Service
public class CityDAOImpl implements CityDAO {

	private static final Logger logger = LoggerFactory.getLogger(CityDAOImpl.class);

	@Autowired
	CityRepository cityRepo;


	public CityMst getPrimaryKey(Long Id) throws NGException {
		return cityRepo.getById(Id);
	}

	public List<CityMst> getByCityName(String cityName) throws NGException {
		logger.info("CityName " + cityName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityName", cityName);
		List<CityMst> rMaaz = cityRepo.findByCityName(cityName);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}

	public List<CityMst> getByCityId(String cityId) throws NGException {
		logger.info("CityId= " + cityId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityId", cityId);
		List<CityMst> rMaaz = cityRepo.findByCityId(cityId);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;

	}

	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<CityMst> rMaaz = cityRepo.findByAuthStatusAndIsDeleted(authStatus, isDeleted);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}

	public String getCityIdByCityName(String cityName) throws NGException {
		logger.info("Inside getCityIdByCityName method " + cityName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityName", cityName);
		String cityId = null;
		List<CityMst> rMaaz = cityRepo.findByCityName(cityName );
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			 cityId = rMaaz.get(0).getCityId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return cityId;
	}

	public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);
		List<CityMst> rMaaz = cityRepo.findByIsDeleted(isDeleted);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}

	public List<CityMst> getByCountryState(String countryId, String stateId, String cityId) throws NGException {
		logger.info("countryId " + countryId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("countryId", countryId);
		queryParams.put("stateId", stateId);
		queryParams.put("cityId", cityId);
		List<CityMst> rMaaz = cityRepo.findByCountryIdAndStateIdAndCityId(countryId, stateId, cityId);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;

	}

	public CityMst save(CityMst city) throws NGException {
		CityMst newCity = cityRepo.save(city);
		return newCity;
	}
	
	
}
