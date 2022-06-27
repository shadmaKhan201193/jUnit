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
import com.itl.dao.masters.OccupationDAO;

import com.itl.domain.entities.masters.OccupationMst;
import com.itl.exceptions.NGException;

@Repository("occupationDAO")
public class OccupationDAOImpl extends JpaDAOImpl<Long, OccupationMst> 
	implements OccupationDAO {
	private static final Logger logger = LoggerFactory.getLogger(OccupationDAOImpl.class);

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
	
	public OccupationMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getUniqueOccupation", queryParams);
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
	
	public List<OccupationMst> getByOccupationName(String OccupationName) throws NGException {
		logger.debug("Inside getByOccupationName method "  +OccupationName);

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("occupationName", OccupationName);

		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getOccupationName", queryParams);
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
	
	public List<OccupationMst> getByOccupationId(String OccupationId) throws NGException {
		logger.debug("Inside getByOccupationId method "  +OccupationId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("occupationId", OccupationId);

		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getOccupationId", queryParams);
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
	
	public List<OccupationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getByAuthStatus", queryParams);
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
	
	public String getOccupationIdByOccupationName(String occupationName) throws NGException {
		logger.info("Inside getOccupationIdByOccupationName method "  +occupationName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("occupationName", occupationName);

		String occupationId = null;
		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getOccupationIdByOccupationName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			occupationId = rMaaz.get(0). getOccupationId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return occupationId;
	}
	
	
	public List<OccupationMst> getByDeleted(Boolean isDeleted) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<OccupationMst> rMaaz = findByNamedQueryAndNamedParams("OccupationMst.getByDeleted", queryParams);
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
