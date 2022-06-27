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
import com.itl.dao.masters.QualificationDAO;
import com.itl.domain.entities.masters.QualificationMst;
import com.itl.exceptions.NGException;

@Repository("qualificationDAO")
public class QualificationDAOImpl extends JpaDAOImpl<Long, QualificationMst> 
	implements QualificationDAO {
    
	private static final Logger logger = LoggerFactory.getLogger(QualificationDAOImpl.class);
	
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
	
	public QualificationMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getUniqueQualification", queryParams);
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
	
	public List<QualificationMst> getByQualificationName(String qualificationName) throws NGException {
		logger.debug("Inside getByQualificationName method ");
		logger.info("QualificationName "  +qualificationName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("qualificationName", qualificationName);

		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getqualificationName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByQualificationName method");
		return rMaaz;
	}
	
	public List<QualificationMst> getByQualificationId(String qualificationId) throws NGException {
		logger.debug("Inside getByQualificationId method ");
		logger.info("QualificationId= "  +qualificationId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("qualificationId", qualificationId);
		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getqualificationId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByQualificationId method");

		return rMaaz;
	}
	
	public List<QualificationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getByAuthStatus", queryParams);
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
		
	public String getQualificationIdByQualificationName(String qualificationName) throws NGException {
		logger.info("Inside getQualificationIdByQualificationName method "  +qualificationName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("qualificationName", qualificationName);

		String qualificationId = null;
		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getQualificationIdByQualificationName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			qualificationId = rMaaz.get(0).getQualificationId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return qualificationId;
	}
	
	public List<QualificationMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<QualificationMst> rMaaz = findByNamedQueryAndNamedParams("QualificationMst.getByDeleted", queryParams);
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
