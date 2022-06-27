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
import com.itl.dao.masters.IndustryDAO;
import com.itl.domain.entities.masters.IndustryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.IndustryService;
import com.itl.utils.OmniConstants;

@Service("industryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class IndustryServiceImpl extends NGServiceImpl<Long, IndustryMst>
	implements IndustryService {

	private static final Logger logger = LoggerFactory.getLogger(IndustryServiceImpl.class);
	
	@Autowired
	protected IndustryDAO industryDAO;
	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) industryDAO);
		String fields[] = { "industryId", "industryName" };
		String cacheContainerName = "industryIdList";
		List<IndustryMst> industry = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up industry cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, industry, initObjCache);
	}
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		industryDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public IndustryMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		IndustryMst masterObj = industryDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	@Cacheable(value = "industryIdList", key = "#industryName", unless="#result == null")
	public String getIndustryIdByIndustryName(String industryName) throws NGException {
		logger.info("Fetching from db - industryId from industryName", industryName);
		String value = industryDAO.getIndustryIdByIndustryName(industryName);
		return value;
	}

	@Cacheable(value = "industryIdList", key = "#industryId", unless="#result == null")
	public IndustryMst getByIndustryId(String industryId) throws NGException {
		logger.info("Fetching from db - industry with industryId", industryId);
		List<IndustryMst> masterList = industryDAO.getByIndustryId(industryId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	
	@Cacheable(value = "industryLists", key = "#authStatus", unless="#result == null")
	public List<IndustryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - industry with authStatus", authStatus);
		List<IndustryMst> masterList = industryDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	

	@CachePut(value = "industryLists", key = "#authStatus")
	public List<IndustryMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of industry with authStatus : {}", authStatus);
		List<IndustryMst> industry = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=industry && industry.size()>0)?industry:null;
	}

	public List<IndustryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.info("Inside getByDeleted method ");
		List<IndustryMst> opStat = industryDAO.getByDeleted(isDeleted);   
		logger.debug("Exit from  getByDeleted method");  
		return opStat;
	}
	
	public IndustryMst saveOrUpdate(String loginId, IndustryMst entity) throws NGException {
		logger.debug("Inside saveOrUpdate method ");
		logger.info("PK>>ID::" + entity.getId());
		IndustryMst rMaaz = getPrimaryKey(entity.getId());
		try {
			logger.debug("Inside try block ");
			if (null == rMaaz) {
				logger.debug("Inside if condition ... Create Mode ");
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.debug("Inside else Condition ");
				logger.debug("update mode ");
				// ---> update mode
				IndustryMst rmfa = rMaaz;
				Mapper mapper = new DozerBeanMapper();
				mapper.map(entity, rmfa);
				return super.saveOrUpdate(loginId, rmfa);
			}
		} catch (Exception e) {
			logger.warn("Error for PK");
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
