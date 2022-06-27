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
import com.itl.dao.masters.ReligionDAO;
import com.itl.domain.entities.masters.ReligionMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.ReligionService;
import com.itl.utils.OmniConstants;

@Service("religionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class ReligionServiceImpl extends NGServiceImpl<Long, ReligionMst>
	implements ReligionService {

	private static final Logger logger = LoggerFactory.getLogger(ReligionServiceImpl.class);
	
	@Autowired
	protected ReligionDAO religionDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) religionDAO);
		String fields[] = { "religionId", "religionName" };
		String cacheContainerName = "religionIdList";
		List<ReligionMst> religion = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up religion cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, religion, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		religionDAO.setEntityManager(entityManager);
	}
		
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public ReligionMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		ReligionMst masterObj = religionDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	
	@Cacheable(value = "religionIdList", key = "#religionName", unless="#result == null")
	public ReligionMst getReligionIdByReligionName(String religionName) throws NGException {
		logger.info("Fetching from db - religionId from religionName", religionName);
		ReligionMst value = religionDAO.getReligionIdByReligionName(religionName);
		return value;
	}
	

	@Cacheable(value = "religionIdList", key = "#religionId", unless="#result == null")
	public ReligionMst getByReligionId(String religionId) throws NGException {
		logger.info("Fetching from db - religion with religionId", religionId);
		List<ReligionMst> masterList = religionDAO.getByReligionId(religionId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	
	@Cacheable(value = "religionLists", key = "#authStatus", unless="#result == null")
	public List<ReligionMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - religion with authStatus", authStatus);
		List<ReligionMst> masterList = religionDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<ReligionMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info(" vALUInside getByDeleted method ");
		List<ReligionMst> opStat = religionDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from getByDeleted");
		return opStat;
	}
	

	@CachePut(value = "religionLists", key = "#authStatus")
	public List<ReligionMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of religion with authStatus : {}", authStatus);
		List<ReligionMst> religion = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=religion && religion.size()>0)?religion:null;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "religionIdList", key = "#result.religionName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "religionIdList", key = "#result.religionId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "religionIdList", key = "#result.religionId", condition = "#result.authStatus == 'A'")
			})
	public ReligionMst saveOrUpdate(String loginId, ReligionMst entity) throws NGException {
		ReligionMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add religion by religionId : {}", entity.getReligionId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update religion record with religionId : {}", entity.getReligionId());
				ReligionMst rmfa = rMaaz;
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
