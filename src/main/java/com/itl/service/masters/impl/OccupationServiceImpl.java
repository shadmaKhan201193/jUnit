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
import com.itl.dao.masters.OccupationDAO;
import com.itl.domain.entities.masters.OccupationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.OccupationService;
import com.itl.utils.OmniConstants;

@Service("occupationService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class OccupationServiceImpl extends NGServiceImpl<Long, OccupationMst>
	implements OccupationService {

	private static final Logger logger = LoggerFactory.getLogger(OccupationServiceImpl.class);
	
	@Autowired
	protected OccupationDAO occupationDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) occupationDAO);
		String fields[] = { "occupationId", "occupationName" };
		String cacheContainerName = "occupationIdList";
		List<OccupationMst> occupation = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up occupation cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, occupation, initObjCache);
	}
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		occupationDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public OccupationMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		OccupationMst masterObj = occupationDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	@Cacheable(value = "occupationIdList", key = "#occupationName", unless="#result == null")
	public String getOccupationIdByOccupationName(String occupationName) throws NGException {
		logger.info("Fetching from db - occupationId from occupationName", occupationName);
		String value = occupationDAO.getOccupationIdByOccupationName(occupationName);
		return value;
	}

	
	@Cacheable(value = "occupationIdList", key = "#occupationId", unless="#result == null")
	public OccupationMst getByOccupationId(String occupationId) throws NGException {
		logger.info("Fetching from db - occupation with occupationId", occupationId);
		List<OccupationMst> masterList = occupationDAO.getByOccupationId(occupationId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	@Cacheable(value = "occupationLists", key = "#authStatus", unless="#result == null")
	public List<OccupationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - countries with authStatus", authStatus);
		List<OccupationMst> masterList = occupationDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<OccupationMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Inside getByDeleted method ");
		List<OccupationMst> opStat = occupationDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from method"); 
		return opStat;
	}
	
	
	@CachePut(value = "occupationLists", key = "#authStatus", unless="#result == null")
	public List<OccupationMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of countries with authStatus : {}", authStatus);
		List<OccupationMst> occupation = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=occupation && occupation.size()>0)?occupation:null;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "occupationIdList", key = "#result.occupationName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "occupationIdList", key = "#result.occupationId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "occupationIdList", key = "#result.occupationId", condition = "#result.authStatus == 'A'")
			})
	public OccupationMst saveOrUpdate(String loginId, OccupationMst entity) throws NGException {
		OccupationMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add occupation by occupationId : {}", entity.getOccupationId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update occupation record with occupationId : {}", entity.getOccupationId());
				OccupationMst rmfa = rMaaz;
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
