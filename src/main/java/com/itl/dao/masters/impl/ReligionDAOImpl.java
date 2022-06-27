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
import com.itl.dao.masters.ReligionDAO;
import com.itl.domain.entities.masters.ReligionMst;
import com.itl.exceptions.NGException;

@Repository("religionDAO")
public class ReligionDAOImpl extends JpaDAOImpl<Long, ReligionMst> 
	implements ReligionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ReligionDAOImpl.class);
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
	
	public ReligionMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info(" Value Inside getPrimaryKey method " + Id);

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getUniqueReligion", queryParams);
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
	
	public List<ReligionMst> getByReligionName(String religionName) throws NGException {
		logger.debug("Inside getByReligionName method");
		logger.info(" Value Inside getByReligionName method "  +religionName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("religionName", religionName);

		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getreligionName", queryParams);
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
	
	public List<ReligionMst> getByReligionId(String religionId) throws NGException {
		logger.debug("Inside getByReligionId method ");
		logger.info(" Value Inside getByReligionId method "  +religionId);

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("religionId", religionId);

		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getreligionId", queryParams);
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
	
	
	public ReligionMst getReligionIdByReligionName(String religionName) throws NGException {
		logger.info("Inside getReligionIdByReligionName method "  +religionName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("religionName", religionName);

		ReligionMst religionId = null;
		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getReligionIdByReligionName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			religionId = rMaaz.get(0);
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return religionId;
	}
	
	public List<ReligionMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getByAuthStatus", queryParams);
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
	
	
	public List<ReligionMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Entry in getByDeleted(Boolean isDeleted)");

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<ReligionMst> rMaaz = findByNamedQueryAndNamedParams("ReligionMst.getByDeleted", queryParams);
		if((null != rMaaz) && (!rMaaz.isEmpty())) {
			logger.debug("list.size = " + rMaaz.size());
			return rMaaz;
		}
		else rMaaz = null;
		
		/*
		 * 
		 * 
		 * if (null == rMaaz) { logger.info("Inside if condition"); return null; } else
		 * if (rMaaz != null && rMaaz.size() == 0) {
		 * logger.info("Inside else if condition"); return null; }
		 */		
		logger.debug("Exit from method");
		return rMaaz;
	}
}
