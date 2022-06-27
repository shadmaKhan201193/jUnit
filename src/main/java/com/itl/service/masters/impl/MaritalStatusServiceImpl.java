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
import com.itl.dao.masters.MaritalStatusDAO;
import com.itl.domain.entities.masters.MaritalStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.MaritalStatusService;
import com.itl.utils.OmniConstants;

@Service("maritalStatusService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class MaritalStatusServiceImpl extends NGServiceImpl<Long, MaritalStatusMst>
	implements MaritalStatusService {

	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusServiceImpl.class);
	
	@Autowired
	protected MaritalStatusDAO maritalStatusDAO;
	
	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) maritalStatusDAO);
		String fields[] = { "maritalStatusId", "maritalStatusName" };
		String cacheContainerName = "maritalStatusIdList";
		List<MaritalStatusMst> maritalStatus = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up maritalStatus cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, maritalStatus, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		maritalStatusDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

    public static String getCacheKey(){
        return CONTROLLED_PREFIX;
    }
    
    public MaritalStatusMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		MaritalStatusMst masterObj = maritalStatusDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
    
    
    @Cacheable(value = "maritalStatusIdList", key = "#maritalStatusName", unless="#result == null")
	public String getMaritalStatusIdByMaritalStatusName(String maritalStatusName) throws NGException {
		logger.info("Fetching from db - maritalStatusId from maritalStatusName", maritalStatusName);
		String value = maritalStatusDAO.getMaritalStatusIdByMaritalStatusName(maritalStatusName);
		return value;
	}
  
    @Cacheable(value = "maritalStatusIdList", key = "#maritalStatusId", unless="#result == null")
   	public MaritalStatusMst getByMaritalStatusId(String maritalStatusId) throws NGException {
   		logger.info("Fetching from db - maritalStatus with maritalStatusId", maritalStatusId);
   		List<MaritalStatusMst> masterList = maritalStatusDAO.getByMaritalStatusId(maritalStatusId);
   		if  (null != masterList && !masterList.isEmpty()) {
   			return masterList.get(0);
   		}
   		return null;
   	}

	
	 @Cacheable(value = "maritalStatusList", key = "#authStatus", unless="#result == null")
		public List<MaritalStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
			logger.info("Fetching from db - maritalStatus with authStatus", authStatus);
			List<MaritalStatusMst> masterList = maritalStatusDAO.getByAuthStatus(authStatus, isDeleted);
			return masterList;
		}
	 

	    @CachePut(value = "maritalStatusList", key = "#authStatus")
		public List<MaritalStatusMst> updateCacheList(String authStatus) throws NGException {
			logger.debug("Manually updating list of maritalStatus with authStatus : {}", authStatus);
			List<MaritalStatusMst> maritalStatus = getByAuthStatus(authStatus, Boolean.FALSE);

			return (null!=maritalStatus && maritalStatus.size()>0)?maritalStatus:null;
		}
	
	
	public List<MaritalStatusMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted method ");
		logger.info(" showing Inside getByDeleted method ");
		List<MaritalStatusMst> opStat = maritalStatusDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from  getByDeleted method");
		return opStat; 
	
	}
	
	 @Caching(evict = {
				@CacheEvict(value = "maritalStatusIdList", key = "#result.maritalStatusName", condition = "#result.authStatus != 'A'"),
				@CacheEvict(value = "maritalStatusIdList", key = "#result.maritalStatusId", condition = "#result.authStatus != 'A'") },
		put = {				
				@CachePut(value = "maritalStatusIdList", key = "#result.maritalStatusId", condition = "#result.authStatus == 'A'")
				})
		public MaritalStatusMst saveOrUpdate(String loginId, MaritalStatusMst entity) throws NGException {
	    	MaritalStatusMst rMaaz = getPrimaryKey(entity.getId());
			try {
				if (null == rMaaz) {
					logger.info("Add MaritalStatus by MaritalStatusId : {}", entity.getMaritalStatusId());
					return super.saveOrUpdate(loginId, entity);
				} else {
					logger.info("Update MaritalStatus record with MaritalStatusId : {}", entity.getMaritalStatusId());
					MaritalStatusMst rmfa = rMaaz;
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
