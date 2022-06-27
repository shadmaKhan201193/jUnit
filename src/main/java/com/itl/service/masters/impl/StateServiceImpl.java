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
import com.itl.dao.masters.StateDAO;
import com.itl.domain.entities.masters.StateMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.StateService;
import com.itl.utils.OmniConstants;

@Service("stateService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class StateServiceImpl extends NGServiceImpl<Long, StateMst>
	implements StateService {

	private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);
	
	@Autowired
	protected StateDAO stateDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) stateDAO);
		String fields[] = { "stateId", "stateName" };
		String cacheContainerName = "stateIdList";
		List<StateMst> state = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up state cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, state, initObjCache);
	}
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		logger.info("Inside setEntityManagerOnDao method :: " + entityManager);
		stateDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public StateMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		StateMst masterObj = stateDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	@Cacheable(value = "stateIdList", key = "#stateName", unless="#result == null")
	public String getStateIdByStateName(String stateName) throws NGException {
		logger.info("Fetching from db - stateId from stateName", stateName);
		String value = stateDAO.getStateIdByStateName(stateName);
		return value;
	}
	

	@Cacheable(value = "stateIdList", key = "#stateId", unless="#result == null")
	public StateMst getByStateId(String stateId) throws NGException {
		logger.info("Fetching from db - state with stateId", stateId);
		List<StateMst> masterList = stateDAO.getByStateId(stateId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	@Cacheable(value = "stateLists", key = "#authStatus", unless="#result == null")
	public List<StateMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - state with authStatus", authStatus);
		List<StateMst> masterList = stateDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	
	@CachePut(value = "stateLists", key = "#authStatus")
	public List<StateMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of state with authStatus : {}", authStatus);
		List<StateMst> state = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=state && state.size()>0)?state:null;
	}

	
	@Caching(evict = {
			@CacheEvict(value = "stateIdList", key = "#result.stateName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "stateIdList", key = "#result.stateId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "stateIdList", key = "#result.stateId", condition = "#result.authStatus == 'A'")
			})
	public StateMst saveOrUpdate(String loginId, StateMst entity) throws NGException {
		StateMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add State by stateId : {}", entity.getStateId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update state record with stateId : {}", entity.getStateId());
				StateMst rmfa = rMaaz;
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
	
	
	public List<StateMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.info("Inside getByDeleted method ");
		List<StateMst> opStat = stateDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from  getByDeleted method");
		return opStat;
	
	}
}
