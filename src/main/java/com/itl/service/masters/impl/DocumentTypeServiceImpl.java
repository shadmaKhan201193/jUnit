package com.itl.service.masters.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.configuration.CacheBuilder;
import com.itl.dao.base.JPADAO;
import com.itl.dao.masters.DocumentTypeDAO;
import com.itl.domain.entities.masters.DocumentTypeMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.DocumentTypeService;
import com.itl.utils.OmniConstants;

@Service("documentTypeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class DocumentTypeServiceImpl extends NGServiceImpl<Long, DocumentTypeMst>
	implements DocumentTypeService {   

	private static final Logger logger = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);
	 
	@Autowired
	protected DocumentTypeDAO documentTypeDAO;

	@Autowired
	private CacheBuilder cacheBuilder;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) documentTypeDAO);
		String fields[] = { "documentTypeId", "docTypeName" };
		String cacheContainerName = "documentTypeIdList";
		List<DocumentTypeMst> documentType= getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up documentType cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, documentType, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		documentTypeDAO.setEntityManager(entityManager);
	}
	
	
	public DocumentTypeMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		DocumentTypeMst masterObj = documentTypeDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	@Cacheable(value = "documentTypeIdList", key = "#docTypeName", unless="#result == null")
	public String getdocTypeIdBydocTypeName(String docTypeName) throws NGException {
		logger.info("Fetching from db - documentTypeId from documentTypeName", docTypeName);
		String value = documentTypeDAO.getdocTypeIdBydocTypeName(docTypeName);
		return value;
	}
	
	
	@Cacheable(value = "documentTypeIdList", key = "#documentTypeId", unless="#result == null")
	public DocumentTypeMst getBydocTypeId(String documentTypeId) throws NGException {
		logger.info("Fetching from db - documentType with documentTypeId", documentTypeId);
		List<DocumentTypeMst> masterList = documentTypeDAO.getBydocTypeId(documentTypeId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	
	@Cacheable(value = "documentTypeLists", key = "#authStatus", unless="#result == null")
	public List<DocumentTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - document with authStatus", authStatus);
		List<DocumentTypeMst> masterList = documentTypeDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	

	public List<DocumentTypeMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted method ");
		List<DocumentTypeMst> opStat = documentTypeDAO.getByDeleted(isDeleted);  
		logger.info("Exit from  getByDeleted method...opStat value= "+ opStat);
		return opStat;
	
	}

	
	@CachePut(value = "documentTypeLists", key = "#authStatus")
	public List<DocumentTypeMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of documentType with authStatus : {}", authStatus);
		List<DocumentTypeMst> documentType = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=documentType && documentType.size()>0)?documentType:null;
	}
	
	
	public DocumentTypeMst saveOrUpdate(String loginId, DocumentTypeMst entity) throws NGException {
		logger.info("PK>>ID::" + entity.getId());
		DocumentTypeMst rMaaz = getPrimaryKey(entity.getId());
		try {
			logger.debug("Inside try block ");
			if (null == rMaaz) {
				logger.debug("Inside if condition ... Create Mode ");
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.debug("Inside else Condition ");
				logger.debug("update mode ");
				// ---> update mode
				DocumentTypeMst rmfa = rMaaz;
				Mapper mapper = new DozerBeanMapper();
				mapper.map(entity, rmfa);
				return super.saveOrUpdate(loginId, rmfa);
			}
		} catch (Exception e) {
			logger.warn("Error for PK>>ID::");
			logger.warn(e.getMessage());
			e.printStackTrace();
			
		}
		return null;
	}

	
}
