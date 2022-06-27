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
import com.itl.dao.masters.GenderDAO;
import com.itl.domain.entities.masters.GenderMst;
import com.itl.exceptions.NGException;

@Repository("genderDAO")
public class GenderDAOImpl extends JpaDAOImpl<Long, GenderMst> 
	implements GenderDAO {
	private static final Logger logger = LoggerFactory.getLogger(GenderDAOImpl.class);
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
	
	public GenderMst getPrimaryKey(Long Id) throws NGException {
		logger.info("Inside getPrimaryKey method " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getUniqueGender", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		logger.info("Exit from getPrimaryKey method...rMaaz= "+rMaaz.get(0));
		return rMaaz.get(0);
	}
	
	public List<GenderMst> getByGenderName(String genderName) throws NGException {
		logger.info("Inside getByCityName method "  +genderName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("genderName", genderName);

		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getmaritalStatusName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		return rMaaz;
	}
	
	public List<GenderMst> getByGenderId(String genderId) throws NGException {
		logger.info("Inside getByGenderId method "  +genderId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("genderId", genderId);

		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getgenderId", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		return rMaaz;
	}
	
	public List<GenderMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method...authStatus= "+authStatus);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getByAuthStatus", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null; 
		}
		logger.debug("Exit from method"); 
		return rMaaz;
	}
	
	public String getGenderIdByGenderName(String genderName) throws NGException {
		logger.info("Inside getGenderIdByGenderName method "  +genderName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("genderName", genderName);

		String genderId = null;
		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getGenderIdByGenderName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			genderId = rMaaz.get(0).getGenderId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return genderId;
	}
	
	
	
	public List<GenderMst> getByDeleted(Boolean isDeleted) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<GenderMst> rMaaz = findByNamedQueryAndNamedParams("GenderMst.getByDeleted", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.debug("Exit from method");
		return rMaaz;
	}

	

	
}
