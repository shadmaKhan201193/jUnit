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
import com.itl.dao.masters.CityDAO;
import com.itl.domain.entities.masters.CityMst;

import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.CityService;
import com.itl.utils.OmniConstants;

@Service("cityService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class CityServiceImpl extends NGServiceImpl<Long, CityMst>
	implements CityService {

	private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	
	@Autowired
	protected CityDAO cityDAO;
	
	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) cityDAO);
		String fields[] = { "cityId", "cityName" };
		String cacheContainerName = "cityIdList";
		List<CityMst> city = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up city cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, city, initObjCache);
	}
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		cityDAO.setEntityManager(entityManager);
	}
	
	
	private static final String CONTROLLED_PREFIX = "AuthList";

    public static String getCacheKey(){
        return CONTROLLED_PREFIX;
    }
    
    public CityMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		CityMst masterObj = cityDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
    
    
    @Cacheable(value = "cityIdList", key = "#cityName", unless="#result == null")
	public String getCityIdByCityName(String cityName) throws NGException {
		logger.info("Fetching from db - cityId from cityName", cityName);
		String value = cityDAO.getCityIdByCityName(cityName);
		return value;
	}


    @Cacheable(value = "cityIdList", key = "#cityId", unless="#result == null")
	public CityMst getByCityId(String cityId) throws NGException {
		logger.info("Fetching from db - city with cityId", cityId);
		List<CityMst> masterList = cityDAO.getByCityId(cityId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

    @Cacheable(value = "cityLists", key = "#authStatus", unless="#result == null")
	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - city with authStatus", authStatus);
		List<CityMst> masterList = cityDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}

    public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Get city by deleted status.");
		List<CityMst> masterList = cityDAO.getByDeleted(isDeleted);
		return masterList;
	}
	
	
    @Caching(evict = {
			@CacheEvict(value = "cityIdList", key = "#result.cityName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "cityIdList", key = "#result.cityId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "cityIdList", key = "#result.cityId", condition = "#result.authStatus == 'A'")
			})
	public CityMst saveOrUpdate(String loginId, CityMst entity) throws NGException {
    	CityMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add city by cityId : {}", entity.getCityId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update city record with cityId : {}", entity.getCityId());
				CityMst rmfa = rMaaz;
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
	
    @CachePut(value = "cityLists", key = "#authStatus")
	public List<CityMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of countries with authStatus : {}", authStatus);
		List<CityMst> city = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=city && city.size()>0)?city:null;
	}
	
}
