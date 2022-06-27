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
import com.itl.dao.masters.OrgTypeDAO;
import com.itl.domain.entities.masters.OrgTypeMst;
import com.itl.exceptions.NGException;

@Repository("orgTypeDAO")
public class OrgTypeDAOImpl extends JpaDAOImpl<Long, OrgTypeMst> 
	implements OrgTypeDAO {
     
	private static final Logger logger = LoggerFactory.getLogger(OrgTypeDAOImpl.class);
	
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
	
	public OrgTypeMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getUniqueOrgType", queryParams);
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
	
	public List<OrgTypeMst> getByOrgTypeName(String orgTypeName) throws NGException {
		logger.debug("Inside getByOrgTypeName method ");
		logger.info("OrgTypeName "  +orgTypeName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("orgTypeName", orgTypeName);

		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getOrgTypeName", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getByOrgTypeName method");
		return rMaaz;
	}
	
	public List<OrgTypeMst> getByOrgTypeId(String orgTypeId) throws NGException {
		logger.debug("Inside getByOrgTypeId  method ");
		logger.info("OrgTypeId= "  +orgTypeId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("orgTypeId", orgTypeId);

		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getorgTypeId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByOrgTypeId method");
		return rMaaz;
	}
	
	
	public List<OrgTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getByAuthStatus", queryParams);
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

	public String getOrgTypeIdByOrgTypeName(String orgTypeName) throws NGException {
		logger.info("Inside getOrgTypeIdByOrgTypeName method "  +orgTypeName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("orgTypeName", orgTypeName);

		String orgTypeId = null;
		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getOrgTypeIdByOrgTypeName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			orgTypeId = rMaaz.get(0).getOrgTypeId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return orgTypeId;
	}
	
	
	public List<OrgTypeMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<OrgTypeMst> rMaaz = findByNamedQueryAndNamedParams("OrgTypeMst.getByDeleted", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside else if condition");
			return null;
		}
		logger.debug("Exit from getByDeleted method");
		return rMaaz;
	}
}
