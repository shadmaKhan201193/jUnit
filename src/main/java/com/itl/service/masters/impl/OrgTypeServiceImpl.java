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
import com.itl.dao.masters.OrgTypeDAO;
import com.itl.domain.entities.masters.OrgTypeMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.OrgTypeService;
import com.itl.utils.OmniConstants;

@Service("orgTypeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class OrgTypeServiceImpl extends NGServiceImpl<Long, OrgTypeMst>
	implements OrgTypeService {

	private static final Logger logger = LoggerFactory.getLogger(OrgTypeServiceImpl.class);
	
	@Autowired
	protected OrgTypeDAO orgTypeDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) orgTypeDAO);
		String fields[] = { "orgTypeId", "orgTypeName" };
		String cacheContainerName = "orgTypeIdList";
		List<OrgTypeMst> orgType = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up orgType cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, orgType, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		orgTypeDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public OrgTypeMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		OrgTypeMst masterObj = orgTypeDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	


	@Cacheable(value = "orgTypeIdList", key = "#orgTypeName", unless="#result == null")
	public String getOrgTypeIdByOrgTypeName(String orgTypeName) throws NGException {
		logger.info("Fetching from db - orgTypeId from orgTypeName", orgTypeName);
		String value = orgTypeDAO.getOrgTypeIdByOrgTypeName(orgTypeName);
		return value;
	}
	

	@Cacheable(value = "orgTypeIdList", key = "#orgTypeId", unless="#result == null")
	public OrgTypeMst getByOrgTypeId(String orgTypeId) throws NGException {
		logger.info("Fetching from db - orgType with orgTypeId", orgTypeId);
		List<OrgTypeMst> masterList = orgTypeDAO.getByOrgTypeId(orgTypeId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	
	@Cacheable(value = "orgTypeLists", key = "#authStatus", unless="#result == null")
	public List<OrgTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - orgType with authStatus", authStatus);
		List<OrgTypeMst> masterList = orgTypeDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	public List<OrgTypeMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Go Inside getByDeleted method ");
		List<OrgTypeMst> opStat = orgTypeDAO.getByDeleted(isDeleted);  
		logger.debug("Exit from getByDeleted method"); 
		return opStat;
	}
	
	@CachePut(value = "orgTypeLists", key = "#authStatus")
	public List<OrgTypeMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of orgType with authStatus : {}", authStatus);
		List<OrgTypeMst> orgType = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=orgType && orgType.size()>0)?orgType:null;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "orgTypeIdList", key = "#result.orgTypeName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "orgTypeIdList", key = "#result.orgTypeId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "orgTypeIdList", key = "#result.orgTypeId", condition = "#result.authStatus == 'A'")
			})
	public OrgTypeMst saveOrUpdate(String loginId, OrgTypeMst entity) throws NGException {
		OrgTypeMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add orgType by orgTypeId : {}", entity.getOrgTypeId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update orgType record with orgTypeId : {}", entity.getOrgTypeId());
				OrgTypeMst rmfa = rMaaz;
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
