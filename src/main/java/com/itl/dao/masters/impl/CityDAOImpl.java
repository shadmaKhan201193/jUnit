package com.itl.dao.masters.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itl.dao.base.impl.JpaDAOImpl;
import com.itl.dao.masters.CityDAO;
import com.itl.domain.entities.masters.CityMst;

import com.itl.exceptions.NGException;

@Repository("cityDAO")
public class CityDAOImpl extends JpaDAOImpl<Long, CityMst> 
	implements CityDAO {
	private static final Logger logger = LoggerFactory.getLogger(CityDAOImpl.class);
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PersistenceContext(unitName = "PRODTECH")
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
		super.setEntityManager(entityManager);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
		super.setEntityManager(entityManager);
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public CityMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getUniqueCity", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz.get(0);
	}
	
	public List<CityMst> getByCityName(String cityName) throws NGException {
		logger.debug("Inside getByCityName method ");
		logger.info("CityName "  +cityName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityName", cityName);

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getcityName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz;
	}
	
	public List<CityMst> getByCityId(String cityId) throws NGException {
		logger.debug("Inside getByCityId method ");
		logger.info("CityId= "  +cityId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityId", cityId);

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getcityId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByCityId method");
		return rMaaz;
	}
	

	
	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getByAuthStatus", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz;
	}
	
	
	public String getCityIdByCityName(String cityName) throws NGException {
		logger.info("Inside getCityIdByCityName method "  +cityName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("cityName", cityName);

		String cityId = null;
		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getCityIdByCityName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
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

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getByDeleted", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz;
	}

	@Override
	public List<CityMst> getByCountryState(String countryId, String stateId, String cityId) throws NGException {
		logger.debug("Inside getByCountryState method ");
		logger.info("countryId "  +countryId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("countryId", countryId);

		List<CityMst> rMaaz = findByNamedQueryAndNamedParams("CityMst.getByCountryState", queryParams);
		if (null == rMaaz) {			
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {			
			return null;
		}		
		return rMaaz;
	}

	

	
}
