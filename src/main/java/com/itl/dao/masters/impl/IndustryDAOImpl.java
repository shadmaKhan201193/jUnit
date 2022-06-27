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
import com.itl.dao.masters.IndustryDAO;
import com.itl.domain.entities.masters.IndustryMst;
import com.itl.exceptions.NGException;

@Repository("industryDAO")
public class IndustryDAOImpl extends JpaDAOImpl<Long, IndustryMst> 
	implements IndustryDAO {

	private static final Logger logger = LoggerFactory.getLogger(IndustryDAOImpl.class);
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
	
	public IndustryMst getPrimaryKey(Long Id) throws NGException {
		logger.info("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getUniqueIndustry", queryParams);
		if (null == rMaaz) {
			
			logger.debug("Inside if condition");
			return null;
			
		} else if (rMaaz != null && rMaaz.size() == 0) {
			
			logger.debug("Inside else if condition");
			return null;
		}
		return rMaaz.get(0);
	}
	
	public List<IndustryMst> getByIndustryName(String industryName) throws NGException {
		logger.info("Inside getByIndustryName method "  +industryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("industryName", industryName);

		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getindustryName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from getByIndustryName method...rMaaz= "+rMaaz);
		return rMaaz;
	}
	
	public List<IndustryMst> getByIndustryId(String industryId) throws NGException {
		logger.info("Inside getByIndustryId method "  +industryId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("industryId", industryId);

		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getindustryId", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from getByIndustryId method....rMaaz= "+rMaaz);
		return rMaaz;
	}
	public List<IndustryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method "  +authStatus );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding authStatus and isDeleted in a queryParams " +authStatus);
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getByAuthStatus", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.info("Exit from getByAuthStatus method...rMaaz= " +rMaaz.get(0) );
		return rMaaz;
	}
	
	public String getIndustryIdByIndustryName(String industryName) throws NGException {
		logger.info("Inside getIndustryId method "  +industryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("industryName", industryName);

		String industryId = null;
		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getIndustryIdByIndustryName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			industryId = rMaaz.get(0).getIndustryId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return industryId;
	}
	
	public List<IndustryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.info("Inside getByDeleted method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding isDeleted  in a queryParams");
		queryParams.put("isDeleted", isDeleted);

		List<IndustryMst> rMaaz = findByNamedQueryAndNamedParams("IndustryMst.getByDeleted", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.info("Exit from getByDeleted method...rMaaz=" +rMaaz.get(0) );
		return rMaaz;
	}
}
