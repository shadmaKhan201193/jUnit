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
import com.itl.dao.masters.BranchDAO;
import com.itl.domain.entities.masters.BranchMst;

import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.BranchService;
import com.itl.utils.OmniConstants;

@Service("branchService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class BranchServiceImpl extends NGServiceImpl<Long, BranchMst>
	implements BranchService {

	private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);
	
	@Autowired
	protected BranchDAO branchDAO;
	
	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) branchDAO);
		String fields[] = { "branchId", "branchName" };
		String cacheContainerName = "branchIdList";
		List<BranchMst> branch = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up branch cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, branch, initObjCache);
	}

	@PreDestroy
	public void destroy() {
	}
	
	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		branchDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}


	public BranchMst getPrimaryKey(Long Id) throws NGException {
		logger.debug("Inside getPrimaryKey Method");
		if (null == Id)
			return null;
		BranchMst masterObj = branchDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	
	@Cacheable(value = "branchIdList", key = "#branchName", unless="#result == null")
	public String getBranchIdByBranchName(String branchName) throws NGException {
		logger.info("Fetching from db - branchId from branchName", branchName);
		String value = branchDAO.getBranchIdByBranchName(branchName);
		return value;
	}
	

	@Cacheable(value = "branchIdList", key = "#branchId", unless="#result == null")
	public BranchMst getByBranchId(String branchId) throws NGException{
		logger.info("Fetching from db - branch with branchId", branchId);		
		List<BranchMst> masterList = branchDAO.getByBranchId(branchId);		
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}


	@Cacheable(value = "branchLists", key = "#authStatus", unless="#result == null")
	public List<BranchMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - branch with authStatus", authStatus);
		List<BranchMst> masterList = branchDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}
	
	
	@Caching(evict = {
			@CacheEvict(value = "branchIdList", key = "#result.branchName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "branchIdList", key = "#result.branchId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "branchIdList", key = "#result.branchId", condition = "#result.authStatus == 'A'")
			})
	public BranchMst saveOrUpdate(String loginId, BranchMst entity) throws NGException {
		BranchMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add branch by branchId : {}", entity.getBranchId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update branch record with branchId : {}", entity.getBranchId());
				BranchMst rmfa = rMaaz;
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
	

	@CachePut(value = "branchLists", key = "#authStatus", unless="#result == null")
	public List<BranchMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of branch with authStatus : {}", authStatus);
		List<BranchMst> branch = getByAuthStatus(authStatus, Boolean.FALSE);
		
		return (null!=branch && branch.size()>0)?branch:null;
	}
	
	
	public List<BranchMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.debug("Inside getByDeleted Method");
		logger.info("Go Inside getByDeleted method ");
		List<BranchMst> opStat = branchDAO.getByDeleted( isDeleted);  
		logger.debug("Exit from getByDeleted method");
		return opStat;
	}

	
}
