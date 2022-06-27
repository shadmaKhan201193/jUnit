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
import com.itl.dao.masters.MaritalStatusDAO;
import com.itl.domain.entities.masters.MaritalStatusMst;
import com.itl.exceptions.NGException;

@Repository("maritalStatusDAO")
public class MaritalStatusDAOImpl extends JpaDAOImpl<Long, MaritalStatusMst> 
	implements MaritalStatusDAO {
	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusDAOImpl.class);
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PersistenceContext(unitName = "PRODTECH")
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager em) {
		logger.debug("Inside setEntityManager method  " +em);
		this.entityManager = em;
		super.setEntityManager(entityManager);
		logger.debug("Exit from method");

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
	
	public MaritalStatusMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getUniqueMaritalStatus", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.info("Inside else if condition");
		return rMaaz.get(0);
	}
	
	
	
	public List<MaritalStatusMst> getByMaritalStatusName(String maritalStatusName) throws NGException {
		logger.debug("Inside getByMaritalStatusName method "  +maritalStatusName);

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("maritalStatusName", maritalStatusName);

		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getMaritalStatusName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.info("Inside else if condition");
		return rMaaz;
	}
	
	
	
	public List<MaritalStatusMst> getByMaritalStatusId(String maritalStatusId) throws NGException {
		logger.info("Inside getByMaritalStatusId method "  +maritalStatusId );

		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding authStatusId and isDeleted in a queryParams " +maritalStatusId);

		queryParams.put("maritalStatusId", maritalStatusId);

		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getMaritalStatusId", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.debug("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	
	public String getMaritalStatusIdByMaritalStatusName(String maritalStatusName) throws NGException {
		logger.info("Inside getMaritalStatusIdByMaritalStatusName method "  +maritalStatusName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("maritalStatusName", maritalStatusName);

		String maritalStatusId = null;
		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getMaritalStatusIdByMaritalStatusName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			maritalStatusId = rMaaz.get(0).getMaritalStatusId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return maritalStatusId;
	}
	
	
	
	public List<MaritalStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method "  +authStatus );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding authStatus and isDeleted in a queryParams " +authStatus);
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getByAuthStatus", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.debug("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	
	
	public List<MaritalStatusMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding isDeleted  in a queryParams");
		queryParams.put("isDeleted", isDeleted);

		List<MaritalStatusMst> rMaaz = findByNamedQueryAndNamedParams("MaritalStatusMst.getByDeleted", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.debug("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
}
