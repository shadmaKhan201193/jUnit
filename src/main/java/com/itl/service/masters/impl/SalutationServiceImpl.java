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
import com.itl.dao.masters.SalutationDAO;
import com.itl.domain.entities.masters.SalutationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.SalutationService;
import com.itl.utils.OmniConstants;

@Service("salutationService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class SalutationServiceImpl extends NGServiceImpl<Long, SalutationMst>
	implements SalutationService {

	private static final Logger logger = LoggerFactory.getLogger(SalutationServiceImpl.class);
	
	@Autowired
	protected SalutationDAO salutationDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) salutationDAO);
		String fields[] = { "salutationId", "salutationName" };
		String cacheContainerName = "salutationIdList";
		List<SalutationMst> countries = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up salutation cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, countries, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		salutationDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public SalutationMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		SalutationMst masterObj = salutationDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	

	@Cacheable(value = "salutationIdList", key = "#salutationName", unless="#result == null")
	public String getSalutationIdBySalutationName(String salutationName) throws NGException {
		logger.info("Fetching from db - salutationId from salutationName", salutationName);
		String value = salutationDAO.getSalutationIdBySalutationName(salutationName);
		return value;
	}
	
	@Cacheable(value = "salutationIdList", key = "#salutationId", unless="#result == null")
	public SalutationMst getBySalutationId(String salutationId) throws NGException {
		logger.info("Fetching from db - salutation with salutationId", salutationId);
		List<SalutationMst> masterList = salutationDAO.getBySalutationId(salutationId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	@Cacheable(value = "salutationLists", key = "#authStatus", unless="#result == null")
	public List<SalutationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - salutation with authStatus", authStatus);
		List<SalutationMst> masterList = salutationDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<SalutationMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info(" getByDeleted method ");
		List<SalutationMst> opStat = salutationDAO.getByDeleted( isDeleted);  
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}
	
	@CachePut(value = "salutationLists", key = "#authStatus")
	public List<SalutationMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of countries with authStatus : {}", authStatus);
		List<SalutationMst> salutation = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=salutation && salutation.size()>0)?salutation:null;
	}

	
	
	@Caching(evict = {
			@CacheEvict(value = "salutationIdList", key = "#result.salutationName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "salutationIdList", key = "#result.salutationId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "salutationIdList", key = "#result.salutationId", condition = "#result.authStatus == 'A'")
			})
	public SalutationMst saveOrUpdate(String loginId, SalutationMst entity) throws NGException {
		SalutationMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add salutation by salutationId : {}", entity.getSalutationId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update salutation record with salutationId : {}", entity.getSalutationId());
				SalutationMst rmfa = rMaaz;
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
