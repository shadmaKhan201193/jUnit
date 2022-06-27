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
import com.itl.dao.masters.GenderDAO;
import com.itl.domain.entities.masters.GenderMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.GenderService;
import com.itl.utils.OmniConstants;

@Service("genderService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class GenderServiceImpl extends NGServiceImpl<Long, GenderMst>
	implements GenderService {

	private static final Logger logger = LoggerFactory.getLogger(GenderServiceImpl.class);
	
	@Autowired
	protected GenderDAO genderDAO;

	@Autowired
	private CacheBuilder cacheBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) genderDAO);
		String fields[] = { "genderId", "genderName" };
		String cacheContainerName = "genderIdList";
		List<GenderMst> gender = getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, false);
		Boolean initObjCache = Boolean.TRUE;
		logger.info("Setting up gender cache !");
		cacheBuilder.initializeCache(cacheContainerName, fields, gender, initObjCache);
	}
	
	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		genderDAO.setEntityManager(entityManager);
	}
	
	private static final String CONTROLLED_PREFIX = "AuthList";

	public static String getCacheKey() {
		return CONTROLLED_PREFIX;
	}

	public GenderMst getPrimaryKey(Long Id) throws NGException {
		if (null == Id)
			return null;
		GenderMst masterObj = genderDAO.getPrimaryKey(Id);
		if (null == masterObj) {
			return null;
		} else {
			logger.info("Master Object: " + masterObj);
			return masterObj;
		}
	}
	
	@Cacheable(value = "genderIdList", key = "#genderName", unless="#result == null")
	public String getGenderIdByGenderName(String genderName) throws NGException {
		logger.info("Fetching from db - genderId from genderName", genderName);
		String value = genderDAO.getGenderIdByGenderName(genderName);
		return value;
	}
	
	@Cacheable(value = "genderIdList", key = "#genderId", unless="#result == null")
	public GenderMst getByGenderId(String genderId) throws NGException {
		logger.info("Fetching from db - gender with genderId", genderId);
		List<GenderMst> masterList = genderDAO.getByGenderId(genderId);
		if  (null != masterList && !masterList.isEmpty()) {
			return masterList.get(0);
		}
		return null;
	}

	@Cacheable(value = "genderLists", key = "#authStatus", unless="#result == null")
	public List<GenderMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		logger.info("Fetching from db - gender with authStatus", authStatus);
		List<GenderMst> masterList = genderDAO.getByAuthStatus(authStatus, isDeleted);
		return masterList;
	}


	public List<GenderMst> getByDeleted(Boolean isDeleted) throws NGException {
		logger.info("Inside getByDeleted method ");
		List<GenderMst> opStat = genderDAO.getByDeleted( isDeleted);  
		logger.info("Exit from getByDeleted method...opStat= "+opStat);
		return opStat;
	}
	
	@CachePut(value = "genderLists", key = "#authStatus")
	public List<GenderMst> updateCacheList(String authStatus) throws NGException {
		logger.debug("Manually updating list of gender with authStatus : {}", authStatus);
		List<GenderMst> gender = getByAuthStatus(authStatus, Boolean.FALSE);

		return (null!=gender && gender.size()>0)?gender:null;
	}
	
	@Caching(evict = {
			@CacheEvict(value = "genderIdList", key = "#result.genderName", condition = "#result.authStatus != 'A'"),
			@CacheEvict(value = "genderIdList", key = "#result.genderId", condition = "#result.authStatus != 'A'") },
	put = {				
			@CachePut(value = "genderIdList", key = "#result.genderId", condition = "#result.authStatus == 'A'")
			})
	public GenderMst saveOrUpdate(String loginId, GenderMst entity) throws NGException {
		GenderMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				logger.info("Add gender by genderId : {}", entity.getGenderId());
				return super.saveOrUpdate(loginId, entity);
			} else {
				logger.info("Update gender record with genderId : {}", entity.getGenderId());
				GenderMst rmfa = rMaaz;
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
