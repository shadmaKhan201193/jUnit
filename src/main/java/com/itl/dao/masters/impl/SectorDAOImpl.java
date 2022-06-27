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
import com.itl.dao.masters.SectorDAO;
import com.itl.domain.entities.masters.SectorMst;
import com.itl.exceptions.NGException;

@Repository("sectorDAO")
public class SectorDAOImpl extends JpaDAOImpl<Long, SectorMst> 
	implements SectorDAO {

	private static final Logger logger = LoggerFactory.getLogger(SectorDAOImpl.class);
	
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
	
	public SectorMst getPrimaryKey(Long Id) throws NGException {
		
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getUniqueSector", queryParams);
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
	
	public List<SectorMst> getBySectorName(String sectorName) throws NGException {
		logger.debug(" Enter Inside getBySectorName method");
		logger.info("SectorName=" + sectorName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("sectorName", sectorName);

		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getSectorName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getBySectorName method");
		return rMaaz;
	}
	
	public List<SectorMst> getBySectorId(String sectorId) throws NGException {
		logger.debug("Inside getBySectorId method ");
		logger.info("SectorId= "  +sectorId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("sectorId", sectorId);

		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getSectorId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getBySectorId method");
		return rMaaz;
	}
	
	
	
	public List<SectorMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);

		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getByAuthStatus", queryParams);
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
	
	public String getSectorIdBySectorName(String sectorName) throws NGException {
		logger.info("Inside getSectorIdBySectorName method "  +sectorName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("sectorName", sectorName);

		String sectorId = null;
		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getSectorIdBySectorName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			sectorId = rMaaz.get(0).getSectorId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return sectorId;
	}
	
	
	public List<SectorMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<SectorMst> rMaaz = findByNamedQueryAndNamedParams("SectorMst.getByDeleted", queryParams);
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
