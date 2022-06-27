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
import com.itl.dao.masters.SalutationDAO;
import com.itl.domain.entities.masters.SalutationMst;
import com.itl.exceptions.NGException;

@Repository("salutationDAO")
public class SalutationDAOImpl extends JpaDAOImpl<Long, SalutationMst> 
	implements SalutationDAO {
    
	private static final Logger logger = LoggerFactory.getLogger(SalutationDAOImpl.class);
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
	
	public SalutationMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getUniqueSalutation", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getPrimaryKey");
		return rMaaz.get(0);
	}
	
	public List<SalutationMst> getBySalutationName(String salutationName) throws NGException {
		logger.debug(" Enter Inside getBySalutationName method");
		logger.info(" salutationName  =" + salutationName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("salutationName", salutationName);

		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getsalutationName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getBySalutationName method");
		return rMaaz;
	}
	
	public List<SalutationMst> getBySalutationId(String salutationId) throws NGException {
		logger.debug(" Enter Inside getBySalutationId method");
		logger.info("salutationId=" + salutationId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("salutationId", salutationId);

		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getsalutationId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getBySalutationId method");
		return rMaaz;
	}
	
	
	public List<SalutationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getByAuthStatus", queryParams);
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
	
	public String getSalutationIdBySalutationName(String salutationName) throws NGException {
		logger.info("Inside getSalutationIdBySalutationName method "  +salutationName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("salutationName", salutationName);

		String salutationId = null;
		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getSalutationIdBySalutationName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			salutationId = rMaaz.get(0).getSalutationId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return salutationId;
	}
	
	public List<SalutationMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<SalutationMst> rMaaz = findByNamedQueryAndNamedParams("SalutationMst.getByDeleted", queryParams);
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
