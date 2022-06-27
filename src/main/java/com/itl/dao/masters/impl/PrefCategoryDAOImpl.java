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
import com.itl.dao.masters.PrefCategoryDAO;
import com.itl.domain.entities.masters.PrefCategoryMst;
import com.itl.exceptions.NGException;

@Repository("prefCategoryDAO")
public class PrefCategoryDAOImpl extends JpaDAOImpl<Long, PrefCategoryMst> 
	implements PrefCategoryDAO {
      
	private static final Logger logger = LoggerFactory.getLogger(PrefCategoryDAOImpl.class);
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
	
	public PrefCategoryMst getPrimaryKey(Long Id) throws NGException {
		
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getUniquePrefCategory", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside  elseif condition");
			return null;
		}
		logger.debug("Exit from getPrimaryKey");
		return rMaaz.get(0);
	}
	
	public List<PrefCategoryMst> getByPrefCategoryName(String prefCategoryName) throws NGException {
		logger.debug(" Enter Inside getByPrefCategoryName method");
		logger.info("PrefCategoryName=" + prefCategoryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("prefCategoryName", prefCategoryName);

		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getprefCategoryName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getByPrefCategoryName method");
		return rMaaz;
	}
	
	public List<PrefCategoryMst> getByPrefCategoryId(String prefCategoryId) throws NGException {
		logger.debug("Inside getByPrefCategoryId method ");
		logger.info("prefCategoryId= "  +prefCategoryId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("prefCategoryId", prefCategoryId);

		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getprefCategoryId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByPrefCategoryId method");
		return rMaaz;
	}
	
	
	
	public List<PrefCategoryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getByAuthStatus", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByAuthStatus method");
		return rMaaz;
	}
	
	public String getPrefCategoryIdByPrefCategoryName(String prefCategoryName) throws NGException {
		logger.info("Inside getPrefCategoryIdByPrefCategoryName method "  +prefCategoryName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("prefCategoryName", prefCategoryName);

		String prefCategoryId = null;
		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getPrefCategoryIdByPrefCategoryName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			prefCategoryId = rMaaz.get(0).getPrefCategoryId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return prefCategoryId;
	}
	
	
	public List<PrefCategoryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<PrefCategoryMst> rMaaz = findByNamedQueryAndNamedParams("PrefCategoryMst.getByDeleted", queryParams);
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
}
