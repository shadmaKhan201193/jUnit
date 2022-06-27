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
import com.itl.dao.masters.BranchDAO;
import com.itl.domain.entities.masters.BranchMst;
import com.itl.exceptions.NGException;

@Repository("branchDAO")
public class BranchDAOImpl extends JpaDAOImpl<Long, BranchMst> 
	implements BranchDAO {
    
	private static final Logger logger = LoggerFactory.getLogger(BranchDAOImpl.class);
	
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
	
	public BranchMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey method ");
		logger.info("PK ID= " + Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getUniqueBranch", queryParams);
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
	
	public List<BranchMst> getByBranchName(String branchName) throws NGException {
		logger.debug("Inside getByBranchName method ");
		logger.info("BranchName "  +branchName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("branchName", branchName);

		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getBranchName", queryParams);
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
	
	public List<BranchMst> getByBranchId(String branchId) throws NGException {
		logger.debug("Inside getByBranchId  method ");
		logger.info("BranchId= "  +branchId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("branchId", branchId);

		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getBranchId", queryParams);
		if (null == rMaaz) {
			logger.info("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.info("Inside elseif condition");
			return null;
		}
		logger.debug("Exit from getByOrgTypeId method");
		return rMaaz;
	}
	
	
	public List<BranchMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getByAuthStatus", queryParams);
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
	
	
	public List<BranchMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByAuthStatus method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("isDeleted", isDeleted);

		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getByDeleted", queryParams);
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

	@Override
	public String getBranchIdByBranchName(String branchName) throws NGException {
		logger.info("Inside getBranchIdByBranchName method "  +branchName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("branchName", branchName);

		String branchId = null;
		List<BranchMst> rMaaz = findByNamedQueryAndNamedParams("BranchMst.getBranchIdByBranchName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			branchId = rMaaz.get(0).getBranchId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return branchId;
	}
}
