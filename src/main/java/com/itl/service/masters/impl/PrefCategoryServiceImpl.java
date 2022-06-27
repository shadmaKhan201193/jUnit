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
import com.itl.dao.masters.PrefCategoryDAO;
import com.itl.domain.entities.masters.PrefCategoryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.PrefCategoryService;
import com.itl.utils.OmniConstants;

@Service("prefCategoryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class PrefCategoryServiceImpl extends NGServiceImpl<Long, PrefCategoryMst>
	implements PrefCategoryService {

	private static final Logger logger = LoggerFactory.getLogger(PrefCategoryServiceImpl.class);
	
	@Autowired
	protected PrefCategoryDAO prefCategoryDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) prefCategoryDAO);
		String fields[] = { "prefCategoryId", "prefCategoryName" };
		String cacheContainerName = "prefCategoryList";
		List<PrefCategoryMst> prefCategory = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up prefCategory cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, prefCategory, initObjCache);
	}
	@PreDestroy
	public void destroy() {
	}
	
	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		prefCategoryDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public PrefCategoryMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		PrefCategoryMst masterObj = prefCategoryDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	
	
	@Cacheable(value = "prefCategoryList", key = "#prefCategoryName", unless="#result == null")
	public String getPrefCategoryIdByPrefCategoryName(String prefCategoryName) throws NGException {
		logger.info("Fetching from db - prefCategoryId from prefCategoryName", prefCategoryName);
		String value = prefCategoryDAO.getPrefCategoryIdByPrefCategoryName(prefCategoryName);
		return value;
	}
	

	@Cacheable(value = "prefCategoryList", key = "#prefCategoryId", unless="#result == null")
	public PrefCategoryMst getByPrefCategoryId(String prefCategoryId) throws NGException {
		logger.info("Fetching from db - prefCategory with prefCategoryId", prefCategoryId);
		List<PrefCategoryMst> masterList = prefCategoryDAO.getByPrefCategoryId(prefCategoryId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	
	@Cacheable(value = "prefCategoryLists", key = "#authStatus", unless="#result == null")
	public List<PrefCategoryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - prefCategory with authStatus", authStatus);
		List<PrefCategoryMst> masterList = prefCategoryDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<PrefCategoryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Go Inside getByDeleted method ");
		List<PrefCategoryMst> opStat = prefCategoryDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}
		
	
	@CachePut(value = "prefCategoryLists", key = "#authStatus")
	public List<PrefCategoryMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of prefCategory with authStatus : {}", authStatus);
		List<PrefCategoryMst> prefCategory = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=prefCategory && prefCategory.size()>0)?prefCategory:null;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "prefCategoryList", key = "#result.prefCategoryName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "prefCategoryList", key = "#result.prefCategoryId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "prefCategoryList", key = "#result.prefCategoryId", condition = "#result.authStatus == 'A'")
			})
	public PrefCategoryMst saveOrUpdate(String loginId, PrefCategoryMst entity) throws NGException {
		PrefCategoryMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add prefCategory by prefCategoryId : {}", entity.getPrefCategoryId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update prefCategory record with prefCategoryId : {}", entity.getPrefCategoryId());
				PrefCategoryMst rmfa = rMaaz;
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
