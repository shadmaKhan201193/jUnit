package com.itl.service.masters.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.configuration.CacheBuilder;
import com.itl.dao.base.JPADAO;
import com.itl.dao.masters.CountryDAO;
import com.itl.domain.entities.masters.CountryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.CountryService;
import com.itl.utils.OmniConstants;

@Service("countryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class CountryServiceImpl extends NGServiceImpl<Long, CountryMst> implements CountryService {

	private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	@Autowired
	protected CountryDAO countryDAO;
	
	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) countryDAO);
		String fields[] = { "countryId", "countryName" };
		String cacheContainerName = "countryIdList";
		List<CountryMst> countries = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up country cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, countries, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		countryDAO.setEntityManager(entityManager);
	}

	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public CountryMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		CountryMst masterObj = countryDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	
	@Cacheable(value = "countryIdList", key = "#countryName", unless="#result == null")
	public String getCountryIdByCountryName(String countryName) throws NGException {
		logger.info("Fetching from db - countryId from countryName", countryName);
		String value = countryDAO.getCountryIdByCountryName(countryName);
		return value;
	}
	

	@Cacheable(value = "countryIdList", key = "#countryId", unless="#result == null")
	public CountryMst getByCountryId(String countryId) throws NGException {
		logger.info("Fetching from db - country with countryId", countryId);
		List<CountryMst> masterList = countryDAO.getByCountryId(countryId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	
	@Cacheable(value = "countryLists", key = "#authStatus", unless="#result == null")
	public List<CountryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - countries with authStatus", authStatus);
		List<CountryMst> masterList = countryDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}

	@Caching(evict = {
			@CacheEvict(value = "countryIdList", key = "#result.countryName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "countryIdList", key = "#result.countryId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "countryIdList", key = "#result.countryId", condition = "#result.authStatus == 'A'")
			})
	@Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100, maxDelay = 500),
			include = {ObjectOptimisticLockingFailureException.class, StaleObjectStateException.class,
					OptimisticLockException.class, DataIntegrityViolationException.class})
	public CountryMst saveOrUpdate(String loginId, CountryMst entity) throws NGException {
		CountryMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add country by countryId : {}", entity.getCountryId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update country record with countryId : {}", entity.getCountryId());
				CountryMst rmfa = rMaaz;
				Mapper mapper = new DozerBeanMapper();
				mapper.map(entity, rmfa);
				return super.saveOrUpdate(loginId, rmfa);
			}
		} catch (ObjectOptimisticLockingFailureException opt) {
			logger.warn("Error for ObjectOptimisticLockingFailureException");
			logger.warn(opt.getMessage());
		} catch (Exception e) {
			logger.warn("Error for PK>>ID::");
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	@CachePut(value = "countryLists", key = "#authStatus")
	public List<CountryMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of countries with authStatus : {}", authStatus);
		List<CountryMst> countries = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=countries && countries.size()>0)?countries:null;
	}

	
	public List<CountryMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Get countries by deleted status.");
		List<CountryMst> masterList = countryDAO.getByDeleted(isDeleted);
		return masterList;
	}
}
