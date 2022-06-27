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
import com.itl.dao.masters.ResidentialStatusDAO;
import com.itl.domain.entities.masters.ResidentialStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.ResidentialStatusService;
import com.itl.utils.OmniConstants;

@Service("ResidentialStatusService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class ResidentialStatusServiceImpl extends NGServiceImpl<Long, ResidentialStatusMst>
	implements ResidentialStatusService {

	private static final Logger logger = LoggerFactory.getLogger(ResidentialStatusServiceImpl.class);
	
	@Autowired
	protected ResidentialStatusDAO residentialStatusDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) residentialStatusDAO);
		String fields[] = { "residentialStatusId", "residentialStatusName" };
		String cacheContainerName = "residentialStatusIdList";
		List<ResidentialStatusMst> residentialStatus = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up residentialStatus cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, residentialStatus, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		residentialStatusDAO.setEntityManager(entityManager);
	}
	

	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public ResidentialStatusMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		ResidentialStatusMst masterObj = residentialStatusDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	

	@Cacheable(value = "residentialStatusIdList", key = "#residentialStatusName", unless="#result == null")
	public String getResidentialStatusIdByResidentialStatusName(String residentialStatusName) throws NGException {
		logger.info("Fetching from db - residentialStatusId from residentialStatusName", residentialStatusName);
		String value = residentialStatusDAO.getResidentialStatusIdByResidentialStatusName(residentialStatusName);
		return value;
	}
	

	@Cacheable(value = "residentialStatusIdList", key = "#residentialStatusId", unless="#result == null")
	public ResidentialStatusMst getByResidentialStatusId(String residentialStatusId) throws NGException {
		logger.info("Fetching from db - residentialStatus with residentialStatusId", residentialStatusId);
		List<ResidentialStatusMst> masterList = residentialStatusDAO.getByResidentialStatusId(residentialStatusId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	@Cacheable(value = "residentialStatusLists", key = "#authStatus", unless="#result == null")
	public List<ResidentialStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - ResidentialStatus with authStatus", authStatus);
		List<ResidentialStatusMst> masterList = residentialStatusDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	
	@CachePut(value = "residentialStatusLists", key = "#authStatus")
	public List<ResidentialStatusMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of ResidentialStatus with authStatus : {}", authStatus);
		List<ResidentialStatusMst> residentialStatus = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=residentialStatus && residentialStatus.size()>0)?residentialStatus:null;
	}

	public List<ResidentialStatusMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info(" getByDeleted method ");
		List<ResidentialStatusMst> opStat = residentialStatusDAO.getByDeleted(isDeleted);
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "residentialStatusIdList", key = "#result.residentialStatusName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "residentialStatusIdList", key = "#result.residentialStatusId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "residentialStatusIdList", key = "#result.residentialStatusId", condition = "#result.authStatus == 'A'")
			})
	public ResidentialStatusMst saveOrUpdate(String loginId, ResidentialStatusMst entity) throws NGException {
		ResidentialStatusMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add residentialStatus by residentialStatusId : {}", entity.getResidentialStatusId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update residentialStatus record with residentialStatusId : {}", entity.getResidentialStatusId());
				ResidentialStatusMst rmfa = rMaaz;
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
