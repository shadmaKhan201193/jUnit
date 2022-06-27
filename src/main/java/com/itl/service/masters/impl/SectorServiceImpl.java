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
import com.itl.dao.masters.SectorDAO;
import com.itl.domain.entities.masters.SectorMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.SectorService;
import com.itl.utils.OmniConstants;

@Service("SectorService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class SectorServiceImpl extends NGServiceImpl<Long, SectorMst>
	implements SectorService {
	
	private static final Logger logger = LoggerFactory.getLogger(SectorServiceImpl.class);
	
	@Autowired
	protected SectorDAO sectorDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) sectorDAO);
		String fields[] = { "sectorId", "sectorName" };
		String cacheContainerName = "sectorIdList";
		List<SectorMst> countries = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up sector cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, countries, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		sectorDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public SectorMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		SectorMst masterObj = sectorDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	@Cacheable(value = "sectorIdList", key = "#sectorName", unless="#result == null")
	public String getSectorIdBySectorName(String sectorName) throws NGException {
		logger.info("Fetching from db - sectorId from sectorName", sectorName);
		String value = sectorDAO.getSectorIdBySectorName(sectorName);
		return value;
	}
	
	
	
	@Cacheable(value = "sectorIdList", key = "#sectorId", unless="#result == null")
	public SectorMst getBySectorId(String sectorId) throws NGException {
		logger.info("Fetching from db - sector with sectorId", sectorId);
		List<SectorMst> masterList = sectorDAO.getBySectorId(sectorId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	@Cacheable(value = "sectorLists", key = "#authStatus", unless="#result == null")
	public List<SectorMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - sector with authStatus", authStatus);
		List<SectorMst> masterList = sectorDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}

	
	
	public List<SectorMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Go Inside getByDeleted method ");
		List<SectorMst> opStat = sectorDAO.getByDeleted( isDeleted);  
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}
	
	@CachePut(value = "sectorLists", key = "#authStatus")
	public List<SectorMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of sector with authStatus : {}", authStatus);
		List<SectorMst> sector  = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=sector && sector.size()>0)?sector:null;
	}

	
	@Caching(evict = {
			@CacheEvict(value = "sectorIdList", key = "#result.sectorName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "sectorIdList", key = "#result.sectorId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "sectorIdList", key = "#result.sectorId", condition = "#result.authStatus == 'A'")
			})
	public SectorMst saveOrUpdate(String loginId, SectorMst entity) throws NGException {
		SectorMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add sector by sectorId : {}", entity.getSectorId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update sector record with sectorId : {}", entity.getSectorId());
				SectorMst rmfa = rMaaz;
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
