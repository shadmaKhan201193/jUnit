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
import com.itl.dao.masters.DocumentTypeDAO;
import com.itl.domain.entities.masters.DocumentTypeMst;
import com.itl.exceptions.NGException;

@Repository("documentTypeDAO")
public class DocumentTypeDAOImpl extends JpaDAOImpl<Long, DocumentTypeMst> 
	implements DocumentTypeDAO {
	private static final Logger logger = LoggerFactory.getLogger(DocumentTypeDAOImpl.class);
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
	
	public DocumentTypeMst getPrimaryKey(Long Id) throws NGException {
		logger.info("Inside getPrimaryKey method ID= " +Id);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getUniqueDocumentType", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from method....rMaaz value= " + rMaaz.get(0));
		return rMaaz.get(0);
	}
	
	public List<DocumentTypeMst> getByDocTypeName(String docTypeName) throws NGException {
		logger.info("Inside getByDocTypeName method...docTypeName= "+ docTypeName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("docTypeName", docTypeName);

		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getdocTypeName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from method ...rMaaz = " +rMaaz);
		return rMaaz;
	}
	
	public List<DocumentTypeMst> getBydocTypeId(String docTypeId) throws NGException {
		logger.info("Inside getByDocTypeId method... docTypeId= " +docTypeId);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("docTypeId", docTypeId);

		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getdocTypeId", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if condition");
			return null;
		}
		logger.info("Exit from method ...rMaaz = " +rMaaz);
		return rMaaz;
	}
	
	public List<DocumentTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Inside getByAuthStatus method "  +authStatus );
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.info("Before adding authStatus and isDeleted in a queryParams " +authStatus); 
		queryParams.put("authStatus", authStatus);
		queryParams.put("isDeleted", isDeleted);
		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getByAuthStatus", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
	
	
	public String getdocTypeIdBydocTypeName(String docTypeName) throws NGException {
		logger.info("Inside getdocTypeIdBydocTypeName method "  +docTypeName);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("docTypeName", docTypeName);

		String documentTypeId = null;
		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getdocTypeIdBydocTypeName", queryParams);
		if (null == rMaaz) {
			logger.debug("Inside if condition");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		} else {
			documentTypeId = rMaaz.get(0).getDocTypeId();
		}
		logger.info("Exit from method..rMaaz " + rMaaz);
		return documentTypeId;
	}
	
	
	public List<DocumentTypeMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted method");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		logger.debug("Before adding isDeleted  in a queryParams");
		queryParams.put("isDeleted", isDeleted);

		List<DocumentTypeMst> rMaaz = findByNamedQueryAndNamedParams("DocumentTypeMst.getByDeleted", queryParams);
		logger.info("After Query Execution " +rMaaz );
		if (null == rMaaz) {
			logger.debug("Inside if Condition ");
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			logger.debug("Inside else if Condition ");
			return null;
		}
		logger.info("Before Return " +rMaaz.get(0) );
		return rMaaz;
	}
}
