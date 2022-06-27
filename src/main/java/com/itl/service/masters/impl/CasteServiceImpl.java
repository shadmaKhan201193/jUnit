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
import com.itl.dao.masters.CasteDAO;
import com.itl.domain.entities.masters.CasteMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.CasteService;
import com.itl.utils.OmniConstants;

@Service("casteService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class CasteServiceImpl extends NGServiceImpl<Long, CasteMst> implements CasteService {

	private static final Logger logger = LoggerFactory.getLogger(CasteServiceImpl.class);
	
	@Autowired
	protected CasteDAO casteDAO;

	@Autowired
	private CacheBuilder cacheBuilder;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) casteDAO);
		String fields[] = { "casteId", "casteName" };
		String cacheContainerName = "casteIdList";
		List<CasteMst> caste = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up caste cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, caste, initObjCache);
	}
	
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		casteDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public CasteMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		CasteMst masterObj = casteDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}

	@Cacheable(value = "casteIdList", key = "#casteName", unless="#result == null")
	public String getCasteIdByCasteName(String casteName) throws NGException {
		logger.info("Fetching from db - casteId from casteName", casteName);
		String value = casteDAO.getCasteIdByCasteName(casteName);
		return value;
	}
	
	
	@Cacheable(value = "casteIdList", key = "#casteId", unless="#result == null")
	public CasteMst getByCasteId(String casteId) throws NGException {
		logger.info("Fetching from db - caste with casteId", casteId);
		List<CasteMst> masterList = casteDAO.getByCasteId(casteId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}
	
	
	@Cacheable(value = "casteLists", key = "#authStatus", unless="#result == null")
	public List<CasteMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - caste with authStatus", authStatus);
		List<CasteMst> masterList = casteDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	
	@CachePut(value = "casteLists", key = "#authStatus")
	public List<CasteMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of caste with authStatus : {}", authStatus);
		List<CasteMst> caste = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=caste && caste.size()>0)?caste:null;
	}
	
	public List<CasteMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Get caste by deleted status.");
		List<CasteMst> opStat = casteDAO.getByDeleted(isDeleted);  		
		return opStat;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "casteIdList", key = "#result.casteName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "casteIdList", key = "#result.casteId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "casteIdList", key = "#result.casteId", condition = "#result.authStatus == 'A'")
			})
	public CasteMst saveOrUpdate(String loginId, CasteMst entity) throws NGException {
		CasteMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add caste by casteId : {}", entity.getCasteId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update caste record with casteId : {}", entity.getCasteId());
				CasteMst rmfa = rMaaz;
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
