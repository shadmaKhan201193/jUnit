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
import com.itl.dao.masters.CasteDAO;
import com.itl.domain.entities.masters.CasteMst;

import com.itl.exceptions.NGException;

@Repository("casteDAO")
public class CasteDAOImpl extends JpaDAOImpl<Long, CasteMst> 
	implements CasteDAO {
    
	private static final Logger logger = LoggerFactory.getLogger(CasteDAOImpl.class);
	
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
	
	public CasteMst getPrimaryKey(Long Id) throws NGException {
		
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getUniqueCaste", queryParams);
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
	
	
	public List<CasteMst> getByCasteName(String casteName) throws NGException {
		logger.debug(" Enter Inside getByCasteName method");
		logger.info("CasteName=" + casteName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("casteName", casteName);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getcasteName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getByCasteName method");
		return rMaaz;
	}
	
	
	public List<CasteMst> getByCasteId(String casteId) throws NGException {
		logger.debug("Inside getByCasteId method ");
		logger.info("CasteId= "  +casteId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("casteId", casteId);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getcasteId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getByCasteId method ");
		return rMaaz;
	}
	
			
	public CasteMst getByReligionCaste(String religionId,String casteId) throws NGException {
		logger.debug("Inside getByReligionCaste method ");
		logger.info("ReligionCasteID= "  +casteId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("religionId", religionId);
		queryParams.put("casteId", casteId);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getByReligioncasteId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByReligionCaste method");
		return rMaaz.get(0);
	}
	
	
	public List<CasteMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getByAuthStatus", queryParams);
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
	
	
	public String getCasteIdByCasteName(String casteName) throws NGException {
		logger.info("Inside getCasteIdByCasteName method "  +casteName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("casteName", casteName);

		String casteId = null;
		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getCasteIdByCasteName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			casteId = rMaaz.get(0).getCasteId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return casteId;
	}
	
	
	

	public List<CasteMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<CasteMst> rMaaz = findByNamedQueryAndNamedParams("CasteMst.getByDeleted", queryParams);
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
