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
import com.itl.dao.masters.StateDAO;
import com.itl.domain.entities.masters.StateMst;
import com.itl.exceptions.NGException;

@Repository("stateDAO")
public class StateDAOImpl extends JpaDAOImpl<Long, StateMst> 
	implements StateDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(StateDAOImpl.class);

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
	
	public StateMst getPrimaryKey(Long Id) throws NGException {
		logger.info("Inside getPrimaryKey method " +Id );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding ID in a queryParams " +Id );
		queryParams.put("Id", Id);
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getUniquestate", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		}
		else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz.get(0);
	}
	
	public List<StateMst> getByStateName(String stateName) throws NGException {
		logger.info("Inside getByStateName method " +stateName );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding stateName in a queryParams " +stateName );
		queryParams.put("stateName", stateName);
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getstateName", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	public List<StateMst> getByStateId(String stateId) throws NGException {
		logger.info("Inside getByStateId method " +stateId );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding stateId in a queryParams " +stateId );
		queryParams.put("stateId", stateId);
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getstateId", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	public List<StateMst> getStateByCountryId(String countryId, String stateId) throws NGException {
		logger.info("Inside getStateByCountryId method "  +countryId +" "+stateId );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding stateId and countryId in a queryParams " +countryId +" "+stateId);
		queryParams.put("countryId", countryId);
		queryParams.put("stateId", stateId);
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getstateByCountryId", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return" +rMaaz.get(0) );
		return rMaaz;
	}
	
	
	public List<StateMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method "  +authStatus );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding authStatus and isDeleted in a queryParams " +authStatus);
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getByAuthStatus", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	public String getStateIdByStateName(String stateName) throws NGException {
		logger.info("Inside getStateIdByStateName method "  +stateName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("stateName", stateName);

		String stateId = null;
		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getStateIdByStateName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			stateId = rMaaz.get(0).getStateId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return stateId;
	}
	
	public List<StateMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.info("Inside getByDeleted method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding isDeleted  in a queryParams");
		queryParams.put("isDeleted", isDeleted);

		List<StateMst> rMaaz = findByNamedQueryAndNamedParams("StateMst.getByDeleted", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.info("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
}
