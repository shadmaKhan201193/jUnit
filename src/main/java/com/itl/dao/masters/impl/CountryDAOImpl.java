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
import com.itl.dao.masters.CountryDAO;
import com.itl.domain.entities.masters.CountryMst;
import com.itl.exceptions.NGException;
import com.itl.utils.OmniConstants;

@Repository("countryDAO")
public class CountryDAOImpl extends JpaDAOImpl<Long, CountryMst> 
	implements CountryDAO {
	private static final Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
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

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public CountryMst getPrimaryKey(Long Id) throws NGException {
		logger.info("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getUniqueCountry", queryParams);
		if (null == rMaaz) {
			logger.debug("rMaaz = null");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("rMaaz.size: " + rMaaz.size());
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz.get(0);
	}
	
	public List<CountryMst> getByCountryName(String countryName) throws NGException {
		logger.info("Inside getByCountryName method "  +countryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("countryName", countryName);

		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getcountryName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from method..rMaaz " +rMaaz);
		return rMaaz;
	}

	public List<CountryMst> getByCountryId(String countryId) throws NGException {
		logger.info("Inside getByCountryId method "  +countryId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("countryId", countryId);

		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getCountryId", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return rMaaz;
	}
	
	public List<CountryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method " +authStatus);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted",isDeleted);

		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getByAuthStatus", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.info("Exit from method ..rMaaz " + rMaaz);
		return rMaaz;
	}
	
	public String getCountryIdByCountryName(String countryName) throws NGException {
		logger.info("Inside getByCountryId method "  +countryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("countryName", countryName);

		String countryId = null;
		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getCountryIdByCountryName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			countryId = rMaaz.get(0).getCountryId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return countryId;
	}
	
	public List<CountryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted",isDeleted);

		List<CountryMst> rMaaz = findByNamedQueryAndNamedParams("CountryMst.getByDeleted", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from method..rMaaz" +rMaaz);
		return rMaaz;
	}
	
}
