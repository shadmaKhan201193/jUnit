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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.configuration.CacheBuilder;
import com.itl.dao.base.JPADAO;
import com.itl.dao.masters.QualificationDAO;
import com.itl.domain.entities.masters.QualificationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.QualificationService;
import com.itl.utils.OmniConstants;

@Service("qualificationService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class QualificationServiceImpl extends NGServiceImpl<Long, QualificationMst>
	implements QualificationService {

	private static final Logger logger = LoggerFactory.getLogger(QualificationServiceImpl.class);
	
	@Autowired
	protected QualificationDAO qualificationDAO;
	
	@Autowired
	private CacheBuilder cacheBuilder;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) qualificationDAO);
		String fields[] = { "qualificationId", "qualificationName" };
		String cacheContainerName = "qualificationIdList";
		List<QualificationMst> qualification = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up qualification cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, qualification, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		qualificationDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public QualificationMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		QualificationMst masterObj = qualificationDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	
	@Cacheable(value = "qualificationIdList", key = "#qualificationName", unless="#result == null")
	public String getQualificationIdByQualificationName(String qualificationName) throws NGException {
		logger.info("Fetching from db - qualificationId from qualificationName", qualificationName);
		String value = qualificationDAO.getQualificationIdByQualificationName(qualificationName);
		return value;
	}
	

	@Cacheable(value = "qualificationIdList", key = "#qualificationId", unless="#result == null")
	public QualificationMst getByQualificationId(String qualificationId) throws NGException {
		logger.info("Fetching from db - qualification with qualificationId", qualificationId);
		List<QualificationMst> masterList = qualificationDAO.getByQualificationId(qualificationId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	
	@Cacheable(value = "qualificatioLists", key = "#authStatus", unless="#result == null")
	public List<QualificationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - qualification with authStatus", authStatus);
		List<QualificationMst> masterList = qualificationDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<QualificationMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Go Inside getByDeleted method ");
		List<QualificationMst> opStat = qualificationDAO.getByDeleted( isDeleted);  
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "qualificationIdList", key = "#result.qualificationName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "qualificationIdList", key = "#result.qualificationId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "qualificationIdList", key = "#result.qualificationId", condition = "#result.authStatus == 'A'")
			})
	public QualificationMst saveOrUpdate(String loginId, QualificationMst entity) throws NGException {
		QualificationMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add qualification by qualificationId : {}", entity.getQualificationId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update qualification record with qualificationId : {}", entity.getQualificationId());
				QualificationMst rmfa = rMaaz;
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
	
	

	@CachePut(value = "qualificatioLists", key = "#authStatus")
	public List<QualificationMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of qualification with authStatus : {}", authStatus);
		List<QualificationMst> qualification = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=qualification && qualification.size()>0)?qualification:null;
	}
}
