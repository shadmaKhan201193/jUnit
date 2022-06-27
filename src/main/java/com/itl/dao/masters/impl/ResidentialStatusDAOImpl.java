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
import com.itl.dao.masters.ResidentialStatusDAO;
import com.itl.domain.entities.masters.ResidentialStatusMst;
import com.itl.exceptions.NGException;

@Repository("residentialStatusDAO")
public class ResidentialStatusDAOImpl extends JpaDAOImpl<Long, ResidentialStatusMst> 
	implements ResidentialStatusDAO {
     
	private static final Logger logger = LoggerFactory.getLogger(ResidentialStatusDAOImpl.class);
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
	
	public ResidentialStatusMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getUniqueResidentialStatus", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getPrimaryKey");
		return rMaaz.get(0);
	}
	
	public List<ResidentialStatusMst> getByResidentialStatusName(String residentialStatusName) throws NGException {
		logger.debug(" Enter Inside getByResidentialStatusName method");
		logger.info(" ResidentialStatusName method =" + residentialStatusName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("residentialStatusName", residentialStatusName);

		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getresidentialStatusName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByResidentialStatusName method");
		return rMaaz;
	}
	
	public List<ResidentialStatusMst> getByResidentialStatusId(String residentialStatusId) throws NGException {
		logger.debug(" Enter Inside getByResidentialStatusId method");
		logger.info("ResidentialStatus=" + residentialStatusId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("residentialStatusId", residentialStatusId);
		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getresidentialStatusId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByResidentialStatusId method");
		return rMaaz;
	}
	

	public List<ResidentialStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getByAuthStatus", queryParams);
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
	
		
	public String getResidentialStatusIdByResidentialStatusName(String residentialStatusName) throws NGException {
		logger.info("Inside getResidentialStatusIdByResidentialStatusName method "  +residentialStatusName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("residentialStatusName", residentialStatusName);

		String residentialStatus = null;
		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getResidentialStatusIdByResidentialStatusName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			residentialStatus = rMaaz.get(0).getResidentialStatusId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return residentialStatus;
	}
	
	
	public List<ResidentialStatusMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<ResidentialStatusMst> rMaaz = findByNamedQueryAndNamedParams("ResidentialStatusMst.getByDeleted", queryParams);
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
